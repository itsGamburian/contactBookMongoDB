This is a different approach to the contactBook app using a live mongoDB database, where the other version of the app was using vectors a text file as a database.

First clone the repo.
From there, the project can be opened in IntelliJ IDEA and ran from there.

This contact book uses a live mongoDB database that will need to be locally installed on your machine. After successful installation, navigate to the folder of the initial installation files and run the commands

mongod --dbpath="/your/contactBook/folder/path"

mongo

The dependencies to run the app are Java8 JRE (Java 1.8 Java Runtime Environment), and mongoDB.