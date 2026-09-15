# Servlet Lifecycle, GET/POST & HTML Integration

## Topics Covered

1. Servlet Lifecycle
2. `init()` method
3. `service()` method
4. `destroy()` method
5. `doGet()` method
6. `doPost()` method
7. GET vs POST
8. How HTML connects with Servlet
9. HTML Forms and Servlet Mapping
10. `request.getParameter()`
11. Sending Response from Servlet
12. Complete Practical Servlet Lifecycle Demonstration
13. GET Form Practical
14. POST Form Practical
15. When to use GET and POST
16. Common Mistakes

---

# 1. Big Picture — What Are We Actually Building?

Before learning individual methods, understand the complete flow.

```text
                USER / BROWSER
                     |
                     |
              HTML Page / Form
                     |
                     |
              HTTP Request
             GET or POST
                     |
                     v
                APACHE TOMCAT
                     |
                     v
             SERVLET CONTAINER
                     |
                     v
             Servlet Object
                     |
                init()
                     |
                     v
                service()
                     |
              +------+------+
              |             |
             GET           POST
              |             |
              v             v
           doGet()       doPost()
              |             |
              +------+------+
                     |
                     v
              HTTP Response
                     |
                     v
                  BROWSER
```

The most important idea:

> **HTML does not directly call `doGet()` or `doPost()`.**

HTML creates an **HTTP request**.

Tomcat receives that HTTP request.

The Servlet Container determines which Servlet should handle the request.

Then `service()` routes the request to:

```text
GET  → doGet()
POST → doPost()
```

---

# 2. What is a Servlet?

A Servlet is a Java class that runs inside a Servlet Container such as Tomcat and handles HTTP requests and responses.

Example:

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

}
```

Here:

```text
HelloServlet
     |
     | mapped to
     ↓
/hello
```

So if the browser requests:

```text
http://localhost:8080/MyApp/hello
```

Tomcat knows that `/hello` belongs to `HelloServlet`.

---

# 3. What is Tomcat?

Apache Tomcat is a server that provides a Servlet Container.

The Servlet Container is responsible for:

* Creating Servlet objects
* Managing Servlet lifecycle
* Calling `init()`
* Receiving HTTP requests
* Calling `service()`
* Calling `doGet()` / `doPost()`
* Providing request and response objects
* Calling `destroy()`

Think:

```text
Tomcat
   |
   └── Servlet Container
           |
           └── Manages Servlets
```

---

# 4. Servlet Lifecycle

A Servlet has a lifecycle managed by the Servlet Container.

The basic lifecycle is:

```text
Servlet Class
     |
     v
Servlet Object Created
     |
     v
init()
     |
     v
service()
     |
     +---- GET  → doGet()
     |
     +---- POST → doPost()
     |
     v
service()
     |
     v
...
More Requests
...
     |
     v
destroy()
```

There are three major lifecycle methods:

```text
init()
service()
destroy()
```

---

# 5. `init()` Method

## Purpose

`init()` is called when the Servlet is initialized.

Example:

```java
@Override
public void init() throws ServletException {

    System.out.println("Servlet Initialized");

}
```

## Important Point

Normally, `init()` is called **once for a Servlet instance**.

It is not called for every request.

Example:

```text
First Request

Servlet created
     ↓
init()
     ↓
service()
     ↓
doGet()
```

Second request:

```text
service()
     ↓
doGet()
```

Third request:

```text
service()
     ↓
doGet()
```

So:

```text
init() → initialization
service() → request handling
```

---

# 6. What Can We Do Inside `init()`?

`init()` can be used for initialization work.

Examples:

* Loading configuration
* Initializing resources
* Reading initial configuration
* Creating objects required by the Servlet

Example:

```java
@Override
public void init() throws ServletException {

    System.out.println("Database configuration loaded");
    System.out.println("Servlet is ready");

}
```

Do not put request-specific logic inside `init()`.

For example, this is wrong conceptually:

```java
// Don't process user form data here
String username = request.getParameter("username");
```

Why?

Because `init()` is not request handling.

---

# 7. `service()` Method

`service()` is responsible for handling incoming requests.

For an `HttpServlet`, the HTTP request method determines what happens next.

Simplified flow:

```text
HTTP Request
     |
     v
