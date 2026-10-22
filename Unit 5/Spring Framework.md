# Spring Framework — Exam-Oriented Practical Notes

## 1. Introduction to Spring Framework

### 1.1 What is the Spring Framework?

The **Spring Framework** is an open-source Java framework used to develop enterprise applications and web applications.

It simplifies Java development by providing features such as Dependency Injection (DI), Inversion of Control (IoC), MVC architecture, transaction management, and integration with databases.

**Simple example:** Without Spring, we may create objects manually using `new`. Spring can create and manage these objects for us.

### 1.2 Why do we need Spring?

Consider a Java application with three classes:

- `Engine` — performs engine-related operations.
- `Car` — requires an engine.
- `Main` — creates and connects objects.

Without Spring:

```java
class Engine {
    void start() {
        System.out.println("Engine started");
    }
}

class Car {
    Engine engine = new Engine();

    void drive() {
        engine.start();
        System.out.println("Car is moving");
    }
}
```

Here, `Car` creates its own `Engine` object. This creates tight coupling because `Car` controls how its dependency is created.

Spring helps manage objects and inject their dependencies, making applications easier to maintain and test.

### 1.3 Important features of Spring

| Feature | Purpose |
|---|---|
| IoC Container | Creates and manages application objects |
| Dependency Injection | Supplies required objects to other objects |
| Spring MVC | Develops web applications using the MVC pattern |
| Spring Security | Provides authentication and authorization |
| Transaction Management | Manages database transactions |
| Database Integration | Integrates with JDBC, JPA and other persistence technologies |
| Test Support | Helps test application components |

### 1.4 Spring Framework architecture

```text
              Java Application
                     |
        +------------+------------+
        |            |            |
    Core Container  Web Layer   Data Access
        |            |            |
     IoC / DI    Spring MVC    JDBC / ORM
        |            |            |
        +------------+------------+
                     |
               Spring Framework
```

Spring Security integrates with the application, particularly the web request-processing pipeline, to provide security features.

### 1.5 Spring Framework vs Spring Boot

| Spring Framework | Spring Boot |
|---|---|
| Provides core application development features | Simplifies creating Spring applications |
| Often requires more manual configuration | Provides auto-configuration |
| Dependencies and server setup may require more work | Starter dependencies and embedded servers simplify setup |
| Gives control over configuration | Uses sensible defaults that can be customized |

**Exam point:** Spring Boot is built on top of the Spring ecosystem. It does not replace the Spring Framework.

---

## 2. Dependency Injection (DI) and Inversion of Control (IoC)

### 2.1 What is Inversion of Control?

**Inversion of Control (IoC)** is a principle in which control over creating and managing objects is transferred from application code to a framework or container.

Without IoC, our application creates and manages its objects directly.

With Spring IoC, the Spring container creates and manages objects called **beans**.

### 2.2 What is Dependency Injection?

**Dependency Injection (DI)** is a technique in which an object receives the other objects it needs from an external source instead of creating them itself.

For example, a `Car` needs an `Engine`. Spring can provide an `Engine` object to the `Car`.

### 2.3 Relationship between IoC and DI

```text
          Spring IoC Container
                   |
            Creates Objects
                   |
             Engine Bean
                   |
           Injects into Car
                   |
                Car Bean
```

- **IoC** is the broader principle.
- **DI** is one of the main ways Spring implements IoC.

### 2.4 Practical example: Constructor Injection

The following example uses Spring Boot's component scanning and constructor injection.

**File 1: `Engine.java`**

```java
import org.springframework.stereotype.Component;

@Component
public class Engine {

    public void start() {
        System.out.println("Engine started");
    }
}
```

`@Component` tells Spring that this class can be discovered and registered as a bean.

**File 2: `Car.java`**

```java
import org.springframework.stereotype.Component;

@Component
public class Car {

    private final Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void drive() {
        engine.start();
        System.out.println("Car is moving");
    }
}
```

The constructor declares that `Car` requires an `Engine`. Spring provides the required bean.

**File 3: `DemoApplication.java`**

```java
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner run(Car car) {
        return args -> car.drive();
    }
}
```

Assuming these classes are in the application's component-scanned package, the output is:

```text
Engine started
Car is moving
```

### 2.5 How does the example work?

