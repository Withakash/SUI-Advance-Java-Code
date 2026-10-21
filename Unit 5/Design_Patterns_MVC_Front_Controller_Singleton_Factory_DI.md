# Design Patterns in Java --- Student Notes

## MVC, Front Controller, Singleton, Factory, and Dependency Injection

**Subject:** Advanced Java / Java Web Applications\
**Level:** Beginner-friendly\
**Language:** Simple English

------------------------------------------------------------------------

## Learning Objectives

After studying these notes, students should be able to:

-   Explain what a design pattern is and why it is useful.
-   Describe MVC and identify the Model, View, and Controller.
-   Explain the Front Controller pattern and how it differs from MVC.
-   Implement a basic Singleton class in Java.
-   Explain and implement a simple Factory pattern.
-   Explain Dependency Injection (DI) and constructor injection.
-   Understand how these concepts are used in Servlet/JSP and Spring
    applications.

------------------------------------------------------------------------

# 1. What Is a Design Pattern?

A **design pattern** is a reusable design idea for solving a commonly
occurring software design problem. It is not a complete application or a
ready-made piece of code. Developers adapt the idea to their specific
requirements.

### Real-life analogy

An architect uses a blueprint as a starting point when designing a
building. Every building is different, but a proven plan can help
organize the work. A design pattern plays a similar role in software
design.

### Why are design patterns useful?

-   They encourage clear and organized code.
-   They provide a shared vocabulary for developers.
-   They can reduce duplicated design effort.
-   They make code easier to maintain when applied appropriately.

**Important:** A pattern is not automatically the best solution for
every problem. Unnecessary patterns can make a small program more
complicated.

## Three common categories

  -----------------------------------------------------------------------
  Category                Main question           Examples
  ----------------------- ----------------------- -----------------------
  Creational              How should objects be   Singleton, Factory,
                          created?                Builder

  Structural              How should classes and  Adapter, Decorator
                          objects fit together?   

  Behavioral              How should objects      Observer, Strategy
                          communicate or share    
                          responsibilities?       
  -----------------------------------------------------------------------

In these notes, MVC and Front Controller are architectural/design
patterns commonly used in web applications. Singleton and Factory
concern object creation. Dependency Injection is a technique and a
central practice in Inversion of Control (IoC)-based design.

------------------------------------------------------------------------

# 2. MVC Pattern

## 2.1 What is MVC?

**MVC stands for Model--View--Controller.** It separates an
application's responsibilities into three parts so that data/business
logic, user interface, and request handling are not all mixed into one
class or file.

Imagine a Student Portal where a student checks their marks.

### The three components

  -----------------------------------------------------------------------
  Component               Responsibility          Example in a
                                                  Servlet/JSP application
  ----------------------- ----------------------- -----------------------
  **Model**               Holds application data  `Student`,
                          and/or business logic   `StudentService`,
                                                  `StudentRepository`

  **View**                Presents information to `result.jsp`, HTML page
                          the user                

  **Controller**          Receives a request,     `ResultServlet`
                          coordinates work, and   
                          selects the response    
  -----------------------------------------------------------------------

The exact classes used for the Model can vary. In a well-structured
application, business logic is often placed in service classes rather
than directly in a JSP.

## 2.2 Simple MVC diagram

``` text
        Student / Browser
               |
          HTTP Request
               v
       +----------------+
       |   Controller   |
       |  ResultServlet |
       +----------------+
               |
       asks for student data
               v
       +----------------+
       |     Model      |
       | Service / Data |
       +----------------+
               |
        result data returns
               v
       +----------------+
       |      View      |
       |   result.jsp   |
       +----------------+
               |
          HTML Response
               v
        Student / Browser
```

This is a simplified flow. In a real application, the controller may
call a service, which may call a repository or database.

## 2.3 MVC flow in a Student Portal

1.  The student opens `/result`.
2.  `ResultServlet` receives the request.
3.  The controller calls a service to obtain the student's result.
4.  The controller places the result in a request attribute.
5.  The controller forwards the request to `result.jsp`.
6.  The JSP displays the result as HTML.

## 2.4 Simple Java Servlet/JSP example

### Controller --- `ResultServlet.java`

