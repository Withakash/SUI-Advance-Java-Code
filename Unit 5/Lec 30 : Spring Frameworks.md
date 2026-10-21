# Lecture 30: Spring Framework
## Introduction, IoC, Dependency Injection, Spring MVC and Spring Security

**Subject:** Java Enterprise Edition (JEE)  
**Lecture Duration:** 50 Minutes

## Learning Objectives

After completing this lecture, students will be able to:

- Understand the Spring Framework and its importance in Java development.
- Explain the Spring Framework architecture using a diagram.
- Understand Inversion of Control (IoC) and Dependency Injection (DI).
- Explain the working of Spring MVC.
- Understand authentication and authorization using Spring Security.

---

# 1. Spring Framework (8 Minutes)

## 1.1 What is the Spring Framework?

The **Spring Framework** is an open-source Java framework used to develop enterprise-level applications.

It provides infrastructure and ready-made components that help developers build applications with less boilerplate code and better modularity.

In traditional Java applications, developers often create objects manually, manage their dependencies, and write repetitive infrastructure code.

Spring simplifies these tasks through features such as:

- **IoC (Inversion of Control):** Spring manages object creation and their lifecycle.
- **Dependency Injection (DI):** Spring provides the dependencies required by an object.
- **Spring MVC:** Supports web application development using the Model-View-Controller pattern.
- **Spring Security:** Provides authentication and authorization features.
- **Data Access Support:** Simplifies database interaction and transaction management.
- **Integration Support:** Helps integrate different technologies and frameworks.

### Example: Without Spring vs With Spring

**Without Spring:**

```java
class Engine {
    void start() {
        System.out.println("Engine started");
    }
}

class Car {
    Engine engine = new Engine();
}
```

Here, the `Car` class creates its own `Engine` object. This creates tight coupling between the classes.

**With Spring:**

```java
@Component
class Engine {
    void start() {
        System.out.println("Engine started");
    }
}

@Component
class Car {
    private final Engine engine;

    @Autowired
    Car(Engine engine) {
        this.engine = engine;
    }
}
```

Spring can create the `Engine` and `Car` objects and inject the engine into the car. In a real application, component scanning and configuration must be enabled.

## 1.2 Why Do We Need Spring?

| Problem in traditional development | Spring solution |
|---|---|
| Objects are created and managed manually | IoC container manages objects |
| Classes are tightly coupled | Dependency Injection reduces coupling |
| Repetitive infrastructure code | Reusable framework services |
| Web request handling requires manual setup | Spring MVC provides structured request handling |
| Security is difficult to implement consistently | Spring Security provides security mechanisms |
| Transaction management is repetitive | Declarative transaction management |

**Important:** Spring does not replace Java. It provides tools and abstractions that make Java application development easier to maintain and extend.

---

# 2. Introduction to Spring Framework (12 Minutes)

## 2.1 Main Features of Spring

1. **Lightweight:** Supports modular development, allowing applications to use only the required features.
2. **Loosely Coupled:** Classes depend on abstractions and injected dependencies rather than creating everything themselves.
3. **Modular:** Provides separate modules for core functionality, web applications, data access, security, and more.
4. **Testable:** Dependency Injection makes it easier to replace real dependencies with test implementations.
5. **Transaction Management:** Supports declarative transaction handling.
6. **Integration:** Works with technologies such as JDBC, JPA, Hibernate, and servlet-based web applications.

## 2.2 Spring Framework Architecture

**Important university question: Explain the Spring Framework architecture with a neat diagram.**

The Spring Framework has multiple modules grouped into logical layers. These modules work together to support enterprise application development.

### Architecture Diagram

```text
             SPRING FRAMEWORK ARCHITECTURE
             ==============================

        ┌───────────────────────────────┐
        │          Web Layer            │
        │ Spring MVC | Web | WebSocket  │
        └──────────────┬────────────────┘
                       │
        ┌──────────────▼────────────────┐
        │     Data Access & Integration │
        │ JDBC | ORM | Transactions     │
        └──────────────┬────────────────┘
                       │
        ┌──────────────▼────────────────┐
        │       AOP and Aspects         │
        │ Logging | Cross-cutting logic │
        └──────────────┬────────────────┘
                       │
        ┌──────────────▼────────────────┐
        │       Core Container          │
        │ Core | Beans | Context | EL   │
        └───────────────────────────────┘

       Spring Security integrates with the
       application and web security layers.
```

