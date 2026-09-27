# Servlets: web.xml, Annotation, ServletConfig, ServletContext

## Topics

1. web.xml and Annotation-based Configuration
2. ServletConfig
3. ServletContext
4. Application-level Configuration and Attributes
5. Practical Examples
6. Common Mistakes and Debugging
7. Exam Questions
8. Quick Revision

---

# 1. web.xml and Annotation-based Configuration

## What is Servlet Configuration?

When Tomcat receives:

`http://localhost:8080/MyApp/hello`

it needs to know which Servlet should handle `/hello`.

The configuration connects:

```text
URL
 ↓
Servlet
```

Two common approaches are:

- `web.xml`
- Annotations such as `@WebServlet`

Both can configure Servlet mappings.

---

# 2. web.xml

## What is web.xml?

`web.xml` is called the **Deployment Descriptor**.

It is an XML configuration file used by a Java web application to provide configuration information to the Servlet container.

Typical locations in Eclipse projects are:

```text
src/main/webapp/WEB-INF/web.xml
```

or:

```text
WebContent/WEB-INF/web.xml
```

## Why use web.xml?

It can define:

- Servlet classes
- Servlet names
- URL mappings
- Servlet initialization parameters
- Application-level initialization parameters
- Other web-application configuration

---

# 3. Basic web.xml Servlet Configuration

For Tomcat 10.1 / Jakarta Servlet 6.0:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<web-app
    xmlns="https://jakarta.ee/xml/ns/jakartaee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="
        https://jakarta.ee/xml/ns/jakartaee
        https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
    version="6.0">

    <servlet>
        <servlet-name>HelloServlet</servlet-name>

        <servlet-class>
            com.test.HelloServlet
        </servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>HelloServlet</servlet-name>
        <url-pattern>/hello</url-pattern>
    </servlet-mapping>

</web-app>
```

## Meaning

### `<servlet>`

Defines a Servlet.

### `<servlet-name>`

Gives it a logical name.

### `<servlet-class>`

Specifies the fully qualified Java class name.

```text
com.test.HelloServlet
```

### `<servlet-mapping>`

Connects a URL pattern to the Servlet.

### `<url-pattern>`

```xml
<url-pattern>/hello</url-pattern>
```

means:

```text
/hello
   ↓
HelloServlet
```

The URL does not have to match the Java class name.

---

# 4. Request Flow with web.xml

```text
Browser
   |
   | GET /hello
   ↓
Tomcat
   |
   ↓
web.xml
   |
   ↓
/hello → HelloServlet
   |
   ↓
doGet()
   |
   ↓
HTTP Response
```

The important idea:

> `web.xml` acts as a configuration map for the Servlet container.

---

# 5. Annotation-based Configuration

A Servlet can be mapped directly in Java:

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().println("Hello");
    }
}
```

The annotation:

```java
@WebServlet("/hello")
```

means:

```text
/hello
   ↓
HelloServlet
```

The configuration is written directly beside the Java class.

---

# 6. web.xml vs Annotation

| Feature | web.xml | Annotation |
|---|---|---|
| Location | XML file | Java class |
| Style | Centralized | Class-level |
| URL mapping | `<url-pattern>` | `@WebServlet` |
| Small projects | More configuration | Simple |
| Central configuration | Yes | Less centralized |
| Configuration near code | No | Yes |
| Useful for deployment-descriptor teaching | Yes | Yes |

Important:

```xml
<url-pattern>/hello</url-pattern>
```

and:

```java
@WebServlet("/hello")
```

can provide the same basic Servlet URL mapping.

They are configuration approaches, not different Servlet technologies.

For a simple practical, avoid unnecessarily configuring the same Servlet using both methods.

---

# 7. ServletConfig

## What is ServletConfig?

`ServletConfig` is an object provided by the Servlet container that allows a Servlet to access its **initialization parameters and Servlet-specific configuration**.

Simple memory trick:

```text
ServletConfig
      ↓
ONE Servlet
```

---

# 8. Why Use ServletConfig?

Suppose only `LoginServlet` needs:

```text
adminEmail = admin@example.com
```

Instead of hard-coding:

```java
String email = "admin@example.com";
```

we can put the value in configuration and read it from the Servlet.

This separates:

```text
Application Code
```

from:

```text
Configuration
```

---

# 9. ServletConfig using web.xml

```xml
<servlet>

    <servlet-name>LoginServlet</servlet-name>

    <servlet-class>
        com.test.LoginServlet
    </servlet-class>

    <init-param>
        <param-name>adminEmail</param-name>
        <param-value>admin@example.com</param-value>
    </init-param>

</servlet>
```

The `init-param` belongs specifically to `LoginServlet`.

