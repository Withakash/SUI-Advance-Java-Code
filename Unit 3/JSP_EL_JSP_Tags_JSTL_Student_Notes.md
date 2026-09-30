# Topic 5: Expression Language (EL)

This is one of the most useful JSP topics because EL is already used in the working example:


```
<h1>Hey ${name}!</h1>
```

Now let's understand what `${name}` actually means.

# 5. Expression Language (EL)

## 5.1 What is EL?

**EL = Expression Language.**

It provides a simple way to **access and display data in JSP without writing Java code/scriptlets**.

Instead of:


```
<%
    String name = (String) request.getAttribute("name");
%>

<h1>Hey <%= name %>!</h1>
```

we can simply write:


```
<h1>Hey ${name}!</h1>
```

Much cleaner.

---

# 5.2 Why was EL introduced?

Remember Scriptlets from the previous topic?


```
<%
    String name = "Akash";
%>

<h1>Hey <%= name %>!</h1>
```

Imagine a JSP page with 50 such pieces of Java code.

It becomes difficult to read and maintain.

EL allows the JSP page to focus more on **presentation**.


```
<h1>Hey ${name}!</h1>
```

So the idea is:


```
Scriptlets
     ↓
Java code inside JSP
     ↓
Difficult to maintain

EL
     ↓
Simple expressions
     ↓
Cleaner JSP
```

---

# 5.3 EL Syntax

The basic syntax is:


```
${expression}
```

For example:


```
${name}


${age}


${10 + 20}


${user.name}
```

The important symbols are:


```
$ + { }
```

---

# 5.4 Let's use the same working example

Our Servlet already does:


```
String name = request.getParameter("name");

request.setAttribute("name", name);

request.getRequestDispatcher("hello.jsp")
       .forward(request, response);
```

Suppose the user enters:


```
Akash
```

The Servlet puts:


```
name → Akash
```

into the request scope.

Then JSP:


```
<h1>Hey ${name}!</h1>
```

EL finds:


```
name = Akash
```

and displays:


```
Hey Akash!
```

### Complete flow


```
index.html
     ↓
User enters "Akash"
     ↓
HelloServlet
     ↓
request.getParameter("name")
     ↓
name = "Akash"
     ↓
request.setAttribute("name", name)
     ↓
hello.jsp
     ↓
${name}
     ↓
Hey Akash!
```

This example clearly demonstrates the complete EL flow.

---

# 5.5 EL vs Scriptlet

This is important for exams.

### Scriptlet


```
<%
    String name = (String) request.getAttribute("name");
%>

<h1>Hey <%= name %>!</h1>
```

### EL


```
<h1>Hey ${name}!</h1>
```

| Scriptlet              | EL                                       |
| ---------------------- | ---------------------------------------- |
| Uses Java code         | Uses expression syntax                   |
| `<% %>`                | `${}`                                    |
| More verbose           | Concise                                  |
| Can contain statements | Primarily accesses/evaluates expressions |
| Older JSP style        | Preferred for JSP presentation           |

---

# 5.6 EL can perform calculations

EL isn't only for variables.

For example:


```
<p>${10 + 20}</p>
```

Output:


```
30
```

Other examples:


```
${10 - 5}
```

Output:


```
5


${10 * 5}
```

Output:


```
50


${20 / 5}
```

Output:


```
4
```

---

# 5.7 EL comparison operators

You can also compare values.


```
${age >= 18}
```

Result:


```
true
```

or:


```
false
```

Examples:


```
${age == 18}


${age != 18}


${age > 18}


${age < 18}
```

---

# 5.8 EL logical operators

For example:


```
${age >= 18 && age <= 60}
```

Or:


```
${age >= 18 || isAdmin}
```

You can also use word-based operators:


```
${age ge 18}


${age eq 18}


${age ne 18}
```

Common operators:

| Operator     | Meaning       |
| ------------ | ------------- |
| `eq` / `==`  | Equal         |
| `ne` / `!=`  | Not equal     |
| `gt` / `>`   | Greater than  |
| `lt` / `<`   | Less than     |
| `ge` / `>=`  | Greater/equal |
| `le` / `<=`  | Less/equal    |
| `&&` / `and` | AND           |
| `||` / `or` | OR            |
| `!` / `not`  | NOT           |

