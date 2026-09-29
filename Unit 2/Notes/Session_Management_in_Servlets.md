# Session Management in Servlets

> **Subject:** Advanced Java  
> **Topic:** Session Management in Servlets  
> **Focus:** Theory + Exam Preparation + Servlet Concepts

---

# 1. Introduction

HTTP is the foundation of web communication.

A browser sends a request to a web server, and the server sends a response.

```text
Client / Browser
       |
       | HTTP Request
       ↓
   Web Server
       |
       | HTTP Response
       ↓
Client / Browser
```

The important property of HTTP is that it is **stateless**.

This means that the server does not automatically remember what happened in a previous request.

For example:

```text
Request 1:
User logs in

Request 2:
User opens profile

Request 3:
User opens dashboard

Request 4:
User logs out
```

HTTP treats these as separate requests.

The web application therefore needs a mechanism to identify that all these requests belong to the same user interaction.

This mechanism is called **Session Management**.

---

# 2. What is Session Management?

## Definition

> **Session Management is the process of maintaining information about a particular user across multiple HTTP requests during a session.**

In simple words:

> Session Management allows a web application to remember a user's information from one request to another.

For example, after successful login, the application may need to remember:

```text
Username = Akash
Logged In = true
Role = Student
```

When the same user requests another page, the application can use this information.

---

# 3. Why is Session Management Required?

HTTP is stateless.

Suppose a user sends:

```text
Request 1 → Login
```

The server validates the username and password.

Then the user sends:

```text
Request 2 → Open Dashboard
```

How does the server know that the user has already logged in?

Without session management:

```text
Request 1 → Login successful
Request 2 → Server does not automatically remember Request 1
```

Therefore, a mechanism is required to maintain the user's state.

---

# 4. Stateless HTTP

## Meaning of Stateless

**Stateless** means:

> Each HTTP request is treated independently, and HTTP does not automatically maintain information about previous requests.

Consider:

```text
Client                         Server

   | -------- Request 1 --------> |
   | <------- Response 1 -------- |
   |                              |
   | -------- Request 2 --------> |
   | <------- Response 2 -------- |
   |                              |
   | -------- Request 3 --------> |
   | <------- Response 3 -------- |
```

The server does not inherently know that:

```text
Request 1
Request 2
Request 3
```

came from the same user.

Session management solves this problem.

---

# 5. Real-World Example

Consider an online shopping application.

A user performs:

```text
Login
  ↓
Browse Products
  ↓
Add Product to Cart
  ↓
View Cart
  ↓
Checkout
  ↓
Logout
```

The application needs to remember:

```text
User = Akash
Login Status = Logged In
Cart = Product A, Product B
```

Without state management, every request would be treated independently.

Session management allows the application to maintain this information during the user's interaction.

---

# 6. Session

A **session** represents a series of related requests made by a particular user during a period of interaction with a web application.

Example:

```text
                    SESSION
                       |
        +--------------+--------------+
        |              |              |
      Login          Profile         Cart
     Request         Request        Request
```

These requests can belong to the same session.

Therefore:

> A session allows a web application to treat multiple HTTP requests as belonging to the same user interaction.

---

# 7. Session Management in Servlets

Servlets provide mechanisms for maintaining user state across multiple requests.

The major session management techniques are:

```text
Session Management
       |
       +----------------------+
       |          |           |
    Cookies   URL Rewriting   HttpSession
       |
       +----------------------+
```

Other historical techniques may include hidden form fields, but the important Servlet techniques for this topic are:

1. Cookies
2. URL Rewriting
3. HttpSession

---

# 8. Session Tracking

The process of identifying and maintaining the relationship between multiple requests from the same client is called:

> **Session Tracking**

For example:

```text
Request 1
   ↓
Session ID = ABC123
   ↓
Request 2
   ↓
Session ID = ABC123
   ↓
Request 3
   ↓
Session ID = ABC123
```

The server can therefore associate these requests with the same session.

---

# 9. Session ID

A session generally has a unique identifier called a **Session ID**.

Example:

```text
JSESSIONID = ABC123XYZ
```

Conceptually:

```text
Client
  |
  | Session ID
  ↓
Server
  |
  ↓
Session Data
```

The session ID identifies the session.

The actual session data can be maintained by the server.

---

# 10. Techniques of Session Management

Servlet applications can maintain state using different techniques.

The important techniques are:

```text
1. Cookies
2. URL Rewriting
3. HttpSession
4. Hidden Form Fields
```