service()
     |
     +----------------+
     |                |
    GET              POST
     |                |
     v                v
 doGet()           doPost()
```

Example:

```java
@Override
protected void service(HttpServletRequest request,
                       HttpServletResponse response)
                       throws ServletException, IOException {

    System.out.println("service() called");

    super.service(request, response);
}
```

### Important

Usually, we do **not** override `service()` for normal GET/POST handling.

Instead:

```java
doGet()
doPost()
```

are overridden.

---

# 8. Why Does `service()` Call `doGet()` or `doPost()`?

Suppose the browser sends:

```http
GET /hello
```

The Servlet Container receives the request.

Conceptually:

```text
GET Request
     ↓
service()
     ↓
GET detected
     ↓
doGet()
```

If the browser sends:

```http
POST /register
```

then:

```text
POST Request
     ↓
service()
     ↓
POST detected
     ↓
doPost()
```

So remember:

```text
service()
    |
    +-- GET  → doGet()
    |
    +-- POST → doPost()
```

---

# 9. `doGet()` Method

`doGet()` is used to handle HTTP GET requests.

Example:

```java
@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
                     throws ServletException, IOException {

    response.setContentType("text/html");

    response.getWriter().println(
        "<h1>Hello from GET</h1>"
    );
}
```

When a GET request arrives:

```text
Browser
   ↓
GET Request
   ↓
Tomcat
   ↓
service()
   ↓
doGet()
   ↓
Response
   ↓
Browser
```

---

# 10. `doPost()` Method

`doPost()` handles HTTP POST requests.

Example:

```java
@Override
protected void doPost(HttpServletRequest request,
                      HttpServletResponse response)
                      throws ServletException, IOException {

    response.setContentType("text/html");

    response.getWriter().println(
        "<h1>Hello from POST</h1>"
    );
}
```

Flow:

```text
Browser
   ↓
POST Request
   ↓
Tomcat
   ↓
service()
   ↓
doPost()
   ↓
Response
   ↓
Browser
```

---

# 11. `destroy()` Method

`destroy()` is called when the Servlet is taken out of service.

Example:

```java
@Override
public void destroy() {

    System.out.println("Servlet Destroyed");

}
```

Typical lifecycle:

```text
init()
   ↓
service()
   ↓
doGet()/doPost()
   ↓
service()
   ↓
doGet()/doPost()
   ↓
...
   ↓
destroy()
```

`destroy()` can be used for cleanup work.

For example:

* Closing resources
* Releasing resources
* Cleanup operations

---

# 12. Complete Servlet Lifecycle

The complete picture:

```text
              Servlet Class
                   |
                   v
          Servlet Object Created
                   |
                   v
                init()
                   |
                   |
            Servlet Ready
                   |
                   v
              service()
                   |
          +--------+--------+
          |                 |
        GET                POST
          |                 |
          v                 v
       doGet()           doPost()
          |                 |
          +--------+--------+
                   |
                   v
               Response
                   |
                   v
               Browser
                   |
                   |
             More Requests
                   |
                   v
               service()
                   |
                   v
          doGet()/doPost()
                   |
                  ...
                   |
             Application
               Stopped
                   |
                   v
               destroy()
```

---

# 13. Now Understand HTML + Servlet Connection

This is one of the most important concepts.

Students often think:

```text
HTML → Java Method
```

That is not exactly what happens.

The real flow is:

```text
HTML
 ↓
Browser
 ↓
HTTP Request
 ↓
Tomcat
 ↓
Servlet Container
 ↓
Servlet
 ↓
doGet()/doPost()
```

HTML is used to create the user interface.

The browser converts the user's action into an HTTP request.

---

# 14. HTML Link → GET Request

Suppose we have:

```html
<a href="hello">
    Click Me
</a>
```

When the user clicks it, the browser sends a GET request to:

```text
/hello
```

If the Servlet is:

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        response.setContentType("text/html");

        response.getWriter().println(
            "<h1>Hello User</h1>"
        );
    }
}
```

Then:

```text
<a href="hello">
        |
        | click
        v
Browser
        |
        | GET /hello
        v
Tomcat
        |
        v
/hello mapping
        |
        v
HelloServlet
        |
        v
doGet()
```

