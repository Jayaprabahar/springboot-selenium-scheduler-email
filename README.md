# Welcome

Welcome to springboot-selenium-scheduler-email application !!
A simple example of selenium application written using springboot which is scheduled to check the application status and send email based on the availability

## Features/Constraints

1.	It is a SpringBootApplication
2.	It has a scheduler which will trigger selenium scripts to see whether server down status is disappered.
3.	It will send email to the receipents

## Versions

1.	spring-boot-starter-parent	2.3.1.RELEASE
2.	Open JDK 11

## Configurations

You can edit following configurations in the application.properties file

1.	Update all the application configurations according to your needs
2.	Scheduler configurations has some default values. Change it according to your needs.
3.	Headless Run configuration has some default values. Default. If you want to see the browser, then change it to false

## Prerequisites

1.	Java 11 and above
2.	Maven tool


## Build


Use any tools that supports executing scripts. Like Gitbash, powershell, shell, commandline

Use the following command to create the package. "mvn clean install"

You should able to see the application building, test execution and successful creation of springboot-selenium-scheduler-email jar file

```
$ mvn clean install

$ cd target/

$ ls
springboot-selenium-scheduler-email-1.0.0.jar  springboot-selenium-scheduler-email-1.0.0.jar.original  classes/  generated-sources/  generated-test-sources/  maven-archiver/  maven-status/  surefire-reports/  test-classes/

$ java -jar springboot-selenium-scheduler-email-1.0.0.jar

```

*Have fun!*
Best Regards
Jayaprabahar