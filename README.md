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
5. **Scope 2**:
   - Install [MySQL WorkBench](https://dev.mysql.com/downloads/installer/) (for Relational Database)
   - (mysql-installer-community-8.0.40.0.msi)


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
