# Introduction

Spring boot is an open source java based framework used to create micro services, it is developed by Pivotal it is
easy to create a stand alone and production ready spring application, using spring boo. Spring Boot enterprise ready
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
• Jackson Databind for serialization and de-serialization (including JSR 310 support) to and from JSON.
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

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
</dependency>
```

The spring-boot-configuration-processor is an annotation processor that generates metadata for your custom Spring Boot
application properties. It achieves this by processing configuration classes annotated with `@ConfigurationProperties`.

#### Properties ordering

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
that can be found in the `application.yml` file or had been provided from outside when the app was started like so

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

So here is a neat detail about how `@ConfigurationProperties` works, this annotation does not create actual
dependency injection managed bean in the spring context, what it does is simply say - hey this class can be injected
with properties from the environment, and use the fields to do that. There is also the usage of the
`@ConstructorBinding` which tells spring that it should prefer the constructors declared for this bean to create the
instance

Now in order to make spring create instances we have to use either one of these, one is meant to specifically enable a
target class(or multiple classes) as a beans that are the configuration properties, the other is a broad package based
bean scanner, meaning that we can put our properties in a common package and add one `@ConfigurationPropertiesScan`
annotation on top of our class, maybe the same one that contains our `@ComponentScan` for regular beans, and be done with
it, now our `@ConfigurationProperties` (under this package being scanned) annotated classes will be constructed into the
spring context.

- `@EnableConfigurationProperties(MyConfig.class)` - this will enable the properties, but still unless we specify
  the values annotation (like above) to tell spring which classes are eligible for for construction that will not work
  them they wont be created in the context.

- `@ConfigurationPropertiesScan(basePackages = "com.spring.demo")` - this will tell spring where it can find classes
  with the `ConfigurationProperties` annotations.

The `@EnableConfigurationProperties` or the `@ConfigurationPropertiesScan` is usually put onto a `@Configuration`
annotated class, say a class named - `ConfigurationPropertiesEnabler` that is part of our config package in our
application

`Remember just this rule, avoid annotating the Configuration properties class itself with Configuration, that will not
do anything, it will not make spring initialize the properties even if we have ConfigurationProperties onto that class
as well, these are two different types of components which spring treats very differently - Normal Beans injected into
the context, and Configuration properties beans`

#### @ConfigurationPropertiesBinding

Now that we have investigated the basics of the configuration properties, we have to expand on the concept a little bit,
properties in the yaml or properties files are usually very simple types, `strings, numbers, booleans` and an array of
those types. But we might want to convert these into actual java objects. How would we do that ? We have property
converters, that can help us configure this behavior for example create full blown objects like Users from configuration
properties that are plain strings, or combine multiple properties into creating java objects.

```java
@Bean
@ConfigurationPropertiesBinding
Converter<String, GrantedAuthority> grantedAuthorityConverter() {
    return new Converter<String, GrantedAuthority>() {
        @Override
        @Nullable
        public GrantedAuthority convert(String source) {
            return new SimpleGrantedAuthority(source);
        }
    };
}
```

Create a converter for granted authorities, granted authorities is an interface that is used in spring security all
over the place and its main purpose is to allow or disallow certain actions to be performed by the currently
authenticated user. They are not easily or trivially convertible from strings, that is why we need to give spring a
helping hand.

Let us change the `application.yml` file a little bit and create our own section for properties, where we will add our
new custom authority type properties.

```yaml
my-application:
    name: demo-app
    authorities: ["user:manage", "user:list"]
```

Then we change the existing implementation of the `DefaultConfigurationProperties` to take this into account, note that
we have already changed the prefix to be `my-application`, and added the new `authorities` property along in the
constructor and a getter for it.

```java
// note that we have changed the source prefix, we no longer use the default root spring.application, instead we have
// created our own root, under which we can put whatever custom properties we like, also changed the ignore* attributes
// or the annotation, that is to ensure that missing or invalid properties get caught as early as possible since we have
// full control over them we can and should ensure their correctness before they are used in the application
@ConfigurationProperties(prefix = "my-application", ignoreInvalidFields = false, ignoreUnknownFields = false)
public class DefaultConfigurationProperties {

    private final String name;

    private final Collection<GrantedAuthority> authorities;

