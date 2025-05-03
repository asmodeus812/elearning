# Introduction

Containers are changing how everyone in the IT industry does their job. Containers initially entered the scene on
developers laptops helping them develop applications more quickly than they could with virtual machines, or by
configuring a laptop's operating system. As containers became more common in development environments their use began to
expand. Once limited to laptops and small development labs, containers worked their way into enterprise. Within a couple
of years containers progressed to the point that they are powering massive production workloads like Github.

## Container platform

A container platform is an application platform that uses containers to build deploy serve and orchestrate the
application running inside it. OpenShift uses two primary tools to serve applications in containers a container runtime
to create containers in Linux and an orchestration engine to manage a cluster of servers or also called nodes, these
servers could be actual physical machines, virtual machines or IOT devices, running the containers.

### Containers in OpenShift

A container runtime works on a Linux server to create and manage containers. For that to make sense we need to look at
how containers function when they are running on a Linux system. In subsequent sections we will dig deeply into how
containers isolate applications in OpenShift. To start, you can think of containers as discrete, portable scalable units
for applications. Containers hold everything required for the application inside them to function. Each time a container
is deployed it holds all the libraries and code needed to its application to function properly. Apps running inside a
container can only access the resources in the container. The applications in the container are isolated from anything
running in other containers or on the host. Five types of resources ar isolated with containers.

- Mounted filesystems.
- Shared memory resources
- Hostname and domain names
- Network resources (IP addresses, MAC addresses, memory buffers)
- Process counters

We will investigate each one of those separately, throughout the next sections. In OpenShift the service that handles
the creation and management of containers is docker. Docker is a large active open source project started by Docker, Inc

- company. The resources that docker uses to isolate processes in containers all exist as part of the Linux kernel.
  These resources include things like SELinux, Linux namespaces and control groups (cgroups), which will be covered later
  on in the sections in detail. In addition to making these resources much easier to use, docker has also added several
  features that have enhanced its popularity and growth.

- Portability - Earlier attempts at container formats were not portable between hosts running different operating
  systems. This container format is now standardized as part of the Open Container Initiative.
- Image reuse - any container image can be reused as the base for other container images.
- Application centric API - the API and command line tooling allow developers to quickly create update and delete
  containers. This is reflected in the API of the docker engine, as well as the API of Kubernetes.
- Ecosystem - Docker Inc, maintains a free public hosting environment for container images it now contains several
  hundred thousand images.

### Orchestrating Containers

Although the docker engine manages containers by facilitating Linux kernel resources, it is limited to a single host
operating system. Although a single server running containers is interesting, it is not a platform that you can use to
create robust applications. To deploy highly available and scalable applications you have to be able to deploy
applications containers across multiple servers. To orchestrate containers across multiple servers effectively you need
to use a container orchestration engine, an application that manages a container runtime across a cluster, of hosts to
provide a scalable application platform OpenShift uses Kubernetes as its container orchestration engine, Kubernetes is
an application open source project that was started by Google, in 2015 it was donated to the Cloud Native Computing
Foundation. Kubernetes employs a master/node architecture, Kubernetes master servers maintain the information about the
server cluster and nodes run the actual application workloads. It is a great open source project. The community around
it is quickly growing and incredibly active. It is consistently one of the most active projects on github. But to
realize the full power of a container platform, it needs a few additional components. This is where OpenShift comes in,
it uses docker and Kubernetes, as a starting point for its design. But to be a truly effective container platform it
adds a few more tools to provide a better experience for users.

## Examining the architecture

OpenShift uses Kubernetes master/node architecture as the base point. From there it expands to provide additional
services that a good application platform needs to include out of the box.

### Integrating container images

In a container platform like OpenShift container images are created when applications are deployed or updated, to be
effective that container image have to be available quickly on all the application nodes in a cluster. To do this
OpenShift includes an integrated image registry as part of its default configuration. An image registry is a central
location that can serve container images to multiple locations. In OpenShift the integrated registry runs in a
container. In addition to providing tightly integrated images access OpenShift works to make access to the applications
more efficient.

### Accessing applications

