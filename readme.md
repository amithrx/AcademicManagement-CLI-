<center>
<h1> Academic management system </h1>
## Folder Structure

The workspace contains following folders, where:

- `src\main\java\softwareproject`: the folder to maintain sources file
- `src\test\java\softwareproject`: the folder to maintain tests file
- `lib`: the folder to maintain dependencies
- `db` : the folder to maintain sql database files
- `app\bin\main\softwareproject` : the folder to maintain .class files for source class
- `app\bin\test\softwareproject` : the folder to maintain .class files for test class
- `assets` : the folder to maintain the transcripts generated and grade submission files 
```
.
├── readme.md
├── db
│   ├── cleardb.sql
│   ├── db.sql
│   └── startPostgres.sh
├── lib
│   └── postgresql-42.5.0.jar
└── src
│    ├── main
│        ├── java
│            ├── softwareproject
│                ├── Admin.java
│                ├── App.java
│                ├── DBconnect.java
│                ├── Instructor.java
│                ├── Person.java
│                ├── Student.java
│                ├── ValidateUsers.java
│    ├── test
│        ├── java
│            ├── softwareproject
│               ├── All test files
└── app
│   ├── build
│       ├── reports
│           ├── jacoco
│               ├── test
│                   ├── html containing jacoco reports
└── app
│   ├── build
│       ├── reports
│           ├── tests
│               ├── test
│_______________├── html containing test plan
```
## Steps to run and test :
### for starting postgres server :
- Open the ubuntu terminal and enter into db folder
- then run the following command:
```
./startPostgres.sh 
and enter into the respective database
``` 
 - run the following command to create and connect to the database:
 ```
\i cleardb.sql
\i db.sql
 ```
- In the second window terminal you will need to run the app.java file

## Steps to build the test :
- Open the window terminal and enter the following command :
```
./gradlew.bat build
./gradlew.bat jacocoTestReport  
```
## Included :
- ER diagram
- Class diagram
- Activity diagram