    @ConstructorBinding
    public DefaultConfigurationProperties(String name, Collection<GrantedAuthority> authorities) {
        this.name = name;
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
```

Thanks to the converter spring context will know how to actually construct a collection instance of these
`GrantedAuthority` based off of our configuration's array of string literals and the provided custom converter. And
below is an example of how the list of granted authorities might look like inspected from the injected bean
`DefaultConfigurationProperties`.

```plaintext
  authorities ArrayList = ArrayList@43 size=2
   0 SimpleGrantedAuthority = SimpleGrantedAuthority@66
     = SimpleGrantedAuthority@66 "user:manage"
   1 SimpleGrantedAuthority = SimpleGrantedAuthority@67
     = SimpleGrantedAuthority@67 "user:list"
```

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
@ConditionalOnProperty(prefix="my.app", name="video", havingValue="youtube")
public YouTubeService youTubeService() {
        return new YouTubeService();
}
@Bean
@ConditionalOnProperty(prefix="my.app", name="video", havingValue="vimeo")
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

#### java.util.logging (JUL)

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

## Spring expression language

The spring expression language is a way to allow certain annotations to execute code at runtime during interaction with
the target of the annotation, such as method invocation, for example. There are some rules and grammar that this
language follows, but it is not a comprehensive scripting language, it is a way to do a quick transformations or
validations

### Core syntax

- `true` / `false` — boolean literals
- `and` / `or` / `not` (also `&&` / `||` / `!`) — boolean operators
- `== != < <= > >=` — comparisons
- `'text'` / `"text"` — string literals
- `+ - * / %` — arithmetic (rare in security rules)
- `(...)` — grouping / precedence
- `a ? b : c` — ternary
- `null` — null literal
- `obj.prop` / `obj.method(...)` — property access / method call
- `T(com.pkg.Type)` — type reference (static access / constants)
- `{1,2,3}` — inline list
- `{'a':'b'}` — inline map
- `arr[0]` / `map['k']` — indexer (arrays/lists/maps)
- `x instanceof T(...)` — type check
- `x matches 'regex'` — regex match (string)

### Method parameters

- `#argName` — method parameter by name (e.g., #id, #user)
- `returnObject` — only in @PostAuthorize (method return value)
- `filterObject` — in @PreFilter/@PostFilter (current element)

### Object References

- `#var` — variable reference (method params, locals, etc.)
- `#root` — the root expression object
- `@beanName` — reference a Spring bean by name
- `@beanName.canEdit(#id)` — call bean method for custom logic

### Security variables

- `authentication` — current Authentication object
- `principal` — shortcut for authentication.principal
- `#root` — method-security root (exposes helper methods below)

### Helper methods

- `hasRole('ADMIN')` — true if user has ROLE_ADMIN (`prefix rules apply`)
- `hasAnyRole('A','B')` — any of the matching roles in the list
- `hasAuthority('perm')` — true if user has exact authority
- `hasAnyAuthority('p1','p2')` — any matching authority in the list
- `hasPermission(target, 'perm')` — delegates to PermissionEvaluator
- `hasPermission(id, 'Type', 'perm')` — PermissionEvaluator w/ type
- `isAuthenticated()` — authenticated (not anonymous)
- `isAnonymous()` — anonymous user

### Discriminators

- `permitAll` — always allow
- `denyAll` — always deny

### Usage

Built in methods expose the ability to do a more broad filtering on roles, authorities or permissions, on a per target
basis, these methods are usually used on the entry points in services or controllers.

```java
// role and authority, the user details in spring provide means of obtaining a list of authorities, these are usually
// prefixed implicitly by ROLE_ but can be customized, just be wary of this
@PreAuthorize("hasRole('ADMIN')")
void adminOnly() {}
@PreAuthorize("hasAnyRole('ADMIN','SUPPORT')")
void adminOrSupport() {}
@PreAuthorize("hasAuthority('video:write')")
void canWriteVideos() {}

@Component
class MyPermissionEvaluator implements PermissionEvaluator {
  @Override
  public boolean hasPermission(Authentication a, Object target, Object perm) { ... }

  @Override
  public boolean hasPermission(Authentication a, Serializable id, String type, Object perm) { ... }
}

// special permission evaluators can be created, these are beans that implement an interface like the one above, to do a
// more detailed check on the permissions a certain principal has over a certain target, the hasPermission is a built in
// method just like hasAuthority, hasRole, but provides a more fine grained control over how we decide if the
// resource/method can be acted upon
@PreAuthorize("hasPermission(#videoId, 'Video', 'write')")
void permissionEvaluatorStyle(Long videoId) {}
```

The context checking built-in methods like checking if we are currently in an authenticated or anonymous context, are
usually a way to separate secured form non-secured resources, when the current resource (api) is not tightly secured by
any security rules .

```java
// helper methods that are exposed by the language, does a broad level checks if we are currently in an authenticated
// context, that is useful to be able to quickly segregate anonymous vs authenticated context
@PreAuthorize("isAuthenticated()")
void anyLoggedInUser() {}
@PreAuthorize("isAnonymous()")
void onlyAnonymousUsers() {}
```

```java
// referencing either the method argument or a bean-by-name from the spring context can also be done using the spring
// expression language, using # or # can invoke methods on the referenced target, access properties etc.
@PreAuthorize("#userId == authentication.name or hasRole('ADMIN')")
void userCanAccessOwnOrAdmin(Long userId) {}
@PreAuthorize("@beanName.canEditVideo(#videoId, authentication)")
void delegateToBean(Long videoId) {}
```

```java
// based on the returned object we can do further authorization, and validation, in case there is something/some other
// action to perform before the return value is delivered
@PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')")
VideoDto getVideo(Long id) { return new UserModel(5, "name"); }

// filter only the videos that have been created by the currently authenticated user, spring will ensure that in the
// list of entities passed to this method the collection is filtered based on the authentication context's name in this
// case only the created by this user entities will actually be updated, these are the ones that are going to match the
// filter
@PreFilter(value = "filterObject.createdBy == authentication.name or hasRole('ADMIN')", filterTarget = "videos")
void bulkUpdateCreator(List<VideoEntity> videoEntities, boolean moreArguments) { }
```

```java
// we saw that above that there is a special #root variable, that represents the root context for the spring root
// expression context, we can re-name a variable for the spell expression evaluator by putting a special @P annotation and
// specifying the new name for the method parameter
@PreAuthorize("@beanName.methodCall(#customVariable)")
void update(@P("customVariable") Root root) { ... }
```

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

### Usage interface

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
X-Product-Name: PremiumCustomer

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

Later on we are going to try to integrate an actual data base provider like Postgres, which requires us to carefully
tailor the configuration properties in our yaml file. There are a few things that we have to change in there to make
this work, but the general idea is the following

```yaml
spring:
    datasource:
        url: jdbc:postgresql://localhost:5432/test
        username: postgres
        password: postgres
        driverClassName: org.postgresql.Driver
    jpa:
        database-platform: org.hibernate.dialect.PostgreSQLDialect
```

The appropriate driver has to be added to our classpath at runtime as well so that Spring and the underlying JDBC can
instantiate it that can be done by adding a dependency such as this:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
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

Here is the actual structure the schema, as you can see each customer has an order, and the order table itself is the
one that holds the reference to the customer, from a data-base schema point of view that is the order that owns the
connection or relationship.

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

The structure of the schema, is represented below, you can notice that just like the CUSTOMER and ORDER example, we have
an owning side, the one that owns the relationship, in this case either one can, but for our purposes we have put that
ownership and in the user entity, here the thing to remember is that each user is connected to exactly one user profile
and vice-versa.

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

    // This annotation tells the ORM that each user can have multiple roles, and that each role itself can be
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

The actual schema we need to construct might look something like that, each user will be tied to a role and vice-versa,
in a separate join table, that will represent that connection pair, remember that both users and roles are independent
entities, a role can be tied or referenced to more than one users, but a single user can generally have more than one
role at a given time, each one representing a sub-set of authorities and actions that can be performed by that user

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

There are many other considerations when it comes to data base design, but for the time being we will only focus on the
relationships

## Security

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

#### Authentication

In spring there is one primary interface called `Authentication` that deals with storing and retrieving data about the
current authentication credentials, the interface itself provides a few core methods that are used cross the internal
implementation of spring-security to represent and hold the name, authorities, and credentials of an authenticated
principal.

The actual current authenticated context can be obtained by using the `SecurityContextHolder`, that is a is a holder for
the Authentication instance and is usually implemented either on a global or per thread level. Use the
`SecurityContextHolder.getContext()` method to obtain that context and then `getAuthentication`, to obtain the current
authentication, that method can return NULL if there is no authentication context, which would imply no authenticated
principal.

Internally the security context holder holds an instance implementation of `SecurityContextHolderStrategy` that is
basically interface that has multiple implementations that handle the different strategies of storing the context, as a
thread-local variable, global-variable:

- `ThreadLocalSecurityContextHolderStrategy` - using thread-local to store the context, each thread has its own context,
  that is the most common one
- `GlobalSecurityContextHolderStrategy` - using a single global context variable, that is shared singleton across
  threads, thread safety not guaranteed

The Authentication interface on the other hand has a few implementations itself, based on the mechanism used to
authenticate the user, but some of the most notable ones are:

- `UsernamePasswordAuthenticationToken` - used when the spring security is configured to authenticated users with
  username and password, during a login form for example
- `AnonymousAuthenticationToken` - when there is no real user authenticated, meaning the resource is not actually
  protected by any authentication
- `RememberMeAuthenticationToken` - obtained when a user has been logged in through the use of a remember me token or a
  cookie, different than direct form login wit username and password credentials

```java
// generally one can use the context and obtain the authentication like so, form this point we should check if the
// authentication instance is valid, and if its not NULL, we can obtain information about the principal
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
```

`What is important to remember is that the Authentication and its implementations most importantly in spring, do NOT
necessarily always represent the final state of authentication, rather it usually represents the way the authentication
was obtained as well, there are, take for example UsernamePasswordAuthenticationToken and RememberMeAuthenticationToken,
both implement Authentication, but both represent different ways the authentication was obtained from the user.`

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

The final structure or the schema will be modified to include the authorities now linked to the role, each authority is
generally going to belong to a single role, because they are mostly unique, authority such as `video:edit` will belong
to a role named `ROLE_VIDEOS` for example and that authority would not belong to another role

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
            return entity.getRole()
                            .getAuthorities()
                            .stream()
                            .map(AuthorityEntity::getGrant)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toUnmodifiableSet());
        }

        @Override
        public String getPassword() {
            return entity.getPassword();
        }

        @Override
        public String getUsername() {
            return entity.getUsername();
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
    UserDetailsService userDetailsService(PrincipalService principalSerivce) {
        return name -> principalSerivce.getPrincipal(name).orElseThrow(() -> new UsernameNotFoundException(name));
    }

    @Bean
    UserDetailsPasswordService userDetailsPasswordService(PrincipalService principalService) {
        return (user, password) -> {
            String username = user.getUsername();
            MutableUserDetails userDetails = new MutableUserDetails(user);
            userDetails.setPassword(password);
            Optional<UserDetails> result = principalService.updatePrincipal(username, userDetails);
            return result.orElseThrow(() -> new UsernameNotFoundException(username));
    };

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

1. `DisableEncodeUrlFilter`
2. `WebAsyncManagerIntegrationFilter`
3. `SecurityContextHolderFilter` - loads/clears `SecurityContext` for the request
4. `HeaderWriterFilter` - add custom headers to the current response towards the client
5. `CorsFilter` - handles the CORS pre-flight requests and intercept actual request
6. `CsrfFilter` - handles reading the CSRF token from the incoming user request
7. `LogoutFilter` - handles /logout sequence and events propagation throughout the filter chain
8. `UsernamePasswordAuthenticationFilter` - pulls the data from the POST to /login, from the login page
9. `DefaultLoginPageGeneratingFilter` - renders the default login page if you did not provide one
10. `DefaultLogoutPageGeneratingFilter` - renders the default logout page if needed, i.e, hit /logout route
11. `RequestCacheAwareFilter` - remembers original URL for redirect after login
12. `SecurityContextHolderAwareRequestFilter` - wraps request with security-aware methods
13. `ExceptionTranslationFilter` - turns “not authenticated” into redirect-to-login or 401
14. `AuthorizationFilter` - final authorization decision, additional customizations

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

1. `DisableEncodeUrlFilter`
2. `WebAsyncManagerIntegrationFilter`
3. `SecurityContextHolderFilter`
4. `HeaderWriterFilter`
5. `LogoutFilter` - often still present but less relevant
6. `BearerTokenAuthenticationFilter` - extracts/validates Bearer token, builds Authentication
7. `SecurityContextHolderAwareRequestFilter` - wraps request with security-aware methods
8. `ExceptionTranslationFilter` - turns “not authenticated” into redirect-to-login or 401
9. `AuthorizationFilter` - final authorization decision

- `First pass`: TODO

#### Routes

Once we have secured the user login we can also provide a more fine grained control over the routes that users can use
and access, which routes are accessible to which users and which routes are not. We do this using the default security
chain, each route can be fine grain controlled, disallowing not only direct access to it but also allowing only users
with specific authorities to access it.

What spring does is allow us to attach different security rules on the requests such that we can enable or disable
access to certain rules or routes dynamically. What is also possible is to create multiple instances or Beans of the
`SecurityFilter`. However it is important to note that we need to order them correctly, because it might cause conflicts
of matching request matchers.

Each security filter chain is built from a `HttpSecurity`, for each we can configure what routes to ignore, accept allow
or authenticate, we can configure the type of login, either normal stateful or stateless one and so on and so forth.

```java
// this is the default web security chain that spring configures, out of the box nothing special just marks all
// resources as authenticated, and ensures that we can login by enabling both the basic form login that each browser
// supports and the advanced UI based html form login that spring also exposes through the filters we have already looked
// at above, either way, the default config is quite bare bones
@Bean
@Order(SecurityProperties.BASIC_AUTH_ORDER)
SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
    http.formLogin(withDefaults());
    http.httpBasic(withDefaults());
    return http.build();
}
```

We are going to attempt to override this config for us to make a more advanced one, in our configuration so far we have
enabled the `H2` in memory database console, we have enabled a few RESTful api and also we have a template engine running
a small UI front end rendered on the server. Each of which requires a different set of constrains, mainly connected to
how we authenticate the user, so below we attempt to customize just this

- The `/h2-consle` should be accessible from anyone, it does not require an authentication step because it already
  provides its own internal way to login and authorize and authenticate people.
- The `/ui` routes are going to use the default DAO provider that spring uses by default to pull users from our repository
  and log them in by username and password
- The `/api` routes we will authorize and authenticate with basic HTTP based header authorization and authentication

```java
@Order(1)
@Bean(name = "h2SecurityFilterChain")
SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {
    // first we configure the h2-console route we know that it has a default login mechanics, so there is no need to add
    // a second one on top, we will not require any user login by default from spring and leave h2's login functionality to
    // work as expected here
    return http.securityMatcher("/h2-console", "/h2-console/**")
        .authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll())
        .formLogin(FormLoginConfigurer<HttpSecurity>::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .build();
}

@Order(2)
@Bean(name = "apiSecurityFilterChain")
SecurityFilterChain apiSecurityFilterChain(HttpSecurity http, @Qualifier("apiUserDetailsService") UserDetailsService apiUserDetailsService,
    @Qualifier("apiUserDetailsPasswordService") UserDetailsPasswordService apiUserDetailsPasswordService,
    PasswordEncoder passwordEncoder) throws Exception {
    // we create a custom authentication provider because we would like to customize it with a special service that
    // provides only specific sub-set of the users that can actually call our API layer, plain password encoder that
    // does nothing more than just compare the raw passwords, no encoding is done for the moment as this is for educational
    // purposes
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(apiUserDetailsService);
    daoAuthenticationProvider.setUserDetailsPasswordService(apiUserDetailsPasswordService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

    // our api is authenticated completely with for now basic HTTP authorization that contains the username and password
    // in the header using the Authorization basic HTTP functionality, we have to provide a header with a base64 encoded
    // value representing the user and password in the format Basic base64(username:password) when we call the http/s
    // request such as, the example below which translates to Basic admin:admin123, a user that exists in our database

    // GET http://localhost:8080/api/list?page=0&size=10
    // Authorization: Basic YWRtaW46YWRtaW4xMjM=
    return http.securityMatcher("/api/**")
        .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
        .authenticationProvider(daoAuthenticationProvider)
        .formLogin(FormLoginConfigurer<HttpSecurity>::disable)
        .httpBasic(Customizer.withDefaults())
        .build();
}

@Order(3)
@Bean(name = "uiSecurityFilterChain")
SecurityFilterChain uiSecurityFilterChain(HttpSecurity http, @Qualifier("uiUserDetailsService") UserDetailsService uiUserDetailsService,
    @Qualifier("uiUserDetailsPasswordService") UserDetailsPasswordService uiUserDetailsPasswordService,
    PasswordEncoder passwordEncoder) throws Exception {

    // we create a custom authentication provider because we would like to customize it with a special service that
    // provides only specific sub-set of the users that can actually login in our ui layer, also we provide in this case a
    // plain password encoder that does nothing more than just compare the raw passwords, no encoding is done for the
    // moment as this is for educational purposes
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(uiUserDetailsService);
    daoAuthenticationProvider.setUserDetailsPasswordService(uiUserDetailsPasswordService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

    // we create a custom login filter, this filter basically is in charge of creating the login page, since by default
    // it is under /login we would like to generate it under /ui/login we need a custom filter to tell it to use the new
    // URL for login. The important part here is the authenticationUrl and the loginPageUrl, the first tells the form POST
    // action where to post to, the second tells the actual URL of the login page
    DefaultLoginPageGeneratingFilter defaultLoginPageGeneratingFilter = new DefaultLoginPageGeneratingFilter();
    defaultLoginPageGeneratingFilter.setLogoutSuccessUrl("/ui/logout");
    defaultLoginPageGeneratingFilter.setLoginSuccessUrl("/ui");
    defaultLoginPageGeneratingFilter.setAuthenticationUrl("/ui/login");
    defaultLoginPageGeneratingFilter.setFailureUrl("/ui/login?error");
    defaultLoginPageGeneratingFilter.setLoginPageUrl("/ui/login");

    // these are mandatory and are not set by default we use just these values, because the
    // `UsernamePasswordAuthenticationFilter` expected these by default, can be customized but we avoid adding more code
    defaultLoginPageGeneratingFilter.setUsernameParameter("username");
    defaultLoginPageGeneratingFilter.setPasswordParameter("password");
    defaultLoginPageGeneratingFilter.setFormLoginEnabled(true);

    // we configure a new security matcher that is going to be matching on our ui layer, only, that is important only
    // these requests will trigger this entire security chain that we configured below, and along side that the custom
    // login functionality as well.
    return http.securityMatcher("/ui/**")
    .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
    .authenticationProvider(daoAuthenticationProvider)
    .httpBasic(AbstractHttpConfigurer::disable)
    .cors(CorsConfigurer<HttpSecurity>::disable)
    .csrf(CsrfConfigurer<HttpSecurity>::disable)
    .formLogin(customizer -> {
        // this is where the magic happens we tell the form login filter, the original one, to redirect to /ui/login
        // that will trigger our custom filter that we have created and attached below, on top of that we also need to tell
        // it where to redirect to on success that would be /ui, where our base ui layer is. Finally the login processing
        // url is what the `UsernamePasswordAuthenticationFilter` is going to filter on and catch, see by default that is
        // /login, now we tell it to extract the username and password parameters from /ui/login.
        customizer.loginPage("/ui/login");
        customizer.defaultSuccessUrl("/ui/");
        customizer.loginProcessingUrl("/ui/login");
    })
    .addFilterBefore(defaultLoginPageGeneratingFilter, UsernamePasswordAuthenticationFilter.class)
    .build();
}
```

Once we provide our custom filters, the default security filter chain that spring builds will be disabled and no longer
active, our chain will be, we have created 3 separate chains that match on separate routes, `/api, /ui and /h2-console,`
they are not related but sibling routes, which makes it easy to avoid conflicts, and its generally accepted that this is
followed, the moment we start messing with different security matcher instances for related child-parent routes that
might become too hard to manage, therefore the accepted idea is to configure related routes in the same method.

#### CORS

Cross origin resource, is a way to 'configure' the single origin policy of the web browser, this is a way to tell the
browser or the client which parts of the request can be sent and which pars of the response can be read from a specific
ORIGIN/web-site that has made the request on the behalf of the client/browser. So imagine this following scenario where,
your browser executes code from two domains `www.a.com` and `www.b.com`, the SERVER can configure the CORS policy to
tell the browser to only allow reading of responses by `www.a.com`.

- Access-Control-Allow-Methods - controls which types of methods actions ORIGIN can make (POST, GET, DELETE)
- Access-Control-Allow-Headers - these are request headers you want to send by the ORIGIN
- Access-Control-Allow-Origin - controls whether the response is readable by which ORIGIN
- Access-Control-Expose-Headers - controls which response headers are readable by the ORIGIN

It is important to note and remember that the browser is like a huge sandbox, each ORIGIN/web-site is executing in is
own sandbox, isolated form the rest of the ORIGINS they do not know of each other and there is no easy way to leak
information or state between the origins, that is what makes the browser a powerful mediator, that is what allows CORS
to be enforced by the browser, it knows which ORIGIN (which web-site's script) can read the response and if it is not
allowed it will be blocked. You have no doubt seen console errors in your browser regarding CORS violations. That is
exactly that the ORIGIN making the request is not allowed to read the response, headers, credentials and the browser
directly errors out directly when the restricted origin attempts to obtain the response from the server.

There is also a policy that might be enforced by the server, and not by the browser that actually restricts the REQUESTS
made by the web-site/ORIGIN, that is a way by the server to add additional security by checking the `Origin` header of the
incoming `request that is set by the browser`, there is no way for an ORIGIN to forge that because it is enforced by the
browser before the request is sent, to the SERVER. The server verifies this header is among the allowed ones, that way
no bad ORIGIN actor can even make the request in the first place, stopping it in its tracks.

#### Cookies

Cookies are a special type of header that is mostly relevant to browser, these are headers that the browser mostly works
with, they were the early ways to store state for the user's session on the ORIGIN (web-site) the cookies themselves are
normal headers, BUT the browser restricts the scripts that are executing from directly accessing them unless the cookies
are specially enabled to be accessed. That is because first by default SERVER provide cookies contain no readable
information, that is relevant to the script, second it might posed security issues. There is a way for a sever to set a
cookie to be readable by the browser and that is how CSRF Tokens are usually transmitted.

There are also local cookies which the web-site executing its script can save to remember certain local state for the
user, and maybe send it to the server, but these are not tied to any security or authorization / authentication
mechanisms.

#### CSRF

One attach vector that many users or clients often find themselves captured by are phishing attacks, when a user is
redirected to a malicious site or web platform that seems to look exactly like the real thing, they can be tricked into
using it to authenticate against it, one way spring guards against that is with what is known as cross site resource
forgery tokens, these are pseudo random numbers or text that is also sent with the request by the client and is tied to
the user's session once bound to the session upon creation this token must be also always sent otherwise the server will
deny any new connections and kill the existing session for the client. When a malicious site attempts to trick the user
to send its data

So how does it work in actual practice, a malicious party creates a web-site or platform that visually and possibly
functionally resembles the target vulnerable site. The actions that the malicious site performs are targeting the actual
vulnerable server as well, so far so good, you might think there is no way then to do any harm. However that is wrong.
Because your browser sends the cookie data to the target vulnerable host every time a request is made. What happens is
that the malicious actor will trick the user into performing actions on his behalf, for example when the user enters
some input into a form instead of calling a harmless action on the vulnerable server the attacker will change that
action to do something more dangerous such as making a payment or changing the user's password. Since the browser
automatically sends the cookies to the vulnerable site the vulnerable site will have no way of knowing that there was a
man in the middle attack that used the user's actions to perform a dangerous action instead.

How does a cross site forgery token prevent this ? There are a few concepts and we need to know first in the modern web
clients such as browsers, first lets fix in place some terms

- `USER` - that is the acting party, usually a human, but it can be anything, that uses the CLIENT, to perform actions on
  the ORIGIN, which in turn has code to allow interacting with the SERVER

- `CLIENT` - that is the browser we are running, it is the execution environment of the code that the origin provides,
  the browser downloads the ORIGIN's code and executes in on our machine, rendering the HTML, executing the JavaScript
  etc. Browsers run the code of each ORIGIN in a sandbox effectively isolating them from each other, allowing for safe
  execution of multiple web-sites, the browser acts like a mediator and a manager for all the ORIGINS we have currently
  executing or working with.

- `ORIGIN` - that is the source or host of the originating request, that is the web-site which we are running on our
  client/browser, that code lives on some server, but our browser has to download it locally to execute it on our
  machine, parsing HTML, executing javascript etc.

- `SERVER` - the server that requests are made towards, sometimes the origin and the server MIGHT share the same domain,
  sometimes that is not the case, and when that is not the case is what we will explore, having the same domain is pretty
  much a done deal, it is considered safe.

- `Request/Response` - that is the round trip that your client takes to communicate with the SERVER, every time the client
  executes code that needs information from the SERVER, we make a request and we get a response, there are simple ways
  that your client makes requests, such as FORM actions, and there are complex ones where scripts take charge of using the
  browser's fetch api to do the same, allowing for more complex requests.

- `Cookies` - cookies are special types of headers with unique values that are used to identify the user's session on the
  server application, it is sent by by the server as a header when the user's session is established or created on the
  server., and by default the browser remembers that cookies and sends it back every time we make a request to the server

1. `Session credentials` - By default browsers send all the cookies they receive to the same SERVER that has send them,
   this has been the default behavior for many years now and that is one of the main attack vectors for malicious
   actors. What does it mean that if we have two ORIGIN called `www.good.com` and `www.bad.com`, which do a /POST action to
   SERVER `www.myapi.com`, will both cause the browser to send the cookies sent by the `www.myapi.com`, regardless of the fact
   that the actual origin that first received the cookies was likely the `www.good.com`, that way the bad actor can take
   advantage of us if we ever visit the `www.bad.com` and use that ORIGIN instead to do certain sensitive actions. This is
   behavior specific to browser based web clients, that is not the case for other web clients such as CLI for example like
   curl, wget etc - which have no understanding of cookies.

2. `Simple vs complex requests` - simple requests are such that they are created without any custom control over the API
   of the browser by a script or a 3rd party, these are for example the form POST actions. Complex requests are these
   that are made by scripts or 3rd party actors on your behalf on your browser using the browser's fetch api.

3. `Single origin policy` - that policy is something that the browser enforces, when we deal with complex requests, that
   is because they (like the simple request) can be cross site, the idea of SOP is that the browser (if it detects cross
   origin/site request will not allow javascript or custom scripts running in the browser to READ the response of this
   request, while requests can be made freely, reading the response is restricted by the Browser itself. That is if we have
   a web site running on `www.site.com`, and a backend on `www.myapi.com`, the `www.site.com` by default will never be able to
   read ANY of the responses that `www.myapi.com` sends unless we have configured CORS on the `www.myapi.com` server to allow
   this origin - `www.site.com`

4. `CORS` - Cross origin resource, is a way to 'configure' the single origin policy of the web browser, we can configure
   these policies on the server and remove some of the restrictions that the SOP enforces by default. This is a way for
   the SERVER to tell the client/browser that it is okay for certain ORIGINS (read web sites, that the CLIENT runs /
   executes their javascript) to read the response, read certain headers, authorities (like cookies) and so on
    - Access-Control-Allow-Methods - controls which types of methods actions ORIGIN can make (POST, GET, DELETE)
    - Access-Control-Allow-Headers - these are request headers you want to send by the ORIGIN
    - Access-Control-Allow-Origin - controls whether the response is readable by which ORIGIN
    - Access-Control-Expose-Headers - controls which response headers are readable by the ORIGIN

5. `Preflight request` - every time the browser makes a request towards a SERVER, a preliminary request is made, and it
   is called `a pre-flight OPTIONS` request, that is OPTIONS request towards the server to obtain information about what
   the Access-Control-\* policies are, the browser asks and the server responds with - Allow-Credentials,
   Access-Control-allow-Origin/Method/Headers. That is the way the SERVER can say which headers are allowed to be read by
   the client, what request methods are supported, and which ORIGINS (in other words which front-end web sites) are allowed
   to read the response of the requests being sent.

Now that we have fixed a few of the primary terms, how does the CSRF Token prevent all of that, first the token is a way
for the server to send a unique token to the CLIENT when the user session is first created, usually that happens the
first time the session is created for the user or every time the session is refreshed/the token expired or invalidated.
There are certain implementations that might send a new token on each response from the server. That is mostly
irrelevant, what the key point here is that besides the cookies the SERVER also sends an additional header that contains
that special unique token

Here is where the magic happens. We already saw that the browser has something called SOP - single origin policy, with
that alone we will not be able to really take advantage of calling 3rd party API or have our ORIGIN and SERVER have
different domains. We also saw that there is something called CORS that allows the `server to lax the restrictions` or
control the SOP that the browser enforces.

So what the server does (www.myapi.com) is configure the safe ORIGINS (www.good.com) that can make the requests, but
most importantly what matters here is that what this CORS enables is `to tell the browser that this ORIGIN can read the
response from this SERVEr and also which headers EXACT headers it can read`. You should be able to see that this is the
KEY here, the server tells the ORIGIN (www.good.com) can read my special X-CSRF-TOKEN header that contains the token,
then it can be read from the response, that the good origin made, and saved in the browser in a place where only scripts
executing on that good ORIGIN can access it.

Now what happens is that every time the good ORIGIN (www.good.com), makes a request to the SERVER (www.myapi.com) it
also sends this special token as a header, the server verifies that this is the correct origin sending it the request
and reads the token from the header along side the automatically sent cookie, in this case the cookies is just used to
identify the user, the actual authorization and security is enforced primary by the CSRF token

You should now see that since the CORS policy dictates which ORIGIN can READ which HEADERS, only the good origin web
site's (www.good.com) scripts can send, the bad origin (www.bad.com) can not, it will fail on the Preflight request, the
browser will see that this origin does not have permissions or is not known to the server. And therefore even if it
manages to make a request it will never be able to read a response from the server.

Note that the biggest enemy of the CSRF token approach is what is called a `XSS`, cross site scripting, that basically
would allow an attacker to tap into the sandbox of the good ORIGIN, and read the token for free, and send the token on
the behalf of the user. The way to handle these things here is to have ORIGIN validation on the SERVER. That is to block
requests, (different than CORS) that originate from an unknown party that is not in our white list.

```java
CorsConfiguration configurationSource = new CorsConfiguration();
// relates to setting data in the request
configurationSource.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
configurationSource.setAllowedHeaders(List.of("Content-Type", "X-XSRF-TOKEN", "Authorization"));
// relates to accessing the data in the response
configurationSource.setAllowedOrigins(List.of("http://www.localhost:8080"));
configurationSource.setExposedHeaders(List.of("Content-Type"));
configurationSource.setAllowCredentials(true);

UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
source.registerCorsConfiguration("/ui/**", configurationSource);

return http.securityMatcher("/ui/**")
    .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
    .httpBasic(AbstractHttpConfigurer::disable)
    // configure the CORS, we can see above the important part that specifies our ORIGIN, in this case we assume localhost,
    // but that is just because we are doing all of this locally. The rest of the methods are which are allowed, to be
    // read for, and the headers that we can read from the response - note that we enable the reading of X-XSRF-TOKEN
    .cors(config -> config.configurationSource(source))
    // we configure a X-CSRF token repository that will, put the token into a cookie that is readable by the script,
    // that way the good origin can pull the token and use it and send it on subsequent requests to the server.
    .csrf(config -> config.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
    .formLogin(customizer -> {
        customizer.loginPage("/ui/login");
        customizer.defaultSuccessUrl("/ui/");
        customizer.loginProcessingUrl("/ui/login");
    })
    .addFilterBefore(defaultLoginPageGeneratingFilter, UsernamePasswordAuthenticationFilter.class)
    .build();
```

So what is going on above, is that we have configured that only our own domain in this case localhost, (not very useful
at the moment) can make requests and read responses, remember this is a policy that is enforced by the client/browser
not the server. The browser checks if the ORIGIN/web site compiles with this CORS policy that the SERVER
declares/defines but the browser enforces.

Here we say that the good origin (localhost) can do most of the HTTP methods, and can set the following headers, from
the response, we have set expose headers (these that can be read from the request) to only Content-Type, for the
purposes of showing this example

Now that the CORS policy is configured, your application can read the cookie and the token, and then send that token as
a dedicated named header on every request it makes. Since we know that only this good origin can make requests in the
first place (otherwise the browser would block them) we know that only this good origin has received the cookie and in
turn it has read the token. Further more on subsequent requests only that good origin is allowed to set that
`X-XSRF-TOKEN` header, where we put the token value that we have pulled from the cookie.
To actually use the CSRF we can enable that by leveraging the `_csrf` parameter that one is set by the
`CookieCsrfTokenRepository`, by default we can pull the value for it from the response and embed it back into the form
so it can be read from the CSRF filter, the default name parameter or `_csrf` can be changed of course

```html
<!-- we have embedded the token as a hidden field in the request body, when the request is received the CsrfFilter will
attempt to read the value of the token from the body (in _csrf) and compare it to the initially generated token. -->
<form action="/ui/delete-video/{{ id }}" method="post" class="inline" style="margin-top: 10px">
    <input type="hidden" name="{{ _csrf.parameterName }}" value="{{ _csrf.token }}" />
    <button class="btn-danger" type="submit">Delete</button>
</form>
```

Also expose the values form the request parameters that are set by spring to the template engine, by default they
are hidden, and are not exposed but are present i.e spring actually sets them.

```yaml
# by default what spring does is when it generates the server-content from the template, it would process/check for
# form fields and would dynamically insert the request attributes that are relevant like _csrf
spring:
    mustache:
        servlet:
            expose-session-attributes: true
            expose-request-attributes: true
```

The actual work is being done in the `CsrfFilter`, where the token is generated or created and validated against the one
contained in the request arguments, in essence what happens is that both the request parameters in the body or the value
in the header are read for the value we expect.

```java
// this is heavily abridged version of the original implementation to demonstrate where or how the token is extracted
// from the request payload, and that both the header and the body of the request are inspected for this special value by a
// known unique name that can identify it, the _csrf is used to avoid conflicts and collisions with other body elements
// that might occur, due to the naming
String getTokenFromRequest(HttpServletRequest request) {
    // By default that is the header value of the header X-XSRF-TOKEN
    String actualToken = request.getHeader("X-XSRF-TOKEN");
    if (actualToken != null) {
        return actualToken;
    }
    // By default that is the request body parameter value of _csrf
    actualToken = request.getParameter("_csrf");
    if (actualToken != null) {
        return actualToken;
    }
}

// get the currently generated token and the one from the current request, and compare them, only if they match we can
// actually proceed with the execution of the request and chain
CsrfToken csrfToken = deferredCsrfToken.get();
String actualToken = getTokenFromRequest(request);
boolean matches = csrfToken.getToken().equals(actualToken);
```

We have to modify the login page to enable the \_csrf token that is just a hidden input in the login form that is a
hidden field, that is bound to the value of the \_csrf and we can use it to. Since the login page is also \_csrf enabled
due to it being under our filter, we need to ensure that the cooki

```java
private Map<String, String> hiddenInputs(HttpServletRequest request) {
    CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    return (token != null) ? Collections.singletonMap(token.getParameterName(), token.getToken())
    : Collections.emptyMap();
}

// this is important since we create our own instance of the page generating filter, we ensure that the hidden
// fields are embedded in that field. This is just like we did with mustache, but here we have to do it manually since
// that page is generated and delivered on the fly by spring, and is not tied to the templating engine.
defaultLoginPageGeneratingFilter.setResolveHiddenInputs(this::hiddenInputs);
```

This is how one of the forms looks like now, after we have inserted the hidden field we have a name for the field
that is important because that is the parameter name based on which we read the token value , and a value of course

```html
<form action="/ui/delete-video/1" method="post" class="inline" style="margin-top: 10px">
    <input type="hidden" name="_csrf" value="D2snrz-_FHLsKwcaY74OviUxGYSKlajr9-NCOp0ik2YW4l" />
    <button class="btn-danger" type="submit">Delete</button>
</form>
```

#### Authorities

Now that we have some of the general security guardrails in place we can move forward to implementing a more fine
grained control policy. We already have in place authorities that each user is assigned to. We can restrict certain
actions for certain users, actions like delete for example should be allowed only by specific authorities. We can do
that by assigning authority filters/annotations on controller/service/repository methods

`ROLE vs AUTHORITY` - first let us establish the difference, the role usually specifies some sort of a broad use case that
has big implications over a large set of resources, such as ROLE_USER, ROLE_ADMIN, ROLE_MANAGER, roles usually provide a
way to specify a wide set of permissions with few specifics as possible. Authority on the other hand in the context of
most applications implies a finer grained control over resources, they might be resource or action based like
video:delete, or delete:video, these allow us to have more control over more specific types of resources and actions.
These are mere conventions that have been established in the space but are good practices to follow.

`This is a mere convention or a guide line, usually systems implement one or the other, sometimes both, but to spring we
expose just one set of these not both under getAuthorities. For example a system might have roles which are set of
authorities, when a user has more than one role he inherits the authorities for each of the role that he has been
assigned, these are what we expos to spring, and not the roles themselves, which serve as containers or holders`

##### `@EnableMethodSecurity`

This annotation will ensure that we enable the security annotations that Spring exposes to the user, there are a few,
some of which are more sophisticated and some are pretty bare bones, each of which is useful for different tasks and
purposes. That would enable the use of `@Secured, @Pre|PostAuthorize annotations and more`

##### `@[Pre|Post]Authorize`

This annotation allows us to execute SPELL, spring's special language that is interpreted at runtime when a method that
is annotated with this annotation is invoked, that happens here is that the method contains a string that is the
expression itself usually, that expression is evaluated against the method's arguments and also provides a way to access
spring internal state context such as singleton beans and more.

```java
// here is an example of how we can secure the invocations of this method to ensure that only the user that is
// currently logged in can delete his own user
@PreAuthorize("#username = authentication.name")
void deleteByUsername(String username);

// using role based permission, allowing only users which posses the admin based roles, to delete users by a target
// username
@PreAuthorize("hasRole('ADMIN')")
void deleteByUsername(String username);

// using an authority resource action based permission where only users which can act on this resource (user) and do
// this certain action can delete users
@PreAuthorize("hasAuthority('user:delete')")
void deleteByUsername(String username);
```

Using `hasRole` & `hasAuthority`

There is one detour we have to make because spring has introduced over time conflicting annotations which work with
authorities, and these can be confusing, both of the `hasRole` and `hasAuthority` annotations work with the
`getAuthorities` method of the `UserDetails` authentication context, however the way they interpret the values is a bit
different. When using the role annotation spring assumes that this list of authorities contains role based permissions
that is why by default it expects to find values that contain the ROLE\* prefix in here, and when using `hasAuthority`
it assumes the value is usually matched as is, no assumptions made, the general format for roles is `ROLE_{NAME}`, and
for authority `action:resource`or`resource:action`. There are no hard core rules that enforce this but this is just the
accepted convention used across most applications. These methods have an alternative multi-argument variant which is
just following the same naming convention - `hasAnyRole` and `hasAnyAuthority`

`hasRole vs hasAuthority are two different annotations that use the same source of information to determine if the
authentication context matches the condition , both are using the getAuthorities() which returns a list of
GrantedAuthority, from Authentication and the main difference is that hasRole checks the value by always pre-pending
special prefix ROLE_ by default while the hasAuthority checks the raw values in the granted authorities and the ones
specified in the annotation without any modification`

Let us assume that a user has the following granted authorities `["ROLE_ADMIN", "write:video"]`, we can match for these
using either the `hasAuthority` or the `hasRole` like so:

`hasRole("ADMIN")` → checks for authority in the list matching "ROLE_ADMIN", prefix the annotation value checks for ROLE_ADMIN
`hasRole("ROLE_ADMIN")` → checks for authority in the list matching "ROLE_ADMIN", does not double prefix the annotation value

`hasAuthority("write:video")` → checks for the value as is in the list of the authorities
`hasAuthority("ADMIN")` → checks for authority "ADMIN" (argument used as-is) - not present
`hasAuthority("ROLE_ADMIN")` → checks for authority "ROLE_ADMIN" (argument used as-is) -present

##### `@Secured`

This is the older legacy annotation that mostly supports role level access, basically the list of `getAuthorities` is
examined for a role that is specified in the secured annotation and if that role is present the method is allowed,
this is an annotation that does not provide as much flexibility and is not that great for modern use but can be used
to do a wide discrimination and exclusion on the authentication context and then use the `PreAuthorize` to do w more
fine grained filter.

`The Secured annotation checks value AS IS, there is no special conversion or assumption for the values that are in the
getAuthorities like there is for when using the hasRole in a spring expression language enabled annotation`

```java
// we have provided multiple authorities that would imply that any one will allow this method to execute, that is not AND
// condition but an OR condition that links these roles together here, deciding which authentication context can call
// this method
@Secured({ "ROLE_USER", "ROLE_ADMIN" })
void deleteByUsername(String username);
@Secured({ "user:delete", "user:manage" })
void deleteByUsername(String username);
```

So below are a few changes that we can make to our template controller, that exposes the UI for interacting with the
entities that we have in our project, one of which is the User, we have also added few security annotations to ensure
that only specific types of users can inspect all users or change user passwords. We have also added the ability for the
user currently authenticated himself to edit or update his password by re-using the `/ui/user` path, we can see that if
no `PathVariable` for the id of the user is provided, then the currently authenticated context is used to extract the
user's details and present them back.

```java
@Controller
@RequestMapping("/ui")
public class TemplateController {

    private static final String TEMPLATE_USER_BASE = "user";
    private static final String TEMPLATE_USERS_BASE = "users";

    private static final String REDIRECT_TEMPLATE_INDEX = "redirect:/ui/";

    private final UserService userService;
    private final PrincipalService principalService;

    @GetMapping(path = "/users")
    @PreAuthorize("hasAuthority('user:manage') or hasAuthority('user:list')")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return TEMPLATE_USERS_BASE;
    }

    @GetMapping(path = {"/user", "/user/{id}"})
    @PreAuthorize("#target == null or hasAuthority('user:manage') or hasAuthority('user:list')")
    public String getUser(@PathVariable(name = "id", required = false) Long target, Model model) {
        if (!Objects.isNull(target)) {
            model.addAttribute("user", userService.get(target));
        } else {
            UserDetails details = principalService.getPrincipal().orElseThrow();
            model.addAttribute("user", userService.findByUsername(details.getUsername()).orElseThrow());
        }
        return TEMPLATE_USER_BASE;
    }

    @PreAuthorize("#user.username == authentication.name or hasAuthority('user:manage')")
    @PostMapping(path = "/update-user", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String editUser(@ModelAttribute UserModel user) {
        userService.update(user.id(), user);
        return REDIRECT_TEMPLATE_INDEX + TEMPLATE_USERS_BASE;
    }

    @PreAuthorize("hasAuthority('user:manage')")
    @PostMapping(path = "/delete-user/{id}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String deleteUser(@PathVariable("id") Long target) {
        userService.delete(target);
        return REDIRECT_TEMPLATE_INDEX + TEMPLATE_USERS_BASE;
    }
}
```

Conversely we will also sync our `RestfulController` as well with the changes in the `TemplateController`, here is the
full version of the restful controller that is made/adapted to replicate what the `TemplateController` now does

```java
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestfulController {

    private final VideoService videoService;
    private final RoleService roleService;
    private final UserService userService;
    private final PrincipalService principalService;
    private final AuthorityService authorityService;

    public RestfulController(VideoService videoService, RoleService roleService, UserService userService, PrincipalService principalService,
                    AuthorityService authorityService) {
        this.videoService = videoService;
        this.roleService = roleService;
        this.userService = userService;
        this.principalService = principalService;
        this.authorityService = authorityService;
    }

    @GetMapping(path = "/videos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<VideoModel>> getVideos(@RequestParam(defaultValue = "1") int page,
                    @RequestParam(defaultValue = "10") int size) {
        final int pageNumber = Math.max(page, 1);
        final int pageSize = Math.min(Math.max(size, 1), 100);
        final Sort sortCriteria = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sortCriteria);
        Page<VideoModel> videosPage = videoService.findAll(pageable);

        return ResponseEntity.ok(videosPage);
    }

    @GetMapping(path = "/find-videos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<VideoModel>> findVideos(@RequestParam Map<String, String> criteria) {
        return ResponseEntity.ok(videoService.search(SearchCriteria.of(criteria), VideoModel.VideoCriteria.values()));
    }

    @PostMapping(path = "/new-video", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VideoModel> addVideo(@RequestBody VideoModel video) {
        return ResponseEntity.ok(videoService.create(video));
    }

    @PostMapping(path = "/update-video", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VideoModel> updateVideo(@RequestBody VideoModel video) {
        return ResponseEntity.ok(videoService.update(video.id(), video));
    }

    @PostMapping(path = "/delete-video/{id}")
    public ResponseEntity<Object> deleteVideo(@PathVariable("id") Long target) {
        return ResponseEntity.ok(videoService.delete(target));
    }

    @GetMapping(path = "/users")
    @PreAuthorize("hasAuthority('user:manage') or hasAuthority('user:list')")
    public ResponseEntity<List<UserModel>> getUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(path = {"/user", "/user/{id}"})
    @PreAuthorize("#target == null or hasAuthority('user:manage') or hasAuthority('user:list')")
    public ResponseEntity<UserModel> getUser(@PathVariable(name = "id", required = false) Long target) {
        if (!Objects.isNull(target)) {
            return ResponseEntity.ok(userService.get(target));
        } else {
            UserDetails details = principalService.getPrincipal().orElseThrow();
            return ResponseEntity.ok(userService.findByUsername(details.getUsername()).orElseThrow());
        }
    }

    @PreAuthorize("#user.username == authentication.name or hasAuthority('user:manage')")
    @PostMapping(path = "/update-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> editUser(@RequestBody UserModel user) {
        return ResponseEntity.ok(userService.update(user.id(), user));
    }

    @PreAuthorize("hasAuthority('user:manage')")
    @PostMapping(path = "/delete-user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long target) {
        return ResponseEntity.ok(userService.delete(target));
    }
}
```

These changes we are going to need with the next chapter where we will start performing tests, the chapter will
ensure to walk us through the different ways we can test these controllers, and all of the dependent components they
rely on.

#### Oauth2

TODO:

## Testing

Just as any other starter in spring, spring provides one for testing in here we have quite a few dependencies
provided that would enable us to do fast test iteration.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

Adding this dependency will pull dependencies in your class path like:

- Spring Test - spring boot oriented test utilities
- JUnit 5 - library for writing the actual tests
- JSONPath - query language for json documents
- AssertJ - fluent api for asserting results
- Hamcrest - library of matchers
- Mockito - mocking and building test cases faster
- JsonAssert - helps with doing JSON assertions
- XMLunit - assert and verify xml documents

Testing usually means that we have to test the entire public API of the project we are working with, sometimes even
the private api might be subject to testing, to ensure the correctness of the program. This happens by asserting
state that is expected, that state can be checking the result of methods, checking if a given method is invoked, or
even verifying that an exception is thrown, that is right we have to verify as much of the execution of the unit we
are testing as possible that includes all possible edge cases aside from the plain obvious straight cases.

### Testing patterns

Okay first before we begin with testing different components we have to clear a few things since, spring is quite
complex beast there are different ways to test components in the spring environment. We know that spring is a huge
container of dependency injected state, that is created even when we start tests. That is most of the time not only
an overkill but also can cause great confusion and does not provide the isolation that we need for tests. What we
need to do is to have a way to control the spring context.

#### Simple context

Use this when you’re testing domain objects, mapping logic, validation rules you wrote yourself, and service logic that
doesn’t require Spring features like transactions, AOP proxies, or auto-wiring. These tests don’t create an
`ApplicationContext`, so there is nothing to “fail to load”.

```java
// most notable here is the fact that we do not annotate this with any annotation, we do not provide the
// SpringBootTest, or the Extend test annotation, this test is pure junit,
class VideoEntityPlainJunitTest {

    @Test
    void shouldCreateVideoEntityWithProperties() {
        var v = new VideoEntity();
        v.setName("n");
        v.setDescription("d");
        assertEquals("n", v.getName());
        assertEquals("d", v.getDescription());
    }
}
```

#### Mocking context

The next level is mocking, that is we test more than one componetns, but still in the realm outside all of the spring
provided context, transactions, proxies, wiring etc.

```java
// note the annotation here which we will focus on later, but what does that say anyway ? It just says to spring use
// this extension, in this case Mockito, Spring has its own which we will later on see how to use as well
@ExtendWith(MockitoExtension.class)
class UserServiceTestOnlyMockito {

  @Mock UserRepository userRepository;
  @Mock UserConverter userConverter;
  @InjectMocks UserService userService;

  @Test
  void shouldCreateUserService() {
    assertNotNull(userService);
  }
}
```

#### Spring context

We are entering the spring territory here where, we would like to allow spring to create a few beans for us but not
everything, we can control this with the following, we provide the `SpringExtension` class to the extends with which
will now involve the spring context extension,

```java
// this annotation provides the bare minimum spring context initialization, it combines a couple of annotations to wire
// spring to Junit and also enable us to write basic tests that interact with the spring context, we also tell spring
// load the user service as context to the test, as it is the target of this test
@SpringJUnitConfig
@ContextConfiguration(UserService.class)
class UserServiceWiringTest {

  @MockitoBean UserRepository userRepository;
  @MockitoBean UserConverter userConverter;

  @Autowired UserService userService;

  @Test
  void wiringWorks() {
    assertNotNull(userService);
    assertNotNull(userService.repository());
  }
}
```

This loads a very tiny spring context and does not scan the entire application also uses and loads only in this case
the `UserService`

```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration
```

Here we can see part of the annotation, it enables or wires the spring context with Junit using the extend annotation,
and also provides a default context configuration, when no parameters are provided to the context config annotation
spring just tries its best to load by default context and configurations from known locations,

#### Stateful context

Further down the levels of testing we might wish to involve actual stateful data, that is to say if we wish to replicate
the state of the application at some future point in time, such as adding data to the store or repositories that we are
using to test certain flow or a condition in our code.

```java
// this is basically an annotation that combines many others that auto-boot the entire spring data stack, enabling it so
// we can actually perform spring-data actions, that will allow us to persist data in our store such as h2, enable
// transactions and so on and so forth
@DataJpaTest
class VideoRepositoryTest {

  @Autowired VideoRepository repo;

  @Test
  void saveAndLoad() {
    var saved = repo.save(new VideoEntity(null, "n", "d"));
    assertThat(repo.findById(saved.getId())).isPresent();
  }
}
```

We can see a little bit more of how this annotation looks like, and that it enables different parts of the data
stack along side the `SpringExtension`.

```java
@ExtendWith(SpringExtension.class)
@OverrideAutoConfiguration(enabled = false)
@TypeExcludeFilters(DataJpaTypeExcludeFilter.class)
@BootstrapWith(DataJpaTestContextBootstrapper.class)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ImportAutoConfiguration
```

We can use this use case to prepare our data base store, and do a test, that does not exclude the fact that we can
combine this annotation with previous ones to still perform tests at a deeper level, that interact with the data
layer.

#### Web context

The next level is testing entire controllers where we would like to do a sort of an end to end test without starting the
whole application. This will actually load a minimal context that includes what we need to test the end-to-end
controller interaction, without booting the entire application, all contexts and configurations.

```java
@WebMvcTest(RestfulController.class)
class RestfulControllerTest {

    @MockBean
    VideoService videoService;

    @Autowired
    MockMvc mvc;

    @Test
    void listVideosReturnsOk() throws Exception {
        when(videoService.findAll(any())).thenReturn(List.of());
        mvc.perform(get("/api/videos")).andExpect(status().isOk());
    }
}
```

We can see a little bit more of how this annotation looks like, and that it enables different parts of the web
stack along side the `SpringExtension`.

```java
@BootstrapWith(WebMvcTestContextBootstrapper.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureCache
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@ImportAutoConfiguration
```

Note that if we have a more complex setup, that has security enabled or such we would have to additionally @Import these
configurations to tell spring how to setup the context, so our integration test actually works.

If security is in play, we typically can run the request “as a user” using `@WithMockUser` (or request post-processors).
The key idea is that authorization decisions still read Authentication and authorities, but you’re not forced to run a
full login flow in the test.

#### Full context

Now finally to enable the full context of a spring application, which would imply that the entire application is
actually started, and everything is loaded for each test we can use the `SpringBootTest` annotation.

```java
@SpringBootTest
class ServiceIntegrationTest {

    @Test
    void loadsContext() { }
}
```

### Testing knobs

To summarize what we have learned so far we have two major ways to configure sprng tests, the first way is to do it
manually, we annotate the tests ourselves and we have the full control over which parts of spring are loaded, or which
are not. These include enabling different auto-configurations and states. The second part is what are known as slice
annotations these are pre-defined annotations that we have used already above, to directly enable certain 'stages' of
our applications, to test these stages. Usually that is the way to go since most of the spring units that we are going
to be testing are already quite deeply integrated into the spring lifecycle, so we will need things like dependency
injection (at the very least) along side some sort of configuration, such as security and so on.

Annotations that control the context, these are plain annotations that we use to load only specific parts of the spring
context and application, we have full control over them, we also are in charge of maintaining them such that when our
requirements change we also change and adapt these annotations as well.

#### Basic

These are the core testing annotations that we can use to tailor the testing context, they can be used to precisely
control what part of the spring context is initialized and when, there is little to no ambiguity, as they only
Crate/initialize what we tell them to.

`@ExtendWith(SpringExtension.class)` - That is the entry point for most of our tests, this is the startup annotation
that basically links JUnit with Spring, allowing spring to be started by Junit, from this point on we can use spring
annotations such as @ContextConfiguration or @Import to enable different components of our spring context of

`@ContextConfiguration` - The core Spring test config way to configure and load a selected part of the
ApplicationContext from specific config classes or locations,not necessarily only spring boot. Use it when you want
precise control over what configuration is loaded, this one is often paired with @SpringJUnitConfig.

`@Import` - Provides a helping hand not strictly only for testing, annotation, but it is often used to include context into
the application context, that is a way of pulling common beans and use-cases usually from some sort of utility
package within the test cases, that provide some common context and configuration to bootstrap the state of the
tests. In other words to compose multiple configuration classes or units to avoid repeating common entry points and
requirements also to separate these configurations and components into their own modules or classes.

#### Slices

Test slice annotations are a collection of annotations that basically configure a specific `SLICE` of the spring
applications context for us- data layer, server/bean layer, web-mvc layer. These are usually provided or exposed by
spring and 3rd party libraries and are a bunch of auto configurations meant to quickly configure a test context for
a specific context.

`@SpringJUnitConfig / @SpringJUnitWebConfig` - These are the “turn on Spring test context in JUnit 5” knobs.
@SpringJUnitConfig is basically the convenient composed version, Spring extension and context configuration, and
@SpringJUnitWebConfig adds web app context semantics.

Annotations that slice the context, load only a part of your app - These are Spring Boot’s biggest productivity win,
load only the layer you’re testing. They auto-configure infrastructure for that layer and intentionally don’t scan the
rest (services/repos/etc.), so you mock the boundaries.

`@WebMvcTest` - Loads Spring MVC infrastructure + controller-related beans (and excludes regular `@Component` beans by
default). Great for controller request/response behavior without booting everything.

`@WebFluxTest` - Same concept as `WebMvcTest` annotation but for Spring `WebFlux` controllers and applications.

`@DataJpaTest` - Loads the JPA persistence slice (entities, repositories, JPA infra). Great for repository
queries/mappings, typically with a test database unless you opt out.

`@JsonTest, @RestClientTest` - plus other data slices like `@DataJdbcTest`, `@DataMongoTest`, `@DataRedisTest`,
@DataR2dbcTest, etc. (depending on your stack). The Boot testing chapter calls out these slice patterns explicitly.

`@SpringBootTest` - Bootstraps (almost) your whole application using Spring Boot’s SpringApplication, so you get
auto-configuration, component scanning, `@ConfigurationProperties`, etc. This is the “integration test” hammer; you use it
when you truly want the assembled app. It can also start a server depending on `webEnvironment`.

Some third-party starters often ship their own `@XXXTest` annotations that behave like slices, they’re usually
meta-annotations combining Boot test auto-config and exclusions. The concept is the same: “load only the tech under
test”.

### Testing units

In unit testing the units are usually the smallest things we can interact with, but that varies from project to
project, in our scenario UNITS can be considered our Database entities, business Models, Services etc. These are to
say, units are a standalone element or component of our project/program, you can think of it as an ATOM, the
smallest indivisible element.

```java
@Test
void toStringShouldAlsoBeTested() {
      VideoEntity entity = new VideoEntity("alice", "title", "description");
      assertThat(entity.toString()).isEqualTo("VideoEntity{id=null, username='alice',
              name='title', description='description'}");
}
```

We can use as many assertions as we wish and usually a test is not required to have one master assert that is
actually much easier to combine multiple asserts into a single test method, however what is important is to ensure
that the test methods are testing a unit (what a unit is in your project, you can decide). The example below tests a
unit in this case the unit is the VideoEntity, and the testing that we perform is over the available setter methods.
That way the test methods are directly referring to some sort of actual action, that might be performed during the
usage of the public API of that object / type class - setters mutate the object, we verify that all the setters work
as expected.

```java
@Test
void settersShouldMutateState() {
      VideoEntity entity = new VideoEntity("alice", "title", "description");
      entity.setId(99L);
      entity.setName("new name");
      entity.setDescription("new desc");
      entity.setUsername("bob");
      assertThat(entity.getId()).isEqualTo(99L);
      assertThat(entity.getUsername()).isEqualTo("bob");
      assertThat(entity.getName()).isEqualTo("new name");
      assertThat(entity.getDescription()) //
            .isEqualTo("new desc");
}
```

### Testing repositories

We can also create our test case for the user repository by using another annotation, such as `@DataJpaTest`, that
would enable the data slice and allow us to take advantage of persisting data to test our service for example.

```java
@Testable
@DataJpaTest
@ActiveProfile("test")
@Import(UserRepository.class)
class UserRepositoryTest {
}
```

Update your `src/test/resources/application-test.yml`, also one should use `@ActiveProfile("test")`, on top of the
target test to ensure that the correct profile is used, usually a generic abstract class is created that contains
all the common annotations and we simply extend off of it when writing tests but, regardless the profile will NOT be
automatically present.

```yaml
spring.sql.init.mode: always
spring.sql.init.schema-locations: classpath:/h2-schema.sql
spring.sql.init.data-locations: classpath:/h2-data.sql
```

A couple more things to note here since we are going to be testing the repository integration, we also need to take care
of ensuring that the H2 container picks the correct data and schema, for our test cases, we can create a new custom data
sql file and put it in our classpath under `src/test/resources/test-data.sql`. The schema can be re-used from the
primary schema file which is what we would expect as well, but some tests might require custom schema objects (keep that
in mind)

### Testing services

Services are a bit more complicated to test because they often rely on multiple beans that need to be instantiated
first, or mocked. And often times there are no pre-defined slices that we can rely on because these services are
user created representing the business layer of an application. These are usually a repository or other services.A
service might also depend on some spring internal beans that are created by spring automatically and we might not
have full control over them in which case instead of mocking them we can spy on them directly from the context,
meaning we do not create a mock instance that is injected into the service, but we wrap around an existing instance
created in the spring context / container to spy on that bean instead

```java
@Testable
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserService() {
        assertNotNull(userService);
        assertNotNull(userService.entityClass());
        assertNotNull(userService.modelClass());

        assertNotNull(userService.repository());
        assertNotNull(userService.converter());
    }
}
```

In this example we have a bare bones mockito context, that is injecting the mocks into the user service, there is no
spring context involved here, we are not actually using spring at all, we are going to see how this can be upgraded
to use spring to actually let spring inject the dependencies in our services instead

To ensure this can be tested correctly we have to ensure that we mock the return values of these beans as well,
there are no actual beans created for these dependencies that this service is getting inject with.

Next step is to let spring create these instances, while we can still have mockito wrap around them and provide a
mock, in the example here we let spring create the service instance, and we @Autowire it, however the dependencies
are still strictly not spring context created, just the primary service that is being tested.

```java
@Testable
@SpringJUnitConfig
@Import(classes = UserService.class)
class UserServiceTest {

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private UserConverter userConverter;

    @Autowired
    private UserService userService;
}
```

We have changed the example above by using different annotations, now we have enabled spring context, so instead of
mockito we are actually loading a minimal spring context, that is going to be loading and auto-wiring the user
service bean along side the dependencies which the service needs. `MockitoBean` is a spring based annotation that
basically allows us to create a mock of a bean in the spring context directly, that will be injected and wired into
the user service, and also mock it at the same time. But remember, the actual beans instances for these service
dependencies are not their concrete actual implementations, they are mock implementation instances injected into the
spring context by Mockito

We can go further, what if we want to provide the actual instances, tell spring to scan the components instead, we
have to swap our the annotations with `MockitoSpyBean`, that will only wrap around the actual implementations
created and injected into the spring context, and not interfere with the bean itself. Still allow us to spy onto the
bean' method calls and so on. Now we have told spring to detect and scan all components under the package base for
the dependencies - `UserRepository.class and UserConverter.class`. And spring will actually create these beans
itself with their implementations.

```java
@Testable
@SpringJUnitConfig
@Import(UserServiceTest.ExtraTestConfiguration.class)
class UserServiceTest {

    @TestConfiguration
    @ComponentScan(basePackageClasses = {UserService.class, UserRepository.class, UserConverter.class})
    static class ExtraTestConfiguration {
    }

    @MockitoSpyBean
    private UserRepository userRepository;

    @MockitoSpyBean
    private UserConverter userConverter;

    @Autowired
    private UserService userService;
}
```

The test above is of course not complete since even though an instance of the user repository might be created in
the spring container context, we have not initialized or ensured that the data layer/slice is, and no proper
persistence would be possible, this is for demonstration purposes to showcase that we can still create the dependent
beans into the spring context, and use their actual implementations.

### Testing controllers

Unlike units, controllers, services and other such components of our program are more complex and usually rely on
interfacing and communicating with other components to perform and action, a controller for example might use
multiple business services to deliver information to the end client. In this case what do we test , and also how ?

First a few things we would have to prepare, since we have already created some sort of protection through our
security for certain routes, in the security configuration we have to ensure that we can actually call the servlet
path under our controller in this case we will attempt to test both the template and restful controllers. Either way
both of them are secured and protected and do not allow anonymous access at all.

To do that we have to take advantage of the spring security testing dependency spring-security-test, that one
contains a few helping hand annotations such as `@WithMockUser`, we can use to annotate our test methods or test suite
to mimic security authentication context.

```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

Here is the general structure of our test suite, we have injected the `VideoService` mock into the controller, which
in this case is initialized by the `WebMvcTest` annotation slice, that will ensure that spring creates our
controllers, and mockito will create an instance proxy of the `VideoService`, that we can mock - tell certain methods
to return certain pre-defined result.

```java
@Testable
@WithMockUser(value = "test-user", username = "admin", authorities = {"video:list"})
@WebMvcTest(controllers = RestfulController.class)
class RestfulControllerTest {

    @MockitoBean
    UserService userService;

    @MockitoBean
    VideoService videoService;

    @MockitoBean
    RoleService roleService;

    @MockitoBean
    PrincipalService principalService;

    @MockitoBean
    AuthorityService authorityService;

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void prepareMockData() {
        Page<VideoModel> emptyPage = new PageImpl<>(List.of(), PageRequest.of(0, 20), 0);
        when(videoService.findAll(any(Pageable.class))).thenReturn(emptyPage);
    }

    @Test
    void shouldReturnPagedVideos() throws Exception {
        mvc.perform(get("/api/videos").param("page", "0").param("size", "20").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.content", hasSize(0)))
                        .andExpect(jsonPath("$.totalElements").value(0));

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(videoService).findAll(captor.capture());

        Pageable pageable = captor.getValue();
        assertThat(pageable.getPageNumber()).isZero();
        assertThat(pageable.getPageSize()).isEqualTo(20);
    }
}
```

What the test actually does is not that important in this case we just verify that the response is valid 200, and
also empty, it should contain page data as well since that controller endpoint returns pageable result.

```java
@Testable
@WithMockUser(value = "test-user", username = "admin", authorities = {"user:list", "user:manage"})
@WebMvcTest(controllers = TemplateController.class)
class TemplateControllerTest {

    @MockitoBean
    UserService userService;

    @MockitoBean
    VideoService videoService;

    @MockitoBean
    RoleService roleService;

    @MockitoBean
    PrincipalService principalService;

    @MockitoBean
    AuthorityService authorityService;

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void prepareMockData() {
        when(userService.findByUsername("admin"))
                        .thenReturn(Optional.of(new UserModel(1L, "admin", "admin", new RoleModel(1L, "ADMIN", Collections.emptySet()))));
        when(userService.get(1L)).thenReturn(new UserModel(1L, "admin", "admin", new RoleModel(1L, "ADMIN", Collections.emptySet())));
        when(principalService.getPrincipal()).thenReturn(Optional.of(new MutableUserDetails("admin", "admin", Collections.emptySet())));
    }

    @Test
    void shouldReturnPrincipalUser() throws Exception {
        mvc.perform(get("/ui/user").accept(MediaType.TEXT_HTML_VALUE))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE));

        verify(userService, times(0)).get(anyLong());
        verify(principalService, times(1)).getPrincipal();
        verify(userService, times(1)).findByUsername("admin");
    }

    @Test
    void shouldReturnTargetUser() throws Exception {
        mvc.perform(get("/ui/user/1").accept(MediaType.TEXT_HTML_VALUE))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE));

        verify(principalService, times(0)).getPrincipal();
        verify(userService, times(0)).findByUsername(anyString());
        verify(userService, times(1)).get(1L);
    }
}
```

Here is a simple test that demonstrates both how to get the current target user principal, this method returns
either a user by username or the currently authenticated principal based on if we provide an id (relational unique
identifier) to obtain a user by it. In the first scenario that we test we test for the currently authenticated
principal which is obtained from the principal service, the second scenario obtains a user only if our user has the
correct roles/permissions which for our test case it has - user:manage or user:list

Note that both tests verify that the respective methods are called with the proper arguments, and even it asserts
that certain methods are never called at all since that is what we would expect as well, that is an important
distinction, because we would like to avoid any side effects that might be produced by unexpected method calls.

```plaintext
[demo-base] [ main] w.c.HttpSessionSecurityContextRepository : Created HttpSession         as SecurityContext is non-default
[demo-base] [ main] w.c.HttpSessionSecurityContextRepository : Stored  SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken]