``` java
@WebServlet("/result")
public class ResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // In a real application, retrieve this from a service/database.
        String studentName = "Aarav";
        int marks = 85;

        request.setAttribute("studentName", studentName);
        request.setAttribute("marks", marks);

        request.getRequestDispatcher("/result.jsp")
               .forward(request, response);
    }
}
```

Add the required Servlet imports and ensure the application is
configured for the Servlet API version used by your server.

### View --- `result.jsp`

``` jsp
<!DOCTYPE html>
<html>
<head>
    <title>Student Result</title>
</head>
<body>
    <h2>Student Result</h2>
    <p>Name: ${studentName}</p>
    <p>Marks: ${marks}</p>
</body>
</html>
```

Here, the Servlet acts as the controller and the JSP acts as the view.
The sample values are hard-coded only to demonstrate the flow.

## 2.5 Advantages of MVC

-   **Separation of concerns:** each part has a clearer responsibility.
-   **Maintainability:** changes to the user interface need not require
    changes to business logic.
-   **Reusability:** business logic can often be reused by different
    views or controllers.
-   **Testability:** business logic can be tested separately from the
    presentation layer.

## 2.6 Important points

-   MVC describes how application responsibilities are separated.
-   In a traditional Servlet/JSP application, a Servlet commonly acts as
    a Controller and JSP as a View.
-   The Model is not necessarily just a data class; it can include
    domain data and business logic, depending on the architecture.
-   Do not put database logic directly inside JSP pages.

------------------------------------------------------------------------

# 3. Front Controller Pattern

## 3.1 What is Front Controller?

The **Front Controller pattern** uses one central entry point to receive
a set of requests and coordinate their processing. It can handle common
tasks and route requests to the appropriate controller or handler.

Imagine a college reception desk. Visitors first speak to the
receptionist, who directs them to the correct department.

**Reception desk = Front Controller**

## 3.2 Why use it?

A web application may have many routes:

-   `/login`
-   `/result`
-   `/attendance`
-   `/student`

If every route separately repeats common request-processing code, the
application can become harder to maintain. A central entry point can
help organize routing and common handling.

Common responsibilities may include:

-   Request routing
-   Logging
-   Authentication checks, where appropriate
-   Common error handling
-   Coordinating the next handler

Authentication and authorization still need to be designed correctly; a
Front Controller alone does not automatically make an application
secure.

## 3.3 Simple diagram

``` text
       Browser Requests
   /login /result /attendance
               |
               v
       +------------------+
       |  Front Controller|
       |  Central Entry   |
       +------------------+
          /      |       \
         v       v        v
     +-------+ +------+ +------------+
     | Login | |Result| | Attendance |
     |Handler| |Handler| |  Handler  |
     +-------+ +------+ +------------+
          \       |        /
           \      |       /
             Response
```

## 3.4 Basic Servlet example

This example demonstrates the central-routing idea. It is intentionally
small; production applications should use dedicated handlers/controllers
rather than growing a large `if-else` block.

``` java
@WebServlet("/app/*")
public class FrontControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if ("/result".equals(path)) {
            request.getRequestDispatcher("/result.jsp")
                   .forward(request, response);

        } else if ("/attendance".equals(path)) {
            request.getRequestDispatcher("/attendance.jsp")
                   .forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
```

For example, `/app/result` is mapped to the servlet, and `getPathInfo()`
returns `/result` in this mapping. This code handles only GET requests;
an application must also design its POST and other request handling as
required.

## 3.5 Front Controller in Spring MVC

In Spring MVC, **`DispatcherServlet` acts as the Front Controller**. It
receives mapped web requests and coordinates with Spring MVC components
to find a handler and process the response.

``` text
Browser
   |
   v
DispatcherServlet  <- Front Controller
   |
   v
Handler Mapping / Controller
   |
   v
Service / Repository (as needed)
   |
   v
View (JSP/template) or response body (JSON)
```

The exact response flow depends on whether the controller returns a
view, a redirect, or a response body such as JSON.

------------------------------------------------------------------------

# 4. MVC vs Front Controller

