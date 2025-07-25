# Introduction

Spring boot is an open source java based framework used to create micro services, it is developed by Pivotal it is
easy to create a stand alone and production read spring application, using spring boo. Spring Boot enterprise ready
application that you can just run.

## Document Prerequisites

This introduction is written to developers and readers who already have experience with Java, Spring, Maven and
Gradle, you can easily understand the concepts of Spring Boot, if you have the aforementioned knowledge already. If
you are a beginner we suggest you to go through some resources which serve as introduction to the topics and
technologies mentioned above before you start reading this resource.

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

# Spring boot

This section will teach you how to create a Spring Boot application using Maven and Gradle.

## Command line interface

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


Project types (* denotes the default)
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

## Getting started

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

## Packaging application

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

```
BOOT-INF/
    ├── classes/      # Your compiled application classes
    ├── lib/          # All dependency JARs
META-INF/
    ├── MANIFEST.MF   # Defines the launcher class
org/springframework/boot/loader/
    ├── JarLauncher.class  # Spring Boot's custom launcher
```

- Spring Boot uses `JarLauncher` (from `spring-boot-loader`) to **bootstrap** the application.
- This launcher knows how to load classes from `BOOT-INF/classes` and `BOOT-INF/lib`, unlike the standard Java classloader.
- All dependencies are **embedded** in the JAR (no need for an external `lib` folder).
- Avoids `classpath` issues when deploying.

How does it work ? If we compare the standard jar packaging plugin provided by maven to the spring one. The Standard
maven plugin would package the JAR without including the dependencies or libraries that our project depends on, just the
compiled classes for our project, the structure of a standard jar might look something like that

```
your-app.jar
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
   $ java -jar your-app.jar
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

| Feature                | `maven-jar-plugin`                  | `spring-boot-maven-plugin`          |
|------------------------|-------------------------------------|-------------------------------------|
| **Dependencies**       | Not included (external `lib/`)      | Embedded in `BOOT-INF/lib/`         |
| **Main-Class**         | Directly points to your class       | Delegates to `JarLauncher`          |
| **Class Loading**      | Standard JVM classloader            | Custom `LaunchedURLClassLoader`     |
| **Runnable**           | Requires manual classpath setup     | Works with `java -jar`              |
| **Web Server**         | Needs external Tomcat/Jetty         | Embedded server (Tomcat/Netty/etc.) |

There is another type of JAR archive, called a WAR application, this is another type of JAR that is deployed on a web
server, unlike the JAR file, the WAR has a different format and specification this is because the web server where this
WAR will be deployed requires a specific format and structure to be present.

A **WAR** is a specialized JAR used for **web applications** (e.g., Servlet-based apps like Spring MVC). It follows a
strict structure defined by the `Java EE/Jakarta EE` spec and is deployed to **external servers** (e.g., Tomcat, Jetty,
WildFly).

```plaintext
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
# Tomcat unpacks the WAR and starts the app.
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
      <scope>provided</scope>  <!-- Server will supply this -->
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
|------------|---------|------|---------|----------|----------------------------------|
| `compile`  | ✅      | ✅   | ✅      | ✅       | Spring, Hibernate                |
| `provided` | ✅      | ✅   | ❌      | ❌       | Servlet API, Tomcat-shared libs  |
| `runtime`  | ❌      | ✅   | ✅      | ✅       | JDBC drivers                     |
| `test`     | ❌      | ✅   | ❌      | ❌       | JUnit, Mockito                   |
| `system`   | ✅      | ✅   | ❌      | ❌       | Avoid (non-portable JARs)        |
| `import`   | ❌      | ❌   | ❌      | ❌       | BOMs for dependency management   |

1. **`compile` (Default)**
   - Available in all phases (compile, test, runtime).
   - Packaged in the final JAR/WAR.
   - **Example**: `spring-core` (needed at runtime).

3. **`runtime`**
   - Not needed for compilation, but required at runtime.
   - Packaged in the final artifact.
   - **Example**: `mysql-connector-java` (loaded dynamically).

2. **`provided`**
   - Available during compilation and testing, but **not packaged**.
   - Assumed to be provided by the runtime (e.g., a server).
   - **Example**: `javax.servlet-api` (provided by Tomcat).

5. **`system`** (Avoid if possible)
   - Like `provided`, but points to a local JAR file.
   - **Example**: Rarely used (non-portable).

6. **`import`** (Special case)
   - Used in `<dependencyManagement>` to inherit versions from another POM.
   - **Example**: `spring-boot-dependencies` (BOM files).

4. **`test`**
   - Only available during testing (`src/test`).
   - Never packaged.
   - **Example**: `junit` (test-only dependency).

## Build system

In Spring Boot, choosing a build system is an important task. We recommend Maven or Gradle as they provide a good
support for dependency management. Spring does not support well other build systems.

Spring Boot team provides a list of dependencies to support the Spring Boot version for its every release. You do not
need to provide a version for dependencies in the build configuration file. Spring Boot automatically configures the
dependencies version based on the release. Remember that when you upgrade the Spring Boot version, dependencies also
will upgrade automatically. This is achieved by the parent BOM spring dependency which you specify, the BOM - which
stands for bill of materials, is a set of pre-defined list of dependencies. The spring BOM has a version, which carries
with it the correct combination of all spring dependency versions.

## Best practices

Spring Boot does not have any code layout to work with. However, there are some best practices that will help us. This
chapter talks about them in detail.

### Default packages

