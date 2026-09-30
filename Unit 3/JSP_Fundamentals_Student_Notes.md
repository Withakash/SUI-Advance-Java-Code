# JSP Fundamentals — Student-Oriented Notes

> **Goal:** Understand JSP from basics to JSTL using one continuous practical flow.

---

# JSP Fundamentals Roadmap

We will learn JSP in this order:

1. Introduction to JSP
2. JSP Lifecycle
3. Scriptlets
4. JSP Directives
5. Expression Language (EL)
6. JSP Tags / Standard Actions
7. JSTL — JSP Standard Tag Library

The important idea is that these topics are connected.

```text
Servlet
   ↓
passes data
   ↓
JSP
   ↓
EL + JSP Tags + JSTL
   ↓
HTML
   ↓
Browser
```

---

# 1. Introduction to JSP

## What is JSP?

**JSP stands for JavaServer Pages.**

JSP is a **server-side technology** used to create dynamic web pages.

It allows us to write HTML and use JSP features to generate dynamic content.

### Simple definition

> JSP is a server-side technology used to create dynamic HTML pages by combining HTML with Java/JSP features.

---

## Why do we need JSP?

Suppose we want a web page like:

```text
Hey Akash!
Welcome to Student Portal.
```

But the name should come from a user.

For example:

```text
Hey Akash!
```

or

```text
Hey Rahul!
```

or

```text
Hey Priya!
```

The HTML page must be generated dynamically.

JSP helps us create such dynamic pages.

---

# JSP vs Servlet

A simple way to remember the difference:

| Servlet | JSP |
|---|---|
| Java-centric | HTML-centric |
| Java code is primary | HTML is primary |
| Good for backend/request processing | Good for presentation/UI |
| Generates HTML from Java | Allows HTML with JSP features |

Think:

```text
Servlet → Backend / Processing

JSP → Presentation / UI
```

A common architecture is:

```text
Browser
   ↓
Servlet
   ↓
Business Logic / Database
   ↓
JSP
   ↓
HTML
   ↓
Browser
```

---

# Important: Does the browser execute JSP?

**No.**

The browser does not understand JSP syntax.

For example:

```jsp
<h1>Hey ${name}!</h1>
```

The browser should not receive `${name}`.

Instead, Tomcat processes the JSP and finally sends HTML such as:

```html
<h1>Hey Akash!</h1>
```

So:

```text
JSP
 ↓
Tomcat/JSP Container
 ↓
HTML
 ↓
Browser
```

---

# How JSP works internally

Suppose we have:

```text
hello.jsp
```

Tomcat processes it approximately like this:

```text
hello.jsp
   ↓
Translation
   ↓
hello_jsp.java
   ↓
Compilation
   ↓
hello_jsp.class
   ↓
Class Loading
   ↓
Object Creation
   ↓
jspInit()
   ↓
_jspService()
   ↓
HTML Response
```

This is the JSP Lifecycle, which we will study next.

---

# Live Demo: First JSP

Create:

```text
hello.jsp
```

Code:

```jsp
<!DOCTYPE html>
<html>
<head>
    <title>My First JSP</title>
</head>
<body>

    <h1>Hey Akash!</h1>

    <p>Welcome to JSP.</p>

    <p>2 + 3 = <%= 2 + 3 %></p>

</body>
</html>
```

Run it using:

```text
Eclipse
→ Run As
→ Run on Server
→ Tomcat 10.1
```

Then open:

```text
http://localhost:8080/YourProjectName/hello.jsp
```

You will see:

```text
Hey Akash!

Welcome to JSP.

2 + 3 = 5
```

Notice:

```jsp
<%= 2 + 3 %>
```

is processed by the server.

The browser receives the final HTML.

---

# Practical Flow: HTML → Servlet → JSP

Now let's create a more realistic example.

We want:

```text
index.html
    ↓
HelloServlet
    ↓
hello.jsp
```

The user enters their name.

Example:

```text
Enter Your Name: Akash
        ↓
      Submit
        ↓
HelloServlet
        ↓
hello.jsp
        ↓
Hey Akash!
```

---

## Step 1: index.html

```html
<!DOCTYPE html>
<html>
<head>
    <title>JSP Demo</title>
</head>
<body>

    <h1>Enter Your Name</h1>

    <form action="hello" method="get">

        <input
            type="text"
            name="name"
            placeholder="Enter your name"
        >

        <button type="submit">Submit</button>

    </form>

</body>
</html>
```

