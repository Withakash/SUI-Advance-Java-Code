# Servlet Theory Notes — Lifecycle, Configuration, Session Management & Java EE

> **Audience:** Beginner / university exam preparation  
> **Focus:** Understanding + theory + exam questions + practical connection  
> **Technology:** Servlets with Tomcat  
> **Note:** Modern Tomcat versions use the `jakarta.servlet.*` package. Older Java EE material may show `javax.servlet.*`.

---

# 1. What is a Servlet?

A **Servlet** is a Java class that runs on a web server/servlet container and is used to handle client requests and generate dynamic responses.

In simple words:

> A Servlet is a Java program that receives an HTTP request, processes it, and sends an HTTP response.

### Basic flow

```text
Browser
   |
   | HTTP Request
   v
Tomcat / Servlet Container
   |
   | finds servlet
   v
Servlet
   |
   | processing
   v
HTTP Response
   |
   v
Browser
```

### Example

Suppose a browser sends:

```text
GET /hello?name=Akash
```

The servlet can:

1. Receive the request.
2. Read `name`.
3. Process the data.
4. Generate HTML/text.
5. Send the response.

---

# 2. Why do we need Servlets?

Static HTML can display fixed information.

```html
<h1>Hello Student</h1>
```

But web applications usually need dynamic processing.

For example:

- Login validation
- Calculator
- Registration form
- Database operations
- Search
- Online shopping cart
- Session management
- Form processing

Servlets allow Java code to participate in this request-response process.

---

# 3. What is a Servlet Container?

A **Servlet Container** is the component of a web server/application server that manages the execution of servlets.

Examples:

- Apache Tomcat
- Jetty
- Undertow

Tomcat is commonly used for learning Servlet/JSP applications.

## Main responsibilities of a Servlet Container

The container:

1. Loads servlet classes.
2. Creates servlet objects.
3. Calls lifecycle methods.
4. Maps URLs to servlets.
5. Creates request and response objects.
6. Manages threads for requests.
7. Handles servlet lifecycle.
8. Provides session management support.
9. Manages servlet configuration.
10. Communicates with the HTTP server.

---

# 4. Servlet Architecture

```text
                 Client / Browser
                        |
                        | HTTP Request
                        v
                +----------------+
                |  Web Server    |
                +----------------+
                        |
                        v
              +---------------------+
              | Servlet Container   |
              |      Tomcat         |
              +---------------------+
                        |
             URL Mapping / Routing
                        |
                        v
                +-------------+
                |  Servlet    |
                +-------------+
                  |         |
             Request       Response
                  |         |
                  +----+----+
                       |
                       v
                    Browser
```

---

# 5. Servlet Lifecycle

The **Servlet Lifecycle** describes the complete life of a servlet object from creation to destruction.

The major lifecycle methods are:

```text
init()
  |
  v
service()
  |
  v
doGet() / doPost()
  |
  v
service()
  |
  v
destroy()
```

Important:

- `init()` is normally called **once**.
- `service()` is called for each request.
- `doGet()` / `doPost()` handle the corresponding HTTP method.
- `destroy()` is normally called **once** before the servlet is removed.

---

# 6. init() Method

The `init()` method is called by the servlet container when the servlet is initialized.

Typical purpose:

- One-time initialization
- Reading configuration
- Creating reusable resources
- Preparing objects required by the servlet

Example:

```java
@Override
public void init() throws ServletException {
    System.out.println("Servlet initialized");
}
```

## Important point

`init()` is generally called only once for a servlet instance.

### Flow

```text
Servlet class loaded
       |
       v
Servlet object created
       |
       v
init()
       |
       v
Servlet ready
```

---

# 7. service() Method

The `service()` method is responsible for handling incoming requests.

For an `HttpServlet`, the container uses `service()` to determine which HTTP method was used.

Conceptually:

```text
HTTP Request
     |
     v
service()
     |
     +---- GET ----> doGet()
     |
     +---- POST ---> doPost()
     |
     +---- PUT ----> doPut() / appropriate handling
     |
     +---- DELETE -> doDelete() / appropriate handling
```

For normal beginner Servlet applications, the most common methods are:

```java
doGet()
doPost()
```

You normally override `doGet()` and/or `doPost()` rather than writing your own `service()` method.

---

# 8. doGet() Method

`doGet()` handles HTTP GET requests.

Example:

```java
@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    response.setContentType("text/html");

    PrintWriter out = response.getWriter();

    out.println("<h1>Hello from GET</h1>");
}
```

A GET request commonly looks like:

```text
http://localhost:8080/MyApp/hello?name=Akash
```

The data can be accessed using:

```java
String name = request.getParameter("name");
```

## Common uses

GET is commonly used for:

- Fetching data
- Searching
- Displaying pages
- Reading resources
- Passing small query parameters

---

# 9. doPost() Method

