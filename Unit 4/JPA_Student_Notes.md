# Java Persistence API (JPA) --- Student Notes

> **Subject:** Advanced Java / Java EE\
> **Topics:** Introduction to JPA, JPA Architecture, EntityManager,
> JPQL, and Entity Relationships\
> **Goal:** Understand the concepts, architecture, working, and
> real-world use of JPA.

------------------------------------------------------------------------

## Learning Objectives

After studying this chapter, students should be able to:

-   Define **JPA** and explain why it is used.
-   Differentiate **JPA**, **ORM**, **JDBC**, and a **JPA provider**.
-   Explain JPA architecture and the responsibilities of its main
    components.
-   Describe the purpose and lifecycle of an **EntityManager**.
-   Use common EntityManager operations conceptually: `persist()`,
    `find()`, `merge()`, `remove()`, and `flush()`.
-   Explain **JPQL** and distinguish it from SQL.
-   Identify and explain one-to-one, one-to-many, many-to-one, and
    many-to-many relationships.

------------------------------------------------------------------------

# 1. Introduction to JPA

## 1.1 What is JPA?

**JPA (Java Persistence API)** is a Java specification that defines a
standard way to map Java objects to relational database tables and to
manage the persistence of those objects.

Persistence means that data can remain available after the application
or program has stopped running. For example, a `Student` object created
in memory disappears when the application ends unless its data is stored
somewhere permanent, such as a database.

JPA lets developers work mainly with Java objects while a JPA provider
handles much of the object-to-table mapping and database interaction.

### Example: A Java object

``` java
Student student = new Student();
student.setId(101);
student.setName("Rahul");
student.setAge(20);
```

At this point, the object exists in application memory. Creating it with
`new` does **not**, by itself, save it to a database.

Conceptually, JPA can map the object to a row in a database table:

``` text
Java object                         Database table: student
+----------------------+            +--------------------------+
| Student              |  mapping   | id | name  | age         |
|----------------------| ---------> |----|-------|-------------|
| id   = 101            |            | 101| Rahul | 20          |
| name = "Rahul"        |            +--------------------------+
| age  = 20             |
+----------------------+
```

This mapping is an example of **Object-Relational Mapping (ORM)**.

## 1.2 What is ORM?

**ORM (Object-Relational Mapping)** is a technique that connects an
object-oriented programming model with a relational database model.

  -----------------------------------------------------------------------
  Java / Object-Oriented World        Relational Database World
  ----------------------------------- -----------------------------------
  Class                               Table

  Object                              Row / record

  Field or property                   Column

  Object relationship                 Foreign key or join table
                                      (depending on mapping)
  -----------------------------------------------------------------------

For example, a Java `Student` class may be mapped to a `student` table.
Each `Student` object represents one student's data, and its fields are
mapped to columns.

**Important:** JPA is a specification that defines standard APIs and
mapping rules. Hibernate and EclipseLink are examples of providers that
implement the specification and perform the actual ORM work.

## 1.3 Why do we need JPA if JDBC already exists?

JDBC (Java Database Connectivity) is a standard Java API for
communicating with databases. With JDBC, developers commonly write SQL
statements, create statements, execute queries, and process `ResultSet`
data.

JPA provides a higher-level object-based approach. It can reduce
repetitive mapping code and helps manage entity state and relationships.
It does not mean SQL or database knowledge becomes unnecessary: queries,
performance, transactions, and database design still matter.

### JDBC approach vs JPA approach

``` text
JDBC
Java code
   |
   v
SQL statement + JDBC APIs
   |
   v
Database
   |
   v
ResultSet processing and manual object mapping


JPA
Java application
   |
   v
EntityManager / JPQL / entity objects
   |
   v
JPA provider (e.g., Hibernate)
   |
   v
JDBC + database driver
   |
   v
Database
```

  -----------------------------------------------------------------------
  JDBC                                JPA
  ----------------------------------- -----------------------------------
  A low-level database access API     A persistence specification based
                                      on entities and ORM

  Developers commonly write SQL       Developers can work with entities
  directly                            and JPQL; provider generates SQL
                                      for many operations

  Result rows are often mapped to     Entity mapping is defined through
  objects manually                    annotations or XML

  Does not provide JPA entity         Defines entity states and
  lifecycle management                persistence-context behavior

  Still useful when direct SQL        Useful for object-centric domain
  control is required                 models and entity persistence
  -----------------------------------------------------------------------