---

## Step 2: HelloServlet.java

For Tomcat 10+, use the `jakarta.servlet` package.

```java
package com.test;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");

        request.setAttribute("name", name);

        request.getRequestDispatcher("hello.jsp")
               .forward(request, response);
    }
}
```

---

## Step 3: hello.jsp

```jsp
<!DOCTYPE html>
<html>
<head>
    <title>Hello</title>
</head>
<body>

    <h1>Hey ${name}!</h1>

</body>
</html>
```

> **Note:** `${name}` is Expression Language (EL). We will study EL formally later.

---

## Complete Flow

Suppose the user enters:

```text
Akash
```

The browser sends:

```text
/hello?name=Akash
```

The Servlet receives it:

```java
String name = request.getParameter("name");
```

Now:

```text
name = "Akash"
```

The Servlet stores it:

```java
request.setAttribute("name", name);
```

Then forwards to:

```java
request.getRequestDispatcher("hello.jsp")
       .forward(request, response);
```

JSP receives the request and:

```jsp
${name}
```

produces:

```text
Akash
```

Final output:

```text
Hey Akash!
```

---

# 2. JSP Lifecycle

JSP has a lifecycle because JSP is processed by the server before the browser receives the response.

## JSP Lifecycle

```text
JSP File
   ↓
Translation
   ↓
Generated Servlet Source
   ↓
Compilation
   ↓
Generated .class File
   ↓
Loading
   ↓
Instantiation
   ↓
jspInit()
   ↓
_jspService()
   ↓
jspDestroy()
```

---

# Step 1: Translation

Tomcat converts the JSP file into Java Servlet source code.

Example:

```text
hello.jsp
    ↓
hello_jsp.java
```

This is called:

> **Translation**

### Important

Translation is:

```text
JSP → Java Servlet Source
```

It is NOT:

```text
JSP → .class
```

---

# Step 2: Compilation

The generated Java source is compiled.

```text
hello_jsp.java
      ↓
Java Compiler
      ↓
hello_jsp.class
```

This is called:

> **Compilation**

---

# Translation vs Compilation

This is a very common exam question.

| Translation | Compilation |
|---|---|
| JSP → Java source | Java source → bytecode |
| Produces generated `.java` source | Produces `.class` file |
| Happens before compilation | Happens after translation |

Remember:

```text
hello.jsp
   ↓
hello_jsp.java       ← Translation
   ↓
hello_jsp.class      ← Compilation
```

---

# Step 3: Loading

The JVM/class loader loads the generated class.

```text
hello_jsp.class
      ↓
Class Loader
      ↓
Loaded Class
```

---

# Step 4: Instantiation

Tomcat creates an object of the generated servlet class.

Conceptually:

```java
hello_jsp obj = new hello_jsp();
```

---

# Step 5: jspInit()

The JSP initialization method is called.

Conceptually:

```java
jspInit();
```

It is used for initialization work.

It is generally called once for the JSP servlet instance.

---

# Step 6: _jspService()

This is the most important request-processing method.

For every request, the generated JSP servlet processes the request through:

```text
_jspService()
```

For example, if the JSP contains:

```jsp
<h1>Hello</h1>
```

the generated servlet contains code that writes the HTML to the response.

---

# Step 7: jspDestroy()

When the JSP servlet is removed from service, cleanup can happen through:

```text
jspDestroy()
```

---

# First Request vs Later Requests

A very important point:

It is not correct to say:

> "JSP is translated and compiled on every request."

Normally, Tomcat translates and compiles the JSP when it is first needed, or when the JSP source needs to be recompiled.

After that, the generated servlet can be reused for subsequent requests.

So conceptually:

```text
First request

hello.jsp
   ↓
translation
   ↓
compilation
   ↓
loading
   ↓
initialization
   ↓
_jspService()
   ↓
response
```

Later requests generally:

```text
request
   ↓
_jspService()
   ↓
response
```

---

# JSP Lifecycle vs Servlet

A normal Servlet already starts as Java source:

```text
HelloServlet.java
      ↓
Compilation
      ↓
HelloServlet.class
```

JSP has an additional translation step:

```text
hello.jsp
   ↓
hello_jsp.java
   ↓
hello_jsp.class
```

Therefore:

> **JSP is eventually executed as a Servlet.**

