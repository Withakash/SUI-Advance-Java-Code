# Java Functional Interfaces (Java 8)
## Predicate • Function • Consumer • Supplier

> **Topic:** Java 8 Functional Interfaces
> **Level:** Beginner → Intermediate
> **Prerequisites:** Java Basics, Lambda Expressions
> **Used In:** Stream API, Collections, Spring Boot, REST APIs, Multithreading

---

# Table of Contents

1. Why Functional Interfaces?
2. What is a Functional Interface?
3. Predicate<T>
4. Function<T, R>
5. Consumer<T>
6. Supplier<T>
7. Comparison Table
8. Complete Real-World Example
9. Industry Use Cases
10. Interview Questions

---

# Why Functional Interfaces?

Before Java 8, whenever we wanted to perform a small operation, we generally created:

- Separate class
- Anonymous Inner Class
- Multiple methods

Example (Before Java 8)

```java
class EvenChecker {

    public boolean check(int n){
        return n % 2 == 0;
    }

}
```

For every small operation we needed another class.

Java 8 introduced:

- Lambda Expressions
- Functional Interfaces

Now the same logic becomes

```java
Predicate<Integer> isEven = n -> n % 2 == 0;
```

Less code.

More readable.

Easy to reuse.

---

# What is a Functional Interface?

A Functional Interface contains **exactly one abstract method**.

It may contain

- default methods
- static methods
- private methods (Java 9+)

Example

```java
@FunctionalInterface
interface Calculator {

    int calculate(int a, int b);

}
```

Lambda

```java
Calculator add = (a, b) -> a + b;

System.out.println(add.calculate(10,20));
```

Output

```
30
```

---

# Four Most Important Functional Interfaces

| Interface | Input | Output | Purpose |
|-----------|-------|--------|----------|
| Predicate<T> | One | boolean | Test Condition |
| Function<T,R> | One | Any Type | Convert Object |
| Consumer<T> | One | Nothing | Perform Action |
| Supplier<T> | None | One Value | Supply Data |

---

# 1. Predicate<T>

## Meaning

Predicate checks whether something satisfies a condition.

Think

```
Should I keep this object?
```

Returns only

```
true
or
false
```

---

## Method

```java
boolean test(T t)
```

---

## Syntax

```java
Predicate<Integer> isEven =
        n -> n % 2 == 0;
```

---

## Example 1

```java
Predicate<Integer> isEven =
        n -> n % 2 == 0;

System.out.println(isEven.test(10));
System.out.println(isEven.test(7));
```

Output

```
true
false
```

---

# Where do we use Predicate?

## 1. Stream filter()

```java
List<Integer> list =
Arrays.asList(2,5,6,9,10);

list.stream()
    .filter(n -> n % 2 == 0)
    .forEach(System.out::println);
```

Output

```
2
6
10
```

---

## 2. Employee Filtering

```java
Predicate<Employee> highSalary =
        e -> e.getSalary() > 50000;

employees.stream()
         .filter(highSalary)
         .forEach(System.out::println);
```

---

## 3. Validation

```java
Predicate<String> validEmail =
        email -> email.contains("@");

System.out.println(
        validEmail.test("abc@gmail.com")
);
```

---

## 4. removeIf()

```java
list.removeIf(n -> n < 10);
```

Java internally expects Predicate.

---

## Real Life Example

Imagine HR says

```
Show employees earning above ₹50,000.
```

Predicate checks

```
Employee

↓

Salary > 50000 ?

↓

true

↓

Keep Employee

false

↓

Ignore Employee
```

---

# Common Predicate Methods

## test()

```java
Predicate<Integer> even =
        n -> n % 2 == 0;

System.out.println(even.test(8));
```

---

## and()

```java
Predicate<Integer> even =
        n -> n % 2 == 0;

Predicate<Integer> greaterThan10 =
        n -> n > 10;

Predicate<Integer> result =
        even.and(greaterThan10);

System.out.println(result.test(12));
```

Output

```
true
```

---

## or()

