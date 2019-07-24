----------------------------------------------------
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