---

# 10. Reading ServletConfig

```java
ServletConfig config = getServletConfig();

String email =
    config.getInitParameter("adminEmail");
```

You can also write:

```java
String email =
    getServletConfig()
        .getInitParameter("adminEmail");
```

Flow:

```text
web.xml
   ↓
init-param
   ↓
ServletConfig
   ↓
Servlet
```

---

# 11. Important ServletConfig Methods

### getInitParameter()

```java
config.getInitParameter("adminEmail");
```

Gets one initialization parameter.

### getInitParameterNames()

Gets the names of all initialization parameters.

### getServletName()

```java
config.getServletName();
```

Gets the configured Servlet name.

### getServletContext()

```java
config.getServletContext();
```

Gets the application's ServletContext.

---

# 12. ServletContext

## What is ServletContext?

`ServletContext` represents the **web application as a whole**.

Simple memory trick:

```text
ServletContext
      ↓
WHOLE Web Application
```

Conceptually:

```text
                Web Application
                       |
                ServletContext
                       |
          ┌────────────┼────────────┐
          ↓            ↓            ↓
      Servlet A    Servlet B    Servlet C
```

Servelts in the same web application can access the same ServletContext.

---

# 13. ServletConfig vs ServletContext

| ServletConfig | ServletContext |
|---|---|
| One Servlet | Whole web application |
| Servlet-specific configuration | Application-wide configuration/data |
| Uses Servlet `init-param` | Uses application `context-param` |
| Example: LoginServlet admin email | Example: college name shared by Servlets |
| Each Servlet can have its own Config | Servlets in same application share Context |

Remember:

```text
ServletConfig → ONE Servlet

ServletContext → WHOLE Application
```

---

# 14. Context Initialization Parameters

Application-level parameters can be defined in `web.xml`:

```xml
<context-param>
    <param-name>collegeName</param-name>
    <param-value>ABC Institute</param-value>
</context-param>
```

Read them using:

```java
ServletContext context =
    getServletContext();

String college =
    context.getInitParameter("collegeName");
```

This is application-wide configuration.

---

# 15. ServletContext Attributes

ServletContext can also store runtime data.

Example:

```java
ServletContext context =
    getServletContext();

context.setAttribute(
    "collegeName",
    "AltaSchoolTech"
);
```

Another Servlet can retrieve it:

```java
ServletContext context =
    getServletContext();

Object value =
    context.getAttribute("collegeName");
```

The stored data is an **attribute**.

---

# 16. setAttribute(), getAttribute(), removeAttribute()

## setAttribute()

```java
context.setAttribute(
    "collegeName",
    "AltaSchoolTech"
);
```

Stores runtime data.

## getAttribute()

```java
Object value =
    context.getAttribute("collegeName");
```

Retrieves runtime data.

## removeAttribute()

```java
context.removeAttribute("collegeName");
```

Removes the attribute.

---

# 17. Attributes Can Store Objects

The value is an `Object`, so it does not have to be a String.

```java
context.setAttribute("count", 100);
```

```java
context.setAttribute("message", "Hello");
```

A collection can also be stored:

```java
List<String> students =
    new ArrayList<>();

context.setAttribute(
    "students",
    students
);
```

---

# 18. Parameter vs Attribute

Students commonly confuse these.

## Context Parameter

Defined in configuration:

```xml
<context-param>
    <param-name>collegeName</param-name>
    <param-value>ABC Institute</param-value>
</context-param>
```

Read using:

```java
context.getInitParameter("collegeName");
```

## Context Attribute

Created at runtime:

```java
context.setAttribute(
    "collegeName",
    "AltaSchoolTech"
);
```

Read using:

```java
context.getAttribute("collegeName");
```

Easy memory trick:

```text
Parameter = Configuration

Attribute = Runtime Data
```

---

# 19. Complete Comparison

| Feature | ServletConfig | ServletContext |
|---|---|---|
| Scope | One Servlet | Whole web application |
| Configuration | `init-param` | `context-param` |
| Runtime attributes | Servlet-specific concept | Application-wide shared storage |
| Parameter method | `getInitParameter()` | `getInitParameter()` |
| Attribute storage | Not application-wide | `setAttribute()` |
| Attribute retrieval | Not application-wide | `getAttribute()` |
| Example | LoginServlet admin email | College name shared by all Servlets |

---

# 20. Practical 1: ServletConfig

## Servlet

```java
package com.test;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out =
            response.getWriter();

        ServletConfig config =
            getServletConfig();

        String admin =
            config.getInitParameter("admin");

        out.println(
            "<h1>Admin: "
            + admin
            + "</h1>"
        );
    }
}
```