---

# 3. Scriptlets

Scriptlets allow Java code to be written inside a JSP.

## Syntax

```jsp
<%
    // Java code
%>
```

Example:

```jsp
<%
    String name = "Akash";
%>

<h1>Hey <%= name %>!</h1>
```

Output:

```text
Hey Akash!
```

---

# Three Important JSP Scripting Elements

## 1. Scriptlet

```jsp
<%
    // Java statements
%>
```

Used to write Java statements.

Example:

```jsp
<%
    int age = 20;
    String name = "Akash";
%>
```

---

## 2. Expression

```jsp
<%= expression %>
```

Used to directly print a value.

Example:

```jsp
<%= 10 + 20 %>
```

Output:

```text
30
```

Another example:

```jsp
<%
    String name = "Akash";
%>

Hey <%= name %>!
```

Output:

```text
Hey Akash!
```

---

## 3. Declaration

```jsp
<%!
    // declaration
%>
```

Used to declare class-level fields or methods.

Example:

```jsp
<%!
    int count = 0;

    int square(int n) {
        return n * n;
    }
%>
```

Then:

```jsp
<%= square(5) %>
```

Output:

```text
25
```

---

# Easy Memory Trick

```text
<%   → DO
<%=  → PRINT
<%!  → DECLARE
```

---

# Where does Scriptlet code go?

A scriptlet:

```jsp
<%
    int age = 20;
%>
```

is conceptually placed inside the generated servlet's:

```text
_jspService()
```

method.

That is why scriptlets are closely connected with the JSP lifecycle.

---

# Why don't we prefer Scriptlets today?

Scriptlets work, but large amounts of Java code inside JSP make the page difficult to maintain.

Bad style:

```jsp
<%
    // database code
    // business logic
    // calculations
    // loops
    // conditions
    // authentication
%>
```

Better architecture:

```text
Servlet
   ↓
Business Logic
   ↓
Data
   ↓
JSP
   ↓
EL + JSTL
```

So learn scriptlets because they are part of JSP and exam syllabi, but understand that modern JSP applications generally keep Java/business logic outside the JSP.

---

# 4. JSP Directives

A JSP directive gives instructions to the JSP container.

## Syntax

```jsp
<%@ directive attribute="value" %>
```

The three important JSP directives are:

```text
1. page
2. include
3. taglib
```

---

# 4.1 Page Directive

The page directive is used to provide page-level information/configuration.

Example:

```jsp
<%@ page contentType="text/html" %>
```

Another:

```jsp
<%@ page pageEncoding="UTF-8" %>
```

Another:

```jsp
<%@ page import="java.util.ArrayList" %>
```

Example:

```jsp
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<body>

<h1>Hello JSP</h1>

</body>
</html>
```

---

## Import using Page Directive

```jsp
<%@ page import="java.util.ArrayList" %>
```

Now the JSP can use:

```java
ArrayList<String> students = new ArrayList<>();
```

---

# 4.2 Include Directive

Syntax:

```jsp
<%@ include file="header.jsp" %>
```

It includes another JSP/resource at **translation time**.

Example:

### header.jsp

```jsp
<h1>Student Portal</h1>
<hr>
```

### home.jsp

```jsp
<%@ include file="header.jsp" %>

<h2>Welcome to Home Page</h2>
```

Conceptually:

```text
header.jsp
     +
home.jsp
     ↓
translation
     ↓
generated servlet
```

---

# 4.3 Taglib Directive

The taglib directive is used to make a tag library available to a JSP.

Example:

```jsp
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
```

Now we can use tags such as:

```jsp
<c:if>
<c:forEach>
<c:choose>
```

JSTL will be covered in detail later.

---

# Important: Directive vs Scriptlet

Directive:

```jsp
<%@ page pageEncoding="UTF-8" %>
```

tells the JSP container how to process the page.

Scriptlet:

```jsp
<%
    int age = 20;
%>
```

contains Java statements.

So:

```text
Directive → instruction/configuration

Scriptlet → Java code
```

---

# 5. Expression Language (EL)

## What is EL?

**EL stands for Expression Language.**

EL provides a simple way to access and work with data in JSP without writing Java scriptlets.

The syntax is:

```jsp
${expression}
```

---

# Our Existing Example

Servlet:

```java
String name = request.getParameter("name");

request.setAttribute("name", name);

request.getRequestDispatcher("hello.jsp")
       .forward(request, response);
```

JSP:

```jsp
<h1>Hey ${name}!</h1>
```

If:

```text
name = Akash
```

then:

```jsp
${name}
```

produces:

```text
Akash
```

Final output:

```text
Hey Akash!
```

---

# Why EL?

Without EL, old JSP code could use:

```jsp
<%
    out.println(name);
%>
```

With EL:

```jsp
${name}
```

The second is much cleaner for presentation.

---

# EL Arithmetic

EL can perform calculations.

```jsp
${10 + 20}
```

Output:

```text
30
```

Examples:

```jsp
${10 - 5}
${10 * 5}
${20 / 5}
```

---

# EL Comparison

Examples:

```jsp
${age >= 18}
${age == 18}
${age != 18}
${age > 18}
${age < 18}
```

These expressions produce a boolean result.

---

# EL Logical Operators

You can use:

```jsp
${age >= 18 && age <= 60}
```

or word forms:

```jsp
${age >= 18 and age <= 60}
```

Other operators:

```text
&& / and
|| / or
!  / not
```

---

# EL Comparison Word Forms

EL also supports word forms:

```text
eq → equal
ne → not equal
gt → greater than
lt → less than
ge → greater than or equal
le → less than or equal
```

Examples:

```jsp
${age ge 18}
${marks gt 50}
${name eq 'Akash'}
```

---

# EL Scopes

JSP has four important scopes:

```text
1. Page
2. Request
3. Session
4. Application
```

Examples:

```jsp
${name}
```

or explicitly:

```jsp
${requestScope.name}
```

```jsp
${sessionScope.username}
```

```jsp
${applicationScope.college}
```

---

# Why use Scopes?

Suppose the Servlet does:

```java
request.setAttribute("name", "Akash");
```

Then JSP can use:

```jsp
${requestScope.name}
```

or simply:

```jsp
${name}
```

The short form is convenient when there is no ambiguity.

---

# EL with JavaBean

Suppose we have a Java object:

```java
Student student = new Student();

student.setName("Akash");
student.setAge(20);

request.setAttribute("student", student);
```

In JSP:

```jsp
${student.name}
```

Output:

```text
Akash
```

And:

```jsp
${student.age}
```

Output:

```text
20
```

EL provides convenient property access.

---

# Overall Architecture

A realistic application may look like:

```text
Browser
   ↓
Servlet
   ↓
Business Logic / DAO
   ↓
Database
   ↓
Java Object / Data
   ↓
request.setAttribute()
   ↓
JSP
   ↓
EL
   ↓
HTML
   ↓
Browser
```

Important:

> EL does not remove Java from the application.

Java/backend logic can still exist in Servlets, services, DAOs, etc.

EL simply makes presentation-side data access easier.

---

# 6. JSP Tags / Standard Actions

JSP provides standard actions that perform operations during request processing.

Common JSP standard actions include:

```text
<jsp:include>
<jsp:forward>
<jsp:param>
<jsp:useBean>
<jsp:setProperty>
<jsp:getProperty>
```

---

# 6.1 `<jsp:include>`

Example:

### header.jsp

```jsp
<h1>Student Portal</h1>
<hr>
```

### hello.jsp

```jsp
<!DOCTYPE html>
<html>
<body>

<jsp:include page="header.jsp" />

<h2>Hey ${name}!</h2>

<p>Welcome to the Student Portal.</p>

</body>
</html>
```

The header is included while the JSP request is being processed.

---

# Include Directive vs Include Action

This is an important exam/practical difference.

## Include Directive

```jsp
<%@ include file="header.jsp" %>
```

```text
Translation time
```

Conceptually, the content is included while the JSP is translated.

---

## Include Action

```jsp
<jsp:include page="header.jsp" />
```

```text
Request time
```

The resource is included while the request is being processed.

---

## Remember

```text
<%@ include ... %>
        ↓
Translation time


<jsp:include ... />
        ↓
Request time
```

---

# 6.2 `<jsp:forward>`

Used to forward the current request to another resource.

Example:

```jsp
<jsp:forward page="welcome.jsp" />
```

Flow:

```text
current.jsp
    ↓
forward
    ↓
welcome.jsp
```

The server handles the forwarding internally.

---

# 6.3 `<jsp:param>`

Used to pass a parameter with a JSP action.

