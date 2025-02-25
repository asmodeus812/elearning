# Introduction

`Kubernetes` is an application `orchestrator`, for the most part it orchestrates containerized cloud native micro
services. An orchestrator is a system that deploys and manages apps, it can deploy your app and dynamically respond to
changes, for example k8s, can:

1. Deploy you app
2. Scale it up and down dynamically based on demand
3. Self heal when things break
4. Perform zero downtime rolling updates and rollbacks
5. Many, many more things

`What really is containerised app` - it is an app that runs in a container, before we had containers, apps ran on physical
servers or virtual machines, containers are just the next iteration of how we package and run apps, as such they are
faster more lightweight and more suited to modern business requirements than servers and virtual machines

`What is a cloud native app` - it is one that is designed to meet the cloud like demands of auto scaling self healing
rolling updates, rollbacks and more, it is important to be clear that cloud native apps are not apps that will only run
in the public cloud, yes they absolutely can run on a public clouds, but they can also run anywhere that you have k8s
even your on premise datacenter.

`What are microservice apps` - is built from lots of independent small specialised parts that work together to form a
meaningful app. For example you might have an e-commerce app that comprises all of the following small components

1. Web front end
2. Catalog service
3. Shopping cart
4. Authentication service
5. Logging service
6. Persistent store

Each of these individual services is called a micro service, typically each is coded and owned by a different team, each
can have its own release cycle and can be scaled independently, for example you can patch and scale the logging micro
service without affecting any of the others. Building apps this way is vital for cloud native features, For the most
part, each microservice runs as a container, assuming that e-commerce app with the 6 microservice there would be one or
more web front end containers one or more catalog containers one or more shipping cart containers etc. With all of this
in mind

`Kubernetes deploys and manages (orchestrates) apps that are packaged and run as containers (containerized) and that are
built in ways (cloud native microservice) that allows them to scale, self heal and be updated in line with modern cloud
like requirements.`

## History

Since amazon brought the Amazon Web Services, the world changed, since then everyone is playing catch-up. One of the
companies trying to catch up was Google. It has its own very good cloud and needs a way to abstract the value of AWS and
make it easier for potential customers to get off AWS and into their cloud. Google also has a lot of experience working
with containers at scale, for example huge google apps such as Search and Gmail have been running at extreme scale on
containers for a lot of years, since way before Docker brought us easy to use containers. To orchestrate and manage
these containerised apps, Google had a couple of in-house proprietary systems called Borg and Omega. Well Google took
the lessons leaned from these systems, and created a new platform called Kubernetes, and donated it to the newly formed
`Cloud Native Computing Foundation (CNCF)` in 2014, as an open source project. Kubernetes enables two things Google and the
rest of the industry needs

1. It abstracts underlying infrastructure such as `AWS`
2. It makes it easy to move apps on and off clouds

Since its introduction in 2014, Kubernetes has become the most important cloud native technology on the planet. Like
many of the modern cloud native projects, it's written in Go, it is built in the open on GitHub it is actively discussed
on the IRC channels, you can follow it everywhere on social media, there are also regular conferences and regular
meetups

## Meaning

The name Kubernetes comes from the Greek word meaning Helmsman - the person steers the seafaring ship. This theme is
reflected in the logo which is the wheel (helm control) of a sea faring ship. You will often see it shortened to k8s -
pronounced `kate`. The number 8 replaces the 8 characters between the K and the S in the name, and that is people
sometimes joke that Kubernetes has girlfriend named Kate

## Kubernetes and Docker

Kubernetes and Docker are two complementary technologies, Docker has tools that build and package apps as container
images. It can also run containers, Kubernetes can't do either of those things, Instead, Kubernetes operates at a higher
level providing orchestration services such as self-healing, scaling and updating. It is common practice to use Docker
for build time tasks such as packaging apps as containers, but then use a combination of Kubernetes and Docker to run
them. In this model, Kubernetes preforms the high level orchestration tasks, while Docker performs the low level tasks
such as starting and stopping containers.

Assume you have a Kubernetes cluster with 10 nodes, to run your production app. Behind the scenes each cluster node is
running Docker as its container runtime. This means Docker is the low-level technology that starts and stops the
containerised apps. Kubernetes is the higher level technology that looks after the bigger picture, such as deciding
which nodes to run containers on, deciding when to scale up or down, and execute updates. Docker is not the only
container runtime that Kubernetes supports, it also does support `gVisor`, `containerd` and `kata`. Kubernetes has
features which abstract the container runtime and make it interchangeable

1. The container runtime interface (CRI) - is an abstraction layer that standardizes the way 3rd party container
   runtimes work with Kubernetes

2. Runtime Classes allows you to create different classes of runtimes. For example the `gVisor` or `Kata` Containers
   runtimes might provide better workload isolation than the Docker and `containerd` runtimes

## Kubernetes and Docker swarm

In 2016 and 2017 we had the orchestrator wards, where `Docker Swarm`, `Mesosphere DCOS`, and `Kubernetes` completed to become
the de-facto container orchestrator. To cut a long story short, Kubernetes WON.

There is a good chance you will hear people talk about how Kubernetes relates to Google's Borg and Omega systems, as
previously mentioned, Google has been running containers at scale for a long time - apparently crunching through
billions of containers a week. So yes, Google has been running things like `Search`, `Gmail`, and `GFS` on lots of
containers for a very longs time. Orchestrating these containerised apps was the job of a couple of in-house
technologies called Borg and Omega. So it is not a huge stretch to make the connection with Kubernetes - all three are
in the game or orchestration or containers at scale, and they are all related to Google.

`Kubernetes is not an opened source version of Borg and Omega, it shares common traits and technologies, common DNA, if
you wish, but the Borg and Omega are still proprietary closed source projects`

They are all separate but all three are related, in fact, some of the people who built Borg and Omega were and still are
involved with Kubernetes. So although Kubernetes was built from scratch, it leverages much of what was learned at Google
with Omega and Borg

## Kubernetes as the operating system of the cloud

Kubernetes has emerged as the de-facto platform for deploying and managing cloud native apps, in many ways it is like an
operating system for the cloud. In the same way that Linux abstracts the hardware differences between server platforms,
Kubernetes abstracts the differences between the different private and public clouds. Net result is that as long as you
are running Kubernetes, it does not matter if the underlying systems are on premises, in your own datacenter, edge
devices or in the public cloud or domain.

## Principles of operation

This sub chapter will summarize the major components required to build a Kubernetes cluster and deploy an app. The aim
is to give you an overview of the major concepts, so you do not worry if you do not understand everything straight away,
most things will be covered again

### Kubernetes from 40k feet

At the highest level, Kubernetes is two things - A cluster to run apps on & an orchestrator of cloud native microservice
apps.

#### Kubernetes as a cluster

Kubernetes is like any other cluster - a bunch of machines to host apps on. We call these machines nodes, and they can
be physical servers, virtual machines, cloud instances, Raspberry Pis, and more. A Kubernetes cluster is made of a
`control plane` and `nodes`. This control plane exposes the API, has scheduler for assigning work, and records the
state of of the cluster and apps in a persistent store. Nodes are where user apps run. It can be useful to think of the
`control plane` as the brains of the cluster and the nodes as the muscle. In this analogy the `control plane` is the
brains because it implements the clever features such as scheduling, auto-scaling and zero-downtime rolling updates.

#### Kubernetes as orchestrator

Orchestrator is just a fancy word for a system that takes care of deploying and managing apps. If we take a quick
analogy from the real world, a football team is made of individuals. Every individual is different and each has a
different role to play in the team - some defend some attack some are great at passing some tackle some shoot... Along
comes the coach and she or he gives everyone a position and organizes them into a team with a purpose. The coach also
makes sure that the team keeps its formation sticks to the game-plan and deals with any injuries and other changes in
the circumstances. Well microservices apps on Kubernetes are the same.

You start out with lots of individual specialised microservices. Some serve web pages, some do authentication some
perform searches, other persist data. Kubernetes comes along - like the coach, organizes everything into a useful app
and keeps things running smoothly. It even responds to events and other changes in the circumstances - auto-scaling,
updating, rolling release etc.

When we start out with an app, package it as a container then give it to the cluster - Kubernetes. The cluster is made
up of one or more control plane nodes and a bunch of worker nodes. As already stated, control plane nodes implement the
cluster intelligence, worker nodes are where user apps run.

### Control plane

As previously mentioned a Kubernetes cluster is made of control plane nodes and worker nodes. These are Linux hosts that
can be virtual machines, bare metal servers in your datacenter or basement, instances in a private or public cloud. You
can even run Kubernetes on ARM and IoT devices

#### The control plane

A Kubernetes control plane node is a server running collection on system services that make up the control plane of the
cluster. Sometimes we call those Masters, Heads or Head nodes. The simplest setups run a single control plane node.
However this is only suitable for labs and test environments, for production environments multiple control plane nodes
configured for high availability is vital. Also considered a good practice not to run user apps on control plane nodes.
This frees them up to concentrate entirely on managing the cluster.

##### The Kubernetes Server

The API server is the Grand Central of Kubernetes. All communication, between all components must go through the API
server. It is important to understand that internal system components as well as external user components all
communicate via the API server - all roads lead to the API server

It exposes a RESTful API that you POST YAML configuration files to over HTTPS. These YAML files which we sometimes call
manifests describe the desired state of an app. This desired state includes things like which container image to use,
which ports to expose and how many Pod replicas to run. All requests to API server are subject to authentication and
authorization checks. Once these are done, the configuration in the YAML file is validated, persisted to the cluster
store, and work is scheduled to the server

##### The cluster store

The cluster store is the only stateful part of the control plane and persistently stores the entire configuration and
state of the cluster. As such it is vital components of every Kubernetes cluster - no cluster store, no cluster. The
cluster store is currently based on `etcd`, a popular distributed database. As it is the single source of truth for a
cluster, you should run between 3-5 replicas of the `etcd` service for high-availability and you should provide adequate
ways to recover when things to bad. A default installation of Kubernetes installs a replica of the cluster store on
every control plane node and automatically configures the high availability (HA)

On the topic of availability, `etcd` prefers consistency over availability. This means it does not tolerate split brains
and will halt updates to the cluster in order to maintain consistency. However, if this happens user apps should
continue to work, you just won't be able to update the cluster configuration.

As with all distributed databases, consistency of writes to the database is vital. For example multiple writes to the
same value originating from different places needs to be handled. `etcd` uses the popular RAFT consensus algorithm to
accomplish this.

##### The controller manager and controllers