In Kubernetes containers are created on nodes using components called pods. There are some distinctions that we will
discuss in more depth in next sections, but they are often similar. When an application consists of more than one pod,
access to the application is managed through a component called a service. A service is a proxy that connects multiple
pods and maps them to an IP address on one or more nodes in the cluster. IP addresses can be hard to manage and share
especially when they are behind a fire wall. OpenShift helps to solve this problem by providing an integrated routing
layer. The routing layer is a software load balancer. When an application is deployed in OpenShift a DNS entry is
created for it automatically. That DNS record is added to the load balancer and the load balancer interfaces with the
Kubernetes service to efficiently handle connections between the deployed applications and its users. With applications
running in pods across multiple nodes and management requires coming from the master node there a lot of communication
between servers in an OpenShift cluster. You need to make sure that traffic is properly encrypted and can be separated
when needed.

### Handling network traffic

OpenShift uses a software defined networking (SDN) Solution to encrypt and shape network traffic in a cluster. OpenShift
SDN, solution that uses `Open vSwitch `. Other SDN solutions are also supported, this will be examined in depth in
future sections. Now that you have a good idea of how OpenShift is designed let us look at the life cycle of an
application in an OpenShift cluster.

## Examining an application

OpenShift has workflows that are designed to help you manage you applications through all phases of its lifecycle -
build, deploy, upgrade and retirement.

### Building an application

The primary way to build application is to use a builder image. This process is the default workflow in OpenShift and
its what you will use in next section to deploy your first application in OpenShift. A builder image is a special
container image that includes applications and libraries needed for an application in a given language. In next section
we will deploy a PHP web application. The builder image you will use for your first deployment includes the Apache web
server and the PHP language libraries - things needs to run this type of application. The build process takes the source
code for an application and combines it with the builder image to create a custom application image for the application.
The custom application image is stored in the integrated registry, where it is ready to be deployed and served to the
application users.

### Deploying and serving applications

In the default workflow in OpenShift applications deployment is automatically triggered after the container image is
build and available. The deployment process takes a newly created application image and deploys it on one or more nodes.
In addition the application pods a service is also created, along with a DNS route in the routing layer. Users are able
to access the newly created application through the routing layer after all components have been deployed. App upgrades
use the same workflow, when an upgrade is triggered a new container image is created and the new application version is
deployed. Multiple upgrade processes are available, we will check them out in future sections.

That is how OpenShift works at a high level we will dig much much deeper into all of these components and mechanisms
over the course of future sections. Now that we are armed with the knowledge of OpenShift let us talk about some of the
things container platforms are good and not so good at doing

## Use case for platforms

The technology in OpenShift is pretty cool, but unless you can tie a new technology to some sort of benefit to your
mission it is hard to justify investigating it, in this section, we will take a look at some of the benefits of
OpenShift can provide.

## Technology use cases

If you stop and think about it for a minute, you can hand the major innovations in IT on a timeline of people seeking
more efficient process isolation. Starting with mainframes, we were able to isolate applications more effectively with
the client-server model and the x86 revolution. That was followed by the virtualization revolution. Multiple virtual
machines can run on a single physical server. This give administrators better density in their datacenters while still
isolating processes from each other. With virtual machines each process was isolated in its own virtual machine. Because
each virtual machine has a full operating system and a full kernel, mut have all the filesystem required for full
operating system. That also means it must be patched managed and treated like traditional infrastructure. Containers are
the next step in this evolution. An application container holds everything the application needs to run - the source
code, the libraries, and the configurations and information about connecting to shared data sources.

What containers do not contain is equally important. Unlike virtual machines, containers are all run on a single, shared
kernel. To isolate the application containers use components inside the kernel. Because containers do not have to
include a full kernel to serve their application, along with al the dependencies of an operating system, they tend to me
much smaller than an equivalent virtual machine. For example whereas a typical virtual machine starts out with a 10GB or
larger disk, the container image could be as small as 100MB. Being smaller comes with a couple of advantages. First
portability is enhanced. Moving a 100MB from one server to another, is much easier than doing the same for multi
gigabyte images. Second because starting a container does not include booting up an entire kernel, the startup process
is much faster. Starting a container is typically measured in milliseconds as opposed to seconds or minutes for virtual
machines.

## Businesses use cases

Modern business solutions must include time or resource savings as part of their design. Solutions today have to be able
to use human and computer resource more efficiently than in the past. Containers ability to enable both types of savings
is one of the major reasons they have exploded on the scene the way they have.. If you compare a server that is using
virtual machines to isolate processes to one that is using containers to do the same thing, you will notice a few key
differences:

- Containers consume server resources more effectively. Because there is a single shared kernel for all containers on a
  host, instead of multiple virtualized kernels, in a virtual machine, more of the servers' resources are used to serve
  applications instead of for platform overhead.

- App density increases with containers. Because the basic unit used to deploy applications is much smaller than the
  unit for virtual machines, more applications can fit per server. This means more applications require fewer servers to
  run.

## Invalid use cases

An ever increasing number of workloads are good fits for containers. The container revolution started with pure web
applications but now includes command line tools, desktop tools and even relation databases. Even with the massive
growth of use cases for containers in some situations they are not the answer. If you have a complex legacy application,
be careful when deciding to break it down and convert it to a series of containers. If an application will be around for
18 months and it will take 9 months of work to properly containerized it you may want to leave it where it is.
Containers solution began in the enterprise IT world. They are designed to work with most enterprise grade storage
systems and network solutions, but they do not work with all of them easily. Some applications are always going to be
very large, very resource intensive monolithic applications, examples are software used to run HR departments and some
very large relation databases. If a single application will take up multiple servers on its own running it in a
container that wants to share resources with other applications on a server does not make any sense

## Container storage

Containers are a revolutionary technology but they can not do everything. Storage is an are where containers need to be
paired with another solution to deploy production-ready applications. This is because the storage created when a
container is deployed is ephemeral. If a container is destroyed or replaced the storage from inside that container is
not reused. This is by design to allow containers to be stateless by default. If something goes bad, a container can be
removed from your environment completely and new one can be stood up in its place, instantly The idea of a stateless
application container is great, but somewhere in your application usually in multiple places data needs to be shared
across multiple containers and state needs to be preserved. Here are some examples of these situations:

- Shared data that needs to be available across multiple containers, like uploaded image, for a web application

- Use state information in a complex application which lets users pick up where they leave off during a long running
  transaction.

- Information that is stored in relational or non-relational databases

In all of these situations, and many others, you need to have persistent storage available to your containers. This
storage should be defined as part of your application deployment and should be available from all the nodes in your
OpenShift cluster, luckily OpenShift has multiple ways to solve this problem. In future sections we will configure
external network storage service, you will then configure it to interact with OpenShift so applications can dynamically
allocate and take advantage of its persistent storage volumes. When you are able to effectively integrate shared storage
into your application containers you can think about scalability in new ways.

## Scaling applications

For stateless application, scaling up and down is straightforward, because there are o dependencies other than what is
in the application container and because the transactions happening in the container are atomic by design all you need
to do to scale a stateless application is to deploy more instance of it and load balance them together. To make this
process even easier OpenShift proxies the connection to each application through a built in load balancer - HAProxy,
This allows applications to scale up and down with no change, in how users connect to the application. If your
application are stateful meaning they need to store or retrieve shared data, such as a database or data that a user has
uploaded then you need to be able to provide persistent storage for them. This storage needs to automatically scale up
and down with your application, in OpenShift. For stateful applications persistent storage is a key component that must
be tightly integrated into your design. At the end of the day stateful pods are how users get data in and out of your
application

## Integrating stateful and stateless apps

As you begin separating traditional monolithic apps into smaller services that work effectively in containers you will
begin to view your data needs in a different way. This process is often referred to as designing apps as microservices.
For any app you have services that you need to be stateful and others that are stateless, for example the service that
provides static web content can be stateless, whereas the service that processes user authentication needs to be able to
write information to persistent storage. These services all go together to form your app. Because each service runs in
its own container the services can be scaled up and down independently. Instead of having to scale up your entire
codebase with containers you can only scale the services in your app that need to process additional workloads.
Additionally because only the containers that need access to persistent storage have it, the data going into your
container is more secure. That brings us to the end of our initial walkthrough. The benefits provided by OpenShift save
time for human and use server resources more efficiently. Additionally the nature of how containers work provides
improved scalability and deployment speed versus virtual machines. This all goes together to provide an incredibly
powerful app platform that you will work with for the rest read.

# Starting

