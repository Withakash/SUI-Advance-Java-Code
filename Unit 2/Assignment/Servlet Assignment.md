# Servlet Assignment

## Beginner Servlet Projects

### Objective

Create a simple web application using **HTML Forms and Java Servlets**.

For each project, students should:

1. Create an HTML form.
2. Take input from the user.
3. Submit the form to a Servlet.
4. Use `request.getParameter()` to receive the form data.
5. Convert the received `String` values into `int` or `double` where required.
6. Perform the required calculation/processing.
7. Display the result from the Servlet using `response.getWriter()`.

---

## 1. Dollar to Rupee Converter

Create a Servlet application that accepts an amount in Dollars and converts it into Indian Rupees.

**Input:**
- Dollar amount

**Output:**
- Equivalent amount in Rupees

---

## 2. Temperature Converter

Create a Servlet application that accepts a temperature in Celsius and converts it into Fahrenheit.

**Input:**
- Temperature in Celsius

**Output:**
- Temperature in Fahrenheit

---

## 3. Length Converter

Create a Servlet application that accepts a length in meters.

Display the equivalent values in:
- Kilometers
- Centimeters

**Input:**
- Length in meters

**Output:**
- Kilometers
- Centimeters

---

## 4. Weight Converter

Create a Servlet application that accepts weight in kilograms.

Display the equivalent weight in:
- Grams
- Pounds

**Input:**
- Weight in kilograms

**Output:**
- Weight in grams
- Weight in pounds

---

## 5. Simple Interest Calculator

Create a Servlet application to calculate Simple Interest.

**Input:**
- Principal amount
- Rate of interest
- Time

**Output:**
- Simple Interest
- Total Amount

**Formula:**

```text
Simple Interest = (P × R × T) / 100

Total Amount = P + Simple Interest
```

---

## 6. Percentage Calculator

Create a Servlet application that accepts marks obtained and total marks.

Calculate the student's percentage.

**Input:**
- Marks obtained
- Total marks

**Output:**
- Percentage

---

## 7. Grade Calculator

Create a Servlet application that accepts marks and calculates the student's grade.

You may use the following grading system:

```text
90 - 100  → A
80 - 89   → B
70 - 79   → C
60 - 69   → D
Below 60  → F
```

**Input:**
- Marks

**Output:**
- Grade

---

## 8. Age Calculator

Create a Servlet application that accepts the birth year and current year.

Calculate the person's age.

**Input:**
- Birth year
- Current year

**Output:**
- Age

---

## 9. Simple Calculator

Create a Servlet-based calculator.

**Input:**
- First number
- Second number
- Operator

Supported operators:

```text
+
-
*
/
```

**Output:**
- Result of the selected operation

Example:

```text
Number 1: 20
Number 2: 5
Operator: *

Result: 100
```

---

## 10. BMI Calculator

Create a Servlet application to calculate Body Mass Index (BMI).

**Input:**
- Weight in kilograms
- Height in meters

**Formula:**

```text
BMI = Weight / (Height × Height)
```

**Output:**
- BMI value

You may also display a basic category:

```text
Below 18.5  → Underweight
18.5 - 24.9 → Normal
25 - 29.9   → Overweight
30 or above → Obese
```

---

## 11. Shopping Bill Calculator

Create a Servlet application that calculates the total price of a shopping item.

**Input:**
- Item name
- Item price
- Quantity

**Output:**
- Item name
- Total bill

**Formula:**

```text
Total = Price × Quantity
```

---

## 12. Salary Calculator

Create a Servlet application to calculate an employee's salary.

**Input:**
- Basic salary

Calculate:

```text
HRA = 20% of Basic Salary
DA  = 10% of Basic Salary
```

**Output:**
- Basic Salary
- HRA
- DA
- Gross Salary

**Formula:**

```text
Gross Salary = Basic Salary + HRA + DA
```

---

## 13. Fuel Cost Calculator

Create a Servlet application to calculate the fuel cost of a journey.

**Input:**
- Distance in kilometers
- Vehicle mileage (km/litre)
- Fuel price per litre

**Output:**
- Fuel required
- Total fuel cost

**Formula:**

```text
Fuel Required = Distance / Mileage

Fuel Cost = Fuel Required × Fuel Price
```

---

## 14. Electricity Bill Calculator

Create a Servlet application that calculates an electricity bill based on units consumed.

Use the following sample slab:

```text
0 - 100 units     → ₹5 per unit
101 - 200 units   → ₹7 per unit
201 - 300 units   → ₹10 per unit
Above 300 units   → ₹12 per unit
```

**Input:**
- Units consumed

**Output:**
- Units consumed
- Electricity bill

Students should use `if-else` conditions to implement the slabs.

---

## 15. EMI Calculator

Create a Servlet application that calculates the monthly EMI for a loan.

**Input:**
- Loan amount
- Annual interest rate
- Loan duration in months

Use the EMI formula:

```text
EMI = P × R × (1 + R)^N / ((1 + R)^N - 1)
```

Where:

```text
P = Principal loan amount
R = Monthly interest rate
N = Number of months
```

Remember to convert the annual interest rate into a monthly rate.

**Output:**
- Loan amount
- Monthly interest rate
- Number of months
- Monthly EMI

---

# General Requirements

For every assignment:

### HTML

Use an HTML form with appropriate:

- `<input>`
- `name`
- `placeholder`
- `<button type="submit">`

Example:

```html
<form action="convert" method="get">

    <input
        type="number"
        name="amount"
        placeholder="Enter amount"
    >

    <button type="submit">
        Submit
    </button>

</form>
```

### Servlet

Use:

```java
@WebServlet("/your-url")
```

and retrieve form values using:

```java
String value = request.getParameter("amount");
```

Convert the value when necessary:

```java
int number = Integer.parseInt(value);
```

or:

```java
double number = Double.parseDouble(value);
```

Display the result using:

```java
response.setContentType("text/html");

response.getWriter().println(
    "<h1>Result: " + result + "</h1>"
);
```

---

# Important Instructions

- Do **not** use a database.
- Do **not** use JDBC.
- Do **not** use JSP.
- Use **HTML + Servlet only**.
- Use `GET` requests for these assignments unless instructed otherwise.
- Use meaningful variable names.
- Validate user input where appropriate.
- Test each application using Apache Tomcat.
- Each project should have a clear and readable HTML form.
- Display the final result clearly in the browser.

---

# Submission

For each project, submit:

```text
Project Name
│
├── HTML file
└── Servlet Java file
```

Students should be able to explain:

1. How the HTML form sends data.
2. What the `name` attribute does.
3. How `request.getParameter()` receives the value.
4. Why the returned value is a `String`.
5. Why type conversion is required.
6. How the Servlet processes the data.
7. How the result is sent back to the browser.