```java
Predicate<Integer> even =
        n -> n % 2 == 0;

Predicate<Integer> greaterThan10 =
        n -> n > 10;

Predicate<Integer> result =
        even.or(greaterThan10);

System.out.println(result.test(9));
```

Output

```
false
```

---

## negate()

```java
Predicate<Integer> even =
        n -> n % 2 == 0;

Predicate<Integer> odd =
        even.negate();

System.out.println(odd.test(7));
```

Output

```
true
```

---

# 2. Function<T,R>

## Meaning

Function converts one object into another.

Think

```
Input

↓

Transformation

↓

Output
```

---

## Method

```java
R apply(T t)
```

---

## Syntax

```java
Function<String,Integer> length =
        str -> str.length();
```

---

## Example

```java
Function<String,Integer> length =
        str -> str.length();

System.out.println(length.apply("Java"));
```

Output

```
4
```

---

# Where do we use Function?

## 1. map()

```java
List<String> names =
Arrays.asList(
        "Akash",
        "Rahul",
        "Aman"
);

names.stream()
     .map(String::length)
     .forEach(System.out::println);
```

Output

```
5
5
4
```

---

## 2. Employee → Name

```java
Function<Employee,String> getName =
        Employee::getName;

System.out.println(getName.apply(emp));
```

---

## 3. Salary → Tax

```java
Function<Double,Double> tax =
        salary -> salary * 0.10;

System.out.println(
        tax.apply(50000.0)
);
```

Output

```
5000.0
```

---

## 4. Object Conversion

```java
Function<UserDTO,User> convert =
        dto -> new User(
                dto.getName(),
                dto.getEmail()
        );
```

---

# Function Methods

## apply()

```java
Function<String,Integer> length =
        String::length;

System.out.println(
        length.apply("Programming")
);
```

---

## andThen()

```java
Function<Integer,Integer> square =
        x -> x * x;

Function<Integer,Integer> doubleValue =
        x -> x * 2;

System.out.println(
        square.andThen(doubleValue)
              .apply(5)
);
```

Output

```
50
```

---

## compose()

```java
Function<Integer,Integer> square =
        x -> x * x;

Function<Integer,Integer> addTwo =
        x -> x + 2;

System.out.println(
        square.compose(addTwo)
              .apply(5)
);
```

Output

```
49
```

---

# Real Life Example

```
Employee

↓

Salary

↓

Tax

↓

Bonus

↓

Display
```

Every transformation is a Function.

---

# 3. Consumer<T>

## Meaning

Consumer accepts an object and performs an action.

Returns nothing.

Think

```
Take it

↓

Do something
```

---

## Method

```java
void accept(T t)
```

---

## Example

```java
Consumer<String> print =
        System.out::println;

print.accept("Hello Java");
```

Output

```
Hello Java
```

---

# Where do we use Consumer?

## 1. Printing

```java
Consumer<Integer> printer =
        System.out::println;

printer.accept(100);
```

---

## 2. Stream forEach()

```java
list.stream()
    .forEach(System.out::println);
```

---

## 3. Logging

```java
Consumer<String> logger =
        msg -> System.out.println(
                "LOG : " + msg
        );

logger.accept("Application Started");
```

---

## 4. Send Email

```java
Consumer<Employee> sendEmail =
        e -> System.out.println(
                "Email sent to " +
                e.getName()
        );

sendEmail.accept(emp);
```

---

# Consumer Method

## andThen()

```java
Consumer<String> upper =
        s -> System.out.println(
                s.toUpperCase()
        );

Consumer<String> length =
        s -> System.out.println(
                s.length()
        );

upper.andThen(length)
     .accept("java");
```

Output

```
JAVA
4
```

---

# Real Life Example

Customer buys product

↓

Generate Invoice

↓

Update Database

↓

Send Email

↓

Print Receipt

Consumer performs actions.

---

# 4. Supplier<T>

## Meaning

Supplier provides data.

No input required.

Returns one value.

Think

```
Give me something.
```

---

## Method

```java
T get()
```

---

## Example