`doPost()` handles HTTP POST requests.

Example:

```java
@Override
protected void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    String username = request.getParameter("username");

    response.setContentType("text/html");

    PrintWriter out = response.getWriter();

    out.println("<h1>Welcome " + username + "</h1>");
}
```

HTML form:

```html
<form action="login" method="post">

    <input type="text" name="username">

    <input type="password" name="password">

    <button type="submit">Login</button>

</form>
```

---

# 10. GET vs POST

| Feature | GET | POST |
|---|---|---|
| Servlet method | `doGet()` | `doPost()` |
| Data location | Usually URL/query string | Request body |
| Visible in URL | Yes | Normally no |
| Bookmarking | Easy | Not normally used |
| Typical purpose | Retrieve/read data | Submit/process data |
| Sensitive data | Should not be placed in URL | Better than URL for credentials, but HTTPS is still required |
| URL length | Limited by practical URL limits | Larger request bodies are possible |
| Browser refresh | Usually repeats GET safely | May repeat form submission |
| Idempotent use | Commonly intended for safe retrieval | Commonly used for state-changing operations |

### Important security point

POST does **not** automatically make data secure.

For secure transmission, use:

```text
HTTPS
```

Do not send passwords through plain HTTP.

---

# 11. GET Request Example

HTML:

```html
<form action="hello" method="get">

    <input type="text" name="username">

    <button type="submit">Submit</button>

</form>
```

Servlet:

```java
@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    String username = request.getParameter("username");

    response.setContentType("text/html");

    PrintWriter out = response.getWriter();

    out.println("<h1>Hello " + username + "</h1>");
}
```

If the user enters:

```text
Akash
```

the URL may become:

```text
/hello?username=Akash
```

---

# 12. POST Request Example

HTML:

```html
<form action="hello" method="post">

    <input type="text" name="username">

    <button type="submit">Submit</button>

</form>
```

Servlet:

```java
@Override
protected void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    String username = request.getParameter("username");

    response.setContentType("text/html");

    PrintWriter out = response.getWriter();

    out.println("<h1>Hello " + username + "</h1>");
}
```

The browser does not normally put the form parameters into the URL.

---

# 13. Complete Servlet Lifecycle

Consider a servlet:

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("INIT");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("GET REQUEST");

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<h1>Hello</h1>");
    }

    @Override
    public void destroy() {
        System.out.println("DESTROY");
    }
}
```

### First request

```text
Container starts
      |
      v
Servlet object created
      |
      v
init()
      |
      v
service()
      |
      v
doGet()
      |
      v
Response
```

### Second request

Usually:

```text
service()
   |
   v
doGet()
   |
   v
Response
```

`init()` is not called again for every request.

### Server shutdown / servlet removal

```text
destroy()
```

is called before the servlet instance is removed.

---

# 14. Practical Lifecycle Demonstration

Create a servlet:

```java
@WebServlet("/life")
public class LifeCycleServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("1. init() called");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("2. doGet() called");

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<h1>Servlet Lifecycle Demo</h1>");
    }

    @Override
    public void destroy() {
        System.out.println("3. destroy() called");
    }
}
```

### Observe the console

On first request:

```text
1. init() called
2. doGet() called
```

On another request:

```text
2. doGet() called
```

When the servlet is destroyed:

```text
3. destroy() called
```

### Student activity

1. Open the servlet URL.
2. Observe the console.
3. Refresh the browser 5 times.
4. Check how many times `init()` executes.
5. Stop the server.
6. Observe `destroy()`.

---

# 15. web.xml Configuration

Servlets can be configured using deployment descriptors.

The traditional configuration file is:

```text
WEB-INF/web.xml
```

Example:

```xml
<web-app>

    <servlet>
        <servlet-name>HelloServlet</servlet-name>

        <servlet-class>
            com.example.HelloServlet
        </servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>HelloServlet</servlet-name>

        <url-pattern>/hello</url-pattern>
    </servlet-mapping>

</web-app>
```

The mapping means:

```text
/hello
   |
   v
HelloServlet
```

---

# 16. Annotation-Based Configuration

Modern Servlet applications commonly use annotations.

Example:

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
}
```

This replaces the need for a basic servlet mapping in `web.xml`.

### Comparison

### web.xml

```xml
<servlet>
    <servlet-name>Hello</servlet-name>
    <servlet-class>com.example.HelloServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>Hello</servlet-name>
    <url-pattern>/hello</url-pattern>
</servlet-mapping>
```