Let's understand each one.

---

# 11. Technique 1: Cookies

## What is a Cookie?

> A cookie is a small piece of data stored by the web browser and associated with a website.

Example:

```text
username=akash
```

or:

```text
JSESSIONID=ABC123
```

The browser can send the cookie back to the server with subsequent requests.

---

# 12. Cookie Working

The general flow is:

```text
             First Request
Browser ------------------------> Server
                                      |
                                      |
Browser <------------------------ Response
              Set-Cookie

Browser stores cookie

Browser ------------------------> Server
              Cookie
```

Example:

```text
Set-Cookie: username=akash
```

The browser stores:

```text
username = akash
```

Later it can send:

```text
Cookie: username=akash
```

---

# 13. Cookies in Servlets

Servlet provides the `Cookie` class.

Import:

```java
import jakarta.servlet.http.Cookie;
```

Create a cookie:

```java
Cookie cookie = new Cookie("username", "Akash");
```

Send the cookie to the browser:

```java
response.addCookie(cookie);
```

Read cookies:

```java
Cookie[] cookies = request.getCookies();
```

Read a particular cookie:

```java
for (Cookie cookie : cookies) {

    if (cookie.getName().equals("username")) {

        String value = cookie.getValue();

        System.out.println(value);
    }
}
```

---

# 14. Advantages of Cookies

- Simple mechanism
- Easy to implement
- Stored on the client side
- Useful for preferences
- Can be used for session tracking
- Supported by modern browsers

---

# 15. Limitations of Cookies

### 1. Cookies are client-side

The browser stores the cookie.

### 2. Cookies can be disabled

Users can configure browsers to reject cookies.

### 3. Limited storage

Cookies are intended for small pieces of data.

### 4. Security concerns

Sensitive information should not be stored directly in plain cookies.

For example, avoid:

```text
password=123456
```

as a plain cookie.

---

# 16. Cookie and Session ID

A very important concept:

**HttpSession and Cookie are not the same thing.**

A Servlet container can use a cookie to carry the session ID.

For example:

```text
Browser                         Tomcat

   | ---- JSESSIONID=ABC ------> |
   |                              |
   |                    Session ABC
   |                    username=Akash
   |                              |
```

Here:

```text
JSESSIONID=ABC
```

identifies the session.

The session data can be maintained on the server.

---

# 17. Technique 2: URL Rewriting

## Definition

> **URL Rewriting is a session tracking technique in which information, commonly a session identifier, is added to the URL.**

Example:

```text
http://example.com/profile?user=akash
```

For Servlet session tracking, a URL may conceptually contain:

```text
/profile;jsessionid=ABC123
```

The session identifier allows the server to identify the session.

---

# 18. Why URL Rewriting?

Suppose cookies are disabled.

Normally, the session ID may be carried using a cookie.

If cookies cannot be used, URL rewriting can provide another way to carry the session identifier.

Conceptually:

```text
Cookies enabled

Browser
   |
   | JSESSIONID
   ↓
Server
```

Without cookies:

```text
Browser
   |
   | URL + session identifier
   ↓
Server
```

---

# 19. URL Rewriting in Servlets

Servlet provides:

```java
response.encodeURL(url)
```

Example:

```java
String url = response.encodeURL("profile");
```

The Servlet container can encode the URL with session information when necessary.

The exact result depends on whether URL rewriting is required.

---

# 20. Advantages of URL Rewriting

- Can work when cookies are disabled
- Does not require client-side cookie storage
- Supported by Servlet session tracking mechanisms
- Useful as an alternative session tracking mechanism

---

# 21. Limitations of URL Rewriting

### 1. URLs become longer

Example:

```text
/profile;jsessionid=ABC123
```

### 2. Session information can appear in URLs

This can create security and privacy concerns.

### 3. Every relevant URL must be encoded

If URLs are generated manually and not encoded correctly, session tracking may fail.

### 4. URLs can be copied or shared

A URL containing session information can potentially be exposed through browser history, logs, bookmarks, or other mechanisms.

Therefore, sensitive information should never be placed directly into URLs.

---

# 22. Technique 3: HttpSession

## What is HttpSession?

> **HttpSession is a Servlet API interface used to maintain information associated with a particular user across multiple HTTP requests.**

It is one of the most important session management mechanisms in Servlet programming.

Package:

```java
jakarta.servlet.http.HttpSession
```

---

# 23. Why HttpSession?