```java
Supplier<Double> random =
        Math::random;

System.out.println(random.get());
```

---

# Where do we use Supplier?

## 1. OTP Generator

```java
Supplier<Integer> otp =
        () -> (int)(
                Math.random()*9000
        ) + 1000;

System.out.println(
        otp.get()
);
```

---

## 2. Current Time

```java
Supplier<LocalDateTime> time =
        LocalDateTime::now;

System.out.println(
        time.get()
);
```

---

## 3. Object Creation

```java
Supplier<Employee> supplier =
        Employee::new;

Employee emp =
        supplier.get();
```

---

## 4. Lazy Initialization

```java
Supplier<List<String>> listSupplier =
        ArrayList::new;

List<String> list =
        listSupplier.get();
```

---

# Real Life Example

ATM Machine

```
No Input

↓

Supplier

↓

Cash
```

Supplier always provides data.

---

# Complete Stream Example

Employee Class

```java
class Employee {

    private String name;
    private double salary;

    public Employee(String name,double salary){

        this.name=name;
        this.salary=salary;

    }

    public String getName(){

        return name;

    }

    public double getSalary(){

        return salary;

    }

}
```

---

Create Data

```java
List<Employee> employees = List.of(

        new Employee("Akash",70000),
        new Employee("Rahul",30000),
        new Employee("Aman",50000)

);
```

---

Use All Functional Interfaces

```java
Predicate<Employee> highSalary =
        e -> e.getSalary() >= 50000;

Function<Employee,String> getName =
        Employee::getName;

Consumer<String> print =
        System.out::println;

employees.stream()

         .filter(highSalary)

         .map(getName)

         .forEach(print);
```

Output

```
Akash
Aman
```

---

# Execution Flow

```
Employee List

        │

        ▼

Predicate

(Filter)

        │

        ▼

Function

(Convert)

        │

        ▼

Consumer

(Print)
```

---

# Industry Usage

## Spring Boot

### Predicate

```java
request -> request.isValid()
```

Validation before saving.

---

### Function

```java
Entity

↓

DTO
```

Mapping objects.

---

### Consumer

```java
logger.info()
```

Logging.

---

### Supplier

```java
UUID.randomUUID()
```

Generate Token.

---

# Comparison

| Feature | Predicate | Function | Consumer | Supplier |
|----------|-----------|----------|-----------|-----------|
| Input | Yes | Yes | Yes | No |
| Output | boolean | Any Type | void | One Value |
| Main Method | test() | apply() | accept() | get() |
| Stream API | filter() | map() | forEach() | generate() |
| Used For | Condition | Transformation | Action | Creation |

---

# Memory Trick

```
Predicate

Question

↓

TRUE/FALSE
```

```
Function

Input

↓

Output
```

```
Consumer

Input

↓

Action
```

```
Supplier

Nothing

↓

Returns Something
```

---

# Interview Questions

## Q1. Why Functional Interfaces?

To pass behavior as data using Lambda Expressions and Method References, reducing boilerplate code.

---

## Q2. Which functional interface returns boolean?

```
Predicate
```

---

## Q3. Which interface is used with filter()?

```
Predicate
```

---

## Q4. Which interface is used with map()?

```
Function
```

---

## Q5. Which interface is used with forEach()?

```
Consumer
```

---

## Q6. Which interface has no input?

```
Supplier
```

---

# Summary

| Functional Interface | Think As | Used In |
|----------------------|----------|---------|
| Predicate | Condition Checker | filter(), removeIf(), validation |
| Function | Converter | map(), DTO ↔ Entity |
| Consumer | Action Performer | forEach(), logging, notifications |
| Supplier | Value Generator | OTP, UUID, object creation, lazy loading |

---

> **Key Takeaway:**  
> **Predicate → Check**  
> **Function → Convert**  
> **Consumer → Use**  
> **Supplier → Provide**

These four functional interfaces form the foundation of Java's Functional Programming model and are extensively used in the **Stream API, Spring Boot, Collections Framework, CompletableFuture, Optional, and Reactive Programming**.
