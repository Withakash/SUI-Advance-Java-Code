# Assignment: Java EE and Servlets


### Q1. What is Java Enterprise Edition (Java EE)?

Explain the purpose of Java EE and where it is used.

### Q2. What is a Java EE Application Server?

Explain its role in developing and running enterprise applications.

### Q3. List and explain the major services provided by a Java EE Application Server.

### Q4. What is a Servlet?

Explain the purpose of Servlets in web application development.

### Q5. What is ServletConfig?

Explain its purpose and how it is used by a Servlet.

### Q6. Explain the `doGet()` and `doPost()` methods.

Mention the major differences between them.

### Q7. Differentiate between Java SE, Java EE, and Java ME.

Compare them based on purpose, applications, features, and examples.

### Q8. Draw and explain the Java EE Architecture.

Your diagram should clearly show the relationship between:

* Client
* Web Server / Application Server
* Web Container
* Servlets
* Business Components
* Database

### Q9. Draw and explain the request-response flow of a Servlet.

Show the flow:

```text
Client / Browser
       ↓
HTTP Request
       ↓
Web Server / Tomcat
       ↓
Servlet
       ↓
Processing
       ↓
HTTP Response
       ↓
Client / Browser
```

Explain each step in the flow.

### Q10. Draw and explain the Servlet Lifecycle.

Your diagram should include:

```text
Servlet Loading
      ↓
   init()
      ↓
   service()
      ↓
doGet() / doPost()
      ↓
 destroy()
```



### Q11. Explain Session Management in Servlets.

### Answer

Session Management in Servlets is the process of maintaining user-specific information across multiple HTTP requests. HTTP is stateless, which means each request is independent and the server does not automatically remember previous requests. However, web applications such as banking systems, e-commerce applications, and login-based portals need to maintain information about users across multiple requests.

Servlets provide session management mechanisms that allow the application to identify requests belonging to the same user and maintain state between those requests.

The major session management techniques include cookies, URL rewriting, hidden form fields, and `HttpSession`.

The general process is:

```text
Client Request
      ↓
Identify Session
      ↓
Retrieve Session Data
      ↓
Process Request
      ↓
Update Session if Required
      ↓
Send Response
```

Session management is commonly used for login authentication, shopping carts, user preferences, and other user-specific operations.

---

### Q12. Describe Different Techniques of Session Management in Servlets.

The major techniques are:

### 1. Cookies

Cookies are small pieces of data stored by the browser. The browser sends the cookie with subsequent requests. Cookies can also carry a session identifier.

```text
Server
  ↓
Set-Cookie
  ↓
Browser stores cookie
  ↓
Browser sends cookie
  ↓
Server identifies session
```

---

### 2. URL Rewriting

URL rewriting maintains state by adding information, commonly a session identifier, to URLs.

Example:

```text
/profile;jsessionid=ABC123
```

Servlet provides methods such as:

```java
response.encodeURL(url);
```

URL rewriting can be useful when cookies cannot be used.

---

### 3. Hidden Form Fields

A hidden field can store information inside an HTML form.

Example:

```html
<input type="hidden"
       name="userId"
       value="101">
```

When the form is submitted, the value is sent to the server.

Its limitation is that it mainly works with form submissions and must be explicitly included in forms.

---

### 4. HttpSession

`HttpSession` is a Servlet API mechanism for maintaining user-specific information across multiple HTTP requests.

Example:

```java
HttpSession session = request.getSession();

session.setAttribute("username", "Akash");
```

Later:

```java
String username =
    (String) session.getAttribute("username");
```

The session can be destroyed using:

```java
session.invalidate();
```

---

### Q13. What is HttpSession?

> **HttpSession is an interface in the Servlet API used to maintain information associated with a particular user across multiple HTTP requests.**

It provides methods for creating/accessing a session, storing and retrieving attributes, removing attributes, and invalidating the session.

Example:

```java
HttpSession session = request.getSession();

session.setAttribute("username", "Akash");

String username =
    (String) session.getAttribute("username");
```

During logout:

```java
session.invalidate();
```

Important methods include:

```text
getSession()
getSession(false)
setAttribute()
getAttribute()
removeAttribute()
invalidate()
getId()
isNew()
setMaxInactiveInterval()
```

---