---

# 5.9 The most important part: Scopes

EL becomes especially useful when working with Servlet/JSP scopes.

Remember that Servlet/JSP applications have different scopes.


```
Application
Session
Request
Page
```

EL can access attributes stored in these scopes.

---

## Request scope

Servlet:


```
request.setAttribute("name", "Akash");
```

JSP:


```
${name}
```

---

## Session scope

Servlet:


```
request.getSession().setAttribute("username", "Akash");
```

JSP:


```
${username}
```

---

## Application scope

Servlet:


```
getServletContext()
    .setAttribute("college", "ABC College");
```

JSP:


```
${college}
```

So:


```
request.setAttribute()
       ↓
${name}

session.setAttribute()
       ↓
${username}

application.setAttribute()
       ↓
${college}
```

---

# 5.10 How does `${name}` know where to search?

A common question is:

If you write:


```
${name}
```

EL searches the relevant scopes for an attribute named `name`.

Conceptually:


```
Page Scope
    ↓
Request Scope
    ↓
Session Scope
    ↓
Application Scope
```

It uses the first matching attribute it finds.

You can also explicitly specify the scope.

For example:


```
${requestScope.name}
```

Session:


```
${sessionScope.username}
```

Application:


```
${applicationScope.college}
```

---

# 5.11 EL with JavaBean/Object

Suppose Servlet sends a Student object:


```
Student student = new Student();

student.setName("Akash");
student.setAge(25);

request.setAttribute("student", student);
```

JSP can do:


```
${student.name}
```

and:


```
${student.age}
```

Output:


```
Akash
25
```

This is especially useful when a Servlet gets data from a database.

---

# 5.12 EL + Database application

Eventually your architecture can look like:


```
Browser
   ↓
Servlet
   ↓
DAO
   ↓
Database
   ↓
Student objects
   ↓
request.setAttribute()
   ↓
JSP
   ↓
EL
   ↓
HTML
```

For example:


```
request.setAttribute("student", student);
```

JSP:


```
<h2>${student.name}</h2>
<p>${student.age}</p>
```

No Java scriptlet required.

---

# 5.13 One important distinction

EL does **not** mean:

> "Java code is completely gone."

Your Servlet can still contain Java:


```
String name = request.getParameter("name");

request.setAttribute("name", name);
```

The idea is:


```
Servlet
    ↓
Java / business/request logic

JSP
    ↓
Presentation

EL
    ↓
Access/display data
```

That's a much cleaner separation.

---

# 5.14 Live demo for your class

You already have:

### `index.html`


```
<form action="hello" method="get">

    <input type="text" name="name">

    <button type="submit">Submit</button>

</form>
```

### `HelloServlet`


```
String name = request.getParameter("name");

request.setAttribute("name", name);

request.getRequestDispatcher("hello.jsp")
       .forward(request, response);
```

### `hello.jsp`


```
<h1>Hey ${name}!</h1>
```

Now ask students:

> **"Where did `${name}` get its value?"**

They should trace:


```
HTML
 ↓
request parameter
 ↓
Servlet
 ↓
request.setAttribute("name", name)
 ↓
JSP EL
 ↓
${name}
```

That's the **core EL concept**.

---

## What comes next?

After EL, the next topic is:

### **JSP Tags**

This creates a useful progression:


```
Scriptlets
    ↓
Java inside JSP

EL
    ↓
Access data without Java code

JSP Tags
    ↓
Perform common JSP operations using tags
```

Then we'll finish with **JSTL**, where students can do things like:


```
<c:if>
<c:forEach>
<c:choose>
```

without writing Java `if`/`for` code inside the JSP.

next topic

# Topic 6: JSP Tags

This topic is important because **JSP Tags, Scriptlets, EL, and JSTL** are different concepts. Keep the distinction clear.

# 6. JSP Tags

A **JSP tag** is an element written in a special tag syntax that tells the JSP container to perform a particular operation.

Instead of writing Java code such as:


```
<%
    // Java code
%>
```

we can use JSP's predefined actions/tags.

