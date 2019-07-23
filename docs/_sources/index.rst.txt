.. test documentation master file, created by
   sphinx-quickstart on Tue Jul 23 15:08:53 2019.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Welcome to Gopass documentation!
================================

.. toctree::
   :maxdepth: 2
   :caption: Contents:

   page1

Install Gopass 
--------------

On Linux
~~~~~~~~

Add the package source.

.. code-block:: Bash shell scripts
   wget -q -O- https://api.bintray.com/orgs/gopasspw/keys/gpg/public.key | sudo apt-key add -
   echo "deb https://dl.bintray.com/gopasspw/gopass trusty main" | sudo tee /etc/apt/sources.list.d/gopass.list
   sudo apt-get update
   sudo apt-get install gopass

Indices and tables
==================

* :ref:`genindex`
* :ref:`modindex`
* :ref:`search`