[Principal=org.springframework.security.core.userdetails.User [Username=admin, Password=[PROTECTED], Enabled=true,
AccountNonExpired=true, CredentialsNonExpired=true, AccountNonLocked=true, Grant ed Authorities=[ROLE_user:list]],
Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[user:list]]]
```

Just as a side note when the tests run, one might notice this get printed, by the spring context being initialized
before the tests start executing, this actually shows us how the `@WithMockUser` constructs and sets the user
principal authentication context. `Take a good note of the fact that we use authorities in the WithMockUser
annotation and not the roles, because that will produce a role of the type ROLE_user:list and not a raw authority
user:list`

### Test containers

We saw above already that we can use an in-memory data base like H2 which we already use for our base application to
simplify its deployment and testing. However in a real world senario our app will not be using an in memory
database, on the contrary it will be using an actual real instance of a database possibly even running on a
different machine in a different data center or server than our business app.

For testing in spring we can still use in-memory h2 replacement to test our data slice, but sometimes it is better
to have the real thing even in tests, so how do we do that. Not longer after the invention of Docker in 2013-2014,
came `testcontainers`, these are a quick way for us to spin a test container on our environment, without having to
worry about setting up and booting them and tearing them down after we have finished ourselves.

The idea is simple when or to be exact just right before tests start, the `testcontainers` will spin up an instance
of an application for us (in this case we will focus on databases, but really it can be anything) and prepare them
for use, our application will never know it is running against a test-container a replica of the actual application
that it expects to connect to in the real world (like a database, such as Postgres, Mysql, etc).

The added benefit here is that we reduce the unknowns, if our application was developed to work with a certain
database - `Postgres` for example, we test with the exact same conditions that it expects, not with a reproduction of
a database like what `H2` is. This is important because different databases have different SQL dialects, sometimes
these things might matter for our use case

`A common mistake when testing repositories is using an in-memory database like H2 for testing while using a
different type of database like PostgreSQL or Oracle Database in production. There is no guarantee that the
implementation that worked with an in-memory database will work in the same way with other databases. Additionally,
the in-memory database may not have all the features provided by your production database and vice versa.`

The general set of dependencies we need to add to our pom file, these are just like any other, note that we do not
specify the version here, we will do that with the `Testcontainers` BOM

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>postgresql</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>
```

