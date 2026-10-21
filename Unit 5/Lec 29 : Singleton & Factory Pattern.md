# Lec 29: Singleton, Factory, and Dependency Injection Patterns

**Course:** Advanced Java\
**Lecture duration:** 50 minutes\
**Topics:** Singleton Pattern (20 min), Factory Pattern (20 min),
Dependency Injection Pattern (10 min)

------------------------------------------------------------------------

## Learning Outcomes

By the end of this lecture, you should be able to:

-   Explain what a design pattern is and why these three patterns are
    useful.
-   Implement a basic Singleton in Java and understand its limitations.
-   Use a Factory to create objects without scattering constructor calls
    throughout an application.
-   Explain Dependency Injection (DI) and how it reduces tight coupling.
-   Recognize where these patterns appear in Java web applications and
    frameworks.

> **Big idea:** These patterns help us control **how objects are created
> and supplied** in an application.

------------------------------------------------------------------------

# 1. Quick Revision: What Is a Design Pattern?

A **design pattern** is a reusable way to solve a commonly occurring
software-design problem. It is not a ready-made library or a fixed piece
of code; it is a general approach that we adapt to our application.

Imagine a college portal. Many parts of the application may need a
database service, different notification types, or a particular
implementation of a service. These patterns help organize those
decisions.

  -----------------------------------------------------------------------
  Pattern                             Main question it answers
  ----------------------------------- -----------------------------------
  Singleton                           How can a class provide one shared
                                      instance?

  Factory                             How can object creation be
                                      centralized?

  Dependency Injection                How can an object receive the
                                      objects it depends on?
  -----------------------------------------------------------------------

These patterns solve different problems and can be used together.

------------------------------------------------------------------------

# 2. Singleton Pattern (20 minutes)

## 2.1 What is the Singleton Pattern?

The **Singleton Pattern** is a creational design pattern that aims to
ensure a class has **one shared instance** and provides a way to access
it.

Think of a college portal's application-wide configuration: several
components may need to read the same configuration object. Instead of
independently creating many copies, an application can expose one shared
instance.

### Basic structure

``` text
                Singleton
                    |
          ---------------------
          |                   |
   private constructor   static instance
                              |
                       getInstance()
                              |
                    returns same object
```

## 2.2 Basic Java Example

``` java
class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    // Prevent normal construction from outside this class
    private AppConfig() {
        System.out.println("AppConfig created");
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public void showMessage() {
        System.out.println("Shared configuration object");
    }
}

public class Main {
    public static void main(String[] args) {
        AppConfig first = AppConfig.getInstance();
        AppConfig second = AppConfig.getInstance();

        first.showMessage();

        System.out.println(first == second); // true
    }
}
```

### Understand the important lines

-   `private AppConfig()` prevents other classes from calling
    `new AppConfig()`.
-   `private static final AppConfig INSTANCE` stores the shared
    instance.
-   `static getInstance()` allows callers to obtain that instance
    without creating an `AppConfig` object first.
-   `first == second` checks whether both references point to the same
    object.

The output includes `true`, because both variables refer to the same
instance.

## 2.3 Why use Singleton?

Possible use cases include:

-   A shared, immutable application configuration object.
-   A carefully designed in-memory registry.
-   A single coordinator object within a defined application scope.

**Important:** Singleton does not automatically mean "one object across
the entire computer." Its scope depends on the class loader, process,
application, and implementation. In a web application, a Java Singleton
is generally not automatically shared across multiple application
servers or JVM processes.

## 2.4 Singleton and Thread Safety

The example above uses **eager initialization**: the instance is created
when the class is initialized. Java class initialization is thread-safe.

A common lazy-initialization example is:

``` java
class LazyConfig {
    private static LazyConfig instance;

    private LazyConfig() {}

    public static synchronized LazyConfig getInstance() {
        if (instance == null) {
            instance = new LazyConfig();
        }
        return instance;
    }
}
```

The `synchronized` keyword ensures that only one thread at a time
executes the method, preventing two threads from creating separate
instances through this method. It may add synchronization overhead on
each call.

For ordinary application code, prefer a simple, clear implementation.
For lazy initialization, the **initialization-on-demand holder** idiom
is also a useful option:

``` java
class AppSettings {
    private AppSettings() {}

    private static class Holder {
        private static final AppSettings INSTANCE = new AppSettings();
    }

    public static AppSettings getInstance() {
        return Holder.INSTANCE;
    }
}
```

## 2.5 Singleton: Advantages and Disadvantages

**Advantages** - Provides a single shared instance within the intended
scope. - Centralizes access to that instance. - Can avoid unnecessary
duplicate objects for suitable use cases.

**Disadvantages** - Hidden global state can make code harder to
understand. - Unit tests may become harder because callers cannot easily
substitute a fake implementation. - A mutable Singleton can create
concurrency bugs. - It can be misused as a global container for
unrelated responsibilities. - Reflection, serialization, cloning, or
multiple class loaders may complicate strict "exactly one instance"
guarantees unless explicitly handled.

### When should you avoid it?

