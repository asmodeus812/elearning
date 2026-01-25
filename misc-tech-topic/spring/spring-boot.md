- spring framework

- `Boot vs Framework` - spring is the core library, it contains all the building blocks that are used by the spring
  framework to achieve results, the framework on the other hand is the collection of these components along with the
  spring portfolio or also called starters

- `Spring Parent BOM` - the bill of materials or the parent spring bom is what tells your application what versions of
  every possible library or component to use. It contains an ungodly amount of dependencies along side their versions
  for each release of spring. It is used in your build system to tell it which dependency version needs to be used

- `spring starters` - these basically comprise of a set of pre-defined combination of libraries or components (all of
which are also declared in the BOM with their versions) that work together to achieve a specific goal or task -
security, data base communication & management, web sockets etc. They ONLY declare the components your app needs NOT
their versions, the versions stay only in the parent spring POM - the bill of materials.

- `Spring Boot packaging` - the default way of packaging a spring web app is to use the spring boot maven plugin, that
will ensure that your code is packaged along side embeded web server that is going to be used to start your app
(Tomcat, Jetty, WildFly)

- `Spring dependency injection` - the most important feature of spring is its container dependency injection, it manages java beans and their lifecycle, allows us to

- `Auto-configuration imports` - spring provides means of allowing users to register their configuration early using
the `org.springframework.boot.autoconfigure.AutoConfiguration.imports` file located in `src/main/resources`, users
can put their configuration classes there, those will be instantiated quite early when the spring container starts

- @Con

- `PlatformTransactionManager` - the core transaction manager, it has multiple implementations, some of which are `JpaTransactionManager`, `JtaTransactionManager`,