The syntax looks like:


```
<jsp:action ... />
```

---

# 6.1 Why do we need JSP Tags?

Suppose we want one JSP page to include another JSP page.

One approach is the directive:


```
<%@ include file="header.jsp" %>
```

But JSP also provides a **standard action**:


```
<jsp:include page="header.jsp" />
```

This is a JSP tag/action.

So JSP provides predefined mechanisms for common operations.

---

# 6.2 JSP Standard Actions

The most important ones for your syllabus are:


```
<jsp:include>
<jsp:forward>
<jsp:param>
<jsp:useBean>
<jsp:setProperty>
<jsp:getProperty>
```

You do not need to memorize all of them at once; understand what each one is used for.

Let's understand the important ones.

---

# 6.3 `<jsp:include>`

This is used to include another resource in the current JSP.

Suppose:


```
WebContent/
│
├── header.jsp
├── footer.jsp
└── home.jsp
```

### `header.jsp`


```
<h1>My College</h1>
<hr>
```

### `footer.jsp`


```
<hr>
<p>Copyright 2026</p>
```

### `home.jsp`


```
<html>
<body>

<jsp:include page="header.jsp" />

<h2>Welcome to Home Page</h2>

<p>This is my home page.</p>

<jsp:include page="footer.jsp" />

</body>
</html>
```

The result:


```
My College
────────────────

Welcome to Home Page

This is my home page.

────────────────
Copyright 2026
```

---

# 6.4 `<jsp:include>` vs `<%@ include %>`

### Important exam distinction

You have:

### Include Directive


```
<%@ include file="header.jsp" %>
```

### Include Action


```
<jsp:include page="header.jsp" />
```

They look similar, but their processing is different.

### Directive


```
Translation time
```

Conceptually:


```
home.jsp + header.jsp
       ↓
   Translation
       ↓
Generated Servlet
```

### JSP Action


```
Request time
```

Conceptually:


```
Request
   ↓
home.jsp
   ↓
<jsp:include>
   ↓
header.jsp
```

### Remember:


```
<%@ include %>
       ↓
Translation time

<jsp:include>
       ↓
Request time
```

This distinction is very useful for exams.

---

# 6.5 `<jsp:forward>`

`jsp:forward` transfers the request to another resource.

Example:


```
<jsp:forward page="welcome.jsp" />
```

Suppose:


```
login.jsp
    ↓
<jsp:forward>
    ↓
welcome.jsp
```

The current JSP stops processing and the request is forwarded.

---

# 6.6 Example with a Servlet

This connects directly with the Servlet concepts learned earlier.

Suppose:


```
index.html
    ↓
LoginServlet
```

The Servlet checks login credentials.

If successful:


```
request.getRequestDispatcher("welcome.jsp")
       .forward(request, response);
```

You can also have a JSP forward:


```
<jsp:forward page="welcome.jsp" />
```

So students should understand that **forwarding transfers the request internally on the server**.

---

# 6.7 `<jsp:param>`

`jsp:param` allows us to pass an additional parameter.

Example:


```
<jsp:forward page="welcome.jsp">

    <jsp:param name="message" value="Welcome Akash" />

</jsp:forward>
```

Then in `welcome.jsp`:


```
${param.message}
```

Output:


```
Welcome Akash
```

This example also connects directly to **EL**.

---

# 6.8 What is `<jsp:useBean>`?

This is an older JSP mechanism for working with JavaBeans.

Example:


```
<jsp:useBean
    id="student"
    class="com.test.Student" />
```

This creates or obtains a JavaBean object.

Then:


```
<jsp:setProperty
    name="student"
    property="name"
    value="Akash" />
```

And:


```
<jsp:getProperty
    name="student"
    property="name" />
```

Output:


```
Akash
```

---

# 6.9 Why JavaBeans?

A JavaBean is basically a Java class following certain conventions, commonly having:


```
private String name;
```

with getters/setters:


```
public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}
```

Then JSP can work with the object's properties.

---

# 6.10 Important distinction: JSP Tags vs JSTL

Students may ask:

> "Is `<c:forEach>` a JSP tag?"

It is a **tag-library tag**, specifically from JSTL.

