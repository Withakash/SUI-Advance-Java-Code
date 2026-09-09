# Lec 10 — Servlet Container, Request–Response Model & First Servlet

**Duration:** 50–60 Minutes\
**Environment:** Eclipse + JDK 17/21 + Apache Tomcat 10.1.x

---

## Lecture Outcomes

By the end of this lecture, students should be able to:

1. Explain why a Servlet is required.
2. Explain the role of a Web Server.
3. Explain the role of a Servlet Container.
4. Explain what Apache Tomcat does.
5. Understand HTTP Request and HTTP Response.
6. Create a Dynamic Web Project in Eclipse.
7. Configure Apache Tomcat in Eclipse.
8. Create a Java package.
9. Create a Servlet class.
10. Extend `HttpServlet`.
11. Use `@WebServlet` for URL mapping.
12. Implement `doGet()`.
13. Deploy the Dynamic Web Project on Tomcat.
14. Run the application in a browser.
15. Understand what happens internally when a browser requests a Servlet.

---

# 1. Start With a Problem

Do not start the lecture by directly defining Servlet.

Ask students:

> **"Suppose I open a website in Chrome. How does my request reach Java code running on the server?"**

Draw:

```text
Student's Browser
       |
       | HTTP Request
       v
    Web Server
       |
       v
Servlet Container
       |
       v
 Java Servlet
       |
       | HTTP Response
       v
    Browser
```

Then ask:

> "Can the browser directly execute Java Servlet code?"

### Answer

No.

The browser sends an **HTTP request** to the server.

The server/container identifies which Servlet should process that request.

For today's practical, we will use:

```text
Apache Tomcat
```

---

# 2. What is a Web Application?

A **web application** is an application that runs in a web environment and communicates with users through a web browser.

Examples:

```text
Login System
Student Registration
Online Shopping
Banking Application
Student Management System
Employee Management System
```

A Java web application can involve technologies such as:

```text
JDBC
Servlet
JSP
```

### JDBC

JDBC stands for:

> Java Database Connectivity

It is used by Java applications to communicate with databases.

### Servlet

A Servlet processes requests coming from clients and generates responses.

### JSP

JSP stands for:

> JavaServer Pages

JSP is used for creating dynamic web pages.

For this lecture, our main focus is:

```text
Servlet
```

---

# 3. What is a Servlet?

A **Servlet** is a Java class that runs on the server and processes client requests to generate responses.

Simple representation:

```text
Browser
   |
   | HTTP Request
   v
Tomcat
   |
   v
Servlet
   |
   | HTTP Response
   v
Browser
```

For example, the browser may request:

```text
GET /hello
```

Tomcat identifies the Servlet mapped to:

```text
/hello
```

For example:

```java
@WebServlet("/hello")
```

---

# 4. Why Do We Need a Servlet?

Suppose the server has an HTML file:

```html
<h1>Hello Student</h1>
```

This is mostly static content.

But real applications need logic.

Examples:

```text
Login
Registration
Search
Student Marks
User Profile
Form Processing
Database Operations
```

For example:

```text
Browser
   |
   | Username + Password
   v
Servlet
   |
   | Java Logic
   v
Database
   |
   | Result
   v
Servlet
   |
   | Response
   v
Browser
```

Servlets allow Java code to process these requests.

---

# 5. Static vs Dynamic Content

## Static Content

Static content is already available as a file.

Examples:

```text
HTML
CSS
Images
JavaScript files
```

Example:

```html
<h1>Welcome to Java</h1>
```

---

## Dynamic Content

Dynamic content is generated using application logic.

Examples:

```text
Welcome Akash
Student Marks: 85
Login Successful
10 students found
```

A Servlet can generate dynamic responses using Java.

```text
Request
   |
   v
Servlet
   |
   | Java Logic
   v
Dynamic Response
```

---

# 6. What is Apache Tomcat?

Students often make this mistake:

> "Tomcat is a Servlet."

❌ Wrong.

Apache Tomcat provides the environment in which Servlet-based web applications can run.

For our course:

```text
Apache Tomcat 10.1.x
```

Mental model:

```text
                 APACHE TOMCAT
                       |
             +---------+---------+
             |                   |
             v                   v
        Web Server        Servlet Container
                                 |
                                 v
                            Java Servlet
```

The easiest definition for students:

> **Tomcat is a server/runtime environment that receives HTTP requests and manages Servlet-based web applications.**

---

# 7. What is a Servlet Container?

A **Servlet Container** manages the execution and lifecycle of Servlets.

Its basic responsibilities are:

```text
Receive HTTP Request
        |
        v
Identify URL
        |
        v
Find Matching Servlet
        |
        v
Create / Manage Servlet
        |
        v
Manage Servlet Lifecycle
        |
        v
Process Request
        |
        v
Generate HTTP Response
```

The Servlet Container manages methods such as:

```java
init()
service()
destroy()
```

Detailed lifecycle will be covered in a later lecture.

---

# 8. Web Server vs Servlet Container

This distinction is important.

| Web Server                 | Servlet Container         |
| -------------------------- | ------------------------- |
| Handles HTTP communication | Manages Servlet execution |
| Can serve static resources | Executes Servlets         |
| Receives client requests   | Finds appropriate Servlet |
| Returns resources          | Manages Servlet lifecycle |

For our practical:

```text
Browser
   |
   | HTTP Request
   v
Tomcat
   |
   +---- Web Server functionality
   |
   +---- Servlet Container
             |
             v
          Servlet
```

---

# 9. Java Program vs Servlet Program

In Core Java, execution usually begins from:

```java
public static void main(String[] args)
```

Example:

```java
public class Demo {

    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```

The JVM starts execution from:

```text
main()
```

A Servlet works differently.

A Servlet does not normally have:

```java
public static void main()
```

Instead:

```text
Browser
   |
   | HTTP Request
   v
Tomcat
   |
   v
Servlet Container
   |
   v
Servlet
```

Tomcat manages Servlet execution.

---

# 10. Servlet API — `jakarta.servlet`

Because we are using:

```text
Tomcat 10.1.x
```

we use the Jakarta Servlet API.

Modern imports look like:

```java
import jakarta.servlet.*;
import jakarta.servlet.http.*;
```

For example:

```java
import jakarta.servlet.http.HttpServlet;
```

and:

```java
import jakarta.servlet.annotation.WebServlet;
```

### Important

Older tutorials may use:

```java
javax.servlet.*
```

For this course, with Tomcat 10.1:

```java
jakarta.servlet.*
```

Do not mix the two APIs.

---

# 11. HTTP Request–Response Model

This is the main concept of today's lecture.

Draw:

```text
                 CLIENT
               +---------+
               | Browser |
               +----+----+
                    |
                    | HTTP Request
                    v
          +-------------------+
          |      TOMCAT       |
          |                   |
          | Servlet Container |
          +---------+---------+
                    |
                    | Find Servlet
                    v
          +-------------------+
          |   HelloServlet    |
          |                   |
          |     doGet()       |
          +---------+---------+
                    |
                    | HTTP Response
                    v
               +---------+
               | Browser |
               +---------+
```

---

# 12. What is an HTTP Request?

When we open:

```text
http://localhost:8080/FirstServlet/hello
```

the browser sends an HTTP request.

Simplified:

```http
GET /FirstServlet/hello HTTP/1.1
Host: localhost:8080
```

Important parts:

```text
GET
 |
 +---- HTTP Request Method

/FirstServlet/hello
 |
 +---- Requested URL
```

Common HTTP methods:

```text
GET
POST
PUT
DELETE
```

For today's example, we will mainly use:

```text
GET
```

---

# 13. What is an HTTP Response?

The Servlet generates a response.

Conceptually:

```http
HTTP/1.1 200 OK
Content-Type: text/html

<h1>Hello from Servlet!</h1>
```

The browser receives the response and renders it.

```text
Servlet
   |
   | HTTP Response
   v
Browser
```

---

# 14. Understand the URL

Suppose our application URL is:

```text
http://localhost:8080/FirstServlet/hello
```

Break it down:

```text
http://
   |
   +---- Protocol

localhost
   |
   +---- Server address

8080
   |
   +---- Tomcat port

FirstServlet
   |
   +---- Application Context Path

hello
   |
   +---- Servlet URL Pattern
```

Therefore:

```text
http://localhost:8080/FirstServlet/hello
```

means:

> Access the `FirstServlet` web application running on Tomcat at port `8080` and request the Servlet mapped to `/hello`.

---

# 15. Practical Starts Here

Now tell students:

> **"Enough theory. Let's create our first Servlet."**

Our environment:

```text
Eclipse
   |
   +---- JDK 17 or 21
   |
   +---- Apache Tomcat 10.1.x
```

---

# 16. Step 1 — Check Java

Open a terminal or command prompt.

Run:

```bash
java -version
```

Example:

```text
java version "17..."
```

or:

```text
java version "21..."
```

Also check:

```bash
javac -version
```

Example:

```text
javac 17.x
```

---

# 17. Step 2 — Install / Download Apache Tomcat

For this course use:

```text
Apache Tomcat 10.1.x
```

After downloading Tomcat, extract it.

Example:

```text
C:\apache-tomcat-10.1.x
```

The important folders are:

```text
apache-tomcat-10.1.x/
|
+---- bin/
+---- conf/
+---- lib/
+---- logs/
+---- webapps/
+---- temp/
+---- work/
```

---

# 18. Step 3 — Understand Tomcat Folders

## `bin`

Contains scripts for starting and stopping Tomcat.

```text
bin/
 |
 +---- startup.bat
 +---- shutdown.bat
```

Linux/macOS:

```text
startup.sh
shutdown.sh
```

---

## `conf`

Contains Tomcat configuration files.

Important file:

```text
server.xml
```

---

## `webapps`

Contains deployed web applications.

Example:

```text
webapps/
 |
 +---- FirstServlet/
 |
 +---- AnotherApplication/
```

---

# 19. Step 4 — Check Tomcat Port

Open:

```text
conf/server.xml
```

Find the HTTP Connector.

Conceptually:

```xml
<Connector
    port="8080"
    protocol="HTTP/1.1"
    ... />
```

The important value is:

```text
8080
```

Therefore:

```text
Tomcat
   |
   +---- HTTP Port = 8080
```

---

# 20. Step 5 — Start Tomcat Manually

### Windows

Go to:

```text
apache-tomcat-10.1.x/bin
```

Run:

```text
startup.bat
```

### Linux/macOS

```bash
cd /path/to/apache-tomcat-10.1.x/bin
```

Then:

```bash
./startup.sh
```

---

# 21. Step 6 — Test Tomcat

Open a browser.

Enter:

```text
http://localhost:8080
```

If Tomcat is running correctly, the Tomcat page should appear.

Therefore:

```text
localhost:8080
       |
       v
    Tomcat
       |
       v
Tomcat Page
```

### Important Check

Before creating the Servlet, always verify:

```text
http://localhost:8080
```

If this does not work, solve the Tomcat problem first.

---

# 22. Step 7 — Open Eclipse

Start Eclipse.

When Eclipse starts, it asks for a workspace.

Example:

```text
C:\Users\Student\eclipse-workspace
```

Select/create your workspace.

Click:

```text
Launch
```

---

# 23. Step 8 — Configure Tomcat in Eclipse

Before creating the project, configure Tomcat.

In Eclipse:

```text
Window
   ↓
Preferences
   ↓
Server
   ↓
Runtime Environments
```

Click:

```text
Add
```

Select:

```text
Apache
   |
   +---- Tomcat v10.1
```

Click:

```text
Next
```

Select the Tomcat installation directory.

Example:

```text
C:\apache-tomcat-10.1.x
```

Select the installed JDK.

Click:

```text
Finish
```

Now Eclipse knows where Tomcat is installed.

---

# 24. Step 9 — Create a Dynamic Web Project

This is the main project creation step.

In Eclipse:

```text
File
   ↓
New
   ↓
Dynamic Web Project
```

If you cannot see it:

```text
File
   ↓
New
   ↓
Other
   ↓
Web
   ↓
Dynamic Web Project
```

---

# 25. Step 10 — Give Project Name