Suppose a user logs in.

The application can store:

```text
username = Akash
loggedIn = true
role = student
```

inside the session.

Later, another Servlet can retrieve this information.

Conceptually:

```text
Login Servlet
      |
      | setAttribute()
      ↓
 HttpSession
      |
      | getAttribute()
      ↓
Dashboard Servlet
```

---

# 24. Getting an HttpSession

The Servlet request object provides:

```java
request.getSession()
```

Example:

```java
HttpSession session = request.getSession();
```

This obtains the current session or creates a new session if necessary.

---

# 25. `getSession()`

```java
HttpSession session = request.getSession();
```

Meaning:

> Get the current session. If there is no existing session, create one.

Conceptually:

```text
Existing Session?
       |
    +--+--+
    |     |
   Yes    No
    |     |
    ↓     ↓
Return   Create
Session  Session
```

---

# 26. `getSession(false)`

Servlet also provides:

```java
HttpSession session = request.getSession(false);
```

Meaning:

> Return the existing session if one exists, but do not create a new session.

This is especially useful when checking whether a user is already logged in.

Example:

```java
HttpSession session = request.getSession(false);

if (session != null) {

    // Existing session

} else {

    // No session
}
```

---

# 27. Storing Data in HttpSession

The method used is:

```java
setAttribute()
```

Example:

```java
session.setAttribute("username", "Akash");
```

Another example:

```java
session.setAttribute("loggedIn", true);
```

The session now contains application-specific information.

Conceptually:

```text
Session
------------------------
username  → Akash
loggedIn  → true
role      → student
```

---

# 28. Retrieving Data from HttpSession

Use:

```java
getAttribute()
```

Example:

```java
String username =
    (String) session.getAttribute("username");
```

The returned value has type `Object`, so type casting is often required.

Example:

```java
Boolean loggedIn =
    (Boolean) session.getAttribute("loggedIn");
```

---

# 29. Removing an Attribute

Use:

```java
session.removeAttribute("username");
```

This removes only that attribute.

Example:

```text
Before:

Session
----------------
username = Akash
role = student

removeAttribute("username")

After:

Session
----------------
role = student
```

---

# 30. Invalidating a Session

To completely invalidate a session:

```java
session.invalidate();
```

This is commonly used during logout.

Example:

```text
Login
  ↓
Session created
  ↓
username stored
  ↓
User works with application
  ↓
Logout
  ↓
session.invalidate()
  ↓
Session destroyed
```

---

# 31. Important HttpSession Methods

| Method | Purpose |
|---|---|
| `getSession()` | Gets current session or creates one |
| `getSession(false)` | Gets existing session without creating one |
| `setAttribute()` | Stores data in session |
| `getAttribute()` | Retrieves data from session |
| `removeAttribute()` | Removes one attribute |
| `invalidate()` | Invalidates the session |
| `getId()` | Returns session ID |
| `isNew()` | Checks whether session is newly created |
| `getCreationTime()` | Returns session creation time |
| `getLastAccessedTime()` | Returns last access time |
| `setMaxInactiveInterval()` | Sets inactive timeout |

---

# 32. Session ID and HttpSession

Students often confuse:

```text
Session ID
```

with:

```text
Session
```

They are different.

### Session

Contains user-specific session data.

```text
Session
--------------------
username = Akash
role = student
loggedIn = true
```

### Session ID

Identifies that session.

```text
JSESSIONID = ABC123
```

Think:

```text
Session ID → identifies the session
Session    → contains session information
```

---

# 33. How the Server Recognizes the Session

Consider:

```text
Request 1
   |
   ↓
Tomcat creates Session
   |
   ↓
Session ID = ABC123
```

The client carries the session identifier.

Later:

```text
Request 2
   |
   | Session ID = ABC123
   ↓
Tomcat
   |
   ↓
Find Session ABC123
   |
   ↓
username = Akash
```

Therefore the server can associate the request with the correct session.

---

# 34. Session Lifecycle

A session has a lifecycle.

```text
              Session
                |
                ↓
            Created
                |
                ↓
         Data is stored
                |
                ↓
       Multiple requests
                |
                ↓
       Data is retrieved
                |
         +------+------+
         |             |
       Timeout       Logout
         |             |
         +------+------+
                |
                ↓
           Invalidated
```

---

# 35. Session Timeout

A session may expire after a period of inactivity.

Example:

```text
User logs in
     ↓
Uses website
     ↓
Stops interacting
     ↓
Long period of inactivity
     ↓
Session expires
```