The controller manager implements all the background controllers that monitor cluster components and respond to events.
Architecturally, it is a controller of controllers, meaning it spawns all the independent controllers and monitors them.
Some of the controllers include the `Deployment` controller, the `StatefulSet` controller and the `ReplicaSet` controller.
Each one is responsible for a small subset of cluster intelligence and runs as a background watch loop constantly
watching the API Server for changes.

`The aim of the game is to ensure the observed state of the cluster matches the desired state. The logic implemented by
each controller is as follows, and is at the heart of Kubernetes and declarative design patterns`

1. Obtain desired state
2. Observe current state
3. Determine differences
4. Reconcile differences

Each controller is also extremely specialized and only interested in its own little corner of the Kubernetes cluster. No
attempts is made to over complicate design by implementing awareness of other parts of the system each controller takes
care of its own business and leaves everything else alone. This is key to the distributed design of Kubernetes and
adheres to the Unix philosophy.

##### The scheduler

At a high level, the scheduler watches the API server for new work tasks and assigns them to appropriate healthy worker
nodes. Behind the scenes, it implements complex logic that filters out nodes incapable of running tasks and the ranks
the nodes that are capable. The ranking system is complex but the node with the highest ranking score is selected to run
the task.

When identifying nodes capable of running a task, the scheduler performs various predicate checks. These include is the
node tainted, are there any affinity or anti affinity rules, is the required network port available on the node does it
have sufficient available resources etc. Any node incapable of running the task is ignored and those remaining are
ranked according to things such as does it already have the required image how much free resources does it have, how
many tasks is it currently running. Each is worth points and the node with the most points is selected to run the task.
If the scheduler does not find a suitable node, the task is not schedule and gets marked as pending. The scheduler is
not responsible for running tasks just picking the nodes to run them. A task is normally a Pod/container.

##### The Kubelet

The kubelet is main Kubelet agent and runs on every cluster node. In fact, it is common to use the terms node and
kubelet interchangeably. When you join a node to a cluster the process installs the kubelet which is then responsible
for registering it with the cluster. This process registers the node's CPU, memory, and storage into the wider cluster
pool.

One of the main jobs of the kubelet is to watch the API server for new work tasks. Any time it sees one, it executed the
task and maintains a reporting channel back to the control plane. If a kubelet can't run a task, it reports back to the
control plane and lets the control plane decide what actions to take. For example if a kubelet can not execute a task,
it is not responsible for finding another node to run it on. It simply reports back to the control plane and the control
plane decides what to do.

##### The Kubeproxy

The kube proxy is another component which runs on each node, it is mostly responsible for routing the traffic between
pods on the same node, if for example there are multiple pods running under a given service or in other words matching
the selector of a given service, and that service is called from the outside world, the kube proxy takes care of load
balancing the traffic between the different number pods, usually in a round robin style, making sure that each pod is
hit evenly. The details of this are usually dependent on the configuration of the kube proxy.

##### The CRI & CNI

Besides the kubelet and proxy, Kubernetes nodes also run other essential management components, one key components if
the container runtime interface (CRI) which is responsible for running and managing containers. Popular CRI
implementations include containerd (used in OpenShift). Another component is the container network interface (CNI) which
handles networking for pods, including IP address allocation and routing, different CNI plugins such as calico, flannel,
cilium, and weave-net, enable networking capabilities based on the cluster needs

##### The controller manager

If one is running cluster on a supported public cloud platform such as `AWS, Azure, GCP, or Linode` your control plane
will be running a cloud controller manager. Its job is to facilitate integrations with cloud services, such as instances
, load-balancers, and storage. For example if your app asks for an internet facing load-balancer the cloud controller
manager provisions a load-balancer from your cloud and connects it to your app.

##### Control plane summary

Kubernetes control plane nodes are servers that run the cluster's control plane services. These services are the brains
of the cluster where all the control and scheduling decisions happen. Behind the scenes, these services include the API
server, the cluster store, scheduler and specialised controller.

The API server is the front end into the control plane and all instructions and communication pass through it. By
default it exposes a RESTful endpoint on port 443.

#### Worker nodes

Nodes are servers that are the workers of a Kubernetes cluster, at a high level they do three things

1. Watch the API server for new work assignments
2. Execute the new work assignments
3. Report back to the control plane

##### Container runtime

The kubelet needs a container runtime to perform a container related task - things like pulling images and starting and
stopping containers. In the early days, Kubernetes had native support for Docker, More recently it has moved to a plugin
model called the `Container Runtime Interface (CRI)`. At a high level, the `CRI` masks the internal machinery of Kubernetes
and exposes a clean documented interface for 3rd party container runtimes to plug into. Kubernetes is dropping support
for Docker as a container runtime, this is because Docker is bloated and does not support the `CRI` (requires a shim
instead). `containerd` is replacing it as the most common container runtime on Kubernetes

`containerd is the container supervisor and runtime logic stripped out from docker engine. It was donated to the CNCF by
Docker Inc, and has a lot of community support. Other CRI container runtimes also exist.`

##### Kubernetes proxy

The last piece of the node puzzle is the `kube-proxy`. This runs on every node and is responsible for local cluster
networking. It ensures each node gets its own unique IP address, and it implements local `iptables` or `IPVS` rules to
handle routing and load balancing of traffic on the Pod network. More on all of this later on in other chapters down
below.

### Kubernetes DNS

As well as the various control plane and node components, every Kubernetes cluster has an internal `DNS` service, that
is vital to service discovery. The cluster's `DNS` service has a static IP address that is hard coded into every Pod on
the cluster. This ensures every container and Pod can locate it and use it for discovery. Service registration is also
automatic. This means apps do not need to be coded with the intelligence to register with Kubernetes service discovery.
Cluster `DNS` is based on open source `CoreCNS` project.

### Packaging apps for Kubernetes

An app needs to tick a few boxes to run on a Kubernetes cluster. These include:

1. Packages as a container image
2. Wrapped the image as a container instance in a pod
3. Deployed via a declarative config manifest file

It goes like this. You write an application microservice in a language of your choice. Then you build it into a
container image and store it in a registry. At this point the app service is containerized. Next you define a Kubernetes
Pod to run the containerized app. At the kind of high lever we are at, a Pod is just a wrapper that allows a container
to run on a Kubernetes cluster. Once you have defined the pod, you are ready to deploy the app to Kubernetes.

While it is possible to run static Pods like this on a Kubernetes cluster, the preferred model is to deploy all Pods via
a higher level controllers. The most common controller is the Deployment. It offers scalability, self healing and
rolling updates for stateless apps. You define Deployments in YAML manifest files that specify things how many replicas
to deploy and how to perform updates. Once everything is defined in the Deployment YAML file, you can use the Kubernetes
command line tools to post it to the API server as the desired state of the app, and Kubernetes will implement it

### Declarative model

The declarative model and the concept of desired state are at the very heart of Kubernetes. So it is vital you
understand them. In Kubernetes the declarative model works like this.

1. Declare the desired state of an app, microservice in a manifest file
2. Post the desired state to the API server
3. Kubernetes stores it in the cluster store as the app's desired state
4. Kubernetes implements the target desired state in the cluster
5. Controller makes sure the observed state of the app does not vary from the desired state

Manifest files are written in simple YAML and tell Kubernetes what an app should look like. This is called desired
state. It includes things such as which image to use, how many replicas to run, which network ports to listen on, and
how to perform updates.

Once you have created the manifest you post it to the API server. The easiest way to do this is with the kubectl command
line utility. This sends the manifest to the control plane as an HTTPS POST request (on port 443)

Once the request is authenticated and authorized. Kubernetes inspects the manifest, identifies which controller to send
it to (i.e. Deployments controller) and records the configuration in the cluster store as part of the overall desired state.
Once this is done any required work tasks get scheduled to the cluster nodes where the kubelet co-ordinates the hard
work of pulling images starting containers attaching to networks, and starting app processes.

Finally controllers run as background reconciliation loops that constantly monitor the state of things, if the observed
state deviates from the desired state, Kubernetes performs the tasks which are necessary to reconcile the differences
and bring the observed state back in sync with the desired state

`It is important to understand that what we have described is the opposite of the traditional imperative model. The
imperative model is where you write long scripts of platform specific commands to build and monitor things, not only is
the declarative model a lot simpler than long scripts with lots of imperative commands, it also enables self-healing,
scaling and lends itself to version control and self-documentation. It does all of this by telling the cluster how thing
should look like. If they start to look different, the appropriate controller notices the discrepancy and does all the
hard work to reconcile the situation.`

#### Example

Assume you have an app with a desired state that includes 10 replicas of a web front end Pod. If a node running two
replicas fails, the observed state will be reduced to 8 replicas but desired state will still be 10. This will be
observed by a controller and Kubernetes will schedule two new replicas to bring the total back up to 10. The same thing
will happen if you intentionally scale the desired number of replicas up or down. You could even change the image you
want to use (this is called a `rollout`). For example if the app is currently using `v2.00` of an image and you update
the desired state to specify `v2.01` the relevant controller will notice the difference and go through the process of
updating the cluster so all 10 replicas are running the new version.

To be clear. Instead of writing a complex script to step through the entire process of updating every replica to the new
version, you simply tell Kubernetes you want the new version and Kubernetes does the hard work for you.

### Pods

In the VMware world the atomic unit of scheduling is the virtual machine. In the docker world it is the container, in
the Kubernetes world it is the Pod. It is true that Kubernetes runs containerized apps. However Kubernetes demands that
every container runs inside a pod.

`Pods are objects in the Kubernetes API, so we capitalize the first letter. This adds clarity and the official
Kubernetes docs are moving towards this standard.`

#### Pods & Containers

The very first thing to understand is that the term Pod comes from a pod of whales - in the English language we call a
group of whales a pod of whales. As the Docker logo is a whale, Kubernetes ran with the whale concept and that is why we
have Pods. The simplest model is to run a single container in every Pod. This is why we often use the term Pod and
container interchangeably. However there are advanced use cases that run multiple containers in a single Pod, Powerful
examples of multi container Pods include:

- Service meshes
- Containers with a tightly coupled log scraper
- Web containers supported by a helper container pulling updated content

`The point is that a Kubernetes Pod is a construct for running one or more containers. A pod is an object, defined
declaratively in the k8s state, they are not physical entities that run on the Nodes, they are used by the kubelet
service or daemon to control containers`

#### Pod anatomy

At the highest level a Pod is ring fenced environment to run containers. Pods themselves do not actually run apps, apps
always run in containers, the Pod is just a sandbox to run one or more containers. Keeping it high level, Pods ring fence
an area of the host OS, build a network stack, create a bunch of kernel namespaces and run one or more containers

