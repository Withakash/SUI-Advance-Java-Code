# Lec 28: Design Patterns

**Course:** Advanced Java\
**Unit:** Unit V --- Java EE Design Patterns\
**Lecture duration:** 50 minutes\
**Main topics:** Design Patterns, MVC Design Pattern, Front Controller
Pattern

------------------------------------------------------------------------

## Learning Outcomes

By the end of this lecture, you should be able to:

-   Explain what a design pattern is and why developers use patterns.
-   Understand the difference between a design pattern and a framework.
-   Explain MVC (Model--View--Controller) using a Java web application.
-   Explain the Front Controller pattern and how it manages incoming
    requests.
-   Identify how MVC and Front Controller can work together in a
    Servlet/JSP application.
-   Design a simple request flow for a student portal.

## 1. What Is a Design Pattern?

A **design pattern** is a reusable, proven approach to a common software
design problem.

When developers build applications, they repeatedly face similar
problems: - How should the user interface be separated from business
logic? - How should incoming web requests be organized? - How can shared
behavior such as logging or authentication be handled consistently? -
How can a large application remain easy to change and test?

Experienced developers have found useful ways to solve many of these
recurring problems. These reusable approaches are called **design
patterns**.

### Simple analogy: Building a house

A house architect may reuse a proven layout for kitchens, bedrooms, and
staircases. The design is a guide, not a complete house that can be
copied without changes.

Similarly, a software design pattern is a guide for structuring code. It
is not a ready-made application or a library that automatically solves
the problem.

### Definition

> **A design pattern is a reusable template for organizing software
> components to solve a recurring design problem.**

### Why use design patterns?

1.  **Maintainability:** code is easier to understand and modify.
2.  **Reusability of ideas:** teams can apply a known solution to
    similar problems.
3.  **Separation of concerns:** each component has a clearer
    responsibility.
4.  **Communication:** developers can discuss a solution by using a
    shared name, such as MVC.
5.  **Testability:** components with clear responsibilities can often be
    tested independently.

### Important point

A design pattern is not the same as an algorithm.

-   An **algorithm** describes steps to solve a computational problem.
-   A **design pattern** describes a way to organize components and
    responsibilities in software.

A pattern is also not automatically the best choice for every project.
Using too many patterns can make simple applications unnecessarily
complicated.

## 2. Common Categories of Design Patterns

A traditional classification groups many object-oriented design patterns
into three categories.

  -----------------------------------------------------------------------
  Category                Main purpose            Examples
  ----------------------- ----------------------- -----------------------
  Creational              How objects are created Factory Method,
                                                  Abstract Factory,
                                                  Builder, Singleton

  Structural              How classes or objects  Adapter, Decorator,
                          are arranged            Facade, Proxy

  Behavioral              How objects communicate Observer, Strategy,
                          or distribute           Command, Iterator
                          responsibilities        
  -----------------------------------------------------------------------

MVC and Front Controller are commonly discussed as **architectural or
presentation-layer patterns** in web applications. They organize how a
user request moves through the application.

This lecture focuses on MVC and Front Controller because they are
directly useful in Servlet/JSP applications.

## 3. Why Do We Need Patterns in Java Web Applications?

Imagine writing a student portal using only one large Servlet.

That Servlet might: 1. Read form parameters. 2. Validate the user's
input. 3. Run SQL queries. 4. Apply business rules. 5. Decide which page
to display. 6. Generate HTML. 7. Handle error messages.

As the application grows, this becomes difficult to maintain. A small
change in the page design may accidentally affect database code.

A better design separates responsibilities.

``` text
Before: One large Servlet
+-----------------------------------+
| Read request                      |
| Validate input                    |
| Business logic                    |
| Database operations               |
| Generate HTML                     |
| Handle errors                     |
+-----------------------------------+

After: Separate responsibilities
+----------+     +----------+     +----------+
|  Model   |     |Controller|     |   View   |
| Data and |<--->| Request  |---->| JSP/HTML |
| business |     | handling |     | display  |
+----------+     +----------+     +----------+
```