The test containers BOM will ensure that the dependencies that are part of the `org.testcontainers` group all come with
their proper versions

```xml
<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.testcontainers</groupId>
                <artifactId>testcontainers-bom</artifactId>
                <version>${testcontainers.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
</dependencyManagement>
```

A few notes about these dependencies, that we have just added to our pom file, and how they interact with spring during
testing:

- The very first specifies the java database drivers for `Postgres`, that is important because we care about having a
  `Postgres` drivers otherwise we would not be able to connect to the database in a `testcontainer` or at actual
  production server

- The other two are the integration between test containers and Postgres, that dependency is actually providing a
  common API between the generic test container API, and Postgres, allowing us to quickly interact and start with
  Postgres containers, and the final dependency is actually the test-container integration with JUnit, these are all
  the things we really need.

- The final dependency is the BOM, that is very similar to the way the spring-boot-parent BOM works, it contains all
  the necessary versions of all dependencies that we would need, including the ones we just included, packaged under
  one major.minor.patch version basically the BOM contains all the dependencies fixed with specific version, that work
  together, that ensures that we will not randomly update one dependency version and forget another an explode our
  application, the BOM ensures that the working versions between the different dependencies are packaged together

```xml
<properties>
    <testcontainers.version>1.21.4</testcontainers.version>
</properties>
```