If you are running multiple containers in a Pod they all share the same Pod environment. This includes the network
stack, volumes `IPC` namespace, shared memory and more. As an example this means all containers in the same Pod will share
the same IP address. If two containers in the same Pod want to talk to each other, they can use the Pod's `localhost` interface.

Multi container Pods are ideal when you have requirements for tightly coupled containers that may need to share memory
and storage. However if you do not need to tightly couple containers, you should put them in their own Pods, and loosely
couple them over the network. This keeps things clean by having each Pod dedicated to a single task. However it creates
a lot of potentially `un-encrypted` network traffic. One must seriously consider using a service mesh to secure traffic
between Pods and app services

Now here is an interesting part of how the pods work internally, even though as already mentioned pods are configuration
entities, managed by the kubelet, the kubelet itself, spawns things called pause containers, each pod is associated with
these pause containers, which are more like a namespace holders, they are responsible for holding the network namespace
and other resources a Pod environment has "promised" to its running containers, this is done `in case all containers
managed by a pod die, somehow, or currently a pod has no running containers, for what ever reason`. The statement that a
pod is not a container `still holds`, it is just that the kubelet uses auxiliary structures to retain the resources and
overall state associated with a pod configuration object. This is mostly an implementation detail, and end users can not
interact with the so called `pause containers`

Here is an example: imagine a pod with two containers - web server and logging sidecar container

1. The kubelet starts the pause container first
2. The pause container sets up the shared network and IPC namespaces.
3. The kubelet starts the web server container and joins it to the pause container namespace
4. The kubelet starts the logging container and joins it to the same namespace

Now both containers share the same networking namespaces, they communicate over localhost, and the same IPC namespace
(they can use shared memory)

`Pause containers simply reserve the linux kernel namespaces, which are then used to be shared between the actual
running containers configured for the Pod, all the resources that are shared between containers inside a pod, are
actually bound to the namespace of the pause container, for that pod, which the kubelet has started`

#### Pods as unit of scaling

Pods are also the minimum unit of scheduling in Kubernetes If you need to scale an app, you add or remove Pods. You do
not scale by adding more containers to existing Pods. Multi-container Pods are only for situations where to different
but complimentary containers need to share resources.

`You never scale an app by adding more of the same app containers to a Pod, multi container pods are not a way to scale
an app, they are only for co scheduling and co locating containers that need tight coupling, like a web service and a
logging service, or a in memory data store etc. If you need to scale the app you add more pods, or remove pods, this is
called horizontal scaling`

#### Pods atomic operations

The deployment of a Pod is an atomic operation. This mean a Pod is only ready for service when all its containers are up
and running. The entire Pod either comes up and is put into service or it does not and it fails. A single Pod can only
be schedules to a single node - you can not schedule a single Pod across multiple nodes. This is also true of multi
container Pods - all containers in the same Pod run on the same node.

#### Pod lifecycle

Pods are mortal. The are created they live and they die. If they die unexpectedly you do not have to bring them back to
life. Instead Kubernetes starts a new one in its place. However even though the new Pod looks, smells and feels like the
old one, it is not. It is a shiny new Pod with a shiny new ID and IP address.

This has implications on how you design you app. Do not design them to be tightly coupled to particular instance of a
Pod. Instead design them so that when Pods fail a totally new one can pop up somewhere else in the cluster and
seamlessly take its place

#### Pod immutability

Pods are also immutable this means you do not change them once they are running. Once a Pod is running you never change
its configuration. If you need to change or update it, you replace it with a new Pod instance running the new
configuration. When we have talked about updating Pods, we have really meant delete the old one and replace it with a
new one having the new configuration The immutable nature of Pods is a key aspect of cloud native microservices, design
and patterns and forces the following:

- When updates are needed replace all old pods with new ones that have the updates
- When failures occur replace failed Pods with new identical ones

To be clear you never update the running pod, you always replace it with a new pod containing the updates, you also
never log onto failed pods and attempt fixes you build fixes into an updated pod and replace failed ones with the update
ones.

#### Pods vs Nodes

It is vital to understand the difference between those two, while both are part of the k8s infrastructure their purpose
is vastly different, and while usually a pod corresponds to a single container instance, that is not always the case, as
mentioned above, a Pod can in theory run multiple containers of the same image, or even of different images (more
common) those can share a single state and make integration and integration between these services more robust and
easier in some situations that is desirable

`Pods are not Nodes, Nodes are the physical devices that run the containers and the containers runtimes, the kubelet and
any other component of the k8s infrastructure, those could be many things, (virtual machines, physical machines, embedded
devices and so on) as long as they support running the k8s runtime, the Pods, are a collection of containers that run on
a Node, you can think of it as an intermediate level of division between a node, and a container, meaning that there is
a physical separation between the container runtime and the node itself, which is not the case of other orchestrators
such as Docker, where the containers run directly on the nodes/workers`

#### Pod strategies

The atomic unit of a scheduling on K8s is the pod, this is just a fancy way of saying that apps deployed to the k8s
always run inside pods.

Why need Pods ? Why not just run the container on the k8s node directly, the short answer is that you can not, k8s does
not allow containers to run directly on a cluster or a node, they always have to be wrapped in a Pod, there are three
main reasons why Pods exist

1. Pods augment containers
2. Pods assist in scheduling
3. Pods enable resource sharing

On the augmentation front, Pods augment container in all of the following ways.

1. Labels and annotations
2. Restart policies
3. Probes, startup, readiness, liveness, and more
4. Affinity and anti affinity rules
5. Termination control
6. Security policies
7. Resource requests and limits

Labels let you group pods and associate them with other obEcts in powerful ways, annotations let you add experimental
features and integrations with 3rd party tools and services, Probes let you test the health and status of Pods, enabling
advanced scheduling, updates and more. Affinity and anti affinity rules give you control over where Pods run,
Termination control lets you to gracefully terminate Pods and the app s they run, Security policies let you enforce
security features, Resource requests and limits let you specify minimum and maximum values for things like CPU and
memory and disk IO. Despite bringing so many features to the party, pods are super lightweight and add very little
overhead. Pods also enable resource sharing, they provide execution environment for one or more containers, this shared
environment includes things such as shared filesystem network stack, memory and fs-volumes.

Pods deployed directly fro the Pod manifest are called static Pods and have no super powers such as self healing scaling
and rolling updates, This is because they are only monitored and managed by the local kubelet process which is limited
to attempting container and Pod restarts, on the local node, if the node they are running on fails there is no control
plan process watching and capable of starting a new one on a different node,

Pods deployed via controllers have all the benefits of being monitored and managed by a highly available controller
running on the control plane, the local kubelet on the node they are running on can still attempt local restarts but if
restart attempts fail or the node itself fails the observing controller can start a replacement pod on a different node.

Just to be clear it is vital to understand that Pods as mortal, When they die, they are gone, there is no fixing them
and bringing them back from the dead, this firmly places them in the cattle category in the pets vs cattle paradigm,
pods are cattle and when they die they get replaced by another. This is why apps should always store state and data
outside the pod, it is also why you should not rely on individual pods, they are ephemeral - lasting for a very short
time

Pods are objects in the Nodes/Workers, and one can think of it as the Pod being room in a house, the House itself, is
the Node, the Room is the pod, and the people living in that room are the containers, the Room or Pod, does not run
anything, it does only manage the containers providing them with the shared state, it is like a bridge or adapter
between multiple containers, as already mentioned one pod object can manage multiple containers, and can provide them
with shared state which is isolated from other pods and containers

1. Each pod has its own IP address, allowing it to communicate with other Pods, between several Nodes. The container
   interface plugin is responsible for assigning IP addresses and setting up networking, pods on the same node
   communicate through a bridge, pods on a different nodes communicate through routing rules set in the CNI plugin

2. Storage between containers in a given Pod is shared using volumes, meaning that a given Pod, mounts volumes from the
   Host/Node to all containers that it is responsible for, these volumes are then used and shared only by the containers
   that this Pod governs.

3. Resources, the governing pod makes sure that each configured container does not exceed the resources allocated for
   it, and if it does it will restart or kill the container

`REMEMBER! Pods are not physical services running on the host, they are merely objects defined in the k8s deployment
config, these pod objects are picked up by the kubelet, which is the service running on the Node, it is actually the
active service that manages pods and by proxy containers defined for these pods, the pods are merely configuration
objects, which tell the kubelet how to manage a common set of containers and what to do with them in the event of
abnormal occurences or if the desired state diverges from the actual state`

#### Pods deployment

The process is simple, the pods are defined in files, as already mentioned pods are mere objects, part of the k8s
environment,

1. Define it in an YAML manifest file
2. Post the YAML to the REST API server
3. The server authenticates the request
4. The configuration file is validated
5. The scheduler deploys the pod to a healthy node
6. The local kubelet monitors it

The pod is deployed via a controller the configuration will be added to the cluster store as part of overall desired
state that has to be maintained and a controller will monitor it. The pod deployment process is an atomic one, this
means it is all or nothing deployment either succeeds or it does not, you will never have a scenario where a partially
deployed pod is servicing requests, only after all the pod's resources are running and ready will it start servicing
requests

#### Pod lifecycle

