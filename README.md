#An Java application for Exam monitoring (CBT)

#use git clone to clone the repository

#install net-tools dependency

sudo apt install net-tools

#install usb dependency

Step 1 : Create Blacklist Config File

First need to create a file by following the commands.

sudo nano /etc/modprobe.d/blacklist.conf

In the end of the config file we need to add the following code.

blacklist usb_storage
blacklist uas

Save and exit from the nano editor.
Step 2: Create rc.local file

Also need to open the /etc/rc.local config file using following commands.

sudo nano /etc/rc.local

Add the given lines as below:-

modprobe -r uas