JPA usually uses JDBC underneath through its provider. They are not
mutually exclusive technologies.

## 1.4 Is JPA a tool or a framework?

JPA itself is a **specification**, not a standalone database engine or
complete implementation.

Think of a specification as a set of rules and contracts. A provider
implements those rules.

``` text
                 JPA specification
            (standard interfaces and rules)
                         |
             +-----------+-----------+
             |                       |
             v                       v
        Hibernate                EclipseLink
      (JPA provider)            (JPA provider)
             |                       |
             v                       v
         Database                  Database
```

Examples of JPA providers include **Hibernate ORM** and **EclipseLink**.
A provider performs operations such as translating entity operations
into SQL, mapping query results to entities, and synchronizing managed
entity changes with the database.

## 1.5 Important JPA terms

-   **Entity:** A Java class whose instances are intended to be
    persisted. It is commonly mapped to a database table.
-   **Persistence:** Keeping data beyond the lifetime of an in-memory
    object or application run.
-   **ORM:** Mapping between Java objects and relational database
    structures.
-   **JPA provider:** An implementation of the JPA specification, such
    as Hibernate.
-   **Persistence unit:** A named configuration describing the
    persistence setup, including managed entity classes and
    provider/database configuration.
-   **Persistence context:** A set of entity instances managed by an
    EntityManager. Within a persistence context, an entity identity is
    tracked and changes to managed entities can be detected.
-   **Transaction:** A unit of work that can be committed or rolled
    back, depending on the transaction environment.

## 1.6 Where is JPA used?

JPA is commonly used in applications that store and retrieve structured
data, for example:

-   Student and university management systems
-   Employee management systems
-   E-commerce product, customer, and order systems
-   Banking and finance applications
-   Hospital and appointment systems
-   Inventory and billing applications

### Real-life example: College portal

When a student registers on a college portal:

1.  The application receives the student's form.
2.  Business logic validates the submitted data.
3.  The application creates or updates a `Student` entity.
4.  An EntityManager manages the entity.
5.  The JPA provider performs the required database interaction.
6.  The transaction is committed, making the change durable if the
    transaction succeeds.

------------------------------------------------------------------------

# 2. JPA Architecture

## 2.1 What is JPA Architecture?

**JPA Architecture** describes how an application, the JPA API, the
persistence provider, JDBC, and the database work together to persist
Java objects.

JPA provides the standard API. The provider implements it and performs
the persistence work. The database stores the actual records.

## 2.2 JPA architecture diagram

``` mermaid
flowchart TD
    A["Client / Browser"] --> B["Application Layer"]
    B --> C["Servlet / Service / Business Logic"]
    C --> D["JPA API: EntityManager, Queries"]
    D --> E["JPA Provider: Hibernate / EclipseLink"]
    E --> F["JDBC API + Database Driver"]
    F --> G[("Relational Database")]
    G --> F
    F --> E
    E --> D
    D --> C
    C --> B
```

### How to read the diagram

1.  A user performs an action in the client, such as registering a
    student.
2.  A web component or service receives the request and executes
    business logic.
3.  The application calls the JPA API, often through an `EntityManager`.
4.  The JPA provider interprets the persistence operation or query.
5.  The provider uses JDBC and a suitable database driver to communicate
    with the database.
6.  The database executes the required operation and returns results.
7.  The provider maps results to entity objects and returns control to
    the application.

The arrows represent a simplified logical flow. Real applications may
have additional layers, transaction managers, connection pools, caches,
and container-managed services.

## 2.3 Main components of JPA Architecture

### 1. Entity

An entity is a persistent domain object. A typical entity is annotated
with `@Entity` and has a primary key marked with `@Id`.

