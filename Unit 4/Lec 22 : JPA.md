# Java Persistence API (JPA) --- Student Notes

## Learning Outcomes

After studying these notes, you should be able to:

-   Explain what JPA is and why it is used.
-   Understand ORM and the role of a JPA provider such as Hibernate.
-   Draw and explain the basic JPA architecture.
-   Use the `EntityManager` for common persistence operations.
-   Write basic JPQL queries.
-   Explain and map relationships between entities.

## Table of Contents

1.  [Introduction to JPA](#1-introduction-to-jpa)
2.  [JPA Architecture](#2-jpa-architecture)
3.  [EntityManager](#3-entitymanager)
4.  [JPQL](#4-jpql-java-persistence-query-language)
5.  [Entity Relationships](#5-entity-relationships)
6.  [Quick Revision](#6-quick-revision)
7.  [Practice Questions](#7-practice-questions)

------------------------------------------------------------------------

# 1. Introduction to JPA

## 1.1 What is JPA?

**JPA (Java Persistence API)** is a Java specification used to map Java
objects to relational database records and manage their persistence.

In simple words, JPA helps a Java application save, retrieve, update,
and delete data in a database by working with Java objects.

For example, a college application may contain a `Student` object:

``` java
Student student = new Student();
student.setName("Rahul");
student.setAge(20);
```

This object exists in program memory. To keep the student's information
after the application stops, we need to store it in a database. JPA
provides a standard way to do this.

## 1.2 Why do we need JPA?

Without an ORM framework, developers often write SQL statements and
manually convert query results into Java objects. JPA can handle much of
this mapping.

  -----------------------------------------------------------------------
  JDBC approach                       JPA approach
  ----------------------------------- -----------------------------------
  Developers commonly write SQL       Developers work with entity objects
  directly.                           and can use JPQL.

  Result sets are processed manually. The JPA provider maps database
                                      records to entities.

  More repetitive database-mapping    Common persistence operations can
  code may be required.               require less boilerplate.
  -----------------------------------------------------------------------

**Important:** JPA does not replace SQL or JDBC completely. A JPA
provider commonly uses JDBC behind the scenes, and developers may still
use native SQL when needed.

## 1.3 What is ORM?

**ORM stands for Object-Relational Mapping.**

-   Java applications work with classes and objects.
-   Relational databases store data in tables, rows, and columns.
-   ORM maps Java objects to database records.

``` text
Java Application                     Relational Database

Student class       <--- ORM --->    student table
-----------------------------------------------
id: int                              id
name: String                         name
age: int                             age
```

Example mapping:

  Java entity field   Database column
  ------------------- -----------------
  `id`                `id`
  `name`              `name`
  `age`               `age`

## 1.4 JPA is a specification

JPA defines standard interfaces, annotations, and rules. It needs an
implementation called a **JPA provider**.

Examples of JPA providers include:

-   **Hibernate**
-   **EclipseLink**

Think of JPA as the rulebook and Hibernate as one implementation that
follows those rules.

``` text
Java Application
       |
       v
   JPA Standard
       |
       v
 JPA Provider
 (e.g., Hibernate)
       |
       v
 Database
```

## 1.5 What is an Entity?

An **entity** is a Java class whose objects can be stored in a database.
An entity is commonly mapped to a database table.

Example:

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

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

### Important annotations

  Annotation          Purpose
  ------------------- ----------------------------------------------
  `@Entity`           Marks the class as a JPA entity.
  `@Id`               Marks the primary-key field or property.
  `@Table`            Customizes the database table mapping.
  `@Column`           Customizes a column mapping.
  `@GeneratedValue`   Configures automatic primary-key generation.

For example:

``` java
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_name", nullable = false)
    private String name;
}
```

This example uses Jakarta Persistence imports such as
`jakarta.persistence.Entity`. Older Java EE applications may use
`javax.persistence` instead. Do not mix the two namespaces in one
application.

## 1.6 Where is JPA used?

-   Student and college management systems
-   Banking applications
-   E-commerce websites
-   Employee management systems
-   Hospital management systems
-   Inventory applications

For example, when a student registers on a college portal, JPA can help
persist the student's details in the database.

------------------------------------------------------------------------

# 2. JPA Architecture

## 2.1 Basic architecture

JPA connects the Java application with a relational database through a
JPA provider.

``` text
+----------------------------------+
|          Java Application        |
|      Servlet / Business Logic    |
+----------------+-----------------+
                 |
                 v
+----------------------------------+
|           EntityManager           |
|  Persist, Find, Merge, Remove     |
+----------------+-----------------+
                 |
                 v
+----------------------------------+
|            JPA Provider           |
|       Hibernate / EclipseLink     |
+----------------+-----------------+
                 |
                 v
+----------------------------------+
|          JDBC / Driver            |
+----------------+-----------------+
                 |
                 v
+----------------------------------+
|           Database                |
|        MySQL / PostgreSQL          |
+----------------------------------+
```

### Explanation of each component

1.  **Java application:** Contains the application's business logic,
    such as a Servlet or service class.
2.  **Entity:** Represents persistent data, such as a student or course.
3.  **EntityManager:** Provides operations to manage entities.
4.  **JPA provider:** Implements the JPA specification and performs ORM
    work.
5.  **JDBC driver:** Allows the provider to communicate with the
    database.
6.  **Database:** Stores the actual data.

## 2.2 Persistence Unit

A **persistence unit** is a named configuration for a set of entities
and their persistence setup. In traditional Java SE/JPA setups, it is
commonly defined in `META-INF/persistence.xml`. Jakarta EE applications
can also use container-managed persistence configuration.

A simplified `persistence.xml` example:

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             version="3.0">

    <persistence-unit name="CollegePU">
        <class>com.example.Student</class>
        <properties>
            <property name="jakarta.persistence.jdbc.url"
                      value="jdbc:mysql://localhost:3306/college"/>
            <property name="jakarta.persistence.jdbc.user"
                      value="root"/>
            <property name="jakarta.persistence.jdbc.password"
                      value="your_password"/>
            <property name="jakarta.persistence.jdbc.driver"
                      value="com.mysql.cj.jdbc.Driver"/>
        </properties>
    </persistence-unit>

</persistence>
```

This is an illustrative configuration. The JDBC driver, database,
provider, credentials, and configuration details must match your
environment. Do not commit real passwords to a public repository.

## 2.3 How a JPA operation works

Suppose an application saves a student:

``` text
1. Application creates Student object
                 |
                 v
2. EntityManager manages the object
                 |
                 v
3. JPA provider prepares database operation
                 |
                 v
4. JDBC communicates with the database
                 |
                 v
5. Database stores the student record
```

The provider may delay the actual SQL execution until a flush or
transaction commit.

## 2.4 JPA and Hibernate: difference

  -----------------------------------------------------------------------
  JPA                                 Hibernate
  ----------------------------------- -----------------------------------
  A specification / standard API      An ORM framework and JPA provider

  Defines common interfaces and       Implements JPA and provides
  annotations                         additional features

  Allows applications to use a        Performs the ORM and database work
  standard persistence API            
  -----------------------------------------------------------------------

**Remember:** JPA is the standard; Hibernate is one popular
implementation of that standard.

------------------------------------------------------------------------

# 3. EntityManager

## 3.1 What is EntityManager?

The `EntityManager` is a central JPA interface used to manage entity
objects and interact with persistent data.

You can think of it as the component through which an application asks
JPA to save, find, update, or delete entity data.

Common operations:

  -----------------------------------------------------------------------
  Method                              Purpose
  ----------------------------------- -----------------------------------
  `persist()`                         Makes a new entity managed so it
                                      can be inserted.

  `find()`                            Finds an entity using its primary
                                      key.

  `merge()`                           Copies the state of a new or
                                      detached entity into a managed
                                      entity and returns that managed
                                      instance.

  `remove()`                          Marks a managed entity for
                                      deletion.

  `createQuery()`                     Creates a JPQL query.

  `flush()`                           Synchronizes pending changes with
                                      the database.
  -----------------------------------------------------------------------

## 3.2 Creating an EntityManager

In Java SE, an `EntityManagerFactory` can create an `EntityManager`:

``` java
EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("CollegePU");

EntityManager em = emf.createEntityManager();
```

The persistence unit name must match the name configured in
`persistence.xml`.

In Jakarta EE, an application can commonly inject a container-managed
EntityManager:

``` java
@PersistenceContext
private EntityManager em;
```

The appropriate approach depends on whether the application is running
in Java SE or inside a Jakarta EE container.

## 3.3 The four main CRUD operations

CRUD means **Create, Read, Update, Delete**.

### A. Create --- `persist()`

``` java
EntityTransaction tx = em.getTransaction();

tx.begin();

Student student = new Student(101, "Rahul", 20);
em.persist(student);

tx.commit();
```

Explanation:

1.  Start a transaction.
2.  Create a Student object.
3.  Call `persist()` to make the entity managed.
4.  Commit the transaction.

The entity must be properly mapped, and the transaction approach shown
here is for an application-managed EntityManager in Java SE.

### B. Read --- `find()`

``` java
Student student = em.find(Student.class, 101);

if (student != null) {
    System.out.println(student.getName());
}
```

`find()` searches for the entity using its primary key. Here, it looks
for the Student whose ID is `101`.

### C. Update --- modify a managed entity

``` java
EntityTransaction tx = em.getTransaction();

tx.begin();

Student student = em.find(Student.class, 101);

if (student != null) {
    student.setAge(21);
}

tx.commit();
```

If the entity is managed, JPA tracks its changes. The provider can
synchronize the updated value with the database at flush or commit.

### D. Delete --- `remove()`

``` java
EntityTransaction tx = em.getTransaction();

tx.begin();

Student student = em.find(Student.class, 101);

if (student != null) {
    em.remove(student);
}

tx.commit();
```

The entity is marked for removal, and the database deletion is
synchronized as part of the persistence operation.

## 3.4 Entity lifecycle states

JPA entities commonly move through four lifecycle states:

``` text
             new Student()
                   |
                   v
             +-----------+
             |   NEW     |
             +-----------+
                   |
                persist()
                   |
                   v
             +-----------+
             |  MANAGED  | <---- find() / query result
             +-----------+
               |       |
       detach()|       | remove()
       or      |       |
       clear() v       v
        +-----------+  +-----------+
        | DETACHED  |  |  REMOVED  |
        +-----------+  +-----------+
               |
               | merge() copies state
               +-------> a MANAGED instance
```

-   **New (transient):** The object has been created but is not managed
    by an EntityManager.
-   **Managed:** The EntityManager tracks the entity's persistent state.
-   **Detached:** The entity was once managed but is no longer tracked
    by that EntityManager.
-   **Removed:** The managed entity has been marked for deletion.

**Important:** `merge()` returns a managed instance. The original
detached object does not automatically become managed.

## 3.5 Transactions

A transaction groups database operations into a unit of work.

``` text
BEGIN TRANSACTION
        |
        v
Perform persistence operations
        |
        v
      COMMIT
        |
        v
Changes are committed
```

If an error occurs, the application may roll back the transaction so
that the transaction's changes are not committed.

In Java SE with an application-managed EntityManager:

``` java
EntityTransaction tx = em.getTransaction();

try {
    tx.begin();

    // Perform database operations

    tx.commit();
} catch (RuntimeException e) {
    if (tx.isActive()) {
        tx.rollback();
    }
    throw e;
}
```

In container-managed Jakarta EE applications, transaction management is
often handled by the container, so the transaction approach differs.

## 3.6 Closing resources

For an application-managed EntityManager in Java SE:

``` java
em.close();
emf.close();
```

Close resources when they are no longer needed. Do not manually close a
container-managed EntityManager injected with `@PersistenceContext`.

------------------------------------------------------------------------

# 4. JPQL (Java Persistence Query Language)

## 4.1 What is JPQL?

**JPQL is a query language used to retrieve and manipulate data through
JPA entities.**

Its syntax resembles SQL, but JPQL generally refers to **entity classes
and their Java attributes**, not directly to database table and column
names.

SQL example:

``` sql
SELECT * FROM students WHERE age > 18;
```

JPQL example:

``` jpql
SELECT s FROM Student s WHERE s.age > 18
```

Notice the difference:

-   SQL refers to the table `students`.
-   JPQL refers to the entity `Student`.
-   JPQL uses the Java attribute `age`.

## 4.2 JPQL architecture

``` text
Java Application
       |
       v
Create JPQL Query
       |
       v
JPA Provider
       |
       v
Translate JPQL to Database Query
       |
       v
Database
       |
       v
Entity Results Returned
```

The JPA provider translates JPQL into database-specific SQL as needed.

## 4.3 Basic SELECT query

``` java
List<Student> students = em
    .createQuery("SELECT s FROM Student s", Student.class)
    .getResultList();
```

Explanation:

-   `Student` is the entity name.
-   `s` is an alias for each Student entity.
-   `getResultList()` returns the matching results as a list.

## 4.4 WHERE clause

Find students older than 18:

``` java
List<Student> students = em
    .createQuery(
        "SELECT s FROM Student s WHERE s.age > :minAge",
        Student.class
    )
    .setParameter("minAge", 18)
    .getResultList();
```

The `:minAge` expression is a named parameter. Setting parameters is
safer and clearer than joining user input into query strings.

## 4.5 Common JPQL examples

### Find a student by name

``` jpql
SELECT s FROM Student s WHERE s.name = :studentName
```

### Find students whose age is between 18 and 22

``` jpql
SELECT s FROM Student s WHERE s.age BETWEEN 18 AND 22
```

### Sort students by name

``` jpql
SELECT s FROM Student s ORDER BY s.name ASC
```

### Count students

``` jpql
SELECT COUNT(s) FROM Student s
```

### Find students whose name starts with 'R'

``` jpql
SELECT s FROM Student s WHERE s.name LIKE 'R%'
```

## 4.6 Retrieving one result

``` java
Student student = em.createQuery(
        "SELECT s FROM Student s WHERE s.id = :id",
        Student.class)
    .setParameter("id", 101)
    .getSingleResult();
```

`getSingleResult()` expects exactly one result. It can throw an
exception if no result or more than one result is found. When zero
results are possible, consider `getResultList()` or another suitable
approach.

## 4.7 JPQL vs SQL

  -----------------------------------------------------------------------
  JPQL                                SQL
  ----------------------------------- -----------------------------------
  Works primarily with entities and   Works with database tables and
  their attributes                    columns

  Uses entity names, such as          Uses table names, such as
  `Student`                           `students`

  Uses Java attribute names, such as  Uses column names, such as `age`
  `s.age`                             

  Provider translates JPQL into SQL   Executed by the database engine
  -----------------------------------------------------------------------

**Remember:** JPQL is object-oriented querying; SQL is database-oriented
querying.

------------------------------------------------------------------------

# 5. Entity Relationships

## 5.1 What are relationships?

In real applications, entities are connected to one another.

Examples:

-   A student belongs to one department.
-   One department has many students.
-   One student can enroll in many courses.
-   A course can contain many students.

JPA relationships represent these connections between entity objects and
their associated database records.

## 5.2 Four main relationship types

  -----------------------------------------------------------------------
  Relationship            Meaning                 Example
  ----------------------- ----------------------- -----------------------
  One-to-One              One record is related   Person ↔ Passport
                          to one record           

  One-to-Many             One record is related   Department → Students
                          to many records         

  Many-to-One             Many records are        Many Students → One
                          related to one record   Department

  Many-to-Many            Many records relate to  Students ↔ Courses
                          many records            
  -----------------------------------------------------------------------

## 5.3 One-to-One

Example: One person has one passport, and each passport belongs to one
person.

``` text
+----------+       1 : 1       +----------+
|  Person  | ----------------- | Passport |
+----------+                   +----------+
```

Example mapping:

``` java
@Entity
public class Person {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "passport_id")
    private Passport passport;
}
```

``` java
@Entity
public class Passport {

    @Id
    private Long id;

    private String passportNumber;
}
```

This is a simplified mapping. In a real application, decide which entity
owns the relationship and which database table contains the foreign key.
If both entities need navigation, use `mappedBy` on the inverse side.

## 5.4 One-to-Many

Example: One department can have many students.

``` text
             +----------------+
             |   Department   |
             +----------------+
                    |
             1      |      *
                    |
        +-----------+-----------+
        |           |           |
        v           v           v
   +---------+ +---------+ +---------+
   | Student | | Student | | Student |
   +---------+ +---------+ +---------+
```

A typical bidirectional mapping places the foreign key on the many side:

``` java
@Entity
public class Department {

    @Id
    private Long id;

    @OneToMany(mappedBy = "department")
    private List<Student> students = new ArrayList<>();
}
```

The Student entity owns the relationship:

``` java
@Entity
public class Student {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
```

Here, `mappedBy = "department"` refers to the Java field in `Student`,
not the database column name.

## 5.5 Many-to-One

Many students may belong to one department.

``` text
Student A ----\
Student B -----+----> Department
Student C ----/
```

The common mapping is:

``` java
@ManyToOne
@JoinColumn(name = "department_id")
private Department department;
```

The `department_id` foreign key is stored in the Student table in this
example.

**Important:** One-to-many and many-to-one often describe opposite sides
of the same relationship.

## 5.6 Many-to-Many

Example: A student can enroll in many courses, and each course can have
many students.

``` text
+-----------+                    +-----------+
| Students  |                    |  Courses  |
+-----------+                    +-----------+
| Rahul     | -----\       /---- | Java      |
| Priya     | ------+-----+----- | DBMS      |
| Aman      | -----/       \---- | OS        |
+-----------+                    +-----------+
```

A many-to-many relationship is commonly represented in the database
using a **join table**.

``` text
Students               Student_Course              Courses
+----+------+          +------------+-----------+   +----+------+
| id | name |          | student_id | course_id |   | id | name |
+----+------+          +------------+-----------+   +----+------+
| 1  | Rahul| -------> |     1      |     10    | <-| 10 | Java |
| 2  | Priya| -------> |     2      |     10    |   | 11 | DBMS |
+----+------+          |     1      |     11    |   +----+------+
                       +------------+-----------+
```

Simplified mapping:

``` java
@Entity
public class Student {

    @Id
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();
}
```

The Course entity can map the inverse side:

``` java
@ManyToMany(mappedBy = "courses")
private Set<Student> students = new HashSet<>();
```

Use the `mappedBy` side to indicate that the other entity owns the
relationship. For production systems, if the association itself needs
attributes (for example, enrollment date or grade), model the join table
as a separate entity instead of using a plain many-to-many mapping.

## 5.7 Relationship annotations

  -----------------------------------------------------------------------
  Annotation                          Meaning
  ----------------------------------- -----------------------------------
  `@OneToOne`                         One entity is related to one other
                                      entity

  `@OneToMany`                        One entity is related to multiple
                                      entities

  `@ManyToOne`                        Multiple entities relate to one
                                      entity

  `@ManyToMany`                       Multiple entities relate to
                                      multiple entities

  `@JoinColumn`                       Configures a foreign-key column

  `@JoinTable`                        Configures a join table

  `mappedBy`                          Identifies the field that owns a
                                      bidirectional relationship
  -----------------------------------------------------------------------

## 5.8 Owning side and inverse side

In a bidirectional relationship, one side owns the database relationship
and the other side refers to it using `mappedBy`.

For example:

``` java
// Owning side: Student
@ManyToOne
@JoinColumn(name = "department_id")
private Department department;
```

``` java
// Inverse side: Department
@OneToMany(mappedBy = "department")
private List<Student> students;
```

The owning side controls updates to the relationship's foreign-key
mapping. When writing application code, keep both sides of a
bidirectional relationship consistent.

## 5.9 Cascade and fetch basics

Relationships may also define how operations and loading behave.

### Cascade

Cascade settings specify whether certain operations on one entity should
propagate to related entities.

Examples include:

-   `CascadeType.PERSIST`
-   `CascadeType.MERGE`
-   `CascadeType.REMOVE`
-   `CascadeType.ALL`

Use cascading carefully. For example, deleting a department should not
automatically delete students unless that behavior is intended.

### Fetching

-   `FetchType.EAGER`: Related data is expected to be fetched eagerly.
-   `FetchType.LAZY`: Related data can be fetched when accessed,
    depending on provider behavior and context.

The defaults vary by relationship type: `@ManyToOne` and `@OneToOne`
default to eager fetching, while `@OneToMany` and `@ManyToMany` default
to lazy fetching.

Lazy-loaded data may not be accessible after the persistence context is
closed if it was not loaded earlier.

------------------------------------------------------------------------

# 6. Quick Revision

## JPA in one diagram

``` text
Java Objects
     |
     v
  JPA API
     |
     v
JPA Provider (e.g., Hibernate)
     |
     v
JDBC / Database
```

## Remember these differences

  Term            Remember
  --------------- -----------------------------------------------------
  JPA             Standard API for persistence
  Hibernate       A popular JPA provider / ORM framework
  Entity          Java class mapped to persistent data
  EntityManager   Manages entity persistence
  JPQL            Queries entities and their attributes
  Relationship    Connection between entities
  Foreign key     Database column that refers to another record
  Join table      Table used to represent a many-to-many relationship

## CRUD mapping

  CRUD operation   Typical JPA method / approach
  ---------------- ---------------------------------------------------------
  Create           `persist()`
  Read             `find()` or JPQL
  Update           Modify a managed entity; use `merge()` when appropriate
  Delete           `remove()`

## Relationship memory trick

-   **One-to-One:** One person ↔ one passport.
-   **One-to-Many:** One department → many students.
-   **Many-to-One:** Many students → one department.
-   **Many-to-Many:** Many students ↔ many courses.

------------------------------------------------------------------------

# 7. Practice Questions

## Short-answer questions

1.  What is JPA? Why is it used?
2.  What is ORM? Explain with an example.
3.  Is JPA an implementation or a specification?
4.  What is the role of a JPA provider?
5.  What is an entity? Explain `@Entity` and `@Id`.
6.  Explain the basic architecture of JPA.
7.  What is the purpose of `EntityManager`?
8.  Explain `persist()`, `find()`, `merge()`, and `remove()`.
9.  What is JPQL? How is it different from SQL?
10. What is a named parameter in JPQL?
11. Explain the four types of entity relationships.
12. What is the purpose of `mappedBy`?
13. What is a join table?
14. What is the difference between eager and lazy fetching?
15. What is cascading in JPA?

## Diagram-based questions

1.  Draw and explain the architecture of JPA.
2.  Draw the lifecycle states of a JPA entity.
3.  Draw a one-to-many relationship between Department and Student.
4.  Draw a many-to-many relationship between Student and Course,
    including the join table.

## Coding practice

1.  Create a `Student` entity with `id`, `name`, and `age`.
2.  Write JPA code to persist a Student.
3.  Retrieve a Student using `EntityManager.find()`.
4.  Write a JPQL query to find students older than 18.
5.  Write a JPQL query to sort students by name.
6.  Map a many-to-one relationship between Student and Department.
7.  Map a many-to-many relationship between Student and Course.

------------------------------------------------------------------------

## Key Takeaway

**JPA makes database persistence more object-oriented.** The
`EntityManager` manages entity objects, JPQL queries entity data, and
relationship annotations model how entities connect to one another.

As you study, focus on understanding the architecture and drawing the
diagrams before memorizing annotations or code.