---

# 15. What is `action` in an HTML Form?

Consider:

```html
<form action="login" method="post">

</form>
```

The `action` tells the browser:

> Where should this form data be sent?

Here:

```html
action="login"
```

means:

```text
Send request to /login
```

If we have:

```java
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

}
```

then:

```text
HTML
 |
 | action="login"
 ↓
/login
 |
 ↓
LoginServlet
```

---

# 16. What is `method` in an HTML Form?

Example:

```html
<form action="login" method="post">
```

The `method` specifies the HTTP method.

If:

```html
method="get"
```

then:

```text
GET Request
   ↓
doGet()
```

If:

```html
method="post"
```

then:

```text
POST Request
   ↓
doPost()
```

Therefore:

```text
HTML                         Servlet

method="get"      →          doGet()

method="post"     →          doPost()
```

This is the key connection.

---

# 17. HTML Form + GET

Example:

```html
<form action="hello" method="get">

    <input type="text"
           name="username">

    <button type="submit">
        Submit
    </button>

</form>
```

Suppose the user enters:

```text
Akash
```

The browser sends something conceptually like:

```text
GET /hello?username=Akash
```

Then:

```text
GET
 ↓
service()
 ↓
doGet()
```

---

# 18. How Does Servlet Get the HTML Form Data?

HTML:

```html
<input type="text" name="username">
```

The important part is:

```html
name="username"
```

In Servlet:

```java
String username =
    request.getParameter("username");
```

The names must match.

### HTML

```html
name="username"
```

### Java

```java
request.getParameter("username");
```

Therefore:

```text
HTML input
name="username"
       |
       v
HTTP Request
       |
       v
request.getParameter("username")
       |
       v
Java variable
```

---

# 19. Complete GET Example

## Step 1 — HTML

Create:

```text
WebContent/index.html
```

Code:

```html
<!DOCTYPE html>
<html>

<head>
    <title>GET Demo</title>
</head>

<body>

    <h1>GET Request Demo</h1>

    <form action="hello" method="get">

        <label>Enter Name:</label>

        <input type="text"
               name="username">

        <button type="submit">
            Send GET
        </button>

    </form>

</body>

</html>
```

---

## Step 2 — Servlet

Create:

```text
HelloServlet.java
```

```java
package com.demo;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        String username =
            request.getParameter("username");

        response.setContentType("text/html");

        response.getWriter().println(
            "<h1>Hello " + username + "</h1>"
        );
    }
}
```

---

# 20. Understand the Complete GET Flow

Suppose the user enters:

```text
Akash
```

and clicks:

```text
Send GET
```

The flow is:

```text
                index.html
                    |
                    |
          <form method="get">
                    |
                    v
                 Browser
                    |
                    |
        GET /hello?username=Akash
                    |
                    v
                 Tomcat
                    |
                    v
            Servlet Container
                    |
                    v
                service()
                    |
                    v
                 doGet()
                    |
                    v
       request.getParameter()
                    |
                    v
                "Akash"
                    |
                    v
                Response
                    |
                    v
                 Browser
```

---

# 21. Why Does the Data Appear in the URL?

GET sends form parameters as part of the URL query string.

Example:

```text
http://localhost:8080/MyApp/hello?username=Akash
```

Here:

```text
/hello
```

is the path.

And:

```text
?username=Akash
```

is the query string.

Structure:

```text
URL
 |
 +-- Path
 |    |
 |    /hello
 |
 +-- Query String
      |
      username=Akash
```

---

# 22. GET With Multiple Parameters

HTML:

```html
<form action="student" method="get">

    <input type="text"
           name="name">

    <input type="text"
           name="course">

    <input type="number"
           name="age">

    <button type="submit">
        Submit
    </button>

</form>
```

Suppose:

```text
name   = Akash
course = Java
age    = 23
```

The URL may become:

```text
/student?name=Akash&course=Java&age=23
```

Servlet:

```java
String name =
    request.getParameter("name");

String course =
    request.getParameter("course");

String age =
    request.getParameter("age");
```

---

# 23. Complete POST Example

Now change the form:

```html
<form action="hello" method="post">

    <label>Enter Name:</label>

    <input type="text"
           name="username">

    <button type="submit">
        Send POST
    </button>

</form>
```

Notice:

```html
method="post"
```

Now the request goes to:

```text
service()
    ↓
doPost()
```

---

# 24. POST Servlet

```java
@Override
protected void doPost(HttpServletRequest request,
                      HttpServletResponse response)
                      throws ServletException, IOException {

    String username =
        request.getParameter("username");

    response.setContentType("text/html");

    response.getWriter().println(
        "<h1>Hello " + username + "</h1>"
    );
}
```

Notice something important:

The code for reading the parameter is the same:

```java
request.getParameter("username");
```

The major difference is how the HTTP request is sent and which method handles it:

```text
GET
 ↓
doGet()

POST
 ↓
doPost()
```

---

# 25. GET vs POST — The Core Difference

## GET

GET is generally used when the client wants to **retrieve/read data**.

Examples:

```text
Search
View product
View student
View profile
Filter records
Pagination
```

Example:

```text
/products?id=101
```

---

## POST

POST is generally used when the client wants to **submit/send data to the server for processing**.

Examples:

```text
Registration
Login
Create student
Create product
Submit application
Upload data
Create order
```

Example:

```text
POST /register
```

with data in the request body.

---

# 26. GET vs POST Table

| Feature                    | GET                                      | POST                                         |
| -------------------------- | ---------------------------------------- | -------------------------------------------- |
| Main purpose               | Retrieve data                            | Submit/process data                          |
| Servlet method             | `doGet()`                                | `doPost()`                                   |
| Data location              | URL query string                         | Request body                                 |
| Visible in URL             | Yes                                      | No                                           |
| Bookmarkable               | Usually yes                              | Generally no                                 |
| Browser refresh            | Usually repeats GET                      | May ask to resubmit                          |
| Suitable for search        | Yes                                      | Usually no                                   |
| Suitable for login form    | Usually no                               | Yes                                          |
| Suitable for registration  | Usually no                               | Yes                                          |
| Suitable for creating data | No                                       | Yes                                          |
| Sensitive data             | Don't put secrets in URL                 | Better than GET, but HTTPS is still required |
| Idempotency                | Generally intended to be safe/idempotent | Not necessarily                              |

---

# 27. Important: POST Does NOT Mean Secure

This is a common misconception.

Some students think:

```text
GET = insecure
POST = secure
```

This is WRONG.

POST does not encrypt data.

For secure communication, use:

```text
HTTPS
```

Correct understanding:

```text
GET + HTTP
    ↓
Not encrypted

POST + HTTP
    ↓
Not encrypted

GET + HTTPS
    ↓
Encrypted in transit

POST + HTTPS
    ↓
Encrypted in transit
```

So for login:

```text
POST + HTTPS
```

is the appropriate combination.

---

# 28. When Should I Use GET?

Use GET when the request is mainly asking:

> "Give me something."

Examples:

### Search

```text
/search?keyword=java
```

### Product

```text
/product?id=101
```

### Student

```text
/student?id=50
```

### Filtering

```text
/products?category=laptop
```

### Pagination

```text
/products?page=2
```

These are naturally represented as URLs.

---

# 29. When Should I Use POST?

Use POST when the request is mainly asking:

> "Here is some data. Process it."

Examples:

### Registration

```text
Name
Email
Password
Course
```

### Login

```text
Username
Password
```

### Create Student

```text
Name
Email
Course
```

### Create Product

```text
Name
Price
Category
```

### Submit Application

```text
Name
Education
Resume
Experience
```

---

# 30. Easy Decision Rule

Use this mental model:

```text
Do I want to READ/GET data?
          |
          YES
          ↓
         GET
          |
          ↓
       doGet()
```

If:

```text
Do I want to SUBMIT/CREATE/PROCESS data?
          |
          YES
          ↓
         POST
          |
          ↓
       doPost()
```

This isn't a complete HTTP specification, but it's a useful beginner rule.

---

# 31. HTML → Servlet Mapping

This is the most important practical relationship.

### HTML

```html
<form action="student" method="post">
```

### Servlet

