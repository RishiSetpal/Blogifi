# Blogifi

## Blogifi: Single Simple Backend Solution for All Your Posts
Blogifi is a lightweight backend solution designed to manage posts effectively. Built using Spring Boot and Maven, this project simplifies backend operations with support for all primary HTTP methods.

---

## Project Setup

This project is built using:
- **Spring Initializer**: To quickly set up a Spring Boot application.
- **Maven Project**: Handles dependencies in `pom.xml`. Alternatively, Gradle can be used for faster builds.

---


Use Spring Initializer to create a simple Maven Project [Spring Boot Initializer](https://start.spring.io/)
- **Maven Project** (has pom.xml - which keeps all the dependencies in xml format)
- Whereas **Gradle project** is the Fast Build tool and new  

## Dependencies

This project uses the following dependencies (defined in `pom.xml`):
- **Spring Web**: For building RESTful APIs.
- **Spring Boot DevTools**: For hot reloading during development.
- **JUnit**: For testing.

---

## Future Scope

- Integrating database support (MySQL, PostgreSQL, etc.) for persistent storage.
- Implementing user authentication and authorization.
- Adding unit and integration tests for better code coverage.

## Learning Features Scope

**Part1 Branch**
-
1. **Scope 1**:
    - Storing data in local memory.
    - Understanding Spring, SpringBoot, Annotations, RealLifeExamples for REST & SOAP API
        - REST(Light Weight | for Faster Communications over the Network Call) 
        - SOAP(in banks | XML format | More Secure in Network)
    - Understanding Folder Structure
    - @SpringBootApplication
    - Understanding Concept of IOC(Spring Container) @Controller, @Service, @Component, @Bean  
    - Annotations @RestController, @RequestMapping("/posts"), 
2. **Scope 1 Part 2**:
    - Controller: Adding All Other https methods get, put, post, get(with id), patch, delete
    - Basic CRUD operations (Create, Read, Update, Delete).
    - @RequestMapping, @
3. **Scope 1 Part 3**:
    - Full implementation of HTTP methods: GET, POST, PUT, PATCH, DELETE.
    - Understanding of
4. **Scope 1 Part 3**:
   - Install [Postman](https://www.postman.com/downloads/) (for API calling)
   - Postman [ ( Invite to View | Can Request to View ) ](https://galactic-desert-826071.postman.co/workspace/BlogifiApplication~c3a0d903-336d-462a-8184-36c6d875aea3/collection/20977820-0be79233-ed90-4356-8dd2-43a6a66c32de?action=share&creator=20977820&active-environment=20977820-75a8665f-867b-449c-8fd3-a62ccf865bb7)
     - Postman Reference Link Part 1
     - Exported as Collection v2.1 in .json(default)
     - Add a Folder named: src> docs/postman/0Posts Controller Collection.postman_collection.json
     - Updated Readme File.

**Part2 Branch**
-

1. **Scope 2**:
   - Install [MySQL WorkBench](https://dev.mysql.com/downloads/installer/) (for Relational Database)
   - (mysql-installer-community-8.0.40.0.msi)
2. **Lombok**:
   - Add Lombok,
     Add Lombok dependency in pom.xml 
     installed Lombok
     used @Data for Getters and Setters
     used @NoArgsConstructor & @AllArgsConstructor
3. **ResponseEntity** 
   - Add return type ResponseEntity<type> with HttpStatus.OK/Created in Controller
4. **Updated Postman**
   - Exported postman 
   - import postman
   - use .postman file Kept inside the Repo

**Part3 Branch**
-

1. **MySQL, Stream, Many-to-Many**
   - [Setup MySQL & Setup MongoDb](https://chatgpt.com/share/67888fce-7444-8008-893f-a668dea8fb4a)
   - MySQL setup in pom.xml and in application.properties **mysql-connector-j**
   - Updated MySQLQuerie.sql
   - Used **@Entity** form jakarta from **spring-boot-Starter-data-JPA** Dependency
   - **@Entity** name will create same in Database @Id [PK] @GenerateValue(strategy = GenerationType.IDENTITY)
   - Add - **JPA** Repository Package > **PostRepository.java Interface extends JpaRepository** with its DataType in TableName, PK type<Posts, Integer>
   - Add - **RequestDto and ResponseDto** 
     - **PostRequestDto** (Sending in Format Type in Which Client Sends Json )
       - We can Create convertToPost() Method in services  
     - Saves in **Post** Entity (in a Format DB can Store)
       - We can Create convertToPostResponseDto() Method in services
     - Sends back Response in **ResponseDto** (Sending in Format Type in Which Client Receives Json )

**Part 4 Branch**
-

    1. Added Tags in MySQL DB
    2. (@ManyToMany(cascade ={CascadeType.ALL}), @JoinTable(name="p_t",jointColumn = {@JointColumn(name="post_id"), inverseJointColumn = {@JointColumn(name="tag_id")})) Set<Tag> tags = new HashSet<>();
    3. changed PostRequestDto and ResponseRequestDto
    4. While Create Post, check tag if Exists then don't add
    5. While Getting RequestDto -> alter Data Using Stream().map(()->Set<Tag> new Tag()) and then -> store in Post Entity Repository DB -> Return Stream.Map again back to Set of Strings
    6. Updated Postman 
    7. Updated MySQL Queries
   

## Folder Structure

This project follows the standard IntelliJ Maven Spring Boot project structure:

<details>
<summary>Click to view detailed structure</summary>

```
Blogifi
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.blogifi
│   │   │       ├── BlogifiApplication.java      # Main application entry point
│   │   │       ├── controller
│   │   │       │   └── PostController.java      # Handles HTTP requests for posts
│   │   │       ├── model 
│   │   │       │   └── Post.java                # Data model representing a post
│   │   │       └── service
│   │   │           └── PostService.java         # Business logic for managing posts
│   │   └── resources
│   │       ├── application.properties               # Spring Boot application configuration file
│   │       └── static                               # Static assets (if needed)
│   └── test
│       └── java
│           └── com.example.blogifi
│               └── BlogifiApplicationTests.java # Unit and integration tests
│
├── pom.xml                                      # Maven configuration and dependencies
├── README.md                                    # Project documentation
└── .gitignore                                   # Git ignore rules
```
</details>

---

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/RishiSetpal/Blogifi
   cd blogifi
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the API:
    - Base URL: `http://localhost:8080`
    - Example endpoints:
        - `GET /posts` - Retrieve all posts
        - `POST /posts` - Create a new post
        - `GET /posts/{id}` - Retrieve posts by ID
        - `PUT /posts/{id}` - Update a post by ID
        - `PATCH /posts/{id}` - Partially update a post by ID
        - `DELETE /posts/{id}` - Delete a post by ID

---

## Dependencies

This project uses the following dependencies (defined in `pom.xml`):
- **Spring Web**: For building RESTful APIs.
- **JUnit**: For testing.
- **Spring Boot DevTools**: For hot reloading during development.
- **Lombok**: Which Automatically adds Constructors, Getters & Setters, etc in just some annotations @ALLConstructors etc

---

## Future Scope

- Integrating database support (MySQL, PostgreSQL, etc.) for persistent storage.
- Implementing user authentication and authorization.
- Adding unit and integration tests for better code coverage.

## Steps

---
# Spring Boot Learning Journey

## Day 1: Introduction
- Spring and Spring Boot
- Use Cases
- Soap API and REST API overview

## Day 2: Spring Basics
- Spring Initializer
- Inversion of Control (IoC): Automatic creation of objects

## Day 3: Core Concepts
- Annotation Implementation
- Introduction to Monolithic Environment (Single-Service)
- Architecture Flow:
   - **Entity** > **Controller** > **Service**
- API Implementation (1)
- Saving Data:
   - Local Storage / In-Memory Storage
   - Introduction to Level 2 (L2) Storage

## Day 4: REST API Basics
- API Implementation (2)
- Using Postman for API Testing
- REST API and HTTP Request Types:
   - `GET`, `PUT`, `POST`, `PATCH`, `DELETE`

## Day 5: Advanced API Features
- Implementation of All APIs (including Path Parameters)
- (Added support for Query Parameters)

## Day 6: Lombok
- Simplifying Code with Lombok:
   - Automatic creation of `@Data` (Getters/Setters)
   - `@NoArgsConstructor` (No-Args Constructor) and others

## Day 7: Database Flow
- Understanding the flow of MySQL:
   - Traverses from **Controller** > **Service** > **JPA Methods**
   - JPA (Java Persistence API): Combining JDBC and ORM (Hibernate)
   - Database Interaction

## Day 8: Database Integration
- Connecting to the Database:
   - Creating Databases using `@Entity` (Creates Tables)
   - Using JPA API Libraries
   - Connected via `Repository/PostRepository.java`

## Day 9: Relationships in MySQL
- **One-to-Many** Relationship: Introduction
- **Many-to-Many** Relationship: Introduction and Example