This leads us to MVC.

------------------------------------------------------------------------

# Part A: MVC Design Pattern

## 4. What Is MVC?

**MVC stands for Model--View--Controller.**

MVC is a design pattern that separates an application's data and
business logic from its user interface and request-handling logic.

The three components have different responsibilities:

-   **Model:** represents application data and business rules.
-   **View:** presents information to the user.
-   **Controller:** receives and processes user requests, coordinates
    the work, and selects the response.

### Simple analogy: College result system

Suppose a student opens the result page.

-   **Model:** obtains the student's result and applies relevant
    business rules.
-   **View:** displays the student's name, subjects, and marks.
-   **Controller:** receives the request, identifies the student, calls
    the required service/model logic, and forwards to the result page.

### MVC diagram

``` text
                 User / Browser
                       |
                       | Request
                       v
                 +-----------+
                 |Controller |
                 | Servlet   |
                 +-----------+
                    |      |
       asks for data|      | selects response
                    v      v
               +--------+  +--------+
               | Model  |  |  View  |
               | Data & |  | JSP /  |
               | Rules  |  | HTML   |
               +--------+  +--------+
                    |          ^
                    | Data     | Rendered response
                    +----------+
```

The exact interactions depend on the MVC implementation. In a common
Servlet/JSP arrangement, the controller invokes application/service
code, places data in request attributes, and forwards to a JSP that
renders the page.

## 5. The Three Components of MVC in Detail

### 5.1 Model

The **Model** represents application data and business rules.

In a Java web application, model-related code may include: - Java
classes representing entities or data-transfer objects. - Service
classes containing business rules. - DAO/repository classes that
retrieve and store data. - Validation rules related to the domain.

Example: `Student.java`

``` java
public class Student {
    private int id;
    private String name;
    private double marks;

    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }
}
```

This class represents student information. A real application would
usually load the data through a service/DAO layer rather than hardcoding
it in the controller.

**Remember:** The model should not be responsible for designing the JSP
page.

### 5.2 View

The **View** is responsible for displaying information to the user.

In a traditional Servlet/JSP application, the view is often a JSP page
or another HTML-rendering template.

Example: `student.jsp`

``` jsp
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Details</title>
</head>
<body>
    <h1>Student Details</h1>

    <p>ID: ${student.id}</p>
    <p>Name: ${student.name}</p>
    <p>Marks: ${student.marks}</p>
</body>
</html>
```

This example uses JSP Expression Language (EL) to read a request-scoped
attribute named `student`.

In a real application: - The controller should provide the required
data. - The view should focus on presentation. - User-provided output
should be escaped appropriately to reduce XSS risk. - Business rules and
SQL should not be placed directly into the JSP.

### 5.3 Controller

The **Controller** receives user requests and coordinates the response.

In a Servlet/JSP application, a Servlet commonly acts as a controller.

Its responsibilities can include: - Reading request parameters. -
Checking input format. - Calling the service layer. - Placing result
data in request attributes. - Choosing a view or redirect. - Handling
expected errors.

Example: `StudentController.java`

``` java
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student")
public class StudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Demonstration data only.
        Student student = new Student(101, "Riya", 87.5);

        request.setAttribute("student", student);

        request.getRequestDispatcher("/student.jsp")
               .forward(request, response);
    }
}
```

This is a small demonstration. In a real application, the controller
should obtain the student from a service/DAO using the authenticated
user's identity and enforce record-level authorization. Do not trust a
user-supplied student ID without checking permission.

## 6. How Does MVC Work Step by Step?

Suppose a user visits:

`http://localhost:8080/StudentPortal/student`

A common Servlet/JSP MVC flow is:

1.  The browser sends a request to `/student`.
2.  The Servlet mapped to `/student` receives the request.
3.  The controller calls the appropriate service/model logic.
4.  The service obtains or processes the data.
5.  The controller stores the result in a request attribute.
6.  The controller forwards the request to `student.jsp`.
7.  The JSP generates HTML using the supplied data.
8.  The server returns the HTML to the browser.