First ensure that your version of `testcontainers` is compatible with the system's docker daemon and engine version,
run `docker info` to verify this, a more recent 29+ version would require an up-to date BOM for test-containers

```sh
$ docker info
Client: Docker Engine - Community
 Version:    29.1.5
 Context:    default
 Debug Mode: false
 Plugins:
  buildx: Docker Buildx (Docker Inc.)
    Version:  v0.30.1
    Path:     /usr/libexec/docker/cli-plugins/docker-buildx
  compose: Docker Compose (Docker Inc.)
    Version:  v5.0.1
    Path:     /usr/libexec/docker/cli-plugins/docker-compose
```

Once we have verified that we have the correct BOM version to work with our local daemon engine, we can take a look
at the test suite.

```java
@Testable
@DataJpaTest
@Testcontainers
@ContextConfiguration(initializers = DataSourceInitializer.class)
@AutoConfigureTestDatabase(replace = Replace.NONE, connection = EmbeddedDatabaseConnection.NONE)
class VideoRepositoryTest {

    @Container
    // create an instance of the Postgres database, with this particular image, that instance will be alive for the
    // duration of this test suite only, and destroyed afterwards
    static final PostgreSQLContainer<?> POSTGRES_DATABASE = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    // tests are focused on this repository, but we can factor-away the common annotations and the initializer into a
    // parent class, where the common logic for setting up the initializer and the database container will lie.
    VideoRepository videoRepository;

    @Test
    void shouldPersistVideos() {
        VideoEntity entity = new VideoEntity("video1", "descrption1");
        entity = videoRepository.save(entity);

        assertNotNull(entity);
        assertNotNull(entity.getId());

        assertEquals(1L, videoRepository.count());
        assertEquals(entity, videoRepository.getReferenceById(entity.getId()));
    }

    static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
            // the important properties are listed here, what we do is prepare the default data source correctly, each
            // data source needs to know not just the connection URL, or merely the password, but most importantly which
            // actual data base driver and dialect is going to be used, otherwise there is no way to establish a correct
            // connection that is going to work. The ddl-auto here is used because we have no compatible schema generation
            // mechanism at this point, not until we change our actual app database integration from H2 to PostgreSQL.
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
                            "spring.datasource.url=" + POSTGRES_DATABASE.getJdbcUrl(),
                            "spring.datasource.username=" + POSTGRES_DATABASE.getUsername(),
                            "spring.datasource.password=" + POSTGRES_DATABASE.getPassword(),
                            "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
                            "spring.datasource.driverClassName=org.postgresql.Driver", "spring.jpa.hibernate.ddl-auto=create-drop");
        }
    }
}
```

A few key points that are incredibly important to understand how this test setup works and why it works, lets start
form the top

- `DataJpaTest` - initialize the entire JPA context slice in the spring context, that is, all the repository
  configurations, down to the JPA transaction managers and so on.
- `Testcontainers` - integrates the test-containers with JUNIT ensures that it will piggy back on Junit, needed to
  actually know when to start itself and the containers that annotation is basically using the JUnit extends with -
  and attaching the test-container as extension, for startup - `@ExtendWith(TestcontainersExtension.class)`
- `ContextConfiguration` - enable our custom `DataSourceInitializer`, that will dynamically modify the spring
  properties during context initialization to ensure that we actually connect to the database instance started by
  test-containers and not whatever we have statically configured to work with our actual production grade database.
- `AutoConfigureTestDatabase` - this one is quite important, it tell the spring context to not create any default
  test data source `DataSource`, that is initially created by `DataJpaTest`

`DataSourceInitializer` - the data source initializer here ensures that we have correctly setup the properties required
for the connection to the Postgres relational data base, most importantly we have to setup the URL, username password
but also ensure that hibernate is using the correct SQL dialect in this case previously we have set this to the
`H2Dialect`, that is not valid for Postgres databases, we have to use the `PostgreSQLDialect` instead, also we have to
change the driver we were thus far using the `H2` Driver, we change it to use the Postgres driver (we added as a runtime
dependency to the pom at the start of this chapter). Take a good note of the way we configure the
`ddl-auto=create-drop`, that will ensure that the schema is created automatically, we need this because our schema is

`AutoConfigureTestDatabase` - the next important part, this is where the connection magic happens, by default the slice
that is initialized by `DataJpaTest` creates an embedded data source connection, it tries to locate one of a few
in-memory databases on the classpath and connect to them, such as `H2, Derby or HSQL`, which we do not want we want to
integrate with the real thing, so we have to either remove our `H2` integration and delete it from the classpath, or
completely disable this behavior which is implemented by `TestDatabaseAutoConfiguration`, by setting the Replace to
NONE, meaning the data source will NOT be replaced with one that is connecting to an in-memory database.

Later on we will actually remove `H2` completely from our application and replace this integration with an actual
Postgres database for both the tests and the actual application, that would solve the issue of having to exclude test
data base data sources from being created because the `H2` will be removed from the classpath / our dependencies.

### Test security

We already did some of that by providing a pre-defined test authentication and authority context, when we used
`@WithMockUser`, which basically setup a testing authentication context out of the box for us allowing us to access an
otherwise protected endpoint, we are going to dig deeper this time and see how to actually verify that the security that
we have put in place works. The test case below is mostly abridged, the full form is not shown but rather what is
actually important for the purposes of demonstrating how to make sure we are actually testing the security layer of our
application.

```java
@Testable
@Import(SecurityConfiguration.class)
@WebMvcTest(controllers = TemplateController.class)
class SecurityConfigurationTest {

    @Autowired
    MockMvc mvc;

    @BeforeEach
    @WithMockUser(value = "setup-user", username = "basic", authorities = {})
    void prepareMockData() {
        when(userService.findByUsername("admin"))
                        .thenReturn(Optional.of(new UserModel(...)));
        when(userService.get(1L)).thenReturn(new UserModel(...));
        when(userService.update(anyLong(), any())).thenReturn( new UserModel(...));
        when(principalService.getPrincipal()).thenReturn(Optional.of(new MutableUserDetails(...)));
    }

    @Test
    void shouldReportUnauthorizedForUserAccess() throws Exception {
        mvc.perform(get("/ui/user/1")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrlPattern("**/ui/login"));
        verify(userService, times(0)).get(1L);
    }

    @Test
    @WithMockUser(value = "test-user", username = "regular-user", authorities = {"video:list"})
    void shouldReportForbiddenForUserAccess() throws Exception {
        mvc.perform(get("/ui/user/1")).andExpect(status().isForbidden());
        verify(userService, times(0)).get(1L);
    }

    @Test
    @WithMockUser(value = "test-user", username = "regular-user", authorities = {"user:manage"})
    void shouldReportAllowForUserAccess() throws Exception {
        mvc.perform(get("/ui/user/1")).andExpect(status().isOk());
        verify(userService, times(1)).get(1L);
    }

    @Test
    @WithMockUser(value = "test-user", username = "advanced-user", authorities = {"user:manage"})
    void shouldReportAllowForUserUpdate() throws Exception {
        UserModel updatedUser = new UserModel(1L, "regular-user", "new-password", null);

        mvc.perform(post("/ui/update-user").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("id", "1")
                        .param("username", "regular-user")
                        .param("password", "new-password")).andExpect(status().is3xxRedirection());
        verify(userService, times(1)).update(1L, updatedUser);
    }

    @Test
    @WithMockUser(value = "test-user", username = "regular-user", authorities = {})
    void shouldReportForbiddenForUserUpdate() throws Exception {
        mvc.perform(post("/ui/update-user").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("id", "1")
                        .param("username", "another-user")
                        .param("password", "new-password")).andExpect(status().isForbidden());
        verify(userService, times(0)).update(anyLong(), any());
    }

    @Test
    @WithMockUser(value = "test-user", username = "myself-user", authorities = {})
    void shouldReportAllowMyselfForUserUpdate() throws Exception {
        UserModel updatedUser = new UserModel(1L, "myself-user", "new-password", null);

        mvc.perform(post("/ui/update-user").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("id", "1")
                        .param("username", "myself-user")
                        .param("password", "new-password")).andExpect(status().is3xxRedirection());
        verify(userService, times(1)).update(1L, updatedUser);
    }
}
```

First take a look at the following setup, we have here specified that, we require to import the security configuration
for our application, if we do not do this the default MVC slice security that is setup will be used, meaning we are not
going to be testing our security. Remember that the testing slice annotations do not implicitly initialize our context
at all, they mostly take care of initializing, the core context that spring needs to start the given slice in this case
the web slice only, and that is all

```java
// these two annotations have to be correctly setup to actually properly test what we expect to test, first the
// controller that we are aiming at testing has to be the one which is initialized in the @WebMvcTest, and secondly we
// @Import our security context configuration.
@Import(com.spring.demo.core.config.SecurityConfiguration.class)
@WebMvcTest(controllers = com.spring.demo.core.web.TemplateController.class)
```

Second we want to see if our security actually works, we have a few guard rails that protect the controller - the user
must be authenticated and also authorized, meaning the user has to have the correct authorities to perform the given
actions, otherwise we get 401 or 403 respectively more on that in a bit. On top of that the endpoints are protected by
CSRF token - BUT that is only for side effect or mutating endpoints - POST, PUT, DELETE, that means that methods like
GET and OPTIONS are not protected by CSRF by default in spring because they do not produce side effects (by convention)

