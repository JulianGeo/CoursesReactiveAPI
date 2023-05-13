<div align="center">
  
  # Courses Reactive consumer API
  
</div>


<div align="center">
  <img src="https://1.bp.blogspot.com/-hvNwNiYWxeM/YS85SsLGxtI/AAAAAAAAL5U/1GdXZUHZ9PgtQWZZZ1jWRbPRuF-lE9LxACLcBGAsYHQ/s215/reactive-spring-boot.png" alt="ReactiveSpringboot" />
</div>

This repository contains a CRUD Java Spring Boot WebFlux application for managing courses entities with the reactive paradigm. 
The application allows students to enroll in courses. The student entities are managed by another application and the communication between both APIs is done with a message broker, 
the courses API can be found in the [StudentsReactiveAPI-RabbitMQ-ConsumerAPI](https://github.com/JulianGeo/StudentsReactiveAPI-RabbitMQ-PublisherAPI) repository.

<div align="center">
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/7/71/RabbitMQ_logo.svg/2560px-RabbitMQ_logo.svg.png" alt="RabbitMQ" />
</div>

## Features

- CRUD operations for student entities
- Enrollment of students in courses
- Reactive programming paradigm using WebFlux
- Message broker (RabbitMQ) for communication between APIs
- Documentation with Swagger (Swagger branch)
- Entity validation using Jakarta Validation
- Unit testing with JUnit and Mockito

## Prerequisites

- Java Development Kit (JDK) 17
- Apache Maven
- RabbitMQ