Enter:

```text
FirstServlet
```

Project name:

```text
FirstServlet
```

Select the configured runtime:

```text
Apache Tomcat v10.1
```

For the Dynamic Web Module version, select the version compatible with your configured Tomcat/Eclipse installation.

Click:

```text
Finish
```

---

# 26. Step 11 — Understand the Dynamic Web Project Structure

Eclipse will create a structure similar to:

```text
FirstServlet
|
+---- Java Resources
|      |
|      +---- src/main/java
|
+---- WebContent
|      |
|      +---- META-INF
|      |
|      +---- WEB-INF
|
+---- build
|
+---- ...
```

Depending on Eclipse's project configuration/version, the exact folder names may differ.

The important concepts are:

```text
Java Resources
      |
      +---- Java classes

WebContent
      |
      +---- Web resources

WEB-INF
      |
      +---- Protected web application configuration/resources
```

---

# 27. Step 12 — Create a Package

Inside Java source:

```text
Java Resources
   |
   +---- src
```

Right-click:

```text
src
   ↓
New
   ↓
Package
```

Enter:

```text
com.example.servlet
```

Click:

```text
Finish
```

You now have:

```text
src
 |
 +---- com.example.servlet
```

---

# 28. Step 13 — Create the Servlet Class

Right-click the package:

```text
com.example.servlet
```

Select:

```text
New
   ↓
Class
```

Class name:

```text
HelloServlet
```

Click:

```text
Finish
```

---

# 29. Step 14 — Create Servlet Using `HttpServlet`

Our class should extend:

```java
HttpServlet
```

Complete code:

```java
package com.example.servlet;

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

        out.println("<h1>Hello from Servlet!</h1>");
        out.println("<p>Welcome to Advanced Java.</p>");
    }
}
```

---

# 30. Step 15 — Understand the Code

## Package

```java
package com.example.servlet;
```

Defines the package containing our Servlet.

---

## Import `HttpServlet`

```java
import jakarta.servlet.http.HttpServlet;
```

Allows us to use:

```java
HttpServlet
```

---

## Class

```java
public class HelloServlet extends HttpServlet
```

This means:

```text
HelloServlet
     |
     | extends
     v
HttpServlet
```

`HttpServlet` provides HTTP-specific Servlet functionality.

---

# 31. Step 16 — `@WebServlet`

Our annotation:

```java
@WebServlet("/hello")
```

maps the URL:

```text
/hello
```

to:

```text
HelloServlet
```

Conceptually:

```text
/hello
   |
   v
HelloServlet
```

This is called:

> **URL Mapping**

---

# 32. Step 17 — Understand `doGet()`

Our method:

```java
protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response)
```

handles HTTP GET requests.

If the browser sends:

```text
GET
```

the Servlet Container routes the request to:

```text
doGet()
```

Conceptually:

```text
GET Request
     |
     v
HttpServlet
     |
     v
doGet()
```

---

# 33. Step 18 — Understand `HttpServletRequest`

The first important object is:

```java
HttpServletRequest request
```

It represents information about the incoming HTTP request.

It can provide information such as:

```text
HTTP Method
URL
Parameters
Headers
Cookies
```

Example:

```java
request.getMethod();
```

could return:

```text
GET
```

---

# 34. Step 19 — Understand `HttpServletResponse`

The second important object is:

```java
HttpServletResponse response
```

It represents the response that the Servlet sends back to the browser.

Example:

```java
response.setContentType("text/html");
```

This tells the browser:

```text
Response Content Type
        =
text/html
```

---

# 35. Step 20 — `PrintWriter`

We create:

```java
PrintWriter out = response.getWriter();
```

Then:

```java
out.println("<h1>Hello from Servlet!</h1>");
```

The HTML is written into the HTTP response.

Conceptually:

```text
Servlet
   |
   | PrintWriter
   v
HTTP Response
   |
   v
Browser
```

---

# 36. Step 21 — Run the Dynamic Web Project on Tomcat

Right-click the project:

```text
FirstServlet
```

Select:

```text
Run As
   ↓
Run on Server
```

If prompted:

```text
Choose an existing server
```

