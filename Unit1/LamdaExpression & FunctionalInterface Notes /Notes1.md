# Lambda Expressions in Java 8 - Complete Notes
## Advanced Java Notes

> **Topic:** Lambda Expressions  
> **Java Version:** Java 8+  
> **Prerequisite:** Interfaces, Anonymous Classes, Functional Interfaces

---

# Table of Contents

1. Introduction
2. Why Lambda Expressions?
3. What is a Lambda Expression?
4. Real World Analogy
5. Anonymous Class vs Lambda
6. Syntax
7. Step-by-Step Method to Lambda Conversion
8. Rules to Simplify Lambda Expressions
9. Functional Interface
10. Lambda Examples
11. Built-in Functional Interfaces
12. Real Industry Usage
13. Advantages
14. Limitations
15. Interview Questions
16. Summary

---

# 1. Introduction

Java 8 introduced one of the biggest language improvements called **Lambda Expressions**.

A Lambda Expression allows us to write **anonymous functions**.

Instead of creating a complete class just to implement one method, we can directly write the implementation.

This makes code

- Smaller
- Cleaner
- More Readable
- Easier to Maintain

---

# 2. Why Lambda Expressions?

Before Java 8, whenever we wanted to pass some behavior (logic) to another method, we had to create a class.

For example,

Suppose we want a Robot to perform an action.

Without Lambda we need

- Interface
- Class
- Object

just for one line of code.

Example:

```java
interface Robot{
    void work();
}

class DanceRobot implements Robot{

    @Override
    public void work(){
        System.out.println("Robot is Dancing");
    }

}

public class Main{

    public static void main(String[] args){

        Robot r = new DanceRobot();
        r.work();

    }

}
```

Output

```
Robot is Dancing
```

This is too much code for a simple task.

---

# 3. Anonymous Class

Java introduced Anonymous Classes to reduce boilerplate.

```java
interface Robot{
    void work();
}

public class Main{

    public static void main(String[] args){

        Robot r = new Robot(){

            @Override
            public void work(){
                System.out.println("Robot is Dancing");
            }

        };

        r.work();

    }

}
```

Although better than creating a separate class, it is still verbose.

---

# 4. Lambda Expression

Java 8 provides Lambda Expressions.

```java
interface Robot{
    void work();
}

public class Main{

    public static void main(String[] args){

        Robot r = () -> {
            System.out.println("Robot is Dancing");
        };

        r.work();

    }

}
```

Output

```
Robot is Dancing
```

Much shorter and cleaner.

---

# 5. What is a Lambda Expression?

A Lambda Expression is an **anonymous function**.

It has

- No Name
- No Return Type
- No Access Modifier

It only contains

- Parameters
- Arrow Operator
- Logic

---

## Definition

> Lambda Expression is a concise way to represent an implementation of a Functional Interface.

---

# 6. Syntax

```java
(parameters) -> { body }
```

Example

```java
(a,b) -> a+b
```

Another Example

```java
(name) -> System.out.println(name)
```

---

# 7. How Lambda Actually Works

When we write

```java
Calculator add = (a,b) -> a+b;
```

Java internally creates an object similar to

```java
Calculator add = new Calculator(){

    @Override
    public int calculate(int a,int b){
        return a+b;
    }

};
```

The compiler generates the implementation automatically.

---

# 8. Step-by-Step: Converting a Method into a Lambda

Suppose we have a normal Java method.

## Step 1

Original Method

```java
private void sayHello(){

    System.out.println("Hello");

}
```

---

## Step 2

Remove Access Modifier

```java
void sayHello(){

    System.out.println("Hello");

}
```

---

## Step 3

Remove Return Type

```java
sayHello(){

    System.out.println("Hello");

}
```

---

## Step 4

Remove Method Name

```java
(){

    System.out.println("Hello");

}
```

---

## Step 5

Replace with Arrow Operator

```java
() -> {

    System.out.println("Hello");

}
```

---

## Final Lambda

