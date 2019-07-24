------------
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