These concepts are related, but they are **not the same pattern**.

  -----------------------------------------------------------------------
  MVC                                 Front Controller
  ----------------------------------- -----------------------------------
  Organizes responsibilities into     Provides one central entry point
  Model, View, and Controller.        for a set of requests.

  Focuses on separation of concerns.  Focuses on central request handling
                                      and routing.

  A controller handles or coordinates The front controller dispatches
  a request.                          requests to appropriate
                                      handlers/controllers.

  Common in Servlet/JSP and Spring    Used by frameworks such as Spring
  MVC applications.                   MVC through `DispatcherServlet`.
  -----------------------------------------------------------------------

**Key point:** An application can use both. MVC defines the overall
separation of responsibilities, while a Front Controller can serve as
the central entry point for requests.

------------------------------------------------------------------------

# 5. Singleton Pattern

## 5.1 What is Singleton?

The **Singleton pattern** is a creational design pattern that controls
object creation so that a class provides one shared instance within its
intended scope, along with a way to access it.

Imagine several classes in an application need to read the same
application configuration object.

``` text
        +-------------------+
        | AppConfig Instance|
        |   Shared Object   |
        +-------------------+
            ^           ^
            |           |
       +---------+  +---------+
       | Class A |  | Class B |
       +---------+  +---------+
       Both use the same instance
```

## 5.2 Without Singleton

``` java
class AppConfig {
    AppConfig() {
        System.out.println("Object created");
    }
}

public class Main {
    public static void main(String[] args) {
        AppConfig a = new AppConfig();
        AppConfig b = new AppConfig();
    }
}
```

Output:

``` text
Object created
Object created
```

Two separate objects are created because the constructor can be called
from outside the class.

## 5.3 Basic Singleton implementation

``` java
class AppConfig {

    private static final AppConfig INSTANCE = new AppConfig();

    private AppConfig() {
        System.out.println("Object created");
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }
}

public class Main {
    public static void main(String[] args) {
        AppConfig a = AppConfig.getInstance();
        AppConfig b = AppConfig.getInstance();

        System.out.println(a == b);
    }
}
```

Output:

``` text
Object created
true
```

The `true` result means both variables refer to the same object.

## 5.4 Three important parts

1.  **Private constructor:** outside code cannot call `new AppConfig()`
    directly.
2.  **Static instance:** stores the shared instance at class level.
3.  **Public static accessor:** `getInstance()` returns that instance.

This implementation uses eager initialization: the instance is created
when the class is initialized.

## 5.5 Where can it be useful?

-   Shared, read-only application configuration
-   A single coordinating object, when the design genuinely requires one
    instance
-   Other resources whose lifecycle and ownership are intentionally
    centralized

Do not make every class a Singleton. It can introduce global state,
complicate testing, and create concurrency problems if mutable state is
shared.

## 5.6 Singleton vs ServletContext

`ServletContext` may feel similar because multiple servlets in the same
web application can access the same context. However, they are different
concepts.

  -----------------------------------------------------------------------
  Singleton                           ServletContext
  ----------------------------------- -----------------------------------
  A design pattern implemented by a   An object provided and managed by
  class.                              the Servlet container.

  Controls access to a class's shared Represents the context of a web
  instance.                           application.

  Can be used outside web             Belongs to the Servlet
  applications.                       web-application environment.

  Its scope depends on the            Shared by components in the same
  implementation and                  web application.
  runtime/class-loader context.       
  -----------------------------------------------------------------------

**Exam point:** `ServletContext` provides application-wide context;
Singleton is a design pattern for providing a shared instance.
Similarity in sharing does not make them identical.

Also, do not store user-specific data in a global Singleton. Mutable
shared data in either a Singleton or `ServletContext` must be handled
carefully because multiple requests may run concurrently.

------------------------------------------------------------------------

# 6. Factory Pattern

## 6.1 What is Factory?

The **Factory pattern** centralizes object-creation decisions. Instead
of making the calling code choose and construct every concrete class
directly, it asks a factory to provide an object of the required type.

### Real-life analogy

At a pizza shop, you request a type of pizza. You do not need to manage
every preparation step yourself. The shop handles the creation process
and gives you the result.

## 6.2 Example: Notification system

Our application supports Email and SMS notifications.

``` text
          Application
               |
      requests "email" or "sms"
               v
     +----------------------+
     | NotificationFactory  |
     +----------------------+
           /          \
          v            v
 +----------------+ +----------------+
 | EmailNotification| | SMSNotification|
 +----------------+ +----------------+
           \          /
            Notification
               interface
```