``` text
Browser
   |
   | GET /student
   v
StudentController (Servlet)
   |
   | Calls service/model logic
   v
Student Service / DAO
   |
   | Returns Student data
   v
StudentController
   |
   | setAttribute("student", student)
   | forward("/student.jsp")
   v
student.jsp
   |
   | Generates HTML
   v
Browser displays student details
```

### Forward vs Redirect

MVC controllers often use forwarding or redirecting.

**Forward** - Uses `RequestDispatcher.forward(...)`. - Happens inside
the server. - The browser's address generally remains the same. -
Request attributes are available to the forwarded resource.

**Redirect** - Uses `response.sendRedirect(...)`. - Tells the browser to
make another request. - The browser's address changes to the redirect
target. - The original request attributes are not automatically carried
into the new request.

A common approach after a successful form submission is
**Post/Redirect/Get (PRG)**, which redirects after a POST to reduce
accidental resubmission on refresh.

## 7. MVC in a Real Student Portal

A possible project structure:

``` text
StudentPortal/
|
+-- src/main/java/
|   +-- controller/
|   |   +-- StudentController.java
|   |   +-- LoginController.java
|   |
|   +-- model/
|   |   +-- Student.java
|   |
|   +-- service/
|   |   +-- StudentService.java
|   |
|   +-- dao/
|       +-- StudentDAO.java
|
+-- src/main/webapp/
    +-- student.jsp
    +-- login.jsp
    +-- css/
        +-- style.css
```

This is a possible Maven-style structure; traditional Eclipse Dynamic
Web Projects may organize source and web content differently.

  Layer        Example responsibility
  ------------ -----------------------------------------------------
  Controller   Receives `/student` requests and chooses a response
  Service      Applies business rules and coordinates operations
  DAO          Executes parameterized SQL queries
  Model        Represents student data
  View         Displays the result using JSP/HTML

MVC does not require exactly these folders or classes. The key idea is
separation of responsibilities.

## 8. Advantages and Limitations of MVC

### Advantages

-   Separates display code from business logic.
-   Makes the application easier to maintain.
-   Supports independent testing of business logic.
-   Allows the view to change without rewriting every business rule.
-   Helps teams divide work between UI and backend development.

### Limitations

-   Requires some initial structure and planning.
-   May feel excessive for a tiny application.
-   Poorly designed controllers can become too large.
-   Separation alone does not guarantee security or good architecture.

------------------------------------------------------------------------

# Part B: Front Controller Pattern

## 9. What Is the Front Controller Pattern?

The **Front Controller** pattern provides one central entry point for
handling a set of incoming requests.

Instead of every page having completely separate request-processing
logic, requests pass through a central controller that can apply common
behavior and then route the request to the appropriate handler.

Common responsibilities include: - Request routing. - Authentication and
authorization integration. - Logging and tracing. - Common error
handling. - Shared request preparation. - Selecting the appropriate
action or controller.

In Java web applications, a Front Controller is often implemented using
a Servlet.

### Analogy: College reception desk

Imagine a college with separate offices for fees, examinations, and
admissions.

A visitor first approaches reception. Reception identifies the purpose
of the visit and directs the visitor to the correct office.

The reception desk is like the Front Controller: - It is the common
entry point. - It determines where the request should go. - It can apply
shared procedures before routing.

The reception desk does not perform every office's work itself.

## 10. Front Controller Diagram

Without a Front Controller, multiple Servlets may each repeat similar
request-handling tasks:

``` text
Browser ---> LoginServlet
Browser ---> StudentServlet
Browser ---> MarksServlet
Browser ---> AdminServlet

Each may repeat common handling:
logging, validation setup, error handling, etc.
```

With a Front Controller:

``` text
                  Browser
                     |
              Incoming request
                     |
                     v
             +----------------+
             | FrontController|
             | Central Servlet|
             +----------------+
                     |
              Identify route
                     |
          +----------+----------+
          |          |          |
          v          v          v
     LoginHandler StudentHandler AdminHandler
          |          |          |
          v          v          v
       Login view Student view Admin view
```

The exact architecture varies. A Front Controller may dispatch to other
controllers, command objects, services, or views.

## 11. How Does a Front Controller Work?

Imagine a student portal has these URLs:

-   `/app/login`
-   `/app/student`
-   `/app/marks`
-   `/app/admin`

A simplified flow:

1.  The browser sends a request to the application.
2.  The Front Controller receives the request.
3.  It determines which action or handler should process it.
4.  Shared processing runs where appropriate.
5.  The request is dispatched to the correct handler or service.
6.  The application selects a view or redirects.
7.  The response returns to the browser.

A Front Controller centralizes request handling, but **it must not
automatically trust every request**. Each protected action still needs
appropriate authentication, authorization, validation, and business-rule
checks.

## 12. Simple Front Controller Servlet Example

This educational example uses a query parameter to choose a route. In a
production application, routing should be designed carefully, and
untrusted input must never be used to load arbitrary Java classes or
files.

``` java
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/app")
public class FrontControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("student".equals(action)) {
            request.getRequestDispatcher("/student.jsp")
                   .forward(request, response);

        } else if ("login".equals(action)) {
            request.getRequestDispatcher("/login.jsp")
                   .forward(request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
```

Example URLs:

``` text
/app?action=student
/app?action=login
```

This example demonstrates routing only. It does **not** implement real
login or authorization. In a secure application, a route to student data
must obtain data from the appropriate service and enforce identity and
record ownership. Admin routes must enforce admin permissions.

A routing framework or explicit route map is often easier to maintain
than a long chain of `if/else` statements as the number of actions
grows.

## 13. MVC vs Front Controller

MVC and Front Controller are related but not identical. They solve
different design problems and can be used together.

  -----------------------------------------------------------------------
  MVC                                 Front Controller
  ----------------------------------- -----------------------------------
  Separates Model, View, and          Establishes a central entry point
  Controller responsibilities         for requests

  Focuses on separation of concerns   Focuses on centralized request
                                      handling and routing

  Often has a controller for a        A central controller may route to
  particular feature or request       feature-specific handlers

  Model represents data and business  Front Controller coordinates common
  rules; View presents results        request processing and dispatch

  Common in web application           Common as part of a web
  architecture                        application's request architecture
  -----------------------------------------------------------------------

### Can MVC and Front Controller work together?

Yes. A Front Controller can receive all requests and dispatch each one
to a feature-specific controller or handler. That handler then works
with model/service code and selects a view.

``` text
Browser
   |
   v
Front Controller Servlet
   |
   +----> Student Controller ----> Student Service / Model
   |                                  |
   |                                  v
   |                             Student JSP
   |
   +----> Login Controller ------> Authentication Service
   |                                  |
   |                                  v
   |                              Login / Home View
   |
   +----> Admin Controller ------> Admin Service
                                      |
                                      v
                                  Admin View
```

This is one possible design, not the only correct architecture.

## 14. Is a Front Controller the Same as MVC Controller?

Not necessarily.

A **controller** is a general role: it receives or coordinates requests
and decides what should happen.

A **Front Controller** is a particular pattern in which a central
component acts as the common entry point for a set of requests.

In a small application, one Servlet may perform both the Front
Controller role and some controller responsibilities. In a larger
application, the Front Controller may delegate to several controllers or
handlers.

## 15. Connection to Frameworks

Many web frameworks use ideas related to MVC and Front Controller.

For example: - Traditional Servlet/JSP applications can implement these
patterns directly. - Spring MVC uses a central `DispatcherServlet` to
route requests to appropriate handlers. - Other frameworks may use
routers or central request pipelines with similar responsibilities.

A framework provides reusable infrastructure and conventions; a design
pattern is the underlying design idea. A framework may implement or
combine multiple patterns.

