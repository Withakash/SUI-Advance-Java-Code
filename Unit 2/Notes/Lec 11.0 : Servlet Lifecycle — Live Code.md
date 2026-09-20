# Lec 11 : Servlet Lifecycle — Live Code

## 🎯 Learning Objectives

After this practical, you should be able to:

- Understand the complete Servlet lifecycle.
- Understand when the Servlet object is created.
- Understand when `init()` executes.
- Understand when `service()` executes.
- Understand how `service()` decides between `doGet()` and `doPost()`.
- Understand when `destroy()` executes.
- Observe the lifecycle using Eclipse Console.
- Understand why `init()` and `destroy()` normally execute once, while `service()` can execute many times.

---

# 1. What is Servlet Lifecycle?

The **Servlet Lifecycle** describes the complete journey of a Servlet:

```text
Servlet Object Created
        ↓
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
        ↓
Servlet Removed
```

The Servlet container, such as **Apache Tomcat**, controls this lifecycle.

You normally do not call:

```java
init();
service();
destroy();
```

yourself.

Tomcat calls these methods at the appropriate time.

---

# 2. Complete Lifecycle

A Servlet lifecycle can be divided into three major stages:

```text
1. Initialization
       ↓
     init()

2. Request Processing
       ↓
    service()
       ↓
 doGet() / doPost()

3. Destruction
       ↓
   destroy()
```

---

# 3. Lifecycle Flow

```text
                    Tomcat
                      |
                      ↓
              Load Servlet Class
                      |
                      ↓
              Create Servlet Object
                      |
                      ↓
                    init()
                      |
             Initialization Done
                      |
                      ↓
              HTTP Request Arrives
                      |
                      ↓
                  service()
                      |
             ┌────────┴────────┐
             ↓                 ↓
            GET               POST
             ↓                 ↓
          doGet()           doPost()
             |                 |
             └────────┬────────┘
                      |
                Send Response
                      |
                      ↓
              Servlet waits
                      |
                Next Request
                      |
                      ↓
                  service()
                      |
                    ...
                      |
              Server shutdown
              / application
                 removed
                      |
                      ↓
                  destroy()
```

---

# 4. Important Methods

The most important methods are:

| Method | Purpose | Normally Called |
|---|---|---|
| Constructor | Creates Servlet object | Once |
| `init()` | Initializes Servlet | Once |
| `service()` | Handles incoming requests | Many times |
| `doGet()` | Handles GET requests | Whenever GET arrives |
| `doPost()` | Handles POST requests | Whenever POST arrives |
| `destroy()` | Cleanup before Servlet removal | Once |

---

# 5. Create the Live Demo

Create a Dynamic Web Project in Eclipse.

Example:

```text
ServletLifecycleDemo
```

Create this Servlet:

```text
Java Resources
└── src
    └── com.demo
        └── LifecycleServlet.java
```

---

# 6. Complete Lifecycle Servlet

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

    // 1. Constructor
    public LifecycleServlet() {

        System.out.println("1. Constructor called");
    }


    // 2. init()
    @Override
    public void init() throws ServletException {

        System.out.println("2. init() called");

        // Initialization work
        System.out.println("Servlet is initialized");
    }


    // 3. service()
    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response)
                           throws ServletException, IOException {

        System.out.println("3. service() called");

        System.out.println(
            "HTTP Method: " + request.getMethod()
        );

        super.service(request, response);
    }


    // 4. doGet()
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        System.out.println("4. doGet() called");

        response.setContentType("text/html");

        response.getWriter().println(
            "<h1>GET Request</h1>"
        );

        response.getWriter().println(
            "<p>doGet() executed</p>"
        );
    }


    // 5. doPost()
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        System.out.println("4. doPost() called");

        response.setContentType("text/html");

        response.getWriter().println(
            "<h1>POST Request</h1>"
        );

        response.getWriter().println(
            "<p>doPost() executed</p>"
        );
    }


    // 6. destroy()
    @Override
    public void destroy() {

        System.out.println("5. destroy() called");

        // Cleanup work
        System.out.println("Servlet is being destroyed");
    }
}
```

---

# 7. Understanding the Constructor

```java
public LifecycleServlet() {

    System.out.println("1. Constructor called");
}
```

The constructor is called when Tomcat creates an instance of the Servlet.

Conceptually:

```text
Tomcat
   ↓
new LifecycleServlet()
   ↓
Constructor
```

You normally do not create the Servlet object yourself.

For example, you do not write:

```java
LifecycleServlet servlet =
    new LifecycleServlet();