There are three ways to interact with OpenShift the command line, the web interface and the REST API. This chapter
focuses on deploying apps using the command line, because the command line exposes more of the process that isued to
create containerized app in OpenShift. In other sections the examples may use the web interface or even the API. Our
intention is to give you the most real world examples of using OpenShift. We want to show you the best tools to get the
job done. We will also try our best not to make you repeat yourself. Almost every action in OpenShift can be performed
using all three access methods. If something is limited we will do our best to let you know. But we want you to get the
best experience possible from using OpenShift. With that said in this section we are going to repeat ourselves, but for
a good reason.

The most common task in OpenShift is deploying an app. Because this is the most common task we need to introduce you to
it as early as practical using both the command line and the web interface. So place bear with us. This section may seem
a little repetitive.

## Cluster options

Before we can start using OpenShift you have to deploy it. There a few options, to install OpenShift locally on your
machine or on cloud providers. We are going to stop at tools like Minishift or `RedHat's` CRC.

Logging in OpenShift must be done, as every action requires authentication. This allows every action to be governed by
the security and access rules set up for all users in an OpenShift cluster. We will discuss the various methods of
managing authentication in next section, but by default your OpenShift cluster initial configuration is set to allow any
user and password. The allow all identity provider creates a user account the first time a user logs in. Each user name
is unique and the password can be anything except an empty field. This configuration is safe and recommended only for
lab and development OpenShift instances like the one we are setting up.

Using the `oc` command line tool, it is a front facing user level program which is used to interact with the REST server
of the running OpenShift cluster, in this case the actual OpenShift server is backed by the Kubernetes REST API server
under the hood, but those are implementation details, OpenShift builds on top of the Kubernetes REST API server to bring
us more flexibility and robustness. What you need to remember is that you must first use the login command to perform
the login action, before any other command can be execute with the `oc` tool

This is done using the following command `oc login -u dev -p dev https://ocp-1.192.168.122.100.nip.io:8443`. What this
does is sets the user and password to `dev`, and points the `oc` tool to a valid running cluster REST API server, those
3 fields are mandatory otherwise the tool would not know who to login to, or where to for that matter.

Creating the projects can now be done using the tool. In OpenShift projects are the fundamental way apps are organized.
Projects let users collect their apps into logical groups. They also serve other useful roles around security that we
will discuss in future sections. For now though think of a project as a collection of related apps. You will create your
first project and then use it to house a handful of apps that you will deploy modify, redeploy and do all sorts of
things to over the course of the next few sections.

`The default project and working with multiple projects - the oc tool's default action is to execute the command you run
using the current working project. If you create a new project it automatically becomes your working project. The oc
project command changes the current working project from one to another. To specify a command to be execute against a
specific project regardless of your current working project use the -n parameter with the project name you want the
command to run against. This is a helpful option when you are writing scripts that use oc and act on multiple projects`

To create a project you need to run the `oc new-project` command and provide a project name. For the first project use
`image-uploader` as the project name

```sh
$ oc new-project image-uploader --display-name='Image Uploader Project'

Now using project "image-uploader" on server "https://api.crc.testing:6443".
You can add applications to this project with the 'new-app' command. For example, try:

    oc new-app rails-postgresql-example
to build a new example application in Ruby. Or use kubectl to deploy a simple Kubernetes application:
    kubectl create deployment hello-node --image=registry.k8s.io/e2e-test-images/agnhost:2.43 -- /agnhost serve-hostname
```

## Application Components

Apps in OpenShift are not monolithic structures, they consist of a number of different components in a project that will
work together to deploy update and maintain your app through its lifecycle. Those components are as follows

- Container images
- Image streams
- App pods
- Build configs
- Deployment configs
- Deployments
- Services

### Container images

Each app deployment in OpenShift creates a custom container image to serve your app. This image is created using the app
source code and custom base image called builder image. For example the PHP builder image contains the Apache web server
and the core PHP language libraries. The image build process takes the builder image you choose integrates your source
code and creates the custom container image that will be used for the app deployment. Once created all the container
images along with all the builder images are stored in OpenShift integrated container registry which we discussed in
first section. The component that controls the creation of your app containers is the build config.

### Build configs

A build config contains all the information needed to build an app using its source code. This includes all the
information required to build the app container image.

- URL for the app source code
- Name of the builder image to use
- Name of the app container image that is created
- Events that can trigger a new build to occur

After the build config does its job, it triggers the deployment config that is created for your newly created app.

### Deployment configs

If an app is never deployed it can never do its job. The job of deploying and upgrading the app is handled by the
deployment config component. Deployment configs track several pieces of information about an app.