The pods' lifecycle starts with the YAML object, and is served down to the API server, then it enters the `pending
phase`, it is schedule to a healthy node, with enough resources, and the local kubelet instance running on tat node
instructs the container runtime to pull all required images and start all containers, once all containers are pulled and
running, the pod enters the `running phase`, if it is a short lived pod, as soon as all containers terminate
successfully the Pod itself terminated and enters the succeeded state. If it is a long running pod, it remains
indefinitely in the `running phase`

Short lived pods can run all different types of apps, some such as web servers are intended to be long lived and should
remain in the running phase indefinitely, if any containers in a long lived Pod fail the local kubelet may attempt to
restart them. We say the kubelet may attempt to restart them, this is based on the container's restart policy, which is
defined in the Pod object itself, Options include - Always, OnFailure and Never, always is the default restart policy
appropriate for most long lived pods, Other workloads such as batch jobs, are designed to be short lived and only run
until a task is complete, Once all containers in a short lived pod terminate, the pods terminate and its status is set
to successful, these container restart policies - Never, OnFailure, are appropriate for short lived pods

#### Pod multi-container control

Multi container pods are powerful pattern and heavily used in the real world, at a very high level every container
should have a single clearly defined responsibility, for example an app that pulls content from a repository and serves
it as a web page, has two clear functions - pull the content, serve the content

In this example one should design two containers one responsible for pulling the content and the other to serve the web
page, we call this separation of concerns. This design approach keeps each container small and simple, and it encourages
re-use, and makes troubleshooting simpler. However these are scenarios where it is a good idea to tightly couple two or
more functions, consider the same example app that pulls content and serves it via web page, a simple design would have
the sync container the one pulling content, put content updates in a volume shared with the web container, for this to
work both containers need to run in the same Pod, so they share the same volume / storage from the Pods execution
environment. Co-locating multiple containers in the same pod allows containers to be designed with a single
responsibility but work closely with others, Kubernetes offers several well defined multi container Pod patterns.

1. Sidecar pattern
2. Adapter pattern
3. Ambassador pattern
4. Init pattern

##### Sidecar

The sidecar pattern is probably the most popular and most generic multi container pattern it has a main app container
and a sidecar container, it is the job of the sidecar to augment or perform a secondary task for the main app container,
the previous example of a main app web container plus a helper pulling up to date content is a classic example of the
sidecar pattern - the sync container pulling the content from the external repository is the sidecar. An increasingly
important user of the sidecar model is the service mesh, at a high level service meshes inject sidecar containers into
app pods and the sidecar do things like encrypt traffic and expose telemetry and metrics

##### Adapter

The adapter pattern is a specific variation of the generic sidecar pattern where the helper container takes non
standardized output from the main container and rejigs it into a format required by an external system, a simple example
is NGINX logs being sent to Prometheus, Out of the box Prometheus does not understand NGINX logs, so a common approach
is to put an adapter sidecar into the NGINX pod, that converts the NGINX logs into a format accepted by Prometheus

##### Ambassador

The ambassador pattern is another variation of the sidecar pattern. This time, the helper container brokers connectivity
to an external system, for example the main app container can just dump its output to a port the ambassador container is
listening on and sit back while the ambassador container does the hard work of getting it to the external system.

##### Init

That pattern is not a form of the sidecar it runs a special init container that is guaranteed to start and complete
before your main app container. As the name suggests its jobs is to run tasks and initialize the environment for the
main app container. For example a main app container may need permissions setting an external API to be up and accepting
connections or a remote repository, cloning to a local volume, in cases like these an init container can do that prep
work and will only exit when the environment is ready for the main app container, The main app container will not start
until the init container completes

##### Notes

`Note that while the ambassador and adapter patterns might seem similar, they are meant for different tasks, while the
adapter is meant as mostly translator or normalization level, for data, from one form to another. The ambassador pattern
is meant for strictly handling communication between to containers or services, it abstracts away the communication
details, for example Envoy, is a sidecar mesh, which serves to abstract away the database connection and communication
details between a service and a database, of any type, it provides a common communications  protocol that the container
can use to communicate, without caring what is on the other side, as long as the other side also understands that
protocol, but in all actuality the other side might be using the same pattern to receive the communication.`

### Deployments

Most of the time you will deploy Pods, indirectly via a higher level controllers. Examples of higher level controllers
include `Deployments`, `DeamonSets` and `StatefulSets`. As an example a Deployment is a higher level Kubernetes object
that wraps around a Pod and adds features such as self-healing, scaling, zero-downtime rollouts, and versioned
rollbacks.

Behind the scenes, `Deployments, DeamonSets and StatefulSets` are implemented as controllers that run as watch loops
constantly observing the cluster making sure observed state matches desired state.

### Services

Since we have already mentioned that Pods can die, they are also managed via a higher level controllers and get replaced
when they die or fail. But replacements come with a totally different IP addresses. This also happens with rollouts and
scaling operations. Rollouts replace old Pods with new ones with new IPs. Scaling up adds new Pods with new IP
addresses, whereas calling down takes existing Pods away. Events like these cause a lot of IP churn. The point we are
making is that Pods are unreliable and this poses a challenge. Assume you have got a microservice app with a bunch of
Pods performing video rendering. How will this work if other parts of the app that use the rendering service can not
rely on rendering Pods being there when needed. This is where Services come in to play. They provide reliable networking
for a set of Pods.

Services are fully fledged objects in the Kubernetes API - just like Pods and Deployments. They have a front end
consisting of a DNS name, IP address and port. On the back end they load balance traffic across a dynamic set of Pods.
As pods come and go, the Service observes this, automatically updates itself, and continues to provide that stable
networking endpoint. The same applies if you scale the number of Pods up or down. New Pods are seamlessly added to the
Service and receive traffic. Terminated Pods are seamlessly removed from the Service and will not receive traffic. That
is the job of a Service - it is a stable network abstraction point that provides TCP and UDP load balancing across a
dynamic set or number (replicas) of Pods/containers

As they operate at the TCP and UDP layer, they do not posses application intelligence, this means they can not provide
app layer host and path routing. For that you need an Ingress which understand HTTP and provides host and path based
routing.

`Services bring stable IP addresses and DNS names to the unstable world of Pods, they are the abstraction layer, that
allows other Services, Pods or Containers to communicate without having to worry about the fact that a target Pod can
die`

## Virtual clusters

Namespaces are the native way to divide a single k8s cluster into multiple virtual clusters, these are not the standard
Linux kernel namespaces, that we have already looked at, the ones responsible for namespacing processes on the kernel
level. K8s namespaces divide the Kubernetes clusters into virtual clusters called - Namespaces

Namespaces partition a Kubernetes cluster and are designed as an easy way to apply quotas and policies to groups of
objects, they are not designed for strong workload isolation. Most k8s objects are deployed into a Namespace, these
objects are said to be namespaced, and include common objects like Pods, Services and Deployments. If you do not
explicitly define a target namespace when deployment a namespaced object, it will be deployed to the default namespace,
you can run the following command to

Namespaces are a good way of sharing a single cluster among different departments and environments for example a single
cluster might have the following Namespace, Dev, Test, QA. Each one can have its own set of users and permissions as
well as unique resource quotas, What they are not good for isolating hostile workloads, this is because a compromised
container or pod in one namespace can wreak havoc in other namespaces, putting into context, you should not competitive
workloads together. Every k8s cluster has set of pre created namespaces, virtual clusters

```sh
# show the list of all namespaces in the current cluster
$ kubectl get namespaces
# one might get an output like that,
> NAME STATUS AGE
> kube-system Active 3d
> default Active 3d
> kube-public Active 3d
> kube-node-lease Active 3d
```

The default namespace is where newly created objects go unless you explicitly specify otherwise, Kube-system is where
DNS the metrics server and other control plane components run, Kube-public is for objects that need to be readable by
anyone, and last but not least kube-node-lease is used for node heartbeat, and managing node leases.

Namespaces are first class resources in the core v1 API group, This means that they are stable well understood and have
been around for a long time, it also means you can create and manage them imperatively with `kubectl` and decoratively
with YAML manifests.

```yml
# sample namespace that is not the default one
kind: Namespace
apiVersion: v1
metadata:
    name: shield
    labels:
    env: marvel
```

```sh
# apply the config to the cluster
$ kubectl apply -f shield-ns.yml
```

When you start using Namespaces you will quickly realize it is painful remembering to dd the -n or --namespace flag on
all `kubectl` commands. A better way might be to set your `kubeconfig` to automatically work with a particular
namespace, the following command configures `kubectl` to run all future commands against the shield Namespace

```sh
# this will make sure that all following commands against kubectl run in the context of the namespace `shield`
$ kubectl config set-context --current --namespace shield
```

To deploy to a given Namespace, as already mentioned most all objects are always tied to a namespace, and if you do not
specify otherwise the default namespace will be used when deploying objects, there are two different ways to deploy
objects to a specific namespace - imperatively and declaratively.

The imperative method requires you to add the -n flag to the command, the declarative method specifies the namespace in
the YAML manifest file. We will declaratively deploy a simple app to the shield namespace, and test it.

```yml
apiVersion: v1
kind: ServiceAccount
metadata:
    namespace: shield <<== Namespace
    name: default << ServiceAccount name
---
apiVersion: v1
kind: Service
metadata:
    namespace: shield <<== Namespace
    name: the-bus <<== Service name
spec:
    ports:
    - nodePort: 31112
      port: 8080
      targetPort: 8080
    selector:
        env: marvel
---
apiVersion: v1
kind: Pod
metadata:
    namespace: shield <<== Namespace
    name: triskelion <<== Pod name
<snip>
```

`Note the use of metadata, this is a common pattern in k8s config manifests, the metadata field is not just for humans
to read, it is often used to also provide control flow to the k8s cluster itself, based on the metadata, the k8s
environment knows what to do with the object, the metadata provides context for the object it is defined for in this
case we define that this particular object is created for this namespace, but other metadata keys also exist and are
used to e.g the name of the object is defined in the metadata field, and that same name is what can be used to reference
that object in other objects. The namespace itself, we have create above, the shield name was defined in the metadata
section`

To deploy these resources, save the YAML manifest as file, and then simply run `kubectl apply -f shield-app.yml`, to
clean up the same resources one can use the `kubectl delete -f shield-app.yml`. The nice part here is that having all of
this deployed in a declarative manner, allows us to clean up the resources using the same declaration and file, no need
of manual steps to delete each object, or having to know in what order they need to be deleted, worrying about stopping
some of the resources which have been allocated by these k8s objects them and so on.

## Kubernetes deployments

Kubernetes offers several controllers that augment Pods with important capabilities, the deployment controller is
specifically designed for stateless app, we will cover some other controllers later on as well.

### Theory

There are two major pieces to deployments, the spec and the controller, the deployment spec is a declarative. The
deployment spec is a declarative YAML object where you describe the desired state of a stateless app, you give that to
kubernetes where the deployment controller implement and manages it, the controller aspect is highly available and
operates as a background loop reconciling observed state with desired state. Deployment objects, and all of their
features and attributes, are defined in the apps/v1 workloads API.

`Note that the kubernetes api is architecturally divided into smaller sub groups to make it easier to manage and
navigate, the apps sub group is where Deployment, DeamonSets and StatefulSet and other workload related objects are
defined, we sometimes call it the workloads API`

You start with a stateless app package it as a container then define it in a Pod template, at this point you could run
it on the kubernetes, however static pods like this do not self heal they do not scale and they do not allow for easy
updates and rollbacks. For these reasons you will almost always wrap them in a deployment object.

### `ReplicaSets`

Behind the scenes deployments rely heavily on another object called replica set. While it is usually recommended not to
manage replica sets directly, deployment controller manage them, it is important to understand the role they play, at a
high level `Containers` provide way to package apps and dependencies Pods allow containers to run kubernetes and enable co
scheduling and a bunch of other good stuff, `ReplicaSets` manage pods and bring self healing and scaling, `Deployments`
manage replica sets and add rollouts and rollbacks.

`ReplicaSets` are implemented as a controller running as a background reconciliation loop checking the right number of Pod
replicas are present on the cluster, if there are not enough it adds more, if there too many it terminates some, assume
a scenario where the desired state is 10 replicas but only 8 are present, it makes no difference if this is due to a
failure or if it is because an `autoscaler` has increased desired state from 8 to 10, Either way, this is a red alert
condition for Kubernetes, so it orders the control plane to bring two more replicas.

`Note that ReplicaSet are owned by the Deployment object, meaning that they are subordinated to them, and their
lifecycle is tied to the Deployment object's lifecycle, when deployment configuration is updated create new ReplicaSets
for the deployment to which the update was made, to begin the deployments of the new Pods, while the old ReplicaSet pods
are being wound down.`

### Pods

A deployment object only manages a single pod template, for example, an app with a front end web service and a back end
catalog will have a different pod for each (two Pod templates). As a result it will need two deployment objects one
managing front end pods, the other managing back end pods, however a deployment can manage multiple replicas of the same
pod, for example the front end deployment might be managing 5 identical front end pod replicas.

### Skeleton

The basic structure of the Deployment object is presented below, it is crucial to understand that the Deployment object
technically controls many aspects of the underlying process of managing Pods, that includes creating and destroying
`ReplicaSet` and other object. To be clear, the Deployment object is just that, an object, the actual management happens
at the kubelet level, which reads these configurations and controls and manages the actual state of the Node, in the cluster.

```yml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: hello-deploy
spec:
    replicas: 10
    selector:
        matchLabels:
            app: hello-world
    revisionHistoryLimit: 5
    progressDeadlineSeconds: 300
    minReadySeconds: 10
    strategy:
        type: RollingUpdate
        rollingUpdate:
            maxUnavailable: 1
            maxSurge: 1
    template:
        metadata:
            labels:
                app: hello-world
        spec:
            containers:
                - name: hello-pod
                  image: nigelpoulton/k8sbook:1.0
                  ports:
                      - containerPort: 8080
                  resources:
                      limits:
                          memory: 128Mi
                          cpu: 0.1