``` java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id
    private int id;

    private String name;
    private int age;

    public Student() {
    }

    // Getters and setters are normally added here.
}
```

This is an illustrative entity. A real project may need additional
constructors, getters/setters, validation, and configuration. Older Java
EE projects may use `javax.persistence.*`; Jakarta EE projects use
`jakarta.persistence.*`, depending on the platform version.

### 2. EntityManager

The `EntityManager` is the main JPA interface used by application code
to manage entity instances, find records, create queries, and perform
persistence operations.

Common methods include:

-   `persist(entity)` --- makes a new entity managed for insertion.
-   `find(EntityClass.class, primaryKey)` --- finds an entity by primary
    key.
-   `merge(entity)` --- copies state from a new or detached object into
    a managed instance and returns that managed instance.
-   `remove(entity)` --- marks a managed entity for deletion.
-   `createQuery(...)` --- creates a JPQL query.
-   `flush()` --- synchronizes pending persistence-context changes with
    the database.

### 3. JPA Provider

A JPA provider implements the JPA specification. Examples include
Hibernate ORM and EclipseLink.

Responsibilities can include:

-   Mapping entities to tables and columns
-   Translating JPQL and entity operations into SQL
-   Tracking changes to managed entities
-   Mapping database results back to entity objects
-   Coordinating persistence-context synchronization

### 4. Persistence Unit

A persistence unit identifies a persistence configuration.
Traditionally, it is configured in `META-INF/persistence.xml`, though
exact configuration approaches depend on the Java EE/Jakarta EE
environment and framework being used.

A persistence unit can define the provider, managed entity classes,
database connection details or data source, and other persistence
properties.

### 5. JDBC and Database Driver

The provider generally uses JDBC and a database driver to send SQL
commands to the database. JPA does not remove the database or the need
for a driver.

### 6. Database

The database stores persistent data in tables, rows, and columns.
Examples include MySQL, PostgreSQL, Oracle Database, and other
relational databases supported by the provider.

## 2.4 How does JPA work? Step-by-step example

Suppose a student named Rahul registers on a college portal.

``` mermaid
sequenceDiagram
    participant U as User
    participant A as Application / Service
    participant E as EntityManager
    participant P as JPA Provider
    participant D as Database

    U->>A: Submit student form
    A->>A: Validate form data
    A->>E: persist(student)
    E->>P: Track persistence operation
    P->>D: INSERT (often at flush/commit)
    D-->>P: Operation result
    P-->>E: Persistence operation completed
    E-->>A: Control returns
    A-->>U: Registration response
```

**Important:** Calling `persist()` does not guarantee that an SQL
`INSERT` is executed immediately. The provider may execute SQL when the
persistence context is flushed or the transaction is committed. The
precise timing depends on the provider, flush mode, and transaction
context.

## 2.5 JPA architecture: exam points

-   JPA is a **specification**, not a provider by itself.
-   Hibernate and EclipseLink are **JPA providers**.
-   EntityManager is the main API for entity operations.
-   The provider commonly uses JDBC to communicate with the database.
-   Entities represent persistent application data.
-   A persistence unit describes the persistence configuration.
-   Transactions help ensure that a set of related database changes is
    committed or rolled back as a unit.

------------------------------------------------------------------------

# 3. EntityManager

## 3.1 What is EntityManager?

**EntityManager** is a JPA interface that manages entity instances
within a persistence context and provides methods to find, persist,
update/merge, remove, and query persistent data.

A simple way to explain it is: the EntityManager is the application's
main point of interaction with JPA-managed entities. It tracks managed
entity objects and coordinates their synchronization with the database
through the provider.

It is not itself the database, and it does not necessarily execute SQL
immediately for every method call.

## 3.2 EntityManager diagram

``` mermaid
flowchart TD
    A["Application / Business Logic"] --> B["EntityManager"]
    B --> C["Persistence Context"]
    C --> D["Managed Entity Objects"]
    B --> E["JPA Provider"]
    E --> F["JDBC / Driver"]
    F --> G[("Database")]
    G --> F
    F --> E
    E --> C
    C --> B
```