## web.xml

```xml
<servlet>

    <servlet-name>HelloServlet</servlet-name>

    <servlet-class>
        com.test.HelloServlet
    </servlet-class>

    <init-param>
        <param-name>admin</param-name>
        <param-value>admin@example.com</param-value>
    </init-param>

</servlet>

<servlet-mapping>

    <servlet-name>HelloServlet</servlet-name>

    <url-pattern>/hello</url-pattern>

</servlet-mapping>
```

Test:

```text
http://localhost:8080/Test-WebXML/hello
```

---

# 21. Practical 2: ServletContext Parameter

In `web.xml`:

```xml
<context-param>
    <param-name>collegeName</param-name>
    <param-value>ABC Institute</param-value>
</context-param>
```

In Servlet:

```java
ServletContext context =
    getServletContext();

String college =
    context.getInitParameter(
        "collegeName");
```

Then:

```java
out.println(
    "<h1>College: "
    + college
    + "</h1>"
);
```

This demonstrates application-level configuration.

---

# 22. Practical 3: ServletContext Attribute

Create two Servlets:

```text
SetContextServlet
GetContextServlet
```

## SetContextServlet

```java
ServletContext context =
    getServletContext();

context.setAttribute(
    "collegeName",
    "AltaSchoolTech"
);

out.println("<h1>Value Stored!</h1>");

out.println(
    "<h2>"
    + context.getAttribute("collegeName")
    + "</h2>"
);
```

## GetContextServlet

```java
ServletContext context =
    getServletContext();

Object value =
    context.getAttribute("collegeName");

out.println(
    "<h1>College: "
    + value
    + "</h1>"
);
```

---

# 23. Correct Testing Order

First run:

```text
http://localhost:8080/Test-WebXML/set
```

This performs:

```java
setAttribute()
```

Then run:

```text
http://localhost:8080/Test-WebXML/get
```

This performs:

```java
getAttribute()
```

Expected:

```text
College: AltaSchoolTech
```

---

# 24. Why Can getAttribute() Return null?

If:

```java
context.getAttribute("collegeName")
```

returns:

```text
null
```

check these possibilities.

### 1. You opened `/get` first

The attribute has not been created yet.

Correct:

```text
/set
 ↓
setAttribute()
 ↓
/get
 ↓
getAttribute()
```

### 2. Tomcat/application was restarted

Attributes are runtime data and are normally lost when the web application is restarted or redeployed.

Set the value again.

### 3. Different web applications or servers

This is a very important practical issue.

Do not do:

```text
Server 1 → SetServlet
Server 2 → GetServlet
```

They do not share the same ServletContext.

Use:

```text
One Tomcat
   ↓
One Web Application
   ↓
Both Servlets
```

---

# 25. The Two-Server Mistake

Suppose:

```text
Tomcat Server 1
      |
      ↓
Application
      |
SetServlet
      |
setAttribute()
      ↓
ServletContext A
```

and:

```text
Tomcat Server 2
      |
      ↓
Application
      |
GetServlet
      |
getAttribute()
      ↓
ServletContext B
```

Then:

```text
ServletContext A
      ≠
ServletContext B
```

Therefore:

```java
getAttribute("collegeName")
```

can return:

```text
null
```

Even if the context path looks the same.

---

# 26. Correct Same-Server Setup

```text
                 Tomcat
                    |
              Test-WebXML
                    |
          ┌─────────┴─────────┐
          ↓                   ↓
 SetContextServlet     GetContextServlet
       /set                  /get
          ↓                   ↑
       set()                get()
          |                   |
          └── ServletContext ┘
```

Important rule:

> ServletContext is shared by Servlets belonging to the same web application running in the same Servlet container.

---

# 27. Debugging ServletContext

You can temporarily print:

```java
out.println(context);
```

and:

```java
out.println(
    context.getContextPath()
);
```

Also print:

```java
out.println(
    context.getAttribute("collegeName")
);
```

For the attribute practical:

1. Start one Tomcat server.
2. Deploy one web application.
3. Run `/set`.
4. Do not restart the server.
5. Run `/get`.

---

# 28. Complete Practical Combining All Concepts

`web.xml`:

```xml
<servlet>

    <servlet-name>HelloServlet</servlet-name>

    <servlet-class>
        com.test.HelloServlet
    </servlet-class>

    <init-param>
        <param-name>admin</param-name>
        <param-value>hello@servlet.com</param-value>
    </init-param>

</servlet>

<servlet-mapping>

    <servlet-name>HelloServlet</servlet-name>

    <url-pattern>/hello</url-pattern>

</servlet-mapping>

<context-param>

    <param-name>college</param-name>

    <param-value>ABC Institute</param-value>

</context-param>
```