Avoid using Singleton merely because a class is used in many places. If
an object is lightweight, has no shared state, or should vary per
user/request, creating or injecting separate instances may be better.

For example, **do not store the currently logged-in user in a global
Singleton** in a multi-user web application. One user's data could leak
into another user's request.

------------------------------------------------------------------------

# 3. Factory Pattern (20 minutes)

## 3.1 What is the Factory Pattern?

A **Factory Pattern** centralizes object creation. Instead of asking
calling code to construct a particular implementation directly, the
caller asks a factory for an object.

Imagine a portal that sends notifications through email or SMS. The
calling code should not need to know all the construction details for
each notification type.

### Without a factory

``` java
EmailNotification email = new EmailNotification();
email.send("Welcome!");
```

If many places create notifications, every place may need to change when
the implementation changes.

### With a factory

``` text
Caller
  |
  v
NotificationFactory
  |
  +---- "email" ---> EmailNotification
  |
  +---- "sms" -----> SmsNotification
```

The factory centralizes the creation decision.

## 3.2 Example: Notification Factory

First, define a common interface:

``` java
interface Notification {
    void send(String message);
}
```

Create implementations:

``` java
class EmailNotification implements Notification {
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

class SmsNotification implements Notification {
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}
```

Create the factory:

``` java
class NotificationFactory {
    public static Notification create(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        switch (type.toLowerCase()) {
            case "email":
                return new EmailNotification();
            case "sms":
                return new SmsNotification();
            default:
                throw new IllegalArgumentException(
                    "Unsupported notification type: " + type
                );
        }
    }
}
```

Use it:

``` java
public class Main {
    public static void main(String[] args) {
        Notification notification =
            NotificationFactory.create("email");

        notification.send("Welcome to the student portal!");
    }
}
```

### What did we achieve?

The caller uses the `Notification` interface and does not need to know
the concrete class's construction details. The factory decides which
implementation to return.

## 3.3 Why return an interface?

Notice that the factory returns `Notification`, not `EmailNotification`:

``` java
Notification notification = NotificationFactory.create("email");
```

This is **programming to an interface**. The caller depends on the
behavior (`send`) rather than one specific implementation. It becomes
easier to add another implementation such as `PushNotification`.

## 3.4 Where can factories be useful?

-   Selecting a payment method implementation.
-   Creating notification services.
-   Choosing a parser based on file type.
-   Creating database-related helper objects.
-   Creating different report generators.

Do not use a factory for every `new` expression. It is most useful when
object creation involves a decision, complexity, configuration, or a
need to hide implementation choices.

## 3.5 Factory Pattern: Advantages and Disadvantages

**Advantages** - Keeps creation logic in one place. - Reduces direct
dependence on concrete classes. - Makes it easier to introduce
additional implementations. - Can hide complicated setup from callers.

**Disadvantages** - Adds another class or layer. - A large factory with
many conditions can become difficult to maintain. - Adding a new type
may still require editing the factory; more flexible designs may use
registration or dependency injection.

------------------------------------------------------------------------

# 4. Dependency Injection Pattern (10 minutes)

## 4.1 What is Dependency Injection?

A **dependency** is an object or service that another class needs to do
its work.

**Dependency Injection (DI)** means that an object receives its
dependencies from outside instead of creating them itself.

Think of a phone: the phone needs a charger to charge, but the phone
does not manufacture the charger internally. The charger is supplied to
it.

In Java, a service may need a repository to retrieve student records.

## 4.2 Tight coupling: creating the dependency internally

``` java
class StudentRepository {
    public String findStudentName() {
        return "Riya";
    }
}

class StudentService {
    private final StudentRepository repository =
        new StudentRepository();

    public String getStudentName() {
        return repository.findStudentName();
    }
}
```

`StudentService` directly constructs `StudentRepository`. Replacing the
repository with a test double or another implementation is harder.

## 4.3 Constructor Injection: a simple DI example

``` java
interface StudentRepository {
    String findStudentName();
}

class DatabaseStudentRepository implements StudentRepository {
    public String findStudentName() {
        return "Riya";
    }
}

class StudentService {
    private final StudentRepository repository;

    // Dependency is supplied from outside
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public String getStudentName() {
        return repository.findStudentName();
    }
}

public class Main {
    public static void main(String[] args) {
        StudentRepository repository =
            new DatabaseStudentRepository();

        StudentService service = new StudentService(repository);

        System.out.println(service.getStudentName());
    }
}
```

### Flow

``` text
Main (composition root)
   |
   | creates and supplies
   v
StudentService ----uses----> StudentRepository interface
                                  ^
                                  |
                      DatabaseStudentRepository
```

`StudentService` knows the interface, not the concrete database class.
Another implementation, such as `InMemoryStudentRepository`, can be
supplied for testing.

## 4.4 Types of Dependency Injection

  -----------------------------------------------------------------------
  Type                    How it works            Typical use
  ----------------------- ----------------------- -----------------------
  Constructor injection   Dependency passed to    Usually preferred for
                          constructor             required dependencies

  Setter injection        Dependency supplied     Optional or changeable
                          through a setter method dependencies

  Field injection         Framework injects       Seen in some
                          directly into a field   frameworks, but can
                                                  make testing and object
                                                  construction less
                                                  explicit
  -----------------------------------------------------------------------

