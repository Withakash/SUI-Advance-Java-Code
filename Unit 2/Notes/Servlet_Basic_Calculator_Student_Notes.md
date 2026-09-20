# Basic Calculator using Servlet + HTML

## What you will learn

In this practical, you will learn how an HTML form sends data to a Servlet and how the Servlet processes that data.

You will use:

- HTML `<form>`
- GET and POST requests
- `@WebServlet`
- `doGet()` / `doPost()`
- `request.getParameter()`
- `HttpServletResponse`
- Basic Java calculation

---

# 1. Project Structure

For a traditional Eclipse Dynamic Web Project:

```text
CalculatorProject
│
├── Java Resources
│   └── src
│       └── com.demo
│           └── CalculatorServlet.java
│
└── WebContent
    └── index.html
```

---

# 2. HTML Form

Create:

```text
WebContent/index.html
```

```html
<!DOCTYPE html>
<html>

<head>
    <title>Basic Calculator</title>
</head>

<body>

    <h1>Basic Calculator</h1>

    <form action="calculator" method="post">

        <label>Enter First Number:</label>
        <input type="number" name="num1" required>

        <br><br>

        <label>Enter Second Number:</label>
        <input type="number" name="num2" required>

        <br><br>

        <label>Select Operation:</label>

        <select name="operation">
            <option value="add">Addition</option>
            <option value="subtract">Subtraction</option>
            <option value="multiply">Multiplication</option>
            <option value="divide">Division</option>
        </select>

        <br><br>

        <button type="submit">
            Calculate
        </button>

    </form>

</body>

</html>
```

---

# 3. Understanding the HTML

## `<form>`

```html
<form action="calculator" method="post">
```

Two important attributes are used here.

### `action`

```html
action="calculator"
```

This tells the browser where to send the form data.

Our Servlet has:

```java
@WebServlet("/calculator")
```

So both match:

```text
HTML                    Servlet
------------------------------------
action="calculator"  →  @WebServlet("/calculator")
```

### `method`

```html
method="post"
```

This tells the browser to send the form data using HTTP POST.

---

# 4. Input `name` is Very Important

Look at:

```html
<input type="number" name="num1">
```

The important part is:

```html
name="num1"
```

The Servlet will use the same name:

```java
request.getParameter("num1")
```

Similarly:

```html
name="num2"
```

is read using:

```java
request.getParameter("num2")
```

And:

```html
<select name="operation">
```

is read using:

```java
request.getParameter("operation")
```

## Remember

```text
HTML name                         Java parameter
----------------------------------------------------
name="num1"             →        getParameter("num1")
name="num2"             →        getParameter("num2")
name="operation"        →        getParameter("operation")
```

The names must match exactly.

---

# 5. Calculator Servlet

Create:

```text
CalculatorServlet.java
```

```java
package com.demo;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calculator")
public class CalculatorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        // Get values from HTML form
        double num1 = Double.parseDouble(
                request.getParameter("num1"));

        double num2 = Double.parseDouble(
                request.getParameter("num2"));

        String operation =
                request.getParameter("operation");

        // Variable to store answer
        double result = 0;

        // Perform calculation
        if (operation.equals("add")) {

            result = num1 + num2;

        } else if (operation.equals("subtract")) {

            result = num1 - num2;

        } else if (operation.equals("multiply")) {

            result = num1 * num2;

        } else if (operation.equals("divide")) {

            if (num2 == 0) {

                response.setContentType("text/html");

                response.getWriter().println(
                    "<h1>Cannot divide by zero!</h1>"
                );

                return;
            }

            result = num1 / num2;
        }

        // Send response to browser
        response.setContentType("text/html");

        response.getWriter().println(
            "<h1>Calculator Result</h1>"
        );

        response.getWriter().println(
            "<p>First Number: " + num1 + "</p>"
        );

        response.getWriter().println(
            "<p>Second Number: " + num2 + "</p>"
        );

        response.getWriter().println(
            "<p>Operation: " + operation + "</p>"
        );

        response.getWriter().println(
            "<h2>Result: " + result + "</h2>"
        );
    }
}
```

---

# 6. Understanding the Servlet Code

## Step 1 — Servlet Mapping

```java
@WebServlet("/calculator")
```

This creates the URL mapping:

```text
/calculator
```

When the browser sends a request to `/calculator`, Tomcat finds this Servlet.

