# Lecture — Servlet Container, First Servlet, Mapping & Tomcat Deployment

## Lecture Goal

By the end of this lecture, students should be able to:

1. Explain the role of a Servlet Container.
2. Explain the HTTP Request–Response model.
3. Create a basic Servlet using `HttpServlet`.
4. Map a Servlet to a URL using `@WebServlet`.
5. Deploy and run a Dynamic Web Project on Apache Tomcat.
6. Identify which solution/configuration to use for common Servlet/Tomcat problems.
7. Debug common errors such as `404`, red imports, deployment failures, and wrong URLs.

> **Central story:** Browser sends a request → Tomcat receives it → Servlet Container finds the mapped Servlet → Servlet processes the request → response goes back to the browser.

---

# 1. Servlet Container and Request–Response Model

## 1.1 What is a Servlet?

A Servlet is a Java class used to handle requests and generate responses in a web application.

A Servlet is still a Java class, but instead of being started through a normal `main()` method, it is managed by a Servlet Container.

```text
Normal Java Application
        ↓
      main()
        ↓
   Program starts


Servlet Application
        ↓
Browser Request
        ↓
Tomcat / Servlet Container
        ↓
Servlet
        ↓
HTTP Response
        ↓
Browser
```

### Important Point

> A Servlet is not a different programming language. It is a Java class that uses the Servlet API and is managed by a Servlet Container.

---

# 2. What is a Servlet Container?

A Servlet Container is the part of a web server/application server that manages Servlets.

In our practical setup:

```text
Apache Tomcat
      ↓
Servlet Container
      ↓
Manages Servlet
      ↓
Receives HTTP Request
      ↓
Finds correct Servlet
      ↓
Calls Servlet method
      ↓
Creates HTTP Response
```

### Main responsibilities

The Servlet Container:

- Loads Servlets.
- Creates Servlet objects when required.
- Maps URLs to Servlets.
- Receives HTTP requests.
- Calls appropriate Servlet methods.
- Provides request and response objects.
- Manages Servlet lifecycle.
- Sends the generated response to the client.
- Manages deployed web applications.

> Lifecycle details such as `init()`, `service()`, and `destroy()` can be covered in the next lecture.

---

# 3. Request–Response Model

Web applications generally follow:

```text
CLIENT
  |
  | HTTP Request
  ↓
SERVER
  |
  | Process Request
  ↓
APPLICATION / SERVLET
  |
  | HTTP Response
  ↓
CLIENT
```

For our Servlet application:

```text
Browser
   |
   | GET /MyFirstServlet/hello
   ↓
Tomcat
   |
   ↓
Servlet Container
   |
   | URL Mapping
   ↓
HelloServlet
   |
   ↓
doGet()
   |
   | HTTP Response
   ↓
Browser
```

---

# 4. Request vs Response

## Request

The request represents information coming **from the client to the server**.

```java
HttpServletRequest request
```

Think:

```text
Browser
   ↓
Request
   ↓
Server
```

The request can later contain information such as:

```java
request.getParameter("name");
request.getRequestURI();
request.getMethod();
```

For this lecture, remember:

> **Request = information coming from the client.**

---

## Response

The response represents information going **from the server back to the client**.

```java
HttpServletResponse response
```

Think:

```text
Server
   ↓
Response
   ↓
Browser
```

For this lecture, remember:

> **Response = information sent back to the client.**

### Easy memory trick

```text
Request  → Client → Server
Response → Server → Client
```

---

# 5. Creating the First Servlet

We will use an Eclipse **Dynamic Web Project**, not a Maven project.

## Recommended setup

```text
JDK
 ↓
Eclipse IDE for Enterprise Java and Web Developers
 ↓
Apache Tomcat 10.1
 ↓
Dynamic Web Project
 ↓
Dynamic Web Module 6.0
 ↓
Tomcat v10.1 Targeted Runtime
 ↓
Jakarta Servlet API
```

> With Tomcat 10.1, use `jakarta.servlet.*`, not `javax.servlet.*`.

---

# 6. Create a Dynamic Web Project

In Eclipse:

```text
File
 → New
 → Dynamic Web Project
```

Example project name:

```text
MyFirstServlet
```

Select the configured runtime:

```text
Apache Tomcat v10.1
```

If Eclipse asks for Dynamic Web Module:

```text
6.0
```

Then finish the project creation.

---

# 7. Create the Servlet

Inside the Java source folder, create a package:

```text
com.myFirstCode
```

Create:

```text
HelloServlet.java
```

Basic code:

```java
package com.myFirstCode;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<h1>Hello Students!</h1>");
        out.println("<p>Welcome to Advanced Java.</p>");
    }
}
```

---

# 8. Understand the Code Top-to-Bottom

## 8.1 Package

```java
package com.myFirstCode;
```

This is normal Java.

It defines the package in which the class exists.

---

## 8.2 Servlet API Imports

Important imports:

```java
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
```

These classes are provided by the Servlet API.

### Tomcat version rule

```text
Tomcat 9 and older
        ↓
javax.servlet

Tomcat 10+
        ↓
jakarta.servlet
```

For our setup:

```text
Tomcat 10.1
   +
Jakarta Servlet API
   +
jakarta.servlet.*
```

---

# 9. `extends HttpServlet`

```java
public class HelloServlet extends HttpServlet
```

`extends` represents inheritance in Java.

`HttpServlet` is a Servlet API class.

Therefore:

```text
HelloServlet
     |
     | extends
     ↓
HttpServlet
```

By extending `HttpServlet`, our Java class becomes an HTTP Servlet.

Compare:

```java
class Student {
}
```

This is an ordinary Java class.

But:

```java
class HelloServlet extends HttpServlet {
}
```

This is a Servlet class.

### Important classroom statement

> **Our Servlet is a Java class that extends `HttpServlet` and is managed by the Servlet Container.**

---

# 10. `@WebServlet("/hello")`

This is the Servlet mapping.

```java
@WebServlet("/hello")
```

It tells the Servlet Container:

```text
When request URL = /hello
            ↓
Run HelloServlet
```

Mapping:

```text
/hello
   ↓
HelloServlet
```

For example:

```java
@WebServlet("/student")
public class StudentServlet extends HttpServlet {
}
```

means:

```text
/student
   ↓
StudentServlet
```

---

# 11. Why Do We Need Mapping?

Suppose our application has:

```text
HelloServlet
StudentServlet
LoginServlet
ProductServlet
```

If the browser requests:

```text
/hello
```

Tomcat needs to know which Servlet should handle it.

Mapping solves this:

```text
/hello      → HelloServlet
/student    → StudentServlet
/login      → LoginServlet
/product    → ProductServlet
```

Therefore:

> **Servlet Mapping connects a URL pattern to a Servlet class.**

---

# 12. `doGet()`

```java
@Override
protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
```

For this lecture:

> `doGet()` is called when the Servlet handles an HTTP GET request.

When the student enters:

```text
http://localhost:8080/MyFirstServlet/hello
```

the browser normally sends a GET request.

Conceptually:

```text
Browser
   |
   | GET /hello
   ↓
Tomcat
   |
   ↓
HelloServlet
   |
   ↓
doGet()
```

Do not overload the first lecture with the complete Servlet lifecycle or detailed GET vs POST discussion.

---

# 13. `HttpServletRequest`

```java
HttpServletRequest request
```

Represents the HTTP request.

```text
Browser
   |
   | Request
   ↓
HttpServletRequest
```

Later we can use:

```java
request.getParameter("name");
request.getRequestURI();
request.getMethod();
```

For now:

> **Request = data/information coming from the browser.**

---

# 14. `HttpServletResponse`

```java
HttpServletResponse response
```

Represents the HTTP response that the Servlet sends back.

```text
Servlet
   |
   | Response
   ↓
Browser
```

For now:

> **Response = data/information sent from the server to the browser.**

---

# 15. `response.setContentType()`

```java
response.setContentType("text/html");
```

This tells the browser what type of content it is receiving.

Here:

```text
text/html
```

means the response should be interpreted as HTML.

Example:

```java
response.setContentType("text/plain");
```