- Currently deployed version of the app

- Number of replicas to maintain for the app

- Trigger events that can trigger a redeployment. By default configuration changes to the deployment or changes to the
  container image trigger an automatic app redeployment

- Upgrade strategy app-cli uses the default rolling upgrade strategy

- App deployments

A key feature of app running in OpenShift is that they are horizontally scalable. This concept is represented in the
deployment config by the number of replicas. The number of replicas specified in a deployment config is passed into a
Kubernetes object called a replication controller. This is a special type of Kubernetes pod that allows for multiple
replicas - copies of the app pod to be kept running at all time. All pods in OpenShift are deployed by replication
controllers by default. Another feature that is managed by a deployment config is how apps upgrades can be fully
automated. Each deployment for an app is monitored and available to the deployment config component using deployments.

In OpenShift a pod can exist in one of five phases at any given time in its lifecycle. These phases are described in
detail in the Kubernetes Documentation. The following is a brief summary of the five pod phases.

- `Pending` - the pod has been accepted by OpenShift but its is not yet schedule on one of the app nodes.
- `Running` - the pod is scheduled on a node and is confirmed to be up and running.
- `Succeeded` - all containers in a pod have terminated successfully and wont be restarted
- `Unknown` - something has gone wrong and OpenShift can not obtain a more accurate status for the pod.

Failed and Succeeded are considered terminal states for a pod in its lifecycle. Once a pod reaches one of these states
it wont be restarted. You can see the current phase for each pod in a project by running the `oc get pods` command Pod
lifecycle will become important when you begin creating project quotas.

Each time a new version of an app is created by its build config, a new deployment is created and tracked by the
deployment config. A deployment represents a unique version of an app. Each deployment references a version of the app
image that was created and creates the replication controller to create and maintain the pod to serve the app. New
deployments can be created automatically in OpenShift by managing how apps are upgraded which is also tracked by the
deployment config.

The default app upgrade method in OpenShift is to perform a rolling upgrade rolling upgrades create new versions of an
app allowing new connections to the app to access only the new version. As traffic increases to the new deployment the
pods for the old deployment are removed from the system.

New app deployments can be automatically triggered by events such as configuration changes to your app, or a new versoin
of a container image being available. These sorts of trigger events are monitored by image streams in OpenShift.

### Image stream

Image stream are used to automate actions in OpenShift. The consist of links to one or more container images. Using
image streams you can monitor apps and trigger new deployments when their components are updated.

## Deploying an app

Apps are deployed using the `oc new-app` command. When you run this command to deploy the `image uploader` app, into the
`image-uploader` project. You need to provide three prices of information.

- The type of the image stream you want to use - OpenShift ships with multiple container images called builder images,
  that you can use as a starting point for apps.

- A name for your app - in this example use app-cli because this version of your app will be deployed from the command
  line.

- The location of your app source code - OpenShift will take the source code and combine it with the PHP builder image
  to create a custom container image for your app deployment

    Here is a new app deployment

```sh
$ oc new-app --image-stream=php --code=https://github.com/OpenShiftInAction/image-uploader.git --name=app-cli

# this is a sample output of what the OpenShift environment might print out during the creation of the new app in the
# cluster, it will automatically do most of the operations and provide a ready to use application to the end user
> Found image 2569ec6 (3 months old) in image stream "openshift/php" under tag "8.0-ubi8" for "php"
>
> Apache 2.4 with PHP 8.0 ----------------------- PHP 8.0 available as container is a base platform for building and
> running various PHP 8.0 applications and frameworks. PHP is an HTML-embedded scripting language. PHP attempts to make it
> easy for developers to write dynamically generated web p ages. PHP also offers built-in database integration for several
> commercial and non-commercial database management systems, so writing a database-enabled webpage with PHP is fairly
> simple. The most common use of PHP coding is probably as a replaceme nt for CGI scripts.
>
> Tags: builder, php, php80, php-80
>
> * The source repository appears to match: php
> * A source build using source code from https://github.com/OpenShiftInAction/image-uploader.git will be created
> * The resulting image will be pushed to image stream tag "app-cli:latest"
> * Use 'oc start-build' to trigger a new build

--> Creating resources ... imagestream.image.openshift.io "app-cli" created buildconfig.build.openshift.io "app-cli"
created deployment.apps "app-cli" created service "app-cli" created --> Success Build scheduled, use 'oc logs -f
buildconfig/app-cli' to track its progress. Application is not exposed. You can expose services to the outside world by
executing one or more of the commands below: 'oc expose service/app-cli' Run 'oc status' to view your app.
```