A class that does not have any package declaration is considered as a **default package**. Note that generally a default
package declaration is not recommended. Spring Boot will cause issues such as malfunctioning of Auto Configuration or
Component Scan, when you use default package.

### Typical layout

It is generally accepted that at the root name of the artifact id for the project - `com.myapp.project`, we should have
the Application.java file, which contains the Main class, this is due to the fact that any class annotated with the
`@SpringBootApplication` annotation, will serve as entry point for scanning the packages and beans under it, in that case
if we put the main class at `com.myapp.project`, then everything under this root package name will be considered for
injection by the spring components.

### Auto-configuration

As mentioned in spring we have a concept called an Auto configuration, this is a process by which spring picks specific
pre-defined beans on the class path. The way it works is by defining a special type of file that contains the full path
to the class that is considered to be the auto-configuration class, this file is called the
`org.springframework.boot.autoconfigure.AutoConfiguration.imports`, this file contains lines of text which define the
different classes spring autoconfigure should consider when starting the application, this is different compared to
classes annotated with @Configuration, which we will talk about later.

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

It is prudent to note that you can also create your own imports file, by simply putting in tin the src/main/resources
folder of your own library of project, then when it gets built it will be put into the META-INF folder in your final
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

On another hand we have the @Configuration annotation, that is a bit different since it is discovered through component
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

## Application properties

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
### Command line

Spring Boot application converts the command line properties into Spring Boot Environment properties. Command line
properties take precedence over the other property sources. By default, Spring Boot uses the 8080 port number to start
the Tomcat. Let us learn how change the port number by using command line properties. There are a few ways to override
certain properties on the command line, such that they will take precedence over the ones with which the app was packaged

```sh
# Values will override any `server.port` or `spring.datasource.url` defined in `application.yml`.
$ java -jar your-app.jar --server.port=8081 --spring.datasource.url=jdbc:mysql://localhost:3306/mydb

# Properties in `custom-config.yml` will **override** those in `application.yml`.
# Add `classpath:` for files in the classpath (e.g., `classpath:override.properties`).
$ java -jar your-app.jar --spring.config.location=file:/path/to/custom-config.yml

# Merge additional files without replacing the packaged `application.yml`:
$ java -jar your-app.jar --spring.config.additional-location=file:/path/to/extra.properties
```

### Environment

There are several ways to override the properties through the environment,

#### Core Spring Boot Variables

| Env Variable                     | Effect                                                                 |
|----------------------------------|-----------------------------------------------------------------------|
| `SPRING_APPLICATION_JSON`        | Injects properties as JSON (e.g., `{"server":{"port":8081}}`).        |
| `SPRING_CONFIG_LOCATION`         | Overrides config file path (e.g., `file:/config/override.yml`).       |
| `SPRING_CONFIG_ADDITIONAL_LOCATION` | Adds extra config files without replacing defaults.                  |
| `SPRING_PROFILES_ACTIVE`         | Sets active profiles (e.g., `prod,debug`).                            |
| `SPRING_CLOUD_CONFIG_ENABLED`    | Disables/configures Spring Cloud Config (e.g., `false`).              |


#### Logging and Debugging

| Env Variable             | Effect                                                                 |
|--------------------------|-----------------------------------------------------------------------|
| `DEBUG`                  | Enables debug logs if set to `true` or `1`.                           |
| `LOGGING_LEVEL_ROOT`      | Sets root log level (e.g., `DEBUG`, `INFO`).                          |
| `LOGGING_LEVEL_org.springframework.web` | Sets package-specific logging (e.g., `DEBUG`).              |
| `SPRING_OUTPUT_ANSI_ENABLED` | Force ANSI color output (e.g., `ALWAYS`).                         |

#### Server and Database Overrides

| Env Variable                     | Effect                                                                 |
|----------------------------------|-----------------------------------------------------------------------|
| `SERVER_PORT`                    | Overrides `server.port` (e.g., `8081`).                               |
| `SERVER_SERVLET_CONTEXT_PATH`    | Sets the context path (e.g., `/api`).                                 |
| `SPRING_DATASOURCE_URL`          | Overrides DB URL (e.g., `jdbc:mysql://db:3306/app`).                  |
| `SPRING_DATASOURCE_USERNAME`     | Sets DB username.                                                     |
| `SPRING_DATASOURCE_PASSWORD`     | Sets DB password.                                                     |


Replace `.` with `_` and uppercase (e.g., `server.port` → `SERVER_PORT`). Nested keys: `spring.datasource.url` →
`SPRING_DATASOURCE_URL`.

#### Overriding with `JAVA_TOOL_OPTIONS`

If you can’t use Spring-specific vars, pass the Java system properties, which will be picked up by spring, since it does
a lookup in order of environment variables, system variables, and then proceeds with startup arguments

```sh
export JAVA_TOOL_OPTIONS="-Dserver.port=8081 -Ddebug=true"
```

### Properties file

Properties files are used to keep 'N' number of properties in a single file to run the application in a different
environment. In Spring Boot, properties are kept in the **application.[properties/yml]** files under the classpath. The
application.properties file is located in the **src/main/resources** directory. The code for sample
**application.properties** and the **application.yml** file are given below:

```properties
server.port=9090
spring.application.name=demoservice
```

```yml
spring:
 application:
 name: demoservice
server:
 port: 9090
```

### Value annotation

The @Value annotation is used to read the environment or application property value in Java code. The syntax to read the
property value is shown below:

```java
@Value("${property_key_name}")
private String applicationName;
```