## 6.3 Step 1 --- Create a common interface

``` java
interface Notification {
    void send(String message);
}
```

The interface specifies what each notification type must do.

## 6.4 Step 2 --- Create implementations

``` java
class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

class SMSNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}
```

## 6.5 Step 3 --- Create the factory

``` java
class NotificationFactory {

    public static Notification create(String type) {
        if ("email".equalsIgnoreCase(type)) {
            return new EmailNotification();
        }

        if ("sms".equalsIgnoreCase(type)) {
            return new SMSNotification();
        }

        throw new IllegalArgumentException(
            "Unknown notification type: " + type
        );
    }
}
```

The factory decides which concrete object to create and returns it
through the common `Notification` interface.

## 6.6 Step 4 --- Use the factory

``` java
public class Main {
    public static void main(String[] args) {
        Notification notification =
            NotificationFactory.create("email");

        notification.send("Welcome to the college portal!");
    }
}
```

Output:

``` text
Email: Welcome to the college portal!
```

To create an SMS notification, call `NotificationFactory.create("sms")`.

## 6.7 Benefits of Factory

-   Centralizes object-creation logic.
-   Reduces direct dependency on concrete implementation classes in
    calling code.
-   Gives the application one place to handle creation rules.
-   Can make it easier to add or change supported implementations.

**Important:** This is a simple factory-style example. The term "Factory
Pattern" is also used for more formal patterns, including Factory Method
and Abstract Factory. A simple factory with `if-else` statements is
useful for learning, but a very large set of conditions may need a more
extensible design.

------------------------------------------------------------------------

# 7. Dependency Injection (DI)

## 7.1 What is Dependency Injection?

**Dependency Injection (DI)** means supplying an object that a class
needs from outside, instead of making that class create the dependency
itself.

A **dependency** is an object or service that another class needs to do
its work.

### Real-life analogy

A teacher needs a projector to present a lesson. The teacher uses the
projector provided by the institution rather than building a projector.
The teacher can focus on teaching.

## 7.2 Without DI

Suppose `StudentService` needs a `StudentRepository`.

``` java
class StudentRepository {
    void save() {
        System.out.println("Student saved!");
    }
}

class StudentService {

    private StudentRepository repository =
        new StudentRepository();

    void registerStudent() {
        repository.save();
    }
}
```

**Problem:** `StudentService` creates its own repository. It is directly
tied to that concrete implementation, which can make replacing or
testing the dependency harder.

## 7.3 With DI --- Constructor Injection

``` java
class StudentRepository {
    void save() {
        System.out.println("Student saved!");
    }
}

class StudentService {

    private final StudentRepository repository;

    StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    void registerStudent() {
        repository.save();
    }
}

public class Main {
    public static void main(String[] args) {
        StudentRepository repo = new StudentRepository();

        StudentService service = new StudentService(repo);
        service.registerStudent();
    }
}
```

Output:

``` text
Student saved!
```

The key line is:

``` java
StudentService service = new StudentService(repo);
```

The repository object is supplied through the constructor. This is
called **constructor injection**.

## 7.4 Use an interface for looser coupling

A more flexible design can depend on an interface rather than a specific
repository implementation.

``` java
interface StudentRepository {
    void save();
}

class InMemoryStudentRepository implements StudentRepository {
    @Override
    public void save() {
        System.out.println("Student saved in memory!");
    }
}

class StudentService {

    private final StudentRepository repository;

    StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    void registerStudent() {
        repository.save();
    }
}

public class Main {
    public static void main(String[] args) {
        StudentRepository repo = new InMemoryStudentRepository();
        StudentService service = new StudentService(repo);

        service.registerStudent();
    }
}
```

Now `StudentService` depends on the `StudentRepository` abstraction.
Another implementation could be supplied without changing the service
class, provided it follows the same interface.

## 7.5 Common types of DI

  -----------------------------------------------------------------------
  Type                    How the dependency is   Typical guidance
                          supplied                
  ----------------------- ----------------------- -----------------------
  Constructor injection   Through a constructor   Usually preferred for
                          parameter               required dependencies

  Setter injection        Through a setter method Useful for optional or
                                                  changeable dependencies
                                                  in some designs

  Field injection         Directly into a field,  Convenient, but can
                          often using a framework make dependencies less
                          annotation              explicit and unit
                                                  testing harder
  -----------------------------------------------------------------------