## 16. Common Mistakes

**Mistake 1: Putting SQL and HTML in the same Servlet.**\
This makes changes harder. Separate data access, business rules, request
handling, and presentation.

**Mistake 2: Putting all business logic in the JSP.**\
JSP should primarily render the response, not act as the main
business-logic layer.

**Mistake 3: Thinking MVC automatically makes an application secure.**\
MVC organizes responsibilities; security still requires authentication,
authorization, safe SQL, output encoding, CSRF protection, and other
controls.

**Mistake 4: Thinking the Front Controller performs every business
operation itself.**\
Its central role is to coordinate common processing and dispatch
requests. It should delegate feature-specific work.

**Mistake 5: Trusting a route or URL parameter.**\
A request parameter such as `action=admin` is not proof that the caller
is an administrator. Enforce permissions on the server.

**Mistake 6: Creating patterns without a real need.**\
Use patterns to make code clearer, not simply to add more classes.

## 17. Quick Revision

-   **Design pattern:** Reusable approach to a recurring software design
    problem.
-   **MVC:** Model--View--Controller.
-   **Model:** Application data and domain/business rules.
-   **View:** User-facing presentation, often JSP/HTML.
-   **Controller:** Handles requests, coordinates work, and chooses the
    response.
-   **Front Controller:** A central entry point that coordinates common
    request handling and routes requests.
-   **Servlet:** Can act as an MVC controller or Front Controller in a
    Java web application.
-   **JSP:** Commonly used as the view in a traditional Servlet/JSP MVC
    application.
-   **Service:** Often holds business operations and coordinates data
    access.
-   **DAO:** Encapsulates database access.
-   **MVC + Front Controller:** Can be combined to organize a larger web
    application.

## 18. Practice Questions

### Short-answer questions

1.  Define a design pattern. Why do developers use design patterns?
2.  What is MVC? Explain the responsibilities of its three components.
3.  Why should business logic not be placed directly inside a JSP?
4.  Explain the Front Controller pattern with a diagram.
5.  Differentiate MVC and Front Controller.
6.  Can a Servlet implement the Front Controller pattern? Explain.
7.  What is the difference between a design pattern and a framework?
8.  How can MVC and Front Controller work together?

### Application-based questions

1.  A Servlet reads form fields, runs SQL, applies business rules, and
    generates a large HTML page. Explain how you would reorganize it
    using MVC.
2.  A student portal has login, student, marks, and admin routes.
    Explain how a Front Controller could route requests.
3.  A developer hides the Admin link from students but does not check
    the user's role on the server. Is the application secure? Explain.
4.  Design a folder/package structure for a Servlet/JSP student portal
    using MVC.
5.  Explain how a central request-handling component can reduce
    duplicated cross-cutting code without becoming a large class that
    contains every business operation.

## 19. Mini Practical Assignment

**Task: Build a simple Student Details application using MVC.**

Requirements: 1. Create a `Student` model class. 2. Create a Servlet
controller mapped to `/student`. 3. Use a service class or demonstration
method to obtain student data. 4. Store the data in a request attribute.
5. Forward the request to a JSP view. 6. Display the student's ID, name,
and marks. 7. Keep SQL and business rules out of the JSP. 8. Add a Front
Controller route for at least two actions, such as `student` and
`login`. 9. Handle unknown routes with an appropriate response. 10. In
the README, explain how the design could be extended with authentication
and role-based authorization.

**Submission:** Source code, a diagram of the request flow, and a short
explanation of the responsibility of each component.

------------------------------------------------------------------------

## Final Takeaway

**Design patterns help developers organize code around recurring
problems. MVC separates data/business responsibilities, request
coordination, and presentation. Front Controller centralizes the entry
point for handling and routing requests.**

In a Servlet/JSP application, these ideas can work together: a central
Servlet receives requests, feature-specific controllers coordinate
operations, service/DAO classes handle business and database work, and
JSP pages render the results.