```

Tomcat manages the Servlet object.

---

# 8. When Does the Constructor Execute?

Usually, the constructor executes when the Servlet instance is created.

Depending on Servlet configuration and container behavior, initialization may happen when the first request arrives or when the application starts.

For a basic classroom example, think:

```text
Servlet needs an instance
        ↓
Constructor
        ↓
init()
```

---

# 9. `init()` Method

```java
@Override
public void init() throws ServletException {

    System.out.println("2. init() called");
}
```

`init()` is used for **one-time initialization**.

Examples:

- Loading configuration.
- Opening a resource.
- Initializing an object.
- Preparing application-level data.
- Creating a database connection/pool in older/simple examples.

The important idea:

```text
init() → initialization
```

---

# 10. How Many Times Does `init()` Execute?

For a given Servlet instance:

```text
init()
```

is called **once** after the Servlet instance is created and before it handles requests.

Example:

```text
Constructor
    ↓
init()
    ↓
Request 1
    ↓
Request 2
    ↓
Request 3
```

Not:

```text
Request 1 → init()
Request 2 → init()
Request 3 → init()
```

---

# 11. `service()` Method

This is one of the most important methods.

```java
@Override
protected void service(HttpServletRequest request,
                       HttpServletResponse response)
                       throws ServletException, IOException {

    System.out.println("3. service() called");

    System.out.println(
        "HTTP Method: " + request.getMethod()
    );

    super.service(request, response);
}
```

`service()` receives the request and decides which HTTP-specific method should process it.

Think of `service()` as a **traffic controller**.

```text
                service()
                    |
          ┌─────────┴─────────┐
          ↓                   ↓
         GET                 POST
          ↓                   ↓
       doGet()             doPost()
```

---

# 12. Very Important: Why `super.service()`?

Our code contains:

```java
super.service(request, response);
```

This is important.

`HttpServlet` already provides the implementation of `service()`.

Its job includes dispatching based on the HTTP method.

Conceptually:

```text
GET
 ↓
doGet()

POST
 ↓
doPost()

PUT
 ↓
doPut() / method handling as applicable

DELETE
 ↓
doDelete() / method handling as applicable
```

For this beginner demonstration, focus primarily on:

```text
GET  → doGet()
POST → doPost()
```

If you override `service()` and do not call:

```java
super.service(request, response);
```

then the inherited HTTP method dispatch will not happen.

For example, this is dangerous for this demo:

```java
@Override
protected void service(...) {

    System.out.println("service called");

    // No super.service()
}
```

The request reaches your `service()` method, but `HttpServlet` will not get the opportunity to dispatch it to `doGet()` or `doPost()`.

---

# 13. `doGet()` Method

```java
@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
                     throws ServletException, IOException {

    System.out.println("4. doGet() called");

    response.setContentType("text/html");

    response.getWriter().println(
        "<h1>GET Request</h1>"
    );
}
```

`doGet()` executes when the HTTP request method is:

```text
GET
```

Example:

```text
Browser
   ↓
GET /lifecycle
   ↓
service()
   ↓
doGet()
```

---

# 14. How to Generate a GET Request

Open:

```text
http://localhost:8080/ServletLifecycleDemo/lifecycle
```

Typing a URL into the browser normally results in a GET request.

Console will show something similar to:

```text
1. Constructor called
2. init() called
Servlet is initialized
3. service() called
HTTP Method: GET
4. doGet() called
```

The exact startup timing can vary depending on how the Servlet is configured and when Tomcat initializes it.

---

# 15. `doPost()` Method

```java
@Override
protected void doPost(HttpServletRequest request,
                      HttpServletResponse response)
                      throws ServletException, IOException {

    System.out.println("4. doPost() called");

    response.setContentType("text/html");

    response.getWriter().println(
        "<h1>POST Request</h1>"
    );
}
```

`doPost()` executes when the browser sends a POST request.

A simple HTML form can generate the POST request.

---

# 16. Create HTML for POST

Create:

```text
WebContent/index.html
```

```html
<!DOCTYPE html>
<html>

<head>
    <title>Servlet Lifecycle Demo</title>
</head>

<body>

    <h1>Servlet Lifecycle Demo</h1>

    <h2>POST Request</h2>

    <form action="lifecycle" method="post">

        <input type="text"
               name="username"
               placeholder="Enter your name">

        <button type="submit">
            Send POST
        </button>

    </form>

</body>

</html>
```

Now:

```text
HTML Form
    ↓
method="post"
    ↓
POST /lifecycle
    ↓
service()
    ↓
doPost()
```

---

# 17. GET vs POST Practical Demonstration

You now have two ways to reach the same Servlet.

## GET

Open:

```text
/lifecycle
```

Flow:

```text
GET
 ↓
