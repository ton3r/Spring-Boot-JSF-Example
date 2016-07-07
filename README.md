Spring-Boot-JSF-Example
=======================

Fork of Zergleb's Spring Boot + JSF Integration

##Changes##

- Maven based project
- Spring Security integration with Spring Security Tags
- Directory structure
- JSF Message resource bundle
- H2 persistence with Console enabled for development purposes
- Session and View expiration handling

##Run##

To run use 

```
mvn spring-boot:run
```

##Credentials##

Admin: admin:12345

User: usuario:12345

##URLS##

[http://localhost:8080/greeting](http://localhost:8080/greeting) - Public Spring MVC view

[http://localhost:8080/login.xhtml](http://localhost:8080/login.xhtml) - Login page, defaults to this route

[http://localhost:8080/admin.xhtml](http://localhost:8080/admin.xhtml) - Protected view for admin role

[http://localhost:8080/test.xhtml](http://localhost:8080/test.xhtml) - Protected view for admin and user role

[http://localhost:8080/console](http://localhost:8080/console) - H2 Console (Note: should only be available for development, tip: use profile feature)

##Note from original##

There was a common belief that because JSF and Spring MVC were their own view technologies and that they could not be used together, but this is incorrect and is part of this example.