means plain text.

For our first Servlet:

```java
response.setContentType("text/html");
```

is appropriate because we are generating HTML.

---

# 16. `PrintWriter`

```java
PrintWriter out = response.getWriter();
```

The response provides a writer through which we can write response content.

Conceptually:

```text
response
   ↓
getWriter()
   ↓
PrintWriter
   ↓
out
```

Then:

```java
out.println("Hello");
```

writes content to the HTTP response.

---

# 17. Generating HTML from Java

```java
out.println("<h1>Hello Students!</h1>");
out.println("<p>Welcome to Advanced Java.</p>");
```

The Servlet generates HTML.

```text
Java Servlet
     ↓
Generates HTML
     ↓
HTTP Response
     ↓
Browser
     ↓
Rendered Web Page
```

The browser receives HTML and renders it.

---

# 18. Complete Request–Response Flow

This is the most important diagram for students.

```text
                 BROWSER
                    |
                    | HTTP GET Request
                    | /MyFirstServlet/hello
                    ↓
              APACHE TOMCAT
                    |
                    ↓
           SERVLET CONTAINER
                    |
                    | Finds mapping
                    ↓
           @WebServlet("/hello")
                    |
                    ↓
             HelloServlet
                    |
                    ↓
                 doGet()
                    |
                    | Creates response
                    ↓
             HTTP RESPONSE
                    |
                    ↓
                 BROWSER
                    |
                    ↓
          Hello Students!
```

Students should understand this flow before memorizing Servlet API classes.

---

# 19. URL Breakdown

Example:

```text
http://localhost:8080/MyFirstServlet/hello
```

Break it into:

```text
http://
   ↓
Protocol

localhost
   ↓
Server / local computer

8080
   ↓
Tomcat HTTP port

MyFirstServlet
   ↓
Application / Context Path

hello
   ↓
Servlet Mapping
```

### Summary table

| URL Part | Meaning |
|---|---|
| `http://` | HTTP protocol |
| `localhost` | This computer |
| `8080` | Tomcat HTTP port |
| `MyFirstServlet` | Web application / context path |
| `/hello` | Servlet URL mapping |

---

# 20. Deploying the Application on Tomcat

Creating a Servlet class is not enough.

The application must be running on Tomcat.

In Eclipse:

```text
Right-click Project
 → Run As
 → Run on Server
```

Select:

```text
Apache Tomcat v10.1
```

Eclipse will build/deploy the application and start it through Tomcat.

Then open:

```text
http://localhost:8080/MyFirstServlet/hello
```

Expected response:

```text
Hello Students!

Welcome to Advanced Java.
```

---

# 21. Eclipse vs Tomcat

Students commonly confuse these two.

## Eclipse

Eclipse is the development environment.

It is used for:

- Writing Java code.
- Creating projects.
- Compiling/building.
- Editing configuration.
- Starting the server from the IDE.

## Tomcat

Tomcat is the Servlet container/server used to run the web application.

It handles:

- HTTP requests.
- Servlet mapping.
- Servlet execution.
- HTTP responses.
- Web application deployment.
- Servlet lifecycle management.

Simple comparison:

```text
Eclipse
   ↓
Development

Tomcat
   ↓
Execution / Web Runtime
```

---

# 22. Why Is There No `main()`?

Students coming from Core Java will ask:

> "Sir, where is `main()`?"

Normal Java:

```text
Class
  ↓
main()
  ↓
Program starts
```

Servlet:

```text
Browser
  ↓
HTTP Request
  ↓
Tomcat
  ↓
Servlet Container
  ↓
Mapped Servlet
  ↓
doGet() / doPost()
```

We do not normally write a `main()` method for the Servlet because Tomcat manages the Servlet.

> **Tomcat is responsible for managing the Servlet application and invoking the appropriate Servlet methods.**

---

# 23. Practical Demonstration — Change the Mapping

Start with:

```java
@WebServlet("/hello")
```

URL:

```text
http://localhost:8080/MyFirstServlet/hello
```

Then change:

```java
@WebServlet("/student")
```

Now the URL becomes:

```text
http://localhost:8080/MyFirstServlet/student
```