```java
() -> System.out.println("Hello");
```

---

# Example with Parameters

Original Method

```java
private void add(int a,int b){

    System.out.println(a+b);

}
```

Step-by-Step

Remove Access Modifier

```java
void add(int a,int b)
```

Remove Return Type

```java
add(int a,int b)
```

Remove Method Name

```java
(int a,int b)
```

Replace with Arrow

```java
(int a,int b)->{

    System.out.println(a+b);

}
```

---

# Final Lambda

```java
(int a,int b) -> System.out.println(a+b);
```

---

# 9. Rules to Simplify Lambda Expressions

Java provides several shortcuts.

---

## Rule 1

### Remove Curly Braces

If there is only one statement.

Before

```java
() -> {

    System.out.println("Hello");

}
```

After

```java
() -> System.out.println("Hello");
```

---

## Rule 2

### Remove Parameter Types

Compiler automatically identifies parameter types.

Before

```java
(String name) -> System.out.println(name)
```

After

```java
(name) -> System.out.println(name)
```

---

## Rule 3

### Remove Return Keyword

If there is only one expression.

Before

```java
(a,b)->{

    return a+b;

}
```

After

```java
(a,b)->a+b
```

---

## Rule 4

### Remove Small Parentheses

Only when there is exactly one parameter.

Before

```java
(name)->System.out.println(name);
```

After

```java
name->System.out.println(name);
```

---

## Complete Refactoring Example

Initial Version

```java
(String s)->{

    return s.length();

}
```

Step 1

```java
(s)->{

    return s.length();

}
```

Step 2

```java
(s)->s.length();
```

Step 3

```java
s->s.length();
```

Final Version

```java
s -> s.length()
```

---

# 10. Functional Interface

Lambda Expressions work only with Functional Interfaces.

---

## Definition

A Functional Interface contains exactly **one abstract method**.

Example

```java
@FunctionalInterface
interface Calculator{

    int calculate(int a,int b);

}
```

Valid ✔

---

Invalid Example

```java
interface Calculator{

    int add(int a,int b);

    int multiply(int a,int b);

}
```

This is NOT a Functional Interface.

---

## @FunctionalInterface Annotation

```java
@FunctionalInterface
interface Printer{

    void print(String message);

}
```

This annotation tells the compiler

"Do not allow more than one abstract method."

---

# 11. Lambda Examples

---

## Example 1

Addition

```java
@FunctionalInterface
interface Calculator{

    int calculate(int a,int b);

}

public class Main{

    public static void main(String[] args){

        Calculator add=(a,b)->a+b;

        System.out.println(add.calculate(10,20));

    }

}
```

Output

```
30
```

---

## Example 2

Multiplication

```java
Calculator multiply=(a,b)->a*b;

System.out.println(multiply.calculate(5,6));
```

Output

```
30
```

---

## Example 3

Printing

```java
@FunctionalInterface
interface Printer{

    void print(String name);

}

public class Main{

    public static void main(String[] args){

        Printer p=name->System.out.println("Hello "+name);

        p.print("Akash");

    }

}
```

Output

```
Hello Akash
```

---

## Example 4

No Parameters

```java
interface Message{

    void show();

}
```

Lambda

```java
Message m=()->System.out.println("Welcome");

m.show();
```

Output

```
Welcome
```

---

## Example 5

Square

```java
interface Square{

    int square(int x);

}
```

Lambda

```java
Square s=x->x*x;

System.out.println(s.square(5));
```

Output

```
25
```

---

## Example 6

Multiple Statements

```java
Calculator add=(a,b)->{

    System.out.println("Adding Numbers");

    return a+b;

};
```

---

# 12. Built-in Functional Interfaces

Java provides ready-made Functional Interfaces.

Package

```java
java.util.function
```

| Interface | Method | Purpose |
|------------|---------|----------|
| Predicate<T> | test() | Condition Checking |
| Function<T,R> | apply() | Transformation |
| Consumer<T> | accept() | Consumes Data |
| Supplier<T> | get() | Supplies Data |
| UnaryOperator<T> | apply() | One Input, Same Output |
| BinaryOperator<T> | apply() | Two Inputs, Same Output |