## 3.3 EntityManager and the persistence context

A **persistence context** is the set of entity instances that are
currently managed by an EntityManager. The provider tracks entity
identity and changes to managed entities within that context.

For example, after an entity has been loaded using `find()`, changing
one of its properties while it remains managed can be detected and
synchronized with the database during flush or commit. In a typical
managed transaction, a separate `update()` method is not required for
every property change.

``` java
Student student = em.find(Student.class, 101);

if (student != null) {
    student.setName("Rahul Sharma");
    // If the entity remains managed, the provider can detect this change.
    // Synchronization usually occurs at flush or transaction commit.
}
```

This is often called **dirty checking**: the provider detects changes to
managed entities and synchronizes them when required.

## 3.4 Entity lifecycle states

JPA entities commonly move through four states: **new (transient),
managed, detached, and removed**.

``` mermaid
stateDiagram-v2
    [*] --> New: new Student()
    New --> Managed: persist()
    Managed --> Detached: detach() / clear() / close()
    Detached --> Managed: merge() returns managed copy
    Managed --> Removed: remove()
    Removed --> Managed: persist() before deletion is flushed (provider/state rules apply)
    Managed --> [*]: transaction commit / context ends
```

The diagram is conceptual; transaction outcomes and exact state
transitions depend on the operation and context. In particular,
`merge()` returns a managed instance containing the copied state; the
original detached object does not itself become managed merely because
it was passed to `merge()`.

### 1. New / Transient

-   The object was created with `new`.
-   It is not yet managed by an EntityManager.
-   Creating the object alone does not save it to the database.

``` java
Student s = new Student();
s.setId(101);
s.setName("Rahul");
```

### 2. Managed

-   The entity is associated with a persistence context.
-   The EntityManager tracks its persistent identity and changes.
-   Changes can be synchronized with the database at flush or commit.

An entity may become managed through operations such as `persist()` or
by being loaded using `find()`.

### 3. Detached

-   The object represents persistent data but is no longer associated
    with the current persistence context.
-   Changes to it are not automatically tracked by that context.
-   Passing it to `merge()` copies its state into a managed instance.

### 4. Removed

-   The entity is managed but marked for deletion.
-   The deletion is normally synchronized with the database at flush or
    commit.
-   If the transaction rolls back, the database deletion may not be
    committed.

## 3.5 Important EntityManager methods

  -----------------------------------------------------------------------
  Method                  Purpose                 Important point
  ----------------------- ----------------------- -----------------------
  `persist(entity)`       Makes a new entity      Usually used to
                          managed                 schedule insertion; SQL
                                                  may occur later

  `find(Class, id)`       Finds an entity by      Returns the entity or
                          primary key             `null` if no matching
                                                  entity is found

  `merge(entity)`         Copies entity state     Use the returned object
                          into a managed instance as the managed instance

  `remove(entity)`        Marks a managed entity  The entity generally
                          for deletion            must be managed

  `refresh(entity)`       Reloads entity state    Overwrites current
                          from the database       in-memory state with
                                                  database state

  `detach(entity)`        Detaches one entity     Changes are no longer
                                                  tracked in that
                                                  persistence context

  `clear()`               Detaches all managed    Useful in some
                          entities from the       batch-processing
                          context                 situations

  `flush()`               Synchronizes pending    Does not itself commit
                          changes with the        the transaction
                          database                

  `createQuery(...)`      Creates a query, often  JPQL normally refers to
                          written in JPQL         entity classes and
                                                  their properties
  -----------------------------------------------------------------------

## 3.6 Example: Persisting a student

The following example illustrates the idea of saving a new entity. It
assumes `Student` is a correctly configured entity and `em` is a valid
EntityManager in an appropriate transaction context.

``` java
EntityManager em = /* obtained from configured JPA setup */;

EntityTransaction tx = em.getTransaction();

try {
    tx.begin();

    Student student = new Student();
    student.setId(101);
    student.setName("Rahul");
    student.setAge(20);

    em.persist(student);

    tx.commit();
} catch (RuntimeException ex) {
    if (tx.isActive()) {
        tx.rollback();
    }
    throw ex;
}
```