Select:

```text
Tomcat v10.1 Server
```

Click:

```text
Finish
```

Eclipse will deploy the Dynamic Web Project to Tomcat.

---

# 37. What Does Eclipse Do?

When we choose:

```text
Run As
   ↓
Run on Server
```

Eclipse performs several tasks.

Conceptually:

```text
Eclipse
   |
   +---- Start Tomcat
   |
   +---- Deploy Web Application
   |
   +---- Publish Application
   |
   +---- Open Browser
```

The application becomes available through Tomcat.

---

# 38. Step 22 — Open the Servlet

The application context path is usually:

```text
FirstServlet
```

Our Servlet mapping is:

```text
/hello
```

Tomcat port:

```text
8080
```

Therefore the URL is:

```text
http://localhost:8080/FirstServlet/hello
```

Open this URL in the browser.

Expected output:

```text
Hello from Servlet!

Welcome to Advanced Java.
```

---

# 39. Understand the Complete URL

```text
http://localhost:8080/FirstServlet/hello
```

Break it down:

```text
http://
   |
   +---- Protocol

localhost
   |
   +---- Server

8080
   |
   +---- Tomcat Port

FirstServlet
   |
   +---- Context Path

hello
   |
   +---- Servlet Mapping
```

The important relationship is:

```text
FirstServlet
     |
     +---- Application
              |
              +---- /hello
                       |
                       v
                 HelloServlet
```

---

# 40. What Happens Internally?

This is the most important part of the practical.

When the browser opens:

```text
http://localhost:8080/FirstServlet/hello
```

the following happens.

---

## Step 1 — Browser Sends Request

```text
Browser
   |
   | HTTP GET
   v
Tomcat
```

---

## Step 2 — Tomcat Receives Request

Tomcat is listening on:

```text
8080
```

So it receives the request.

```text
localhost:8080
       |
       v
     Tomcat
```

---

## Step 3 — Tomcat Identifies the Application

The URL contains:

```text
/FirstServlet
```

Tomcat identifies:

```text
FirstServlet
```

as the web application's context path.

---

## Step 4 — Tomcat Checks URL Mapping

The remaining URL is:

```text
/hello
```

Tomcat finds:

```java
@WebServlet("/hello")
```

Therefore:

```text
/hello
   |
   v
HelloServlet
```

---

## Step 5 — Servlet Container Manages Servlet

The Servlet Container manages the Servlet object and lifecycle.

Conceptually:

```text
Servlet Container
       |
       +---- Load Servlet
       |
       +---- Initialize Servlet
       |
       +---- Process Request
```

---

## Step 6 — Request Reaches `doGet()`

Because the browser sent:

```text
GET
```

the request is handled by:

```java
doGet()
```

Our method receives:

```java
HttpServletRequest request
```

and:

```java
HttpServletResponse response
```

---

## Step 7 — Servlet Generates Response

The Servlet executes:

```java
response.setContentType("text/html");

PrintWriter out = response.getWriter();

out.println("<h1>Hello from Servlet!</h1>");
```

---

## Step 8 — Response Goes Back to Browser

```text
HelloServlet
      |
      | HTTP Response
      v
   Tomcat
      |
      v
   Browser
```

The browser displays:

```text
Hello from Servlet!
```

---

# 41. Complete Request–Response Flow

Draw this on the board:

```text
+----------------+
|    Browser     |
+-------+--------+
        |
        | HTTP GET
        v
+----------------+
|     Tomcat     |
|   Port: 8080   |
+-------+--------+
        |
        | Context Path
        v
+----------------+
|  FirstServlet  |
+-------+--------+
        |
        | URL Mapping
        | /hello
        v
+----------------+
| HelloServlet   |
+-------+--------+
        |
        | doGet()
        v
+----------------+
|   Java Logic   |
+-------+--------+
        |
        | HTTP Response
        v
+----------------+
|    Browser     |
+----------------+
```

---

# 42. Servlet Lifecycle — Introduction

A Servlet has a lifecycle managed by the Servlet Container.

The important lifecycle methods are:

```java
init()
service()
destroy()
```

Basic flow:

```text
Servlet Created
      |
      v
   init()
      |
      v
  service()
      |
      v
  service()
      |
      v
  service()
      |
      v
 destroy()
```

For `HttpServlet`, HTTP request handling eventually gets dispatched to methods such as:

```java
doGet()
doPost()
```

We will study the lifecycle in detail in the next lecture.

---

# 43. `service()` vs `doGet()`

Students may see older Servlet examples using:

```java
service(
    ServletRequest req,
    ServletResponse res
)
```

For HTTP applications, `HttpServlet` provides HTTP-specific methods.

Conceptually:

```text
HTTP Request
      |
      v
HttpServlet
      |
      v
service()
      |
      +---- GET ----> doGet()
      |
      +---- POST ---> doPost()
      |
      +---- PUT ----> doPut()
      |
      +---- DELETE -> doDelete()
```

For today's practical:

```text
Browser GET Request
        |
        v
      doGet()
```

---

# 44. Annotation-Based Configuration

We are using:

```java
@WebServlet("/hello")
```

This is annotation-based URL mapping.

It connects:

```text
URL
 |
 +---- /hello
        |
        v
HelloServlet
```

The older approach uses:

```text
web.xml
```

for Servlet configuration and mapping.

For today's first Servlet, we use:

```java
@WebServlet
```

because it is simpler.

---

# 45. `web.xml`

The Dynamic Web Project contains:

```text
WEB-INF
```

and older Servlet applications commonly use:

```text
WEB-INF/web.xml
```

for deployment configuration.

For example, traditional configuration can define:

```text
Servlet
   |
   +---- Servlet Name
   |
   +---- Servlet Class
   |
   +---- URL Mapping
```

But today's Servlet uses:

```java
@WebServlet("/hello")
```

so we don't need to manually create the URL mapping in `web.xml`.

We will cover `web.xml` separately.

---

# 46. Test 1 — Change the Response

Change:

```java
out.println("<h1>Hello from Servlet!</h1>");
```

to:

```java
out.println("<h1>Welcome to Advanced Java!</h1>");
```

Save the file.

Refresh:

```text
http://localhost:8080/FirstServlet/hello
```

Expected:

```text
Welcome to Advanced Java!
```

---

# 47. Test 2 — Add More HTML

Try:

```java
out.println("<h1>Hello from Servlet!</h1>");
out.println("<h2>My First Servlet</h2>");
out.println("<p>This response is generated by Java.</p>");
```

The browser renders the HTML.

This demonstrates:

```text
Java Code
    |
    v
HTML Response
    |
    v
Browser
```

---

# 48. Test 3 — Change URL Mapping

Change:

```java
@WebServlet("/hello")
```

to:

```java
@WebServlet("/welcome")
```

Now the URL becomes:

```text
http://localhost:8080/FirstServlet/welcome
```

The mapping is:

```text
/welcome
    |
    v
HelloServlet
```

This demonstrates the importance of URL mapping.

---

# 49. Test 4 — Add Dynamic Data

Try:

```java
String name = "Akash";

out.println("<h1>Hello " + name + "</h1>");
```

Output:

```text
Hello Akash
```

The important concept is:

```text
Java Variable
      |
      v
Java Logic
      |
      v
Dynamic HTML
      |
      v
Browser
```

---

# 50. Common Problems

## Problem 1 — `localhost:8080` Doesn't Open

Check:

```text
Is Tomcat running?
```

Try:

```text
http://localhost:8080
```

---

## Problem 2 — Port 8080 Already in Use

Another process may already be using:

```text
8080
```

Possible causes:

```text
Another Tomcat instance
Another application
Eclipse server
Manually started Tomcat
```

Do not run two Tomcat instances on the same port.

---

# 51. Problem 3 — `javax.servlet` Error

If using Tomcat 10.1, avoid:

```java
javax.servlet.*
```

Use:

```java
jakarta.servlet.*
```

---

# 52. Problem 4 — 404 Error

If the browser shows:

```text
404 Not Found
```

check:

```text
1. Is Tomcat running?
2. Is the application deployed?
3. Is the project name correct?
4. Is @WebServlet mapping correct?
5. Is the URL correct?
```