## 7.6 DI and Spring Framework

Spring provides an IoC container that creates and manages configured
objects called **Spring beans**. The container can inject one bean into
another.

Example:

``` java
@Repository
class StudentRepository {
    public void save() {
        System.out.println("Student saved!");
    }
}
```

``` java
@Service
class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public void registerStudent() {
        repository.save();
    }
}
```

With suitable Spring configuration and component scanning, Spring can
discover these classes, create their beans, and supply the repository
bean to the service constructor. The snippets illustrate the pattern; a
complete application also needs the relevant Spring dependencies and
configuration.

## 7.7 IoC vs DI

  -----------------------------------------------------------------------
  Term                                Simple meaning
  ----------------------------------- -----------------------------------
  **IoC --- Inversion of Control**    Control over creating and managing
                                      objects is shifted from application
                                      code to a framework/container or
                                      another coordinator.

  **DI --- Dependency Injection**     A way to provide the dependencies
                                      an object needs.

  **Spring Bean**                     An object managed by the Spring
                                      container.
  -----------------------------------------------------------------------

**Important:** DI and IoC are related but are not identical. DI is a
common way to achieve IoC.

Also, **DI does not mean Singleton**. Spring can inject beans with
different scopes. By default, a Spring bean is typically
singleton-scoped within its bean factory, but that is a Spring scope
setting---not a requirement of Dependency Injection itself.

------------------------------------------------------------------------

# 8. Singleton vs Factory vs Dependency Injection

  ---------------------------------------------------------------------------------------------
  Concept           Main question     Main purpose      Example
  ----------------- ----------------- ----------------- ---------------------------------------
  **Singleton**     "How do we        Controls shared   `AppConfig.getInstance()`
                    provide one       instance access   
                    shared instance?"                   

  **Factory**       "Which object     Centralizes       `NotificationFactory.create("email")`
                    should be         creation          
                    created?"         decisions         

  **Dependency      "How does this    Supplies          `new StudentService(repo)`
  Injection**       class receive     dependencies from 
                    what it needs?"   outside           
  ---------------------------------------------------------------------------------------------

These concepts can work together, but they solve different problems.

### Combined example

In a Student Portal:

-   A factory may choose an Email or SMS notification implementation.
-   A service may receive its repository through Dependency Injection.
-   A shared configuration object might use Singleton if a single
    instance is genuinely appropriate.

Do not use Singleton just because an object is injected. An injected
object can have a different scope.

------------------------------------------------------------------------

# 9. Quick Revision

-   **Design Pattern:** A reusable design idea for a recurring software
    problem.
-   **MVC:** Separates Model, View, and Controller responsibilities.
-   **Front Controller:** Uses one central entry point to handle and
    route requests.
-   **Singleton:** Provides controlled access to one shared instance
    within an intended scope.
-   **Factory:** Centralizes decisions about which object to create.
-   **Dependency Injection:** Supplies an object's dependencies from
    outside.
-   **Spring `DispatcherServlet`:** Acts as the Front Controller in
    Spring MVC.
-   **ServletContext:** A Servlet-container-managed context shared
    within one web application.
-   **IoC:** Transfers object-management control to a
    container/framework.
-   **Spring Bean:** An object managed by the Spring container.

------------------------------------------------------------------------

# 10. Exam Questions

## A. Short-answer questions (2--3 marks)

1.  What is a design pattern?
2.  What does MVC stand for?
3.  State one responsibility of the Model in MVC.
4.  What is the role of a Controller in an MVC application?
5.  Define the Front Controller pattern.
6.  Name the class that acts as the Front Controller in Spring MVC.
7.  What is the Singleton pattern?
8.  Why is the constructor private in a typical Singleton
    implementation?
9.  What is the purpose of `getInstance()` in a Singleton class?
10. Define the Factory pattern.
11. What is the purpose of a common interface in the notification
    Factory example?
12. What is Dependency Injection?
13. What is constructor injection?
14. Define IoC.
15. What is a Spring bean?
16. Differentiate between a Singleton and a Factory in one or two
    points.
17. Is Dependency Injection the same as Singleton? Explain briefly.
18. How is `ServletContext` different from the Singleton pattern?

## B. Long-answer questions (5 marks)