This is called:

> **Session Timeout**

Timeout is useful because it:

- prevents inactive sessions from remaining forever
- helps reduce server-side resource usage
- improves security for applications containing user-specific information

---

# 36. Setting Session Timeout

A timeout can be configured using:

```java
session.setMaxInactiveInterval(600);
```

The value is generally specified in seconds.

Here:

```text
600 seconds = 10 minutes
```

This means the session can expire after the specified period of inactivity.

---

# 37. Login Using HttpSession

A typical login process is:

```text
User
 ↓
Login Form
 ↓
Login Servlet
 ↓
Verify username/password
 ↓
Valid?
 ├── No → Error
 └── Yes
       ↓
Create/Get Session
       ↓
Store username
       ↓
Dashboard
```

Conceptually:

```java
HttpSession session = request.getSession();

session.setAttribute("username", "Akash");
```

Now the application can identify the logged-in user through the session.

---

# 38. Protecting a Page

Suppose `/dashboard` should only be accessible to logged-in users.

The Servlet can check:

```java
HttpSession session =
    request.getSession(false);

if (session != null &&
    session.getAttribute("username") != null) {

    // User is logged in

} else {

    // User is not logged in
}
```

The important idea is:

```text
Request
  ↓
Check Session
  ↓
Logged in?
  |
 +--+--+
 |     |
Yes    No
 |     |
 ↓     ↓
Allow  Login
       Page
```

---

# 39. Logout Using HttpSession

Logout generally means ending the authenticated session.

Example:

```java
HttpSession session =
    request.getSession(false);

if (session != null) {
    session.invalidate();
}
```

After invalidation:

```text
Session
   ↓
Invalid
```

The user should then be redirected to the login page.

---

# 40. Complete Login → Session → Logout Flow

```text
                  USER
                    |
                    ↓
               Login Page
                    |
                    ↓
          Username + Password
                    |
                    ↓
              Login Servlet
                    |
              Verify Credentials
                    |
              +-----+-----+
              |           |
           Invalid       Valid
              |           |
              ↓           ↓
          Error Page   Create Session
                           |
                           ↓
                 Store User Information
                           |
                           ↓
                       Dashboard
                           |
                           ↓
                  Other Protected Pages
                           |
                           ↓
                    Session Checked
                           |
                           ↓
                        Logout
                           |
                           ↓
                 session.invalidate()
                           |
                           ↓
                      Login Page
```

---

# 41. Cookies vs URL Rewriting vs HttpSession

| Feature | Cookies | URL Rewriting | HttpSession |
|---|---|---|---|
| Main idea | Data stored in browser | Information carried in URL | User data maintained through session |
| Storage | Client | URL/client request | Server-side session data |
| Requires cookies? | Yes | No | Session can use cookie or URL rewriting for ID |
| Easy to use | Yes | Moderate | Yes |
| Common purpose | Preferences / identification | Alternative session tracking | User session/state |
| Security considerations | Client-controlled data | URL exposure | Server-side session data |
| Servlet API | `Cookie` | `encodeURL()` | `HttpSession` |

---

# 42. Important Difference: Cookie vs HttpSession

This is an important exam point.

### Cookie

```text
Browser
   |
   ↓
Cookie
```

Cookie data is stored on the client.

### HttpSession

```text
Server
   |
   ↓
HttpSession
   |
   +--- username
   +--- role
   +--- login status
```

Session data is associated with the server-side session.

However, the session ID itself may be carried between browser and server using a cookie or URL rewriting.

Therefore:

> **A cookie can be used to carry a session ID, but a cookie and an HttpSession are not the same thing.**

---

# 43. Hidden Form Fields

Another traditional technique is the **hidden form field**.

Example:

```html
<input type="hidden"
       name="username"
       value="akash">
```

When the form is submitted, this value is sent to the server.

Conceptually:

```text
Form
 |
 +-- username
 |
 +-- password
 |
 +-- hidden session-related value
```

### Limitation

Hidden fields work mainly when the user moves through forms.

They are not automatically included in every HTTP request.

Therefore, they are less convenient for general session tracking compared with `HttpSession`.

---

# 44. Why HttpSession is Important

`HttpSession` provides a convenient API for maintaining user-specific state.

Without it, developers would need to manually manage state using mechanisms such as:

```text
Cookies
URL parameters
Hidden fields
```

With `HttpSession`:

```java
session.setAttribute("username", "Akash");
```