service()
 ↓
doGet()
```

## POST

Submit:

```html
<form action="lifecycle" method="post">
```

Flow:

```text
POST
 ↓
service()
 ↓
doPost()
```

---

# 18. Does `service()` Execute Every Time?

Yes.

Suppose you open the Servlet three times:

```text
Request 1
Request 2
Request 3
```

You can think of the execution as:

```text
Request 1
   ↓
service()
   ↓
doGet()

Request 2
   ↓
service()
   ↓
doGet()

Request 3
   ↓
service()
   ↓
doGet()
```

`service()` is involved in handling each request.

---

# 19. Does `doGet()` Execute Every Time?

Only when the request is a GET request.

For example:

```text
GET
 ↓
service()
 ↓
doGet()
```

But:

```text
POST
 ↓
service()
 ↓
doPost()
```

Therefore:

```text
service() → common request entry point

doGet()   → GET-specific processing

doPost()  → POST-specific processing
```

---

# 20. `destroy()` Method

```java
@Override
public void destroy() {

    System.out.println("5. destroy() called");

    System.out.println(
        "Servlet is being destroyed"
    );
}
```

`destroy()` is called when the Servlet container is removing the Servlet instance.

It is used for cleanup.

Examples:

- Releasing resources.
- Closing files.
- Cleaning up objects.
- Releasing resources created during initialization.

Think:

```text
init()
   ↓
Acquire / prepare resources

...

destroy()
   ↓
Release / cleanup resources
```

---

# 21. When Does `destroy()` Execute?

It can happen when:

- The web application is stopped.
- The server is stopped.
- The application is redeployed.
- The container removes the Servlet instance.

For example, when stopping Tomcat:

```text
Tomcat stopping
      ↓
destroy()
      ↓
Servlet removed
```

The exact timing is controlled by the Servlet container.

---

# 22. Important Lifecycle Rule

For one Servlet instance, the basic lifecycle is:

```text
Constructor
    ↓
init()
    ↓
service() → request 1
    ↓
service() → request 2
    ↓
service() → request 3
    ↓
...
destroy()
```

So remember:

| Method | Frequency |
|---|---|
| Constructor | Once per Servlet instance |
| `init()` | Once per Servlet instance |
| `service()` | Once per request |
| `doGet()` | Once per GET request |
| `doPost()` | Once per POST request |
| `destroy()` | Once before Servlet instance is removed |

---

# 23. Complete Execution Example

Suppose Tomcat starts the application and the Servlet is initialized.

Then:

```text
1. Constructor called
2. init() called
```

Now user sends a GET request:

```text
3. service() called
HTTP Method: GET
4. doGet() called
```

User sends another GET request:

```text
3. service() called
HTTP Method: GET
4. doGet() called
```

User submits the POST form:

```text
3. service() called
HTTP Method: POST
4. doPost() called
```

Finally, the application is stopped:

```text
5. destroy() called
Servlet is being destroyed
```

---

# 24. Complete Lifecycle Diagram

```text
             Servlet Container
                  (Tomcat)
                      |
                      ↓
            Create Servlet Object
                      |
                      ↓
                Constructor
                      |
                      ↓
                   init()
                      |
              Initialization
                   Complete
                      |
                      ↓
             ┌─────────────────┐
             │ HTTP Request    │
             └────────┬────────┘
                      ↓
                  service()
                      |
             ┌────────┴────────┐
             ↓                 ↓
            GET               POST
             ↓                 ↓
          doGet()           doPost()
             |                 |
             └────────┬────────┘
                      ↓
                HTTP Response
                      |
                      ↓
             Wait for next request
                      |
                     ...
                      |
                      ↓
             Application stops
                      |
                      ↓
                  destroy()
                      |
                      ↓
              Servlet removed
```

---

# 25. Why Is `service()` Not Usually Overridden?

In most Servlet applications, you normally write:

```java
doGet()
```

and/or:

```java
doPost()
```

You usually do not need to override `service()`.

For example:

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        response.getWriter().println(
            "<h1>Hello</h1>"
        );
    }
}
```

Tomcat and `HttpServlet` handle the request dispatching for you.

Override `service()` for teaching, special request-dispatching behavior, or advanced use cases—not for ordinary GET/POST handling.

---

# 26. One Important Correction About `service()`

The lifecycle is often simplified as:

```text
service()
 ↓
doGet()
```

But remember:

`service()` itself is a method provided by `HttpServlet`.

Its implementation checks the HTTP method and dispatches to the appropriate method.

Therefore:

```text
HTTP Request
     ↓
service()
     ↓
HTTP method checked
     ↓
doGet() / doPost()
```