Java:

```java
ServletConfig config =
    getServletConfig();

String admin =
    config.getInitParameter("admin");

ServletContext context =
    getServletContext();

String college =
    context.getInitParameter("college");
```

Conceptually:

```text
web.xml
   |
   ├── init-param
   |      ↓
   |  ServletConfig
   |      ↓
   |  admin
   |
   └── context-param
          ↓
      ServletContext
          ↓
       college
```

---

# 29. Common Beginner Mistakes

## Wrong URL

If:

```xml
<url-pattern>/hello</url-pattern>
```

the URL is:

```text
/hello
```

not automatically:

```text
/HelloServlet
```

---

## Wrong class name

If:

```java
package com.test;

public class HelloServlet
```

then:

```xml
<servlet-class>
    com.test.HelloServlet
</servlet-class>
```

must match exactly.

---

## Mixing javax and jakarta

Tomcat 10.1 uses Jakarta Servlet APIs.

Use:

```java
import jakarta.servlet.*;
```

Do not mix this practical with:

```java
import javax.servlet.*;
```

---

## Running Set and Get on different servers

Wrong:

```text
Server 1 → setAttribute()
Server 2 → getAttribute()
```

Correct:

```text
One server
    ↓
One web application
    ↓
Both Servlets
```

---

## Restarting before reading

This:

```text
/set
 ↓
restart Tomcat
 ↓
/get
```

may result in:

```text
null
```

because the runtime attribute was lost.

---

# 30. Important Exam Definitions

## web.xml

`web.xml` is the deployment descriptor of a Java web application. It provides configuration information such as Servlet definitions, Servlet mappings, initialization parameters, and application-level parameters to the Servlet container.

## ServletConfig

`ServletConfig` is an object provided by the Servlet container that allows an individual Servlet to access its initialization parameters and Servlet-specific configuration.

## ServletContext

`ServletContext` represents the web application environment and provides application-wide configuration, information, and shared runtime attributes to Servlets within the same web application.

## Application-level Attribute

An application-level attribute is runtime data stored in the `ServletContext` using `setAttribute()` and retrieved using `getAttribute()` by Servlets in the same web application.

---

# 31. Important 5-Mark Questions

## Q1. Explain web.xml and Annotation-based Servlet Configuration.

Include:

- Definition of web.xml
- Deployment descriptor
- Servlet definition
- Servlet mapping
- `@WebServlet`
- Comparison
- Example

## Q2. Explain ServletConfig with an example.

Include:

- Definition
- Servlet-specific scope
- `init-param`
- `getInitParameter()`
- Example
- Uses

## Q3. Explain ServletContext.

Include:

- Definition
- Application-wide scope
- Context parameters
- Attributes
- `setAttribute()`
- `getAttribute()`
- Example

## Q4. Differentiate ServletConfig and ServletContext.

Key point:

```text
ServletConfig → One Servlet
ServletContext → Whole Application
```

## Q5. Explain application-level attributes.

Include:

- Meaning
- `setAttribute()`
- `getAttribute()`
- `removeAttribute()`
- Runtime data
- Shared application scope
- Same application/server requirement

---

# 32. Quick Revision

```text
web.xml
   ↓
Deployment configuration
```

```text
@WebServlet
   ↓
Annotation-based Servlet mapping
```

```text
ServletConfig
   ↓
One Servlet's configuration
```

```text
ServletContext
   ↓
Whole Web Application
```

```text
init-param
   ↓
ServletConfig
```

```text
context-param
   ↓
ServletContext configuration
```

```text
setAttribute()
   ↓
Store runtime application data
```

```text
getAttribute()
   ↓
Retrieve runtime application data
```

```text
removeAttribute()
   ↓
Remove runtime application data
```

---

# 33. Final Mental Model

Think of a college web application:

```text
                    WEB APPLICATION
                          |
                    ServletContext
                          |
        ┌─────────────────┼─────────────────┐
        ↓                 ↓                 ↓
  LoginServlet      StudentServlet    AdminServlet
        |                 |                 |
 ServletConfig      ServletConfig     ServletConfig
        |                 |                 |
  own settings       own settings      own settings
```

Shared application information can be stored in:

```text
ServletContext
      |
      ├── collegeName
      ├── applicationStatus
      ├── sharedList
      └── other runtime data
```

## Final Memory Rule

> **ServletConfig is for ONE Servlet. ServletContext is for the WHOLE web application.**

And:

```text
init-param
    ↓
ServletConfig

context-param
    ↓
ServletContext configuration

setAttribute()
    ↓
Runtime application data

getAttribute()
    ↓
Read runtime application data
```