The important relationship is:

```text
@WebServlet("/student")
        ↓
URL Mapping
        ↓
/student
        ↓
Student/Servlet class
```

---

# 24. Practical Demonstration — Wrong URL

Suppose the Servlet is:

```java
@WebServlet("/hello")
```

Correct:

```text
http://localhost:8080/MyFirstServlet/hello
```

Wrong:

```text
http://localhost:8080/MyFirstServlet/student
```

Possible result:

```text
404 Not Found
```

Why?

Because:

```text
/student
   ↓
No matching Servlet mapping
```

This does **not automatically mean Tomcat is broken**.

Always separate:

```text
Server problem
vs
Application problem
vs
URL/mapping problem
```

---

# 25. Problem → Which Solution Should I Use?

This section is important for students because beginners often change random settings instead of identifying the actual problem.

## Problem 1 — `HttpServlet` is red

### Symptom

```java
import jakarta.servlet.http.HttpServlet;
```

is red.

Or:

```java
public class HelloServlet extends HttpServlet
```

shows an error.

### Most likely reason

The Servlet API is missing from the project's classpath.

### Solution

Check:

```text
Project
 → Properties
 → Targeted Runtimes
```

Make sure:

```text
Apache Tomcat v10.1
```

is selected.

Then check:

```text
Project
 → Properties
 → Java Build Path
 → Libraries
```

Look for the Tomcat runtime/library.

### Do NOT

Do not immediately download random JAR files from the internet and add them manually.

First fix the project runtime.

---

# 26. Problem 2 — All `jakarta.servlet` imports are red

Example:

```java
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
```

Everything is red.

### Diagnosis

This strongly suggests the Servlet API is unavailable to the project.

### Solution

Check:

```text
Project
 → Properties
 → Targeted Runtimes
 → Apache Tomcat v10.1
```

Then:

```text
Project
 → Properties
 → Java Build Path
 → Libraries
```

After correcting the runtime:

```text
Project
 → Clean
```

If required, restart Eclipse.

---

# 27. Problem 3 — `jakarta` is red but `javax` works in an old tutorial

### Reason

The tutorial and your project may be using different Servlet generations.

### Rule

```text
Tomcat 9 or older
      ↓
javax.servlet.*

Tomcat 10+
      ↓
jakarta.servlet.*
```

For this course:

```text
Tomcat 10.1
      ↓
jakarta.servlet.*
```

### Solution

Do not mix:

```java
javax.servlet.*
```

with a Tomcat 10.1 Jakarta setup.

Use:

```java
jakarta.servlet.*
```

---

# 28. Problem 4 — `@WebServlet` is red

### Possible reason

Servlet API is not available.

### Check

```text
Targeted Runtime
Java Build Path
Tomcat version
Dynamic Web Module
```

If the project is correctly configured for Tomcat 10.1, this should resolve:

```java
import jakarta.servlet.annotation.WebServlet;
```

---

# 29. Problem 5 — Application runs but URL gives 404

Example:

```text
HTTP Status 404
```

Do not immediately reinstall Tomcat.

Check in this order:

### Step 1 — Is Tomcat running?

Check Eclipse Servers view.

```text
Tomcat
   ↓
Started
```

### Step 2 — Is the application deployed?

Check the server/project deployment.

### Step 3 — Is the context path correct?

If project name is:

```text
MyFirstServlet
```

try:

```text
/MyFirstServlet
```

### Step 4 — Is the Servlet mapping correct?

If code says:

```java
@WebServlet("/hello")
```

URL must contain:

```text
/hello
```

### Step 5 — Check complete URL

```text
http://localhost:8080/MyFirstServlet/hello
```

### Diagnosis pattern

```text
Tomcat stopped?
    ↓ YES → Start Tomcat

Tomcat running?
    ↓ YES

Application deployed?
    ↓ NO → Fix deployment

Application deployed?
    ↓ YES

Correct context path?
    ↓ NO → Fix URL

Correct mapping?
    ↓ NO → Fix @WebServlet

Everything correct?
    ↓
Check server logs
```

---