and later:

```java
session.getAttribute("username");
```

This makes session-related programming easier.

---

# 45. Session Management and Authentication

Session management is commonly used in authentication systems.

Authentication:

> **Who are you?**

Session management:

> **How do we remember that you have already authenticated during subsequent requests?**

Example:

```text
Login
  ↓
Authentication
  ↓
Successful
  ↓
Create/use Session
  ↓
Store user information
  ↓
Subsequent request
  ↓
Check Session
```

---

# 46. Important Security Concepts

Session management has security implications.

## 1. Do not store passwords in sessions unnecessarily

Avoid:

```text
password = 123456
```

A session should generally store only information required by the application.

For example:

```text
userId = 101
role = student
```

---

## 2. Invalidate session during logout

Use:

```java
session.invalidate();
```

---

## 3. Use session timeout

Inactive sessions should not remain active indefinitely.

---

## 4. Avoid sensitive information in URLs

Do not use:

```text
/login?password=123456
```

or put confidential information into rewritten URLs.

---

## 5. Protect session identifiers

A session ID should be treated as sensitive because someone who obtains a valid session identifier may potentially impersonate the associated session, depending on the application's security controls.

---

# 47. Advantages of Session Management

Session management provides:

- User identification across requests
- Login state maintenance
- Shopping cart management
- User preferences
- Multi-page workflows
- Personalized content
- Authentication state
- Temporary user-specific information

---

# 48. Disadvantages / Challenges

Session management also introduces challenges:

- Session timeout handling
- Session security
- Session hijacking risks
- Server-side memory/resource usage
- Distributed server/session management
- Cookie-disabled environments
- Correct logout handling

---

# 49. Frequently Asked Questions

## Q1. Why is session management required?

Because HTTP is stateless. The server does not automatically remember information from previous requests. Session management allows the application to maintain user-specific information across multiple requests.

---

## Q2. What is session tracking?

Session tracking is the process of identifying multiple requests as belonging to the same client/session.

---

## Q3. What is a session ID?

A session ID is a unique identifier associated with a user's session. It allows the server to associate subsequent requests with the correct session.

---

## Q4. What is HttpSession?

`HttpSession` is a Servlet API interface used to maintain user-specific information across multiple HTTP requests.

---

## Q5. What is `getSession()`?

```java
request.getSession()
```

gets the current session or creates one if no session exists.

---

## Q6. What is `getSession(false)`?

```java
request.getSession(false)
```

returns the existing session if one exists and does not create a new session when none exists.

---

## Q7. How do you store data in a session?

```java
session.setAttribute("username", "Akash");
```

---

## Q8. How do you retrieve session data?

```java
session.getAttribute("username");
```

---

## Q9. How do you remove one session attribute?

```java
session.removeAttribute("username");
```

---

## Q10. How do you destroy a session?

```java
session.invalidate();
```

---

# 50. University Exam Questions

## Question 1

### Explain Session Management in Servlets.

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

# 51. University Exam Question 2

## Describe Different Techniques of Session Management in Servlets.

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

# 52. University Exam Question 3

## What is HttpSession?

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

# 53. One-Minute Revision

Remember:

```text
HTTP
 ↓
Stateless
 ↓
Server doesn't automatically remember previous requests
 ↓
Need Session Management
 ↓
Session Tracking
 ↓
+-------------+-------------+-------------+
|             |             |             |
Cookies    URL Rewriting  Hidden Fields  HttpSession

```

For `HttpSession`:

```text
request.getSession()
        ↓
     Session
        ↓
setAttribute()
        ↓
Store data
        ↓
getAttribute()
        ↓
Retrieve data
        ↓
invalidate()
        ↓
Destroy session
```

---

# 54. Final Concept

The complete idea can be remembered as:

```text
              HTTP
                |
                ↓
            STATELESS
                |
                ↓
       Need to maintain state
                |
                ↓
         Session Management
                |
       +--------+--------+
       |        |        |
    Cookies   URL      HttpSession
              Rewrite
       |        |        |
       +--------+--------+
                |
                ↓
          Session Tracking
                |
                ↓
        Identify the user
                |
                ↓
       Maintain user state
                |
                ↓
       Login → Dashboard
                |
                ↓
             Logout
                |
                ↓
       session.invalidate()
```

> **Key line for the exam:**  
> **Session Management is the mechanism used by web applications to maintain user-specific state across multiple HTTP requests in a stateless HTTP environment.**