```java
// take a good note of the fact that we use the authorities  of the mock user, we specify that this user has this
// specific authority list, which we require for mutating the user data in our controller otherwise we will get forbidden
// even if we are authenticated
@WithMockUser(value = "test-user", username = "advanced-user", authorities = {"user:manage"})
void () throws Exception {
    ...
    // enhances this update-user request with a csrf token, also take a note of the content type, since this is part of
    // the `TemplateController`, we do not have an APPLICATION_JSON but rather we use `APPLICATION_FORM_URLENCODED_VALUE`, or
    // in other words form data, the csrf will be included in both the form data and the request headers
    mvc.perform(post("/ui/update-user").with(csrf())
        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .param("id", "1")
        .param("username", "regular-user")
        .param("password", "new-password"))
    ...
}
```

The `csrf` static method located at `SecurityMockMvcRequestPostProcessors`, is a post processor for requests. What it
does is enhance the test request we construct with MVC and perform, with a cross site resource forgery token, to allow
us to call the endpoint otherwise we will receive 415

We have come to the final point we can see that we have two types of statuses related to security context, mostly - we
usually get `401 or 403`, that spring e - `401` means unauthenticated (even though actually that is unauthorized as
specified in the web spec) and `403` is unauthorized. What does that imply

`401` - unauthenticated - means that our user never authenticated, that might be due to wrong user/password credentials,
or we can get this error code on endpoints that only require authentication, but no authorization

`403` - forbidden - means that we were not authorized or allowed to perform a certain action, that means that we are
authenticated, and the application recognizes us but we are not having the correct set of permissions to evaluate the
action we are trying to evaluate

We can clearly actually see the difference between these two in the test which is expressed above, here is an excerpt
from that test. We can see that without any mock user the application will directly redirect to the login page, that is
how we have it setup in our security config. Below we have put the abridged important parts of the security config that
actually configures this exact behavior

```java
...

// here we actually create a new login page filter that is under /ui/login, the default login page remains and it is
// actually what redirects to this custom login page, this is for convenience and consistency mostly we could have used
// the default login page just as well which is under /login
DefaultLoginPageGeneratingFilter defaultLoginPageGeneratingFilter = new DefaultLoginPageGeneratingFilter();
defaultLoginPageGeneratingFilter.setAuthenticationUrl("/ui/login");
defaultLoginPageGeneratingFilter.setLoginPageUrl("/ui/login");

...

.formLogin(customizer -> {
    // this configuration will force the default filter to redirect to the custom login page, instead of the
    // default login page which is what it does by default, this was done to separate the login into its own
    // sub-section path under /ui
    customizer.loginPage("/ui/login");
    customizer.defaultSuccessUrl("/ui/");
    customizer.loginProcessingUrl("/ui/login");
})
...
```

Here is the test itself, notice that we expect a redirect, not a 401, because that is what our security does, unlike
when you are unauthorized, where you will get an error 403 page, here we do not default to a 401 page but rather
directly proactively redirect to the login page, to force a user / principal to login. That is as we same configured
in our security config, we can of course change this behavior and redirect to a 401 error page just like we do for
unauthorized / 403 errors

```java
@Test
void shouldReportUnauthorizedForUserAccess() throws Exception {
    // we specifically test that the access to an endpoint without a user will directly navigate us to the login
    // page, this is the setup that we have chosen, attempting to present the login to the user
    mvc.perform(get("/ui/user/1")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrlPattern("**/ui/login"));
}
```

## Release

The art of actually packing your application is just as important as making it. There are many ways to package spring
apps. Above in the very first chapters we already talked these many ways but we will re-iterate on them briefly and
maybe expand a little bit. First we are going to take a better look at the `Uber jar` idea which is a term coined by the
spring team, and is something that is produced by the spring maven plugin.

The idea is simple we package everything together, including a small embedded web server that will run our app, and that
enables us to use around this `Uber jar`, everywhere, everywhere, really any environment that has a compatible JVM
installed on it will run this thing, no extra dependencies no extra hassle, just a jar, usually a couple dozen of
megabytes big at most.

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

Maybe this is not as impressive as you were led to believe, perhaps because this is incredibly simple. So, let’s revisit
what exactly has happened:

1. There is no need to download and install an Apache Tomcat servlet container anywhere. We are
   using embedded Apache Tomcat. This means this tiny JAR file is carrying the means to run itself.

2. There is no need to go through the legacy procedure of installing an application server, fashioning
   a WAR file, combining it with third-party dependencies using some ugly assembly file to fashion
   an EAR file, and then uploading the whole thing to some atrocious UI.

3. We can push this whole thing to our favorite cloud provider and command the system to run
   10,000 copies of it, the same setup for each of these copies.

Another option is to use the docker engine and docker itself to package our application, that is also much easier to
distribute our app as well. The idea is that once we have the `Uber` jar created, we can easily bake that into a docker
image, spring itself has a plugin goal that will build and package and create an image all in one go for your
application.

```sh
# just run the following in your command line, that will trigger the plugin goal that is built and distributed with
# spring boot and do the entire process for you from compiling, testing, and packaging the app down to making a docker
# image and deploying that onto your local docker engine client
./mvnw spring-boot:build-image

# this is the equivalent of the above, it is just the actual goal that is being run, it is contained inside the spring
# boot maven plugin and it is the actual thing that knows how to construct and build not only our application, but the
# entire stack.
./mvnw org.springframework.boot:spring-boot-maven-plugin:build-image
```

Okay now we can go a step further, docker images and containers as well are built from something called layers, these
layers are a construct that allows us to stack a read only file system resources on top of each other like a Lego. The
layers allow us to be re-used across images. Further more for our use case we might want to do something like this -
have the application dependencies in another layer than our application and thus when we change our application code
that would only trigger layer re-build for the layer that our application occupies and not all layers which potentially
will force dependencies to be re-downloaded that really have not changed (usually those change quite rarely as well)

We have to mention here that we can very well package our application as one big fat jar, but the general idea is to
split it up, into layers, each layer represents a logical part of the whole, that logical part - dependencies,
resources, app-code, changes at different cadences, thus we can safely move them to different layers, that can greatly
increase the efficiency when building applications, not to mention that if we have more than one, that becomes kind of
crucial. Imagine we have 10 applications all using spring boot 6, that implies that most all dependencies are going to
be the same - meaning their version, that is because all versions are defined and declared in the spring parent BOM
file. Thus if we have a layer just for these dependencies that layer can be shared between all of our 10 application,
and the main differences will be the actual app-code and possibly the resources the app uses. You can see how that can
become a quite beneficial approach to packing the app image, instead of putting everything into one layer one big fat
jar.

So what is the general process that is being followed by these packaging and building tools. First we have already seen
what the fat jar contains in early chapters we knwo that the jar contains all the dependencies that we would need to run
the app, along side the app code and resources, all into one big jar file. But what we need to realize is that the
structure needs not be that, actually the structure can be anything as long as we have the dependencies and know how to
build the class path to run our application, after all.

First lets see what is going on on the surface, lets assume that you are running locally and run this java command
against your fat jar in the target directory

```sh
# first lets run the following command and see what is going to be printed out
java -Djarmode=tools -jar target/app-0.0.1-SNAPSHOT.jar list-layers

# these are the folders that are going to be created, each one of these folders, contains files form the fat jar, some
# contain only dependencies, another contain the boot loader to start our app, the others contain our source code alone
dependencies
spring-boot-loader
snapshot-dependencies
application
```

```sh
# now we can run the same command just change it a bit and extract the layers instead of listing them, that will create
# local folders with the content above.
java -Djarmode=tools -jar target/app-0.0.1-SNAPSHOT.jar extract --layers --destination ./target/extracted
```

So here is the actual directory structure that we expect to see, under the root folder extracted, we have the
dependencies, the app itself, these are the most important ones for now.

```plaintext
 target/
   extracted/
     application/
       app-0.0.1-SNAPSHOT.jar
     dependencies/
       lib/
     snapshot-dependencies/
     spring-boot-loader/
```

Now that we have seen what these folders contain, we can move into actually creating a Dockerfile that will replicate
the same behavior but with docker. So what is going on with the Dockerfile lets examine the docker file that might be
generated.

```Dockerfile
# first we create a base builder throwaway image from the OpenJDK Alpine version, and copy our fat jar into that image,
# we are going to use our fat jar to extract into the different layers
FROM openjdk:17-jdk-alpine as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
```

```Dockerfile
# second we actually do the extraction in the builder image, that image is as we mentioned above a throwaway one, that
# means we will use it only as a staging area for our resources
RUN java -Djarmode=layertools -jar application.jar extract --layers --destination ./
```

```Dockerfile
# from the builder image now we can copy all the resources back into an image that will contain all of these folders,
# each of these copy commands will create a new independent layer
FROM openjdk:17-jdk-alpine
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

# tell docker how to start this image as a container, that jar launcher is what will construct the classpath and ensure
# that the application is started as if it were in a regular fat jar, what is crucial to remember here is that we do not
# need the application to be in a jar, all we need to do is know how to start it meaning - knowing the entry point and how
# to construct the classpath for the application to work correctly. And all of that information is inside the MANIFEST file
ENTRYPOINT ["java", "-jar", "application.jar"]
```

Remember that each COPY creates a new layer, that means that the next time we perform a COPY for example in - `COPY
--from=builder application/ ./` to update our application jar, we are not going to touch the layers created before that,
such as the dependencies layers, or snapshots, or the boot-loader, only the application layer will be re-built

Now that we have this knowledge we can see how this is beneficial, these layers are managed by docker engine, docker has
their `SHA`, and can share the layers (in this case the most crucial one is the dependencies layer) between different
images, if we had a few dozen applications these benefits can compound quickly

Here is an abridged version of the manifest file for this application, you can see that it constructs and references the
classpath by using all dependencies in the lib/ folder which is exactly where our dependencies live, so instead of
having one big fat jar, we have smaller files, the dependencies and our actual application, that way we can easily only
update our dependencies independently in the lib/ folder or our application.jar again independently without destroying
all of the layers.

```MF
Manifest-Version: 1.0
Created-By: Maven JAR Plugin 3.4.2
Build-Jdk-Spec: 21
Implementation-Title: demo
Implementation-Version: 0.0.1-SNAPSHOT
Main-Class: com.spring.demo.core.DemoApplication
Spring-Boot-Version: 3.5.3
Class-Path: lib/spring-boot-3.5.3.jar lib/spring-boot-autoconfigure-3.5. 3.jar lib/jakarta.annotation-api-2.1.1.jar
 lib/snakeyaml-2.4.jar lib/ja ckson-databind-2.19.1.jar lib/jackson-annotations-2.19.1.jar lib/jackso n-core-2.19.1.jar
 lib/jackson-datatype-jdk8-2.19.1.jar lib/jackson-data type-jsr310-2.19.1.jar
 lib/jackson-module-parameter-names-2.19.1.jar li b/tomcat-embed-core-10.1.42.jar lib/tomcat-embed-el-10.1.42.jar lib/tom
 cat-embed-websocket-10.1.42.jar lib/spring-web-6.2.8.jar lib/spring-bea ns-6.2.8.jar
 lib/micrometer-observation-1.15.1.jar lib/micrometer-commo ns-1.15.1.jar lib/spring-webmvc-6.2.8.jar
 lib/spring-context-6.2.8.jar lib/spring-expression-6.2.8.jar lib/logback-classic-1.5.18.jar lib/logb
 ack-core-1.5.18.jar...
```

Once the image is ready you can release your new application to repositories like docker hub, these are public
repositories that host container images, which can be accessed from pretty much anywhere in the world, that is a useful
mechanism to share application container images in a wider community. Each image is tagged, the tag represents the
version of your application, that way you can release a new version while the old one is still available, the versioning
should generally follow `SemVer`, meaning the version string contains 3 main components, or numbers representing the,
MAJOR.MINOR.PATCH version of the application.

The MAJOR number is only incremented when there are breaking or significant changes in the application, that notifies
the clients of your app of that (one such example might be a public API that has changed, and is no longer compatible
with the old one). The MINOR change is usually reserved for non-breaking but still significant changes, mostly for new
features or improvements. These are not supposed to break the application for your clients, and the clients do not
expect that to be the case by default. The final number is the PATCH, that usually represents bug fixes and general
quality of life improvements

## Upgrades

Once we know how to deploy our application, and we also know how to start a docker image we can take advantage of that
an for example modify our application to use another database instance instead of the built in-memory one we have been
using so far, so for example to do this change we only need to modify some of the application properties, such as
providing a new profile or directly setting these on the command line, some of the approaches are not as portable as
others.

### Database

What we can do is change our application yaml config to first have the following `DataSource`, username and password
config. Do not forget to also change the dialect, we have been using an `H2` dialect so far but that is not going to
work, we have to tell JPA or in this case its implementation Hibernate which dialect to use when building the underlying
SQL queries. We also need to configure the correct driver as well, that means we need two things first enable the
correct diver in the data source, in this case that would be - setting the correct value for the `driverClassName`

- `org.postgresql.Driver`. Then we have to add the Postgres driver into the POM file of our project

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