**Teaching note:** The exact way an EntityManager and transaction are
obtained depends on the application. In Java SE, an application may
create an EntityManager from an `EntityManagerFactory`. In a Jakarta EE
container-managed environment, the container may inject an EntityManager
and manage transactions.

## 3.7 Example: Finding and changing a student

``` java
Student student = em.find(Student.class, 101);

if (student != null) {
    student.setAge(21);
    // If student is managed, no explicit update() call is needed.
    // The change can be written during flush/commit.
}
```

This example assumes the entity remains managed and the change occurs in
a suitable transaction. If the object is detached, changing it alone
will not automatically update the database.

## 3.8 `merge()` vs updating a managed entity

A common misunderstanding is that `merge()` simply makes the same object
managed. More precisely, `merge()` copies state into a managed instance
and returns that instance.

``` java
Student managedStudent = em.merge(detachedStudent);
// Continue working with managedStudent.
```

Do not assume `detachedStudent` itself is now managed.

## 3.9 Transaction and flush

A **transaction** groups database operations into a unit of work.
`commit()` attempts to commit the transaction; `rollback()` cancels
uncommitted transactional work when possible.

`flush()` sends pending changes from the persistence context to the
database, but it is not the same as committing the transaction.

``` text
Change managed entity
        |
        v
Persistence context tracks change
        |
        v
flush() sends pending SQL to database
        |
        v
commit() completes transaction
```

The provider may flush automatically at certain points, including
transaction commit. The exact behavior depends on the flush mode and
environment.

## 3.10 Where is EntityManager used?

-   Inserting new student, employee, or product records
-   Finding a record by primary key
-   Changing a managed entity
-   Removing a record
-   Executing JPQL queries
-   Managing the persistence context for a unit of work

------------------------------------------------------------------------

# 4. JPQL (Java Persistence Query Language)

## 4.1 What is JPQL?

**JPQL (Java Persistence Query Language)** is an object-oriented query
language defined by JPA. It is used to query entities and their
persistent properties rather than directly querying database tables and
columns.

The JPA provider translates JPQL into database-specific SQL as needed.

## 4.2 JPQL vs SQL

  -----------------------------------------------------------------------
  JPQL                                SQL
  ----------------------------------- -----------------------------------
  Queries entities and their Java     Queries database tables and columns
  properties                          

  Uses entity names and property      Uses table and column names
  names                               

  Defined by the JPA specification    Defined by database SQL standards
                                      and vendor features

  Translated by the JPA provider      Executed by the database
  -----------------------------------------------------------------------

Suppose the entity class is named `Student` and it has a property named
`name`.

**JPQL:**

``` java
SELECT s FROM Student s WHERE s.name = :studentName
```

This refers to the entity `Student` and its property `name`, not
necessarily a database table literally named `Student` or a column
literally named `name`.

Equivalent SQL might look like:

``` sql
SELECT id, name, age
FROM student
WHERE name = ?;
```

The exact SQL depends on the entity mapping and database.

## 4.3 Common JPQL examples

### Retrieve all students

``` java
List<Student> students =
    em.createQuery("SELECT s FROM Student s", Student.class)
      .getResultList();
```

### Find students by name

``` java
List<Student> students =
    em.createQuery(
        "SELECT s FROM Student s WHERE s.name = :studentName",
        Student.class)
      .setParameter("studentName", "Rahul")
      .getResultList();
```

### Find students older than a given age

``` java
List<Student> students =
    em.createQuery(
        "SELECT s FROM Student s WHERE s.age > :minAge",
        Student.class)
      .setParameter("minAge", 18)
      .getResultList();
```

### Count students

``` java
Long count = em.createQuery(
    "SELECT COUNT(s) FROM Student s", Long.class
).getSingleResult();
```

## 4.4 Important JPQL points

-   JPQL is based on the **entity model**, not directly on table names.
-   Use entity class/entity name and Java persistent property names.
-   Use named parameters such as `:studentName` and bind values using
    `setParameter()`.