```

- `apiVersion`: At the top the API version is specified that is to be used.

- `kind`: that is the type of the object that is being defined, in this case the Deployment

- `metadata`: gives the Deployment a name, this should be a valid DNS name, so, that means alphanumeric the dot and the
  dash are valid, avoid exotic characters.

- `spec`: this section is where most of the action is, anything directly below spec relates to the Deployment, anything
  nested below refers to the actual behavior of the deployment object

  - `spec.template` is the Pod template the Deployment uses to stamp out the Pod replicas, in this example the
      Pod template defines a single container Pod.

  - `spec.replicas` is how many pod replicas the deployment should create and manage.

  - `spec.selector` is a list of labels that pods must have in order for the deployment to manage them, notice how
      the Deployment selector matches the labels assigned to the pod.

  - `spec.revisionHistoryLimit` tells Kubernetes how may older version of `ReplicaSet` to keep, keeping more gives you
      more rollback options but keeping too many can bloat the object, this can be a problem on large cluster with lots of
      software releases.

  - `spec.progressDeadlineSeconds` tells kubernetes how long to wait during a rollout for each new replica to come
      online, the example sets a 5 minute deadline, meaning that each new replica has 5 minutes to complete up before
      kubernetes considers the rollout stalled, to be clear the clock is reset after each new replica comes up meaning each
      step in the rollout gets its own 5 minute window.

  - `spec.strategy` tells the deployment controller how to update the pods when a rollout occurs. There are some more
      details to take a look at here, first the `maxUnavailable` - which tells that no more than one Pod below the desired
      state should considered valid state, meaning that somehow two pods failed, getting us at 8, the kubelet will try to
      scale up to 10. The `maxSurge` - which means that we should not have more than one pod above the desired state, i.e if
      somehow the deployments overshoot 10, i.e become 12, the additional pods will be scaled down to match the desired
      state.

```sh
# to activate the deploy configuration
$ kubectl apply -f deploy.yml

# to get a brief description of it
$ kubectl get deploy hello-deploy

# to get full details of the object
$ kubectl describe deploy hell-deploy
```

### Rollouts

Rolling updates with deployments zero downtime rolling updates of stateless apps are what Deployments are all about and
they are amazing, however they require a couple of things from your microservice apps in order to work properly, - loose
coupling via API and backward and forward compatibility. Both of these are hallmarks of modern cloud native microservice
apps and work as follows. All microservices in an app should be decoupled and only communicate via a well defined API.
This allows any microservice to be updated without having to think about clients and other microservices that interact
with them everything talks to a formalized API that expose documented interface and hide specifics. Ensuring releases
are backwards and forwards compatible means you ca perform independent, updated without having to factor in which
versions of the clients are consuming the service. With those points in mind, zero downtime rollouts work like this:

Assume you are running 5 replicas of a stateless web front end. As long as all clients communicate via API and are
backwards and forwards compatible it does not matter which of the 5 replicas a client connects to. To perform a rollout,
Kubernetes creates a new replica running the new version and terminates an existing one running the old version. At this
point you have got 4 replicas on the old version and 1 on the new. This process repeats until all 5 replicas are on the
new version. As the app is stateless and there are always multiple replicas up and running clients experience no
downtime or interruption of service, there is actually a lot that goes on behind the scenes so let us look at this

You design apps which each discrete microservice as its own Pod. For convenience self healing and scaling rolling update
and more - you wrap the pod in their own higher level controller such a Deployment. Each Deployment describes all the
following

- How many Pods replicas
- What image to use for the Pods container
- What network ports to expose
- Details about how to perform rolling updates

In the case of Deployments when you post the YAML file to the API server, the Pods get scheduled to healthy nodes and a
deployment and `ReplicaSets` work together to make the magic happen. The `ReplicaSet` controller sits in a watch loop making
sure our observed state and desired state are in agreement. A Deployment object sits above the `ReplicaSet` governing its
configuration as well as how rollouts will be performed. Now assume you are exposed to a known vulnerability and need to
rollout a newer image, with the fix, to do this you update the same Deployment YAML file with the new image version and
re-post that to the API server. This updates the existing Deployment object with a new desired state requesting the same
number of Pods but all running the newer image.

To make this happen, kubernetes creates a second `ReplicaSet` to create and manage the Pods with the new image, you now
have two `ReplicaSets` - the original one for the Pods with the old image, and the new one, for the Pods with the new
image. As Kubernetes increases the number of Pods in the new `ReplicaSet` it decreases the number of Pods in the old
`ReplicaSet`. Net result you get a smooth incremental rollout with zero downtime.

You can rinse and repeat the process for future updates - just keep updating the same Deployment manifest file which
should be stored in a version control system

The way Kubernetes knows how to correctly rollout a given pod is by using the list of labels, the Deployment controller
looks for when finding Pods to update during rollouts operations in this example it is looking for Pods with the given
label, `the label selector is immutable you can not change it once it is deployed`.

So imagine the following situation we have a deployment which has a container image with version 1.0, and we would like
to deploy a set of new pods with a new version 2.0, this would imply we have to only change one thing, that is the
version of the image defined in the YAML manifest file of the deployment so something like changing the image tag from 1
to 2 here - `image: nigelpoulton/k8sbook:2.0`, this would trigger the internal process of creating new `ReplicaSet` for
the new pods, which will start creating new pods, with the new version, scaling to the target number of replicas which
we have defined in our deployment manifest file. We simply have to apply with - `kubectl apply -f deploy.yml`. To
monitor the status one can use `kubectl rollout status deployment hello-deploy`.

The rollouts can also be paused, this can be done with the rollout pause command, for example `kubectl rollout pause
deploy hello-deploy`, if one tries to run the `kubectl describe` provides some information on the state of the deployment
and also the state of the `ReplicaSet`.

```sh
# pause the deployment, after we have run the apply -f deploy.yml, immediately just pause
$ kubectl rollout pause deploy hello-deploy
deployment.apps/hello-deploy paused
```

```sh
# print out the current state of the deployment object, note it is marked as DeploymentPaused
$ kubectl describe deploy hello-deploy
Name:                  hello-deploy
Annotations:           deployment.kubernetes.io/revision: 2
Selector:              app=hello-world
Replicas:              10 desired | 4 updated | 11 total | 11 available | 0 unavailable
StrategyType:          RollingUpdate
MinReadySeconds:       10
RollingUpdateStrategy: 1 max unavailable, 1 max surge
<Snip>
Conditions:
Type                   Status Reason
----                   ------ ------
Available              True MinimumReplicasAvailable
Progressing            Unknown DeploymentPaused
OldReplicaSets:        hello-deploy-85fd664fff (7/7 replicas created)
NewReplicaSet:         hello-deploy-5445f6dcbb (4/4 replicas created)
```

The `deployment.kubernetes.io` annotation shows the object is on revision 2, the revision 1, was the initial rollout and
this update we have done is revision 2, Replicas shows the rollout is incomplete, the third line from the bottom shows
the Deployment condition as progressing but paused, finally you can see that the `ReplicaSet` for the initial release is
would up to 7 replicas and the one for the new release is up to 4, paused right before all new pods were actually
finished deploying

```sh
# To resume the deployment process
$ kubectl rollout resume deploy hello-deploy
```

After we resume the process will continue from where it was paused, meaning that the remaining set of replicas and pods
will be scaled up to the desired state, and the state of the deployment object will no longer show that it is
`DeploymentPaused`, but the state will be completed, after all pods are up and running, in the meantime the old
`ReplicaSet` will be gradually decommissioned and all of its pods with it, we will see in the next section how to
actually rollback to this very `ReplicaSet` that will be getting decommissioned, which has the version 1.0 of the image.

### Rollbacks

As you saw older `ReplicaSet` are wound down and no longer manage Pods. However their configuration still exists on the
cluster, making them a great option for reverting to previous versions, The process of a rollback is the opposite of of
a rollout you wind the one of the old `ReplicaSet` up while you wind the current one down.

Imagine the situation where the current image for the deployment object is updated to a new version, from 1 to 2, and
that the deployment object was updated and everything went smoothly, the old version 1 Pods were decommissioned by the
`ReplicaSet` and the new ones were now in place, however as we know the old `ReplicaSet` is still active, assuming our
deployment configuration is configured to hold at least 2 versions of the deployment history with history -
`revisionHistoryLimit`.

A rollout history can be obtained using the command `kubectl rollout history deployment hello-deploy`, Revision 1 was
the initial deploy, that used the 1.0 image, tag, Revision 2.0 is the rolling update we just performed, The old
`ReplicaSet` are still active, for the old image version, meaning that we can easily revert to those, which will in turn
commission a new set of Pods with the original version of the image 1.0. So if we call `kubectl get rs`, we should at
the very least see two `ReplicaSet` for bound to the hello-deploy object.

```txt
NAME                    DESIRED CURRENT READY AGE
hello-deploy-65cbc9474c 0       0       0     42m
hello-deploy-6f8677b5b  10      10      10    5m
```

From this output we can see some useful info, like that the old `ReplicaSet` has no pods that are currently active, that
one is the one which was deployed with the original image, version 1.0, and the new one has, this one is the new one
with the new image version 2.0. Now what we need to do is simply commission the old one again, and decommission the new
one. This will take care of creating the new pods and removing the old ones as necessary.

`Note that rollback and update are in a way a similar thing, meaning that the process being followed when a rollback is
done is the same as rollout, the difference is only meaningful for the person doing the process, the underlying
Kubernetes infrastructure does not distingush between rollout and rollback, it just applies one set of ReplicaSet and
decommissions the other.`

```sh
$ kubectl rollout undo deployment hello-deploy --to-revision=1
deployment.apps "hello-deploy" rolled back
```

This operation is not instant, remember that the rollback has to provision the new (technically old) set of pods with
the original image 1.0, and remove the new ones, however as we have already seen this is not happening in an instant, as
it is a gradual process of bringing up the pods with the old version 1.0 of the image and removing the ones with the new
2.0 version of the image.

### Labels

As we have already seen that `Deployments` and `ReplicaSet` use labels and selectors to find Pods they own, it was
possible in earlier versions of kubernetes for deployments to take over management of existing static pods if they had
the same label, however recent versions use the system generated pod template hash label so only pods create by the
`deployment/ReplicaSet` will be managed. Assume a quick example you already have 5 pods on a cluster with the label
app=front-end. At a later date, you crate a deployment that requests 10 pods with the same app=front-end label. Older
versions of Kubernetes would notice there were already 5 Pods with that label and only create 5 new ones, and the
`Deployment/ReplicaSet` will manage all 10. However newer versions of Kubernetes tag all pods created by a
`deployment/ReplicaSet` with the pod-template-hash label. This stops higher level controllers seizing ownership of
existing static pods.

```sh
$ kubectl describe deploy hello-deploy
Name: hello-deploy
NewReplicaSet: hello-deploy-5445f6dcbb

