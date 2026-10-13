# JSPStudentPortal — Practical Setup Guide

This guide builds `JSPStudentPortal` in Eclipse using Java 21 and Tomcat 10.0.20.

> **Compatibility:** Tomcat 10.0.x supports Jakarta Servlet 5.0, so **Dynamic Web Module 5.0** is appropriate. You do not need Module 6.0 for this project. Java 21 can be used if installed and configured in Eclipse.

## 1. Install and verify Java 21

1. Download a JDK 21 `.pkg` installer for macOS from [Eclipse Temurin JDK 21](https://adoptium.net/temurin/releases/?version=21).
2. Choose `aarch64` for Apple Silicon or `x64` for Intel Macs.
3. Install it, open Terminal, and run:

```bash
java -version
javac -version
/usr/libexec/java_home -V
```

## 2. Install Eclipse

Download **Eclipse IDE for Enterprise Java and Web Developers** from the [official Eclipse packages page](https://www.eclipse.org/downloads/packages/). Choose the macOS build matching your Mac's architecture.

## 3. Add JDK 21 to Eclipse

1. Open **Eclipse → Settings/Preferences → Java → Installed JREs**.
2. Click **Add → Standard VM**.
3. For **JRE home**, use the JDK path returned by:

   ```bash
   /usr/libexec/java_home -v 21
   ```

4. Click **Finish**, tick JDK 21, then **Apply and Close**.

## 4. Create the project

1. Select **File → New → Dynamic Web Project**.
2. Project name: `JSPStudentPortal`.
3. Select your configured **Apache Tomcat v10.0** target runtime.
4. Set **Dynamic Web Module** to **5.0**.
5. Finish the wizard.

Typical project structure:

```text
JSPStudentPortal/
├── Java Resources/src/
│   └── com.test/
│       └── StudentServlet.java
└── WebContent/
    ├── index.html
    ├── result.jsp
    └── WEB-INF/
        └── lib/
            ├── jakarta.servlet.jsp.jstl-api-2.0.0.jar
            └── jakarta.servlet.jsp.jstl-2.0.0.jar
```

If your project uses `src/main/webapp` instead of `WebContent`, put the web files and `WEB-INF/lib` there.

## 5. Create `index.html`

Create this file in `WebContent` (or `src/main/webapp`):

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Student Portal</title>
</head>
<body>
    <h1>Student Portal</h1>
    <form action="student" method="post">
        <label>Student Name:</label>
        <input type="text" name="name" required>
        <br><br>
        <label>Age:</label>
        <input type="number" name="age" min="1" max="120" required>
        <br><br>
        <label>Marks:</label>
        <input type="number" name="marks" min="0" max="100" required>
        <br><br>
        <button type="submit">Submit</button>
    </form>
</body>
</html>
```

## 6. Create `StudentServlet.java`

1. Right-click `Java Resources/src` → **New → Package** → enter `com.test`.
2. Right-click the package → **New → Class** → enter `StudentServlet`.
3. Paste this code:

```java
package com.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String ageInput = request.getParameter("age");
        String marksInput = request.getParameter("marks");

        if (name == null || name.trim().isEmpty()
                || ageInput == null || marksInput == null) {
            response.sendError(400, "Please enter all details.");
            return;
        }

        try {
            int age = Integer.parseInt(ageInput);
            int marks = Integer.parseInt(marksInput);

            if (age < 1 || age > 120 || marks < 0 || marks > 100) {
                response.sendError(400, "Invalid age or marks.");
                return;
            }

            request.setAttribute("name", name.trim());
            request.setAttribute("age", age);
            request.setAttribute("marks", marks);

            List<String> students = Arrays.asList(
                "Akash", "Rahul", "Priya", "Amit"
            );
            request.setAttribute("students", students);

            request.getRequestDispatcher("/result.jsp")
                   .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(400, "Age and marks must be whole numbers.");
        }
    }
}
```

Use `jakarta.servlet.*` imports with Tomcat 10; do not use `javax.servlet.*`.

## 7. Add JSTL libraries

The JSP uses JSTL tags, so add both JAR files to `WEB-INF/lib`.

- [JSTL API 3.0.2 JAR](https://repo1.maven.org/maven2/jakarta/servlet/jsp/jstl/jakarta.servlet.jsp.jstl-api/3.0.2/jakarta.servlet.jsp.jstl-api-3.0.2.jar)
- [JSTL implementation 3.0.1 JAR](https://repo1.maven.org/maven2/org/glassfish/web/jakarta.servlet.jsp.jstl/3.0.1/jakarta.servlet.jsp.jstl-3.0.1.jar)

Folder location:

```text
JSPStudentPortal/WebContent/WEB-INF/lib/
```

If using `src/main/webapp`, place them in `src/main/webapp/WEB-INF/lib`. Refresh the project in Eclipse after copying the JARs.

## 8. Create `result.jsp`

Create it beside `index.html`:

```jsp
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Student Result</title>
</head>
<body>
    <h1>Student Result</h1>
    <h2>Welcome, <c:out value="${name}" />!</h2>
    <p>Your age is ${age}.</p>
    <p>Your marks are ${marks}/100.</p>

    <hr>
    <h2>Age Eligibility</h2>
    <c:if test="${age >= 18}">
        <p>You are eligible by age.</p>
    </c:if>
    <c:if test="${age < 18}">
        <p>You are below 18 years of age.</p>
    </c:if>

    <hr>
    <h2>Grade Result</h2>
    <c:choose>
        <c:when test="${marks >= 90}">
            <h3>Excellent! Grade A+</h3>
        </c:when>
        <c:when test="${marks >= 75}">
            <h3>Very Good! Grade A</h3>
        </c:when>
        <c:when test="${marks >= 50}">
            <h3>Pass</h3>
        </c:when>
        <c:otherwise>
            <h3>Fail</h3>
        </c:otherwise>
    </c:choose>

    <hr>
    <h2>Student List</h2>
    <ul>
        <c:forEach var="student" items="${students}">
            <li><c:out value="${student}" /></li>
        </c:forEach>
    </ul>

    <a href="index.html">Back to Form</a>
</body>
</html>
```

## 9. Run the project

1. Save all files.
2. Right-click `JSPStudentPortal` → **Run As → Run on Server**.
3. Select your Tomcat 10.0 server.
4. Open:

```text
http://localhost:8080/JSPStudentPortal/index.html
```

If the context root differs, use that root in the URL.

### Test data

- Name: `Akash`
- Age: `20`
- Marks: `85`

Expected: welcome message, age 20, marks 85/100, age eligibility message, Grade A, and the sample student list.

## 10. Troubleshooting

- **`jakarta.servlet` imports are red:** Check **Properties → Targeted Runtimes** and select Tomcat 10.0; check Java Build Path for the server runtime library.
- **JSTL tag error:** Confirm both JSTL JARs are in `WEB-INF/lib`, refresh, then restart/re-publish Tomcat.
- **404 error:** Check the context root and make sure `index.html` is in the web content folder.
- **500 error:** Read the first relevant exception in Eclipse's Console.
- **Port 8080 busy:** Stop another server using that port or change Tomcat's port.

## What this practical demonstrates

- HTML form submission using POST
- Servlet request parameters and validation
- `request.setAttribute()` and forwarding to JSP
- JSP Expression Language (EL), such as `${name}`
- JSTL output (`c:out`), conditions (`c:if`), branching (`c:choose`) and loops (`c:forEach`)

**Remember:** Tomcat 10.0.20 → Dynamic Web Module 5.0. Use Tomcat 10.1 if you specifically need Servlet 6.0 / Dynamic Web Module 6.0.