### Annotation

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
}
```

---

# 17. web.xml vs Annotation

| Feature | web.xml | Annotation |
|---|---|---|
| Configuration | XML | Java annotation |
| Location | `WEB-INF/web.xml` | Servlet source code |
| Readability | Separate configuration | Close to code |
| Modern use | Still supported | Very common |
| Example | `<servlet-mapping>` | `@WebServlet` |

Both approaches are important for university exams.

---

# 18. ServletConfig

`ServletConfig` provides configuration information to a **specific servlet**.

Think:

> `ServletConfig` = configuration for one servlet.

It can contain initialization parameters.

Example:

```java
@WebServlet(
    value = "/hello",
    initParams = {
        @WebInitParam(
            name = "message",
            value = "Welcome Student"
        )
    }
)
public class HelloServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {

        String message =
            getServletConfig().getInitParameter("message");

        System.out.println(message);
    }
}
```

---

# 19. ServletConfig Methods

Important methods include:

```java
getInitParameter(String name)
```

Gets one initialization parameter.

```java
getInitParameterNames()
```

Gets the names of all initialization parameters.

```java
getServletName()
```

Gets the servlet name.

```java
getServletContext()
```

Gets the associated `ServletContext`.

---

# 20. ServletConfig Example Using web.xml

```xml
<servlet>

    <servlet-name>PaymentServlet</servlet-name>

    <servlet-class>
        com.example.PaymentServlet
    </servlet-class>

    <init-param>
        <param-name>currency</param-name>
        <param-value>INR</param-value>
    </init-param>

</servlet>
```

Servlet:

```java
String currency =
    getServletConfig().getInitParameter("currency");
```

The parameter belongs to:

```text
PaymentServlet
```

It is not automatically a global application parameter.

---

# 21. ServletContext

`ServletContext` represents the **web application as a whole**.

Think:

> `ServletConfig` = one servlet  
> `ServletContext` = entire web application

Example:

```java
ServletContext context =
    getServletContext();
```

---

# 22. ServletContext Uses

ServletContext can be used for:

- Application-wide attributes
- Application initialization parameters
- Sharing information between servlets
- Accessing application resources
- Getting application/server information

---

# 23. ServletConfig vs ServletContext

| Feature | ServletConfig | ServletContext |
|---|---|---|
| Scope | One servlet | Entire web application |
| Parameters | Servlet-specific | Application-wide |
| Object | Associated with servlet | Associated with web application |
| Sharing | Not intended for all servlets | Can share attributes across servlets |
| Example | Payment gateway setting for one servlet | Application name |

### Easy memory trick

```text
Config  → one servlet
Context → complete application
```

---

# 24. Application-Level Attributes

ServletContext can store attributes.

Example:

```java
ServletContext context =
    getServletContext();

context.setAttribute("collegeName",
                     "ABC Institute");
```

Another servlet can retrieve it:

```java
ServletContext context =
    getServletContext();

String college =
    (String) context.getAttribute("collegeName");
```

This allows servlets in the same web application to share application-level data.

---

# 25. ServletContext Methods

Important methods:

```java
setAttribute(String name, Object value)
```

Stores an application-level attribute.

```java
getAttribute(String name)
```

Retrieves an attribute.

```java
removeAttribute(String name)
```

Removes an attribute.

```java
getInitParameter(String name)
```

Gets an application initialization parameter.

---

# 26. Application Scope

The major scopes commonly discussed in Servlet applications are:

```text
Application
    |
Session
    |
Request
    |
Page
```

For the current topics, remember:

### Application scope

Available across the web application.

Implemented using:

```java
ServletContext
```

### Session scope

Data associated with one user's session.

Implemented using:

```java
HttpSession
```

### Request scope

Data exists for one HTTP request.

---

# 27. Why Sessions Are Needed?

HTTP is **stateless**.

Stateless means:

> The server does not automatically remember previous HTTP requests as belonging to the same user.

Example:

```text
Request 1:
Login with username = Akash

Request 2:
Open Profile

Request 3:
Open Orders
```

Without session management, the server does not automatically know that all three requests belong to the same logged-in user.

Therefore, web applications use **session management**.

---

# 28. HTTP Statelessness

Consider:

```text
Browser ---> Request 1 ---> Server

Browser ---> Request 2 ---> Server

Browser ---> Request 3 ---> Server
```

Each request is independent from the HTTP protocol's basic perspective.

The application needs a mechanism to associate requests with a user/session.

Common mechanisms:

1. Cookies
2. URL Rewriting
3. HttpSession
4. Hidden form fields
5. Other application-level authentication/token mechanisms

For this syllabus, focus on:

```text
Cookies
URL Rewriting
HttpSession
```

---

# 29. Session Management

**Session Management** is the process of maintaining information about a user's interaction with a web application across multiple HTTP requests.

Example:

```text
Login
  |
  v
Dashboard
  |
  v
Profile
  |
  v
Orders
  |
  v
Logout
```

The application needs to associate all these requests with the same session.

---

# 30. Cookies

A **cookie** is a small piece of data stored by the browser and associated with a website/domain.

Servlet example:

```java
Cookie cookie =
    new Cookie("username", "Akash");