-   Parameter binding is safer and clearer than building query strings
    by concatenating user input.
-   JPQL supports filtering, joins, ordering, grouping, and aggregate
    functions.
-   JPQL queries are executed through the JPA query APIs, such as
    `createQuery()`.

------------------------------------------------------------------------

# 5. Entity Relationships in JPA

## 5.1 What is an entity relationship?

An entity relationship describes how one entity is associated with
another entity. For example, one department can have many employees, and
each employee may belong to one department.

JPA relationship annotations describe these associations in the Java
object model. The database representation commonly uses foreign keys or
join tables.

The four common relationship types are:

1.  **One-to-One** --- `@OneToOne`
2.  **One-to-Many** --- `@OneToMany`
3.  **Many-to-One** --- `@ManyToOne`
4.  **Many-to-Many** --- `@ManyToMany`

## 5.2 One-to-One relationship

One record of an entity is associated with one record of another entity.

**Example:** One person has one passport, and one passport belongs to
one person (simplified example).

``` mermaid
erDiagram
    PERSON ||--|| PASSPORT : has
    PERSON {
        int id PK
        string name
    }
    PASSPORT {
        int id PK
        string passportNumber
        int person_id FK
    }
```

Conceptual Java mapping:

``` java
@Entity
public class Person {
    @Id
    private int id;

    private String name;

    @OneToOne
    private Passport passport;
}
```

The complete mapping may require a corresponding `Passport` entity and
explicit ownership/join-column configuration. The side that owns the
relationship usually defines the foreign key mapping.

**Use cases:** Person--passport, user--profile, employee--parking permit
(depending on business rules).

## 5.3 One-to-Many relationship

One entity is associated with multiple instances of another entity.

**Example:** One department has many employees.

``` mermaid
erDiagram
    DEPARTMENT ||--o{ EMPLOYEE : contains
    DEPARTMENT {
        int id PK
        string name
    }
    EMPLOYEE {
        int id PK
        string name
        int department_id FK
    }
```

Conceptual Java mapping:

``` java
@Entity
public class Department {
    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}
```

Here, `mappedBy = "department"` indicates that the `Employee.department`
property owns the relationship. The `mappedBy` value is a Java property
name, not a database column name.

## 5.4 Many-to-One relationship

Many instances of one entity can be associated with one instance of
another entity.

**Example:** Many employees work in one department.

``` java
@Entity
public class Employee {
    @Id
    private int id;

    private String name;

    @ManyToOne
    private Department department;
}
```

In a typical relational mapping, the foreign key is stored on the many
side, here in the employee table. The exact schema depends on the
mapping configuration.

**Important:** `@ManyToOne` is often the owning side of a bidirectional
one-to-many/many-to-one relationship because it usually maps the foreign
key.

## 5.5 Many-to-Many relationship

Many instances of one entity can be associated with many instances of
another entity.

**Example:** A student can enroll in multiple courses, and a course can
have multiple students.

``` mermaid
erDiagram
    STUDENT }o--o{ COURSE : enrolls
    STUDENT {
        int id PK
        string name
    }
    COURSE {
        int id PK
        string title
    }
```

Relational databases commonly represent this association using a join
table:

``` mermaid
erDiagram
    STUDENT ||--o{ STUDENT_COURSE : has
    COURSE ||--o{ STUDENT_COURSE : includes
    STUDENT {
        int id PK
        string name
    }
    COURSE {
        int id PK
        string title
    }
    STUDENT_COURSE {
        int student_id FK
        int course_id FK
    }
```

Conceptual Java mapping:

``` java
@Entity
public class Student {
    @Id
    private int id;

    private String name;

    @ManyToMany
    private List<Course> courses;
}
```

A production mapping often defines `@JoinTable` explicitly and considers
which side owns the association. If the relationship needs additional
attributes---such as enrollment date, grade, or status---an explicit
enrollment entity is often more suitable than a plain many-to-many
association.