# 30. Problem 6 — 404 on `/MyFirstServlet/` but `/hello` works

This is normal if there is no resource mapped to the root.

For example:

```java
@WebServlet("/hello")
```

means:

```text
/hello → HelloServlet
```

It does NOT automatically mean:

```text
/ → HelloServlet
```

So:

```text
http://localhost:8080/MyFirstServlet/
```

may return 404.

But:

```text
http://localhost:8080/MyFirstServlet/hello
```

works.

### Solution

Use the mapped URL, or create/configure an appropriate welcome resource if you want the root URL to show something.

---

# 31. Problem 7 — 404 after changing `@WebServlet`

Old mapping:

```java
@WebServlet("/hello")
```

New mapping:

```java
@WebServlet("/student")
```

Student still opens:

```text
/hello
```

### Reason

The URL and mapping no longer match.

### Solution

Open:

```text
/student
```

after redeploying if Eclipse has not automatically republished the change.

---

# 32. Problem 8 — 405 Method Not Allowed

Possible situation:

The client sends a request using a method that the Servlet does not handle in the expected way.

For example, your current Servlet may implement:

```java
doGet()
```

but a POST request is sent.

### Basic rule

```text
GET request
   ↓
doGet()

POST request
   ↓
doPost()
```

### Solution

Use the correct method for the request.

Detailed GET vs POST handling should be taught separately rather than mixing it into the first Servlet.

---

# 33. Problem 9 — Tomcat server will not start

### Possible reasons

Common causes include:

- Port `8080` is already in use.
- Incorrect Java/JDK configuration.
- Incorrect Tomcat runtime path.
- Broken server configuration.
- Another Tomcat instance is already running.

### First check

Look at the Eclipse Console/Server logs.

Do not randomly change multiple settings.

### If port 8080 is already in use

You can either:

1. Stop the application using port 8080, or
2. Change Tomcat's HTTP port.

Example:

```text
8080 → 8081
```

Then URL becomes:

```text
http://localhost:8081/MyFirstServlet/hello
```

Notice:

```text
Port changed
   ↓
URL port must also change
```

---

# 34. Problem 10 — "Port 8080 already in use"

### Linux/macOS

Check:

```bash
lsof -i :8080
```

or:

```bash
sudo lsof -i :8080
```

### Windows

```cmd
netstat -ano | findstr :8080
```

Then identify the process.

### Student rule

> If the server cannot bind to port 8080, first find out who is already using the port.

Do not blindly kill processes.

---

# 35. Problem 11 — Server starts but Servlet changes are not visible

Suppose you changed:

```java
out.println("<h1>Hello Students!</h1>");
```

to:

```java
out.println("<h1>Hello Java Students!</h1>");
```

but browser still shows old output.

### Possible reason

The updated application has not been republished/redeployed.

### Try

```text
Project → Clean
```

Then:

```text
Servers → Tomcat → Clean
```

and restart/redeploy.

Also refresh the browser.

---

# 36. Problem 12 — Wrong project/context path

Suppose the project is:

```text
ServletDemo
```

but the student opens:

```text
http://localhost:8080/MyFirstServlet/hello
```

### Reason

The application context may be different from the URL being used.

### Solution

Check the deployed application/context path in Eclipse/Tomcat.

Then construct:

```text
http://localhost:<port>/<context-path>/<mapping>
```

For example:

```text
http://localhost:8080/ServletDemo/hello
```

---

# 37. Problem 13 — Servlet class exists but URL still gives 404

Check these four things:

```text
1. Class extends HttpServlet?
       ↓
2. @WebServlet mapping present?
       ↓
3. Application deployed?
       ↓
4. URL contains correct context path + mapping?
```

Example:

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
}
```

Required URL pattern:

```text
http://localhost:8080/<context-path>/hello
```

---

# 38. Problem 14 — Java version / runtime mismatch

Symptoms may include:

- Project does not compile.
- Server does not start.
- Facet/runtime errors.
- Unsupported Java version messages.

### Check

```text
Project
 → Properties
 → Java Compiler
```

and:

```text
Project
 → Properties
 → Project Facets
