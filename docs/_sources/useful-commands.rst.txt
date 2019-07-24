---------------
Useful commands 
---------------

Delete a secret key
+++++++++++++++++++

   .. code-block:: shell 

      gpg --delete-secret-key "username"


List secret keys 
++++++++++++++++

   .. code-block:: shell 

      $ gpg --list-secret-keys --keyid-format LONG
       result 
       sec   4096R/3AA5C34371567BD2 2016-03-10 [expires: 2017-03-10]
       uid                          Hubot 
       ssb   4096R/42B317FD4BA89E7A 2016-03-10