For example:


```
<c:forEach var="name" items="${names}">
    ${name}
</c:forEach>
```

This is different from the standard JSP action:


```
<jsp:include page="header.jsp" />
```

So teach them:


```
JSP Standard Actions
        ↓
<jsp:include>
<jsp:forward>
<jsp:param>
<jsp:useBean>
...

JSTL Tags
        ↓
<c:if>
<c:forEach>
<c:choose>
...
```

---

# 6.11 Our complete JSP picture

At this point, the main JSP concepts fit together as follows:


```
                 JSP
                  |
      ┌───────────┼────────────┐
      ↓           ↓            ↓
 Scriptlets       EL       JSP Actions
   <% %>         ${}       <jsp:...>
      |
   Java code
```

And then comes:


```
                 JSTL
                  |
          <c:if>
          <c:forEach>
          <c:choose>
```

---

# 6.12 Practical Demonstration

Use the existing Servlet → JSP application so the concepts remain connected.

Create:


```
header.jsp


<h1>Student Portal</h1>
<hr>
```

Then in `hello.jsp`:


```
<!DOCTYPE html>
<html>
<body>

<jsp:include page="header.jsp" />

<h2>Hey ${name}!</h2>

<p>Welcome to the Student Portal.</p>

</body>
</html>
```

The resulting flow is:


```
Servlet
   ↓
hello.jsp
   ↓
<jsp:include>
   ↓
header.jsp
```

This gives you a natural practical demonstration of JSP Actions.

---

## The important things for the exam

Students should know:

**`<jsp:include>`**

> Includes another resource during request processing.

**`<jsp:forward>`**

> Forwards the current request to another resource.

**`<jsp:param>`**

> Passes parameters to an included/forwarded resource.

**`<jsp:useBean>`**

> Creates or locates a JavaBean.

**`<jsp:setProperty>`**

> Sets a bean property.

**`<jsp:getProperty>`**

> Gets/displays a bean property.

---

# 7. JSTL — JSP Standard Tag Library

That's where everything comes together:


```
Scriptlet
   ↓
"How do I write Java?"

EL
   ↓
"How do I access data?"

JSP Tags
   ↓
"How do I perform JSP operations?"

JSTL
   ↓
"How do I do conditions, loops, formatting, etc.
 without putting Java code in my JSP?"
```

**Next: JSTL — and this one deserves a proper live example with `<c:if>` and `<c:forEach>`.**

next

## 7. JSTL — JSP Standard Tag Library

JSTL connects the JSP concepts learned so far:

> **Scriptlets → EL → JSP Tags → JSTL**

### 1. What is JSTL?

**JSTL (JSP Standard Tag Library)** is a collection of predefined JSP tags used to perform common tasks such as:

-  Conditions 
-  Loops 
-  Iteration 
-  Formatting 
-  Working with URLs 
-  Basic data manipulation 

The main idea is:

> **Instead of writing Java code inside JSP using scriptlets, use JSTL tags.**

### Without JSTL


```
<%
    int age = 20;

    if (age >= 18) {
%>

<h2>You are eligible.</h2>

<%
    }
%>
```

This puts Java logic directly inside the JSP.

### With JSTL


```
<c:if test="${age >= 18}">
    <h2>You are eligible.</h2>
</c:if>
```

Much cleaner.

---

# 2. JSTL + EL work together

This is an important concept for students.

EL:


```
${name}
```

is mainly used to **access/evaluate data**.

JSTL:


```
<c:if>
<c:forEach>
<c:choose>
```

is used to **perform common JSP operations**.

So:


```
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

# 3. JSTL Core Library

The most important JSTL library for your syllabus is the **Core library**.

Common tags:

| Tag             | Purpose                       |
| --------------- | ----------------------------- |
| `<c:if>`        | if condition                  |
| `<c:choose>`    | if-else / multiple conditions |
| `<c:when>`      | condition inside choose       |
| `<c:otherwise>` | else                          |
| `<c:forEach>`   | loop                          |
| `<c:set>`       | create/set variable           |
| `<c:remove>`    | remove variable               |
| `<c:out>`       | display output                |
| `<c:url>`       | create URL                    |

The prefix usually used is:


```
c
```

So we write:


```
<c:if>
<c:forEach>
```

---

# 4. First: Add JSTL Taglib

At the top of the JSP:


```
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
```

Now the JSP container knows that `c:` represents the JSTL Core tag library.

So:


```
<c:if>
```

becomes available.

---

# 5. Live Demo — `<c:if>`

Using the same Servlet that sends data:

### Servlet


```
String name = request.getParameter("name");