---

# 7. Why `doPost()`?

Our HTML contains:

```html
<form action="calculator" method="post">
```

Therefore, the browser sends a POST request.

The request flow is:

```text
Browser
   ↓
POST /calculator
   ↓
Tomcat
   ↓
service()
   ↓
doPost()
```

`service()` determines which method should handle the request.

```text
GET  → doGet()
POST → doPost()
```

---

# 8. Reading Form Data

This is one of the most important Servlet concepts.

```java
request.getParameter("num1")
```

gets the value submitted by:

```html
<input name="num1">
```

For example, if the user enters:

```text
10
```

then:

```java
request.getParameter("num1")
```

returns:

```text
"10"
```

Notice that the returned value is a **String**.

---

# 9. Why `Double.parseDouble()`?

This:

```java
request.getParameter("num1")
```

returns a String.

But we want to perform mathematical operations.

So we convert it:

```java
double num1 = Double.parseDouble(
    request.getParameter("num1"));
```

Flow:

```text
HTML
  ↓
"10"
  ↓
getParameter()
  ↓
String
  ↓
Double.parseDouble()
  ↓
10.0
  ↓
Java calculation
```

---

# 10. Getting the Operation

The HTML contains:

```html
<select name="operation">
    <option value="add">Addition</option>
    <option value="subtract">Subtraction</option>
    <option value="multiply">Multiplication</option>
    <option value="divide">Division</option>
</select>
```

Suppose the user selects Addition.

The browser sends:

```text
operation=add
```

The Servlet receives it using:

```java
String operation =
    request.getParameter("operation");
```

The value becomes:

```text
"add"
```

Then we check:

```java
if (operation.equals("add")) {
    result = num1 + num2;
}
```

---

# 11. Performing the Calculation

### Addition

```java
if (operation.equals("add")) {
    result = num1 + num2;
}
```

### Subtraction

```java
else if (operation.equals("subtract")) {
    result = num1 - num2;
}
```

### Multiplication

```java
else if (operation.equals("multiply")) {
    result = num1 * num2;
}
```

### Division

```java
else if (operation.equals("divide")) {
    result = num1 / num2;
}
```

---

# 12. Division by Zero

We should not allow:

```text
10 / 0
```

So we check:

```java
if (num2 == 0) {
    response.getWriter().println(
        "<h1>Cannot divide by zero!</h1>"
    );
    return;
}
```

`return` stops the current method.

---

# 13. Sending the Response

The Servlet sends HTML back to the browser:

```java
response.setContentType("text/html");
```

This tells the browser:

> The response contains HTML.

Then:

```java
response.getWriter().println(
    "<h2>Result: " + result + "</h2>"
);
```

sends the result.

---

# 14. Complete Request-Response Flow

Suppose the user enters:

```text
First Number  = 10
Second Number = 5
Operation     = Addition
```

The browser sends approximately:

```text
POST /calculator

num1=10
num2=5
operation=add
```

Tomcat receives it:

```text
                    Browser
                       |
                       | POST
                       | num1=10
                       | num2=5
                       | operation=add
                       ↓
                    Tomcat
                       |
                       ↓
                   service()
                       |
                       ↓
                    doPost()
                       |
             ┌─────────┴─────────┐
             ↓         ↓          ↓
          num1       num2     operation
           10          5          add
             \          |         /
              \         |        /
               └──── Calculation
                       |
                       ↓
                    Result = 15
                       |
                       ↓
                 HTTP Response
                       |
                       ↓
                    Browser
```

---

# 15. GET vs POST — Where Should We Use Them?

This calculator is a good example to understand the difference.

## GET

GET sends data as part of the URL.

Example:

```text
/calculator?num1=10&num2=5&operation=add
```

You can implement this with:

```java
@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
                     throws ServletException, IOException {

    // read parameters
}
```

### GET is useful when:

- You are retrieving/searching data.
- The request does not change server-side data.
- You want the URL to contain the parameters.
- The result can safely be bookmarked/shared.

Examples:

```text
Search
Product filtering
Student search
Viewing a record
```

---

# 16. POST

POST sends form data in the HTTP request body rather than putting it in the URL.

Our calculator uses:

```html
<form action="calculator" method="post">
```

So:

```text
POST
  ↓
service()
  ↓
doPost()
```

### POST is useful when:

- Sending form data.
- Creating/submitting data.
- Performing an action.
- You do not want form values exposed in the URL.

Examples:

```text
Login
Registration
Add Student
Add Product
Submit Form
```

Important: **POST is not automatically encrypted.** For sensitive data, HTTPS is what protects data in transit.

---

# 17. Can We Use GET for the Calculator?

Yes.

Change:

```html
<form action="calculator" method="post">
```

to:

```html
<form action="calculator" method="get">
```

Then the Servlet should handle:

```java
doGet()
```

The URL may look like:

```text
http://localhost:8080/CalculatorProject/calculator?num1=10&num2=5&operation=add
```

For this classroom calculator, **both GET and POST can work**.

The important learning point is understanding how the request method determines whether `doGet()` or `doPost()` handles the request.

---

# 18. What Happens If We Use POST but Only Have `doGet()`?

If the form says:

```html
method="post"
```

but the Servlet only overrides:

```java
doGet()
```

then `doGet()` is not supposed to handle that POST request.

The request is dispatched to:

```java
doPost()
```

If you have not overridden `doPost()`, the inherited `HttpServlet.doPost()` handles it and normally returns **HTTP 405 Method Not Allowed**.

This is a useful practical demonstration of the role of `service()`.

---

# 19. Practical Test

Run:

```text
WebContent/index.html
```

using:

```text
Right Click
    ↓
Run As
    ↓
Run on Server
```

Test:

| Number 1 | Number 2 | Operation | Expected Result |
|---:|---:|---|---:|
| 10 | 5 | Addition | 15 |
| 10 | 5 | Subtraction | 5 |
| 10 | 5 | Multiplication | 50 |
| 10 | 5 | Division | 2 |
| 10 | 0 | Division | Cannot divide by zero |

---

# 20. Common Mistakes

### Mistake 1 — `name` doesn't match

HTML:

```html
<input name="num1">
```

Java:

```java
request.getParameter("number1");
```

Wrong.

They must match:

```java
request.getParameter("num1");
```

---

### Mistake 2 — Wrong servlet URL

HTML:

```html
<form action="calculator">
```

Servlet:

```java
@WebServlet("/calculate")
```

These do not match.

Use:

```html
action="calculator"
```

and:

```java
@WebServlet("/calculator")
```

---

### Mistake 3 — Running the Servlet directly

Do not treat:

```text
CalculatorServlet.java
```

like a normal Java `main()` program.

The Servlet is executed by **Tomcat** when an HTTP request reaches its mapping.

---

### Mistake 4 — Forgetting `method`

If you write:

```html
<form action="calculator">
```

the default method is GET.

So the request goes to:

```java
doGet()
```

---

# 21. Remember This

The most important pattern is:

```text
HTML FORM
   ↓
action
   ↓
@WebServlet mapping
   ↓
HTTP method
   ↓
service()
   ↓
doGet() / doPost()
   ↓
request.getParameter()
   ↓
Java processing
   ↓
response.getWriter()
   ↓
Browser
```

---

# Assignment 1 — Student Marks Calculator

Create a web application that accepts:

```text
Student Name
Subject 1 Marks
Subject 2 Marks
Subject 3 Marks
```

The Servlet should calculate:

```text
Total Marks
Average Marks
```

Display the result in the browser.

### Requirements

- Use HTML form.
- Use POST.
- Create a Servlet using `@WebServlet`.
- Use `request.getParameter()`.
- Convert marks from String to numeric values.
- Calculate total and average.
- Display the result using `response.getWriter()`.

---

# Assignment 2 — Simple Bill Calculator

Create a web application that accepts:

```text
Product Name
Price
Quantity
```

The Servlet should calculate:

```text
Total = Price × Quantity
```

Display:

```text
Product Name
Price
Quantity
Total Bill
```

### Requirements

- Use HTML form.
- Use POST.
- Use `doPost()`.
- Read all values using `request.getParameter()`.
- Perform calculation in Java.
- Display the result in the browser.

### Optional challenge

Add a discount field and calculate:

```text
Subtotal
Discount
Final Amount
```

---

# Final Practice Challenge

After completing the calculator, try changing it from:

```text
POST → GET
```

and implement:

```java
doGet()
```

Observe the difference in the browser URL.

This will help you understand **GET vs POST practically**, rather than only memorizing the definitions.
