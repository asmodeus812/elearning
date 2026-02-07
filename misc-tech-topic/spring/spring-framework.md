# Introduction

Spring boot is an open source java based framework used to create micro services, it is developed by Pivotal it is
easy to create a stand alone and production read spring application, using spring boo. Spring Boot enterprise ready
application that you can just run.

## Document Prerequisites

This introduction is written to developers and readers who already have experience with Java, Spring, Maven and Gradle,
you can easily understand the concepts of Spring Boot, if you have the aforementioned knowledge already. If you are a
beginner we suggest you to go through some resources which serve as introduction to the topics and technologies
mentioned above before you start reading this resource. This document mainly focuses on working with Spring Boot 3.0.0,
and Spring 6.0.0.

## Spring and Spring boot

There are two different concepts here, which are Spring and Spring Boot. What is the difference between these ? Spring
is the core framework, it consists of many components which are completely de-coupled from each other, they are
standalone components used in building systems, they can be composed and layered on top of each other to build complex
systems. However this requires a good amount of configuration to do, it is a complex process and it is more often than
not very common to build applications of the same type very often. Therefore the same amount of work has to be done
every time. This is where the Spring Boot comes in, the spring boot framework is also a list of components which are
simply Spring components that are pre-configured to work together. These components are usually represented by Gradle or
Maven dependencies, and to distinguish them from the regular spring components they are suffixed with the '-boot' work
in their name, they are nothing more than a collection of raw Spring components that are made to work together, but in
its core Spring Boot is simply just a well defined and configured common set of components for developing applications
on top of the core Spring framework where all the cruft and complexity is abstracted into

Spring Boot automatically configures your application based on the dependencies you have added to the project by using
`@EnableAutoConfiguration` annotation. For example, if MySQL database is on your `classpath`, but you have not
configured any database connection, then Spring Boot auto-configures an in-memory database. The entry point of the
spring boot application is the class contains `@SpringBootApplication` annotation and the main method. Spring Boot
automatically scans all the components included in the project by using `@ComponentScan` annotation.

As mentioned already all spring boot components contain the spring-boot name prefix, along with the starter word, which
is simply a way of marking a component as part of the spring-boot framework, and the starter signals that this component
is meant to be used as a bedrock for starting with the development, it should contain all the basics that one should
expect for the dependency to work with spring and all of its internal components, without requiring a massive amount of
user defined configuration to produce something useable

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Here is a general idea of what an entry point looks like in a spring boot application, where we have the following
annotation, `@SpringBootApplication`, this annotation alone is a combination of many other annotations, which are in
charge of constructing the initial context for running your application.

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

     public static void main(String[] args) {
            SpringApplication.run(DemoApplication.class, args);
     }
}
```

## Getting started

This section will teach you how to create a Spring Boot application using Maven and Gradle.

### Command line interface

The Spring Boot CLI is a command line tool and it allows us to run the Groovy scripts. This is the easiest way to create
a Spring Boot application by using the Spring Boot Command Line Interface. You can create, run and test the application
in command prompt itself.

The spring cli has a neat way to generate projects, by simply providing the name of the project and some build and
project arguments one can build any type of project skeleton very quickly, with the required set of dependencies and
build environment conditions. This is mostly applicable for Maven and Gradle projects.

```sh
# this would build a very simple maven project skeleton, that would only include the basic spring boot starter and
# starter test dependencies and simple pom file for the maven project, as specified in the command below
$ spring init spring-boot --java-version=17 --build=maven
```

```sh
# here is the list, heavily abridged for demonstration purposes, that lists what are the supported dependencies, project
# types and, different project parameters one can pass to the spring cli to construct a project skeleton
$ spring init --list
```

```
Supported dependencies
+--------------------------------------------+--------------------------------------------------------------+-----------------------+
| Id                                         | Description                                                  | Required version      |
+--------------------------------------------+--------------------------------------------------------------+-----------------------+
| activemq                                   | Spring JMS support with Apache ActiveMQ 'Classic'.           |                       |
|                                            |                                                              |                       |
| actuator                                   | Supports built in (or custom) endpoints that let you monitor |                       |
|                                            | and manage your application - such as application health,    |                       |
|                                            | metrics, sessions, etc.                                      |                       |
|                                            |                                                              |                       |
| amqp                                       | Gives your applications a common platform to send and        |                       |
|                                            | receive messages, and your messages a safe place to live     |                       |
|                                            | until received.                                              |                       |
|                                            |                                                              |                       |
| azure-support                              | Auto-configuration for Azure Services (Service Bus, Storage, | >=3.4.0 and <3.5.0-M1 |
|                                            | Active Directory, Key Vault, and more).                      |                       |
|                                            |                                                              |                       |
| batch                                      | Batch applications with transactions, retry/skip and chunk   |                       |
|                                            | based processing.                                            |                       |
|                                            |                                                              |                       |
| cache                                      | Provides cache-related operations, such as the ability to    |                       |
|                                            | update the content of the cache, but does not provide the    |                       |
|                                            | actual data store.                                           |                       |
|                                            |                                                              |                       |
|                                            |                                                              |                       |
| wavefront                                  | Publish metrics and optionally distributed traces to Tanzu   | >=3.4.0 and <3.6.0-M1 |
|                                            | Observability by Wavefront, a SaaS-based metrics monitoring  |                       |
|                                            | and analytics platform that lets you visualize, query, and   |                       |
|                                            | alert over data from across your entire stack.               |                       |
|                                            |                                                              |                       |
| web                                        | Build web, including RESTful, applications using Spring MVC. |                       |
|                                            | Uses Apache Tomcat as the default embedded container.        |                       |
|                                            |                                                              |                       |
| web-services                               | Facilitates contract-first SOAP development. Allows for the  |                       |
|                                            | creation of flexible web services using one of the many ways |                       |
|                                            | to manipulate XML payloads.                                  |                       |
|                                            |                                                              |                       |
| webflux                                    | Build reactive web applications with Spring WebFlux and      |                       |
|                                            | Netty.                                                       |                       |
|                                            |                                                              |                       |
| websocket                                  | Build Servlet-based WebSocket applications with SockJS and   |                       |
|                                            | STOMP.                                                       |                       |
|                                            |                                                              |                       |
| zipkin                                     | Enable and expose span and trace IDs to Zipkin.              |                       |
+--------------------------------------------+--------------------------------------------------------------+-----------------------+

Project types
+-----------------------+--------------------------------------------------------------+--------------------------------------------+
| Id                    | Description                                                  | Tags                                       |
+-----------------------+--------------------------------------------------------------+--------------------------------------------+
| gradle-build          | Generate a Gradle build file.                                | build:gradle,format:build                  |
|                       |                                                              |                                            |
| gradle-project *      | Generate a Gradle based project archive using the Groovy     | build:gradle,dialect:groovy,format:project |
|                       | DSL.                                                         |                                            |
|                       |                                                              |                                            |
| gradle-project-kotlin | Generate a Gradle based project archive using the Kotlin     | build:gradle,dialect:kotlin,format:project |
|                       | DSL.                                                         |                                            |
|                       |                                                              |                                            |
| maven-build           | Generate a Maven pom.xml.                                    | build:maven,format:build                   |
|                       |                                                              |                                            |
| maven-project         | Generate a Maven based project archive.                      | build:maven,format:project                 |
+-----------------------+--------------------------------------------------------------+--------------------------------------------+

Parameters
+-------------+------------------------------------------+------------------------------+
| Id          | Description                              | Default value                |
+-------------+------------------------------------------+------------------------------+
| artifactId  | project coordinates (infer archive name) | demo                         |
| bootVersion | spring boot version                      | 3.5.3                        |
| description | project description                      | Demo project for Spring Boot |
| groupId     | project coordinates                      | com.example                  |
| javaVersion | language level                           | 17                           |
| language    | programming language                     | java                         |
| name        | project name (infer application name)    | demo                         |
| packageName | root package                             | com.example.demo             |
| packaging   | project packaging                        | jar                          |
| type        | project type                             | gradle-project               |
| version     | project version                          | 0.0.1-SNAPSHOT               |
+-------------+------------------------------------------+------------------------------+
```

### Getting started

Spring boot provides a number of starters to add the jars in our class paths, for example for writing a rest endpoint we
end to add the spring-boot-starter-web dependency in our class path. Observe the codes shown below for a better
understanding.

The main method should be writing the Spring Boot Application class. This class should be annotated with the
`SpringBootApplication` annotation, this is the entry point for the spring boot application to start, you can find the
main class file under the src/java/main path / directory

Now to write a simple Hello World Rest endpoint in the spring boot application main class file itself, follow the steps shown below:

- Firstly, add the `RestControoller` annotation at the top of the class
- Now, write a request URI method with the `RequestMethod` annotation
- Then the request URI method should return the Hello World string

```java
package com.tutorialspoint.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloWorldController {

    @RequestMapping(value="/")
    public String hello() {
        return "Hello World";
    }
}
```

### Packaging application

Creating an executable JAR. Let us create and executable JAR file to run the Spring Boot Application, by using Maven or
Gradle. The Spring executable JAR is somewhat different than regular JAR files which one can execute, the Spring `JARs`
are meant to be self containing, they bundle the actual web server that is used to run the application, which could be a
small web server like Tomcat, then on top of that all other dependencies are included into the jar. Unlike regular JARs
(which only contain your classes in `/`), a Spring Boot executable JAR has:

First make sure that the maven plugin that is responsible for packaging the spring application is included into your pom
file, we are not going to be using the regular maven package plugin - `maven-jar-plugin`

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>
```

This is what we might expect to see in our jar if we were to extract its contents out

```
my-app.jar
├── BOOT-INF/
│       ├── classes/      # Your compiled application classes
│       ├── lib/          # All dependency JARs
├── META-INF/
│       ├── MANIFEST.MF   # Defines the launcher class
└── org/springframework/boot/loader/
        ├── JarLauncher.class  # Spring Boot's custom launcher
```

- Spring Boot uses `JarLauncher` (from `spring-boot-loader`) to **bootstrap** the application.
- This launcher knows how to load classes from `BOOT-INF/classes` and `BOOT-INF/lib`, unlike the standard Java classloader.
- All dependencies are **embedded** in the JAR (no need for an external `lib` folder).
- Avoids `classpath` issues when deploying, everything is packaged together.

How does it work ? If we compare the standard jar packaging plugin provided by maven to the spring one. The Standard
maven plugin would package the JAR without including the dependencies or libraries that our project depends on, just the
compiled classes for our project, the structure of a standard jar might look something like that