response.addCookie(cookie);
```

Reading cookies:

```java
Cookie[] cookies =
    request.getCookies();
```

Then:

```java
for (Cookie cookie : cookies) {

    if (cookie.getName().equals("username")) {

        String username =
            cookie.getValue();
    }
}
```

---

# 31. Cookie Working

```text
First Response
Server
  |
  | Set-Cookie
  v
Browser
  |
  | stores cookie
  |
  v
Next Request
Browser
  |
  | Cookie
  v
Server
```

Cookies are commonly used to maintain identifiers and preferences.

### Important security note

Do not store sensitive information directly in a cookie unless the application design explicitly protects it. Session identifiers should be protected and transmitted over HTTPS.

---

# 32. URL Rewriting

URL rewriting adds session information or other parameters to the URL.

Example:

```text
profile?username=Akash
```

Servlets provide:

```java
response.encodeURL("profile");
```

The container can encode the URL when necessary for session tracking.

### When is URL rewriting useful?

It can be useful when cookies are unavailable or disabled.

---

# 33. Cookies vs URL Rewriting

| Feature | Cookies | URL Rewriting |
|---|---|---|
| Storage | Browser cookie store | URL |
| Visible in URL | No | Yes |
| Works without cookies | Not for cookie-based tracking | Can |
| URL exposure | Lower | Higher |
| Common session support | Yes | Fallback/alternative |

Avoid putting sensitive information into URLs.

---

# 34. HttpSession

`HttpSession` provides a convenient way to maintain session data for a user.

Example:

```java
HttpSession session =
    request.getSession();
```

Store data:

```java
session.setAttribute(
    "username",
    "Akash"
);
```

Retrieve data:

```java
String username =
    (String) session.getAttribute("username");
```

---

# 35. HttpSession Methods

Important methods:

### `getSession()`

Gets the current session or creates one if necessary.

```java
HttpSession session =
    request.getSession();
```

### `getSession(false)`

Gets the existing session if one exists; otherwise returns `null`.

This is useful when checking whether a user is already logged in.

```java
HttpSession session =
    request.getSession(false);
```

### `setAttribute()`

```java
session.setAttribute(
    "username",
    "Akash"
);
```

### `getAttribute()`

```java
session.getAttribute("username");
```

### `removeAttribute()`

```java
session.removeAttribute("username");
```

### `invalidate()`

Ends the session.

```java
session.invalidate();
```

---

# 36. Session Example

Login servlet:

```java
String username =
    request.getParameter("username");

HttpSession session =
    request.getSession();

session.setAttribute(
    "username",
    username
);
```

Dashboard servlet:

```java
HttpSession session =
    request.getSession(false);

if (session != null) {

    String username =
        (String) session.getAttribute("username");

    response.getWriter().println(
        "Welcome " + username
    );
}
```

---

# 37. Login/Logout Flow

A basic session-based login system:

```text
              Login Form
                  |
                  v
            LoginServlet
                  |
            Validate user
                  |
             Successful?
              /       \
            No         Yes
            |           |
            v           v
         Error      Create Session
                        |
                        v
                    Dashboard
                        |
                        v
                      Logout
                        |
                        v
                session.invalidate()
```

---

# 38. Login Implementation

HTML:

```html
<form action="login" method="post">

    <input
        type="text"
        name="username"
        placeholder="Username">

    <input
        type="password"
        name="password"
        placeholder="Password">

    <button type="submit">
        Login
    </button>

</form>
```

Servlet:

```java
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username =
            request.getParameter("username");

        String password =
            request.getParameter("password");

        if ("admin".equals(username)
                && "1234".equals(password)) {

            HttpSession session =
                request.getSession();

            session.setAttribute(
                "username",
                username
            );

            response.sendRedirect("dashboard");

        } else {

            response.getWriter()
                    .println("Invalid Login");
        }
    }
}
```

> This hard-coded example is only for learning. Real applications should validate credentials against a secure authentication system and never store plaintext passwords.

---

# 39. Protecting a Dashboard

```java
@WebServlet("/dashboard")
public class DashboardServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
            request.getSession(false);

        if (session == null
            || session.getAttribute("username") == null) {

            response.sendRedirect("login.html");

            return;
        }

        String username =
            (String) session.getAttribute("username");

        response.setContentType("text/html");

        response.getWriter().println(
            "<h1>Welcome " + username + "</h1>"
        );
    }
}
```

---

# 40. Logout Implementation

```java
@WebServlet("/logout")
public class LogoutServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
            request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect("login.html");
    }
}
```

### Main logout operation

```java
session.invalidate();
```

This invalidates the user's current session.

---

# 41. Session Timeout

A session should not remain active forever.

Timeout can be configured programmatically:

```java
session.setMaxInactiveInterval(30 * 60);
```

The value is in seconds.

```text
30 × 60 = 1800 seconds
```

It can also be configured using deployment configuration.

---

# 42. Session Management Summary

```text
HTTP
 |
 | stateless
 v
