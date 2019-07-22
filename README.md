

### Install gopass

#### On Linux

Add the package source.

```
wget -q -O- https://api.bintray.com/orgs/gopasspw/keys/gpg/public.key | sudo apt-key add -
echo "deb https://dl.bintray.com/gopasspw/gopass trusty main" | sudo tee /etc/apt/sources.list.d/gopass.list
sudo apt-get update
sudo apt-get install gopass
```

#### On windows

You can install gopass by Chocolatey :

```
choco install gopass
```

### Install GPG

source : [Setup](https://github.com/gopasspw/gopass/blob/master/docs/setup.md)

#### On Linux

```
apt-get install gnupg git rng-tools
```

**IMPORTANT:**

If ubuntu 16.04  ->  gnupg2 

#### On windows

Go to :  [Setup](https://www.gpg4win.org/)

### Generate Jenkins GPG keys  !! whithout passPhrase !!

Create a gen-key-script file with the following content and run the gpg command to generate the keys in batch mode:

```
$ cat gen-key-script
Key-Type: 1
Key-Length: 2048
Subkey-Type: 1
Subkey-Length: 2048
Name-Real: <jenkins-username>
Name-Email: <jenkins-email>
Expire-Date: 5y

$ gpg --batch --gen-key gen-key-script (no passphrase !)
$ gpg --export-secret-keys -a <jenkins-username> > jenkins-secret-key.asc
$ gpg --export -a <jenkins-username> > jenkins-public-key.asc
```


### Initialise password store 

```
gopass init
```

Create a .password-store GIT directory in home directory.
- To personalize the directory go [here][https://github.com/gopasspw/gopass/blob/master/docs/features.md)

### Configure GIT  

#### List secret key 

```
$ gpg --list-secret-keys --keyid-format LONG

result 
sec   4096R/3AA5C34371567BD2 2016-03-10 [expires: 2017-03-10]
uid                          Hubot 
ssb   4096R/42B317FD4BA89E7A 2016-03-10
```

#### Add key to GIT config 

```
git config --local user.signingkey 3AA5C34371567BD2 (Key from list-secret-keys ...) 
git config --local user.email <same email as gen-key-script>
git config --local commit.gpgsign true
```

**IMPORTANT:**

If ubuntu 16.04 :  

```
git config --local gpg.program gpg2
```

### Gopass usage

#### Create a new secret 

```
$ gopass insert presence/JWT_PASSWORD 
$ gopass

gopass
└── presence
    └── JWT_PASSWORD

$ gopass show presence/JWT_PASSWORD
test
```



### On Jenkins 

#### Create new credentials 

- Import jenkins-public-key.asc   -> the id will be use in the pipeline file
- Set git credentials -> the id will be use in the pipeline file
- Gopass crypt unlock :

```
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
```

- Create pipeline secret with : 

```
def admin_username = sh(script: "gopass show -o ${secrets_store_name}/envs/prod/presence/JWT_PASSWORD", returnStdout: true)
```


### Optional 

#### Configure git to use commit signature 

##### verify GIT compatibility (optional)

```
git add .
git commit -S -m "Initial commit" 
```

##### Export gpg plublic key to GitHub (optional)

```
$ gpg --armor --export 3AA5C34371567BD2
```

- Copy to GitHub settings GPG keys

```
-----BEGIN PGP PUBLIC KEY BLOCK-----
.
.
.
-----END PGP PUBLIC KEY BLOCK-----
```