request.setAttribute("name", name);

request.getRequestDispatcher("hello.jsp")
       .forward(request, response);
```

Now in `hello.jsp`:


```
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


```
Akash
```

Output:


```
Hey Akash!

Welcome Akash!
```

If the user enters:


```
Rahul
```

the second message won't appear.

---

# 6. `<c:choose>` — if / else

Suppose we have:


```
marks = 75
```

We want:


```
90+ → Excellent
75+ → Very Good
50+ → Pass
below 50 → Fail
```

Using JSTL:


```
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

This is basically:


```
if
else if
else
```

but written using JSP tags.

---

# 7. `<c:forEach>` — Very Important

The `<c:forEach>` tag is one of the most useful JSTL tags for practical applications.

Suppose the Servlet sends:


```
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


```
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h2>Students</h2>

<c:forEach var="student" items="${students}">

    <p>${student}</p>

</c:forEach>
```

Output:


```
Students

Akash
Rahul
Priya
Amit
```

Notice something important:

### No Java loop!

We don't write:


```
<%
for (...) {
%>
```

Instead:


```
<c:forEach>
```

---

# 8. `begin`, `end`, `step`

You can also use:


```
<c:forEach var="i" begin="1" end="5">

    <p>${i}</p>

</c:forEach>
```

Output:


```
1
2
3
4
5
```

With step:


```
<c:forEach var="i" begin="1" end="10" step="2">

    ${i}

</c:forEach>
```

Output:


```
1
3
5
7
9
```

---

# 9. `<c:set>`

Used to create/set a variable.


```
<c:set var="name" value="Akash" />

<h2>Hello ${name}</h2>
```

Output:


```
Hello Akash
```

You can also set a value from EL:


```
<c:set var="age" value="${20}" />

Age: ${age}
```

---

# 10. `<c:out>`

Used to display a value.


```
<c:out value="${name}" />
```

Equivalent in many simple cases to:


```
${name}
```

So students should understand:


```
${name}
```

is usually simpler.

---

# 11. The Big Picture

The evolution of JSP programming can be understood as follows:

### Scriptlet


```
<%
if (age >= 18) {
%>

Adult

<%
}
%>
```

↓

### EL


```
${name}
```

↓

### JSTL


```
<c:if test="${age >= 18}">
    Adult
</c:if>
```

↓

### Loop


```
<c:forEach var="student" items="${students}">
    ${student}
</c:forEach>
```

So the philosophy is:

> **Move Java/business logic out of JSP and keep JSP focused on presentation.**

---

## Your complete JSP flow is now


```
                    Browser
                       │
                       ▼
                 index.html
                       │
                       ▼
                  Servlet
                       │
             ┌─────────┴─────────┐
             │                   │
             ▼                   ▼
      Business Logic          Data/DB
             │
             ▼
     request.setAttribute()
             │
             ▼
            JSP
             │
       ┌─────┼─────┐
       ▼     ▼     ▼
      EL    JSTL   JSP Tags
       │     │      │
       └─────┼──────┘
             ▼
          HTML
             │
             ▼
          Browser
```

### The key student takeaway

| Technology      | Main job                             |
| --------------- | ------------------------------------ |
| **Servlet**     | Java/backend logic                   |
| **JSP**         | Presentation                         |
| **EL**          | Access/display data                  |
| **JSP Actions** | JSP-level operations                 |
| **JSTL**        | Conditions, loops, common operations |
| **HTML**        | Final UI                             |

## Next Step

A good way to apply these concepts is to build a small **JSP + Servlet mini-project** using:

**Form → Servlet → Request Attributes → EL → JSTL → JSP**

This connects the complete flow into one practical application.