Need to associate requests
 |
 +------ Cookies
 |
 +------ URL Rewriting
 |
 +------ HttpSession
 |
 v
Login
 |
 v
Session Created
 |
 v
User Requests
 |
 v
Session Checked
 |
 v
Logout
 |
 v
Session Invalidated
```

---

# 43. What is a Java EE Application?

Java EE was the enterprise Java platform used for building large-scale, multi-tier applications.

The platform later evolved under the Jakarta EE name.

Java EE/Jakarta EE provides APIs and specifications for things such as:

- Web applications
- Servlets
- Enterprise components
- Persistence
- Transactions
- Security
- Messaging
- Dependency injection
- Web services

### Important terminology

```text
Java EE
   |
   | evolved into
   v
Jakarta EE
```

Older university syllabi may continue to use the term **Java EE**.

---

# 44. What is a Java EE Application Server?

A **Java EE application server** is a server runtime that provides enterprise services and manages Java EE/Jakarta EE application components.

It provides a managed environment in which enterprise applications can run.

Examples include:

- WildFly
- Payara
- Oracle WebLogic
- Eclipse GlassFish

Tomcat is primarily a **Servlet/Jakarta Servlet container**, not a full Java EE/Jakarta EE application server.

---

# 45. Services Provided by an Enterprise Application Server

An enterprise application server can provide services such as:

## 1. Web / Servlet Services

Runs web components such as:

```text
Servlets
```

and, depending on the runtime, other web technologies.

---

## 2. Dependency Injection

Helps components obtain required dependencies without manually constructing everything.

Conceptually:

```text
Component A
    |
    | needs
    v
Component B
```

The container can manage the relationship.

---

## 3. Transaction Management

Helps applications manage database and other transactional operations.

Example:

```text
Transfer ₹1000
   |
   +-- Debit Account A
   |
   +-- Credit Account B
```

Both operations should be handled consistently.

---

## 4. Security

Application servers can provide mechanisms for:

- Authentication
- Authorization
- Role-based access
- Secure application resources

---

## 5. Persistence / Database Integration

Enterprise applications commonly interact with databases.

Persistence technologies can manage objects and database records.

---

## 6. Messaging

Enterprise applications may need asynchronous communication.

Messaging services can support:

```text
Application A
     |
     | message
     v
Message System
     |
     v
Application B
```

---

## 7. Naming Services

Naming services help components locate resources such as:

- Data sources
- Enterprise resources
- Other managed components

---

## 8. Connection Pooling

Opening a database connection repeatedly can be expensive.

A connection pool maintains reusable connections.

```text
Application
    |
    v
Connection Pool
 /    |    \
DB Connection
DB Connection
DB Connection
```

---

## 9. Lifecycle Management

The server/container manages the lifecycle of application components.

It can:

- Create objects
- Initialize them
- Provide dependencies
- Manage resources
- Destroy components when required

---

## 10. Concurrency and Resource Management

The runtime manages resources and concurrent requests so applications can serve multiple users.

---

# 46. Java SE vs Java EE vs Java ME

| Feature | Java SE | Java EE | Java ME |
|---|---|---|---|
| Full form | Java Standard Edition | Java Enterprise Edition | Java Micro Edition |
| Main focus | Core Java | Enterprise applications | Resource-constrained devices |
| Examples | Collections, Threads, I/O | Servlets, enterprise APIs, transactions | Small/embedded environments |
| Typical use | Desktop, utilities, core programming | Web and enterprise systems | Embedded/mobile-era environments |
| Target | General-purpose Java | Enterprise/server applications | Limited-resource devices |

### Easy memory trick

```text
SE → Standard / Core Java
EE → Enterprise
ME → Micro / constrained environments
```

---

# 47. Relationship Between Java SE and Java EE

Java EE builds on Java SE concepts.

```text
Java SE
  |
  +-- OOP
  +-- Collections
  +-- Exceptions
  +-- Threads
  +-- I/O
  |
  v
Java EE / Jakarta EE
  |
  +-- Servlets
  +-- Enterprise APIs
  +-- Transactions
  +-- Security
  +-- Persistence
  +-- Messaging
```

A student should have a strong Java SE foundation before learning enterprise Java.

---

# 48. Servlet vs Application Server

These terms should not be confused.

### Servlet

A Java web component that processes HTTP requests.

### Servlet Container

Runtime that manages servlet execution.

Example:

```text
Tomcat
```

### Application Server

A broader enterprise runtime that can provide many enterprise services.

Examples:

```text
WildFly
Payara
GlassFish
WebLogic
```

---

# 49. Important Comparison: ServletConfig vs ServletContext

### ServletConfig

Use when configuration belongs to **one servlet**.

Example:

```text
PaymentServlet
     |
     +-- currency = INR
