# Certified
Diploma thesis for NTUA Electrical and Computer Engineering

## Project Description
The goal is to implement a platform that anyone can have free access to and verify documents through the blockchain. The users of this platform are the main user or recruiter who wants to verify a certificate through the blockchain and the admin user or company who can manage subscriptions. This platform will help each individual to have a legit verification for the documents he needs and saves him time because the old certification methods are very time-consuming and demanding. Finally, the primary tasks of the project is to develop a platform-webapp that is usable, creat understandable documentations and functional-adaptive code.

* [Παππάς Δαμιανός](https://github.com/damianosp7), Α.Μ.: 03116608

## Project Structure
**/src/main/java/com/example/certified/** : The source code for the REStFull API requests.(controllers, methods,classes)

- config (SpringSecurity configuration for login and security in general)
- model (models of user and docs)
- repository (repositories for user and docs)
- service (all the main functionalities)
- web (controllers)

**/src/main/resources/** : The source code for the front-end and the web-page.

- static
- templates (all the html pages)
- static (js and css files for the animations and the overall UI of the web-app)

## Setup for the database
1. Install [mysql installer community](https://dev.mysql.com/downloads/installer/)
2. Install [wampserver](https://sourceforge.net/projects/wampserver/) (*optional)
3. Open Mysql-Installer and download Mysql-Workbench, Mysql-Shell and Mysql-Server.
4. Open Mysql-Workbench and create a localhost with username **root** and password **root**. (if you already have a localhost connection with different credentials you have to replace these credentials in pom.xml)
5. Create new schema in database with the name of "certified"   
5. In order to create new schema manually do the following steps:
    - CREATE SCHEMA certified;
   
## Setup for the pom.xml (if you dont create localhost with the given username and password)
1. Open pom.xml file (Certified/pom.xml)
2. Find <dbUserName>root</dbUserName>, <dbPassword>root</dbPassword> (lines 35 and 36)
3. Replace 'root' with your mysql credentials

## Setup for the backend locally
1. Install [IntelliJ ](https://www.jetbrains.com/idea/download) (or other equivalent IDE)
2. Fork this project and save it locally
3. Open the folder of this project with your IDE
4. In maven tab on your right side of your screen select 'Local' in profiles and click the reload all maven projects

## Setup for the backend On Cloud (Recommended)
1. Install [IntelliJ ](https://www.jetbrains.com/idea/download) (or other equivalent IDE)
2. Fork this project and save it locally
3. Open the folder of this project with your IDE
4. In maven tab on your right side of your screen select 'Azure' in profiles and click the reload all maven projects



## Run the Project
1. Click the run 'CertifiedApplication' and got to [localhost](http://localhost:8080/)
2. You can register and login with new user
3. Admin credentials are Username: root , Password: root

## Technologies used
<u>DataBase</u>

* MySQL
* PhpMyAdmin
* JPA/Hibernate
* Azure Mysql Database

<u>back-end</u>

* Java
* SpringBoot MVC
* SpringSecurity
* Google mail server
* Maven


<u>front-end</u>

* Html
* Javascript
* CSS
* SCSS
* Thymeleaf
* Bootstrap