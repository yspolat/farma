# The Project Farma

![](https://github.com/yspolat/it529-farma/blob/master/media/farma_logo.png?raw=true)

### Overview
Farma is an online pharmacy platform and the customers can easily buy their medicines on this web app with generated code by doctor. Local pharmacies can join this platform and offer their medicines to the customers. Medicines are easily delivered to customers location by courier.

### Features

- Farma is offering an user-friendly web application, even elderly people can use this application to order their medicines without asking help from someone else.

- Our system design is not complex, we choose a solid database like PostgreSQL and keep as simple as the table design and relations. Spring Boot is a viable option for back-end and Bootstrap is viable for front-end.

- The infrastructure and services that we use to set up the system will support the keep the application live and cloud is most suitable option for our project. Therefore, we choose to work with Amazon Web Services (AWS) and EC2 offers scalability, reliability, cost effectiveness and agility.

- Best practices were followed and the design is simple and lean in every aspect of UI, system and DB.

- In our application, it is possible to detect issues with monitoring the logs and debugging it if bugs, issues and problems occur whether it's from database problem such as index, tablespaces, also problem with web service integration. All can be tracked easily in the logs.

- Compatible with all major browsers (IE10+, Edge, Chrome, Firefox ) and devices(Android and iOS) thanks to Bootstrap.


**Table of Contents**

[TOCM]

[TOC]

#Use Case

**Customer**: Presentation layer is designed for the customer. Customers are literally end-user who want to order the medicines.

**Pharmacy**:  The pharmacy like a supplier maintains inventory for providing medicines to the customer. Pharmacists are actual user for Marketplace layer, they can add/edit/delete medicines and view orders from customer which has also preparation of orders, delivery to the courier.

**Administrator**: Master role and can control pharmacies and also pharmacists in Administrative layer.

#Design

For technical point of view and platform decision, we did very deep research to determine proper technologies. Agile model and Aspect Oriented Programming are used and they could cut concerns like logging and security and enable cohesive development. It is a model is an iterative way to deal with arranging and directing project process. The reasons for using the Agile methodology are to complete our tasks as the segments are completed in the project. This continuous release program enabled us to show that our tasks are successful and if not, we need to accelerate, and if there is an error situation in the tasks, we focus on the this. Actually, we are not solving a problem, but we are trying to create a platform like Delivery Hero. Anyway, Agile helped reduce the possibility of large-scale failure, because there is continuous improvement throughout the project life cycle. This model encourages to start to project requirements, analysis, design, coding, testing and maintenance.

![](https://github.com/yspolat/it529-farma/blob/master/media/conceptual_design.png?raw=true)

We tried to follow best practices and wanted to keep our design simple and lean in every aspect of UI, system and database. Furhermore, we would like to use fancy technologies like Bootstrap, Spring Boot and Vue.js

+ **Back-end**
	+ Spring Boot is a very good decision for us, and glad to use it in our project, since provide comprehensive infrastructure support for developing Java applications.
	+ Spring Session and Authentication are very useful for the project for session, authorization and authentication contexts.
	+ To provide data to front-end including Thymeleaf and Vue.js, it contains service and repository class. Hibernate–JPA made our lives easier. Without touching DB, we created all relations, tables and fields.
    + Some parts are developed by Rest API such as Dummy API for Ministry of Health, pharmacist and pharmacy entities, rest can be turn to Rest API to be used for mobile app in the future.

+ ** Front-end**
	+	Mostly Bootstrap, partially Vue.js

+  **Server** 
	+ AWS as cloud provider is to host our project with using EC2 and RDS. There are two environments in the project, like in the industry production and development environment are used in our project's SDLC. We created AWS RDS instance which is PostgreSQL DB and has limited resources, because we could use only free-tier solutions. Furthermore, we created AWS EC2 - free tier and Operation system is Red Hat Enterprise Linux 8 and instance type is t2.micro (*Variable ECUs, 1 vCPUs, 2.5 GHz, Intel Xeon Family, 1 GiB memory, EBS only*). This server has application server (Wildfly or Apache Tomcat) and serves the application.

+ **Build Automation Tool**
	+ Apache Maven

+ ** Version Control**
	+ Git is also installed and configure, and our project deliverables went to GitHub private repository frequently.

+ **Relational Database**
	+ Amazon RDS - PostgreSQL

+ **ORM** 
	+ Hibernate

+ **Web Service**
	+ Rest API

+ **Testing Tools**
	+ Swagger, JUnit and Mockito used in testing. Mockito is a framework for unit tests, can be used with JUnit and allowed us to create and configure mock objects. These are included in Spring Boot for testing.

+ **Application Server **
	+ Wildfly


#Database
Following is ER diagram on our DB (PostgreSQL), and there are 13 entities in our schema (Farm) and there are relations Many to Many and One to Many and we aimed to keep design simple and understandable. Design can be scale according to new requirements. PostgreSQL is our decision for Relational Database. REST API and Spring JPA, Data currently consume Database for CRUD operations.

*ER Diagram* 
![](https://github.com/yspolat/it529-farma/blob/master/media/er_diagram.png?raw=true)

#Development
There are two modules in main project. With our IDE - Intellij IDEA, those modules separated from each other. Our goal is to have a clear project structure. Both modules have Maven pom.xml and main folder also has own pom.xml as well.

We set groupId, artifactId, version, name and description of project and choose necessary dependencies.

Here is the dependencies that are used in Spring Boot;

+ Lombok
    + Reducing boilerplate code
+ Rest Repositories
    + To provide web service in JSON based
+ Spring Session
    * Authentication in security context for user sessions
+ Thymeleaf
    * It's a moderate Java template engine. Boostrap is integrated into Thymeleaf.
+ Spring Security (default)
    * For authorization and authentication
+ Spring Data JPA
    * For Hibernate
+ PostgreSQL Driver
    * To access PostgreSQL and create connection to DB
+ AWS Core
    * It's mandatory to reach AWS RDS
+ AWS RDS
    * It's Relational Database instance dependency

#Maintenance

We are willing to follow Continuous Integration / Continuous Development best practices so on. Our plan is to help development team in building and testing software continuously and automate building, deployment and testing. Therefore, Jenkins is chosen one. In fact, we installed Jenkins on our AWS EC2 server, but configuration is not completed yet.

#Screen-shots

**MARKETPLACE**

![](https://github.com/yspolat/it529-farma/blob/master/media/marketplace.png?raw=true)

**PRESENTATION**

![](https://github.com/yspolat/it529-farma/blob/master/media/presentation.png?raw=true)