After you run the `oc new-app` command you will see a long list of output, as shown above. This is OpenShift building
the image out of all the components needed to make your app work properly. Now if you visit the web console you will be
able to browse the project structure as well, and you will also see that there are quite a few new objects created for
the new app in the new project. With the triggering of new `oc new-app` command various new objects are created in the
cluster for the current project, this is usually not the way we would do this in the real world, each of those new
objects will be manually defined in manifest files by the developers where the properties of these objects can be fine
tuned. But for the sake of demonstration and ease of use OpenShift provides the users with quick and dirty ways to
deploy apps, this is very useful for development purposes where we just want to put our app into use, and avoid the
hassle of manual configuration of OpenShift objects.

```sh
# list some of the objects which would have been automatically created by the OpenShift environment
$ oc get services && oc get pods && oc get deployments

# here is what the output might look like, there are multiple pods that were created, you may notice that there was a
# pod called build - which is basically spun up to build the image from the source, and then it is uploading that
# image into the internal OpenShift registry # and deployed as an actual pod which is in Running state. There is
# also a new service created for our app.
NAME    TYPE      CLUSTER-IP   EXTERNAL-IP PORT(S)           AGE
app-cli ClusterIP 10.217.5.212 <none>      8080/TCP,8443/TCP 9m31s

NAME                     READY STATUS    RESTARTS AGE
app-cli-1-build          0/1   Completed 0        9m31s
app-cli-5b9c58956d-g7fx5 1/1   Running   0        8m33s

NAME    READY UP-TO-DATE AVAILABLE AGE
app-cli 1/1   1          1         9m32s
```

## Providing access to apps

In future sections we will explore multiple ways to force OpenShift to redeploy app pods. In the course of a normal day
this happens all the time, for any number of reasons, you are scaling apps up and down, apps pods stop responding
correctly, nodes are rebooted or have issues, human error, and so on. Although pods may come and go there needs to be a
consistent presence for your app in OpenShift. That is what a service does. A service uses labels applied to application
pods when they are created to keep track of all pods associated with a given app. This allows a service to act as an
internal proxy for your app. You can see information about the service for app-cli by running the `oc describe
svc/app-cli command`.

```sh
$ oc describe svc/app-cli

Type:                     ClusterIP
IP Family Policy:         SingleStack
IP Families:              IPv4
IP:                       10.217.5.212
IPs:                      10.217.5.212
Port:                     8080-tcp  8080/TCP
TargetPort:               8080/TCP
Endpoints:                10.217.0.83:8080
Port:                     8443-tcp  8443/TCP
TargetPort:               8443/TCP
Endpoints:                10.217.0.83:8443
```

Now note that there are the fields which are IP addresses, these are the IP addresses that each service gets, these are
the cluster virtual IP addresses that are only routable within the OpenShift cluster. Other information that is
maintained includes the IP address of the service and the TCP ports to connect to the in the pod.

`Most components in OpenShift have a shorthand that can be used on the command line to save time and avoid misspelled
components names. The previous command uses svc/app-cli to get information about the service for the app-cli app. Build
configs can be accessed with the bc/<app-name>`

Services provide a consistent gateway into your app deployment, but the IP addresses of a service is available only in
your OpenShift cluster, to connect users to your app and make DNS work properly you need one or more app components Next
you will create a route to expose app-cli externally from your OpenShift cluster

## Exposing application services

When you install your OpenShift cluster, one of the services that is created is the HAProxy service running in a
container on OpenShift, the HAProxy is an open source software load balancer, we will look at this service in depth in
next sections,. To create a route for the app-cli run the following command -

```sh
$ oc expose svc/app-cli

route.route.openshift.io/app-cli exposed
```

As we discussed earlier, OpenShift uses projects to organize applications. An application project is included in the URL
that is generated when you create an application route. Each application UR takes the following format -
`<application-name>-<project-name>-.<cluster-app.domain>`. This is actually the default format, coming from kubernetes,
but slightly modified by OpenShift to include the name of the project, in Kubernetes in place of the project name in
that format is actually the namespace for the app, in OpenShift the projects are actually implemented internally as
kubernetes namespaces, but very much enhanced.