```

### ServletContext

Use when information belongs to the **whole application**.

Example:

```text
Application
     |
     +-- collegeName
     +-- applicationVersion
```

Memory trick:

```text
Config  → one
Context → all
```

---

# 50. Important Comparison: Cookies vs HttpSession

| Feature | Cookie | HttpSession |
|---|---|---|
| Main location | Client/browser | Server-side session state |
| Created/used by | Browser + server | Servlet application |
| Purpose | Store small client-side data / identifiers | Maintain server-side user session state |
| Access | `request.getCookies()` | `request.getSession()` |
| Example | Preference / session identifier | Logged-in username |
| Security | Client-controlled data | Application controls session data |

In practice, `HttpSession` commonly uses a session identifier that the browser sends back, often through a cookie.

---

# 51. Important Exam Concepts

## 51.1 Why is HTTP called stateless?

Because each HTTP request is treated independently by the protocol; the protocol itself does not automatically maintain application-level user state between requests.

Applications add state management using mechanisms such as sessions and cookies.

---

## 51.2 Why is init() not called for every request?

Because initialization is intended to happen once for a servlet instance.

Requests are normally processed through:

```text
service()
   |
   +-- doGet()
   +-- doPost()
```

---

## 51.3 Why is destroy() needed?

It gives the servlet an opportunity to release resources before the servlet instance is removed.

---

## 51.4 Why use doGet() and doPost() instead of service()?

`HttpServlet` uses `service()` to dispatch HTTP requests to method-specific handlers such as:

```text
GET  → doGet()
POST → doPost()
```

This makes HTTP request handling clearer.

---

## 51.5 Can multiple users access the same servlet?

Yes.

A servlet container can process requests from multiple users, commonly using multiple threads.

Therefore, servlet code must be written carefully with respect to shared mutable state.

---

# 52. Very Important: Servlet Thread Safety

A common mistake is storing request-specific data in servlet instance variables.

Avoid:

```java
public class LoginServlet extends HttpServlet {

    private String username;

}
```

Why?

A servlet instance can serve requests from multiple users.

Instead, keep request-specific data in local variables:

```java
protected void doPost(...) {

    String username =
        request.getParameter("username");
}
```

### Rule

> Keep request-specific data in local variables, request/session attributes, or other appropriately scoped storage rather than unsafe shared servlet fields.

---

# 53. Complete Topic Flow

For revision, remember the entire unit as:

```text
Servlet
   |
   v
Servlet Container
   |
   v
Servlet Lifecycle
   |
   +---- init()
   |
   +---- service()
            |
            +---- doGet()
            |
            +---- doPost()
   |
   +---- destroy()
   |
   v
Configuration
   |
   +---- web.xml
   |
   +---- @WebServlet
   |
   +---- ServletConfig
   |
   +---- ServletContext
   |
   v
HTTP is Stateless
   |
   v
Session Management
   |
   +---- Cookies
   |
   +---- URL Rewriting
   |
   +---- HttpSession
   |
   v
Login / Logout
   |
   v
Java EE / Jakarta EE
   |
   v