$ kubectl describe rs hello-deploy-5445f6dcbb
Name: hello-deploy-5445f6dcbb
Selector: app=hello-world,pod-template-hash=5445f6dcbb

$ kubectl get pods --show-labels
NAME READY STATUS LABELS
hello-deploy-5445f6dcbb.. 1/1 Running app=hello-world,pod-template-hash=5445f6dcbb
hello-deploy-5445f6dcbb.. 1/1 Running app=hello-world,pod-template-hash=5445f6dcbb
hello-deploy-5445f6dcbb.. 1/1 Running app=hello-world,pod-template-hash=5445f6dcbb
```

So you can see how the different levels of objects actually are linked together through the pod template hash, along
with the label selector

### Cleanup

To delete the deployment object one should simply use the `kubectl delete -f deploy.yml`, this will make sure to clean
up all resources tied to the deployment object, that implies all ReplicaSets, Pods and everything else, that might be
actively connected to the `hello-deploy`

## Kubernetes Services

As we have already seen how pods are related containers, and then to Deployments, we have seen the core levels of
abstraction, starting off from `Containers -> Pods -> -> ReplicaSet -> Deployments`, each of these provide different
capabilities, the containers are what provide a meaningful way to run images, the Pods are used to manage the resources
and namespace the containers, the `ReplicaSet` are governing how to scale pods and containers, and the deployments are all
about self healing and overall control over everything else below.

There is a higher level of abstraction in the kubernetes world, and these are called services. Services provide a
reliable networking for a set of unreliable Pods managed by Deployments, since pods and containers effectively are
immutable and ephemeral can be created and destroyed without any notice, we need a way to abstract away the gazillion
number of Pods that might come into life or get destroyed, without having to think about that process at all.

When a Pods fail they get replaced by a new one with new IP. Scaling up introduces a new Pod with new IP addresses,
scaling down removes Pods. Rolling updates also replace existing Pods with completely new ones with new IPs. This create
a massive IP churn, and demonstrates why you should never connect directly to any particular pod. You also need to know
3 fundamental things about Kubernetes Services

- First when talking about Services, we are talking about Service object in the Kubernetes world that provides a stable
  networking for Pods. Just like a Pod, ReplicaSet and Deployment, Services are defined through a manifest YAML file,
  posted to the API server.

- Second every Service gets its own stable IP address, its own stable DNS name and its own stable port.

- Third, Services use labels and selectors to dynamically select the Pods to send traffic to.

### Theory

With a service in place the Pods can scale up and down they can fail and they can be updated and rolled back, and
clients will continue to access them without interruption. This is because the Service is observing the changes and
updating its list of healthy Pods. But it never changes its stable IP, DNS and port

Think of services as having a static front end and a dynamic back end the front end consisting of the IP, DNS name and
port never change, The back end comprising the list of healthy Pods can be constantly changing.

### Labels

Services are loosely coupled with Pods via labels and selectors. This is the same technology that loosely couples
Deployments to Pods and is key to the flexibility of Kubernetes. For the service to send traffic to a give Pod, the Pod
needs every label the Service is selecting on. It can also have additional Labels the Service is not looking for.
However the `Service might have multiple labels and the logic between those is AND`. Therefore extra care is needed when
configuring selection labels for the pods

`Services are Orthogonal to Deployments, they are not responsible for managing deployments, they like the deployment
object work with pods, therefore the Services and Deployment objects are on the same "level" in the kubernetes
hierarchy, just above the Pod, services do not control deployments, they work along side deployment objects to manage
pods, deployments are responsible for managing the deployment process, while services are meant to manage traffic and
abstract away the communication between the pod and the outside world`

Take a look at these two definitions, one is for Service the other for Deployment

```yml
apiVersion: v1
kind: Service
metadata:
    name: hello-svc # <<- name of the service
    labels:
        app: hello-world
spec:
    type: NodePort
    ports:
        - port: 8080
          nodePort: 30001
          protocol: TCP
    selector:
        app: hello-world # <<- match and manage all pods with this label
```

```yml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: hello-deploy # <<- name of the service
spec:
    replicas: 10 # <<- ReplicaSet properties and options start here
    selector:
        matchLabels:
            app: hello-world # <<- match and manage all pods with this label
    revisionHistoryLimit: 5
    progressDeadlineSeconds: 300
    minReadySeconds: 10
    strategy:
        type: RollingUpdate
        rollingUpdate:
            maxUnavailable: 1
            maxSurge: 1
    template: # <<- the pod definition starts here
        metadata:
            labels:
                app: hello-world # <<- this is the label of the pod that the service and deployment will match
        spec:
            containers: # <<- tell the pod what image it has to use
                - name: hello-pod
                  image: nigelpoulton/k8sbook:1.0
                  ports:
                      - containerPort: 8080
                  resources:
                      limits:
                          memory: 128Mi
                          cpu: 0.1
```

Note that Deployments and Services both have to have correct selectors configured to match against the labels of the
Pods. While deployments create the Pods through the use of the `ReplicaSets`, deployments are not managed by Services,
services manage pods just like deployments do through the `ReplicaSets` objects. It might seem a bit odd, since the pod is
defined only in the Deployment, and does not exist as a standalone "object" in the Kubernetes world

One might have noticed that the Service uses a different definition for the selector than the Deployments object, this
is because the Services selector is simpler, the selector of services only provides a way for simple matching, there is
no way to do advanced expression matching like the one of the Deployment object, which is the `selector.matchLabels` or
`selector.matchExpression`

Here are two examples of how Deployment can use the advanced selector section, which is not available for Services, it
can match either on simple labels, just like Services or on more advanced expression rules

```yml
# Deployment spec
spec:
  selector:
    matchLabels:
      app: my-app

spec:
  selector:
    matchExpressions:
      - key: app
        operator: In
        values: ["my-app", "test-app"]
```

The service on the other hand is rather simple, it just matches on one or more labels with AND condition

```yml
# Service spec
spec:
    selector:
        app: my-app
```

### Endpoints

As pods come and go, the Service dynamically updates its list of healthy matching Pods. It does this through a
combination of label selection and a construct called and Endpoint object. Every time you create a Service, Kubernetes
automatically creates and associated Endpoint object. The endpoints object is used to store a dynamic list of healthy
pods matching the service's label selector

Kubernetes constantly is evaluating the Service label selector against the healthy Pods on the cluster, as new pods that
match the selector get added to the endpoints object whereas any pods that disappear get removed this means that the
endpoints object is always up to date.

When sending traffic to pods via a service the cluster's internal DNS resolves to the service name to an IP address. It
then sends the traffic to this stable IP address and the traffic gets routed to one of the Pods in the endpoints list,
However a Kubernetes native application, can query the endpoint API directly bypassing the DNS lookup and use the
service's IP address

Accessing Services from inside the cluster, there are several service types, the default one is called `ClusterIP`. A
`ClusterIP` service has a stable virtual IP address that s only accessible from inside the cluster, we call this a
`ClusterIP` it is programmed into the network fabric and guaranteed to be stable for the life of the service, programmed
into the network fabric is a fancy way of saying that the network just knows about it and you do not need to bother with
the details.

The `ClusterIP` is registered against the name of the service in the cluster internal DNS service, all pods in the
cluster a are pre-configured to use the cluster DNS service meaning all pods can convert service names to `ClusterIP`

That means that if we create a new Service, called magic-sandbox will dynamically assign a stable `ClusterIP`. This name
and the `ClusterIP` are automatically registered with the cluster's DNS service, These are all guaranteed to be long
lived and stable. As all pods in the cluster send service discovery requests to the internal DNS they can all resolve
magic-sandbox to the actual IP, based on the `ClusterIP`. `Iptables` or `IPVS` rules are distributed across the cluster to
ensure traffic sent to the `ClusterIP` gets routed to matching Pods. Net result if a Pods knows the name of a Service
it can resolve that to a `ClusterIP` address and connect to the Pods behind it. This only works for Pods and other objects
on the cluster as it requires access to the cluster's DNS service, It does not work outside the cluster

Lets have a simple example. Imagine a service named `one` and another one named `two`, active pods are running for both,
we are located in a pod in service `one`, how would like to make a call to a Pod in service `two`. Here’s how a `curl`
request would look like from a Pod in Service `one` to a Pod in Service `two`:

```bash
# note that the HOSTNAME of the service two, includes the name of the service, as already established above, that is normal,
# then the namespace under which this service is deployed, by default that is the `default` namespace, and then the cluster
# name suffix, that is configured in the `CoreDNS` server that kubernetes is using as implementation of the DNS service
curl http://two.default.svc.cluster.local
```

What are the exact elements of this FQDN specified in the curl request:

- `two`: The name of the Service to call.

- `default`: The namespace where the Service `two` is deployed. If the Service is in a different namespace, replace
  default with that namespace name.

- `svc.cluster.local`: The default domain for Services in Kubernetes, The `svc.cluster.local` domain is the default DNS
  suffix for Services in Kubernetes. It is defined in the `CoreDNS` or `kube-dns` configuration. The configuration is
  typically stored in a **ConfigMap** named `coredns` (or `kube-dns` in older clusters) in the `kube-system` namespace.

`Services uses a special auxiliary EndpointSlices object internally, to manage the endpoints for the pods the service is
responsible for and matches based on the selector labels`

### Types

Accessing Services from outside the cluster, Kubernetes has two types of Services for requests originating from outside
the cluster - `NodePort` and `LoadBalancer`

- `NodePort` Services build on top of the `ClusterIP` type and enable external access via a dedicated port on every cluster
  node, we call this port the `NodePort`. Since the default service type is `ClusterIP` and it registers a DNS name virtual IP
  and port with the cluster's DNS. `NodePort` Services build on this by adding a `NodePort` that can be used to reach the
  service from outside the cluster. Below is a type of `NodePort` service

`NodePort service types are not so special, since while they would allow you to access a service from the outside world,
there is no other way but to know the exact IP address of a node, you call directly the node, meaning that you always
hit the same node from the cluster.`

```yml
apiVersion: v1
kind: Service
metadata:
    name: hello-svc
    labels:
        app: hello-world
