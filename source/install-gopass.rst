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