Application Server + Enterprise Services
```

---

# 54. University Exam Questions — Given Questions

## Q1. Differentiate between Java SE, Java EE, and Java ME.

**Answer points:**

- Full forms
- Purpose
- Target environment
- Major features
- Examples
- Typical applications

Use the comparison table from Section 46.

---

## Q2. What is ServletConfig?

**Answer points:**

- Servlet-specific configuration object.
- Provides initialization parameters for a particular servlet.
- Can be accessed using `getServletConfig()`.
- Important methods:
  - `getInitParameter()`
  - `getInitParameterNames()`
  - `getServletName()`
  - `getServletContext()`

---

## Q3. Explain Session Management in Servlets.

**Answer points:**

- HTTP is stateless.
- Multiple requests need to be associated with the same user.
- Session management maintains user-related state.
- Techniques:
  - Cookies
  - URL rewriting
  - HttpSession
  - Hidden form fields
- Explain login → session → dashboard → logout flow.
- Mention `session.invalidate()` during logout.

---

## Q4. [Blank in supplied question list]

The original question list contains a blank Question 4. A suitable related question is:

**Q4. Differentiate between ServletConfig and ServletContext.**

---

## Q5. List and explain the services provided by a Java EE application server.

**Answer points:**

- Web/Servlet services
- Dependency injection
- Transaction management
- Security
- Persistence/database integration
- Messaging
- Naming services
- Connection pooling
- Lifecycle management
- Resource/concurrency management

---

## Q6. What is a Servlet?

**Answer points:**

- Java web component
- Runs inside servlet container
- Processes client requests
- Generates responses
- Commonly handles HTTP requests
- Example: `HttpServlet`

---

## Q7. Explain doGet() and doPost() methods.

**Answer points:**

- Both are methods of `HttpServlet`.
- `doGet()` handles GET.
- `doPost()` handles POST.
- Compare request data location, visibility, common use cases, and practical examples.

---

# 55. Additional Important Exam Questions

## Short-answer questions

1. What is a Servlet Container?
2. What is Tomcat?
3. What is the Servlet lifecycle?
4. What is the purpose of `init()`?
5. What is the purpose of `service()`?
6. What is the purpose of `destroy()`?
7. What is `HttpServlet`?
8. What is `@WebServlet`?
9. What is `web.xml`?
10. What is URL mapping?
11. What is ServletConfig?
12. What is ServletContext?
13. What is an application attribute?
14. What is HTTP statelessness?
15. What is session management?
16. What is a cookie?
17. What is URL rewriting?
18. What is HttpSession?
19. What does `session.invalidate()` do?
20. What is session timeout?
21. What is a Java EE application server?
22. What is Jakarta EE?
23. Differentiate Servlet Container and Application Server.
24. What is the difference between application scope and session scope?
25. Why should request-specific data not normally be stored in servlet instance variables?

---

# 56. Long-answer / 5-Mark Questions

1. Explain the complete Servlet lifecycle with a neat diagram.
2. Explain `init()`, `service()`, and `destroy()` with examples.
3. Explain the working of `doGet()` and `doPost()`.
4. Differentiate GET and POST methods.
5. Explain web.xml and annotation-based servlet configuration.
6. Explain ServletConfig with a suitable example.
7. Explain ServletContext with a suitable example.
8. Differentiate ServletConfig and ServletContext.
9. Explain why session management is required in HTTP.
10. Explain different session tracking techniques.
11. Explain cookies and URL rewriting.
12. Explain HttpSession with important methods.
13. Explain login and logout implementation using HttpSession.
14. Explain Java EE/Jakarta EE application server and its services.
15. Differentiate Java SE, Java EE, and Java ME.
16. Explain Servlet Container and its responsibilities.
17. Explain how a browser request reaches a Servlet and how the response returns.
18. Explain session timeout and session invalidation.
19. Explain servlet thread safety and shared state.
20. Compare Cookies, URL Rewriting, and HttpSession.

---

# 57. Diagram-Based Exam Questions

Students should practice drawing these diagrams.

## Servlet Request-Response

```text
Browser
   |
HTTP Request
   |
   v
Servlet Container
   |
URL Mapping
   |
   v
Servlet
   |
Processing
   |
HTTP Response
   |
   v
Browser
```

## Servlet Lifecycle

```text
       Servlet Loaded
             |
             v
        Object Created
             |
             v
           init()
             |
             v
          service()
             |
        +----+----+
        |         |
      GET       POST
        |         |
     doGet()   doPost()
        |         |
        +----+----+
             |
          Response
             |
        More Requests
             |
             v
          destroy()
```

## Session Management

```text
Login Request
      |
      v
Validate User
      |
      v
Create Session
      |
      v
Store User Data
      |
      v
Dashboard
      |
      v
Other Requests
      |
      v
Check Session
      |
      v
Logout
      |
      v
Invalidate Session
```

---

# 58. Quick Revision Table

| Topic | Remember This |
|---|---|
| Servlet | Java web component |
| Container | Manages servlet lifecycle/execution |
| Tomcat | Servlet/container runtime |
| `init()` | Initialization, normally once |
| `service()` | Dispatches/processes requests |
| `doGet()` | Handles GET |
| `doPost()` | Handles POST |
| `destroy()` | Cleanup before removal |
| `web.xml` | XML-based deployment configuration |
| `@WebServlet` | Annotation-based servlet mapping |
| ServletConfig | One servlet's configuration |
| ServletContext | Whole application's context |
| HTTP | Stateless by default |
| Cookie | Client-side stored data |
| URL Rewriting | State/session information through URL encoding |
| HttpSession | Server-side user session state |
| `invalidate()` | Ends session |
| Java EE | Enterprise Java platform; evolved into Jakarta EE |
| Application Server | Provides managed enterprise runtime/services |

---

# 59. Important One-Line Differences

### ServletConfig vs ServletContext

```text
ServletConfig  → one servlet
ServletContext → entire application
```

### doGet vs doPost

```text
doGet()  → GET request
doPost() → POST request
```

### Cookie vs Session

```text
Cookie  → client/browser-side data
Session → server-side application state
```

### web.xml vs Annotation

```text
web.xml      → XML configuration
@WebServlet  → annotation configuration
```

### Servlet vs Servlet Container

```text
Servlet          → application component
Servlet Container → runs/manages servlet
```

### Servlet Container vs Application Server

```text
Servlet Container
→ primarily manages web/Servlet components