---

# 27. What If `doPost()` Is Missing?

Suppose the HTML says:

```html
<form action="lifecycle" method="post">
```

but the Servlet only has:

```java
doGet()
```

Then:

```text
POST Request
     ↓
service()
     ↓
doPost()
     ↓
No custom doPost()
     ↓
HttpServlet's default handling
     ↓
HTTP 405 Method Not Allowed
```

Your `doGet()` will not automatically handle a POST request.

---

# 28. What If `doGet()` Is Missing?

Similarly, if a GET request arrives but there is no custom `doGet()` implementation:

```text
GET Request
     ↓
service()
     ↓
doGet()
     ↓
Default HttpServlet handling
```

The request does not automatically become a POST request.

---

# 29. Lifecycle vs Request Handling

Do not confuse these two ideas.

## Servlet Lifecycle

```text
Constructor
    ↓
init()
    ↓
service()
    ↓
destroy()
```

## HTTP Request Handling

Inside request processing:

```text
service()
    ↓
GET  → doGet()
POST → doPost()
```

So `doGet()` and `doPost()` are part of **request handling**, while `init()` and `destroy()` are lifecycle phases.

---

# 30. Practical Experiment

Run the application and observe the Eclipse Console.

### Experiment 1

Open:

```text
/lifecycle
```

Observe:

```text
Constructor
init()
service()
doGet()
```

### Experiment 2

Refresh the page.

Observe:

```text
service()
doGet()
```

Notice that constructor and `init()` do not normally run again for the same Servlet instance.

### Experiment 3

Submit the POST form.

Observe:

```text
service()
doPost()
```

### Experiment 4

Stop Tomcat.

Observe:

```text
destroy()
```

---

# 31. Important Observation

If you refresh the page multiple times, you may see:

```text
service()
doGet()

service()
doGet()

service()
doGet()
```

But normally you should not see:

```text
init()
init()
init()
```

for every request.

This is one of the most important lifecycle concepts.

---

# 32. Quick Revision

### Constructor

```text
Creates Servlet object
```

### `init()`

```text
One-time initialization
```

### `service()`

```text
Receives each request
```

### `doGet()`

```text
Handles GET
```

### `doPost()`

```text
Handles POST
```

### `destroy()`

```text
Cleanup before Servlet removal
```

---

# 33. One-Line Memory Trick

Remember:

```text
CREATE → INIT → SERVE → DESTROY
```

And inside **SERVE**:

```text
GET  → doGet()
POST → doPost()
```

---

# 34. Final Interview Questions

### Q1. Who manages the Servlet lifecycle?

**Answer:**

The Servlet container, such as Apache Tomcat, manages the Servlet lifecycle.

---

### Q2. Which method is called only once for initialization?

**Answer:**

```java
init()
```

for a given Servlet instance.

---

### Q3. Which method handles every incoming request?

**Answer:**

```java
service()
```

---

### Q4. Which method handles GET?

**Answer:**

```java
doGet()
```

---

### Q5. Which method handles POST?

**Answer:**

```java
doPost()
```

---

### Q6. When is `destroy()` called?

**Answer:**

When the Servlet container is removing the Servlet instance, such as during application shutdown, server shutdown, or redeployment.

---

### Q7. Should we call `init()` manually?

**Answer:**

No. The Servlet container calls it.

---

### Q8. Should we create a Servlet object using `new`?

**Answer:**

Normally no. The Servlet container creates and manages the Servlet instance.

---

### Q9. Why do we call `super.service()` when overriding `service()`?

**Answer:**

Because `HttpServlet` provides the HTTP method dispatching logic. Calling `super.service()` allows it to route GET requests to `doGet()`, POST requests to `doPost()`, and so on.

---

# 35. Final Lifecycle Summary

```text
                TOMCAT
                   |
                   ↓
           Create Servlet
                   |
                   ↓
             Constructor
                   |
                   ↓
                init()
                   |
                   ↓
          ┌────────────────┐
          │ HTTP Request   │
          └───────┬────────┘
                  ↓
               service()
                  |
          ┌───────┴───────┐
          ↓               ↓
         GET             POST
          ↓               ↓
       doGet()         doPost()
          |               |
          └───────┬───────┘
                  ↓
              Response
                  |
                  ↓
          Wait for request
                  |
                 ...
                  |
                  ↓
              destroy()
                  |
                  ↓
          Servlet removed
```

## The most important thing to remember

```text
Constructor → object creation

init()      → initialize once

service()   → receives requests

doGet()     → handles GET

doPost()    → handles POST

destroy()   → cleanup
```

The Servlet container controls this entire lifecycle.
