# JSP & JSF — Student Notes

> **Student-Oriented Notes**
>
> These notes follow the same classroom flow used in the lecture: first understand **why** a technology is needed, then understand its **syntax**, then see a **practical example**, followed by the **complete flow**, important distinctions, and finally the **university exam questions**.

---

# 1. JSTL — JSP Standard Tag Library

## 1.1 What is JSTL?

**JSTL = JSP Standard Tag Library**

JSTL is a collection of predefined JSP tags used to perform common tasks such as:

- Conditions
- Loops
- Iteration
- Formatting
- Working with URLs
- Basic data manipulation

The main idea is:

> **Instead of writing Java code inside JSP using scriptlets, use JSTL tags.**

---

## 1.2 Why do we need JSTL?

Before JSTL, Java code was commonly written directly inside JSP using **scriptlets**.

### Without JSTL

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

Here, Java logic is directly mixed with HTML.

This becomes difficult to read and maintain when the JSP page becomes large.

### With JSTL

```jsp
<c:if test="${age >= 18}">
    <h2>You are eligible.</h2>
</c:if>
```

Much cleaner.

### Basic idea

```text
Scriptlet
    ↓
Java code inside JSP
    ↓
Difficult to maintain

JSTL
    ↓
JSP tags
    ↓
Cleaner JSP
```

---

# 2. JSTL + EL Work Together

This is an important concept.

We already learned **EL — Expression Language**.

EL:

```jsp
${name}
```

is mainly used to **access/evaluate data**.

JSTL:

```jsp
<c:if>
<c:forEach>
<c:choose>
```

is used to **perform common JSP operations**.

So the flow becomes:

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

### Example

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

JSTL can then be used to perform conditions and loops using that data.

---

# 3. JSTL Core Library

The most important JSTL library for this topic is the **Core library**.

| Tag | Purpose |
|---|---|
| `<c:if>` | if condition |
| `<c:choose>` | if-else / multiple conditions |
| `<c:when>` | condition inside choose |
| `<c:otherwise>` | else |
| `<c:forEach>` | loop |
| `<c:set>` | create/set variable |
| `<c:remove>` | remove variable |
| `<c:out>` | display output |
| `<c:url>` | create URL |

The prefix usually used is:

```text
c
```

So we write:

```jsp
<c:if>
<c:forEach>
```

---

# 4. Adding JSTL to the JSP

At the top of the JSP page, add the JSTL Core tag library declaration:

```jsp
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
```

Now the JSP container knows that:

```text
c:
```

represents the JSTL Core tag library.

Therefore tags such as:

```jsp
<c:if>
<c:forEach>
<c:choose>
```

can be used.

---

# 5. `<c:if>` — Conditional Statement

`<c:if>` is used when we want to execute some JSP content only when a condition is true.

## Example

Suppose the Servlet sends:

```java
String name = request.getParameter("name");

request.setAttribute("name", name);

request.getRequestDispatcher("hello.jsp")
       .forward(request, response);
```

Now in `hello.jsp`:

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

### If user enters:

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

the second message will not appear.

### Important

The condition is written using:

```jsp
test="${condition}"
```

---

# 6. `<c:choose>` — if / else-if / else

Suppose:

```text
marks = 75
```

We want:

```text
90+  → Excellent
75+  → Very Good
50+  → Pass
below 50 → Fail
```

Using JSTL:

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

This is conceptually similar to:

```text
if
else if
else
```

but written using JSP tags.

---

# 7. `<c:forEach>` — Loop

`<c:forEach>` is one of the most useful JSTL tags for practical applications.

Suppose the Servlet sends a list of students:

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

In `students.jsp`:

```jsp
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h2>Students</h2>

<c:forEach var="student" items="${students}">

    <p>${student}</p>

</c:forEach>
```

### Output

```text
Students

Akash
Rahul
Priya
Amit
```

Notice something important:

### No Java loop!

We do not write:

```jsp
<%
    for (...) {
%>
```

Instead:

```jsp
<c:forEach>
```

---

# 8. `<c:forEach>` with `begin`, `end`, and `step`

You can also use `begin` and `end`.

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

## Using `step`

```jsp
<c:forEach var="i" begin="1" end="10" step="2">

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

# 9. `<c:set>` — Set a Variable

`<c:set>` is used to create or set a variable.

```jsp
<c:set var="name" value="Akash" />

<h2>Hello ${name}</h2>
```

Output:

```text
Hello Akash
```

You can also set a value using EL:

```jsp
<c:set var="age" value="${20}" />