For our example:

```text
http://localhost:8080/FirstServlet/hello
```

---

# 53. Problem 5 — Wrong Context Path

Suppose your Eclipse project is named:

```text
FirstServlet
```

but the generated context path is different.

Check the Eclipse server configuration.

The URL should use the actual deployed context path.

---

# 54. Problem 6 — Servlet Not Found

Check:

```java
@WebServlet("/hello")
```

and make sure the URL contains:

```text
/hello
```

Example:

```text
@WebServlet("/hello")
```

Correct:

```text
http://localhost:8080/FirstServlet/hello
```

Wrong:

```text
http://localhost:8080/FirstServlet/Hello
```

URL matching is case-sensitive in typical deployments.

---

# 55. Problem 7 — Application Not Published

If changes are not appearing:

In Eclipse:

```text
Servers
   |
   +---- Tomcat Server
```

Right-click and use:

```text
Publish
```

or restart the server if necessary.

---

# 56. Important Eclipse Workflow

Students should memorize this workflow:

```text
Open Eclipse
     |
     v
Configure Tomcat
     |
     v
Create Dynamic Web Project
     |
     v
Create Package
     |
     v
Create Servlet Class
     |
     v
Extend HttpServlet
     |
     v
Add @WebServlet
     |
     v
Implement doGet()
     |
     v
Run on Server
     |
     v
Select Tomcat
     |
     v
Deploy Application
     |
     v
Open Browser
     |
     v
Test Servlet
```

---

# 57. Project Structure — Final View

Your project should conceptually look like:

```text
FirstServlet
|
+---- Java Resources
|       |
|       +---- src
|              |
|              +---- com.example.servlet
|                     |
|                     +---- HelloServlet.java
|
+---- WebContent
|       |
|       +---- META-INF
|       |
|       +---- WEB-INF
|
+---- build/
```

The exact Eclipse structure can vary depending on the Dynamic Web Project configuration.

The important part is:

```text
Java Source
    |
    +---- HelloServlet.java

Web Application
    |
    +---- WebContent / web resources

Deployment
    |
    +---- Tomcat
```

---

# 58. Final Architecture

Students should understand this architecture:

```text
                         CLIENT
                     +-------------+
                     |   Browser   |
                     +------+------+
                            |
                            | HTTP Request
                            v
                  +-------------------+
                  |      TOMCAT       |
                  |                   |
                  |    Port: 8080     |
                  +---------+---------+
                            |
                            v
                  +-------------------+
                  | Servlet Container |
                  +---------+---------+
                            |
                            | URL Mapping
                            | /hello
                            v
                  +-------------------+
                  |   HelloServlet    |
                  +---------+---------+
                            |
                            | doGet()
                            v
                  +-------------------+
                  |    Java Logic     |
                  +---------+---------+
                            |
                            | HTTP Response
                            v
                     +-------------+
                     |   Browser   |
                     +-------------+
```

---

# 59. One-Line Definitions

### Web Application

> An application that runs in a web environment and communicates with users through a web browser.

### Servlet

> A Java class that runs on the server and processes client requests to generate responses.

### Servlet Container

> A component that manages the execution and lifecycle of Servlets.

### Apache Tomcat

> A server/runtime environment commonly used to run Java Servlet-based web applications.

### HTTP Request

> A message sent by the client to the server requesting a resource or operation.

### HTTP Response

> A message sent by the server back to the client containing the result of the request.

### `HttpServlet`

> A base class used to create HTTP-based Servlets.

### `@WebServlet`

> An annotation used to map a URL pattern to a Servlet.

### WAR

> Web Application Archive, a packaged Java web application used for deployment.

---

# 60. Important Commands

## Check Java

```bash
java -version
```

## Check Java Compiler

```bash
javac -version
```

---

# 61. Important Tomcat URLs

Check whether Tomcat is running:

```text
http://localhost:8080
```

Open our Servlet:

```text
http://localhost:8080/FirstServlet/hello
```

---

# 62. Viva Questions

### Q1. What is a Servlet?

A Java class that runs on the server and processes client requests.