```

and:

```text
Window
 → Preferences
 → Server
 → Runtime Environments
```

Make sure the JDK and Tomcat runtime are configured consistently.

---

# 39. Problem 15 — Student followed an old YouTube tutorial

### Typical mismatch

Tutorial:

```java
import javax.servlet.http.HttpServlet;
```

Student project:

```text
Tomcat 10.1
```

### Problem

The tutorial may target an older Servlet API.

### Correct approach

For this class:

```text
Tomcat 10.1
        +
Jakarta Servlet
        +
jakarta.servlet.*
```

Do not blindly copy old imports.

---

# 40. Problem 16 — "Should I use Maven?"

For this classroom setup, **no**.

If your course practical is based on Eclipse Dynamic Web Projects, students should follow:

```text
Dynamic Web Project
      ↓
Tomcat 10.1
      ↓
Servlet API from runtime
      ↓
@WebServlet
```

Maven is useful and should be taught separately if required, but introducing it here only adds another configuration layer.

---

# 41. Problem-Solving Decision Tree

Give students this troubleshooting flow.

```text
                 Servlet Problem
                       |
                       ↓
             Is code showing red?
                  /          \
                YES           NO
                 |             |
                 ↓             ↓
       Check Servlet API    Run application
       / Tomcat Runtime          |
                 |               ↓
                 |          URL gives 404?
                 |            /       \
                 |          YES        NO
                 |           |          |
                 |           ↓          ↓
                 |     Check mapping   Done
                 |     + context path
                 |
                 ↓
          Is jakarta red?
             /        \
           YES         NO
            |           |
            ↓           ↓
      Check Tomcat     Check
      10.1 runtime     Java/code
            |
            ↓
      Clean + rebuild


If Tomcat won't start
        ↓
Check Console
        ↓
Port 8080 already used?
     /           \
   YES            NO
    |              |
    ↓              ↓
Find process     Check JDK/
or change port   Tomcat config
```

---

# 42. Quick Error → Solution Table

| Problem | Most likely reason | First solution |
|---|---|---|
| `HttpServlet` red | Servlet API unavailable | Check Targeted Runtime |
| `jakarta` red | Jakarta Servlet API missing | Check Tomcat 10.1 runtime |
| `@WebServlet` red | Servlet API missing | Check runtime/build path |
| `javax` vs `jakarta` error | Version mismatch | Tomcat 10+ → `jakarta` |
| 404 | Wrong URL/mapping/context | Check mapping and URL |
| 404 on `/project/` | No root resource | Use mapped URL such as `/hello` |
| 405 | HTTP method mismatch | Check `doGet()` / `doPost()` |
| Tomcat won't start | Port/config/JDK issue | Read Console error |
| 8080 already in use | Another process owns port | Find process/change port |
| Changes not visible | Application not republished | Clean/redeploy/restart |
| Wrong application URL | Wrong context path | Check deployed context |
| Old tutorial code fails | Old Servlet API | Use Jakarta imports for Tomcat 10.1 |
| Maven dependency issue | Maven not configured | Use Dynamic Web Project for this practical |

---

# 43. Student Practical

## Task 1 — Hello Servlet

Create:

```text
HelloServlet
```

Mapping:

```text
/hello
```

Response:

```html
<h1>Hello Students!</h1>
<p>This is my first Servlet.</p>
```

---

## Task 2 — Change Mapping

Change:

```text
/hello
```

to:

```text
/student
```

Verify the old URL fails and the new URL works.

---

## Task 3 — Create a Second Servlet

Create:

```text
WelcomeServlet
```

Mapping:

```text
/welcome
```

Expected:

```text
Welcome to Advanced Java!
```

Students should understand:

```text
/hello
   ↓
HelloServlet

/welcome
   ↓
WelcomeServlet
```

---

## Task 4 — Create a Third Mapping

Create:

```text
AboutServlet
```

Mapping:

```text
/about
```

Expected:

```text
About Advanced Java
```

Now the application has:

```text
/hello
/welcome
/about
```

---

# 44. Student Challenge — Diagnose the Problem

Give students this code:

```java
@WebServlet("/student")
public class HelloServlet extends HttpServlet {
}
```

Then ask:

> What URL should be used if the application context path is `MyFirstServlet` and Tomcat runs on port `8080`?

Answer:

```text
http://localhost:8080/MyFirstServlet/student
```

---

# 45. Student Challenge — Find the Mistake

Given:

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
}
```