Age: ${age}
```

---

# 10. `<c:out>` — Display a Value

`<c:out>` is used to display a value.

```jsp
<c:out value="${name}" />
```

In many simple cases, this is equivalent to:

```jsp
${name}
```

So students should understand:

```jsp
${name}
```

is usually the simpler form for straightforward output.

---

# 11. Complete JSTL Picture

Now look at the evolution.

## Scriptlet

```jsp
<%
    if (age >= 18) {
%>

Adult

<%
    }
%>
```

↓

## EL

```jsp
${name}
```

↓

## JSTL

```jsp
<c:if test="${age >= 18}">
    Adult
</c:if>
```

↓

## JSTL Loop

```jsp
<c:forEach var="student" items="${students}">
    ${student}
</c:forEach>
```

The philosophy is:

> **Move Java/business logic out of JSP and keep JSP focused on presentation.**

---

# 12. Complete Servlet → JSP → EL → JSTL Flow

```text
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

### Key takeaway

| Technology | Main job |
|---|---|
| **Servlet** | Java/backend logic |
| **JSP** | Presentation |
| **EL** | Access/display data |
| **JSP Actions** | JSP-level operations |
| **JSTL** | Conditions, loops, common operations |
| **HTML** | Final UI |

---

# 13. JSP Lifecycle

## 13.1 What is JSP Lifecycle?

The **JSP Lifecycle** describes the complete process through which a JSP page is:

1. Translated into a Servlet
2. Compiled
3. Loaded
4. Instantiated
5. Initialized
6. Used to process requests
7. Destroyed

The most important point is:

> **A JSP is ultimately converted into a Servlet by the JSP container.**

---

# 14. JSP Lifecycle — Complete Flow

```text
             JSP File
                │
                ▼
       JSP Translation
                │
                ▼
        Servlet Source
                │
                ▼
          Compilation
                │
                ▼
       Servlet Class
                │
                ▼
       Class Loading
                │
                ▼
       Object Creation
                │
                ▼
          jspInit()
                │
                ▼
         _jspService()
                │
                ▼
        HTTP Response
                │
                ▼
             Browser
                │
                │
        When JSP is removed
                ▼
          jspDestroy()
```

---

# 15. Step 1 — JSP Translation

Suppose we create:

```text
hello.jsp
```

The JSP container first translates the JSP into Servlet source code.

Conceptually:

```text
hello.jsp
    ↓
JSP Translator
    ↓
hello_jsp.java
```

The generated Servlet contains the code required to generate the final HTML response.

---

# 16. Step 2 — Compilation

The generated Servlet source code is compiled.

```text
hello_jsp.java
       ↓
Java Compiler
       ↓
hello_jsp.class
```

So:

```text
JSP
 ↓
Servlet source
 ↓
Servlet class
```

---

# 17. Step 3 — Class Loading

The generated Servlet class is loaded into memory by the container.

```text
hello_jsp.class
       ↓
Class Loader
       ↓
JVM Memory
```

---

# 18. Step 4 — Object Creation

The container creates an object of the generated Servlet class.

Conceptually:

```java
Hello_jsp obj = new Hello_jsp();
```

The exact generated class name depends on the container.

---

# 19. Step 5 — `jspInit()`

After creating the JSP Servlet object, the container calls:

```java
jspInit();
```

This method is called once during the JSP lifecycle.

It can be used for initialization-related work.

Conceptual lifecycle:

```text
Object Created
      ↓
  jspInit()
      ↓
Initialization complete
```

---

# 20. Step 6 — `_jspService()`

This is the main request-processing phase.

For every request, the container invokes:

```java
_jspService(request, response);
```

This method generates the response for the client.

Conceptually:

```text
Browser Request
      ↓
_jspService()
      ↓
HTML Response
      ↓
Browser
```

### Important exam point

`_jspService()` is generated by the JSP container.

You normally do **not** override it manually.

---

# 21. Step 7 — `jspDestroy()`

When the JSP Servlet is being removed from service, the container calls:

```java
jspDestroy();
```

This is used for cleanup-related work.

Flow:

```text
JSP Servlet
    ↓
jspDestroy()
    ↓
Removed from service
```

---

# 22. JSP Lifecycle — Easy Memory Trick

Remember:

```text
T → C → L → I → S → D
```

Where:

```text
T = Translation
C = Compilation
L = Loading
I = Initialization
S = Service
D = Destroy
```

Or remember the major methods:

```text
jspInit()
   ↓
_jspService()
   ↓
jspDestroy()
```