Application Server
→ broader enterprise runtime and services
```

---

# 60. Exam Answer Writing Pattern

For a 5-mark theory question, use this structure:

```text
1. Definition
2. Purpose / Need
3. Working
4. Diagram
5. Example
6. Advantages / Important points
7. Conclusion
```

Example for:

> Explain Servlet Lifecycle.

Write:

```text
Definition
   ↓
What lifecycle means
   ↓
init()
   ↓
service()
   ↓
doGet()/doPost()
   ↓
destroy()
   ↓
Lifecycle diagram
   ↓
Small code example
   ↓
Important points
```

This gives the examiner both conceptual explanation and practical understanding.

---

# 61. Final Revision Checklist

Before the exam, make sure you can explain without notes:

- [ ] What is a Servlet?
- [ ] What is a Servlet Container?
- [ ] What is Tomcat?
- [ ] Servlet request-response architecture
- [ ] Servlet lifecycle
- [ ] `init()`
- [ ] `service()`
- [ ] `doGet()`
- [ ] `doPost()`
- [ ] `destroy()`
- [ ] GET vs POST
- [ ] web.xml
- [ ] `@WebServlet`
- [ ] ServletConfig
- [ ] ServletContext
- [ ] Application-level attributes
- [ ] HTTP statelessness
- [ ] Need for sessions
- [ ] Cookies
- [ ] URL rewriting
- [ ] HttpSession
- [ ] Session timeout
- [ ] Login implementation
- [ ] Logout implementation
- [ ] `session.invalidate()`
- [ ] Java SE vs Java EE vs Java ME
- [ ] Java EE / Jakarta EE
- [ ] Application server
- [ ] Services of application server
- [ ] Servlet Container vs Application Server
- [ ] Servlet thread-safety basics

---

# 62. Practice Questions Without Answers

### Very Short

1. Define a Servlet.
2. Define Servlet Container.
3. What is `init()`?
4. What is `destroy()`?
5. What is `HttpSession`?
6. What is ServletConfig?
7. What is ServletContext?
8. What is URL rewriting?
9. What is HTTP statelessness?
10. What does `session.invalidate()` do?

### Short / Medium

11. Explain the Servlet lifecycle.
12. Explain the role of `service()`.
13. Differentiate GET and POST.
14. Explain annotation-based Servlet configuration.
15. Explain web.xml.
16. Explain ServletConfig with an example.
17. Explain ServletContext with an example.
18. Explain the need for session management.
19. Explain cookies.
20. Explain URL rewriting.
21. Explain HttpSession.
22. Explain login/logout using HttpSession.

### Long Answer

23. Explain the complete Servlet architecture with a diagram.
24. Explain Servlet lifecycle with all major methods and a practical example.
25. Compare ServletConfig and ServletContext.
26. Explain all major session tracking techniques.
27. Explain login and logout using HttpSession.
28. Differentiate Java SE, Java EE, and Java ME.
29. Explain Java EE/Jakarta EE application server and its services.
30. Differentiate Servlet Container and Java EE application server.
31. Explain servlet thread safety and why shared instance variables can be problematic.
32. Explain how an HTTP request reaches a servlet and how the response is returned.

---

# 63. Final Concept Map

```text
                    JAVA WEB APPLICATION
                            |
                            v
                     Servlet Container
                            |
                     +------+------+
                     |             |
                 Servlet       Configuration
                     |             |
                     |        +----+----+
                     |        |         |
                     |     web.xml   Annotation
                     |
                Servlet Lifecycle
                     |
          +----------+----------+
          |          |          |
        init()    service()   destroy()
                     |
              +------+------+
              |             |
           doGet()       doPost()
              |
              v
          HTTP Request
              |
              v
        HTTP is Stateless
              |
              v
        Session Management
              |
       +------+------+------+
       |             |      |
    Cookies    URL Rewrite  HttpSession
                              |
                         Login / Logout
                              |
                              v
                      Enterprise Java
                              |
                       Java EE / Jakarta EE
                              |
                              v
                      Application Server
                              |
       +----------+-----------+-----------+
       |          |           |           |
   Security   Transactions  Messaging  Persistence
       |
       v
 Enterprise Web Applications
```

---

# Key Takeaway

A Servlet application can be understood as one continuous story:

```text
Client sends request
        ↓
Servlet Container receives it
        ↓
Servlet is selected using URL mapping
        ↓
Lifecycle methods manage the Servlet
        ↓
doGet()/doPost() process HTTP requests
        ↓
Application configuration comes from
web.xml / annotations / ServletConfig / ServletContext
        ↓
Because HTTP is stateless,
session management is required
        ↓
Cookies / URL Rewriting / HttpSession
        ↓
Login creates a session
        ↓
Protected pages check the session
        ↓
Logout invalidates the session
        ↓
Enterprise applications can run inside
a Java EE/Jakarta EE application server
```

**If students understand this flow, the individual theory topics become much easier to remember.**