1. `@SpringBootApplication` starts the Spring Boot application and enables component scanning.
2. Spring discovers the `Engine` and `Car` components.
3. Spring creates and manages their beans.
4. Spring sees that `Car` needs an `Engine`.
5. Spring injects the `Engine` bean into the `Car` constructor.
6. The `CommandLineRunner` invokes `car.drive()` when the application starts.

### 2.6 Types of Dependency Injection

| Type | Explanation | Common use |
|---|---|---|
| Constructor Injection | Dependency is supplied through a constructor | Recommended for required dependencies |
| Setter Injection | Dependency is supplied through a setter method | Useful for optional dependencies |
| Field Injection | Dependency is injected directly into a field | Convenient, but generally less suitable for testing and immutability |

**Setter Injection example:**

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {

    private Engine engine;

    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
```

Constructor injection is generally preferred for required dependencies because it makes them explicit and allows fields to be `final`.

### 2.7 Important Spring annotations

| Annotation | Purpose |
|---|---|
| `@Component` | Registers a class as a Spring-managed component |
| `@Service` | Identifies a service-layer component |
| `@Repository` | Identifies a data-access component |
| `@Autowired` | Requests dependency injection |
| `@Bean` | Declares a bean through a configuration method |
| `@Configuration` | Declares a configuration class |
| `@SpringBootApplication` | Main Spring Boot application annotation |

**Exam questions:**

1. Define IoC and Dependency Injection.
2. Explain how IoC and DI are related.
3. Explain constructor, setter and field injection.
4. Write a Spring program demonstrating constructor injection.
5. Explain the role of the Spring IoC container.

---

## 3. Spring MVC

### 3.1 What is Spring MVC?

**Spring MVC** is a web framework based on the Model-View-Controller design pattern. It separates application data, user-interface presentation, and request-handling logic.

It is commonly used to develop Java web applications.

### 3.2 MVC components

| Component | Responsibility | Example |
|---|---|---|
| Model | Holds application data | Student details |
| View | Displays information | HTML page |
| Controller | Processes requests and coordinates responses | `StudentController` |

### 3.3 Spring MVC architecture diagram

```text
          User / Browser
                 |
             HTTP Request
                 |
                 v
       DispatcherServlet
          (Front Controller)
                 |
                 v
             Controller
                 |
                 v
          Service / Model
                 |
                 v
        Model + View Name
                 |
                 v
            View Resolver
                 |
                 v
            HTML / View
                 |
                 v
          HTTP Response
                 |
                 v
          User / Browser
```

The `DispatcherServlet` is the front controller. It receives incoming requests and coordinates request handling with the appropriate components.

### 3.4 Practical example: Student greeting application

This example returns a simple HTML response. It demonstrates request mapping without requiring a template engine.

**File 1: `StudentController.java`**

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @GetMapping("/student")
    public String showStudent() {
        return "<h1>Welcome, Student!</h1>";
    }
}
```

**How it works:**

1. Start the Spring Boot application with the Spring Web dependency.
2. Open a browser.
3. Visit `http://localhost:8080/student`.
4. Spring maps the request to `showStudent()`.
5. The browser receives the returned HTML string.

Output displayed in the browser:

Welcome, Student!

`@RestController` returns response data directly. It is suitable for this simple example, but a traditional server-rendered MVC application commonly uses `@Controller` with a view template.

### 3.5 Traditional MVC example with a view

**Controller: `StudentController.java`**

```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    @GetMapping("/student")
    public String showStudent(Model model) {

        model.addAttribute("name", "Akash");

        return "student";
    }
}
```

**View: `src/main/resources/templates/student.html`**

```html
<!DOCTYPE html>
<html>
<head>
    <title>Student Page</title>
</head>
<body>
    <h1>Welcome, <span th:text="${name}"></span>!</h1>
</body>
</html>
```

This version requires the Spring Boot Thymeleaf starter dependency.

When the browser requests `/student`, the controller adds the student's name to the model and returns the view name `student`. Thymeleaf renders the HTML template.

Expected page:

```text
Welcome, Akash!
```

### 3.6 Important Spring MVC annotations

| Annotation | Purpose |
|---|---|
| `@Controller` | Declares a web controller that commonly returns view names |
| `@RestController` | Returns response data directly by default |
| `@RequestMapping` | Maps requests to controller classes or methods |
| `@GetMapping` | Maps HTTP GET requests |
| `@PostMapping` | Maps HTTP POST requests |
| `@PathVariable` | Reads a value from the URL path |
| `@RequestParam` | Reads a query parameter or request parameter |
| `@ModelAttribute` | Binds form data to an object or exposes model attributes |
| `@ResponseBody` | Writes a method's return value directly to the response body |

### 3.7 GET vs POST

| GET | POST |
|---|---|
| Commonly used to retrieve information | Commonly used to submit data or perform an operation |
| Parameters may appear in the URL | Data is commonly sent in the request body |
| Can be bookmarked with its URL | Request body is not stored in a bookmark |
| Should not be used to perform state-changing operations | Often used for state-changing operations |

**Important:** POST does not automatically make data secure. HTTPS and appropriate application security are still required.

### 3.8 Spring MVC vs Spring Boot REST API

Spring MVC supports both server-rendered web pages and HTTP APIs. A REST API is not a separate replacement for Spring MVC; Spring MVC provides the request-mapping infrastructure commonly used to build REST APIs.

**Exam questions:**

1. Define Spring MVC and explain its architecture with a diagram.
2. Explain the role of `DispatcherServlet`.
3. Differentiate Model, View and Controller.
4. Explain `@Controller` and `@RestController`.
5. Write a program to handle an HTTP GET request.
6. Differentiate GET and POST requests.

---

## 4. Spring Security

### 4.1 What is Spring Security?

**Spring Security** is a framework that provides authentication, authorization and protection against common security attacks in Java applications.

It integrates with Spring applications to control who can access application resources.

### 4.2 Why do we need Spring Security?

Consider a student portal with the following pages:

- Login page — accessible to users who are not logged in.
- Student dashboard — accessible to authenticated users.
- Admin dashboard — accessible only to administrators.

Without security controls, users may be able to access restricted pages. Spring Security allows us to configure access rules and enforce them.

### 4.3 Authentication vs Authorization

| Authentication | Authorization |
|---|---|
| Verifies who the user is | Determines what the user is allowed to do |
| Example: Checking login credentials | Example: Checking whether the user has the ADMIN role |
| Happens when establishing the user's identity | Access permissions are evaluated for protected resources |

**Remember:** Authentication = Who are you? Authorization = What can you access?

### 4.4 Spring Security request flow

```text
           User / Browser
                  |
                  v
             HTTP Request
                  |
                  v
        Security Filter Chain
                  |
                  v
          Authentication Check
                  |
             +----+----+
             |         |
          Success    Failure
             |         |
             v         v
      Authorization   Deny /
          Check       Login
             |
        +----+----+
        |         |
     Allowed    Denied
        |         |
        v         v
    Controller   403
```

The diagram is simplified. In an actual application, the filter chain contains multiple filters, and authentication and authorization checks depend on the configured security mechanism.

### 4.5 Important Spring Security concepts

- **Security Filter Chain:** Applies security filters to incoming requests.
- **Authentication:** Establishes a user's identity.
- **Authorization:** Checks access permissions.
- **Password Encoding:** Stores password hashes instead of plain-text passwords.
- **Role-Based Access Control:** Restricts resources based on roles such as `USER` and `ADMIN`.
- **CSRF Protection:** Helps protect against cross-site request forgery.
- **Session Management:** Controls how authenticated sessions are handled.

### 4.6 Practical example: Secure a Spring Boot application

Add the Spring Boot Security starter dependency to a Spring Boot project that already includes Spring Web.

For Maven, the dependency is:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

With the standard Spring Boot security auto-configuration and no custom security configuration, requests are secured by default. A generated development password is logged when the application starts, and the default login form is provided.

For a predictable demonstration, we can define explicit credentials and URL rules.

**File: `SecurityConfig.java`**

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder encoder) {

        UserDetails student = User.builder()
                .username("student")
                .password(encoder.encode("student123"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(student, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/student").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults());

        return http.build();
    }
}
```

This example uses in-memory users for learning purposes only. The sample passwords are for a local demonstration and must not be used in a real application.

### 4.7 Create the controller

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/public")
    public String publicPage() {
        return "This page is public.";
    }

    @GetMapping("/student")
    public String studentPage() {
        return "Welcome to the student dashboard.";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "Welcome to the admin dashboard.";
    }
}
```

### 4.8 Test the application

| URL | Access requirement | Expected result |
|---|---|---|
| `/public` | No login required | Public message |
| `/student` | `USER` or `ADMIN` | Student dashboard |
| `/admin` | `ADMIN` only | Admin dashboard |

Try these steps:

1. Start the application.
2. Open `http://localhost:8080/public`. The page should open without login.
3. Open `http://localhost:8080/student`. The login form should appear.
4. Log in with `student` and `student123`. The student page should open.
5. Try `/admin` as the student. Access should be denied.
6. Log in as `admin` with `admin123`. The admin page should open.

The exact browser behavior can vary with the session and authentication state. Log out or use a private browser window when testing different users.

### 4.9 HTTP status codes

| Code | Meaning | Example |
|---|---|---|
| 200 | OK | Successful page response |
| 302 | Redirect | Redirect to login |
| 401 | Unauthorized | Authentication is required or failed in an API-oriented flow |
| 403 | Forbidden | Access is denied |
| 404 | Not Found | Requested resource does not exist |

In Spring Security, an unauthenticated browser request may be redirected to the login page instead of receiving a `401` response.

### 4.10 CSRF protection

CSRF stands for **Cross-Site Request Forgery**. It is an attack in which a user's authenticated browser is tricked into sending an unwanted request to a website.

Spring Security enables CSRF protection by default for typical web configurations. Do not disable it simply to make a form work. For a traditional web form, include the required CSRF token; Spring-integrated form technologies can add it automatically.

**Exam questions:**

1. Define Spring Security and explain its importance.
2. Differentiate authentication and authorization.
3. Explain the Spring Security filter chain with a diagram.
4. Explain role-based access control with an example.
5. What are CSRF attacks?
6. Explain `SecurityFilterChain` and `PasswordEncoder`.
7. Write a configuration that restricts access to an admin page.

---

## 5. Quick Revision: Important Annotations and Classes

| Name | Main purpose |
|---|---|
| `@SpringBootApplication` | Starts and configures a Spring Boot application |
| `@Component` | Registers a component as a bean candidate |
| `@Service` | Marks service-layer logic |
| `@Repository` | Marks data-access logic |
| `@Autowired` | Requests dependency injection |
| `@Bean` | Registers an object returned by a configuration method |
| `@Configuration` | Defines Spring configuration |
| `DispatcherServlet` | Central request dispatcher in Spring MVC |
| `@Controller` | Handles web requests, commonly returning views |
| `@RestController` | Handles web requests and returns response bodies |
| `@GetMapping` | Maps GET requests |
| `@PostMapping` | Maps POST requests |
| `SecurityFilterChain` | Configures the web security filter chain |
| `PasswordEncoder` | Encodes passwords and verifies password matches |
| `UserDetailsService` | Loads user information for authentication |

---

## 6. Important Exam Questions

### Short-answer questions (2–3 marks)

1. Define the Spring Framework.
2. What is a Spring bean?
3. Define IoC and DI.
4. What is constructor injection?
5. What is `DispatcherServlet`?
6. Differentiate `@Controller` and `@RestController`.
7. Define authentication and authorization.
8. What is the purpose of `PasswordEncoder`?

### Long-answer questions (5–10 marks)

1. Explain the architecture and features of the Spring Framework.
2. Explain IoC and DI with a practical Java example.
3. Describe the different types of Dependency Injection.
4. Explain Spring MVC architecture with a neat diagram.
5. Develop a simple Spring MVC application that handles a GET request.
6. Explain Spring Security architecture and its filter chain.
7. Demonstrate role-based access control for student and admin pages.
8. Explain CSRF protection and password encoding.

### Practical lab tasks

- **Task 1:** Create two components and demonstrate constructor injection.
- **Task 2:** Create a Spring MVC controller that returns a greeting for `/hello`.
- **Task 3:** Build a student page that displays data using a Thymeleaf template.
- **Task 4:** Configure a public page and an authenticated student page.
- **Task 5:** Create `USER` and `ADMIN` roles and restrict the admin page to administrators.

---

## 7. Final Revision Summary

| Topic | Remember this |
|---|---|
| Spring Framework | Simplifies enterprise Java application development |
| IoC | Spring takes control of object creation and management |
| DI | Required objects are supplied to a class |
| Spring MVC | Separates request handling, data and presentation |
| DispatcherServlet | Central entry point for Spring MVC requests |
| Spring Security | Handles authentication and authorization |
| PasswordEncoder | Avoids storing raw passwords |
| Role-Based Access | Allows access according to a user's roles |

**Exam preparation tip:** Practise drawing the IoC/DI diagram, Spring MVC architecture and Spring Security flow without looking at the notes. Then write one small practical program for each major topic.
