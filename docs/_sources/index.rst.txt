.. test documentation master file, created by
   sphinx-quickstart on Tue Jul 23 15:08:53 2019.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Welcome to Gopass documentation!
================================

.. toctree::
   :maxdepth: 2
   :caption: Contents:

Install Gopass 
--------------

Linux
~~~~~

Add the package source.

.. code-block:: shell

   wget -q -O- https://api.bintray.com/orgs/gopasspw/keys/gpg/public.key | sudo apt-key add -
   echo "deb https://dl.bintray.com/gopasspw/gopass trusty main" | sudo tee /etc/apt/sources.list.d/gopass.list
   sudo apt-get update
   sudo apt-get install gopass


Windows
~~~~~~~

You can install gopass by Chocolatey :

.. code-block:: shell

   choco install gopass


Install GPG
-----------

:Source: `Doc <https://github.com/gopasspw/gopass/blob/master/docs/setup.md>`_

Linux
~~~~~

.. code-block:: shell

   apt-get install gnupg git rng-tools



.. warning:: If ubuntu 16.04 use gnupg2 

Windows
~~~~~~~

:Source: `Doc <https://www.gpg4win.org/<`

Generate Jenkins GPG keys  !! whithout passPhrase !!
----------------------------------------------------

Create a gen-key-script file with the following content and run the gpg command to generate the keys in batch mode:

.. code-block:: shell

   $ cat gen-key-script
   Key-Type: 1
   Key-Length: 2048
   Subkey-Type: 1
   Subkey-Length: 2048
   Name-Real: <jenkins-username>
   Name-Email: <jenkins-email>
   Expire-Date: 5y

   $ gpg --batch --gen-key gen-key-script (no passphrase - click "OK" )

   result
   gpg: clef 0.........1 marquée de confiance ultime.
   gpg: revocation certificate stored as 'C:/Users/username/AppData/Roaming/gnupg/openpgp-revocs.d\A2D..................rev'

   $ gpg --export-secret-keys -a <jenkins-username> > jenkins-secret-key.asc
   $ gpg --export -a <jenkins-username> > jenkins-public-key.asc



Initialise password store 
-------------------------

 * First create a private GIT repository.

 * Then, let's start configuring gopass

.. code-block:: shell

   $ gopass
     It seems you are new to gopass. Do you want to run the onboarding wizard? [Y/n/q]: Y (select local store)

     [init] [local] Initializing your local store ...
     Please select a private key for encrypting secrets:
     [0] gpg - 0x0564165464S4 - jenkins-username <jenkins-email>
     Please enter the number of a key (0-0, [q]uit) [0]: 0 (select the key id - here 0)

     Use jenkins-username (jenkins-email) for password store git config? [Y/n/q]: Y
     [init] [local]  -> OK
     [init] [local] Configuring your local store ...
     [init] [local] Do you want to add a git remote? [y/N/q]: y
     [init] [local] Configuring the git remote ...
     Please enter the git remote for your shared store []: http://git-scale-tools.scale-n-eu.sanofi.com/I0383327/presenceSecrets.git ( paste your git remote )

     [init] [local] Do you want to automatically push any changes to the git remote (if any)? [Y/n/q]: Y
     [init] [local] Do you want to always confirm recipients when encrypting? [y/N/q]: y
     [init] [local]  -> OK
     gopass (here your empty password store)


By default, the password store is stored in the .password-store GIT directory in home directory.
 * To personalize the directory go [here](https://github.com/gopasspw/gopass/blob/master/docs/features.md)


Gopass usage
------------

Create a new secret 
~~~~~~~~~~~~~~~~~~~

.. code-block:: shell

   $ gopass insert presence/JWT_PASSWORD 
     Enter password for presence/JWT_PASSWORD []: test
     Retype password for presence/JWT_PASSWORD []: test
     Warning: Password is too short
     gopass: Encrypting presence/JWT_PASSWORD for these recipients:
     - ADFSSDF5646QZEF86741 - 0x5641967419678 - jenkins-username <jenkins-email>

     Do you want to continue? [Y/n/q]: Y
     Pushed changes to git remote

   $ gopass

   gopass
   └── presence
       └── JWT_PASSWORD

   $ gopass show presence/JWT_PASSWORD
   test

