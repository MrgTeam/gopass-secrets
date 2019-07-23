// Global variables
def openshift_project_name = 'cable-dev'
def secrets_git_repo_url = 'https://git....'
def secrets_store_name = 'test-gopass'
def jenkins_username = 'cable-prod-jenkins'
def jenkins_email = 'cable-prod-jenkins@scale-eu.sanofi.com'
def jenkins_git_credentials_id = 'cable-prod-jenkins-git-credentials'
def jenkins_gpg_credentials_id = 'jenkins-gpg-keys'

// createBasicAuthSecret() creates an OpenShift Secret with one or more key/value pairs.
def createBasicAuthSecret(String secret_name, String username, String password) {

  def obj = [
      "kind": "Secret",
      "metadata": [
          "name": "${secret_name}",
          "labels": [
              "secret":"test-gopass"
          ]
      ],
      "stringData": [
          "username": "${username}",
          "password": "${password}"
      ]
  ]
  // openshift.create() will marshal the model into JSON, validate and send it to the API server.
  return openshift.create(obj, '--validate')
}

// Pipeline //
node ('jenkins-slave-gopass') {
  stage('Gopass Crypt Unlock') {
    // Import the Jenkins GPG keys
    withCredentials([file(credentialsId: "${jenkins_gpg_credentials_id}", variable: 'JENKINS_GPG_KEYS_FILE')]) {
      sh "gpg --batch --import ${JENKINS_GPG_KEYS_FILE}"
    }
    // Setup global Git configuration
    sh """
      echo "[INFO] Setting up global Git configuration"
      git config --global user.name ${jenkins_username}
      git config --global user.email ${jenkins_email}
      git config --global --list
    """
    // Change the workdir and init gopass secrets store
    dir("/home/jenkins/${secrets_store_name}"){
      git credentialsId: "${jenkins_git_credentials_id}", branch: 'master', url: "${secrets_git_repo_url}"
    }
    sh """
      echo "[INFO] Init Gopass secrets store"
      gopass config autosync false
      gopass --yes init ${jenkins_email}
      gopass --yes mounts add ${secrets_store_name} /home/jenkins/${secrets_store_name}
    """
  }

  stage('Create secrets') {
    // Select the OpenShift targeted project/namespace
    openshift.withCluster() {
      openshift.withProject( openshift_project_name ) {
        // Delete all secrets with label 'secret:test-gopass'
        def secrets = openshift.selector('secret', [secret: 'test-gopass'])
        if (secrets.exists()) {
          secrets.delete()
        }
        // Create new secret for Grafana app
        def grafana_secret_name = 'grafana-test-credentials'
        def grafana_admin_username = sh(script: "gopass show -o ${secrets_store_name}/envs/prod/grafana/admin-username", returnStdout: true)
        def grafana_admin_password = sh(script: "gopass show -o ${secrets_store_name}/envs/prod/grafana/admin-password", returnStdout: true)

        // NOTE: To get the value of a binary type secret, it's required to include the `< /dev/null`
        // operation at the end of the `gopass binary cat` command to block the STDIN.
        // Otherwise it won't work.
        // ISSUE: https://github.com/gopasspw/gopass/issues/1051
        // def builder_sa_token = sh(script: "gopass binary cat ${secrets_store_name}/serviceaccounts/tokens/builder < /dev/null", returnStdout: true)

        // Create the basic-auth secret for Grafana app
        // createBasicAuthSecret() returns a Selector which will select the resulting object(s)
        try {
          def obj = createBasicAuthSecret(grafana_secret_name, grafana_admin_username, grafana_admin_password)
          obj.describe()
        } catch (err) {
          // Best practise: do other staff if needed, print error message and exit the Pipeline
          error("Error encountered: ${err}")
        }
      }
    }
  }
}