1.  Explain the MVC pattern with a suitable example and a neat diagram.
2.  Explain the roles of Model, View, and Controller in a Servlet/JSP
    application.
3.  Explain the Front Controller pattern with a diagram and describe its
    advantages.
4.  Differentiate between MVC and Front Controller.
5.  Explain the Singleton pattern with Java code and output.
6.  Explain the Factory pattern using an Email/SMS notification example.
7.  Explain Dependency Injection and demonstrate constructor injection
    using Java.
8.  Differentiate between IoC and DI.
9.  Compare Singleton, Factory, and Dependency Injection with examples.
10. Explain how `DispatcherServlet` works as the Front Controller in
    Spring MVC.

## C. Long-answer questions (10 marks)

1.  **Explain MVC architecture in detail.** Draw a diagram and describe
    the complete request flow in a Student Portal using Servlet and JSP.
2.  **What is the Front Controller pattern?** Explain its purpose,
    working, advantages, and implementation using a basic Servlet
    example.
3.  **Explain the Singleton pattern.** Write a Java program to
    demonstrate it, explain the private constructor and static instance,
    and compare it with `ServletContext`.
4.  **Explain the Factory pattern in detail.** Develop a Java
    notification system that supports Email and SMS using a common
    interface and a factory class.
5.  **What is Dependency Injection?** Explain the problem of tight
    coupling, demonstrate constructor injection with Java code, and
    discuss how Spring supports DI.
6.  Compare MVC, Front Controller, Singleton, Factory, and Dependency
    Injection in terms of purpose, working, and use cases.
7.  Explain IoC and DI in Spring Framework. Describe Spring beans,
    constructor injection, and the role of the IoC container with a
    diagram.
8.  Design a simple Student Portal using MVC. Explain how a Front
    Controller could route requests and how DI could supply a service's
    repository dependency.

## D. Practical / programming questions

1.  Write a Java Singleton class and show that two references point to
    the same instance.
2.  Implement `Notification`, `EmailNotification`, and
    `SMSNotification`, then create a `NotificationFactory`.
3.  Write a program that uses constructor injection to provide a
    repository to a service.
4.  Create a basic `ResultServlet` that sends student data to
    `result.jsp` using request attributes and `forward()`.
5.  Draw a diagram showing how a browser request moves through Spring
    MVC's `DispatcherServlet` to a controller and then to a view or
    response body.

------------------------------------------------------------------------

# 11. Practice Quiz

**1. Which pattern separates application responsibilities into Model,
View, and Controller?**

A. Singleton\
B. MVC\
C. Factory\
D. Adapter

**2. Which pattern uses a central entry point to receive and route
requests?**

A. Front Controller\
B. Singleton\
C. Builder\
D. Observer

**3. Why is a Singleton constructor commonly private?**

A. To prevent direct construction from outside the class\
B. To make every method static\
C. To create a new object for every call\
D. To prevent inheritance in every case

**4. Which pattern centralizes the decision about which concrete object
to create?**

A. MVC\
B. Factory\
C. Front Controller\
D. Observer

**5. Passing a `StudentRepository` into a `StudentService` constructor
is an example of:**

A. Factory\
B. Singleton\
C. Dependency Injection\
D. MVC

**6. In Spring MVC, which component acts as the Front Controller?**

A. `JspWriter`\
B. `DispatcherServlet`\
C. `HttpSession`\
D. `Result.jsp`

**7. Are Dependency Injection and Singleton the same thing?**

A. Yes, always\
B. No; they solve different problems\
C. Yes, only in Spring\
D. Neither can be used in Java

### Answer key

1.  **B --- MVC**
2.  **A --- Front Controller**
3.  **A --- Prevents direct construction from outside the class**
4.  **B --- Factory**
5.  **C --- Dependency Injection**
6.  **B --- `DispatcherServlet`**
7.  **B --- They solve different problems**

------------------------------------------------------------------------

## Final takeaway

Think of the patterns through the problem each one solves:

-   **MVC:** How should application responsibilities be organized?
-   **Front Controller:** Where should requests enter and be routed?
-   **Singleton:** How can a class provide one shared instance?
-   **Factory:** Where should object-creation decisions be made?
-   **Dependency Injection:** How should a class receive the objects it
    depends on?