```yaml
# Schema initialization
sql.init.mode=always
sql.init.data-locations=classpath:/postgres-data.sql
sql.init.schema-locations=classpath:/postgres-schema.sql
# JDBC DataSource settings
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.driverClassName: org.postgresql.Driver
# JPA settings
spring.jpa.hibernate.ddl-auto=none
spring.jpa.hibernate.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

Take a good note of the `DDL` configuration, that is important, we are going to provide our own schema file instead,
just as like we did for the H2 database, that is much easier to maintain in the long run, not allow hibernate to update
it or change it. That also implies that we will have to introduce new tools to do this for us there are some famous ones
in existence such as `Liquibase` or `Flyway`, both of which ensure proper schema migration and updates.

For our purposes we are just going to use a single schema file for now, and ensure that the schema is created. By
default the `sql.init.mode` is enabled only for embedded data bases, which `H2` was but we would like to change that to be
for all data bases that is why we use the always.

```sql
CREATE SEQUENCE IF NOT EXISTS videos_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS users_seq  START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS roles_seq  START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS auth_seq   START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS roles (
    id   BIGINT PRIMARY KEY DEFAULT nextval('roles_seq'::regclass),
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS authorities (
    id    BIGINT PRIMARY KEY DEFAULT nextval('auth_seq'::regclass),
    name  VARCHAR(32) NOT NULL UNIQUE,
    "grant" VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
    id       BIGINT PRIMARY KEY DEFAULT nextval('users_seq'::regclass),
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(128),
    role_id  BIGINT NOT NULL,
    CONSTRAINT fk_users_role
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS videos (
    id          BIGINT PRIMARY KEY DEFAULT nextval('videos_seq'::regclass),
    name        VARCHAR(128) NOT NULL UNIQUE,
    description VARCHAR(1024)
);

CREATE TABLE IF NOT EXISTS role_authority (
    role_id      BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, authority_id),
    CONSTRAINT fk_role_authority_role
    FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT fk_role_authority_authority
    FOREIGN KEY (authority_id) REFERENCES authorities(id)
);

ALTER SEQUENCE roles_seq  OWNED BY roles.id;
ALTER SEQUENCE auth_seq   OWNED BY authorities.id;
ALTER SEQUENCE users_seq  OWNED BY users.id;
ALTER SEQUENCE videos_seq OWNED BY videos.id;
```

The schema ensures that we only create these schema entities once, we can also do the same for the data by using
similar idea that is to ensure that on subsequent restarts of our application we do not get errors due to conflicts,
that is something that might occur if we do not take this into account. Take note of the usage of `on conflict
(<key>) do nothing`, which will ensure that if we had already inserted that record before, (app has been restarted
we do not get failed sql queries.

```sql
INSERT INTO roles (name) VALUES
    ('ADMIN'),
    ('EDITOR'),
    ('USER')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO authorities (name, "grant") VALUES
    ('VIDEO_READ',   'video:read'),
    ('VIDEO_CREATE', 'video:create'),
    ('VIDEO_UPDATE', 'video:update'),
    ('VIDEO_DELETE', 'video:delete'),
    ('USER_MANAGE',  'user:manage'),
    ('USER_LIST',    'user:list')
    ON CONFLICT (name) DO NOTHING;

WITH r AS (SELECT id FROM roles WHERE name = 'ADMIN'),
a AS (SELECT id FROM authorities)
INSERT INTO role_authority (role_id, authority_id)
    SELECT r.id, a.id
    FROM r CROSS JOIN a
    ON CONFLICT DO NOTHING;

WITH r AS (SELECT id FROM roles WHERE name = 'EDITOR'),
a AS (
    SELECT id FROM authorities
    WHERE name IN ('VIDEO_READ', 'VIDEO_CREATE', 'VIDEO_UPDATE', 'USER_LIST')
)
INSERT INTO role_authority (role_id, authority_id)
    SELECT r.id, a.id
    FROM r CROSS JOIN a
    ON CONFLICT DO NOTHING;

WITH r AS (SELECT id FROM roles WHERE name = 'USER'),
a AS (SELECT id FROM authorities WHERE name = 'VIDEO_READ')
INSERT INTO role_authority (role_id, authority_id)
    SELECT r.id, a.id
    FROM r CROSS JOIN a
    ON CONFLICT DO NOTHING;

INSERT INTO users (username, password, role_id) VALUES
    ('admin',  'admin123',  (SELECT id FROM roles WHERE name = 'ADMIN')),
    ('editor', 'editor123', (SELECT id FROM roles WHERE name = 'EDITOR')),
    ('user1',  'user123',   (SELECT id FROM roles WHERE name = 'USER')),
    ('user2',  'user123',   (SELECT id FROM roles WHERE name = 'USER'))
    ON CONFLICT (username) DO NOTHING;
```

Once we ensure these properties are set, and we have added the new schema file we can create a Postgres container using
a regular Postgres image that will run in our case on our localhost

```sh
# we run the Postgres image, and ensure that we set the same username and password that we have configured for our
# application above, in the properties, this of course is important
$ docker run -d -p 5432:5432 --name postgres -e POSTGRES_USERNAME=postgres -e POSTGRES_PASSWORD=postgres postgres:16-alpine
```

To summarize some of these properties that we pass to docker to run our image, with the docker run command, and we
customize it to set it up to work with our application:

- p - tell the docker engine which port to forward from the container to the host, in this case we forward the same
  default Postgres 5432 port from the container to the host. The format of that port pair is the container:host
- d - tells the docker engine to start the container as a background non blocking one, that means our shell process will
  not be blocked and we can still run other commands, like for example starting our app
- e - pass environment variables and their values, in this case this is quite important as we use that flag to set the
  username and password variables which the Postgres image is expecting to see to configure them
- `postgres:16-alpine` lastly the docker run command requires one unnamed argument to be provided which is the name of the
  image to use to start the container with

Finally we can start the application and that will now be using the actual Postgres database instance deployed in the
container `postgres`, we are no longer going to be using `H2` for our production application, further more we can even
remove the embedded database `H2` dependency and its drivers from the POM file altogether as well.

```xml
<!-- we can remove this dependency tag from our pom file now, we are no longer going to be needing it as we will not be
using the embedded database, neither in the actual application execution, or in the tests, where remember we implemented
the usage of test containers -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Versioning

Once we have setup the new database for application, we can move onto adding the support for working with adding support
for declaring a structured schema versioning. That is as already mentioned tools like `Liquibase` or `Flyway`, which are
used to incrementally mutate and upgrade our schema when changes arise. The idea is simple each time we need to
implement some change to our application or a new feature, we might need to touch the database schema that means that we
will have to change the schema.

We have two options to change the schema directly, to reflect our desired new state, or to to do what is called a
versioned change. What a versioned changed implies means that we have a list of changes to our schema. Every change that
we have ever done has been recorded, every new change is just like any other old change, in the end it is very easy to
track, revert and manage schema changes. We still have only one schema which represents the final set of applied changes
in order, but it is very easy to pick the list of changes to our schema and revert to any valid state from the past

```xml
<!-- add the following dependency to our POM file, by default liquibase is versioned directly in the spring-parent BOM
file there fore we need not provide any version -->
<dependency>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-core</artifactId>
</dependency>
```

The premise is quite similar to how file versioning is done in source control systems such as git, or svn. That gives us
great flexibility and is invaluable resource in production grade environments, and applications.

First what we need to do is convert our schema to a `liquibase` changelog, remember that we have to track the entire chain
of changes to our schema including the initial state as well, that also includes the actual schema as it exists at this
very moment. We can capture that with a CLI application or use as a docker image, easily by attaching to our database.

First ensure that the `postgres` database container is running and you have at least started the application once, to
generate the schema, the rest will be taken care by `liquibase` it will use that schema to create the initial starting
changelog file along with all change sets that are required to replicate the same database schema

```sh
# this is done again to avoid AccessDenied with liquibase image user, that is because the image user by default is 1001,
# and the one we use by default on the system is with id 1000, that wont allow the image to write to folders created by
# user with id 1000.
$ mkdir -p liquibase-out
$ sudo chown -R 1001:1001 liquibase-out
$ docker run --rm --network container:postgres \
  -v "$PWD/liquibase-out:/liquibase/changelog" \
  --entrypoint sh liquibase/liquibase:5.0.1 \
  -c '
    lpm add postgresql --global &&
    liquibase \
      --url=jdbc:postgresql://localhost:5432/postgres \
      --username=postgres \
      --password=postgres \
      --changelog-file=/liquibase/changelog/db.changelog.xml \
      --default-schema-name=public \
      --exclude-objects=databasechangelog,databasechangeloglock \
      generate-changelog
  '
```

This will produce an xml file which will contain all the necessary steps to re-create our schema as we have it exactly
in our schema.sql file, then we can start modifying it with new changes, having this first step though is highly
important, we need to know the base starting state

Note that we have mounted a volume to the working directory and we have also created that directory beforehand with some
pre-defined permissions that is needed to allow the `liquibase` container to correctly be able to mount this file and
write to it, we shall not re-use the file just copy the contents to our `src/main/resources/schema.xml`

```xml
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
     https://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.23.xsd">
    <changeSet author="Author">
        <createTable tableName="videos">
            <column autoIncrement="true" incrementBy="50" name="id" startWith="3001" type="BIGINT">
                <constraints nullable="false" primaryKey="true" primaryKeyName="videos_pkey" />
            </column>
            <column name="name" type="VARCHAR(128)">
                <constraints nullable="false" />
            </column>
            <column name="description" type="VARCHAR(1024)" />
        </createTable>
    </changeSet>
    <changeSet author="Author">
        <createTable tableName="users">
            <column autoIncrement="true" incrementBy="50" name="id" startWith="401" type="BIGINT">
                <constraints nullable="false" primaryKey="true" primaryKeyName="users_pkey" />
            </column>
            <column name="username" type="VARCHAR(64)">
                <constraints nullable="false" />
            </column>
            <column name="password" type="VARCHAR(128)" />
            <column name="role_id" type="BIGINT">
                <constraints nullable="false" />
            </column>
        </createTable>
    </changeSet>
    <changeSet author="Author">
        <createTable tableName="roles">
            <column autoIncrement="true" incrementBy="50" name="id" startWith="301" type="BIGINT">
                <constraints nullable="false" primaryKey="true" primaryKeyName="roles_pkey" />
            </column>
            <column name="name" type="VARCHAR(32)">
                <constraints nullable="false" />
            </column>
        </createTable>
    </changeSet>
    <changeSet author="Author">
        <createTable tableName="authorities">
            <column autoIncrement="true" incrementBy="50" name="id" startWith="601" type="BIGINT">
                <constraints nullable="false" primaryKey="true" primaryKeyName="authorities_pkey" />
            </column>
            <column name="name" type="VARCHAR(32)">
                <constraints nullable="false" />
            </column>
            <column name="grant" type="VARCHAR(128)">
                <constraints nullable="false" />
            </column>
        </createTable>
    </changeSet>
    <changeSet author="Author">
        <addUniqueConstraint columnNames="name" constraintName="videos_name_key" tableName="videos" />
    </changeSet>
    <changeSet author="Author">
        <addUniqueConstraint columnNames="username" constraintName="users_username_key" tableName="users" />
    </changeSet>
    <changeSet author="Author">
        <addUniqueConstraint columnNames="name" constraintName="roles_name_key" tableName="roles" />
    </changeSet>
    <changeSet author="Author">
        <addUniqueConstraint columnNames="grant" constraintName="authorities_grant_key" tableName="authorities" />
    </changeSet>
    <changeSet author="Author">
        <addUniqueConstraint columnNames="name" constraintName="authorities_name_key" tableName="authorities" />
    </changeSet>
    <changeSet author="Author">
        <createTable tableName="role_authority">
            <column name="role_id" type="BIGINT">
                <constraints nullable="false" primaryKey="true" primaryKeyName="role_authority_pkey" />
            </column>
            <column name="authority_id" type="BIGINT">
                <constraints nullable="false" primaryKey="true" primaryKeyName="role_authority_pkey" />
            </column>
        </createTable>
    </changeSet>
    <changeSet author="Author">
        <addForeignKeyConstraint baseColumnNames="authority_id" baseTableName="role_authority"
            constraintName="fk_role_authority_authority" deferrable="false" initiallyDeferred="false" onDelete="NO
                                                                                                       ACTION"
            onUpdate="NO ACTION" referencedColumnNames="id" referencedTableName="authorities" validate="true" />
    </changeSet>
    <changeSet author="Author">
        <addForeignKeyConstraint baseColumnNames="role_id" baseTableName="role_authority"
            constraintName="fk_role_authority_role" deferrable="false" initiallyDeferred="false" onDelete="NO ACTION"
            onUpdate="NO ACTION" referencedColumnNames="id" referencedTableName="roles" validate="true" />
    </changeSet>
    <changeSet author="Author">
        <addForeignKeyConstraint baseColumnNames="role_id" baseTableName="users" constraintName="fk_users_role"
            deferrable="false" initiallyDeferred="false" onDelete="NO ACTION" onUpdate="NO ACTION"
            referencedColumnNames="id" referencedTableName="roles" validate="true" />
    </changeSet>
</databaseChangeLog>
```

```yaml
# we can put that under the following location, that implies src/main/resources/changelog.xml, the exact directory is
# not strictly decided as long as the structure makes sense and the file is in resources where it will be copied form into
# our jar and classpath, any location and logical file name is going to do just fine
spring:
    liquibase:
        change-log: classpath:changelog.xml
```

Create the `changelog.xml` file, that will include both the schema we just created and the data migration change set later on,
for now the `changelog.xml` file will only include the `schema.xml` file import

```xml
<?xml version="1.1" encoding="UTF-8" standalone="no"?>
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog-ext
     http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd http://www.liquibase.org/xml/ns/dbchangelog
     http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd">
    <include file="schema.xml" relativeToChangelogFile="false" />
</databaseChangeLog>
```

The schema is one part of the database but we also have some data in there as well, next section will show us how to
ensure that we migrate what data we have in this database currently in a more data / declarative oriented way

### Migration

Now what if we also want to retain and migrate the data as well, `liquibase` is not really great for that but based on the
database type we have a few options, what if we want to migrate in a database independent way, such as we do not need to
care if we are hosting our data on a `postgres`, or `mysql` or `mssql` database.

The solution is not meant to replace proper data migration, in a production environment there are other more robust
solutions, but this is more useful to initialize our database with data similarly to how we have done so far with the
data.sql files. After we have done all of that we can start our application again,

```sh
# this is done again to avoid AccessDenied with liquibase image user, that is because the image user by default is 1001,
# and the one we use by default on the system is with id 1000, that wont allow the image to write to folders created by
# user with id 1000.
$ mkdir -p liquibase-data
$ sudo chown -R 1001:1001 liquibase-data

$ docker run --rm --network container:postgres \
  -v "$PWD/liquibase-data:/liquibase/changelog" \
  --entrypoint sh liquibase/liquibase:5.0.1 \
  -c '
    mkdir -p /liquibase/changelog/data &&
    lpm add postgresql --global &&
    liquibase \
      --url=jdbc:postgresql://localhost:5432/postgres \
      --username=postgres \
      --password=postgres \
      --default-schema-name=public \
      --exclude-objects=databasechangelog,databasechangeloglock \
      --changelog-file=/liquibase/changelog/db.data.changelog.xml \
      --dataOutputDirectory=/liquibase/changelog/data \
      --diffTypes=data \
      generate-changelog
  '
```

After running this command we will see similar structure in the `liquibase-data` folder we can take this data folder
that contains these `csv` files and move them to our resources classpath under `src/main/resources`. Then we will make
sure that `liquibase` picks them up for considerations when we boot our application

```plaintext
 data/
   authorities.csv
   role_authority.csv
   roles.csv
   users.csv
   videos.csv
```

Create the `data.xml` file now which will contain references to the csv files which are going to be imported by liquibase,
we are going to reference this `data.xml` file in our changelog file put the include right after the schema file

```xml
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
     https://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-4.23.xsd">
    <!-- Load tables in FK-safe order -->
    <changeSet id="data-001-roles" author="generated">
        <loadData file="data/roles.csv" relativeToChangelogFile="false" tableName="roles" separator="," encoding="UTF-8" />
    </changeSet>

    <changeSet id="data-002-authorities" author="generated">
        <loadData file="data/authorities.csv" relativeToChangelogFile="false" tableName="authorities" separator="," encoding="UTF-8" />
    </changeSet>

    <changeSet id="data-003-users" author="generated">
        <loadData file="data/users.csv" relativeToChangelogFile="false" tableName="users" separator="," encoding="UTF-8" />
    </changeSet>

    <changeSet id="data-004-videos" author="generated">
        <loadData file="data/videos.csv" relativeToChangelogFile="false" tableName="videos" separator="," encoding="UTF-8" />
    </changeSet>

    <changeSet id="data-005-role-authority" author="generated">
        <loadData file="data/role_authority.csv" relativeToChangelogFile="false" tableName="role_authority" separator="," encoding="UTF-8" />
    </changeSet>
</databaseChangeLog>
```

We update the `changelog.xml` file to now include the new data.xml, the final structure of the `src/main/resources` folder
is shown below, or rather the relevant parts that include the `changelog.xml`, `data.xml`, `schema.xml`, and the data folder

```xml
<?xml version="1.1" encoding="UTF-8" standalone="no"?>
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog-ext
     http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd http://www.liquibase.org/xml/ns/dbchangelog
     http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd">
    <include file="schema.xml" relativeToChangelogFile="false" />
    <include file="data.xml" relativeToChangelogFile="false" />
</databaseChangeLog>
```

Here is what the resources folder should look like, we have the data folder filled with the csv files which contain our
data. The data.xml file is meant to reference these files and make sure to create the change set for each one of them.
The schema we already saw in the previous chapter is in charge or creating the schema for our data base. And the
changelog.xml file is the entry point, that includes both the schema and data xml files (the order here is important)

```plaintext
 data/
   authorities.csv
   role_authority.csv
   roles.csv
   users.csv
   videos.csv
󰗀 changelog.xml
󰗀 data.xml
󰗀 schema.xml
```

After all of that we can finally remove or delete the database `postgres` container, do not worry we are not going to lose
our precious data, we will make sure that `liquibase` picks the data and schema files.

```sh
# remove the container and delete it, that will clean up all the data we have in there, including the database, tables,
# schemas and data
$ docker container stop postgres
$ docker container rm -f postgres
$ docker run -d -p 5432:5432 --name postgres -e POSTGRES_USERNAME=postgres -e POSTGRES_PASSWORD=postgres postgres:16-alpine
```

```yaml
# we can delete these from our application yaml file, they are no longer needed we are not going to dump the data and
# schema files form there but use `liquibase` to load them, or we can change the value of init.mode to never from always
sql.init.mode: always -> never
sql.init.data-locations: classpath:/postgres-data.sql
sql.init.schema-locations: classpath:/postgres-schema.sql
```

Now build and start the application, we can inspect the contents of the `liqiubase` system/log table that is going to be
created by `liquibase` in that database - `SELECT * FROM public.databasechangelog LIMIT 500`. We will see all the change
log entries created along side the data entries. this is an abridged version of the actual table, there are many
columns in that table that are not relevant for displaying the essence of the actions that we care about were performed
by `liquibase`.

```plaintext
   │ id                      │ author      │ filename   │ dateexecuted     │ description
1  │ 1771685413670-1         │ Author Name │ schema.xml │ {year}-{mm}-{dd} │ createTable tableName=videos
2  │ 1771685413670-2         │ Author Name │ schema.xml │ {year}-{mm}-{dd} │ createTable tableName=users
3  │ 1771685413670-3         │ Author Name │ schema.xml │ {year}-{mm}-{dd} │ createTable tableName=roles
4  │ 1771685413670-4         │ Author Name │ schema.xml │ {year}-{mm}-{dd} │ createTable tableName=authorities
5  │ 1771685413670-5         │ Author Name │ schema.xml │ {year}-{mm}-{dd} │ addUniqueConstraint constraintName=videos_name_key, tableName=videos
6  │ 1771685413670-6         │ Author Name │ schema.xml │ {year}-{mm}-{dd} │ addUniqueConstraint constraintName=users_username_key, tableName=users
7  │ 1771685413670-7         │ Author Name │ schema.xml │ {year}-{mm}-{dd} │ addUniqueConstraint constraintName=roles_name_key, tableName=roles
8  │ 1771685413670-8         │ Author Name │ schema.xml │ {year}-{mm}-{dd} │ addUniqueConstraint constraintName=authorities_grant_key, tableName=authorities
9  │ 1771685413670-9         │ Author Name │ schema.xml │ {year}-{mm}-{dd} │ addUniqueConstraint constraintName=authorities_name_key, tableName=authorities
10 │ 1771685413670-10        │ Author Name │ schema.xml │ {year}-{mm}-{dd} │ createTable tableName=role_authority
11 │ 1771685413670-11        │ Author Name │ schema.xml │ {year}-{mm}-{dd} │ addForeignKeyConstraint baseTableName=role_authority, constraintName=fk_role_authority_authority, referencedTableName=authorities
12 │ 1771685413670-12        │ Author Name │ schema.xml │ {year}-{mm}-{dd} │ addForeignKeyConstraint baseTableName=role_authority, constraintName=fk_role_authority_role, referencedTableName=roles
13 │ 1771685413670-13        │ Author Name │ schema.xml │ {year}-{mm}-{dd} │ addForeignKeyConstraint baseTableName=users, constraintName=fk_users_role, referencedTableName=roles
14 │ data-001-roles          │ generated   │ data.xml   │ {year}-{mm}-{dd} │ loadData tableName=roles
15 │ data-002-authorities    │ generated   │ data.xml   │ {year}-{mm}-{dd} │ loadData tableName=authorities
16 │ data-003-users          │ generated   │ data.xml   │ {year}-{mm}-{dd} │ loadData tableName=users
17 │ data-004-videos         │ generated   │ data.xml   │ {year}-{mm}-{dd} │ loadData tableName=videos
18 │ data-005-role-authority │ generated   │ data.xml   │ {year}-{mm}-{dd} │ loadData tableName=role_authority
```

### Native

After we have already seen how to convert and bundle our application into an executable that can be run anywhere, and
also how to scale it horizontally by running multiple copies of it all independent of each other. We are going to focus
on performance, take a look what are our options. We are going to focus more specifically onto something called `GraalVM`.

What really is `GraalVM` ? Well for years the java eco system was constantly bashed by other communities and even its
own, that it does not come on par with binary based applications that do not require an interpreted environment like the
java virtual machine. Binary applications that execute directly machine code for the target platform that they were
compiled for were far more performant than java applications which run inside a virtual machine and for all intents and
purposes is an interpreted language with a runtime byte code optimization techniques.

Deploying thousands of applications daily on cloud providers started to add up to people's cloud bills, some java
applications take quite a while to start up, and some of that is the Java virtual machine booting up the application and
itself. There has to be a better more performant way to do these things.

A new player that had just popped up into the cloud sphere were the so called lambda functions, these are as the name
suggests very small `functions` of sorts that can boot up an application on demand, instead of having your application
run inside a dedicated cluster node as a container, the application is started and destroyed on demand, there is no idle
time. This is all good but for a java application that is based off of a virtual machine, that was not possible without
some significant changes in the ecosystem

As it turns out a new player comes into town again, and that was Oracle, they created GraalVM the idea and target is
simple it will support any language under the sun - C++, Java, Javascript, Ruby, C and so on. Soon after the spring
native project was born around 2019, to support this, the idea of the spring team was to help with the transition to
this new GraalVM technology.

`Just to put an emphasis on that java has been since the dawn of time an interpreted language that is, our source code
gets translated into bytecode, that byte code is interpreted by the java virtual machine at runtime. During the runtime
of an application the virtual machine is capable of performing optimizations such as hot-spot compilation, that means
that the virtual machine i s able to optimize hot paths in our code, compiling them into machine code directly (for the
target platform). But at its heart the Java language is interpreted, by the virtual machine`

So to translate our application into native representation that can be run on the GraalVM we have to do a few trade
offs, such as limited support for reflection, and limited support for runtime proxies, as well some special handling of
external resources.

- First GraalVM takes care of reducing the total footprint of the program, it employs a principal called `reachability`,
  what that means is that any code path that it deems is not reachable is completely cut off the final binary. That
  implies that for example if we pull an entire library that we use just parts of, only the parts that are going to ever
  be used are going to be part of the final binary image.
- Reflection's capabilities are also reduced, that is because some of the information for reflection is completely
  dropped and it is possible that at runtime the full capabilities of the reflection in Java are not available without
  some preliminary work
- Proxies - these are a familiar issue, java has for long supported runtime proxies but those are not possible with
  GraalVM, instead all proxies have to be known and ready at `compile` time

All of that implies that accessing code that was easily accessible and reachable before through reflection, like
invoking methods, inspecting properties of objects at runtime and a whole host of useful reflection features are now
much harder to achieve, that is a huge blow to an ecosystem like Spring because it takes a big advantage of that
specific Java feature a lot.

That is why in recent years spring itself has taken a great amount of care to reduce its use of dynamic proxies and
most of these are replaced with actual beans, that serve as the proxy objects, for example instead of wrapping around a
bean (silently), we are often required to provide implementations that serve the purpose of a proxy object or sometimes
Spring generates them, but does not strictly rely on the runtime proxies that it once did so heavily.

Running an application with the GraalVM is a bit different than what we are used to and naturally it is using a
completely different set of tools to do that, for example when we were building the app for use with the Java virtual
machine, we were using the spring maven plugin to build the `UBER-JAR`, here we are using a completely different plugin
called the native maven plugin, the name should be enough to imply that this is meant for running the java application
as a native binary instead.

So for example let us take a few examples of what is going on, when we take two core concepts in spring and try to
explain how they are handled by spring to prepare your application to be run on the GraalVM. First it is curcial to know
that unlike with the regular java virtual machine, where most of the spring context and code paths get loaded on demand
lazily. With native applications that aim to run on the GraalVM that is not possible, everything has to be ready to be
analyzed and compiled at build time that is what we call static analysis.

That means that GraalVM has to have a complete overview of the full application code-path at build and compile time
dynamic object creation and runtime dynamic behavior has to be converted into something that is visible & available at
compile time and analysis time so the GraalVM can perform the proper static code analysis on it - that really means
quite literally `everything`

So what spring does is provide a helping hand to GraalVM by converting our application to exactly that, it converts our
application to be ready for static analysis, What does that imply ? Basically everything that was so far done at runtime

For example creating beans, on demand, loading classes, and initializing and binding configuration properties and what
not is now done upfront - What does that translate into ? Simply put Spring generates code for us that is representative
of our code but converted into code that is easier for the GraalVM to reason about and analyze. So Spring will generate
additional java code that is reflective (pun-intended) of our annotated code, see a few examples below

- `@Configuration` - these are configuration beans that usually are in charge of providing some sort of a @Bean or a
  general configuration that needs to be analyzed and instantiated by spring. This initialization and construction spring
  usually does at runtime. That does not work with native assembly and compilation however. Instead what spring does
  internally to prepare these classes ahead of time, it analyzing the annotations and creates a stub source files and
  classes that are then passed to the GraalVM compiler. That way the runtime initialization is converted into one that is
  happening at compile time

    ```java
    @Configuration(proxyBeanMethods = false)
    class MyConfig {
        @Bean Greeter greeter() { return new Greeter(); }
    }
    ```

    ```java
    final class MyConfig__BeanDefinitions {

        static BeanDefinition myConfig() {
            RootBeanDefinition bd = new RootBeanDefinition(MyConfig.class);
            bd.setInstanceSupplier(MyConfig::new);
            return bd;
        }

        static BeanDefinition greeter() {
            RootBeanDefinition bd = new RootBeanDefinition(Greeter.class);
            bd.setInstanceSupplier(() -> /* call factory method */);
            return bd;
        }
    }
    ```

- `Proxies` - spring makes a huge use out of proxies, however by default those are the java runtime proxies which are
  created on demand and wrap around the actual instances of the objects in our code, that is not possible with GraalVM,
  what spring does it to replace these proxies, with `CGlib` proxies with generate actual bytecode and inject that byte code
  in our class files

    ```java
      public interface Catalog {
          @Cacheable("prices")
          BigDecimal price(String sku);
      }

      @Service
      class CatalogImpl implements Catalog {
          public BigDecimal price(String sku) { return lookupInDb(sku); }
      }

      System.out.println(ctx.getBean(Catalog.class).getClass());
      // in default JVM that will produce by default a runtime proxy
      // typically prints something like - class com.sun.proxy.$Proxy
    ```

    ```markdown
    <!-- Spring Boot explains that `CGLIB` proxy bytecode is generated at build time for native images, and you can inspect
         it the target folder where the class files are found, so for example, we might see a file looking like that -->

    CatalogImpl$$SpringCGLIB$$0.class
    ```

- `Dependency Injection (DI)` - spring would convert that into actually creating our beans manually, generating java
  code that would invoke the constructor for the bean, usually with native images spring suggests that we use constructor
  injection, that is much easier to reason about, and must easier for spring to generate the code that would construct the
  object, avoid using setters and especially having just private properties and no exposed public api that would allow you
  to construct the object in the first place.

    ```java
    @Service
    class OrderService {

    private final PaymentClient paymentClient;
    private final OrderRepository orderRepository;

    OrderService(PaymentClient paymentClient, OrderRepository orderRepository) {
        this.paymentClient = paymentClient;
        this.orderRepository = orderRepository;
    }

    public void placeOrder(String userId) {
        paymentClient.charge(userId);
        orderRepository.save(userId);
    }
    }
    ```

    ```java
    // conceptual generated code shape
    OrderService os = new OrderService(
        beanFactory.getBean(PaymentClient.class),
        beanFactory.getBean(OrderRepository.class)
    );
    ```

After having examined the top level, lets try to convert our Application to add support for the GraalVM generation.
First lets add the following plugins to our pom file, these are important because we need to ensure that we have both
the build native plugin and the hibernate configuration that is to disable the runtime lazy proxies that hibernate
usually uses.

```xml
<plugins>
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
    <plugin>
        <groupId>org.graalvm.buildtools</groupId>
        <artifactId>native-maven-plugin</artifactId>
    </plugin>
    <plugin>
        <groupId>org.hibernate.orm.tooling</groupId>
        <artifactId>hibernate-enhance-maven-plugin</artifactId>
        <executions>
            <execution>
                <id>enhance</id>
                <goals>
                    <goal>enhance</goal>
                </goals>
                <configuration>
                    <enableDirtyTracking>true</enableDirtyTracking>
                    <enableLazyInitialization>true</enableLazyInitialization>
                    <enableAssociationManagement>true</enableAssociationManagement>
                </configuration>
            </execution>
        </executions>
    </plugin>
</plugins>
```

Finally run the build image command, that will ensure that an image is created for docker, that image will contain the
native binary that is built for our application, you also will be able to see that under target/classes we have a bunch
of new classes that are created by the spring ahead of time processor. So under the `org.springframework` we can see the
different folders that were created these are mostly generated by the Spring AOP that is generating our code prepared
for GraalVM, to analyze and build.

```xml
target/classes/org/springframework
drwxrwxr-x aop
drwxrwxr-x boot
drwxrwxr-x context
drwxrwxr-x data
drwxrwxr-x orm
drwxrwxr-x security
drwxrwxr-x transaction
drwxrwxr-x web
```

The command below will ensure that the native image is built and packaged into an image that is going to be deployed to
your local docker repository, the image will tagged and created under the name of your application. After executing the
command below just run `docker image ls` and identify which of the images is yours.

Note that without providing the profile flag, an image will still be built, however, that image will be normal JVM based
one, that is we have already actually done this in previous older chapters where we split the application into its
building blocks and saw how to have the different application components split into layers, where we had a layer for
dependencies, one for the resources and the application code itself.

Here is a little bit of a hint that we can provide before building the java application into a native one, we can start
the JVM with a hint that will generate a hint by leveraging the native image agent, that will help us ensure that as
much information is recorded for our application while it starts and works, that can be quite beneficial before we
decide to generate the native image.

```sh
# this will require JDK with a native image agent, that means a GraalVM enabled virtual machine, meaning that, we need
# to start our java binary application with that GraalVM virtual machine, instead of the regular one under $JAVA_HOME
# while the application is running it is important to exercise the different paths in the application, that might not
# immediately be analyzed by spring AOP, that can greatly improve the compilation of the native image
$ /Library/Java/JavaVirtualMachines/graalvm-25.jdk/Contents/Home/bin/java \
    -Dspring.aot.enabled=true \
    -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image/ \
    -jar target/yourapp.jar
```

`Ensure that you install GraalVM for your machine type of architecture, use uname -m to see what type or arch you have,
either arm64, or x86_64 or antoher one`

The native profile does exactly the same thing, there is conceptually no difference, the application image is still
built from layers, where each layer again represents the same building block components of your app.

```sh
# that is all that we need to run here, this will both create the image that we have seen used, already and also do that
# for the native profile, without providing the profile what this does is simply build the docker layered image that we
# had a look at in previous chapters already, adding the native profile with the -P flag, enables the GraalVM native
# compile, in the end we will still get a docker image with the native package
./mvnw -Pnative spring-boot:build-image
```

Here is what the output of the command above might look like, that is the GraalVM analyzing our application, below you
can actually see the difference between the layers that the native image is producing, instead of having layers, like
dependencies, snapshot-dependencies, and application, we actually see different types of layers being created and into
the image, since the final build image contains only the actually reachable code meaning - not only our dependencies but
also the code dependencies on the java standard library and runtime.

```plaintext
[INFO] [creator] ================================================================================
[INFO] [creator] GraalVM Native Image: Generating 'com.spring.demo.core.DemoApplication' (executable)...
[INFO] [creator] ================================================================================
[INFO] [creator] --------------------------------------------------------------------------------
[INFO] [creator] [2/8] Performing analysis...  [******]                          (28.5s @ 3.50GB)
[INFO] [creator]    39,923 reachable types   (92.3% of   43,257 total)
[INFO] [creator]    60,456 reachable fields  (64.8% of   93,302 total)
[INFO] [creator]   196,564 reachable methods (63.5% of  309,656 total)
[INFO] [creator]    12,132 types, 2,161 fields, and 18,883 methods registered for reflection
[INFO] [creator]       132 types,   206 fields, and   119 methods registered for JNI access
[INFO] [creator]         4 native libraries: dl, pthread, rt, z
[INFO] [creator] --------------------------------------------------------------------------------
[INFO] [creator] Produced artifacts:
[INFO] [creator]  /layers/paketo-buildpacks_native-image/native-image/com.spring.demo.core.DemoApplication (executable)
[INFO] [creator]  /layers/paketo-buildpacks_native-image/native-image/libawt.so (jdk_library)
[INFO] [creator]  /layers/paketo-buildpacks_native-image/native-image/libawt_headless.so (jdk_library)
[INFO] [creator]  /layers/paketo-buildpacks_native-image/native-image/libawt_xawt.so (jdk_library)
[INFO] [creator]  /layers/paketo-buildpacks_native-image/native-image/libfontmanager.so (jdk_library)
[INFO] [creator]  /layers/paketo-buildpacks_native-image/native-image/libfreetype.so (jdk_library)
[INFO] [creator]  /layers/paketo-buildpacks_native-image/native-image/libjava.so (jdk_library_shim)
[INFO] [creator]  /layers/paketo-buildpacks_native-image/native-image/libjavajpeg.so (jdk_library)
[INFO] [creator]  /layers/paketo-buildpacks_native-image/native-image/libjvm.so (jdk_library_shim)
[INFO] [creator]  /layers/paketo-buildpacks_native-image/native-image/liblcms.so (jdk_library)
[INFO] [creator] ================================================================================
[INFO] [creator] Reusing cache layer 'paketo-buildpacks/bellsoft-liberica:native-image-svm'
[INFO] [creator] Adding cache layer 'paketo-buildpacks/bellsoft-liberica:native-image-svm'
[INFO] [creator] Reusing cache layer 'paketo-buildpacks/syft:syft'
[INFO] [creator] Adding cache layer 'paketo-buildpacks/syft:syft'
[INFO] [creator] Adding cache layer 'paketo-buildpacks/native-image:native-image'
[INFO] [creator] Reusing cache layer 'buildpacksio/lifecycle:cache.sbom'
[INFO] [creator] Adding cache layer 'buildpacksio/lifecycle:cache.sbom'
[INFO] Successfully built image 'docker.io/library/demo:0.0.1-SNAPSHOT'
```

We can also build a native image as well, instead of building that into a docker image we build it directly onto the
local machine, that can be quite a bit more involved because you need to have GraalVM installed locally, that is because
the actual GraalVM will be responsible for analyzing, compiling and generating the actual native binary of course.

```sh
# this is another very useful command, what it does is to compile the binary locally, so instead of having a docker
# image this will create the compiled binary locally using GraalVM, that implies that you have to have set the
# correct value for the `GRAALVM_HOME` environment variable to actually work.
./mvnw -Pnative native:compile
```

`Keep in mind that not all applications might scale up with GraalVM it is relatively new approach to building
applications, there fore you can not expect that every third party library out side of the core spring framework will
provide support for it.`

### Scaling