Example:

```jsp
<jsp:forward page="welcome.jsp">

    <jsp:param
        name="message"
        value="Welcome Akash"
    />

</jsp:forward>
```

Then in `welcome.jsp`:

```jsp
${param.message}
```

Output:

```text
Welcome Akash
```

---

# 6.4 `<jsp:useBean>`

Used with JavaBeans.

Example:

```jsp
<jsp:useBean
    id="student"
    class="com.test.Student"
    scope="request" />
```

This makes a bean available to the JSP.

---

# 6.5 `<jsp:setProperty>`

Used to set a bean property.

Example:

```jsp
<jsp:setProperty
    name="student"
    property="name"
    value="Akash" />
```

---

# 6.6 `<jsp:getProperty>`

Used to get/display a bean property.

Example:

```jsp
<jsp:getProperty
    name="student"
    property="name" />
```

Output:

```text
Akash
```

---

# JSP Standard Actions vs JSTL

Do not confuse these two.

## JSP Standard Actions

```jsp
<jsp:include>
<jsp:forward>
<jsp:param>
<jsp:useBean>
<jsp:setProperty>
<jsp:getProperty>
```

## JSTL

```jsp
<c:if>
<c:forEach>
<c:choose>
<c:when>
<c:otherwise>
```

Both are JSP features, but they serve different purposes.

---

# 7. JSTL — JSP Standard Tag Library

## What is JSTL?

**JSTL stands for JSP Standard Tag Library.**

It provides predefined tags for common tasks such as:

- Conditions
- Loops
- Iteration
- Variable handling
- URL handling
- Formatting

The main idea is:

> Instead of writing Java code inside JSP using scriptlets, use JSTL tags for common presentation logic.

---

# Why JSTL?

Without JSTL:

```jsp
<%
    int age = 20;

    if (age >= 18) {
%>

<h2>You are eligible.</h2>

<%
    }
%>
```

With JSTL:

```jsp
<c:if test="${age >= 18}">
    <h2>You are eligible.</h2>
</c:if>
```

The JSP becomes easier to read.

---

# JSTL + EL

EL:

```jsp
${name}
```

is mainly used to access/evaluate data.

JSTL:

```jsp
<c:if>
<c:forEach>
<c:choose>
```

is used for common JSP operations.

Together:

```text
Servlet
   ↓
request.setAttribute()
   ↓
JSP
   ↓
EL + JSTL
   ↓
HTML
```

---

# JSTL Core Library

The most important JSTL library for basic JSP work is the Core library.

Common tags:

| Tag | Purpose |
|---|---|
| `<c:if>` | if condition |
| `<c:choose>` | multiple conditions |
| `<c:when>` | condition inside choose |
| `<c:otherwise>` | else |
| `<c:forEach>` | loop/iteration |
| `<c:set>` | set a variable |
| `<c:remove>` | remove a variable |
| `<c:out>` | output a value |
| `<c:url>` | create a URL |

---

# JSTL Taglib Declaration

For Jakarta-based applications such as Tomcat 10+ environments, the Core tag library can be declared as:

```jsp
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
```

The prefix is usually:

```text
c
```

Therefore:

```jsp
<c:if>
<c:forEach>
<c:choose>
```

can be used.

> **Important:** The exact JSTL/Jakarta Tags library must also be available in the application's classpath. The taglib declaration tells JSP which tag library to use; it does not itself install the library.

---

# 7.1 `<c:if>`

Suppose our Servlet sends:

```java
String name = request.getParameter("name");

request.setAttribute("name", name);

request.getRequestDispatcher("hello.jsp")
       .forward(request, response);
```

JSP:

```jsp
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<body>

<h1>Hey ${name}!</h1>

<c:if test="${name == 'Akash'}">
    <h2>Welcome Akash!</h2>
</c:if>

</body>
</html>
```

If the user enters:

```text
Akash
```

Output:

```text
Hey Akash!

Welcome Akash!
```

If the user enters:

```text
Rahul
```

the second message will not be displayed.

---

# 7.2 `<c:choose>`

`<c:choose>` is useful for if-else-if-else style logic.

Suppose:

```text
marks = 75
```

We want:

```text
90+ → Excellent
75+ → Very Good
50+ → Pass
below 50 → Fail
```

JSTL:

```jsp
<c:choose>

    <c:when test="${marks >= 90}">
        Excellent
    </c:when>

    <c:when test="${marks >= 75}">
        Very Good
    </c:when>

    <c:when test="${marks >= 50}">
        Pass
    </c:when>

    <c:otherwise>
        Fail
    </c:otherwise>

</c:choose>
```

Conceptually:

```java
if
else if
else
```

becomes:

```jsp
<c:choose>
    <c:when>
    <c:when>
    <c:otherwise>
</c:choose>
```

---

# 7.3 `<c:forEach>`

This is one of the most useful JSTL tags.

Suppose the Servlet sends a list of students.

```java
List<String> students = new ArrayList<>();

students.add("Akash");
students.add("Rahul");
students.add("Priya");
students.add("Amit");

request.setAttribute("students", students);

request.getRequestDispatcher("students.jsp")
       .forward(request, response);
```

In JSP:

```jsp
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h2>Students</h2>

<c:forEach var="student" items="${students}">

    <p>${student}</p>

</c:forEach>
```

Output:

```text
Students

Akash
Rahul
Priya
Amit
```

Notice:

> There is no Java `for` loop inside the JSP.

---

# `<c:forEach>` with Numbers

You can also use:

```jsp
<c:forEach var="i" begin="1" end="5">

    <p>${i}</p>

</c:forEach>
```

Output:

```text
1
2
3
4
5
```

---

# `<c:forEach>` with Step

```jsp
<c:forEach var="i"
           begin="1"
           end="10"
           step="2">

    ${i}

</c:forEach>
```

Output:

```text
1
3
5
7
9
```

---

# 7.4 `<c:set>`

Used to create/set a variable.

```jsp
<c:set var="name" value="Akash" />

<h2>Hello ${name}</h2>
```

Output:

```text
Hello Akash
```

Another example:

```jsp
<c:set var="age" value="${20}" />

Age: ${age}
```

---

# 7.5 `<c:out>`

Used to output a value.

```jsp
<c:out value="${name}" />
```

In simple cases, this can often be written as:

```jsp
${name}
```

So students should recognize both.

---

# Complete Conceptual Flow

Now connect everything together.

```text
index.html
     ↓
User enters name
     ↓
HelloServlet
     ↓
request.getParameter("name")
     ↓
request.setAttribute("name", name)
     ↓
forward()
     ↓
hello.jsp
     ↓
EL: ${name}
     ↓
JSTL: <c:if>, <c:forEach>, etc.
     ↓
HTML
     ↓
Browser
```

---

# Complete JSP Learning Map

## 1. Introduction to JSP

We learned:

```text
JSP = JavaServer Pages
```

JSP is mainly used for presentation and dynamic HTML generation.

---

## 2. JSP Lifecycle

We learned:

```text
JSP
 ↓
Translation
 ↓
Generated Java
 ↓
Compilation
 ↓
.class
 ↓
Loading
 ↓
Instantiation
 ↓
jspInit()
 ↓
_jspService()
 ↓
jspDestroy()
```

---

## 3. Scriptlets

We learned:

```jsp
<%  %>
<%= %>
<%! %>
```

Memory trick:

```text
<%   → DO
<%=  → PRINT
<%!  → DECLARE
```

---

## 4. Directives

Three important directives:

```jsp
<%@ page %>
<%@ include %>
<%@ taglib %>
```

They provide instructions to the JSP container.

---

## 5. EL

Syntax:

```jsp
${expression}
```

Used to access and evaluate data.

Example:

```jsp
${name}
```

---

## 6. JSP Standard Actions

Examples:

```jsp
<jsp:include>
<jsp:forward>
<jsp:param>
<jsp:useBean>
<jsp:setProperty>
<jsp:getProperty>
```

---

## 7. JSTL

Common tags:

```jsp
<c:if>
<c:choose>
<c:when>
<c:otherwise>
<c:forEach>
<c:set>
<c:out>
<c:url>
```

Used to perform common JSP operations without writing Java scriptlets.

---

# Most Important Differences

## JSP vs Servlet

```text
Servlet → Java/backend/request processing

JSP → HTML/presentation
```

---

## Scriptlet vs EL

Scriptlet:

```jsp
<%
    out.println(name);
%>
```

EL:

```jsp
${name}
```

---

## Directive vs Action

Directive:

```jsp
<%@ include file="header.jsp" %>
```

Action:

```jsp
<jsp:include page="header.jsp" />
```

