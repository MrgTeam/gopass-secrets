-------------------------
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


