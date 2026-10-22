# Singleton Design Pattern

## 1. What is the Singleton Pattern?

The **Singleton Pattern** is a creational design pattern that ensures a class has only one instance and provides a common way to access that instance.

In simple words, **Singleton means creating only one object of a class and reusing it whenever required.**

## 2. Real-Life Example: College Principal

Imagine a college has one Principal. Teachers, students, and HODs all contact the same Principal instead of creating a new Principal for every department.

This is similar to the Singleton Pattern.

### Diagram

```text
          College Application
                   |
          +--------+--------+
          |        |        |
       Teacher   Student    HOD
          |        |        |
          +--------+--------+
                   |
                   v
           +---------------+
           |   Principal   |
           |  One Object   |
           +---------------+
```

All parts of the application access the same object.

## 3. Java Implementation

```java
class Principal {

    // One shared instance
    private static Principal instance = new Principal();

    // Prevent outside object creation
    private Principal() {
    }

    // Provide access to the shared instance
    public static Principal getInstance() {
        return instance;
    }
}

public class Main {
    public static void main(String[] args) {

        Principal p1 = Principal.getInstance();
        Principal p2 = Principal.getInstance();
        Principal p3 = Principal.getInstance();

        System.out.println(p1 == p2);
        System.out.println(p2 == p3);
    }
}
```

### Output

```text
true
true
```

### Explanation

- `private static Principal instance`: Stores the single shared object.
- `private Principal()`: Prevents other classes from creating objects using `new Principal()`.
- `public static Principal getInstance()`: Provides access to the existing object.
- `p1 == p2`: Returns `true` because both variables refer to the same object.

## 4. How Does It Work?

```text
       getInstance() called
                |
                v
       Is an instance available?
                |
                v
       Return the same instance
                |
                v
       p1, p2, and p3 refer
          to one object
```

In this implementation, the instance is created when the class is initialized. Every call to `getInstance()` returns that same instance.

## 5. Why Do We Use Singleton?

1. **Single shared instance:** Avoids creating multiple instances when only one is needed.
2. **Shared access:** Different parts of an application can access the same object.
3. **Centralized management:** Useful for managing shared configuration or application-wide services.
4. **Controlled creation:** The class controls how its instance is created and accessed.

## 6. Practical Software Example

A configuration manager may store application settings such as the application name, default language, or configuration values.

Instead of creating a separate configuration manager everywhere, an application can use a shared instance.

Other possible use cases include:
- Application configuration services
- Certain logging services
- Shared application-level registries

**Note:** Singleton should be used only when a single shared instance is genuinely appropriate. It is not necessary for every class.

## 7. Important Keywords

| Keyword | Purpose |
|---|---|
| `private` constructor | Prevents direct object creation from outside the class |
| `static` instance | Associates the instance with the class |
| `public static` method | Allows access without creating another object |

## 8. Singleton vs. Normal Object Creation

### Normal object creation

```java
Student s1 = new Student();
Student s2 = new Student();
```

If the constructor is accessible, these statements create two separate objects.

### Singleton access

```java
Principal p1 = Principal.getInstance();
Principal p2 = Principal.getInstance();
```

Both variables refer to the same object.

## 9. Connection with ServletContext

In Java web applications, `ServletContext` provides application-wide information and shared attributes within a web application.

This may feel similar to Singleton because both involve shared access.

However:
- **Singleton Pattern:** A design pattern for controlling a class's instances.
- **ServletContext:** An object managed by the Servlet container for a web application.

They are related in concept, but they are not the same thing.

## 10. Important Note: Thread Safety

The implementation above creates the instance during class initialization, which is safe under Java's class-initialization guarantees.

If a Singleton instead creates its instance lazily using an `if (instance == null)` check, concurrent threads may create multiple instances unless synchronization or another thread-safe technique is used.

## Quick Revision

- Singleton is a **creational design pattern**.
- It aims to provide one instance of a class.
- The constructor is commonly private.
- A static instance stores the shared object.
- A public static method provides access to it.
- Example: A shared configuration manager.

### Practice Questions

1. What is the Singleton Design Pattern?
2. Why is the constructor private?
3. What is the purpose of a static instance?
4. What does `p1 == p2` check?
5. Explain Singleton using the college Principal example.
6. How is Singleton different from `ServletContext`?