Main difference:

```text
Directive → Translation time

Action → Request time
```

---

## EL vs JSTL

EL:

```jsp
${name}
```

Mainly accesses/evaluates data.

JSTL:

```jsp
<c:if>
<c:forEach>
```

Provides common control/utility operations through tags.

---

# One Final Mental Model

Think of a web application like this:

```text
             USER
               │
               ▼
           Browser
               │
               │ HTTP Request
               ▼
            Servlet
               │
       ┌───────┴────────┐
       │                │
       ▼                ▼
 Business Logic       Database
       │                │
       └───────┬────────┘
               │
               ▼
       request.setAttribute()
               │
               ▼
              JSP
               │
       ┌───────┼────────┐
       │       │        │
       ▼       ▼        ▼
      EL      JSTL   JSP Actions
       │       │        │
       └───────┼────────┘
               ▼
              HTML
               │
               ▼
            Browser
```

The main principle is:

> **Servlet handles processing. JSP handles presentation. EL accesses data. JSTL handles common presentation logic.**

---

# Quick Revision

### What is JSP?

Server-side technology for creating dynamic web pages.

### Does the browser execute JSP?

No. The JSP is processed on the server and the browser receives HTML.

### What is JSP translation?

Conversion of JSP source into generated Java Servlet source.

### What is JSP compilation?

Compilation of generated Java source into `.class` bytecode.

### What is a scriptlet?

Java code written inside JSP using:

```jsp
<% ... %>
```

### What is EL?

Expression Language used to access/evaluate data:

```jsp
${name}
```

### What are JSP directives?

Instructions to the JSP container:

```jsp
<%@ page %>
<%@ include %>
<%@ taglib %>
```

### What are JSP standard actions?

Predefined JSP actions such as:

```jsp
<jsp:include>
<jsp:forward>
<jsp:param>
```

### What is JSTL?

JSP Standard Tag Library containing predefined tags for common operations.

### Most important JSTL tags?

```jsp
<c:if>
<c:choose>
<c:when>
<c:otherwise>
<c:forEach>
<c:set>
<c:out>
```

---

# Exam-Oriented Questions

1. What is JSP? Explain its advantages.
2. Differentiate between JSP and Servlet.
3. Explain the JSP Lifecycle with a neat diagram.
4. Explain JSP translation and compilation.
5. What is a JSP Scriptlet?
6. Explain Scriptlet, Expression and Declaration.
7. What are JSP directives?
8. Explain the three types of JSP directives.
9. What is Expression Language (EL)?
10. Explain JSP scopes.
11. Explain JSP standard actions.
12. Differentiate between `<%@ include %>` and `<jsp:include>`.
13. What is JSTL?
14. Explain the JSTL Core library.
15. Explain `<c:if>` with an example.
16. Explain `<c:choose>`, `<c:when>` and `<c:otherwise>`.
17. Explain `<c:forEach>` with an example.
18. Explain `<c:set>` and `<c:out>`.
19. Differentiate between EL and JSTL.
20. Explain the complete flow of Servlet → JSP → HTML.

---

# Practical Practice

## Practice 1

Create:

```text
index.html
HelloServlet.java
hello.jsp
```

Take the user's name and display:

```text
Hey Akash!
```

---

## Practice 2

Modify the application to take:

```text
Name
Age
```

Display:

```text
Hey Akash!

Your age is 20.
```

Use EL in the JSP.

---

## Practice 3

Use `<c:if>`:

```text
Age >= 18
→ You are eligible.

Age < 18
→ You are not eligible.
```

---

## Practice 4

Take marks and use:

```text
90+ → Excellent
75+ → Very Good
50+ → Pass
below 50 → Fail
```

Use:

```jsp
<c:choose>
<c:when>
<c:otherwise>
```

---

## Practice 5

Create a list:

```text
Akash
Rahul
Priya
Amit
```

Send it from Servlet to JSP and display it using:

```jsp
<c:forEach>
```

---

# Final Takeaway

The most important transformation to understand is:

```text
OLD STYLE

Java code inside JSP
        ↓
Scriptlets
        ↓
Harder to maintain


BETTER STYLE

Servlet / Backend
        ↓
Prepare data
        ↓
JSP
        ↓
EL + JSTL
        ↓
HTML
```

If you understand this flow, you have understood the main purpose of JSP in a Servlet-based web application.