```
my-app.jar
├── META-INF/
│   └── MANIFEST.MF       # Basic metadata (Main-Class points directly to your app)
├── com/
│   └── yourpackage/      # Your compiled classes
└── (No dependencies!)    # Requires external `lib/*.jar` files
```

Meaning we have no embedded dependencies included, and we have no embedded server included in the final JAR, that means
that these have to be provided manually, that works if we are trying to deploy on an already existing web server, which
presumable would have these dependencies installed. There are many ways to run a jar without its dependencies, but the
most common one is to simply add those to the classpath, meaning that we have to build the command `java -jar` to
include the required dependencies along with the jar we are trying to execute, this could be useful on systems where
many jars are deployed on a single web server, and the dependencies are shared across the jars for the most part. This
is how it is done in monolithic systems where micro services are not deployed.

```sh
# first we copy all the dependencies into a common directory, which would then be passed to the java cp command, note
# that we are using shell expansion here, the target/lib/*, is shell specific and the shell itself would expand all the
# jars in the lib folder when running the command, this is not something that is done or understood by the java binary,
mkdir -p target/lib && cp myapp.jar target/lig
mvn dependency:copy-dependencies -DoutputDirectory=target/lib
java -cp "target/your-app.jar:target/lib/*" com.myapp.MainClass
```

Note that we are passing the entire classpath to the JVM, above, we are not using `java -jar` that is because that
command expects the jar to have a valid MANIFEST file, which the jar compiled and packaged by default from the
maven-jar-plugin does not. We are going to leverage another plugin called the assembly plugin to do exactly that - build
a MANIFEST file into the jar which will be read by the JVM to construct the classpath based on the contents of that file

There are also other ways to do this by configuring the maven plugin to package these libraries for you while the jar is
being built, this is what the spring boot plugin does actually it would put a lib/ folder with the libraries and
dependencies directly into the jar. Which is effectively the same as passing it on the classpath. Here is an example of
how you can package the jar using the assembly plugin which would effectively do what we discussed

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-assembly-plugin</artifactId>
    <version>3.6.0</version>
    <configuration>
        <descriptorRefs>
            <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
        <archive>
            <!-- Here is the config for the MANIFEST file that will be constructed and put into the jar -->
            <manifest>
                <mainClass>com.yourpackage.MainClass</mainClass>
            </manifest>
        </archive>
    </configuration>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>single</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

```sh
# after the assembly plugin has done its job, we would have a jar file which can be simply run like so, which is pretty
# much the same way we run the spring jars as well, since your jar would now contain the MANIFEST file, which described
# how to construct the classpath and where to find all the libraries and dependencies required
$ java -jar app.jar
```

So how does the spring boot maven plugin package the jar and how does it run, the following steps are being followed to
execute this process:

1. **We start by running the jar**:

    ```sh
    $java -jar your-app.jar
    ```

2. **Java virtual machine reads `META-INF/MANIFEST.MF`**:

    ```plaintext
     Manifest-Version: 1.0
     Created-By: Maven JAR Plugin 3.4.2
     Build-Jdk-Spec: 17
     Implementation-Title: demo
     Implementation-Version: 0.0.1-SNAPSHOT
     Main-Class: org.springframework.boot.loader.launch.JarLauncher
     Start-Class: com.spring.demo.DemoApplication
     Spring-Boot-Version: 3.5.3
     Spring-Boot-Classes: BOOT-INF/classes/
     Spring-Boot-Lib: BOOT-INF/lib/
     Spring-Boot-Classpath-Index: BOOT-INF/classpath.idx
     Spring-Boot-Layers-Index: BOOT-INF/layers.idx
    ```

3. **`JarLauncher` takes over**:
    - Sets up a **custom classloader** to load classes from:
        - `BOOT-INF/classes/` (your code).
        - `BOOT-INF/lib/*.jar` (dependencies).
    - Discovers and launches the `Start-Class` (your `@SpringBootApplication` class).

4. **Spring Boot initializes**:
    - Starts the embedded server (if applicable).
    - Autoconfigures beans, initialize the context, etc.

So if we want to really compare both here is what we have established so far, the main differences between the
maven-jar-plugin and the spring-boot-maven-plugin.

| Feature           | `maven-jar-plugin`              | `spring-boot-maven-plugin`            |
| ----------------- | ------------------------------- | ------------------------------------- |
| **Dependencies**  | Not included (external `lib/`)  | Embedded in `BOOT-INF/lib/`           |
| **Main-Class**    | Directly points to your class   | Delegates to `JarLauncher`            |
| **Class Loading** | Standard JVM classloader        | Custom `LaunchedURLClassLoader`       |
| **Runnable**      | Requires manual classpath setup | Works with simple call to `java -jar` |
| **Web Server**    | Needs external Tomcat/Jetty     | Embedded server (Tomcat/Netty/etc.)   |

There is another type of JAR archive, called a WAR application, this is another type of JAR that is deployed on a web
server, unlike the JAR file, the WAR has a different format and specification this is because the web server where this
WAR will be deployed requires a specific format and structure to be present.

A **WAR** is a specialized JAR used for **web applications** (e.g., Servlet-based apps like Spring MVC). It follows a
strict structure defined by the `Java EE/Jakarta EE` spec and is deployed to **external servers** (e.g., Tomcat, Jetty,
WildFly).

```
my-app.war
├── WEB-INF/
│   ├── classes/       # Your compiled classes (e.g., `com.yourpackage`)
│   ├── lib/           # Dependency JARs (external to the WAR)
│   └── web.xml        # Servlet configuration (optional in modern apps)
├── META-INF/          # Metadata (e.g., context info)
└── static/            # Static files (HTML, JS, CSS)
```

Requires a Servlet container (e.g., Tomcat). The dependencies in `WEB-INF/lib/` are loaded by the server, and there is no main
class, the server starts the app (not `java -jar`).

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-war-plugin</artifactId>
    <version>3.4.0</version>
</plugin>
```

To setup and deploy the application you can simply do

```sh
$ mvn clean package  # Generates `target/my-app.war`
$ cp target/my-app.war /opt/tomcat/webapps/
## Tomcat unpacks the WAR and starts the app.
```

To be able to share common libraries, we can use the special directory that tomcat checks, where we can Place common
JARs (e.g., Spring, Hibernate, JDBC drivers) in the server’s `lib/` folder. All deployed WARs automatically inherit
these JARs in their classpath.

```plaintext
/opt/tomcat/lib/
├── spring-core.jar
├── hibernate-core.jar
└── postgresql-driver.jar
```

We can also make sure to exclude the dependency from the final archive, by setting the provided property on the scope
tag, this will signal the maven builder that this dependency must not be packaged in, and it will be `provided` by an
external party, in this case it will be the web server - tomcat in our example.

```xml
<project>
    <packaging>war</packaging>
    <dependencies>
        <!-- Mark shared dependencies as 'provided' -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>6.1.0</version>
            <!-- Server will supply this -->
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
```

WARs can **omit libraries** if the server provides them centrally (via `lib/` or `shared/lib/`). This is common in
traditional Java EE deployments but sacrifices version flexibility. Modern apps (especially Spring Boot) prefer
**self-contained JARs** to avoid conflicts, and it is much harder to setup a micro service environment where the web
server is separately deployed from the application, and the web server has the common dependencies already installed.
This is more suitable for monolithic applications where there are centralized servers installed on big fat environments,
and they can be pre-loaded with all the common dependencies marked as provided in the pom file of the application.

To understand it better, here are the different types of scopes that maven allows you to specify for a dependency, you can
certainly see that there are different use cases, and in most use cases, we are either going to be using runtime,
compiled or provided. For an embedded server, the most common one will be the compile one, since all the libraries must
be present at compile time into the JAR, for WAR types we might make use of a lot of provided dependencies such as Tomcat
shared implementation libraries that are going to be provided by the web server by default

| Scope      | Compile | Test | Runtime | Packaged | Example Use Case                |
| ---------- | ------- | ---- | ------- | -------- | ------------------------------- |
| `compile`  | ✅      | ✅   | ✅      | ✅       | Spring, Hibernate               |
| `provided` | ✅      | ✅   | ❌      | ❌       | Servlet API, Tomcat-shared libs |
| `runtime`  | ❌      | ✅   | ✅      | ✅       | JDBC drivers                    |
| `test`     | ❌      | ✅   | ❌      | ❌       | JUnit, Mockito                  |
| `system`   | ✅      | ✅   | ❌      | ❌       | Avoid (non-portable JARs)       |
| `import`   | ❌      | ❌   | ❌      | ❌       | BOMs for dependency management  |

1. **`compile` (Default)**
    - Available in all phases (compile, test, runtime).
    - Packaged in the final JAR/WAR.
    - **Example**: `spring-core` (needed at runtime).

2. **`runtime`**
    - Not needed for compilation, but required at runtime.
    - Packaged in the final artifact.
    - **Example**: `mysql-connector-java` (loaded dynamically).

3. **`provided`**
    - Available during compilation and testing, but **not packaged**.
    - Assumed to be provided by the runtime (e.g., a server).
    - **Example**: `javax.servlet-api` (provided by Tomcat).

4. **`system`** (Avoid if possible)
    - Like `provided`, but points to a local JAR file.
    - **Example**: Rarely used (non-portable).

5. **`import`** (Special case)
    - Used in `<dependencyManagement>` to inherit versions from another POM.
    - **Example**: `spring-boot-dependencies` (BOM files).

6. **`test`**
    - Only available during testing (`src/test`).
    - Never packaged.
    - **Example**: `junit` (test-only dependency).

### Build system

In Spring Boot, choosing a build system is an important task. We recommend Maven or Gradle as they provide a good
support for dependency management. Spring does not support well other build systems.

Spring Boot team provides a list of dependencies to support the Spring Boot version for its every release. You do not
need to provide a version for dependencies in the build configuration file. Spring Boot automatically configures the
dependencies version based on the release. Remember that when you upgrade the Spring Boot version, dependencies also
will upgrade automatically. This is achieved by the parent BOM spring dependency which you specify, the BOM - which
stands for bill of materials, is a set of pre-defined list of dependencies. The spring BOM has a version, which carries
with it the correct combination of all spring dependency versions.

### Best practices

Spring Boot does not have any code layout to work with. However, there are some best practices that will help us. This
chapter talks about them in detail.

#### Default packages

A class that does not have any package declaration is considered as a **default package**. Note that generally a default
package declaration is not recommended. Spring Boot will cause issues such as malfunctioning of Auto Configuration or
Component Scan, when you use default package.

#### Typical layout

It is generally accepted that at the root name of the artifact id for the project - `com.myapp.project`, we should have
the Application.java file, which contains the Main class, this is due to the fact that any class annotated with the
`@SpringBootApplication` annotation, will serve as entry point for scanning the packages and beans under it, in that case
if we put the main class at `com.myapp.project`, then everything under this root package name will be considered for
injection by the spring components.

#### Application context

First it is prudent to understand what is the app context in spring, that is what gets initialized the moment the spring
container starts, that is not a docker container, but a container that starts to load and create various different java
bean objects, that would constitute the spring environment for the duration of the app runtime

These beans are registered into the app context, that implies that they will be used later on for various purposes like
dependency injection, probably one of the most important features of the spring framework, which allows one to create
graph like connections between different components of ones system, to achieve a certain goal

Java beans - loosely are types of java classes that have very specific properties - they are all defining at least one
default non-argument constructor, they must all declared their properties or fields as private, each field can be
exposed to the public world with a getter or/and setter but that is not strictly required for each and every one, the
Java beans also provide means of Serializing, and also there is a special pattern that they may implement which the
listener/consumer which allows other java beans to register them selves as a listeners or observers and to listen for
property mutations on a given java bean and control the that.

And then the corner stone of the spring framework, the only thing it does best, is to provide easy means for one class
or a piece of code to express the need for another or in other words what we call - dependency injection. That is a deep
seated concept in the world of spring and the spring framework.

```java
@Bean
public BookRepository bookRepository(DataSource dataSource) {
    // the method will be invoked and injected with an instance of the data source bean, the very first argument here
    // tells that this method requires a valid instance of a dataSource which first needs to be present before method is
    // invoked, afterwards the method will produce a new managed instance a bean of type BookRepository, which will be
    // added to the spring app context
    return new BookRepository(dataSource);
}
```

This preceding Java configuration, when seen by the Spring Framework, will cause the following flow of actions:

1. bookRepository() needs a DataSource.
2. Ask the application context for a DataSource.
3. The application context either has it or will go create one and return it.
4. Method bookRepository() executes its code while referencing the app context’s DataSource.
5. Instance of BookRepository is registered in the application context under the name bookRepository.

`The main advantage of letting spring manage the instance creation, among other things, is the fact that we delegate the
creation of these instances to the spring framework, really we are delegating this to us, because something has to
create the instances of these dependent classes but we now have the choice of providing any number of possible options
to create these instances, and if we are smart we will try to depend on interfaces as much as possible allowing us to
re-inject/change/modify implementations sometimes even at runtime with minimal effort, thanks to the architecture of the
framework that gives us these flexible ways of doing this`

It is important to note that the dependency injection container in spring will ensure that the proper ordering is taken
into account when creating the beans, it will ensure that all dependent beans are created in order as required, to
create the final target bean, that is usually taken care for us by the framework. What are are required to do is provide
the actual instances, or implementations we know are going to be needed to construct. Some of the existing
autoconfiguration policies baked into Spring Boot extend across these areas:

```plaintexet
• Apache Kafka - Asynchronous messaging
• Data stores - Apache Cassandra, Elasticsearch, Hazelcast, InfluxDB, JPA, MongoDB, Neo4j, Solr, Infinispan, Redis, etc.
• Flyway - Database schema management
• Liquibase - Database schema management
• Quartz - Timed tasks and scheduling
• SendGrid - Publish emails
• Serialization/deserialization - (Gson and Jackson)
• Templating engines - (Freemarker, Groovy, Mustache, Thymeleaf)
• jOOQ - Query databases using Java Object Oriented Querying (jOOQ)
```

```plaintexet
• Spring AMQP - Communicate asynchronously using an Advanced Message Queueing Protocol (AMQP) message broker
• Spring AOP - Apply advice to code using Aspect-Oriented Programming
• Spring Batch - Process large volumes of content using batched jobs
• Spring Cache - Ease the load on services by caching results
• Spring Data (Apache Cassandra, Couchbase, Elasticsearch, JDBC, JPA, LDAP, MongoDB, Neo4j, R2DBC, Redis, REST) - Simplify data access
• Spring HATEOAS - Add Hypermedia as the Engine of Application State (HATEOAS) or hypermedia to web services
• Spring Integration - Support integration rules
• Spring JDBC - Simplify accessing databases through JDBC
• Spring JMS - Asynchronous through Java Messaging Service (JMS)
• Spring JMX - Manage services through Java Management Extension (JMX)
• Spring LDAP - Directory-based services over Lightweight Directory Access Protocol (jOOQ)
• Spring MVC - Spring’s workhorse for servlet-based web apps using the Model-View-Controller (MVC) paradigm
• Spring Mail - Publish emails
• Spring R2DBC - Access relational databases through Reactive Relational Database Connectivity (R2DBC)
• Spring RSocket - Support for the async wire protocol known as RSocket
• Spring Session - Web session management
• Spring Validation - Bean validation
• Spring Web Services - Simple Object Access Protocol (SOAP)-based services
• Spring WebFlux - Spring’s reactive solution for web apps
• Spring WebSocket - Support for the WebSocket messaging web protocol
```

#### Auto-configuration

As mentioned in spring we have a concept called an Auto configuration, this is a process by which spring picks specific
pre-defined beans on the class path. The way it works is by defining a special type of file that contains the full path
to the class that is considered to be the auto-configuration class, this file is called the
`org.springframework.boot.autoconfigure.AutoConfiguration.imports`, this file contains lines of text which define the
different classes spring autoconfigure should consider when starting the application, this is different compared to
classes annotated with `@Configuration`, which we will talk about later.

Here is the abridged version of the file included in the `META-INF/spring/` folder in the autoconfigure dependency, this
file is automatically picked when we define the `EnableAutoConfiguration` annotation on a spring project, then each of
those classes will be instantiated and their methods run.

```imports
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration
............................................................................................
org.springframework.boot.autoconfigure.websocket.reactive.WebSocketReactiveAutoConfiguration
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration
org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration
```

It is prudent to note that you can also create your own imports file, by simply putting in tin the `src/main/resources`
folder of your own library or project, then when it gets built it will be put into the META-INF folder in your final
JAR, that file will then be looked up by the spring autoconfigure library. Create the file into the resources directory
`src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`: Loaded
**automatically** when the JAR is on the classpath, and no `scanBasePackages` required.

```plaintext
com.yourlib.autoconfig.CustomSecurityAutoConfig
```

```java
package com.yourlib.autoconfig;

@Configuration
@ConditionalOnClass(SomeDependency.class)
public class CustomSecurityAutoConfig { ... }
```

`The lifecycle of the automatic configurations is a bit different, unlike their counterpart - beans annotated with the
@Configuration annotation, the auto-configurations are special component that is initialized by the spring DI container
framework/layout the moment it starts, this is very early on, unlike the @Configuration annotations which are discovered
later and created later, also the auto-configuration classes specified in the imports file do not require any type use
of the scanBasePackages to be used`

#### @Configuration

On another hand we have the `@Configuration` annotation, that is a bit different since it is discovered through component
scanning, which is a different process in spring, it also occurs in a different stage of the application startup
process, this is later than the auto configuration classes as well. They are loaded if these fall under the scan base
packages list, i.e. the class is in a package scanned by Spring (`com.yourpackage`), or explicitly imported with the
annotation - `@Import(AppConfig.class)`.

```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() { return new MyService(); }
}
```

`In the end the result of both is the same, both types of classes are defined using the same annotations, and the same
rules, the only difference is that the spring provided auto configurations are mostly located in the starter packages,
which spring provides, and are defined in the imports file, in theory if you remove the imports file, and put the spring
org.srpingboot base package in the scanBasePackages annotation value of ComponentScan, you will achieve the same effect,
the only difference is that the configurations defined in the imports file are run earlier than the component scanning
that spring does on the classes under packages defined in scanBasePackages, but that still happens after the spring
context is initialized`

The general rule of thumb, is that if you are creating a library or re-usable component it is easier and cleaner to
provide your own imports file, this would avoid having your package's users or clients to have to include the base
package of your library or component every time into the `ComponentScan.scanBasePackages annotation`.

#### Spring Boot Starters

As we have already stated spring provides a multitude of starter projects that can be used to bootstrap your project,
these usually contain mostly auto-configurations and glue logic between different primary dependencies and libraries.

Spring MVC versus Spring Web? Spring Framework has three artifacts involving web applications: Spring Web, Spring MVC,
and Spring WebFlux.

- Spring MVC is servlet-specific bits.
- Spring WebFlux is for reactive web app development and is not tied to any servlet-based contracts.
- Spring Web contains common elements shared between Spring MVC and Spring WebFlux.

This mostly includes the annotation-based programming model Spring MVC has had for years. This means that the day you
want to start writing reactive web apps, you don’t have to learn a whole new paradigm to build web controllers.

So if we wanted to build a standard web application, we would need the following, first we need the Spring web starter

• MVC and the associated annotations found in Spring Web. These are the Spring Framework bits that support servlet-based web apps.
• Jackson Databind for serialization and deserialization (including JSR 310 support) to and from JSON.
• An embedded Apache Tomcat servlet container.
• Core Spring Boot starter.
• Spring Boot.
• Spring Boot Autoconfiguration.
• Spring Boot Logging.
• Jakarta annotations.
• Spring Framework Core.
• SnakeYAML to handle YAML (YAML)-based property files.

`What is Jakarta? Jakarta EE is the new official specification, replacing Java EE. Oracle wouldn’t
relinquish its trademarked Java brand (nor grant a license) when it released its Java EE specs to
the Eclipse Foundation. So, the Java community chose Jakarta as the new brand going forward.
Jakarta EE 9+ is the official version that Spring Boot 3.0 supports. For more details, checkout
my video What is Jakarta EE? at https://springbootlearning.com/jakarta-ee`

### Application properties

Application Properties support us to work in different environments. In this section, you are going to see how to
configure and specify the properties to a Spring Boot application.

The general order of properties collection that Spring Boot does is by evaluating sources in this order (higher
overrides lower order):

1. **Command-line arguments** (`--key=value`, directly run the application with these properties).
2. **`SPRING_APPLICATION_JSON`** (environment variable with JSON properties, not a file location).
3. **`JNDI`** (Java Naming and Directory Interface).
4. **Externalized files** (`--spring.config.location`).
5. **Environment YAML/properties** (e.g., `application-prod.yml`).
6. **Default files `application.yml`/`application.properties`**.

Here an example of implementing the different types of property overrides when working with a spring application, also
defined in order of most precedence to the least precedence.

1. **Command-line arguments** (`--key=value` in `java -jar`).

    ```sh
    java -jar app.jar --server.port=8081
    ```

2. **Environment variables** (e.g., `SPRING_DATASOURCE_URL`, `SERVER_PORT`).

    ```sh
    export SERVER_PORT=8081
    ```

3. **System properties** (`-Dkey=value` passed to JVM).

    ```sh
    java -Dserver.port=8081 -jar app.jar
    ```

4. **Profile-specific YAML/properties** (e.g., `application-prod.yml`).

5. **Default `application.yml`/`application.properties`**.

#### Command line

Spring Boot application converts the command line properties into Spring Boot Environment properties. Command line
properties take precedence over the other property sources. By default, Spring Boot uses the 8080 port number to start
the Tomcat. Let us learn how change the port number by using command line properties. There are a few ways to override
certain properties on the command line, such that they will take precedence over the ones with which the app was packaged

```sh
## Values will override any `server.port` or `spring.datasource.url` defined in `application.yml`.
$ java -jar your-app.jar --server.port=8081 --spring.datasource.url=jdbc:mysql://localhost:3306/mydb

## Properties in the `custom-config.yml` will **override** those in `application.yml`.
## Add `classpath:` for files in the classpath (e.g., `classpath:override.properties`).
$ java -jar your-app.jar --spring.config.location=file:/path/to/custom-config.yml

## Merge with additional files without actually replacing the packaged `application.yml`:
$ java -jar your-app.jar --spring.config.additional-location=file:/path/to/extra.properties
```

#### Environment

There are several ways to override the properties through the environment,

##### Core Spring Boot Variables

| Env Variable                        | Effect                                                          |
| ----------------------------------- | --------------------------------------------------------------- |
| `SPRING_APPLICATION_JSON`           | Injects properties as JSON (e.g., `{"server":{"port":8081}}`).  |
| `SPRING_CONFIG_LOCATION`            | Overrides config file path (e.g., `file:/config/override.yml`). |
| `SPRING_CONFIG_ADDITIONAL_LOCATION` | Adds extra config files without replacing defaults.             |
| `SPRING_PROFILES_ACTIVE`            | Sets active profiles (e.g., `prod,debug`).                      |
| `SPRING_CLOUD_CONFIG_ENABLED`       | Disables/configures Spring Cloud Config (e.g., `false`).        |

##### Logging and Debugging

| Env Variable                            | Effect                                         |
| --------------------------------------- | ---------------------------------------------- |
| `DEBUG`                                 | Enables debug logs if set to `true` or `1`.    |
| `LOGGING_LEVEL_ROOT`                    | Sets root log level (e.g., `DEBUG`, `INFO`).   |
| `LOGGING_LEVEL_org.springframework.web` | Sets package-specific logging (e.g., `DEBUG`). |
| `SPRING_OUTPUT_ANSI_ENABLED`            | Force ANSI color output (e.g., `ALWAYS`).      |

##### Server and Database Overrides

| Env Variable                  | Effect                                               |
| ----------------------------- | ---------------------------------------------------- |
| `SERVER_PORT`                 | Overrides `server.port` (e.g., `8081`).              |
| `SERVER_SERVLET_CONTEXT_PATH` | Sets the context path (e.g., `/api`).                |
| `SPRING_DATASOURCE_URL`       | Overrides DB URL (e.g., `jdbc:mysql://db:3306/app`). |
| `SPRING_DATASOURCE_USERNAME`  | Sets DB username.                                    |
| `SPRING_DATASOURCE_PASSWORD`  | Sets DB password.                                    |

Replace `.` with `_` and uppercase (e.g., `server.port` → `SERVER_PORT`). Nested keys: `spring.datasource.url` →
`SPRING_DATASOURCE_URL`.

##### Overriding with `JAVA_TOOL_OPTIONS`

If you can’t use Spring-specific vars, pass the Java system properties, which will be picked up by spring, since it does
a lookup in order of environment variables, system variables, and then proceeds with startup arguments

```sh
export JAVA_TOOL_OPTIONS="-Dserver.port=8081 -Ddebug=true"
```

#### Properties file

Properties files are used to keep 'N' number of properties in a single file to run the application in a different
environment. In Spring Boot, properties are kept in the **application.[properties/yml]** files under the classpath. The
application.[properties/yml] file is located in the **src/main/resources** directory. The code for sample
**application.properties** and the **application.yml** file are given below:

```properties
server.port=9090
spring.application.name=demoservice
```

```yaml
spring:
    application:
        name: demo
server:
    port: 8080
```

#### @Value

The @Value annotation is used to read the environment or application property value in Java code. The syntax to read the
property value is shown below:

```java
@Value("${spring.application.name}")
private String applicationName;
```

#### @ConfigurationProperties

The `ConfigurationProperties` annotation is used to group and combine a set of configuration properties that have
common root, what we usually do is to define a common root path and then define and declare the structure of these
root properties in code, effectively binding the class annotated with this annotation to a specific set o properties
that can be found in the application.yml file or had been provided from outside when the app was started like so

```java
@ConfigurationProperties(prefix = "spring.application", ignoreInvalidFields = false, ignoreUnknownFields = true)
public class DefaultConfigurationProperties {

    private final String name;

    @ConstructorBinding
    public DefaultConfigurationProperties(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

So as you can see the annotation actually has verification and validation properties, that is because by default it
really enforces the structure of the prefix to match exactly what the class declares. In our case we use the
`spring.application` prefix, that means that it will inject properties under this property root, and we use just the
name property under this tree of properties, which we know is of type String

So here is a neat detail about how `ConfigurationProperties` works, this annotation does not create actual
dependency injection managed bean in the spring context, what it does is simply say - hey this class can be injected
with properties from the environment, and use the fields to do that. There is also the usage of the
`ConstructorBinding` which tells spring that it should prefer the constructors declared for this bean to create the
instance

Now in order to make spring create instances we have to use either one of these

- `@EnableConfigurationProperties(MyConfig.class)` - this will enable the properties, but still unless we specify
  the values annotation (like above) to tell spring which classes are eligible for for construction that will not work
  them they wont be created in the context.
- `@ConfigurationPropertiesScan(basePackages = "com.spring.demo")` this will tell spring where it can find classes
  with the `ConfigurationProperties` annotations.

The `@EnableConfigurationProperties` or the `@ConfigurationPropertiesScan` is usually put onto a `@Configuration`
annotated class, say a class named - `ConfigurationPropertiesEnabler` that is part of our

#### @ConditionalOnProperty

Besides the usual usage of properties in the spring context, we can also decide if a bean has to be created based on
the presence or absence of a property or even if the property matches some condition such as a value etc.

```java
@Bean
@ConditionalOnProperty(prefix="my.app", name="video")
public YouTubeService youTubeService() {
    return new YouTubeService();
}
```

The annotation above will ensure that a bean is only created when the condition, in this case just the presence of a
value for the `spring.applicatoin.name` is present, note how spring works by composing annotations, just like with
`@ConfigurationProperties` annotation which by itself does not create a bean instance, where we could have combined
it with a Bean annotation, to achieve the same.

```java
@Bean
@ConditionalOnProperty(prefix="my.app", name="video",
    havingValue="youtube")
public YouTubeService youTubeService() {
        return new YouTubeService();
}
@Bean
@ConditionalOnProperty(prefix="my.app", name="video",
    havingValue="vimeo")
public VimeoService vimeoService() {
        return new VimeoService();
}
```

A more advanced example which not only uses the value's presence but also creates conditional beans based on the
specific value this property has, and note that no bean will be created by default if the value for example was
present but did not match any of these

### Spring Profiles

Spring boot supports different properties based on the Spring active profile. For example we can keep two separate files
for development and production to run the Spring Boot application, or we can also use multi-document setup in the same
file, in a regular yml file it is possible to separate different documents using the `---` atom, which is the
equivalent of having different / separate files.

```yaml
---
# this is the base document, or the default one which will be picked up by spring, anything defined here can be
# overridden with a specific active profile configuration, below
spring:
    application:
        name: demo-base

# make sure that the logging is active for all profiles by default, we are using the INFO level, which means that we
# will be logging everything above and including INFO level, that implies - INFO, WARN, ERROR.
logging:
    level:
        com.spring.demo.core: INFO
---
spring:
    application:
        name: demo-local
    config:
        activate:
            # this tells spring that this document will be only active if the current profile that is active is called `local`, in
            # that case this file / document will be merged with the base config, the value of on-profile is array
            on-profile:
                - local
---
```

The other traditional option that spring provides is separate files, each file has to include the name of the active
profile in its name - `application, application-local, application-dev`, where the active properties will be, respectively
in order - the profiles `default , local and dev`. The default profile is always active.

While running the JAR file, we need to specify the spring active profile based on each properties file. By default,
Spring Boot application uses the application.properties file. The command to set the spring active profile is shown
below:

```sh
$ java -jar <demo-application>.jar --spring.profiles.active=dev
```

```plaintext
2017-11-26 08:13:16.322 INFO 14028 --- [ main]
com.tutorialspoint.demo.DemoApplication : The following profiles are active: dev
```

### Spring Logging

Spring Boot uses `Apache Commons logging` for all internal logging. Spring Boot's default configurations provides a
support for the use of Java Util Logging, Log4j2, and Logback. Using these, we can configure the console logging as well
as file logging.

If you are using Spring Boot Starters, Logback will provide a good support for logging. Besides, Logback also provides a
use of good support for Common Logging, Util Logging, Log4J, and SLF4J.

The default log messages will print to the console window. By default, "INFO", "ERROR" and "WARN" log messages will
print in the log file.

This is what the starter maven dependency for the spring logging pulls into your project, as you can see it depends on
`Apache logging` and the `logback` implementation.

```plaintext
[INFO] +- org.springframework.boot:spring-boot-starter-logging:jar:3.5.3:compile
[INFO] |  +- ch.qos.logback:logback-classic:jar:1.5.18:compile
[INFO] |  |  +- ch.qos.logback:logback-core:jar:1.5.18:compile
[INFO] |  |  \- org.slf4j:slf4j-api:jar:2.0.17:compile
[INFO] |  +- org.apache.logging.log4j:log4j-to-slf4j:jar:2.24.3:compile
[INFO] |  |  \- org.apache.logging.log4j:log4j-api:jar:2.24.3:compile
[INFO] |  \- org.slf4j:jul-to-slf4j:jar:2.0.17:compile
```

#### SLF4J (Simple Logging Facade for Java)

- **Role**: Abstraction layer (facade) over concrete logging implementations.
- **Purpose**: Decouples your code from specific logging frameworks.
    - Provides a unified API (e.g., `LoggerFactory.getLogger()`).
    - Allows switching implementations (Logback, Log4j2, etc.) without code changes.

#### Logback

- **Default in Spring Boot**.
    - Native SLF4J implementation (no adapter needed).
    - Fast, flexible configuration via `logback.xml`.

#### Log4j2

- **Successor to Log4j 1.x**.
- **Strengths**:
    - High performance (asynchronous logging).
    - Advanced features (JSON/YAML config, plugins).

### java.util.logging (JUL)

- **Built into the JVM**.
    - Limited features, poor performance.
    - Rarely used directly (SLF4J bridges exist).

```java
import org.slf4j.Logger;
Logger logger = LoggerFactory.getLogger(MyClass.class); // SLF4J API
logger.info("Hello"); // Delegates to the implementation Logback/Log4j2
```

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-logging</artifactId> <!-- Logback -->
</dependency>
```

To use the `log4j2` Replace the starter dependency with `spring-boot-starter-log4j2`. That would replace the
implementation that is used in your project with log4j however, you make sure to remove the old default implementation
of spring - `logback` otherwise it will likely conflict when bean configurations are created

By default, all logs will print on the console window and not in the files. If you want to print the logs in a file, you
need to set the property **logging.file** or **logging.path** in the application.properties file.

You can specify the log file path using the property shown below. Note that the log file name is spring.log.

```yaml
# we have to provide the name of the log file, and path, by default these values are not set, thus no file logging is
# performed but it is a good practice in production to make sure both are explicitly provided, and have a persistent log location,
# note that this is using a relative path, the file will be created in the current working directory where the jar was run, the
# reason being is that depending on your system, paths like /tmp or /var might not be accessible to the process running the jar,
# and the log file will never be created, so make sure that your process has the permissions to read/write at the file.path location
logging.file.path: ./tmp/
logging.file.name: spring.log
```

To modify the log levels, for all packages we can use the `root` prefix, this will enable logging levels for every
package in the classpath, however it is possible to provide a more granular logging based on a specific package or even
an entire sub-package.

```yaml
logging:
    level:
        root: ERROR
        com.my.package: TRACE
        org.hibernate.SQL: DEBUG
        org.hibernate.type: TRACE
        org.springframework.web: DEBUG
```

### Dependency versioning

How does spring version its dependencies ? Most of the magic happens in the Spring Parent BOM (bill of materials)
along side the spring boot stater projects which package all of the dependencies for a given starter to work out of
the box and actually be useful.

Think of Spring itself as a bunch of separate projects that can be used together, there are two main lines:

- The Spring Framework (core container, MVC, etc.) has its own version line, usually currently at major version 4.0.0,
  5.0.0, 6.0.0, these change and update mostly independently of the spring boot's platform version
- Spring Boot is the “platform release” that picks a compatible versions for Spring Framework and lots of other Spring
  projects and third-party libs, and then wires them together in a convenient way. Spring Boot 4.0.0, for example, is
  explicitly “building on top of Spring Framework 7”. Spring 3.0.0 is built on top of Spring framework version 6.0.0 and
  so on. Spring Boot works with Spring as its major dependency and other smaller third-party ones needed by the starters
  and Spring itself

First Spring Framework version is not the same as Spring Boot version. They’re related, but not the same thing. Spring
Boot docs put it plainly - each Spring Boot release is associated with a base Spring Framework version (4,5,6 etc), and
they recommend you don’t specify the Spring Framework version yourself. So you normally pick a Spring Boot version (2,
3), and that implicitly picks a Spring Framework version and plus a lot more.

Spring Boot’s real “secret sauce” is dependency management. Spring Boot maintains a curated list of dependency versions
that includes the major Spring Framework version and third-party dependencies. That curated list is shipped as a BOM
called spring-boot-dependencies.

What is a BOM, in plain terms? A BOM (Bill of Materials) is basically: “Here is the approved version number for artifact
X, Y, Z…”. In Maven, BOM info lives in \<dependencyManagement\>.

How “starters” tie in. A starter (like spring-boot-starter-web) is mostly a shopping list - it pulls in the common
dependencies you need for a use-case (web, data, security so you don’t have to remember them - BUT the starters do NOT
specify versions just the artifacts, the versions stay in the spring boot parent BOM only. Spring Boot docs describe
starters as “convenient dependency descriptors” that bring a consistent, supported set of transitive dependencies. Just
to emphasise again the starters usually do not “pin” versions themselves. They rely on the BOM / dependency management
to decide versions.

So what is “the parent BOM” / “parent POM”? This is where people get mixed up. spring-boot-dependencies (BOM) This
is the actual BOM: a big list of managed versions. That comes from the `spring-boot-starter-parent` (parent POM).
This is a Maven parent POM you inherit from. It gives you Maven defaults and dependency management so you can omit
\<version\> tags for the “blessed” dependencies. So when someone says “parent BOM”, they often mean - “the Spring Boot
parent POM” (spring-boot-starter-parent) which brings in dependency management (the BOM behavior) for you.

What’s actually happening when you create a new spring project

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.0.1</version>
</parent>
```

That parent provides dependency management so you can just add the starter

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

`When Common Vulnerabilities and Exposures (CVE) security vulnerabilities are reported to the Spring team, no matter
which component of the Spring portfolio (starter) is impacted, the Spring Boot team will make a security-based patch
release. This BOM is released alongside Spring Boot’s actual code. All we have to do is adjust the version of Spring
Boot parent (spring-boot-starter-parent) in our build file then the latest starter will be used, and everything will
update and follow pulling the necessary updates from the versions defined int the BOM.`

## Templates

One of the primary use-cases of web applications is to provide some sort of content to the end-user, we will first start
our journey with templates, or in other words, generate some html content. These templates are a domain specific driven
language and it allows us to generate server delivered content in the format of html, but other types of content can
also be used too.

Define a dependency for the templating engine, this will just ensure that we can use a special template language
mixed in with html,css and javascript to generate a dynamic content that the server will deliver to the client.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mustache</artifactId>
</dependency>
```

We need to define a bean annotated with Controller, note that is a regular MVC controller, and not a `RestController`, we
will investigate the difference down later below. By default the Controller annotated beans in Spring are expected to
return the name of a view - in this case that would be "index". That name will be used to discover the static or dynamic
view that needs to be delivered to the client. That is different to what the RestController annotated beans return by
default, which is plain data/content that is delivered as is to the user.

### Content Controller

```java
@Controller
public class TemplateControllerController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
```

Finally create the index file under `src/main/resources/index.moustache`, that is going to ensure that the correct
view is picked and that this file is going to be considered by the moustache templating engine and evaluated before
it is delivered to the client. The file can contain any valid html

```html
<h1>Greetings Learning Spring Boot 3.0 fans!</h1>
```

You can experiment by renaming the file to simply index.html which will deliver the file but no dynamic templating
will be done

We can expand the controller by defining a model for our MVC structure, that would help us generate a dynamic
template,

```java
import org.springframework.ui.Model;

@Controller
public class TemplateController {

    record Video(String name) {
    }

    private List<Video> videos = new ArrayList<>();

    {
        videos.add(new Video("one"));
        videos.add(new Video("two"));
        videos.add(new Video("three"));
        videos.add(new Video("four"));
    }

    @GetMapping("/")
    public String index(Model model) {
        // add the videos as an attribute to the model, note that we are adding the entire collection, Mustache
        // has a way of determining if a certain structure is Iterable and allows us to iterate over the elements
        // it contains instead of trying to parse or display it as a normal non-iterable element
        model.addAttribute("videos", videos);
        return "index";
    }

    @PostMapping("/new-video")
    public String add(@ModelAttribute Video video) {
        // add the video back to the list of videos, that is persistent until our application is still running,
        // we are going to change this soon.
        videos.add(video);
        // redirect here tells the browser to soft re-direct 302, that is not the same as redirect 301, which is
        // permanent redirect, it is not bad to know the difference.
        return "redirect:/";
    }
}
```

Modify the index file to contain the following, this template expands the videos attribute that was added in our
controller, that attribute can then be directly referenced and expanded in the template

```html
<h1>Greetings Learning Spring Boot 3.0 fans!</h1>
<ul>
    <b>{{#videos}}</b>
    <li>{{name}}</li>
    <b>{{/videos}}</b>
</ul>
<form action="/new-video" method="post">
    <input type="text" name="name" />     
    <button type="submit">Submit</button>
</form>
```

One thing to note here is the way we have defined the `form html` tag for this change, you can see here that the
post action is directly linked to the `new-video` action which will be intercepted by the spring dispatcher servlet,
and that will be then resolved and called.

What is important is the way it is going to be resolved, the add method will be called with a model attribute, similarly
to how RESTful controllers work, we can intercept the body of the form by using either `@ModelAttribute` or the
`@RequestParam`, either one will attempt to read the from the request's parameters - query parameters, form attributes
and fields, do not confuse this with `@RequestBody`, that annotation is meant to be used to intercept the body of the
request but the form body is encoded part of the body of an HTTP payload, however it is treated differently by the
Servlet and the web server.

We have an expanded and a more complete example below that demonstrates how we can leverage the different actions
like update an delete for a video for our video store. The controller is also using a new video service that
provides the actual business logic to manipulate the video store, the implementation we will take a look later but
for now what is important to note are the few nuanced methods here.

`First note that since we use simple forms to send data back to the server, we can only use two methods - POST and
GET, HTML forms do not support any other verbs besides these two basic ones as of 2025`

```java
@Controller
public class TemplateController {

    private final VideosService videosService;

    public TemplateController(VideosService videosService) {
        this.videosService = videosService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("videos", videosService.getVideos());
        return "index";
    }

    // to create a new video we simply need a new name for the video that is the primary data that the user sees
    // when the video is displayed along side a unique id which is automatically generated and incremented on each
    // new video that is being created
    @PostMapping("/new-video")
    public String add(@RequestParam("name") String newVideoName) {
        videosService.addVideo(newVideoName);
        return "redirect:/";
    }

    // just as the delete-method we leverage the path variable to pull the id of the video, which we have bound
    // in the template, for each video from the list we have created an inner-inlined form each of which has a
    // unique delete-video/video-id URL that has been called. Further more note that we also pull the argument
    // from the FORM data, in this case the name which comes from the input box, that is where the new name for the
    // video will be located,
    @PostMapping("/update-video/{id}")
    public String update(@PathVariable("id") Integer target, @RequestParam("name") String newVideoName) {
        videosService.updateVideo(target, newVideoName);
        return "redirect:/";
    }

    // we are leveraging the spring framework's capabilities to be able to pull the actual argument from the URL,
    // here we create a template of the URL and in the method we are able to inject that name argument from the URL
    // as a path variable, more on these in the next chapter for RESTful
    @PostMapping("/delete-video/{id}")
    public String delete(@PathVariable("id") Integer target) {
        videosService.deleteVideo(target);
        return "redirect:/";
    }
}
```

The changes to the template introduce two new actions that are added to the video list, next to each video we have
two new actions to either delete it or update it, both call the template controller methods for their respective
actions.

```html
<h2>List of videos</h2>
<ul>
        <b>{{#videos}}</b>
    <li>
        <b>{{ getId }}: {{ getName }}</b>
        <form action="/update-video/{{ getId }}" method="post" style="display: inline">
            <input type="text" value="{{ getName }}" placeholder="Update video name" name="name" />     
            <button type="submit">Update</button>
        </form>
        <form action="/delete-video/{{ getId }}" method="post" style="display: inline">
            <button type="submit">Delete</button>
        </form>
    </li>
    <b>{{/videos}}</b>
</ul>

<h2>Videos actions</h2>
<form action="/new-video" method="post">
        <input type="text" value="" placeholder="New video name" name="name" />     
    <button type="submit">Submit</button>
</form>
```

With these changes, the example concludes - represents the full expanded implementation that takes care of the
most basic CRUD operations and demonstrates how we can communicate between the server rendered content in plain html
here, and the server itself.

## RESTful

By now the world has converged on a handful of formats one of those formats is JSON, by default when we introduced the
web starter spring portfolio project into our dependencies it has already pulled something called Jackson, that is the
de-facto standard de-serializer for the web these days, and more specifically for JSON structures it allows one to
serialize and de-serialize JSON data from and into java objects and java beans

Before you proceed to build a RESTful web service, it is suggested that you have knowledge of the following annotations:
The `@RestController` annotation is used to define the RESTful web services. It serves JSON, XML and custom response. Its
syntax is shown below:

The `@RestController` annotation has one key difference when compared to the `@Controller` annotation unlike the
@Controller annotation which expects that all methods return a name for a view, the `@RestController` treats the return
data as a JSON payload, converting everything we return into json. There are other caveats, but both annotations are
eligible for bean injection, and bean creation, they are both discovered by the spring container and spring creates and
manages instances of these controllers internally, handling the template and RESTful based controllers itself. It is
advised that these two annotation are not mixed on the same java bean controller because the Spring container
environment might become confused.

```java
@RestController
public class ProductServiceController {
    // controller class implementation goes here, along with methods that accept and return JSON based content
}
```

### Request Verbs

Before we proceed with investigating the actual constructs that allow us to create restful requests and payloads, we
need to understand what are the common practices of using that data. First let us take a look at some of the HTTP verbs

- `GET` - this type of action is not meant to be mutating data but simply as the name implies retrieve data based on some criteria
- `POST` - this type of action is usually meant to only create new piece of data and/or is expected to accept such data from the client
- `PUT` - is used to update existing data and it is not usual to accept partial data to update existing records in the system
- `DELETE` - is used to only delete existing data from the system, and nothing else

There are other verbs that might be used in specific scenarios but for now we will keep our focus on these particular
ones, you might have noticed that in the `TemplateController`, above we actually used POST mapping to accept the new
form-data which is what is expected, when the form action button is clicked a POST method is actually send to the server
with the data the client/user has entered into the form. Similarly in our `RestController` examples we will use POST to
create data, the only meaningful difference is that the body of the request will no longer be form data but rather JSON
data, the contents and meaning however do not change - just the communication language.

### Request Mapping

The `@RequestMapping` annotation is used to define the Request URI to access the REST Endpoints. We can define Request
method to consume and produce object. The default request method is GET. However the annotation itself has a field that
can be used to specify the type - GET, POST, DELETE etc. All major METHOD types are supported out of the box. There are
dedicated annotations which are called `PostMapping`, `GetMapping`, `DeleteMapping` and so forth, which are preferred over the
plain direct usage of the `RequestMapping` annotation

```java
@RequestMapping(value="/products")
public ResponseEntity<Object> getProducts() { }
```

### Request Body

The `@RequestBody` annotation is used to define the request body content type. This is relevant when the request method
in question is of type that is different than GET, in those cases it is possible to send payload to the Servlet. The
payload in this case will be automatically de-serialized into the Product POJO, however if there are types mismatch
present between the types of the fields or properties of the Product POJO then an error would be thrown.

```java
@RequestMapping(method=RequestMethod.POST, value="/products")
public ResponseEntity<Object> createProduct(@RequestBody Product product) {
}
```

This example shows how we can read in an ambiguous set of values and fields from the request instead of having a
dedicated java class that defines the fields, this is possible due to the usage of the Object in the map which is
polymorphic at runtime. The de-serialized values in the Map would Object instances, but actually be de-serialized values
of class primitives such as Integer, String, Byte, Boolean etc.

```java
@RequestMapping(method=RequestMethod.POST, value="/products2")
public ResponseEntity<Object> createProductMap(@RequestBody Map<String, Object> product) {
}
```

### Request Parameter

The `@RequestParam` annotation is used to read the request parameters from the Request URL. By default, it is a required
parameter. We can also set default value for request parameters as shown here. The Request parameter can either come
as data from a form, or from the URL itself (also called query parameter).

```java
// one would be able to call this GET endpoint such as follows schema://hostname/products?name=product-name, the query
// parameter is optional meaning that a call will match without it being present, and a value will be defined by default if
// there is no value provided
@RequestMapping(method=RequestMethod.GET, value="/products")
public ResponseEntity<Object> getProduct(@RequestParam(value="name", required=false, defaultValue="honey") String name) {
}
```

### Request Header

While a request is being built by the client it is possible to construct a custom header that can be passed in the
header section of the HTTP payload, this header has a name and a value, in our case the name is `x-product-name` note
that the header names are **`not case sensitive`**, the values in there are usually primitive strings, numbers or
boolean types.

```java
@RequestMapping(method=RequestMethod.GET, value="/products")
public ResponseEntity<Object> getProduct(@RequestHeader("X-Product-Name") String name) {
}
```

### Request Attribute

The request attribute, is an internal construct that is attached to the request by the Servlet processor, as early as
the request being captured by the web server, usually in the real world a special filter or interceptor might add some
request attribute to the request before it reaches the method call in the restful controller, this can then be used to
do some further processing, this usually can happen based on some business logic, or in the example below, based on some
other properties found in the http request sent by the client.

```java
// based on some business logic a special attribute might be added to the request before it reaches the controller method
// in our example we `imagine` that a special discount value will be added based on some heuristic calculated from the
// client request payload.
@RequestMapping(method=RequestMethod.GET, value="/products")
public ResponseEntity<AuditLog> getProducts(@RequestAttribute("discount") Double discount) {
}
```

### Path Variable

The `@PathVariable` annotation is used to define the custom or dynamic request URI. The Path variable in request URI is
defined as curly braces {} as shown below. This would inject the value of the templated entry in the path string in this
case called {id} into the method argument, when the request is received, which would allow you to dynamically read a
request value from the path. Take a good note of the name of the argument in the annotation - `id`, this has to match
the value in the brackets. It is also possible to skip the value in the `@PathVariable` annotation, and the name would
be inferred from the name of the argument of the function, this however is only possible if the jar is compiled with the
-parameter option passed to the compiler, that would tell the compiler to retain the name of the arguments at runtime,
making them accessible to the annotation processor that injects the value into the arguments of the function, by reading
their names

```java
// so we can call this endpoint with different paths and id arguments such as /products/1, /products/2 and so on, that
// simplifies development as we are not required to have different endpoints for every possible variation of the id
// that a product can have, and we are not forced to use query parameters to have an id provided by the user which
// would be the natural alternative to this problem
@RequestMapping(method=RequestMethod.GET, value="/products/{id}")
public ResponseEntity<Object> updateProduct(@PathVariable("id") String id) {
}
```

It is important to note that unlike the other HTTP request 'parameters' that we can pull from a request, The path
variable is something that the spring framework offers the actual underlying Servlet or the web server have no
knowledge of this argument as it is not really part of the HTTP spec, like query parameters or the body of a request
is

### Frontend client

So having introduced the different annotations and methods of extracting data from a request above we can actually
also complete the description by introducing the actual front end, the client that will invoke those end points and
how they are going to be invoked, how is the data actually going to be passed to them.

```http
### 1. POST with @RequestBody
POST http://localhost:8080/products-body
Content-Type: application/json

{
    "id": "laptop",
    "name": "Laptop",
}

### 2. GET with @RequestHeader
GET http://localhost:8080/products-header
name: PremiumCustomer

### 3. GET with @RequestParam
GET http://localhost:8080/products-query?name=Smartphone

### 4. GET with @PathVariable
GET http://localhost:8080/products-path/12345

### 5. GET with @RequestAttribute (requires server-side setup)
GET http://localhost:8080/products-discount
```

### Rest controller

Below we have adapted the original TemplateController to to a new implementation that matches what a RESTful based
controller standard implementation might look like.

```java
@RestController
@RequestMapping("/api")
public class RestfulController {

    private final VideosService videosService;

    public RestfulController(VideosService videosService) {
        this.videosService = videosService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getMethodName() {
        // the get is the simplest one it just returns all videos without any input arguments and parameters
        return ResponseEntity.of(Optional.of(videosService.getVideos()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> getProductsQuery(@PathVariable Integer id) {
        // the delete mapping requires the path to be specific we have to call like such - /delete/1, otherwise if
        // we call just /delete the DispatcherServlet will never find a matching path and throw 404
        return ResponseEntity.of(Optional.of(videosService.deleteVideo(id)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> getProductsHeader(@PathVariable Integer id, @RequestBody Video video) {
        // a more complex example that involves multiple arguments in this case an id provided in the path just
        // as the delete above, we have to call specific /update/1, on top of that the request body has to contain
        // a name, and must be of json
        return ResponseEntity.of(Optional.ofNullable(videosService.updateVideo(id, video.getName())));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> getProductsQuery(@RequestBody Video video) {
        // the create is rather simple it is again a method that requires a body of json payload to be provided
        // and it has to contain a name in there.
        return ResponseEntity.of(Optional.ofNullable(videosService.addVideo(video.getName())));
    }
}
```

So here is what the front end client might do with this controller, below we have a few examples of calling these
methods for the controller expressed above, note that this is by no means an exhaustive list of the different
combinations and method/verbs that can be used with a RESTful based controller, it is just the most basic core
functionality that one might need for a quick start to get a grasp of what can be achieved.

It is safe to say that the entirety of the HTTP request and response are available for access to the developer from
Spring and there is no need to go to a lower level and operate with raw `Servlets` to achieve some complex flow

```hurl
@base_url = http://localhost:8080
@json = application/json

GET {{base_url}}/
HTTP 200

POST {{base_url}}/create
Content-Type: {{json}}

{
  "name": "Created from Hurl"
}

HTTP 200

PUT {{base_url}}/update/1
Content-Type: {{json}}

{
  "name": "Updated from Hurl"
}

HTTP 200

DELETE {{base_url}}/delete/1
HTTP 200

GET {{base_url}}/
HTTP 200
```

## Servlet

Now let us deep dive into what is going on here, how does this work, how does all of this relate to the MVC Spring
framework and the underlying web server, how do they tie up together. And how do our controllers - be it a restful one
or a view one actually gets called and then result is delivered back to the user. First lets us introduce some key
concepts that we are going to work with here:

- `Web server` - That is the actual application that runs our application, which is deployed on the web server, our
  application will be unable to work or run without it (Tomcat, Jetty, Undertow etc are all examples of web servers)
- `Web application` - Is our application that we run on the web server this is what we have developed this is what we
  expect to actually work with.
- `Web Contract` - That is the glue/contract, the jakarta.servlet that contains most of the interfaces-specification
  that most web servers working with java applications need to implement, or work with, it contains core interfaces that
  our application will implement, the job of the web server is to instantiate our implementations and plug them in the
  internal workings of the web server pipeline

How does that work though, so first when we configure our application we do it such that the server provides its own
implementation of the `jakarta.servlet` spec, this is done by providing the `provided` scope for the `jakarta` spec,
that will ensure that the server itself will provide the implementation once its loads the application. What it does
actually is just start our application and puts its own implementation on the classpath that way the JVM will discover
it.

```sh
# this is a very crude example just showing what exactly is going on here with the provided scope, it just says, we let
# whoever starts the application provide the library on the class path, while our app is built and compiled with that
# library, it is not included in the final package when the provided scope is used.
$ java -jar <demo-application>.jar --classpath=/tomcat/jakarta/implementation.jar
```

Here is how a typical pom.xml maven build file might look like, as you can see we use the scope provided here, which
will ensure that the final jar does not contain this dependency under /lib. It is only used during test/compile phases

```xml
<dependencies>
    <!-- For Tomcat 10+ -->
    <dependency>
        <groupId>jakarta.servlet</groupId>
        <artifactId>jakarta.servlet-api</artifactId>
        <version>6.0.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

Next we have to provide the configuration - `src/main/webapp/WEB-INF/web.xml`. This file will be read by the server, it
is the entry point, the glue between our application and the web server, it tells the web server how to bootstrap our
application. Here is an example.

Note that this is only needed if we are not using an embedded strategy, to run our application, otherwise the approach
is a bit different and we would be otherwise in charge of starting the tomcat instance and registering our beans with it
manually

Here is a very abridged version of what the web.xml file will look like for Spring boot itself, as you can see we have
basically 2 main entry points, the actual `ContextLoaderListener`, which stats the app and builds the `ApplicationContext`,
and the main front Servlet implementation entry point `DispatcherServlet`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
     http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
    version="4.0">

    <!-- Builds the "root" Spring ApplicationContext for the whole webapp
       (services/repos/datasources, shared infrastructure). -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!-- The Front Controller servlet for Spring MVC:
       receives incoming requests and dispatches to @Controller methods. -->
    <servlet>
        <servlet-name>mvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

        <!-- Create/initialize DispatcherServlet at app startup (not on first request). -->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <!-- Route all paths through Spring MVC (static resources may still be handled by container/default servlet). -->
    <servlet-mapping>
        <servlet-name>mvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

Once the `ApplicationContext` is loaded and started the spring container does its job creates the Beans, Controllers,
Services, Components and so on, and so forth. The `DispatcherServlet` is like a huge Map of key/value pairs that routes
the client calls to our concrete controller implementations, that is of course an over simplification, but what we need
to know is that the `DispatcherServlet` is in charge of doing all the routing, it is a stateless class that just serves
as a mediator for the requests coming from the web server, and it also of course implements the `Servlet` interface

So how does this work, once the actual web server starts our app, how is the user request propagated down to our own
controller. What is the process that is being executed in order for our code to get executed when we hit the "endpoint"
path in our browser ?

1. The web server part (Tomcat/Jetty/Undertow) When a request hits your app, Socket accept the server accepts a TCP
   connection (or reuses one via keep-alive), then it does a simple `HTTP` parsing, it parses bytes into an HTTP request
   (method, path, headers, body). It typically assigns a worker thread from a pool to handle that request, then it runs the
   filter chain, then calls the matching Servlet that is done through one main entry point called the `DispatcherServlet`,
   that is basically like a huge Map of key/value pairs where the keys are the routes and the values are instances of our
   controllers

2. The key integration point is `DispatcherServlet`. Spring MVC is basically “everything behind that `DispatcherServlet`
   implementation”. Spring Boot registers it roughly like a map, it also register static resource handling (so resources
   under /static/\*\* can be served without a controller). What `DispatcherServlet` actually does ? `DispatcherServlet` is
   a front controller. It does not contain our logic. It routes the request through Spring’s MVC machinery. Step-by-step,
   it attempts to pick a handler - a controller based on the path and then a controller method.

3. Spring uses `HandlerMappings` (especially `RequestMappingHandlerMapping`) to match - HTTP method (GET/POST/…) path
   (/api/users/{id}). After wards the method is invoked. Before calling, it resolves method parameters via argument
   resolvers - `@PathVariable`, `@RequestParam`, `@RequestBody`, `Principal`, `HttpServletRequest`, etc. If there’s a
   request body (JSON), it tries to deserialize it (typically Jackson).

4. Handle the return value. Spring looks at what you returned and decides how to build the HTTP response - If you’re in
   `@RestController` (or `@ResponseBody`) return value becomes the response body and converters serialize it to
   JSON/text/etc, sets Content-Type, writes bytes to response output stream. If you’re in @Controller returning a String
   like "home" - treat it as a view name use a `ViewResolver` to find a template (Mustache, Thymeleaf…) pass the it to the
   renderer

We can summarize this interaction like so, using this simplified diagram to inspect all the different layers that are
involved here. There are other components which can be registered to a web server like Filters and such, but those are
not as important to understand how the general flow is implemented

```plaintext
┌──────────────────────────────────────────────┐
│ 🌐  Browser                                  │
│     (User / Client request origin)           │
└──────────────────────────────────────────────┘
                    │
                    │  HTTP request
                    ▼
┌──────────────────────────────────────────────┐
│ 🧰  Tomcat / Jetty / Undertow                │
│     (Web server + servlet container)         │
│                                              │
│   ┌──────────────┐  ┌──────────────┐         │
│   │ parse HTTP   │→ │ pick thread  │         │
│   └──────────────┘  └──────────────┘         │
│                 │                            │
│                 ▼                            │
│            ┌───────────┐                     │
│            │ Filters   │  (chain)            │
│            └───────────┘                     │
└──────────────────────────────────────────────┘
                    │
                    ▼
┌──────────────────────────────────────────────┐
│ 🚦  DispatcherServlet                        │
│     (Spring MVC entry point)                 │
│                                              │
│   ┌───────────────────────────────────────┐  │
│   │ HandlerMapping                        │  │
│   │  └─ finds matching controller method  │  │
│   └───────────────────────────────────────┘  │
│                    │                         │
│                    ▼                         │
│   ┌───────────────────────────────────────┐  │
│   │ HandlerAdapter                        │  │
│   │  ├─ argument resolvers                │  │
│   │  └─ message converters JSON ⇄ objects │  │
│   └───────────────────────────────────────┘  │
│                    │                         │
│                    ▼                         │
│   ┌───────────────────────────────────────┐  │
│   │ Return value handling                 │  │
│   │  ├─ @ResponseBody → JSON/text bytes   │  │
│   │  └─ view name → ViewResolver → render │  │
│   │                 template → HTML bytes │  │
│   └───────────────────────────────────────┘  │
└──────────────────────────────────────────────┘
                    │
                    │  HTTP response
                    ▼
┌──────────────────────────────────────────────┐
│ 📦  HTTP Response                            │
│     (Server response origin)                 │
└──────────────────────────────────────────────┘
                    │
                    ▼
             ┌────────────────┐
             │ bytes → 🌐     │
             │ back to browser│
             └────────────────┘
```

So in summary the web server really needs `two main things to make our application work,` a listener that is invoked
when our application starts and is somewhat similar to a main entry point. And at least one Servlet, it can of
course be more than one. But since we would like to dynamically register paths for our endpoints we do that with one
big mediator Servlet implementation that routes to our paths, instead of having to manually register those into the
xml file for the web server to discover and initialize, it also allows us to take advantage of Spring Boot Framework
that would manage dependencies for us and allow us to create robust applications

### Request

When a servlet receives a request it has already been processed by the web server, and the web server does some
specific processing of the data which is converted internally into a `ServletRequest`, some of the most important
methods in the `ServletRequest` interface are the following two methods, which allow the user to directly fetch the
request parameters from the request

```java
// obtain values from the request query parameters (part of the URL) or body payload (in case it is valid and
// contains valid form data), these two methods allow one to obtain one value or all values for a given parameter
// name, note that collisions between parameter names in form data and URL query are not handled specially by the
// server and are all dumped into the same Map of parameters
String[] getParameterValues(String name);
String getParameter(String name);
```

There are a few examples below that demonstrate what is going on with different types of requests, that contain
different types of body data and additional data into the URL query section. How the web server treats them is important
to understand as some values `are opaque` to the server - e.g. HTTP body types such as JSON, XML, byte streams etc. Some
however are not opaque and `the data is read from the body` and populated into the request structure like form data
located in the body of the request

1. GET with query arguments

```plaintext
GET /videos?name=five&tag=spring&tag=boot&page=2 HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 ...
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-US,en;q=0.9
Connection: keep-alive
Cookie: JSESSIONID=ABC123...
```

The servlet will process this request and put the query parameters (the only ones in this case) in the parameters map of
the request object, those can be obtained with the `getParameter("name")` method. therefore the parameters map will
contain two parameters with keys/names five and tag matching the values in the query

2. POST with form data

```plaintext
POST /new-video HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 ...
Accept: text/html,application/xhtml+xml
Content-Type: application/x-www-form-urlencoded
Origin: http://localhost:8080
Referer: http://localhost:8080/
Connection: keep-alive
Cookie: JSESSIONID=ABC123...

name=john
age=5
```

This example contain just a form data, similarly as the one above the web server will create the servlet request object
and put the form data into the request parameters map, in this case the `name` field of the form, and it can be obtained
the same way using the `getParameter("name")`, method. The parameters map will contain two parameters with key/names
name, and age matching the values in the form body

3. POST with form and query parameters

```plaintext
POST /new-video?param=homepage HTTP/1.1
Host: localhost:8080
Accept: text/html
Content-Type: application/x-www-form-urlencoded
Connection: keep-alive

param=five
```

This example contains both the query argument and a form data, what happens here, well, the web server will do exactly
the same and put both of these into the parameters map, under the keys source and name. That implies that in the end
data from the form and from the query arguments both go into the same single level map of parameters, that also implies
that we can have name collisions between the name of the form arguments and the query arguments, this is where the
method `getParameterValues` comes in handy that allows us to obtain all values for a given argument, returning String[],
not just the first one (which is what `getParameter` does). In this specific scenario we have only one parameter that is
mapped to 2 values, the name of the parameter is param, however, it has two values one from the form data, one from the
query, that can cause hidden issues bugs and conflicts.

`All in all avoid using form data arguments with the same name as the ones that are used in the query, otherwise it
might cause issues and collisions with the data fields from the form data, The form data is NOT opaque in the body
of the request for the web server, the web server attempts to read form data fields and put them into the inernal
servlet request structure`

4. POST with JSON payload

```plaintext
POST /new-video HTTP/1.1
Host: localhost:8080
User-Agent: curl/8.x
Accept: application/json
Content-Type: application/json
Connection: keep-alive

{
  "name": "five",
  "meta": {
    "quality": "hd",
    "tags": ["spring", "mvc"]
  }
}
```

The example above shows one of the most common examples of what an HTTP request looks like in modern times, that is
an opaque value in the body, could be JSON or XML, anything that is not a form body

So to obtain the data contained in the form parameters we can either use the `@ModelAttribute`, or the `@RequestParam`
annotations, the `@ModelAttribute` can be used to map complex structures nested data models, while the `@RequestParam` is
exactly used to pull singular request parameter values from the request, when we would like to read only a few of them
and are not interested in the rest.

```java
// using the ModelAttribute the container will attempt to construct an object of type Video using the named
// arguments of the request parameters matching them against the named arguments of the Video data model, in this case
// that is possible, if not a runtime exception will occur
@PostMapping("/new-video")
public String addFromObject(@ModelAttribute Video video) {
    videos.add(video);
}

// using RequestParam, the container will simply pull from the request object's parameter map a parameter value
// matching the name of the method argument/parameter and call/populate the method
@PostMapping("/new-video")
public String addFromParam(@RequestParam String name) {
    videos.add(new Video(name));
}
```

`One might think that duplicating the names of the query parameters and the body of the request containing the form
data, might present a security risk because an evil actor can possibly override a form data parameter by adding a
key=value pair with the same name into the URL, however that is not an issue when using proper TLS encrypted
communication, because all of the data including the URL query string, body and headers are encrypted. Therefore it
is not possible to tamper with the data in the form or 'replace' it by 'overriding' it from the query parameters`

### Response

TODO: add caveats and issues here related to a request

## Static

Besides allowing us to use dynamic content, like templates or JSON, and XML the web servers usually have means of
delivering static content that is just as simple as serving static files to the user's browser directly. These
usually go into `src/main/resources/static`, everything in there is served to the end user as is, no mutation no
modification the files are just pulled from the server onto the client's browser as is, it is very much like
downloading a plain file to the client - that file can be anything really, but most commonly those resources are
some of these - javascript, css, html.

## Services

The service component and its accompanying annotation called @Service defines a spring component that is meant to be
used to define our business rules and logic, just like the @Controller annotation is meant to be used for annotating
certain components in spring as Controllers, the Service annotation marks them as services, both of these will cause
spring to create a bean of the given type, and both will go into the `ApplicationContext`, however they have different
semantic meaning, and it is generally not a good idea to mix them up.

```java
public class Video {

    private final Integer id;

    private String name;

    public Video(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // .... Get and Set methods, equals, toString and hashCode, the rest is abridged for simplicity
}
```

```java
// we have defined an intermediate service bean that will be in charge of actually providing the data to our
// controllers, that is usually how in Spring we delegate different roles to different beans, that way each type of
// bean is dong its part allowing us to extend the application by simply composing more components providing greater
// flexibility.
@Service
public class VideosService {

    private static int VIDEO_SEQUENTIAL_INDEX = 1;

    private List<Video> videos = new ArrayList<>();

    public VideosService() {
        videos.add(new Video(VIDEO_SEQUENTIAL_INDEX++, "Youtube"));
        videos.add(new Video(VIDEO_SEQUENTIAL_INDEX++, "Vimeo"));
        videos.add(new Video(VIDEO_SEQUENTIAL_INDEX++, "Vbox"));
        videos.add(new Video(VIDEO_SEQUENTIAL_INDEX++, "Onion"));
    }

    public List<Video> getVideos() {
        return videos.stream().sorted(Comparator.comparing(Video::getId)).toList();
    }

    public Optional<Video> getVideo(Integer target) {
        return videos.stream().filter(vid -> vid.getId().equals(target)).findFirst();
    }

    public Optional<Video> updateVideo(Integer target, String newName) {
        Optional<Video> video = getVideo(target);
        video.ifPresent(vid -> vid.setName(newName));
        return video;
    }

    public boolean deleteVideos(String name) {
        int oldSize = videos.size();
        videos = videos.stream().filter(vid -> vid.getName().equalsIgnoreCase(name)).toList();
        int currentSize = videos.size();
        return oldSize > currentSize;
    }

    public boolean deleteVideo(Integer target) {
        Iterator<Video> iterator = videos.iterator();
        while (iterator.hasNext()) {
            Video video = iterator.next();
            if (video.getId().equals(target)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public Video addVideo(String name) {
        var video = new Video(VIDEO_SEQUENTIAL_INDEX++, name);
        videos.add(video);
        return video;
    }
}
```

## Exceptions

Handling exceptions and errors in API, and sending the proper responses to the client is good for enterprise
application, in this chapter we will learn how to handle the exceptions in spring boot, before proceeding with exception
handling let us gain an understanding on the following annotations:

### @ControllerAdvice

This annotation is used to handle the exceptions globally. What it does it create an advice - an advice is a type of
functionality that is wrapped around a certain functionality, when you a controller method is triggered, a certain code
can be executed before or after or around the execution of this method.

```java
public ResponseEntity<Object> getProducts() {}
```

If we assume we have this method present in our controller, the Advice can be called right before the method is called,
right after the method exits or/and both could be done as well. Meaning that we can do some generic work before or/and
after the method execution.

In the most general use case that can be used to catch exceptions in a single location no mater which controller method
was called. This is useful when used in conjunction with the `@ExceptionHandler` annotation.

### @ExceptionHandler

This annotation is used to handle the specific exceptions, what it does is annotate a method inside a class annotated
with `@ControllerAdvice`, that method will be called when a specific exception of a specific type is emitted from a
controller method.

The advice is usually implemented internally with standard java `runtime proxies` or `cglib` or with a library like
`aspectj`, which allows us to inject at either runtime (through a proxy object) or compile time (by literally writing
byte code during compile time) additional code around a certain construct like - method, constructor etc. The
`ControllerAdvice` and the `ExceptionHandler` are provided by Spring to simplify the process. What is important is that
existing code can be enhanced with additional code implementation that does something either before / after or any
combination of the above, allowing us to control any given block of code and it execution.

```java
// we create an advice for controllers, this annotation will pick up this class and create an advice handler wrapped
// around all methods in classes marked with RestController in our module, it could look something like that, in pseudo code:
// try {
//     return productController.getProducts();
// } catch(Throwable e) {
//     for handler in handlers {
//         if e instanceOf handler.getException() {
//             handler.executeExceptionHandlerMethod();
//         }
//     }
// }
@ControllerAdvice
public class ProductExceptionController {

    @ExceptionHandler(value = ProductNotfoundException.class)
    public ResponseEntity<Object> exception(ProductNotfoundException exception) {
        // handle the specific exception, if another exception is triggered this method will not be called, the
        // ExceptionHandler specifically handles only this type of exception and ignores others, if you wish to be more
        // generic you could create another method, annotated with - @ExceptionHandler(value = RuntimeException.class)
    }
}
```

## Spring Data

As mentioned above while we created the service that was responsible for managing the entries of videos, we also
mentioned that that is usually not how it is done. These are non-persistent entries that live only for the duration of
the runtime, if we want to persist this over time, we have to use a persistent storage. That storage can be anything
really from a file, to a database, to a remote service that stores our data in an opaque way we do not care about.This
is where spring data comes into play it is like a huge adapter or a mediator that allows us to store and work with data
without much hassle.

The most common database used today is a relational one (Oracle, MySQL, PostgreSQL, and so on). Relational databases
comprise 80% of all projects created. Choosing a NoSQL (not only SQL) data store requires careful consideration, but
here are three options we can explore:

• Redis is principally built as a key/value data store. It’s very fast and very effective at storing huge amounts of
key/value data. On top of that, it has sophisticated statistical analysis, time- to-live, and functions.

• MongoDB is a document store. It can store multiple levels of nested documents. It also has the ability to create
pipelines that process documents and generate aggregated data.

• Apache Cassandra provides a table-like structure but has the ability to control consistency as a trade-off with
performance. SQL data stores have historically had hard requirements on predefining the structure of the data, enforcing
keys, and other aspects that tend to give no quarter.

NoSQL data stores tend to relax some of these requirements. Many don’t require an upfront schema
design. They can have optional attributes, such that one record may have different fields from those
of other records (in the same keyspace or document).

Some NoSQL data stores give you more flexibility when it comes to scalability, eventual consistency,
and fault tolerance. For example, Apache Cassandra lets you run as many nodes as you like and lets
you choose how many nodes have to agree on the answer to your query before giving it. A faster
answer is less reliable, but 75% agreement may be faster than waiting for all nodes (as is typically the
case with relational data stores).

### Interface

Before we start we have to first have a good grasp on how spring data operates, unlike other data based interfaces
spring data is not one big giant API that is implemented by different data store solutions, that is because each of them
offer a great deal of varying features, and having one common interface is never going to be efficient. Instead what
spring data does is offer completely different incompatible modules that build from the ground up for each different
solution, like Infinispan, Cassandra, MongoDB, Redis, JDBC, JPA, etc.

That is why the spring data starters look like this, each of them offers a very distinct type of api and a very distinct
type of architecture to handle the specifics of the data store it is implementing. One of the most important data
starters is probably the JPA one. JPA is a specification that describes an ORM (object relational model) database model
based and usually built on top of relational data bases. JPA specification is implemented by many providers some of the
most important and famous ones are `Hibernate` and `EclipseLink`. Unlike other spring data providers or portfolios, the
`JPA` starter is a bit different, as it does not directly work with the database, rather it uses underlying
implementation (usually hibernate by default) to talk to the database. While the other starters are taking advantage of
direct communication with the data base storage directly - MongoDB, Redis, JDBC etc.

For That reason the JPA starter is a bit different and does not follow the more standardized architecture that most
spring data starters follow, it is a more specialized spring-data module. We will take a deep look at its architecture
and contrast and compare between the other standard spring data starters.

```xml
<!-- this is just an example of the different starters that exist for the different offerings that spring has to -->
<!-- communicate with different data stores, each of these has specific requirements and needs when it comes to
operating with it that is why they are in separate starters to begin with -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### Templates

Spring data offers something called templates, those are the work horses of spring data they executes the low level
operations that actually execute the actual underlying data base ops. The templates internally use other beans which
actually establish the connection, hold a connection pool to the database, do the actual communication and ops.

### Database

Starting off with possibly the most prolific database type that is used in the vast majority of projects, a relational
database is a structured table based data store that defines a very strict form and shape for the data that is being
stored.

Adding the spring data starter JPA and `H2` in memory database to the project is as simple as adding these two
dependencies to our project build file

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

Then we can add the following to our application configuration, that will ensure that a default data source is
initialized and auto-configured by spring, later we will investigate how to customize our data sources and how to
create more than one.

```yaml
spring:
    datasource:
        url: jdbc:h2:mem:mydb
        username: sa
        password: password
        driverClassName: org.h2.Driver
    jpa:
        database-platform: org.hibernate.dialect.H2Dialect
```

To ensure that we can actually see what is going on with the database and the tables we can enable the console, the
h2 console allows us to inspect the database as if we are connected to a database management tool, but in the
browser, visit `http://localhost:8080/h2-console`

```yaml
spring:
    h2:
        console:
            enabled: true
            path: /h2-console
```

That is it in the console when you open it connect to the same URL as we have configured above, that would be -
`jdbc:h2:mem:mydb`, and use the same username and password as in the `datasource` configuration in the main
application properties file

One can also configure a file to be used to construct and initialize the database and tables as well as pre-populate
it with data. That can be done in the connection URL string for the database providing the full absolute path to a
file on your file system.

By default if we are using the JPA spring data starter, which internally uses Hibernate, the moment we start the
application, Hibernate will add / create schemas that match our entities that we have defined and declared in our
app, this is something that can be turned off and is usually done so with - `hibernate.hbm2ddl.auto=none`

Set the hibernate from spring directly, this is a shortcut property that one can set and it will actually under the
hood set the `hibernate.hbm2ddl.auto` that is directly tied to hibernate

```yaml
spring:
    jpa:
        hibernate:
            ddlAuto: none
```

Put these files directly in your src/main/resources, one should be called data.sql, and the other schema.sql, and
the names are pretty much self explanatory these files are read by, `DataSourceInitializationConfiguration`, and are
applied to the first data source found in the spring container environment.

```sql
CREATE SEQUENCE IF NOT EXISTS VIDEOS_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS VIDEOS (
  ID BIGINT DEFAULT NEXT VALUE FOR VIDEOS_SEQ PRIMARY KEY,
  NAME VARCHAR(128) NOT NULL UNIQUE,
  DESCRIPTION VARCHAR(1024)
);
```

```sql
INSERT INTO VIDEOS (NAME, DESCRIPTION) VALUES ('Neon Harbor', 'A disillusioned dockworker uncovers a smuggling ring and must choose between quiet survival and loud justice.');
INSERT INTO VIDEOS (NAME, DESCRIPTION) VALUES ('Clockwork Orchard', 'In a town where timekeeping is law, a botanist finds a tree that blooms one hour into the future.');
```

### Entities

The entities are the lowest form of data transfer definitions, these are basically what a way to describe our
database data structure or in other words for relational data bases that is also known as a schema with code. The
JPA specification has a special annotation that is called @Entity, that is what is used to mark java classes as
such.

```java
@Entity
@Table(name = "videos")
public class VideoEntity {

    @Id
    @SequenceGenerator(name = "VIDEOS_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VIDEOS_SEQ")
    @Column(name = "id", nullable = false, updatable = false, insertable = true, unique = true)
    private Long id;

    @Column(name = "name", unique = true, updatable = true, insertable = true, nullable = false, length = 128)
    private String name;

    @Column(name = "description", unique = false, updatable = true, insertable = true, nullable = true, length = 1024)
    private String description;

    public VideoEntity() {
        // require a default safe constructor
    }
    .....
}
```

There are few things going on there but what we need to care about is the annotations, such as Entity and Id, these
two define the cornerstone, of a relational data/entity, each entity in a database is most commonly identified with
an ID, that is a unique identifier that is generated by the database, based on certain rules that we can customize
and define, but most commonly that is a self incrementing positive integer starting from 1, and always incrementing.

As we mentioned above if you start the application and have already configured the database connection and have it
up and running the provider (by default Hibernate) will create the entity schema for you).That also includes any
number of auxiliary structures like sequences, indexes, foreign keys, and so on, set `hibernate.hbm2ddl.auto to
configure this behavior`.

```
The primary key of an entity is one of the most important properties for that entity and it is crucial to understand
these two annotations however, they contan a lot of information on how hibernate will use the unique identifier for
the entity, and how a enw value will be assigned when a new entity is received, in this case, what happens here is
that we tell it that the ID is a GENERATED value, and also tell it HOW to generate that value, in our schema we have
created the sequence that generates new values, and they have to match - name, initialValue and allocationSize.

Hibernate is also capable of creating these sequences on its own but usually it is a much better idea to construct
these manually when we craete the schema of te entity ourselves. That way we have proper control over what is going
on. This is usually done with tools lke Flyway or Liquibase

@Id
@SequenceGenerator(name = "VIDEOS_SEQ", initialValue = 1, allocationSize = 1)
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VIDEOS_SEQ")
long id;
```

### DTO

A data transfer object - DTO, is a bridge or interface that we use between the database entities and the clients of
our code, we would like to avoid exposing the internal structure of our database entities, and this is where data
transfer objects come into place. The allow us to show a snapshot of the database entity, usually some of the data
that is relevant to the client of a particular business component of our application.

This allows us to abstract away the internal structure oor schema of the database entity away from the business. It
is easy to maintain and change, and usually this intermediate structure allows us to model the business domain much
better when we are not confined by the restrictions and structure of the database entity itself.

### Repositories

```java
@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

    Optional<VideoEntity> findByName(String name);

    Optional<VideoEntity> findByDescription(String name);

    List<VideoEntity> findAllByNameContaining(String name);

    List<VideoEntity> findAllByDescriptionContaining(String name);

    void deleteAllByName(String name);
}
```

The spring dogmatic way of working with the entities and extracting them is through what are called repositories,
these are marker interfaces, where we add just the interface methods without any implementation, we can still have
default interface methods to aggregate the results of existing ones of course.

The idea of these spring data repositories is to let spring provide its own implementation, for these interfaces
that we create. At startup time spring detects interfaces that extend the Repository interface that is at the root
hierarchy and provide implementations for our user defined and declared interfaces

By default the `JpaRepository` has already a list of pre-declared and pre-defined methods which are some of the most
common ones that somebody might expect to see such as `findAll`, `findById` etc. There are other ones such as
`getById`, `deleteById` and so on and so forth. Not all of those methods are declared in the `JpaRepository`
interface some come from a higher order interfaces from which `JpaRepository` itself extends. But that is not crucial
we need to know that this entire hierarchy terminates in a Repository interface which is a marker interface used by
spring data internally to detect repository beans

We can also see that the interface that we have created also declares its own method that is called `findByName`,
that is going to be picked up by spring data and automatically it will generate an implementation based on the name
of the method. There is some magic going on here which we will investigate later. but in a few words, the spring
data looks at the name and based on that and the target entity and its properties can deduce and create a query for
the internal data base implementation that it is backed against, for example for this simple method name it will
generate an SQL query such as - `SELECT * FROM videos WHERE videos.name = 'name'`

`This automatic query generation is quite useful because we will not have to care about database SQLdialects,
meaning we can change the undelrying database implementation such as MSSQL or MySQL or Postgres without having to
care about our queries being incompatible because spring daata will take care of that`

#### Pageable & Sort

Spring data supports what are known as pages these pageable objects can be passed down to the repository itself, and
based on it it will page the result, with a size per page and page index, `remember that page number starts at 0`

```java
// in the repository we might have declared a method like that, spring data will automatically take care of
// generating an underlying implementation that does handle the paging of the results.
Page<VideoEntity> findAll(@NonNull Pageable pageable);

public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, Model model) {
// clamp the number of the page to never be less than 0, and the size to be at least between 1 and 100, by default
// the page number and size have limits that can be configured when using the spring request to construct these
    page = Math.max(page, 0);
    size = Math.min(Math.max(size, 1), 100);

    // construct the actual page manually, based on the input arguments.
    Pageable pageable = PageRequest.of(page, size, sortCriteria);
    videoRepository.findAll(pageable);
}
```

These properties are only in effect when we leave spring to initialize our pageable objects, not if we manually call
Pageable.of, these take effect mostly if we have a controller that takes in an argument of type Pageable and we let
Spring construct these for us, that way Spring create the pageable object from the specified properties and do the
clamping and size restrictions on its own, the final Pageable object that we receive will still start from 0, but we are
allowed to pass in a page number of 1, and spring will take care of clamping it down to 0, we can also change the name
of the parameter that is used to read the page number the name of the parameter that is used to read the page size and
so on and so forth.

```yaml
spring:
    data:
        web.pageable.default-page-size: 20 # Default page size.
        web.pageable.max-page-size: 2000 # Maximum page size to be accepted.
        web.pageable.one-indexed-parameters: true # Whether to expose and assume 1-based page number indexes.
        web.pageable.page-parameter: page # Page index parameter name.
        web.pageable.prefix: # General prefix to be prepended to the page number and page size parameters.
        web.pageable.qualifier-delimiter: _ # Delimiter to be used between the qualifier and the actual page number and size properties.
        web.pageable.size-parameter: size # Page size parameter name.
        web.sort.sort-parameter: sort # Sort parameter name.
```

```java
@GetMapping("/list")
public ResponseEntity<Object> getVidoes(Pageable pageable) {
    // the pageable object above will be created based on the web.pageable configuration we have set, it is as
    // if we are leaving spring to automatically construct the page object, something that we might have to have
    // done manually instead.
    return ResponseEntity.of(Optional.of(videosService.getVideos(pageable)));
}
```

We have done some changes to the Video class, mostly rename and converted it to a more robust immutable record
model, where it is easier to pass around as a plain object without having to worry about intermediate state
mutations. We have also introduced a new converter that will easily allow us to convert from one type to another. In
this example we have only one `DTO (VideoModel)` and only one `Entity (VideoEntity)` that we convert from but in a
real business scenario you could imagine that we might have more.

```java
// Modify the video model to use record, effectively the same implementation as the original data transfer object
// but a record allows us to create a truly immutable object that we can pass around and not worry about state changes.
public record VideoModel(Long id, String name, String description) {
}

// a simple converter utility class that is meant to speed up the usage and ergonomics of the conversion between one
// type to another - or in this case from the business data transfer type to the underlying type being represented and
// stored into the database
public class VideoConverter {

    private VideoConverter() {
    }

    public static final VideoModel convertFrom(VideoEntity entity) {
        return new VideoModel(entity.getId(), entity.getName(), entity.getDescription());
    }

    public static final VideoEntity convertFrom(VideoModel model) {
        return new VideoEntity(model.name(), model.description());
    }
}
```

#### Example & Matchers

Searching in a repository can also be done by what is called query by example this is a spring declarative
alternative of writing dynamic queries instead of having to create a custom method for each possible type of query
combination or having to do post filtering.

```java
// construct a probe or also called example object, the idea is that this, is the prime 'example' by which the
// repository will look for other matching results, if we provide no custom matcher, it will simply find this probe
// object. If we however create a matcher that also sets the condition matching on certain fields from the object
// then we can/will do a broader match, effectively allowing us to match more than one entity based on certain
// properties it posses
VideoEntity probe = new VideoEntity();
probe.setId(id);
probe.setName(name);
probe.setDescription(descrition);

// create a matcher that will instead of attempt to exact match values for the fields from the probe that are set,
// will match using starts or contains conditions, that way the results that are gong to be found are more than just
// one - the probe
ExampleMatcher matcher = ExampleMatcher.matchingAll()
    .withIgnoreNullValues()
    .withIgnorePaths("id")
    .withMatcher("name", m -> m.startsWith().ignoreCase())
    .withMatcher("description", m -> m.contains().ignoreCase());

// by default the spring data repository extends off of an interface that has a findAll overload that takes in as
// its first argument this special example object, the idea behind the example is that it asks the repository to find
// all entities that match this 'example' probe
Example<VideoEntity> ex = Example.of(probe, matcher);
videoRepository.findAll(example, pageable).map(VideoConverter::convertFrom);
```

### Service

We can then update over service to use the videos repository and actually take advantage of the methods that it
exposes to do the same operations that we have already done to the service. This is not an exhaustive representation
of the service but merely a simple example that aims to show how we can use the concepts we have thus far looked
into

```java
@Service
public class VideosService {

    private final VideoRepository videoRepository;

    public VideosService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<VideoModel> getVideos() {
        // extract all videos from the repository - can be costly with lots of rows
        return videoRepository.findAll()
        .stream()
        .map(VideoConverter::convertFrom)
        .toList();
    }

    public Page<VideoModel> getVideos(Pageable pageable) {
        // fetch only one "page" at a time, map entities -> models
        return videoRepository.findAll(pageable)
        .map(VideoConverter::convertFrom);
    }

    public Page<VideoModel> searchVideos(VideoSearchCriteria criteria, Pageable pageable) {
        // query-by-example (QBE) works by:
        VideoEntity probe = new VideoEntity();
        probe.setName(blankToNull(criteria.name()));                 // String
        probe.setDescription(blankToNull(criteria.description()));   // String

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
            .withIgnoreNullValues()
            // avoid matching by id unless you explicitly want it
            .withIgnorePaths("id")
            // global string rule (optional)
            .withStringMatcher(StringMatcher.CONTAINING)
            .withIgnoreCase()
            // per-field overrides (recommended)
            .withMatcher("name", m -> m.startsWith().ignoreCase())
            .withMatcher("description", m -> m.contains().ignoreCase());

        Example<VideoEntity> example = Example.of(probe, matcher);

        return videoRepository.findAll(example, pageable)
        .map(VideoConverter::convertFrom);
    }

    @Transactional
    public Optional<VideoModel> updateVideo(VideoModel target) {
        // update an existing video, apply non-blank fields
        return videoRepository.findById(target.id()).map(entity -> {
                if (StringUtils.hasText(target.name())) {
                    entity.setName(target.name().trim());
                }
                if (StringUtils.hasText(target.description())) {
                    entity.setDescription(target.description().trim());
                }
                VideoEntity saved = videoRepository.save(entity);
                return VideoConverter.convertFrom(saved);
            });
    }

    @Transactional
    public boolean deleteVideos() {
        // delete all videos in a batch
        videoRepository.deleteAllInBatch();
        return videoRepository.count() == 0;
    }

    @Transactional
    public boolean deleteVideo(Long target) {
        // delete a single video by a target
        videoRepository.deleteById(target);
        return videoRepository.findById(target).isEmpty();
    }

    @Transactional
    public VideoModel addVideo(VideoModel video) {
        // new entity from model; name required, description optional
        VideoEntity newEntity = new VideoEntity(video.name(), video.description());
        VideoEntity saved = videoRepository.save(newEntity);
        return VideoConverter.convertFrom(saved);
    }

    public record VideoSearchCriteria(String name, String description, Long ownerId) {
    }

    private static String blankToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}
```

The usage of the `@Transactional` annotation above is really mandatory when the function encompasses more than one
calls or operations to the database, even if some of them are not mutating the state like the ones that are responsible for
finding entities, it is advised that these are compactly wrapped in a transaction.

`Transactions provide an atomic all or nothing execution, the idea is that certain actions against the database have
to be performed in an atomic and safe way, if exceptions arise the all of the actions are rolled back - these the
transaction wraps around.`

#### Transactions & Transactional

Transactions are a core structure that is not only relevant for the persistence layer, everything can really be
wrapped in a transactional context, allowing execution to be be performed in a structured atomic way, what does it
mean ? Several actions can be performed in a sequence and they will be committed only when every action completes

Transactions are often abbreviated for short as `Tx` or `tx`, in case you see that term mentioned somewhere that is
what it means, it is just a short-hand for transaction.

`Transactional methods by default are implemented with spring proxies, which are runtime proxies that allow spring
to wrap around our methods, however due to some limitations calling transactional method from non-transactional
method will not actually trigger transaction`

```java
class Service {
    // this method by itself will be correctly called within transaction when we call service.doWork(), because
    // the spring proxy wraps around the bean, however internal calls will not trigger this proxy, see below
    @Transactional
    public void doWork() {}

    // this method is not annotated with the @Transactional annotation and will not actually trigger the
    // annotation or transaction when calling doWork, this occurs only when calling the method internally from
    // the same bean, might want to look into AspectJ
    public void doWork2() {
        this.doWork()
    }

    // this one will bypass the proxy issue with spring, and run the method in a transaction, but the method
    // that is going to be in transaction is obviously doWork3 not doWork, however since it is internally calling
    // doWrok every action perormed by doWork will properly be executed within a transactional context
    @Transactional
    public void doWork3() {
        this.doWork();
    }
}
```

So how does it work, well there is one `PlatformTransactionManager` that is the parent interface from which all other
transaction manager implementations extend and implement, the idea is that every data source provider such as Hibernate
(implementing JPA), Mongo, JDBC, Redis and so on CAN but are not mandated to implement their own transaction manager,
then we can use that manager in our business services.

`PlatformTransactionManager` - (`TransactionManager` marker interface) - the central interface in spring imperative
transaction infrastructure, all transaction managers extend or implement this interface, here are a few examples:

- `JpaTransactionManager` — JPA transactions (EntityManager), typically with Hibernate underneath.
- `MongoTransactionManager` - Mongo transaction that manages ClientSession based transactions
- `DataSourceTransactionManager` — JDBC transactions (Connection auto-commit off/on).
- `JtaTransactionManager` — JTA / distributed transactions across multiple resources.
- `R2dbcTransactionManager` - Reactive transaction api for R2DBC

It is important to remember that by default each spring starter will have a default tx manager, for each data store,
typically, that Mongo, Jpa, some data starters do not have a concept of a transaction manager,

`However you have to remember, your spring application will use the default/primary one when the code is annotated
with @Transactional, whichever transaction manager is by default setup will be the one that is used. This is crucial
because, assuming you have more than one transaction manager available from AutoConfiguration, you might be thinking
that you are using transactions for your MongoDB operations, but in all actuality it is using the one for JPA
because that is the default one.`

#### Design

Before we dig deeper we have to add some context, in the standard java persistence layer, there are a few annotations
that are directly linked to working with database entities like tables, primary keys, foreign keys and so on. These are
crucial when designing a database schema for our business case lets examine a few of them.

##### Annotations

- `@OneToOne`
- `@OneToMany`

- `@ManyToOne`
- `@ManyToMany`

- `@JoinColumn`
- `@JoinTable`

##### Usage

`Many-to-one + One-to-many` - this one is among the most common ones. What this relation expresses is that one side of
many entities has at most one reference to the other. Think of it like that a User of a store or a customer has many
orders, each of which is unique, and each of which belongs to that one user, and no other, in other words an order can
not belong to another user, but that user is often having many more than one orders..

How does it work, on the “many” side, that is the Order side, in our example `@ManyToOne` + `@JoinColumn`, On the “one”
side, that is the User in our example, (use for optional navigation) - `@OneToMany(mappedBy="...")`. What `mappedBy`
expresses here is the inverse non-owning side, in our case the User entity does not own that relation ship in a data base
terms that relationship is expressed on the `Order` entity or table, that is because many unique orders are linked to
exactly one user, since they can only be made by that user. It does sound counter intuitive that the owning side is the
Order, but what we say is that is the owning side of the relationship, usually the java persistence layer allows us to
control the relation from both sides so that is not a problem when the model is expressed in ORM fashion

To even further explain and expand how do we think about it, is that the table on the “many” side holds the foreign key
column, so it’s usually the **owning side** (the side that writes the foreign key), while the `@OneToMany(mappedBy=...)`
side is typically **inverse** and is mainly for convenience, however as mentioned both sides can control and persist
data when working with the ORM model.

```java
@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    private Long id;

    // This annotation tells the ORM that one customer can be linked to many orders, and that this is the non
    // owning side of the relationship, note that we have specified the mappedBy, which is the same name as the
    // field / property / variable in the order entity, that can be omitted it is usually automatically deduced by
    // the ORM - Hibernate in our case can resolve it on its own
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<OrderEntity> orders;
}

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    private Long id;

    // This annotation tells the ORM that many orders are linked to one customer, that is because each customer
    // makes his own unique order entities of course. We can also see that the Join column is used here that
    // indicates that this is the owning side, or in other words the side that has the FK column.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;
}
```

```sql
CREATE SEQUENCE IF NOT EXISTS CUSTOMERS_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS ORDERS_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS CUSTOMERS (
  ID BIGINT DEFAULT NEXT VALUE FOR CUSTOMERS_SEQ PRIMARY KEY,
);

CREATE TABLE IF NOT EXISTS ORDERS (
  ID BIGINT DEFAULT NEXT VALUE FOR ORDERS_SEQ PRIMARY KEY,
  FOREIGN KEY (customer_id) references CUSTOMERS(ID)
);
```

`One-to-one + join-column` - another example is a unique one to one relation ship between two different entities, think
of it as two entities that are both completely unique linked to each other, in some way this can usually be done to
expand an existing table or avoid pulling too much data at once with the ORM model, or in fact to avoid having too much
data in a single table, there might be multitude of reasons, however the important part to remember here is that the one
to one relation is between two unique entities and the relation has to also be unique, meaning an entity from one table
can not have relation to more than 1 entity from the other table, otherwise if it does we are in the Many-to-one
relation use-case. It is truly one-to-one only if the foreign key is **unique**

As an example we can consider a `User` and a `UserProfile` entities, the profile entity belongs to one and only one user and
that is it, the relation is unique by convention, and understanding. The owning side here is again the one that posses
the foreign key, and in this scenario we can either pick the user or the profile entities, but usually it will be the
user, the semantically more important entity.

This relation uses a combination of the `@OneToOne + @JoinColumn` that is because the ORM needs to know on which column
to join the two tables to extract the details from. The inverse side is again marked as `OneToOne` and just like the
`ManyToOne` we only need to specify the `mappedBy` that is the name of the field that is on the owning side.

```java
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private Long id;

    // This annotation tells the ORM that one user can have exactly one unique profile linked to it, what is
    // important here to repeat, one profile belongs to only one user only, further more it tells the ORM that the
    // owning side is the user because it holds the FK column called profile_id, that is where the @JoinColumn
    // comes in play
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileEntity profile;
}

@Entity
@Table(name = "profiles")
public class ProfileEntity {
    @Id
    private Long id;

    // This annotation tells the ORM that one profile belongs only to one user that this is  the non owning side
    // because we are using the mappedBy property of the annotation, there is no user entity data in this table
    // it is implicitly linked to the users table by the user table only
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "profile")
    private UserEntity user;
}
```

```sql
CREATE SEQUENCE IF NOT EXISTS USERS_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS PROFILES_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS USERS (
  ID BIGINT DEFAULT NEXT VALUE FOR USERS_SEQ PRIMARY KEY,
  FOREIGN KEY (profile_id) references PROFILES(ID)
);

CREATE TABLE IF NOT EXISTS PROFILES (
  ID BIGINT DEFAULT NEXT VALUE FOR PROFILES_SEQ PRIMARY KEY,
);
```

`Many-to-many + Join-table` - the many to many relation is a bit more complicated, with this one we are saying that two
entities are not owning each other explicitly or implicitly, with the example above the `Order` could not really exist
without the presence of a `Customer` to make that order, and the `UserProfile` could not really exist without the presence
of a `User`. Here usually the many-to-many expresses a connection between two independent entities that can share some
sort of a connection. For example Students and Courses.

Each `student` can be taking many `courses`, and each course can be in turn linked to many students, these entities can
indeed exist independently of each other, and can be linked to other entities as well, other than from each other. To
express this relation we need a 3rd table, that is called a `JoinTable` that table is meant to contain at least a pair
of foreign keys that is unique (the pair of keys itself is unique yes). That pair expresses the connection between the
two entities.

The way it works is by using a `ManyToMany` annotation, and a `JoinTable` on the owning side, in this case the owning
side might be the Course entity since, but either one does make sense it up to the design. On the inverse side we use
again `ManyToMany` and `mappedBy`, that is the non owning side.

“Typical” ownership guidance (practical defaults) - If there is a foreign key column in a table, the entity field mapped
to that column (commonly `@ManyToOne` or owning `@OneToOne`) is usually the **owning side**, while seeing `mappedBy`
marks the **inverse side**, for many-to-many, the side with `@JoinTable` is the **owning side**.

```java
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private Long id;

    //  this annotation tells the ORM that each user can have multiple roles, and that each role itself can be
    // tied to many users, there is no uniqueness requirement, roles can be shared between the users, and users can
    // be shared between roles, further more it tells the ORM that this is the owning side because we have the
    // @JoinTable declared here, this means that connections will be persistent when we make changes from the user
    // entity
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;
}

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    private Long id;

    // This annotation tells the ORM that there are many users related to this role, but that the ownership is
    // now held by the role entity, it is held by the mapped by property in the user entity, in this case that is
    // the one named roles
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<UserEntity> users;
}
```

```sql
CREATE SEQUENCE IF NOT EXISTS USERS_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS ROLES_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS ROLES (
  ID BIGINT DEFAULT NEXT VALUE FOR ROLES_SEQ PRIMARY KEY,
);

CREATE TABLE IF NOT EXISTS USERS (
  ID BIGINT DEFAULT NEXT VALUE FOR USERS_SEQ PRIMARY KEY,
);

CREATE TABLE user_role (
  role_id BIGINT NOT NULL,
  user_id  BIGINT NOT NULL,
  PRIMARY KEY (role_id, user_id),
  FOREIGN KEY (role_id) REFERENCES ROLES(id),
  FOREIGN KEY (user_id)  REFERENCES USERS(id)
);
```

There are many other considerations when it comes to data base design, but for the time being we will only focus on the relation ship

### Security

By default spring exposes a default configuration that can be used to secure an application directly we have a great
control over how the security is implemented and configured, but it can be quickly enabled out of the box to give you
some basic user authorization and authentication to get you going fast. Include the security dependency in your build
files first

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

The security will be enabled immediately, that is because spring tries to eagerly auto configure default security
when we include the dependency on the class path, we can disable this later provide our own stack, remember that for
each starter spring declares auto-configuration classes in its resource file, that tells spring which
auto-configuration class beans to create early, these beans construct the security context, by default most of these
beans are conditional so they are not enforced onto the user, they can be disabled and a custom configuration can be
setup by the user -

We are referring to the following file as we have already seen it can be used to control which beans spring auto creates
with the start of the spring container. In the new Spring 4 version, this file is split to achieve a more modularized
structure for the entire spring framework.

`src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`

The spring security filter chain is basically a big servlet chain. On each request the entire filter chain is
examined, usually when we visit a protected route, the filter chain will throw an exception that is going to be
caught and then the spring container will attempt to establish a user login and authentication

- security context - where spring stores the current authentication for the request and for the session.
- authentication filters - extract credentials from the request - basic header, login form POST, etc.
- authorization filter - checks if this request is allowed to be accessed or not, throws if not.

We are going to build a comprehensive structure of entities that is going to represent the users, roles they have,
the authorities these roles poses and so on. The structure will be as follows, we will have a `UserEntity`, that has
a `RoleEntity` bound to it, each role will be a set of `AuthorityEntity`, authorities which themselves are also
entities in the data base. That will allow us to re-use role entities across multiple users and authority entities
across multiple roles.

Each authority will have a many to many relation with a role entity, and each user entity will also have a many to
many relation with the user entities. That is because we will have unique entries of roles that can be linked to
many users, and unique entries of authorities that can be linked to many roles and vice versa.

We have already discussed a way to do this in a chapter above but the general gist of this is to construct a
hierarchy similar to this. We will have these entities created `UserEntity -> RoleEntity -> AuthorityEntity.` They
represent the control a user can have over a resource in the system and we can see how we can feed the information
we have in the database directly to spring, and its internally user representation to secure our application not just
with a user but by providing a fine grained control over the actions a user can partake based on its granted roles
and by proxy the granted authorities for these roles.

#### Structure

This is a very brief description of the structure we are going to use there are quite a few things missing here, but
this is the general gist of it we create the user and role structures that we are going to need to provide a details
for the security service in spring.

`You have to remember that when the persistence annotations are either required to be put on methods or the fields,
however we should not mix them up`

```java
@MappedSuperclass
public abstract class AbstractEntity {
}
```

```java
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @SequenceGenerator(name = "USERS_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @Column(name = "id", nullable = false, updatable = false, insertable = true, unique = true)
    private Long id;

    @Column(name = "username", unique = true, updatable = false, insertable = true, nullable = false, length = 64)
    private String username;

    @Column(name = "password", unique = false, updatable = true, insertable = true, nullable = false, length = 128)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
}

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @SequenceGenerator(name = "ROLES_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLES_SEQ")
    @Column(name = "id", nullable = false, updatable = false, insertable = true, unique = true)
    private Long id;

    @Column(name = "name", unique = true, insertable = true, updatable = true, nullable = false, length = 32)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<UserEntity> users;

    @ManyToMany
    @JoinTable(name = "role_authority", joinColumns = @JoinColumn(name = "role_id"),
                    inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<AuthorityEntity> authorities;
}

@Entity
@Table(name = "authorities")
public class AuthorityEntity {

    @Id
    @SequenceGenerator(name = "AUTH_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTH_SEQ")
    @Column(name = "id", nullable = false, updatable = false, insertable = true, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, updatable = false, insertable = true, unique = true, length = 32)
    private String name;

    @Column(name = "grant", nullable = false, updatable = false, insertable = true, unique = true, length = 128)
    private String grant;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
    private Set<RoleEntity> roles;
}
```

```sql
CREATE SEQUENCE IF NOT EXISTS USERS_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS ROLES_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS AUTH_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS ROLES (
  ID BIGINT DEFAULT NEXT VALUE FOR ROLES_SEQ PRIMARY KEY,
  NAME VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS AUTHORITIES (
  ID BIGINT DEFAULT NEXT VALUE FOR AUTH_SEQ PRIMARY KEY,
  NAME VARCHAR(32) NOT NULL UNIQUE,
  GRANT VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS USERS (
  ID BIGINT DEFAULT NEXT VALUE FOR USERS_SEQ PRIMARY KEY,
  USERNAME VARCHAR(64) NOT NULL UNIQUE,
  PASSWORD VARCHAR(128),
  ROLE_ID BIGINT NOT NULL,
  FOREIGN KEY (ROLE_ID) references ROLES(ID)
);

CREATE TABLE ROLE_AUTHORITY (
  role_id BIGINT NOT NULL,
  authority_id  BIGINT NOT NULL,
  PRIMARY KEY (role_id, authority_id),
  FOREIGN KEY (role_id) REFERENCES ROLES(id),
  FOREIGN KEY (authority_id)  REFERENCES AUTHORITIES(id)
);
```

#### Users

We need to provide a service that will tell spring how to pull information about our users, first we need a
repository that will be able to pull the data for a user from the data base.

```java
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
```

Then we will provide a security configuration that will create an instance of users details, that will be used by
spring and used to authorize and authenticate users when we attempt to login as a known user. We can add some dummy
users in the data base following the scheme we will have to create both the user and the roles along side some dummy
authorities.

```java
@Transactional
public Optional<UserDetails> getPrincipal(String username) {
    return userRepository.findByUsername(username).map(user -> new UserDetails() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return user.getRole().getAuthorities().stream().map(AuthorityEntity::getGrant).map(SimpleGrantedAuthority::new).toList();
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }
    });
}
```

Lastly we link the, repository to the bean and create an anonymous instance of the details service that is going to
be used as the default one to be injected internally by the default spring authenticator

```java
@Configuration
public class SecurityConfiguration {

    @Bean
    UserDetailsService userDetailsService(UsersService userService) {
        return name -> userService.getPrincipal(name).orElseThrow(() -> new UsernameNotFoundException(name));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword == null ? null : rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String storedPassword) {
                return Objects.equals(rawPassword == null ? null : rawPassword.toString(), storedPassword);
            }
        };
    }
}
```

By default spring uses the concrete instance called - `DaoAuthenticationProvider`, that class has a setter injection
method that is called `setPasswordEncoder`, once we create our password encoder instance bean it will actually be
injected into the bean through the setter method.

Our example demonstrates how to set a very plain encoder, that just matches the raw password that was entered by the
user, in reality that will only be a valid case if we were doing this for development locally or in noncritical
environments

The other bean in this class is our beans details service for the user. That one is in charge of creating the providing
details from a repository or some sort of a storage.

In reality we will actually take care of encrypting these passwords, or encoding them salting them, so how does this
process actually work. The default spring encoders 'encode' the password and ensure that it matches with the one stored
in the database, these include things like `MessageDigestPasswordEncoder` (Md5) and `BCryptPasswordEncoder` and more.

#### Login

First lets examine types of of login and the credentials approaches, By default the spring security context is
configured to use the login form login, which present the user with an html login form that validates the user.

The types of logins that spring provides are many some of which are related to external providers like saml or oidc,
some of them can be built in -house ones like a basic login form or a more advanced post based login form.

- Basic login - the basic login is supported by most browsers, it is a native browser interface that shows up like a
  pop up and allows the user to login by entering his credentials.

- Login form - the login form is a more advanced built in html based form, that spring provides, the user is
  presented with a form where he again enters username and password, then this information is sent for authorization
  and authentication

- Providers - a list of many 3rd party authorization and authentication providers that are supported by default.
  Each of them has a completely different way of doing the user validation and registration so we are not going to dig
  deep into them at this time.

Lets examine the different types of login, generally we have two types ones are the stateful logins, and the other
is the stateless, the stateful are these which restore the state of the user based on some information that is sent
on each request by the user's client, usually the browser and usually a cookie. The stateless ones are the ones
where the state is sent manually, such as a token that the user himself has, and it exposed to the user like a JWT token.

Stateful login - usually implemented with a cookie, this cookies is stored in your browser usually not accessible to
scripts running on the browser side, it is securely stored in the browser and the browser (or the user's client) sends
it on every request, that is just an identity, a unique string, which is then related to some state stored on the
server. What does it look like, here is the brief description of the filter chain, for our stateful login attempt

1. DisableEncodeUrlFilter
2. WebAsyncManagerIntegrationFilter
3. SecurityContextHolderFilter (loads/clears SecurityContext for the request)
4. HeaderWriterFilter
5. CorsFilter
6. CsrfFilter
7. LogoutFilter
8. UsernamePasswordAuthenticationFilter - pulls the data from the POST to /login, from the login page
9. DefaultLoginPageGeneratingFilter - renders the default login page if you did not provide one
10. DefaultLogoutPageGeneratingFilter - renders the default logout page if needed, i.e. visit the /logout route
11. RequestCacheAwareFilter - remembers original URL for redirect after login
12. SecurityContextHolderAwareRequestFilter - wraps request with security-aware methods
13. ExceptionTranslationFilter - turns “not authenticated” into redirect-to-login or 401
14. AuthorizationFilter - final authorization decision

- `First pass`: You’re not authenticated → authorization fails. `ExceptionTranslationFilter` triggers the “start
  authentication” behavior. For form-login, that means 302 redirect to /login, then we hit GET /login Filter chain runs in
  order: `UsernamePasswordAuthenticationFilter` sees: method is GET, not POST /login → does nothing, passes through
  `DefaultLoginPageGeneratingFilter` sees: this is GET /login and you did not define a custom login page → renders the
  HTML login form

- `Second pass`: Browser submits the form (username/password) to /login. The same exact filter chain runs again in the
  same order again: `UsernamePasswordAuthenticationFilter` sees: this is POST /login → this is my request, It reads
  credentials from the request (by default request parameters username and password, via
  `HttpServletRequest.getParameter(...))`. Builds an Authentication token and calls the `AuthenticationManager`. On
  success, it stores Authentication into the `SecurityContext` (and typically the session). Then it redirects you (often
  back to the original URL, using the saved request)

Stateless login - usually implemented by receiving a special token that contains the full information of the user or
some sort of a bare minimum that will be enough to identify the user, but no state is stored on the server, that relates
to this token, the actual data is stored in the token, or at least a minimal amount of it enough to recover most of the
required data for the user from the internal database storage.

1. DisableEncodeUrlFilter
2. WebAsyncManagerIntegrationFilter
3. SecurityContextHolderFilter
4. HeaderWriterFilter
5. LogoutFilter - often still present but less relevant
6. BearerTokenAuthenticationFilter - extracts/validates Bearer token, builds Authentication
7. SecurityContextHolderAwareRequestFilter - wraps request with security-aware methods
8. ExceptionTranslationFilter - turns “not authenticated” into redirect-to-login or 401
9. AuthorizationFilter - final authorization decision

- `First pass`:

#### Routes

Once we have secured the user login we can also provide a more fine grained control over the routes that users can use
and access, which routes are accessible to which users and which routes are not. We do this using the default security
chain, each route can be fine grain controlled, disallowing not only direct access to it but also allowing only users
with specific authorities to access it
