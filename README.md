# University Grading System

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

The Application simulate basic University System, providing features of Instructor as adding marks for Students, moreover Student could Calculate Fees or Average of marks, or add/drop courses related to Student identity. 

- IDE
- Java
- PostgresSQL

## Mandatory-Installation

- Installation Postgres  for linux os https://computingforgeeks.com/install-postgresql-13-on-fedora/ for windows and mac https://www.datacamp.com/tutorial/installing-postgresql-windows-macosx
- Installation MySQL for windows and mac and linux  https://overiq.com/installing-mysql-windows-linux-and-mac/ 
- java 8 or higher and  set JAVA_HOME
- Start code

Technical Specs :

After Installation, you will need to create DataBase called university . please be careful when you set the java in project structure and when you download postgreSQl JDBC.jar be careful if its compatible with Postgres that you have installed down below i will explain this step by step .

### Download postgreSQL JDBC

For get connection with Database and execute statements are in program, you need to download the jar file and embedded it in Dependencies the images below is too important and i will  add the site where you can download the PostgreSQL JDBC 

<div>
https://jdbc.postgresql.org/download.html
</div>

<div>
<img src="https://github.com/abdullahalmasri/University-Grading-System/blob/bb789ec52d62c2dea1ea6a97c03f62a5de2f2a49/screenShots/Screenshot%20from%202022-06-25%2013-28-04.png" alt="">
</div>

### 1. Create table in postgresDB

> sudo -i -u postgres
> psql
> \password 
> "your-password"
> CREATE DATABASE "DATABASE_NAME";

 the result will be as below , i am using centos7 OS, but in windows there will be interface of pgAdmin also in linux and mac, but i prefer dealing with terminal.

<div>
<img src="https://github.com/abdullahalmasri/University-Grading-System/blob/main/screenShots/Screenshot%20from%202022-06-25%2013-43-01.png" alt"terminal"/>
</div>

### 2. check java version to be compatible with JDBC

to check java version all you need is to open terminal 

> java -version 



## 3.start code :P

Finally we reach out to fun part, where we could change code this part, i will explain how to make the sql file.sql execute and read statements but be sure you got path of sql file in your computer.  in class SQLConn there are String called aSQLScriptFilePath

where you need to open the file where you clone the project and press right click in file Tables.sql and click at  properties and copy the path of the file exists in resources folder and paste the path in aSQLScriptFilePath in project. :D

we are done and we can start the application :peace

*please remember this project is for learning more than being complete project to use.  have fun*

OS used Centos7 
