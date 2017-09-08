Spring-Boot-JSF-Example
=======================

Fork of Zergleb's Spring Boot + JSF Integration, forked from cenobyte321/Spring-Boot-JSF-Example 

# Problem get solved if you set <tomcat.version>8.5.11</tomcat.version to your pom.xml properties section. Then it runs also with Spring-Boot-1.5.6.RELEASE

https://github.com/spring-projects/spring-boot/issues/10232

# This is a demonstration that there is probably a _bug/error/feature/_ during the creation of an executable .war file with spring-boot-maven-plugin since Version >= 1.5.3.RELEASE

Build it with

```
mvn clean install spring-boot:repackage
```

and try to execute it with

```
java -jar target/application-0.0.1-SNAPSHOT.war
```

You will get during startup an Exception like this :

```
2017-09-08 10:48:23.860  INFO 35369 --- [ost-startStop-1] j.e.resource.webcontainer.jsf.config     : Mojarra 2.2.7 ( 20140610-1547 https://svn.java.net/svn/mojarra~svn/tags/2.2.7@13362) für Kontext '' wird initialisiert.

2017-09-08 10:48:27.938 ERROR 35369 --- [ost-startStop-1] j.e.resource.webcontainer.jsf.config     : Critical error during deployment: 

com.sun.faces.config.ConfigurationException: java.util.concurrent.ExecutionException: javax.faces.FacesException: java.io.FileNotFoundException: JAR entry META-INF/ not found in /var/folders/gj/...
```

Changing spring-boot-parent version in pom to something lower 1.5.3.RELEASE it works fine.





### Original Readme

## Changes

- Maven based project
- Spring Security integration with Spring Security Tags
- Directory structure
- JSF Message resource bundle
- H2 persistence with Console enabled for development purposes
- Session and View expiration handling

## Run

To run use 

```
mvn spring-boot:run
```

## Credentials

Admin: admin:12345

User: usuario:12345

## URLS

[http://localhost:8080/greeting](http://localhost:8080/greeting) - Public Spring MVC view

[http://localhost:8080/login.xhtml](http://localhost:8080/login.xhtml) - Login page, defaults to this route

[http://localhost:8080/admin.xhtml](http://localhost:8080/admin.xhtml) - Protected view for admin role

[http://localhost:8080/test.xhtml](http://localhost:8080/test.xhtml) - Protected view for admin and user role

[http://localhost:8080/console](http://localhost:8080/console) - H2 Console (Note: should only be available for development, tip: use profile feature)

## Note from original

There was a common belief that because JSF and Spring MVC were their own view technologies and that they could not be used together, but this is incorrect and is part of this example.