```java
@WebServlet("/student")
public class StudentServlet extends HttpServlet {
```

These two connect because:

```text
action="student"
        |
        v
/student
        |
        v
@WebServlet("/student")
```

Then:

```text
method="post"
        |
        v
POST
        |
        v
doPost()
```

Complete:

```text
<form
    action="student"
    method="post">

        ↓

/student

        ↓

StudentServlet

        ↓

doPost()
```

---

# 32. `name` Attribute Connects HTML to Java

This is the second important connection.

HTML:

```html
<input type="text" name="username">
```

Servlet:

```java
request.getParameter("username");
```

The connection is:

```text
name="username"
       |
       v
HTTP Parameter
       |
       v
getParameter("username")
```

If HTML says:

```html
name="studentName"
```

then Java must use:

```java
request.getParameter("studentName");
```

This will NOT work:

```java
request.getParameter("username");
```

because the names don't match.

---

# 33. Complete Registration Practical

Now combine everything.

## HTML

```html
<!DOCTYPE html>
<html>

<head>
    <title>Student Registration</title>
</head>

<body>

    <h1>Student Registration</h1>

    <form action="register" method="post">

        <label>Name:</label>
        <input type="text"
               name="name">
        <br><br>

        <label>Email:</label>
        <input type="email"
               name="email">
        <br><br>

        <label>Age:</label>
        <input type="number"
               name="age">
        <br><br>

        <label>Course:</label>
        <input type="text"
               name="course">
        <br><br>

        <button type="submit">
            Register
        </button>

    </form>

</body>

</html>
```

---

# 34. Registration Servlet

```java
package com.demo;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        String name =
            request.getParameter("name");

        String email =
            request.getParameter("email");

        String age =
            request.getParameter("age");

        String course =
            request.getParameter("course");

        response.setContentType("text/html");

        response.getWriter().println(
            "<h1>Registration Successful</h1>"
        );

        response.getWriter().println(
            "<p>Name: " + name + "</p>"
        );

        response.getWriter().println(
            "<p>Email: " + email + "</p>"
        );

        response.getWriter().println(
            "<p>Age: " + age + "</p>"
        );

        response.getWriter().println(
            "<p>Course: " + course + "</p>"
        );
    }
}
```

---

# 35. Registration Flow

Suppose the user enters:

```text
Name   → Akash
Email  → akash@gmail.com
Age    → 23
Course → B.Tech
```

The flow becomes:

```text
                 HTML FORM
                     |
                     |
               User clicks
                REGISTER
                     |
                     v
               Browser
                     |
                     |
             POST /register
                     |
                     v
                TOMCAT
                     |
                     v
           Servlet Container
                     |
                     v
                 service()
                     |
                     v
                 doPost()
                     |
                     v
          request.getParameter()
                     |
          +----------+----------+
          |          |          |
         name       email      age
          |          |          |
          +----------+----------+
                     |
                     v
                 Java Code
                     |
                     v
              HTTP Response
                     |
                     v
                  Browser
```

---

# 36. Practical Servlet Lifecycle Demonstration

Create:

```text
LifecycleServlet.java
```

```java
package com.demo;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/lifecycle")
public class LifecycleServlet extends HttpServlet {

    // ============================
    // 1. INIT
    // ============================

    @Override
    public void init() throws ServletException {

        System.out.println("========== INIT ==========");
        System.out.println("Servlet initialized");
    }


    // ============================
    // 2. SERVICE
    // ============================

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response)
                           throws ServletException, IOException {

        System.out.println("========== SERVICE ==========");

        System.out.println(
            "HTTP Method: " + request.getMethod()
        );

        super.service(request, response);
    }


    // ============================
    // 3. GET
    // ============================

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        System.out.println("========== DO GET ==========");

        response.setContentType("text/html");

        response.getWriter().println(
            "<h1>GET Request Received</h1>"
        );
    }


    // ============================
    // 4. POST
    // ============================

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        System.out.println("========== DO POST ==========");

        response.setContentType("text/html");

        response.getWriter().println(
            "<h1>POST Request Received</h1>"
        );
    }


    // ============================
    // 5. DESTROY
    // ============================

    @Override
    public void destroy() {

        System.out.println("========== DESTROY ==========");
        System.out.println("Servlet destroyed");
    }
}
```