## 5.6 Relationship comparison

  -----------------------------------------------------------------------------
  Relationship      Meaning           Example                 Typical
                                                              relational
                                                              representation
  ----------------- ----------------- ----------------------- -----------------
  `@OneToOne`       One A relates to  Person--passport        Foreign key with
                    one B                                     uniqueness, or
                                                              another suitable
                                                              mapping

  `@OneToMany`      One A relates to  Department--employees   Usually
                    many B                                    represented by a
                                                              foreign key on
                                                              the many side

  `@ManyToOne`      Many A relate to  Employees--department   Foreign key on
                    one B                                     the many side

  `@ManyToMany`     Many A relate to  Students--courses       Usually a join
                    many B                                    table
  -----------------------------------------------------------------------------

## 5.7 Unidirectional vs bidirectional relationships

-   **Unidirectional:** Only one entity has a reference to the other
    entity.
-   **Bidirectional:** Both entities have properties referring to each
    other.

For a bidirectional association, one side owns the database relationship
and the other side uses `mappedBy` to refer to the owning property.
Keeping both sides of an in-memory relationship consistent is the
application's responsibility.

Example of a bidirectional department--employee association:

``` java
// Department side
@OneToMany(mappedBy = "department")
private List<Employee> employees;

// Employee side
@ManyToOne
@JoinColumn(name = "department_id")
private Department department;
```

These snippets illustrate the association only; imports, constructors,
getters/setters, and complete entity definitions are omitted.

## 5.8 Why are relationships useful?

Relationships let an application model real-world connections in Java
objects. They can simplify navigation between related data, but they
must be designed carefully.

Important considerations include:

-   Which side owns the relationship
-   Foreign-key and join-table design
-   Whether the relationship is unidirectional or bidirectional
-   Fetching behavior (`LAZY` or `EAGER`)
-   Cascading operations and orphan removal
-   Avoiding unexpected queries or loading too much data

Do not use cascade options blindly. For example, deleting a department
should not automatically delete employees unless that behavior matches
the application's actual business rules.

------------------------------------------------------------------------

# 6. Putting the Topics Together

Consider a college management application.

``` mermaid
flowchart TD
    A["Student / Faculty User"] --> B["Web Layer: Servlet / Controller"]
    B --> C["Business Service"]
    C --> D["EntityManager"]
    D --> E["JPA Provider"]
    E --> F["JDBC / Driver"]
    F --> G[("College Database")]
    C --> H["JPQL Queries"]
    H --> D
    G --- I["Student, Department, Employee, Course tables"]
```

-   **JPA** defines the standard persistence API.
-   **JPA architecture** explains how the application, provider, JDBC,
    and database work together.
-   **EntityManager** manages entity objects and persistence operations.
-   **JPQL** queries entities and their properties.
-   **Relationships** model associations between entities such as
    students and courses.

### One complete conceptual flow

1.  A faculty member opens the college portal.
2.  The application checks that the user is permitted to perform the
    action.
3.  Business logic validates the data.
4.  The application uses an EntityManager to create, find, or modify an
    entity.
5.  The provider maps the operation to database interactions.
6.  JPQL can be used to retrieve entities matching a condition.
7.  Entity relationships let the application navigate from a department
    to employees or from a student to courses.
8.  A transaction commits the successful changes.

------------------------------------------------------------------------

# 7. Important Differences for Revision

## JPA vs Hibernate

  -----------------------------------------------------------------------
  JPA                                 Hibernate
  ----------------------------------- -----------------------------------
  A specification defining standard   An ORM framework and a widely used
  persistence APIs and rules          JPA provider

  Defines interfaces such as          Implements JPA APIs and supplies
  `EntityManager`                     additional Hibernate-specific
                                      features

  Aims to support provider            Provides the actual persistence
  portability when standard features  implementation
  are used                            
  -----------------------------------------------------------------------

## EntityManager vs Database

  -----------------------------------------------------------------------
  EntityManager                       Database
  ----------------------------------- -----------------------------------
  Manages entity instances in a       Stores persistent records
  persistence context                 

  Offers Java APIs such as `find()`   Executes database operations
  and `persist()`                     

  Coordinates with a provider         Receives SQL through database
                                      connectivity infrastructure
  -----------------------------------------------------------------------

