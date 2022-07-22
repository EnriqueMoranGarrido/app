# Getting Started

[//]: # (### Reference Documentation)

[//]: # (For further reference, please consider the following sections:)

[//]: # ()
[//]: # (* [Official Apache Maven documentation]&#40;https://maven.apache.org/guides/index.html&#41;)

[//]: # (* [Spring Boot Maven Plugin Reference Guide]&#40;https://docs.spring.io/spring-boot/docs/2.7.1/maven-plugin/reference/html/&#41;)

[//]: # (* [Create an OCI image]&#40;https://docs.spring.io/spring-boot/docs/2.7.1/maven-plugin/reference/html/#build-image&#41;)

[//]: # (* [Spring Web]&#40;https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#web&#41;)

[//]: # (* [Spring Data JPA]&#40;https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#data.sql.jpa-and-spring-data&#41;)

[//]: # ()
[//]: # (### Guides)

[//]: # (The following guides illustrate how to use some features concretely:)

[//]: # ()
[//]: # (* [Building a RESTful Web Service]&#40;https://spring.io/guides/gs/rest-service/&#41;)

[//]: # (* [Serving Web Content with Spring MVC]&#40;https://spring.io/guides/gs/serving-web-content/&#41;)

[//]: # (* [Building REST services with Spring]&#40;https://spring.io/guides/tutorials/rest/&#41;)

[//]: # (* [Accessing Data with JPA]&#40;https://spring.io/guides/gs/accessing-data-jpa/&#41;)

[//]: # ()

# Virtual Wallet

[//]: # ()
[//]: # ([![Build Status]&#40;https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master&#41;]&#40;https://travis-ci.org/codecentric/springboot-sample-app&#41;)

[//]: # ([![Coverage Status]&#40;https://coveralls.io/repos/github/codecentric/springboot-sample-app/badge.svg?branch=master&#41;]&#40;https://coveralls.io/github/codecentric/springboot-sample-app?branch=master&#41;)

[//]: # ([![License]&#40;http://img.shields.io/:license-apache-blue.svg&#41;]&#40;http://www.apache.org/licenses/LICENSE-2.0.html&#41;)

Virtual Wallet for [Agiles Engine](http://projects.spring.io/spring-boot/) Java Junior Bootcamp app.

## Requirements

For building and running the application you need:

- [Java Development Kit (JDK).](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

The base project created for you was made for Java 17 so that’s the version you should get.
  Make sure you install a JDK and not a JRE!

- [Maven 3.](https://maven.apache.org)

What we’ll use to build the project and manage dependencies. The project created for you
  comes with a Maven wrapper built-in. Should you need to install it, there are plenty of guides
  online on how to do so.




## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.aevw.app.AppApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

One way is to open up the project and navigate to the target folder `\app\target` and executing the jar file from your IDE like so:

```shell
java -jar app-0.0.1-SNAPSHOT.jar
```

The port in which the application can be configured like so:
```shell
java -jar app-0.0.1-SNAPSHOT.jar --server.port= [port]
```

[//]: # ()
[//]: # (## Deploying the application to OpenShift)

[//]: # ()
[//]: # (The easiest way to deploy the sample application to OpenShift is to use the [OpenShift CLI]&#40;https://docs.openshift.org/latest/cli_reference/index.html&#41;:)
[//]: # ()
[//]: # (```shell)

[//]: # (oc new-app codecentric/springboot-maven3-centos~https://github.com/codecentric/springboot-sample-app)

[//]: # (```)

[//]: # (This will create:)

[//]: # ()
[//]: # (* An ImageStream called "springboot-maven3-centos")

[//]: # (* An ImageStream called "springboot-sample-app")

[//]: # (* A BuildConfig called "springboot-sample-app")

[//]: # (* DeploymentConfig called "springboot-sample-app")

[//]: # (* Service called "springboot-sample-app")