---

# 37. Test the Lifecycle

Start Tomcat.

Open:

```text
http://localhost:8080/YourProjectName/lifecycle
```

You should see the lifecycle in the Eclipse Console.

Conceptually:

```text
========== INIT ==========
Servlet initialized

========== SERVICE ==========
HTTP Method: GET

========== DO GET ==========
```

Refresh the page.

You should see:

```text
========== SERVICE ==========
HTTP Method: GET

========== DO GET ==========
```

Notice:

```text
init()
```

was not called again.

---

# 38. Test POST

Create another HTML form:

```html
<form action="lifecycle" method="post">

    <button type="submit">
        Send POST
    </button>

</form>
```

Click the button.

Console:

```text
========== SERVICE ==========
HTTP Method: POST

========== DO POST ==========
```

Now you have demonstrated:

```text
GET
 ↓
service()
 ↓
doGet()

POST
 ↓
service()
 ↓
doPost()
```

---

# 39. Test `destroy()`

Stop Tomcat.

The Servlet Container takes the Servlet out of service.

You should see:

```text
========== DESTROY ==========
Servlet destroyed
```

So the complete practical demonstration becomes:

```text
START SERVER
     |
     v
   init()
     |
     v
USER SENDS GET
     |
     v
 service()
     |
     v
 doGet()
     |
     v
USER SENDS POST
     |
     v
 service()
     |
     v
 doPost()
     |
     v
STOP SERVER
     |
     v
 destroy()
```

---

# 40. Complete Architecture to Remember

```text
                         CLIENT
                           |
                           |
                    Browser / HTML
                           |
                           |
                     HTTP Request
                     GET / POST
                           |
                           v
                    APACHE TOMCAT
                           |
                           v
                  SERVLET CONTAINER
                           |
                           v
                    Servlet Object
                           |
                           v
                         init()
                           |
                           v
                       service()
                           |
                 +---------+---------+
                 |                   |
                GET                 POST
                 |                   |
                 v                   v
              doGet()             doPost()
                 |                   |
                 +---------+---------+
                           |
                           v
                      HTTP Response
                           |
                           v
                        Browser
                           |
                           |
                     More Requests
                           |
                           v
                       service()
                           |
                          ...
                           |
                     App Shutdown
                           |
                           v
                       destroy()
```

---

# 41. Important Terms

| Term              | Meaning                                |
| ----------------- | -------------------------------------- |
| Servlet           | Java class that handles web requests   |
| Tomcat            | Servlet container/server               |
| Servlet Container | Manages Servlet lifecycle and requests |
| `init()`          | Initializes Servlet                    |
| `service()`       | Handles incoming request               |
| `doGet()`         | Handles GET request                    |
| `doPost()`        | Handles POST request                   |
| `destroy()`       | Performs Servlet cleanup               |
| `request`         | Contains client request information    |
| `response`        | Used to send response to client        |
| `action`          | URL where HTML form sends request      |
| `method`          | HTTP method used by form               |
| `name`            | Parameter name sent by form            |
| `getParameter()`  | Reads form/request parameter           |

---

# 42. Three Connections You Must Remember

There are three critical connections between HTML and Servlet.

## Connection 1 — `action`

```html
<form action="register">
```

connects to:

```java
@WebServlet("/register")
```

---

## Connection 2 — `method`

```html
method="get"
```

connects to:

```java
doGet()
```

and:

```html
method="post"
```

connects to:

```java
doPost()
```

---

## Connection 3 — `name`

HTML:

```html
<input name="username">
```

connects to:

```java
request.getParameter("username");
```

Remember this:

```text
ACTION  → WHICH SERVLET?

METHOD  → WHICH HTTP HANDLER?

NAME    → WHICH DATA?
```

This is probably the easiest way to teach the HTML–Servlet connection.

---

# 43. Common Mistakes

## Mistake 1 — Wrong Servlet Mapping

HTML:

```html
<form action="register">
```

Servlet:

```java
@WebServlet("/registration")
```

These don't match.

Correct:

```html
<form action="register">
```

```java
@WebServlet("/register")
```

