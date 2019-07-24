--------
Optional
--------

Manual GIT configuration
~~~~~~~~~~~~~~~~~~~~~~~~

List secret key 
+++++++++++++++

.. code-block:: shell 

   $ gpg --list-secret-keys --keyid-format LONG

    result 
    sec   4096R/3AA5C34371567BD2 2016-03-10 [expires: 2017-03-10]
    uid                          Hubot 
    ssb   4096R/42B317FD4BA89E7A 2016-03-10


Add key to GIT config 
+++++++++++++++++++++

.. code-block:: shell 

   git config --local user.signingkey 3AA5C34371567BD2 (Key from list-secret-keys ...) 
   git config --local user.email <same email as gen-key-script>
   git config --local commit.gpgsign true



.. warning:: If ubuntu 16.04   

   .. code-block:: shell

      git config --local gpg.program gpg2


Configure git to use commit signature 
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Verify GIT compatibility
++++++++++++++++++++++++

.. code-block:: shell

   git add .
   git commit -S -m "Initial commit" 


Export gpg plublic key to GitHub
++++++++++++++++++++++++++++++++

* Export the key

.. code-block:: shell 

   $ gpg --armor --export 3AA5C34371567BD2


* Copy the key to GitHub settings GPG keys

.. code-block:: shell

   -----BEGIN PGP PUBLIC KEY BLOCK-----
   .
   .
   .
   -----END PGP PUBLIC KEY BLOCK-----