*Note: This is a simplified conceptual diagram, not a strict runtime execution sequence. Spring Security is a separate project in the Spring ecosystem, rather than a core Spring Framework module.*

## 2.3 Explanation of Spring Architecture Modules

### A. Core Container

The Core Container is the foundation of Spring.

It includes:

- **Core:** Provides fundamental framework functionality.
- **Beans:** Supports object creation, configuration, and management.
- **Context:** Provides access to application components through the IoC container.
- **Expression Language (SpEL):** Supports expressions for accessing and evaluating object properties and values.

The container manages objects called **Spring Beans**.

### B. AOP (Aspect-Oriented Programming)

AOP helps separate cross-cutting concerns from the main business logic.

Examples:

- Logging
- Security checks
- Transaction boundaries
- Performance monitoring

For example, a logging aspect can record method execution without requiring the same logging code in every business class.

### C. Data Access and Integration

This layer simplifies database and enterprise integration.

Important components include:

- **JDBC:** Simplifies JDBC-based database operations.
- **ORM:** Integrates with ORM technologies such as Hibernate.
- **Transactions:** Supports transaction management.
- **Integration support:** Connects Spring applications with other technologies.

### D. Web Layer

The Web layer supports web-based application development.

It includes:

- **Spring MVC:** Implements the Model-View-Controller pattern for servlet-based web applications.
- **Spring Web:** Provides common web infrastructure.
- **WebSocket support:** Supports two-way communication between clients and servers.

### E. Spring Security

Spring Security is used to secure applications by implementing authentication, authorization, and other security controls.

It integrates with Spring applications and is commonly used with Spring MVC.

## 2.4 Advantages of Spring Framework

- Reduces boilerplate code.
- Promotes loose coupling.
- Improves testability and maintainability.
- Supports modular development.
- Integrates with many Java technologies.
- Provides web, transaction, and security support.

## 2.5 Spring Framework vs Spring Boot

| Spring Framework | Spring Boot |
|---|---|
| Provides core application development features | Simplifies the setup and configuration of Spring applications |
| Often requires more explicit configuration | Provides auto-configuration and starter dependencies |
| Does not itself require one particular deployment model | Commonly supports standalone applications with embedded servers |
| Can be used independently of Spring Boot | Built on top of the Spring ecosystem |

**Remember:** Spring Boot is not a replacement for Spring. It makes Spring application development and deployment easier.

---

# 3. Inversion of Control (IoC) and Dependency Injection (DI) (10 Minutes)

## 3.1 What is Inversion of Control?

**Inversion of Control (IoC)** is a design principle in which control over object creation and management is transferred from application code to a framework or container.

In a traditional Java program, a class may create the objects it needs using the `new` keyword.

With Spring, the IoC container can create and manage those objects and provide them to the classes that require them.

### IoC Diagram

```text
Traditional Java
================

       Car
        |
        | creates object using new
        ▼
      Engine


Spring IoC
==========

     Spring IoC Container
              |
       creates/manages
              |
        ┌─────┴─────┐
        ▼           ▼
       Car        Engine
        |
        └── receives Engine
            through DI
```

The diagram illustrates that the container manages the objects and supplies dependencies. It does not mean that every dependency must be created as a separate component; the configuration determines which objects Spring manages.

## 3.2 What is Dependency Injection?

**Dependency Injection (DI)** is a technique in which an object receives the other objects it depends on instead of creating those objects itself.

For example, a `Car` needs an `Engine` to function. The engine is a dependency of the car.

Spring can inject this dependency through a constructor, a setter, or a field.

### Types of Dependency Injection

1. **Constructor Injection:** Dependencies are passed through a constructor. This is generally preferred for required dependencies.
2. **Setter Injection:** Dependencies are provided through setter methods. This is useful for optional dependencies.
3. **Field Injection:** Dependencies are injected directly into fields, often using annotations. It is less suitable for straightforward unit testing and immutable design.

### Example: Constructor Injection

```java
import org.springframework.stereotype.Component;

@Component
class Engine {
    void start() {
        System.out.println("Engine started");
    }
}

@Component
class Car {
    private final Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }

    void drive() {
        engine.start();
        System.out.println("Car is moving");
    }
}
```