When you did deploy OpenShift in previous sections you specified the application domain, by default all application in
OpenShift are served using the HTTP protocol when you pull all this together the URL for app-cli should be as follows -
`http://app-cli-image-uploader.<cluster-app.domain>`. You can get more information about the route you just
created by running the `oc describe route/app-cli` command

```sh
$ oc describe route/app-cli

Name:                   app-cli
Namespace:              image-uploader
Created:                4 minutes ago
Labels:                 app=app-cli
                        app.kubernetes.io/component=app-cli
                        app.kubernetes.io/instance=app-cli
                        app.kubernetes.io/name=php
Annotations:            openshift.io/host.generated=true
Requested Host:         app-cli-image-uploader.apps-crc.testing
                           exposed on router default (host router-default.apps-crc.testing) 4 minutes ago
Path:                   <none>
TLS Termination:        <none>
Insecure Policy:        <none>
Endpoint Port:          8080-tcp

Service:        app-cli
Weight:         100 (100%)
Endpoints:      10.217.0.83:8080
```

The output tells you that the host configuration added to the HAProxy the service associated with the route and the
endpoints for the service, to connect to when handling requests for the route, how that we have created the route to you
application go ahead and verify that by visiting the IP address in a web browser.

Focusing on the component that deploy and deliver the app-cli application, you can see the relationship between the
service, the newly created route, and the end users. We will cover this in more depth in next sections, but in summary
the route is tied to the app-cli service, users access the application pod through the route. This chapter is about
relationships. In OpenShift multiple components work in concert to build deploy and manage application. We spend the
rest of the discussing the different aspects of these relationships in depth, that fundamental knowledge of how
container platforms operate is incredibly valuable.

# Containers

In the previous sections you deployed your first app, in OpenShift in this chapter we will look deeper into your
OpenShift cluster and investigate how these containers isolate their processes on the application node. Knowledge of how
containers work in a platform like OpenShift is some of the most powerful information in IT right now. This fundamental
understanding of how a container actually works as part of the Linux kernel and server informs how systems are designed
and how issues are analyzed when they inevitable occur. This is a challenging section, not because of a lot of
configuration and making complex changes, but because we are talking about the fundamental layers of abstractions that
make a container a container in the modern kernel world.

## Defining containers

You can find five different container experts and ask them to define what a container is and you are likely to get five
different answers, the following are some our personal favorites all of which are correct from a certain perspective.

- A transportable unit to move apps around. This is a typical developer answer
- A fancy linux process
- A more effective way to isolate processes on a linux system, this is a more operations centered answer.

What we need to untangle is the fact that they are all correct, depending on your point of view. In section 1, we talked
about how OpenShift uses Kubernetes and docker to orchestrate and deploy apps in a container in your cluster. But we
have not talked much about which application component is created by each of these services, before we move forward it
is important for you to understand these responsibilities as you begin interacting with application components directly.

## OpenShift component interaction

When you deploy and application in OpenShift the request starts in the OpenShift API server. To really understand how
containers isolate the process within them we need to take a more detailed look at how these services work together to
deploy your application. The relationship between OpenShift Kubernetes docker and ultimately the Linux kernel is a chain
of dependencies. When you deploy an application in OpenShift the process starts with the OpenShift services.

## OpenShift manages deployments

Deploying application begin with application components that are unique to OpenShift the process is as follows:

1. OpenShift creates a custom container image using your source code and the builder image template you specified.

2. This image is uploaded to the OpenShift container image registry

3. OpenShift creates a build config to document how your -- is built. This includes which image was created the builder
   image used the location of the source code and other information.

4. OpenShift creates a deployment config to control deployments and deploy and update your application. Information in
   deployment configs includes the number replicas the upgrade method, and application specific variables and mounted
   volumes.

5. OpenShift creates a deployment which represents a single deployed version of an application. Each unique application
   deployment is associated with your application deployment config component.

6. the OpenShift internal load balancer is updated with an entry for the DNS record for the application. This entry will
   be linked to a component that is created by Kubernetes which we will get to shortly.

7. OpenShift creates an image stream component, in OpenShift an image stream monitors the builder image, deployment
   config, and other components for changes, if a change is detected image streams can trigger application redeployments to
   reflect changes