---

# 23. JSP Lifecycle vs Servlet Lifecycle

Students often confuse these two.

### Servlet Lifecycle

```text
Loading
   ↓
Instantiation
   ↓
init()
   ↓
service()
   ↓
destroy()
```

### JSP Lifecycle

```text
Translation
   ↓
Compilation
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

The key difference is:

> **JSP has an additional translation and compilation stage because the JSP page must first become a Servlet.**

---


---

# 24. JSP Lifecycle vs Servlet Lifecycle

Students often confuse these two.

### Servlet Lifecycle

```text
Loading
   ↓
Instantiation
   ↓
init()
   ↓
service()
   ↓
destroy()
```

### JSP Lifecycle

```text
Translation
   ↓
Compilation
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

The key difference is:

> **JSP has an additional translation and compilation stage because the JSP page must first become a Servlet.**

---

# 25. Important Student Connections

Students should connect these JSP topics instead of memorizing them separately.

## JSP Lifecycle

```text
JSP
 ↓
Translation
 ↓
Compilation
 ↓
Loading
 ↓
jspInit()
 ↓
_jspService()
 ↓
jspDestroy()
```

## EL

```text
EL
 ↓
Access/display data
 ↓
${name}
```

## JSP Actions

```text
JSP Actions
 ↓
<jsp:include>
<jsp:forward>
<jsp:param>
<jsp:useBean>
...
```

## JSTL

```text
JSTL
 ↓
Conditions / loops / common operations
 ↓
<c:if>
<c:forEach>
<c:choose>
```

---

# 26. Complete JSP Flow

```text
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
       ┌──────┼───────┐
       ▼      ▼       ▼
      EL     JSTL   JSP Actions
       │      │       │
       └──────┼───────┘
              ▼
             HTML
              │
              ▼
           Browser
```

### Key takeaway

| Technology | Main job |
|---|---|
| **Servlet** | Java/backend logic |
| **JSP** | Presentation |
| **EL** | Access/display data |
| **JSP Actions** | JSP-level operations |
| **JSTL** | Conditions, loops, common operations |
| **HTML** | Final UI |

---

# 27. Exam-Oriented Important Points

## JSTL

- JSTL stands for **JSP Standard Tag Library**.
- It provides predefined tags for common JSP operations.
- It reduces the need for Java scriptlets inside JSP.
- Core JSTL uses the `c` prefix.
- Important tags include:
  - `<c:if>`
  - `<c:choose>`
  - `<c:when>`
  - `<c:otherwise>`
  - `<c:forEach>`
  - `<c:set>`
  - `<c:remove>`
  - `<c:out>`
  - `<c:url>`

## JSP Lifecycle

Remember:

```text
Translation
    ↓
Compilation
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

## JSP Actions

Remember:

```text
<jsp:include>
<jsp:forward>
<jsp:param>
<jsp:useBean>
<jsp:setProperty>
<jsp:getProperty>
```

---

# 28. 10-Mark University Questions

Prepare the following as **10-mark long-answer questions**.

### Question 1

> **What is JSTL (JSP Standard Tag Library)? Explain its purpose and important JSTL Core tags with suitable examples.**

### Question 2

> **Explain the JSP Lifecycle with a neat diagram. Describe JSP translation, compilation, loading, initialization, request processing and destruction.**

### Question 3

> **Explain JSP Standard Actions with suitable examples. Discuss `<jsp:include>`, `<jsp:forward>`, `<jsp:param>`, `<jsp:useBean>`, `<jsp:setProperty>` and `<jsp:getProperty>`.**

### Question 4

> **Explain the difference between `<%@ include %>` and `<jsp:include>` with suitable examples.**

### Question 5

> **What is JSTL (JSP Standard Tag Library)? Explain how JSTL reduces the use of Java scriptlets in JSP with suitable examples.**

---

# 29. Last-Minute Revision

## JSP Lifecycle

```text
JSP
 ↓
Translation
 ↓
Compilation
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

## JSP Actions

```text
<jsp:include>
<jsp:forward>
<jsp:param>
<jsp:useBean>
<jsp:setProperty>
<jsp:getProperty>
```

## JSTL

```text
<c:if>
<c:choose>
<c:when>
<c:otherwise>
<c:forEach>
<c:set>
<c:remove>
<c:out>
<c:url>
```

### One-line memory

> **JSP is used for presentation, EL is used to access/display data, JSP Actions perform JSP-level operations, and JSTL provides tags for conditions, loops, and other common JSP operations.**