Student opens:

```text
http://localhost:8080/MyFirstServlet/student
```

Ask:

> Why could this return 404?

Answer:

```text
Configured mapping = /hello
Requested mapping = /student
```

Therefore there is no matching mapping.

---

# 46. Student Challenge — Request or Response?

Ask students to classify:

```text
request.getParameter("name")
```

Answer:

```text
Request
```

Because information is coming from the client.

Classify:

```java
response.getWriter();
```

Answer:

```text
Response
```

Because the Servlet is preparing content to send back.

---

# 47. Student Challenge — URL Breakdown

Given:

```text
http://localhost:8080/CollegeApp/student
```

Ask students to identify:

```text
Protocol       = http
Host           = localhost
Port           = 8080
Context Path   = CollegeApp
Mapping        = /student
```

---

# 48. Student Checklist Before Asking for Help

Students should provide these details when reporting an error:

```text
1. Tomcat version:
2. Java/JDK version:
3. Eclipse version:
4. Project type:
5. Complete URL:
6. @WebServlet mapping:
7. Exact error message:
8. Console error:
9. Screenshot of Targeted Runtimes:
10. Screenshot of Java Build Path if imports are red
```

### Bad error report

> "Sir, Servlet is not working."

This gives almost no useful information.

### Good error report

> "Sir, Tomcat 10.1 is running. `jakarta.servlet.http.HttpServlet` is red. Project is a Dynamic Web Project with Dynamic Web Module 6.0. Targeted Runtime is currently empty."

That is a useful debugging report.

---

# 49. What NOT to Teach Deeply in This Lecture

Do not overload the first Servlet class with:

- Complete Servlet lifecycle.
- `init()`.
- `service()`.
- `destroy()`.
- Detailed GET vs POST.
- `doPost()`.
- Cookies.
- Sessions.
- ServletConfig.
- ServletContext.
- Filters.
- Listeners.
- JSP.
- MVC.
- Database connectivity.

These should be introduced in later lectures.

---

# 50. Suggested 50-Minute Teaching Flow

| Time | Topic | Student Activity |
|---:|---|---|
| 5 min | What is a Servlet? | Connect with Core Java |
| 7 min | Servlet Container | Draw request-response flow |
| 5 min | Request vs Response | Identify client/server direction |
| 8 min | Dynamic Web Project + Tomcat | Create project |
| 10 min | First Servlet | Write and explain code |
| 5 min | `@WebServlet` mapping | Change `/hello` to `/student` |
| 5 min | Deployment | Run on Tomcat |
| 5 min | Troubleshooting | Demonstrate 404 and red-import cases |

---

# 51. Final Mental Model

Students should leave the lecture knowing this:

```text
                 USER
                  |
                  | Opens URL
                  ↓
               BROWSER
                  |
                  | HTTP Request
                  ↓
                TOMCAT
                  |
                  ↓
          SERVLET CONTAINER
                  |
                  | Checks mapping
                  ↓
        @WebServlet("/hello")
                  |
                  ↓
           HelloServlet
                  |
                  ↓
               doGet()
                  |
                  | HTTP Response
                  ↓
               BROWSER
                  |
                  ↓
          Rendered Web Page
```

## One-line definition

> **Browser sends a request → Tomcat's Servlet Container finds the mapped Servlet → Servlet processes the request → Tomcat sends the response back to the browser.**

---

# 52. Next Lecture

After students understand this flow, the next logical topic is:

```text
Servlet Lifecycle
      ↓
init()
      ↓
service()
      ↓
doGet() / doPost()
      ↓
destroy()
```

Then move to:

```text
GET vs POST
      ↓
Form Data
      ↓
request.getParameter()
      ↓
doPost()
```

This keeps the learning progression logical instead of introducing every Servlet API concept at once.