---

## Mistake 2 — Wrong `name`

HTML:

```html
<input name="username">
```

Java:

```java
request.getParameter("user");
```

Wrong.

Correct:

```java
request.getParameter("username");
```

---

## Mistake 3 — Using `doGet()` for POST

HTML:

```html
<form action="login" method="post">
```

But Servlet only has:

```java
doGet()
```

Then `doGet()` won't handle that POST request.

Use:

```java
doPost()
```

---

## Mistake 4 — Thinking `init()` Runs for Every Request

Wrong:

```text
Request
 ↓
init()
 ↓
doGet()
```

every time.

Correct:

```text
Servlet creation
 ↓
init()       ← once
 ↓
service()
 ↓
doGet()
```

Subsequent requests:

```text
service()
 ↓
doGet()
```

---

## Mistake 5 — Thinking Browser Directly Calls Java

Wrong mental model:

```text
HTML → Java Method
```

Correct:

```text
HTML
 ↓
Browser
 ↓
HTTP Request
 ↓
Tomcat
 ↓
Servlet Container
 ↓
service()
 ↓
doGet()/doPost()
```

---

## Mistake 6 — Thinking POST Encrypts Data

Wrong:

```text
POST = Secure
```

Correct:

```text
HTTPS = Encryption in transit
```

POST is about how data is submitted, not encryption.

---

# 44. Final Mental Model

If you remember only one thing from this entire topic, remember this:

```text
              HTML
               |
               |
          action + method
               |
               v
            Browser
               |
               |
          HTTP Request
               |
               v
            Tomcat
               |
               v
      Servlet Container
               |
               v
          service()
               |
        +------+------+
        |             |
       GET           POST
        |             |
        v             v
     doGet()       doPost()
        |             |
        +------+------+
               |
               v
           Response
               |
               v
            Browser
```

And the lifecycle around it:

```text
             Servlet Created
                    |
                    v
                  init()
                    |
                    v
               service()
                    |
             GET / POST
                    |
                    v
             doGet/doPost
                    |
                    v
               Response
                    |
                    v
              More Requests
                    |
                   ...
                    |
                    v
                destroy()
```

---

# 45. Recommended Learning Order

Do not memorize the methods independently.

Learn them in this exact order:

```text
1. What is Servlet?
        ↓
2. What is Tomcat / Servlet Container?
        ↓
3. Servlet Lifecycle
        ↓
4. init()
        ↓
5. service()
        ↓
6. doGet()
        ↓
7. doPost()
        ↓
8. destroy()
        ↓
9. HTML
        ↓
10. HTML Form
        ↓
11. action
        ↓
12. method
        ↓
13. input name
        ↓
14. request.getParameter()
        ↓
15. GET Practical
        ↓
16. POST Practical
        ↓
17. GET vs POST
        ↓
18. Complete Registration Form
```

---

# 46. One-Line Revision

```text
init()
→ Prepare Servlet

service()
→ Receive HTTP request

doGet()
→ Handle GET

doPost()
→ Handle POST

destroy()
→ Cleanup Servlet
```

HTML connection:

```text
action
→ Which Servlet?

method
→ GET or POST?

name
→ Parameter name?

getParameter()
→ Read parameter in Java
```

Final flow:

```text
HTML
 ↓
Browser
 ↓
HTTP Request
 ↓
Tomcat
 ↓
Servlet Container
 ↓
service()
 ↓
doGet()/doPost()
 ↓
Response
 ↓
Browser
```

# 47. Suggested Classroom Practical

Build the practical in this order:

### Practical 1 — Lifecycle

Create:

```text
LifecycleServlet
```

Demonstrate:

```text
init()
service()
doGet()
destroy()
```

### Practical 2 — GET

Create:

```text
index.html
```

with:

```html
<form action="hello" method="get">
```

Read:

```java
request.getParameter()
```

Observe the URL.

### Practical 3 — POST

Change:

```html
method="post"
```

Implement:

```java
doPost()
```

Observe the difference.

### Practical 4 — Registration

Build:

```text
Name
Email
Age
Course
     ↓
Register
     ↓
doPost()
     ↓
Display submitted data
```

This final practical ties the entire topic together.