spec:
    type: NodePort
    ports:
        - port: 8080
          nodePort: 30001
          protocol: TCP
    selector:
        app: hello-world
```

Pods on the cluster can access this service by the name magic-sandbox, on port 8080. Clients connecting from outside the
cluster can send traffic to any cluster node on port 30081.

- `LoadBalancer` service types make external access even easier by integrating with an internet facing load balancer, on
  your underlying cloud platform, You get a high performance highly available public IP or DNS name that you can access
  the service from, you can even register friendly DNS names to make access even simpler, you do not need to know the
  cluster node names or IP. `LoadBalancer` services are tightly coupled with cloud providers. They may not work in
  on-premises environments without additional configuration (e.g., using `MetalLB`).

`LoadBalancer has the benefit that there is a load balancer service/server infront of the cluster nodes, and unlike the
NodePort type, we do not hit a cluster node IP directly, we hit the IP or domain name of the load balancer, which would
then route the traffic to one of the underlying nodes on the cluster, this gives us the benefit of first not caring
about node IP addresses and balancing traffic`

```yaml
apiVersion: v1
kind: Service
metadata:
    name: my-loadbalancer-service
spec:
    type: LoadBalancer
    ports:
        - port: 80 # Port exposed by the load balancer
          targetPort: 8080 # Port on the Pods
    selector:
        app: my-app
```

What happens if we have a node inside of which we have deployed multiple pods that match the given service, would k8s
load balance between the pods within the node itself ? Yes, most often the used algorithm is just simple round-robin,
meaning that all pods on the given node for a given matching service/selector will be hit sequentially, one after the
other, in a round robin style. This is often done by the `kube-proxy`

### Registration

Service registration is the process of an app posting its connection details to a service registry so other apps can
find it and consume it, a few important things to note bout service discovery in k8s - Kubernetes uses its internal DNS
as a service registry, All k8s service automatically register their details with the DNS.

For this to work, k8s provides a well known internal DNS service that we usually call the cluster DNS. It is well known
because every pod in the cluster knows where to find it, it is implemented in the kube-system namespace as a set of Pods
managed by a Deployment called `coredns`. These pods are fronted by a Service called kube-dns. Behind the scenes it is
based on a DNS technology called `CoreDNS`, and runs as a k8s native app.

The actual registration is divided in two parts - we can call them front and back end, briefly this is what is going on:

- The front end - that is the actual API server receiving the request to deploy the service on the cluster, there are
  certain steps (see below) that happen here, like registering the service IP in the cluster DNS creating the Service
  object, and other auxiliary objects

- The back end - this is all work that needs to be done on the actual node that runs the pods matching the new service,
  through the selector metadata. This is for example configuring `iptables` on the actual nodes

```sh
# to list the actual pods which are running the coredns deployment
$ kubectl get pods -n kube-syste- -l k8s-app=kube-dns
NAME                     READY STATUS  RESTARTS AGE
coredns-5644d7b6d9-fk4c9 1/1   Running 0        28d
coredns-5644d7b6d9-s5zlr 1/1   Running 0        28d
```

```sh
# to list the actual deployment object that is managing these pods
$ kubectl get deploy -n kube-system -l k8s-app=kube-dns
NAME    READY UP-TO-DATE AVAILABLE AGE
coredns 2/2   2          2         28d
```

```sh
$ kubectl get svc -n kube-system -l k8s-app=kube-dns
NAME     TYPE      CLUSTER-IP     EXTERNAL-IP PORT(S)                AGE
kube-dns ClusterIP 192.168.200.10 <none>      53/UDP,53/TCP,9153/TCP 28d
```

So how does the process of service registration work:

1. You post a new service manifest to the API server.
2. The request is authenticated authorized and subject to admission policies
3. The service is allocated a stable virtual IP address called `ClusterIP`
4. An endpoints object i.e `EndpointSlices` is created to hold a list of healthy pods matching the service label selector
5. The pod network is configured to handle traffic sent to the `ClusterIP`
6. The service name and IP are registered with the cluster DNS service

The step 6 is the secret sauce, we mentioned earlier that the cluster DNS is a kubernetes native app. This means it
knows it is running on k8s and implements a controller that watches the API server for new Service objects, any time it
observes one it automatically creates the DNS records mapping and links the service name to its `ClusterIP`. This means
apps and even services do not need to perform their own service registration the cluster DNS does it for them it is
important to understand that the name registered in the DNS for the service is the value stored in its `metdata.name`
property, this is why it is important that service names are a valid DNS names and do not include exotic characters, The
`ClusterIP` is dynamically assigned by K8s

```yml
apiVersion: v1
kind: Service
metadata:
    name: valid-dns-name-goes-here <<== this is the secret sauce and should be a valid dns name
```

Now that the service front end is registered and can be discovered by other apps, the back end needs building so there
is something to send traffic to, this involves maintaining a list of healthy pod IPs the service will load balance
traffic to. As explained earlier every service has a label selector that determines which pods it will load balance and
manage traffic to. To help with backend operations such as knowing which pods to send traffic to and how traffic is
routed k8s builds and endpoint object - `EndpointSlices` for every Service

The kubelet agent on every node is watching the API server for new `EndpointSlices` objects. When it sees one it creates
local networking rules to redirect `ClusterIP` traffic to pod IP in modern k8s clusters the technology used to create
these rules is the Linux IP virtual server (`IPVS`). Older version used `iptables`.

At this point the service is fully registered, and ready to be used. Next is the active phase which is the service
discovery this happens when an actual application wants to actually connect to another Service running in the cluster.

### Discovery

Service discovery is quite an important topic, but to be brief kubernetes uses DNS service, Kubernetes clusters run an
internal DNS service that is the center of service discovery, service names are automatically registered with the DNS
service on the cluster, every Pod and container is pre-configured to use the cluster DNS (e.g. /etc/resolv.conf). This
means every Pod or container can resolve every Service name to a `ClusterIP` and connect to the Pods behind it.

The alternative form of service discovery is through environment variables every pod gets a set of environment variables
that resolve Services currently on the cluster. This is extremely limited, they can not learn about new services added
after the Pod they are in was created. This is a major reason DNS is the preferred method.

Let us assume there are two microservice apps on the same K8s cluster - called `enterprise` and `cerritos`. The Pods for
enterprise sit behind a Service called `ent`, and the Pods for `cerritos` sit behind another Service called `cer`. They are
being assigned `ClusterIP`, which are registered with the cluster DNS service, and things are as follows

| App        | Service name | ClusterIP       |
| ---------- | ------------ | --------------- |
| Enterprise | ent          | 192.168.201.240 |
| Cerritos   | cer          | 192.168.200.217 |

For service discovery to work apps need to know both of the following:

- The name of the other app they want to connect to - that is the name of service fronting the pods
- How to convert the name of to an IP address

Apps developers are responsible for point 1, which is normal, They need to code apps with the names of other apps they
want to consume, Actually they need to code the names of Services fronting the remote apps, or in other words the pods
running the apps. K8s takes care of the second part.

Converting the names to IP addresses using the cluster DNS, happens by k8s, automatically configuring that in every
container so it can find and use the cluster DNS to convert service names to IPs. It does this by populating every
container's `/etc/resolv.conf` file with the IP address of the CLUSTER DNS service, as well any search domains that
should be appended to unqualified names

```sh
$ cat /etc/resolv.conf
search svc.cluster.local cluster.local default.svc.cluster.local
nameserver 192.168.200.10
options ndots:5
```

Let us explore a little side tangent. What is the structure of the `resolv.conf` file, and here is some basic theory:

What is an unqualified name ? That is a short name such as `ent`. Appending a search domain converts it to a fully
qualified domain name (FQDN) such as `ent.default.svc.cluster.local`. The following snippet shows a container that is
configured to send DNS queries to the cluster DNS at 192.168.200.10. It also lists three search domains to append to
unqualified names.

**Fully Qualified Domain Name (FQDN):** A hostname that ends with a dot (e.g., `host.`) is considered an FQDN. This
means the DNS resolver will treat it as a complete domain name and **will not append any search domains**.

**Unqualified Hostname:** A hostname without a trailing dot (e.g., `host`) is considered unqualified. In this case, the
DNS resolver will append search domains (if configured) to attempt resolution.

- `nameserver` - well that is pretty self explanatory, this is pointing at the IP address of the cluster DNS service, this
  is a must have in order to resolve the Service names, otherwise there is no way for us to map the Service name to an
  actual `ClusterIP`, and eventually to an actual `EndpointSlices` and to a physical Pod IP address

- `search` - this one is a bit more complex, first we have to understand what an FQDN is, those are domain names that end
  with a dot `.`, usually the dot is omitted in most cases but according to the spec a fully qualified domain name is only
  the one that ends with a dot, if it does not it is not FQDN by omission, meaning that if we use the following hostname
  in our app configuration `ent` to refer to the enterprise service, this would be seen as non FQDN, therefore according
  to the `resolv.conf` it will try to resolve the host as follows `ent.svc.cluster.local, ent.cluster.local and
