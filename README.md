## Messaging Service API
This API is a Spring Boot application with MongoDB backend that uses Websocket server for 2-way communications and allows users to connect to the backend using STOMP client. This application is developed with [Level 2 of the Richardson Maturity Model](https://martinfowler.com/articles/richardsonMaturityModel.html) in mind.


## Architecture
The Architecture shows the high level visual display of how each component work together, but this project only contains the Spring boot REST API how how it connects to the MongoDB database backend. The frontend is not implemented, but could be implemented with React.js, Angular or VUE.js frontend or any other frontend frameworks whenever needed.

![Architecture of the project](https://persistentminds.com/wp-content/uploads/2021/04/Messaging-Service.png)

## Scope of the Project
The following contains the list of the functionalities of the application:
* You can create user, get user and get user by username in the [User-Service project](https://github.com/ademolakazeem/user-service.git) on this repository.
* The application allows users to send a message to one other user.
* When a message is sent, it is marked as RECEIVED, and marked as DELIVERED once the recipient views the the message.
* The application allows editing and deleting messages.
* The application is able to get all the messages sent between two users.
* The application allows a user to "like" a message.
* The application  is able to get a list of other users that have sent or received messages to/from a specified user.
* It allows users to find messages by senders
* It allows users to find messages by recipients
# Other Functionalities
* The application counts the number of new messages
* It allows users to delete entire chats between 2 users at once, etc.

## Out of Scope
* Because of time constraints, this project have small number of test cases to test save and find all for both `MessageService` and `MessageController`
* The whole essence of this test is to show that this could be implemented
* The [User-Service project](https://github.com/ademolakazeem/user-service.git) does not have test too, but could be implemented if required.

## Requirements  (Prerequisites)
Before you can run this application, you need to first install the following:
* Maven
* MongoDB database
* [User-Service project](https://github.com/ademolakazeem/user-service.git) which is the project to save and get user information for this project

## Deployment
Here are steps taken to be able to run this project successfully:

This application is docker ready for your deployment, here are the steps involved in order to run it on docker:

* Clone or Download this project
* Run `$ mvn clean install` in order to build the application
* At the root of the application, run `$ docker network create messaging-network`
* Next build the containers with the following command: `$ docker-compose --env-file “<path/to/your/project>/messaging-service/docker-setup/.env" build`
* Where <path/to/your/project> is the path to the project. e.g. `$ docker-compose --env-file "/Users/ademola/developments/messaging-service/docker-setup/.env" build`
* Now run the following commands `$ docker-compose --env-file "<path/to/your/project>/messaging-service/docker-setup/.env" up -d`
* Now, in order to run your application, go to [http://localhost:8881/swagger-ui.html](http://localhost:8881/swagger-ui.html) 
* If you want to see your database, login into to your mongodb Compass with the following login `mongodb://user1:user1@localhost:26000/messaging-service?authSource=MessagingDB&readPreference=primary&appname=MongoDB%20Compass&ssl=false`


Or

* Clone or Download this project
* Start your MongoDB database
* Open the Application.properties in this project and create the database using the name in the Application.properties (MessagingDB)
* Make sure that the port address is the same with one in the properties file (27017)
* Run `$ mvn clean install` in order to build the project
* Next, Run `$ mvn spring-boot:run` to run the Spring Boot application
* If everything goes as planned, your application should be able to open in port: 8881
* Please do not forget to add swagger-ui.html in front of the localhost: e.g. http://localhost:8881/swagger-ui.html

## Video Demo of the application
Please click on the image below in order to view a short Video Demo of the application.

[![Watch the video](https://persistentminds.com/wp-content/uploads/2021/04/Messaging-Service.png)](https://youtu.be/RSD8-FDedQo)