In this example:

- `Engine` is a Spring component.
- `Car` requires an `Engine`.
- The constructor declares the dependency.
- Spring can inject the managed `Engine` object when component scanning is configured.

### IoC vs DI

| IoC | DI |
|---|---|
| A broad design principle | A technique for implementing IoC |
| Transfers control of object management to a container | Supplies an object's dependencies |
| Describes who manages the objects | Describes how dependencies are supplied |

**Important:** IoC and DI are related but are not identical concepts.

## 3.3 What Is the Spring IoC Container?

The Spring IoC container creates, configures, and manages Spring Beans according to the application's configuration.

Two important container interfaces are:

- **BeanFactory:** Provides basic bean management functionality.
- **ApplicationContext:** Extends BeanFactory and provides additional enterprise application features, such as event publication and message resolution.

In most Spring applications, developers use an `ApplicationContext`.

## 3.4 Benefits of IoC and DI

- Reduces tight coupling.
- Improves code reusability.
- Makes unit testing easier.
- Simplifies object lifecycle management.
- Makes it easier to replace one implementation with another.

---

# 4. Spring MVC (10 Minutes)

## 4.1 What is Spring MVC?

**Spring MVC** is a web framework in Spring based on the **Model-View-Controller (MVC)** design pattern.

It separates an application's data, user interface, and request-handling logic.

### Components of MVC

- **Model:** Represents application data and business information.
- **View:** Displays information to the user, such as an HTML page.
- **Controller:** Receives requests, invokes application logic, and determines the response.

## 4.2 Spring MVC Architecture Diagram

```text
           Client / Browser
                  |
                  | HTTP Request
                  ▼
       ┌──────────────────────┐
       │ DispatcherServlet    │
       │ Front Controller     │
       └──────────┬───────────┘
                  |
                  ▼
       ┌──────────────────────┐
       │ HandlerMapping       │
       │ Finds suitable       │
       │ controller           │
       └──────────┬───────────┘
                  |
                  ▼
       ┌──────────────────────┐
       │ Controller           │
       └──────────┬───────────┘
                  |
                  ▼
       ┌──────────────────────┐
       │ Service / Model      │
       │ Business logic/data  │
       └──────────┬───────────┘
                  |
                  ▼
       ┌──────────────────────┐
       │ ViewResolver         │
       │ Selects the view     │
       └──────────┬───────────┘
                  |
                  ▼
              View / HTML
                  |
                  ▼
           Client / Browser
```

*Note: The diagram shows a common view-rendering flow. REST controllers can instead return data, such as JSON, without resolving a traditional HTML view.*

## 4.3 Working of Spring MVC

1. The browser sends an HTTP request to the application.
2. `DispatcherServlet` receives the request.
3. `HandlerMapping` helps identify the appropriate controller.
4. The controller processes the request and invokes the required business logic.
5. The controller provides a model and view name, or returns a response body.
6. For traditional views, `ViewResolver` identifies the view.
7. The server sends the generated response to the client.

## 4.4 Simple Spring MVC Controller Example

```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class HomeController {

    @GetMapping("/home")
    @ResponseBody
    public String home() {
        return "Welcome to Spring MVC!";
    }
}
```

When a client requests `/home`, the controller returns the text `Welcome to Spring MVC!` as the HTTP response body.

Here:

- `@Controller` identifies the class as an MVC controller.
- `@GetMapping("/home")` maps HTTP GET requests to the method.
- `@ResponseBody` tells Spring to write the return value directly into the response body rather than resolving a view.

This example assumes a Spring MVC application has already been configured.

## 4.5 Advantages of Spring MVC

- Separates application responsibilities.
- Supports flexible request mapping.
- Simplifies request and response handling.
- Supports form processing and validation.
- Supports REST API development.
- Integrates with Spring's DI and security features.

---

# 5. Spring Security (10 Minutes)

## 5.1 What is Spring Security?

**Spring Security** is a security framework for Java applications that helps implement authentication, authorization, and protection against common web security threats.

It integrates with Spring applications to control who can access an application and what they can do.

## 5.2 Important Security Concepts

### A. Authentication

Authentication answers the question:

**Who are you?**

It verifies a user's identity using credentials such as a username and password, or other configured authentication methods.

Example: A student logs in with a valid username and password.

### B. Authorization

Authorization answers the question:

**What are you allowed to do?**

It determines which resources or operations an authenticated user can access.

Example:

- A student can view course materials.
- A teacher can manage assignments.
- An administrator can manage user accounts.

### Authentication vs Authorization

| Authentication | Authorization |
|---|---|
| Verifies identity | Checks permissions |
| Happens during identity verification | Determines access to protected resources |
| Example: Verify login credentials | Example: Check whether the user can open an admin page |

## 5.3 Spring Security Working Diagram

```text
          User / Browser
                 |
                 ▼
          Login Request
                 |
                 ▼
       ┌───────────────────┐
       │ Security Filter   │
       │ Chain             │
       └─────────┬─────────┘
                 |
                 ▼
       ┌───────────────────┐
       │ Authentication    │
       │ Verify identity   │
       └─────────┬─────────┘
                 |
          ┌──────┴──────┐
          │             │
          ▼             ▼
       Invalid        Valid
          |             |
          ▼             ▼
       Reject       Authorization
       request      / Permission
                         |
                  ┌──────┴──────┐
                  ▼             ▼
                Denied        Allowed
                  |             |
                  ▼             ▼
              Error /       Protected
              403           Resource
```

*Note: Actual behavior depends on the configured authentication mechanism, authorization rules, and exception handling.*

## 5.4 Important Features of Spring Security

- **Authentication:** Verifies user identity.
- **Authorization:** Controls access to resources.
- **Password encoding:** Supports secure password storage using appropriate password encoders.
- **Request protection:** Supports protections such as CSRF defense.
- **Session management:** Supports session-related security controls.
- **Method security:** Can restrict access to specific methods based on permissions.

## 5.5 Example: Role-Based Access

Suppose an online university portal has three roles:

| Role | Permitted action |
|---|---|
| Student | View course materials |
| Teacher | Manage assignments |
| Admin | Manage users and system settings |

Spring Security can enforce these rules so that a student cannot access an administrator-only operation merely by entering its URL.

**Important:** Hiding a button in the user interface is not sufficient security. Access must also be checked on the server.

---

# 6. Quick Revision

| Concept | Meaning |
|---|---|
| Spring Framework | Framework for developing Java applications |
| IoC | Transfers object management control to a container |
| DI | Supplies dependencies to an object |
| Spring Bean | An object managed by the Spring container |
| ApplicationContext | A commonly used Spring IoC container interface |
| AOP | Separates cross-cutting concerns |
| Spring MVC | Web framework based on MVC |
| DispatcherServlet | Front controller in servlet-based Spring MVC |
| Spring Security | Framework for authentication and authorization |
| Authentication | Verifies identity |
| Authorization | Checks permissions |
| Spring Boot | Simplifies Spring application configuration and setup |

---

# 7. Important 10-Mark University Questions

### Question 1 — Most Important

**Explain the Spring Framework architecture with a neat diagram. Describe the functions of its major modules.**

Suggested answer structure:

1. Definition and introduction of Spring Framework.
2. Neat architecture diagram.
3. Core Container.
4. AOP and Aspects.
5. Data Access and Integration.
6. Web Layer.
7. Role of Spring Security in application security.
8. Advantages and conclusion.

### Question 2

Explain Inversion of Control (IoC) and Dependency Injection (DI). Differentiate between them with a suitable example.

### Question 3

Explain Spring MVC architecture with a neat diagram. Describe the role of `DispatcherServlet`, `HandlerMapping`, Controller, and `ViewResolver`.

### Question 4

What is Spring Security? Explain authentication and authorization with suitable examples.

### Question 5

Explain the important features and advantages of the Spring Framework. Differentiate between Spring Framework and Spring Boot.

---

# 8. Conclusion

The Spring Framework simplifies enterprise Java development by providing dependency management, modular architecture, web application support, data access integration, and transaction management. IoC and DI reduce tight coupling, Spring MVC organizes web request handling, and Spring Security helps protect application resources.

**Exam tip:** For a 10-mark question on Spring Framework architecture, draw the diagram first, explain every major module, and finish with advantages and practical applications.