### Setter injection example

``` java
class ReportService {
    private StudentRepository repository;

    public void setRepository(StudentRepository repository) {
        this.repository = repository;
    }
}
```

For required dependencies, constructor injection is usually easier to
reason about because the object cannot be constructed without receiving
them.

## 4.5 Dependency Injection in Jakarta EE / Spring

In Jakarta EE, a container can manage components and inject
dependencies. For example:

``` java
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class StudentService {
    @Inject
    StudentRepository repository;
}
```

This is a simplified illustration. `StudentRepository` must be a bean
that the CDI container can discover and resolve; the application must
run in a compatible Jakarta EE environment with CDI enabled. Plain Java
execution alone will not automatically perform `@Inject`.

Spring provides similar dependency-injection capabilities using its
application context and annotations such as `@Component` and
`@Autowired` (constructor injection is commonly preferred).

### Benefits of DI

-   Reduces tight coupling.
-   Makes unit testing easier.
-   Makes it easier to replace implementations.
-   Makes dependencies more visible.
-   Lets a container manage object creation and lifecycle where
    appropriate.

**DI is not the same as Singleton.** DI describes how dependencies are
supplied. A container may choose a scope such as application-wide,
request-scoped, or dependent/transient behavior. Injection does not
automatically mean every object is a Singleton.

------------------------------------------------------------------------

# 5. How the Three Patterns Relate

  -----------------------------------------------------------------------
  Pattern                 Responsibility          Example
  ----------------------- ----------------------- -----------------------
  Singleton               Controls access to a    Shared immutable
                          shared instance         configuration

  Factory                 Decides which object to Email or SMS
                          create                  notification

  Dependency Injection    Supplies an object's    Inject repository into
                          dependencies            service
  -----------------------------------------------------------------------

They can work together, but they are not interchangeable.

A factory may create a service; dependency injection may supply the
service's repository; a container may manage the service's scope. A
Singleton is only appropriate if a single shared instance is genuinely
the right design.

``` text
Application setup / container
          |
          +---- obtains or creates service
          |
          +---- supplies repository dependency
                         |
                         v
                  StudentService
                         |
                         v
                 StudentRepository
```

------------------------------------------------------------------------

# 6. Common Student Mistakes

1.  **Making every class a Singleton:** one shared instance is not
    always desirable.
2.  **Confusing `static` with Singleton:** a static method belongs to a
    class; a Singleton is an object-instance design.
3.  **Returning concrete classes from factories unnecessarily:**
    returning an interface often keeps callers flexible.
4.  **Thinking DI requires a framework:** constructor injection can be
    implemented with ordinary Java.
5.  **Thinking `@Inject` works in every Java program:** it needs a
    compatible dependency-injection container.
6.  **Storing user-specific mutable data in a global Singleton:** this
    can cause cross-request data leaks and race conditions.
7.  **Using factories for trivial object creation:** extra abstraction
    should solve a real problem.

------------------------------------------------------------------------

# 7. Quick Revision

-   **Singleton:** one shared instance within a defined scope.
-   **Factory:** centralizes the decision about which object to create.
-   **Dependency Injection:** supplies required objects from outside the
    class.
-   **Constructor injection:** a clear default for required
    dependencies.
-   **Interface-based design:** allows different implementations to be
    substituted.
-   **Container-managed scope:** determines how long and how broadly a
    managed object is shared.

## Interview Questions

1.  What is the Singleton Pattern, and why is its constructor often
    private?
2.  What is the difference between a static method and a Singleton
    object?
3.  Why is thread safety important when implementing lazy Singleton
    initialization?
4.  What problem does a Factory Pattern solve?
5.  Why might a factory return an interface instead of a concrete class?
6.  What is a dependency in Java?
7.  Explain Dependency Injection with a real-world analogy.
8.  Compare constructor injection and field injection.
9.  Does `@Inject` work in a plain Java `main()` method without a
    container?
10. Can Singleton, Factory, and Dependency Injection be used together?

## Practical Assignment

Build a small **Student Notification System**.

### Requirements

1.  Create a `Notification` interface with a `send(String message)`
    method.
2.  Implement `EmailNotification` and `SmsNotification`.
3.  Create a `NotificationFactory` that selects the implementation based
    on a type.
4.  Create a `StudentService` that receives a `Notification` through its
    constructor.
5.  Test both notification types.
6.  Add a short note explaining why the service depends on the interface
    rather than directly creating an email or SMS object.

### Extension tasks

-   Add a third implementation such as `ConsoleNotification`.
-   Handle an unsupported notification type cleanly.
-   Write a simple test using a fake `Notification` implementation.
-   Explain whether a Singleton is needed anywhere in your solution and
    justify your answer.

------------------------------------------------------------------------

## Final Takeaway

**Singleton controls instance sharing. Factory centralizes object
creation. Dependency Injection supplies dependencies from outside.** Use
each pattern when it solves a real design problem---not simply because
the pattern exists.