## `persist()` vs `merge()`

  -----------------------------------------------------------------------
  `persist()`                         `merge()`
  ----------------------------------- -----------------------------------
  Primarily used to make a new entity Copies state from a new or detached
  managed                             entity into a managed instance

  Returns `void`                      Returns the managed instance

  The provider can schedule an insert The provider may schedule an insert
                                      or update depending on entity
                                      identity/state and mapping
  -----------------------------------------------------------------------

## JPQL vs SQL

  -----------------------------------------------------------------------
  JPQL                                SQL
  ----------------------------------- -----------------------------------
  Uses entity names and persistent    Uses tables and columns
  properties                          

  Provider translates it to SQL       Database executes it

  Example: `SELECT s FROM Student s`  Example: `SELECT * FROM student`
  -----------------------------------------------------------------------

------------------------------------------------------------------------

# 8. University / Examination Questions

## Long-answer questions (10 marks each)

### 1. Explain the JPA Architecture.

In your answer, include:

-   Definition of JPA architecture
-   A labelled architecture diagram
-   Role of entity, EntityManager, persistence unit, JPA provider,
    JDBC/driver, and database
-   Step-by-step flow of a persistence operation
-   One real-life example, such as saving a student record

### 2. What is Java Persistence API (JPA)?

In your answer, include:

-   Definition of JPA
-   Explanation of persistence and ORM
-   Difference between Java objects and relational tables
-   Why JPA is used and how it relates to JDBC
-   JPA specification vs provider, with examples
-   Applications of JPA in real-world systems

### 3. What is EntityManager?

In your answer, include:

-   Definition and purpose of EntityManager
-   Explanation of the persistence context
-   Important methods: `persist()`, `find()`, `merge()`, `remove()`, and
    `flush()`
-   Entity lifecycle states: new, managed, detached, and removed
-   A small code example or lifecycle diagram
-   The role of transactions and the difference between `flush()` and
    `commit()`

## Additional practice questions

4.  Define ORM and explain how it maps Java objects to relational
    database tables.
5.  Differentiate JPA and JDBC with a suitable diagram.
6.  What is a JPA provider? Explain the role of Hibernate or
    EclipseLink.
7.  What is JPQL? Explain how JPQL differs from SQL with examples.
8.  Explain the four entity relationship types in JPA with diagrams and
    examples.
9.  Differentiate unidirectional and bidirectional relationships in JPA.
10. Explain the difference between `persist()` and `merge()`.
11. What is a persistence context? Explain dirty checking.
12. Differentiate `flush()` and transaction `commit()`.

------------------------------------------------------------------------

# 9. Quick Revision

-   **JPA:** Java specification for persistence and ORM.
-   **ORM:** Maps object-oriented data to relational database
    structures.
-   **JPA provider:** Implements the JPA specification, e.g., Hibernate
    or EclipseLink.
-   **Entity:** Java class representing persistent application data.
-   **EntityManager:** API for managing entities and performing
    persistence operations.
-   **Persistence context:** Tracks managed entities and their changes.
-   **JPQL:** Queries entities and Java properties, not directly table
    names.
-   **`persist()`:** Makes a new entity managed.
-   **`find()`:** Retrieves an entity by primary key.
-   **`merge()`:** Copies state into a managed entity and returns that
    instance.
-   **`remove()`:** Marks a managed entity for deletion.
-   **`flush()`:** Synchronizes pending persistence-context changes with
    the database.
-   **`commit()`:** Commits a transaction.
-   **`@OneToOne`:** One-to-one association.
-   **`@OneToMany`:** One entity associated with many entities.
-   **`@ManyToOne`:** Many entities associated with one entity.
-   **`@ManyToMany`:** Many entities associated with many entities,
    commonly using a join table.

> **Remember:** JPA defines the standard, the provider implements it,
> EntityManager manages entity operations, JPQL queries the entity
> model, and relationships connect entities.