ent.default.svc.cluster.local`, in that order it will try each and every one of those host names against the DNS cluster
  `nameserver`, if the DNS cluster returns a valid IP for the host we are good to go. Now what if our app configuration
  was actually providing the host as `ent.` note the `.` at the end, well in that case the search config will be ignored,
  as it will consider this a FQDN and directly try to resolve `ent.` ip address from the cluster DNS service, which will
  fail.

- `options` - directive allows you to configure additional resolver behavior. In our example, `options ndots:5` specifies
  a threshold for the number of dots (`.`) in a hostname before the resolver treats it as a fully qualified domain name
  (FQDN).

So what is the process, how and by whom is the `resolv.conf` file actually get interpreted, in order to obtain the actual
IP address to establish TCP connection to

1. **Application:** Your app calls `getaddrinfo("example.com")` internally, take `curl` as simple example
2. **Resolver Library:** The resolver library (glibc) reads `/etc/resolv.conf` to determine the nameserver and search domains.
3. **DNS Query:** The resolver library sends a DNS query to the specified nameserver.
4. **Kernel:** The kernel handles the network communication (e.g., sending TCP packets to the DNS server).
5. **Response:** The resolver library processes the DNS response and returns the IP address to your app.

### Skeleton

Below is the general skeleton of a service object, presented as a YAML manifest file. There are a few interesting
properties to take a note of which are important to understand how the service integrates with the running pods managed
by a deployment object

```yml
apiVersion: v1
kind: Service
metadata:
    name: svc-test
    labels:
        chapter: services
spec:
    type: NodePort
    ports:
        - port: 8080
          nodePort: 30001
          targetPort: 9090
          protocol: TCP
    selector:
        chapter: services
```

- `apiVersion`: At the top the API version is specified that is to be used.

- `kind`: that is the type of the object that is being defined, in this case the Service

- `metadata`: gives the Service a name, this should be a valid DNS name, so, that means alphanumeric the dot and the
  dash are valid, avoid exotic characters.

- `spec`: this section is where most of the action is, anything directly below spec relates to the Service, anything
  nested below refers to the actual behavior of the service object

  - `spec.type`: In this case it is configured as `NodePort` not a default `ClusterIP`, for the sake of this example

  - `spec.port`: this is the port on which the service listens to

  - `spec.targetPort`: this is the port on which the app inside the container listens to

  - `spec.nodePort`: this is the cluster wide port on which the service can be accessed from the outside

  - `spec.protocol`: by default, using TCP, but UDP for example is also a probable option, based on the type of app

```sh
# to just deploy the service manifest file
$ kubectl apply -f svc.yml

# to inspect the list of service
$ kubectl get svc

# to check on the details of specific service
$ kubectl get svc svc-test
NAME TYPE CLUSTER-IP EXTERNAL-IP PORT(S) AGE
hello-svc NodePort 100.70.40.2 <none> 8080:30001/TCP 8s
```

To inspect some of the created resources, alongside the service, remember that endpoints are created per service, and
they hold the information about the active / alive pods and their actual IP addresses, these will be used to route the
traffic to the pods, when a service hostname is hit

```sh
# to get the endpoint objects, which are tied to a service
$ kubectl get endpointslices
NAME ADDRESSTYPE PORTS ENDPOINTS AGE
svc-test-sbhbj IPv4 8080 10.42.1.119,10.42.0.117,10.42.1.120... 6m38s

# to get the details for the given endpoint object for the svc-test
$ kubectl describe endpointslices svc-test-sbhbj
Name: svc-test-sbhbj
Namespace: default
Labels: endpointslice.kubernetes.io/managed-by=endpointslice-controller.k8s.io
kubernetes.io/service-name=svc-test
Annotations: endpoints.kubernetes.io/last-change-trigger-time: 2021-02-05T20:01:31Z
AddressType: IPv4
Ports:
Name Port Protocol
---- ---- --------
Endpoints:
- Addresses: 10.42.1.119
    Conditions:
    Ready: true
    Hostname: <unset>
    TargetRef: Pod/svc-test-84db6ff656-wd5w7
    Topology: kubernetes.io/hostname=k3d-gsk-book-server-0
- Addresses: 10.42.0.117
    <Snip>
Events: <none>
```

Take a note at the `Endpoints:` section, which describes in detail all (output abbreviated) Pods and their IP addresses,
they also provide status information about the Pod. Similarly to how `ReplicaSet` are helper objects to the
`Deployments` the `Endpoints` are helper objects for the `Services`.

If we were to change the type of this service which in the manifest above is of type `NodePort`, to a `LoadBalancer`,
one simply needs to change the configuration slightly to `type: LoadBalancer` and remove the config for `nodePort:
30001`, the rest will be `automagically` done by the cloud provider, internally Kubernetes will interface with the cloud
provider's internal load balancer, and setup the required configurations to deploy and make the service accessible over
the load balancer's host name (which is managed and owned by the cloud provider directly). This can also be done in an
on premise location, but the load balancer setup is something that we would have to do manually, for example using
something like `MetalLB`, which has a native integration with Kubernetes.

```sh
$ kubectl get svc --watch
NAME TYPE CLUSTER-IP EXTERNAL-IP PORT(S) AGE
svc-test LoadBalancer 10.43.128.113 172.21.0.4 9000:32688/TCP 47s
```

The external IP columns shows the public address of the service assigned to by your cloud provider. On some cloud
platforms this might be a DNS name, instead of an IP, and it may take a minute to let the setup complete.

Note that it is very much the case that the cloud provider would create a separate load balancer service instance per
service, that is configured with `LoadBalancer`, this is to provide maximal isolation from the other services, which
provides better encapsulation. Each load balancer server instance will be configured to connect to the underlying
Service

### Clean up

Clean up the lab with the following command, these will delete the Deployment and Services, endpoints and
`EndpointSlices` are automatically deleted with their Service.

```sh
# make sure to delete the resources that were deployed, this will clean up all of the internal resources created by the
# kubelet on the node
$ kubeclt delete -f deploy.yml -f svc.yml -f lb.yml
```

## Ingress

Ingress is all about accessing multiple web applications through a single `LoadBalancer` service. A working knowledge of
Kubernetes Services is recommended before reading forward. We have already seen how Service objects provide a stable
networking for Pods. You also saw how to expose apps to external consumers via a `NodePort` services and `LoadBalancer`
Services. However both of these have limitations. `NodePort` only work on high port numbers - 30000 - 32767 and require
knowledge of node names or IPs `LoadBalancer` Services fix this but require a 1-to-1 mapping between an internal Service
and a cloud load balancer. This means a cluster with 25 internet facing, apps will need 25 cloud load balancers, and
cloud load balancers are not cheap. They may also be a finite resource you may be limited to how many cloud load
balancer instances you can provision.

Ingress fixes this by exposing multiple Service through a single cloud load balancer, it creates a `LoadBalancer`
Service, on port 80 or 443 and uses host based and path based routing to send traffic to the correct backend Service.

### Architecture

Ingress is a stable resource in the Kubernetes API. It went general availability in Kubernetes 1.19 after being in beta
for over 15 releases. During the 3+ years it was in alpha and beta service meshes increased in popularity and there is
some overlap in functionality, as a result if you plan to run a service mesh you may not need ingress. Ingress is
defined in the `networking.io` API sub group as a `v1` object and is based on the usual two constructs

- A controller
- An object spec

The object spec defined rules that govern traffic routing and the controller implements the rules. However a lot of
Kubernetes clusters do not ship with a built in ingress controller you have to install your own. This is the opposite of
other API resources, such as Deployments and `ReplicaSets`, which have a built in pre configured controller. However
some hosted Kubernetes clusters such as GKE have installed one. Once you have an Ingress controller you deploy Ingress
objects with rules that govern how traffic hitting the Ingress is routed

On the topic of routing, Ingress operates at a layer 7 of the OSI model, also known as the app layer. This means it has
awareness of HTTPS headers, and can inspect them and forward traffic based on the hostnames and paths, The following
table shows how hostnames and paths can route to backend `ClusterIP` Services.

| Host-based     | Path-based     | Backend K8s Service |
| -------------- | -------------- | ------------------- |
| shield.mcu.com | mcu.com/shield | svc-shield          |
| hydra.mcu.com  | mcu.com/hydra  | svc-hydra           |

This shows how two different hostnames, configured to hit the same load balancer, an ingress object is watching and uses
the hostnames in the HTTPS headers to route traffic to the appropriate backend service. This is an example of the HTTP
host based routing pattern, and it is almost identical for path based routing

`For this to wrok name resolution needs to point to the appropriate DNS names to the public endpoint of the Ingress load balancer`

A quick side node, The OSI model is the reference model for modern networking, it comprises seven layers, numbered from
1-7, with the lowest layer concerned with things like signaling and electronics - hardware, the middle layers dealing
with reliability through things like acks and retries and the higher layers adding awareness of user apps such as HTTPS
services, Ingress operates at a layer 7, also known as the app layer and implements HTTP intelligence

`Ingress exposes multiple CLUSTERIP service through a single cloud load balancer, you create and deploy ingress objects,
which are rules governing how traffic reaching the load balancer is routed to the backend services, the ingress
controller which you usually have to install yourself uses hostnames and paths to make intelligent routing decisions.`

## Getting Kubernetes

This section will describe a few fast and quick ways to obtain Kubernetes. Will also introduce you to `kubectl`, the
Kubernetes command line tool.

### Kubernetes playground

Playgrounds are the quickest and easiest way to get Kubernetes but they do not function for production, Popular examples
include `Play with Kubernetes, Katakoda, Docker Desktop, minikube, k3d and more`

### Hosted Kubernetes

All of the major cloud platforms offer a hosted Kubernetes service. This is a model where you outsource a bunch of
Kubernetes infrastructure to your cloud provider, letting them take care of things like high availability, performance
and updates.

Of course not all hosted Kubernetes solutions are equal and even though your cloud provider is managing a lot of the
infrastructure for you, the ultimate responsibility remains with you.

### DIY Kubernetes

By far the hardest way to get a Kubernetes cluster is to build it yourself. Yes, installations such as these are
possible are a lot easier now than they used to be, but they can still be hard. However they provide most flexibility
and give you ultimate control - which can be good for learning