---

## Predicate Example

```java
Predicate<Integer> even=x->x%2==0;

System.out.println(even.test(10));
```

Output

```
true
```

---

## Function Example

```java
Function<String,Integer> length=s->s.length();

System.out.println(length.apply("Java"));
```

Output

```
4
```

---

## Consumer Example

```java
Consumer<String> c=s->System.out.println(s);

c.accept("Hello");
```

---

## Supplier Example

```java
Supplier<Double> s=()->Math.random();

System.out.println(s.get());
```

---

# 13. Real Industry Usage

---

## Sorting Collections

Before Java 8

```java
Collections.sort(list,new Comparator<Integer>(){

    @Override
    public int compare(Integer a,Integer b){

        return a-b;

    }

});
```

Java 8

```java
Collections.sort(list,(a,b)->a-b);
```

---

## Creating Threads

Before

```java
Thread t=new Thread(new Runnable(){

    @Override
    public void run(){

        System.out.println("Running");

    }

});
```

After

```java
Thread t=new Thread(()->System.out.println("Running"));
```

---

## Streams API

```java
List<Integer> list=List.of(2,5,8,9,10);

list.stream()
    .filter(x->x%2==0)
    .forEach(System.out::println);
```

Output

```
2
8
10
```

---

## forEach()

```java
List<String> names=List.of("A","B","C");

names.forEach(name->System.out.println(name));
```

---

## Event Handling

```java
button.setOnAction(e->System.out.println("Clicked"));
```

---

## Spring Boot

```java
users.forEach(user->System.out.println(user.getName()));
```

---

# 14. Benefits of Lambda Expressions

- Less Boilerplate Code
- Cleaner and Readable Code
- Supports Functional Programming
- Smaller Source Code
- Easier Maintenance
- Better Performance with Streams
- Supports Parallel Processing
- Works well with Collections
- Eliminates unnecessary anonymous classes

---

# 15. Limitations

- Works only with Functional Interfaces.
- Not suitable for very large logic blocks.
- Overusing lambdas can reduce readability.
- Cannot define constructors.
- Cannot have instance variables.

---

# 16. Anonymous Class vs Lambda

| Anonymous Class | Lambda |
|-----------------|---------|
| More Code | Less Code |
| Creates Separate Class | No Separate Class |
| Verbose | Concise |
| Slower to Write | Faster to Write |
| Introduced before Java 8 | Introduced in Java 8 |

---

# 17. Interview Questions

### Basic

- What is a Lambda Expression?
- Why was Lambda introduced?
- What is a Functional Interface?
- Can Lambda work with multiple abstract methods?
- What is the use of @FunctionalInterface?

### Intermediate

- Difference between Lambda and Anonymous Class?
- Explain the arrow operator.
- Why does Lambda require Functional Interfaces?
- Name four built-in Functional Interfaces.
- Explain Predicate, Function, Consumer, and Supplier.

### Advanced

- Can Lambda access local variables?
- What are effectively final variables?
- Where are Lambdas used in Streams?
- Explain Method References.
- Explain Functional Programming in Java.

---

# 18. Summary

- Lambda Expressions were introduced in Java 8.
- A Lambda Expression is an anonymous function.
- Lambdas reduce boilerplate code.
- They work only with Functional Interfaces.
- Syntax:

```java
(parameters) -> expression
```

- Four simplification rules:
  - Remove braces for single statements.
  - Remove parameter types (type inference).
  - Remove `return` for single expressions.
  - Remove parentheses for a single parameter.

- Widely used with:
  - Collections
  - Streams API
  - Multithreading
  - Event Handling
  - Spring Boot
  - Functional Programming

---

# One-Line Revision

> **Lambda Expression = Short way to implement the single abstract method of a Functional Interface using the `->` operator.**