### Q2. Can the browser directly execute a Servlet?

No.

### Q3. Who manages Servlet execution?

The Servlet Container.

### Q4. Which server are we using?

Apache Tomcat.

### Q5. What is the Tomcat port in our setup?

8080.

### Q6. Which package is used with Tomcat 10.1?

```java
jakarta.servlet.*
```

### Q7. What does `@WebServlet("/hello")` do?

It maps `/hello` to the Servlet.

### Q8. Which method handles a GET request?

```java
doGet()
```

### Q9. What is `HttpServletRequest`?

It represents the incoming HTTP request.

### Q10. What is `HttpServletResponse`?

It represents the response sent back to the client.

### Q11. What does this do?

```java
response.setContentType("text/html");
```

It tells the client that the response contains HTML.

### Q12. Why do we use `PrintWriter`?

To write content into the HTTP response.

### Q13. What is a Dynamic Web Project?

An Eclipse project structure designed for Java web applications.

### Q14. What is the role of Tomcat?

It provides the runtime/server environment for the web application and manages Servlet execution.

### Q15. What happens when we open:

```text
http://localhost:8080/FirstServlet/hello
```

Answer:

```text
Browser
   ↓
HTTP Request
   ↓
Tomcat
   ↓
Servlet Container
   ↓
/hello Mapping
   ↓
HelloServlet
   ↓
doGet()
   ↓
HTTP Response
   ↓
Browser
```

---

# 63. Final Lecture Summary

The complete concept:

```text
Browser
   |
   | HTTP Request
   v
Tomcat
   |
   | Servlet Container
   v
URL Mapping
   |
   | @WebServlet("/hello")
   v
HelloServlet
   |
   | doGet()
   v
Java Logic
   |
   | HttpServletResponse
   v
HTTP Response
   |
   v
Browser
```

The complete practical:

```text
JDK
 |
 v
Apache Tomcat
 |
 v
Eclipse
 |
 v
Configure Tomcat
 |
 v
Dynamic Web Project
 |
 v
Create Package
 |
 v
Create Servlet
 |
 v
extends HttpServlet
 |
 v
@WebServlet("/hello")
 |
 v
doGet()
 |
 v
Run on Server
 |
 v
Tomcat
 |
 v
Browser
 |
 v
http://localhost:8080/FirstServlet/hello
```

---

# 64. Final Takeaway

The single most important concept from today's lecture is:

> **A browser sends an HTTP request → Tomcat receives the request → the Servlet Container identifies the correct Servlet → the Servlet processes the request → an HTTP response is generated → the response is sent back to the browser.**

Remember:

```text
Browser
   ↓
HTTP Request
   ↓
Tomcat
   ↓
Servlet Container
   ↓
@WebServlet Mapping
   ↓
Servlet
   ↓
doGet()
   ↓
HTTP Response
   ↓
Browser
```

And our first Servlet mapping is:

```java
@WebServlet("/hello")
```

which connects:

```text
/hello
   |
   v
HelloServlet
```

---

# 65. Practical Checklist for Students

Before leaving the lab, make sure you can:

- [ ] Check Java version
- [ ] Install/configure Apache Tomcat
- [ ] Verify `localhost:8080`
- [ ] Configure Tomcat in Eclipse
- [ ] Create a Dynamic Web Project
- [ ] Create a Java package
- [ ] Create `HelloServlet`
- [ ] Extend `HttpServlet`
- [ ] Add `@WebServlet("/hello")`
- [ ] Implement `doGet()`
- [ ] Use `HttpServletRequest`
- [ ] Use `HttpServletResponse`
- [ ] Use `PrintWriter`
- [ ] Run the project on Tomcat
- [ ] Open the Servlet in the browser
- [ ] Explain the complete request–response flow

---

# 66. First Servlet — Final Code

```java
package com.example.servlet;

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

        out.println("<h1>Hello from Servlet!</h1>");
        out.println("<p>Welcome to Advanced Java.</p>");
    }
}
```

### Browser URL

```text
http://localhost:8080/FirstServlet/hello
```

### Expected Output

```text
Hello from Servlet!

Welcome to Advanced Java.
```
