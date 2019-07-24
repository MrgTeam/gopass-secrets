-------
Jenkins
------- 

Create new credentials 
~~~~~~~~~~~~~~~~~~~~~~

 * Import jenkins-public-key.asc   -> the id will be use in the pipeline file
 * Set git credentials -> the id will be use in the pipeline file
 * Gopass crypt unlock :

.. code-block:: groovy	

   // Pipeline //
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


* Create pipeline secret with : 

.. code-block:: groovy

   def admin_username = sh(script: "gopass show -o ${secrets_store_name}/envs/prod/presence/JWT_PASSWORD", returnStdout: true)


`Here a concrete pipeline configuration file <https://git-scale-tools.scale-n-eu.sanofi.com/abessifi/openshift-utils/src/master/cd-pipeline/jenkins/pipeline-with-gopass-integration.groovy>`