The build config creates an application specific custom container image using the specified builder image and source
code, that image is stored in the OpenShift image registry. The deployment config component creates an application
deployment that is unique for each version of the app. The image stream is created and monitors for changing to the
deployment config and related images in the internal registry. The DNS route is also created and will be linked to the
Kubernetes object

## Kubernetes schedules applications

Kubernetes is the orchestration engine, at the heart of OpenShift, in many ways an OpenShift cluster is a kubernetes
cluster. When you initially deploy app-cli, Kubernetes created several application components.

- Replication controller - scales the application as needed in Kubernetes. This component also ensures that the desired
  number of replicas in the deployment config is maintained at all times.

- Service - Exposes the application . A kubernetes service is a single IP address that is used to access all the active
  pods for an application deployment. When you scale an application up or down the number of pods changes, but they are
  all accessed through a single service proxy object.

- Pods - represent the smallest scalable unit in OpenShift.

The replication controller dictates how many pods are created for an initial application deployment is linked to the
OpenShift deployment component. Also linked to the pod components is a Kubernetes service. The service represents all
the pods deployed by a replication controller. It provides a single IP address in OpenShift to access your application
as it scaled up and down on different nodes in your cluster. The service in the internal IP address that is referenced
in the route created in the OpenShift load balancer.

## Docker creates containers

Docker is a container runtime. A container runtime is the application on a server that creates, maintains and removes
containers. A container runtime can act as a stand alone tool on a laptop or a single server, but it is at its most
powerful when being orchestrated across a cluster by a tool like kubernetes. Kubernetes controls docker to create
containers that house the app. These containers use the custom base image as the starting point for the files that are
visible to application in the container. Finally the docker container is associated with the Kubernetes pod. To isolate
the libraries and application in the container image along with other server resources docker uses Linux kernel
components. These kernel level resources are the components that isolate the application in your container from
everything else on the application node. Let us check them out

## Linux isolates resources

We are down to the core of what makes a container a container in OpenShift , and Linux. Docker uses three Linux kernel
components to isolate the application running in containers it creates and limit their access to resources on the host
machine or cluster node in our case.

- Linux namespaces - provide isolation for the resources running in the container. Although the term is the same this is
  a different concept than Kubernetes namespaces, which are roughly analogous to n OpenShift project. We will discuss
  these in more depth in next sections. For the sake of brevity in this section when we reference namespaces, we are
  talking about Linux namespaces

- Control groups - Provide maximum guaranteed access to limits for CPU and memory on the app node, or cluster node.

- SELinux contexts - prevents the container application from improperly accessing resources on the host or in other
  containers. An SELinux context is a unique label that is applied to a container resources on the application node. This
  unique label prevents the container from accessing anything that does not have a matching label on the host.

The docker daemon creates these kernel resources dynamically when the container is created, these resources aer
associated with the application that are launched for the corresponding container your application is now running in a
container. Apps in OpenShift are run and associated with these kernel components they provide the isolation that you see
from inside a container in upcoming sections, we will discus how you can investigate a container from the application
node. From the point of view of being able inside the container, an application only has the resources allowed and
allocated to it.

## Listing kernel components

Armed with the process id of the current app-cli we can begin to analyze how containers isolate process resources with
Linux namespaces. Earlier in this section we discussed how kernel namespaces are used to isolate the application in a
container from the other processes on the host. Docker creates a unique set of namespaces to isolate the resources in
each container looking again the application is linked to the namespaces because they are unique for each container.
Cgroups and SELinux are both configured to include information for a newly created container but those kernel resources
are shared among all containers running on the application node. To get a list of the namespaces that were created for
the app-cli use the `lsns` command. You need the POD for the application to pass as a parameter to `lsns`.

OpenShift uses the five linux namespaces to isolate processes and resources on application nodes. Coming up with a
concise definition for exactly what a namespace does is a little difficult, two analogies best describe their most
important properties

- Namespaces are like paper walls in the linux kernel, they are lightweight and easy to stand up and tear down, but they
  offer sufficient privacy when they are in place.
- Namespaces are similar to two way mirrors, from within the container only the resources in the namespace are available
  but with proper tooling you can see what is in a namespace from the host system.

The following snippet lists all namespaces for the app-cli with `lsns`

```sh
$ lsns -p

```
