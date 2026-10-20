# Web Services in Advanced Java
## Introduction to Web Services, SOAP and RESTful Web Services

> **Main focus:** RESTful Web Services  
> **Level:** Beginner-friendly Advanced Java notes

---

## 1. Learning Objectives

After studying this topic, students should be able to:

- Explain what a web service is and why it is used.
- Understand how two different applications communicate over a network.
- Differentiate SOAP and REST.
- Explain REST architecture, resources, endpoints, HTTP methods, and status codes.
- Understand JSON request and response data.
- Read a simple REST API example using Java and JAX-RS.
- Identify common uses of REST APIs in real applications.

---

# Part A — Introduction to Web Services

## 2. What Is a Web Service?

A **web service** is a software component that allows two applications to communicate with each other over a network, commonly using the internet.

The applications may be built using different programming languages or technologies. For example, a Java application can communicate with a Python service or a mobile app.

### Simple real-life example: Checking a delivery order

Suppose you use a shopping app to check your order status.

1. You open the mobile app.
2. The app sends a request to the company's server.
3. The server checks the order information.
4. The server sends a response such as `Out for delivery`.
5. The app displays the result.

The app and server communicate through an API. If that API is exposed over a network using web technologies, it may be part of a web service.

## 3. Web Service Communication Diagram

```text
+--------------------+        Request         +--------------------+
|                    | ---------------------> |                    |
| Mobile App /       |                        | Server / Web       |
| Browser / Client   | <--------------------- | Service            |
|                    |        Response        |                    |
+--------------------+                        +--------------------+
```

**Remember:**
- **Client:** The application that requests information or an action.
- **Server:** The application that receives the request and processes it.
- **Request:** The message sent by the client.
- **Response:** The result returned by the server.
- **API:** A defined interface that allows software components to interact.

## 4. Why Do We Need Web Services?

Imagine a college has separate systems for its website, student mobile app, attendance system, and fee payment system. Each system needs access to student information.

Without a shared service, each application might need its own separate connection and implementation. This creates duplication and makes updates difficult.

A web service can provide a common interface for authorized applications.

### Advantages

1. **Interoperability:** Applications written in different languages can communicate.
2. **Reusability:** Multiple clients can use the same service.
3. **Loose coupling:** The client does not need to know the server's internal implementation.
4. **Platform independence:** Communication can work across different operating systems and platforms.
5. **Integration:** Services can connect websites, mobile apps, payment systems, and other software.
6. **Centralized business logic:** Rules can be maintained on the server instead of duplicated across clients.

### Limitations and challenges

- A network connection is required.
- Network delays or server failures can affect the client.
- Authentication and authorization must be handled correctly.
- Poorly designed APIs can be difficult to maintain.
- Sensitive information must be protected using suitable security measures, usually including HTTPS.

## 5. Web Service vs. Website

| Website | Web Service / API |
|---|---|
| Commonly designed for people to view and interact with. | Commonly designed for software applications to communicate with. |
| Often returns HTML pages. | Often returns structured data such as JSON or XML. |
| A browser usually displays the result directly. | A client application usually processes the returned data. |
| Example: A college website's home page. | Example: An API that returns student details. |

This is a general distinction, not an absolute rule. Websites can use APIs internally, and APIs can return different formats.

---

# Part B — SOAP and REST

## 6. What Is SOAP?

**SOAP (Simple Object Access Protocol)** is a messaging protocol used for exchanging structured information between applications.

SOAP messages are represented using **XML** and follow a defined message structure. SOAP can work over HTTP and can also be used with other underlying transport protocols.

### SOAP message structure

A SOAP message commonly contains:

- **Envelope:** The outer element that identifies the message as SOAP.
- **Header:** Optional metadata, such as security or routing information.
- **Body:** The main request or response content.
- **Fault:** Standardized error information when a SOAP operation fails.

### Simplified SOAP message example

```xml
<soap:Envelope
    xmlns:soap="http://www.w3.org/2003/05/soap-envelope">
    <soap:Header>
        <!-- Optional metadata -->
    </soap:Header>
    <soap:Body>
        <GetStudentRequest>
            <studentId>101</studentId>
        </GetStudentRequest>
    </soap:Body>
</soap:Envelope>
```

This is a simplified example of a SOAP request. The exact XML structure depends on the service definition.

### SOAP communication diagram

```text
Client
  |
  | SOAP request (XML envelope)
  v
SOAP Web Service
  |
  | Processes the operation
  v
SOAP response (XML envelope)
  |
  v
Client
```

### Important SOAP features

- Uses a defined XML message format.
- Commonly uses a formal service contract, often described using **WSDL (Web Services Description Language)**.
- Supports standardized extensions for concerns such as message security and reliability.
- Can be useful in enterprise systems where strict contracts and established messaging standards are important.
- Usually requires more message structure than a small JSON-based REST API.

## 7. What Is REST?

**REST (Representational State Transfer)** is an architectural style for designing networked applications. A service designed according to REST principles is called a **RESTful web service**.

REST APIs commonly use HTTP and exchange data in JSON format, although REST is not limited to JSON.

In a RESTful API, information is modeled as **resources**. A resource might be a student, product, order, employee, or book. Each resource is identified through a URI (Uniform Resource Identifier), commonly seen as a URL in API examples.

### Simple definition

A **RESTful web service** exposes resources through URLs and uses HTTP methods to read, create, update, or delete those resources.

### Real-life example: Student Management API

Suppose a college application manages students.

A student resource might be represented as:

```json
{
  "id": 101,
  "name": "Aarav",
  "course": "B.Tech CSE"
}
```

The API might provide these endpoints:

| Operation | HTTP method | Endpoint | Meaning |
|---|---|---|---|
| Get all students | `GET` | `/api/students` | Return the student list |
| Get one student | `GET` | `/api/students/101` | Return student 101 |
| Create a student | `POST` | `/api/students` | Add a new student |
| Update a student | `PUT` | `/api/students/101` | Replace/update student 101 |
| Partially update a student | `PATCH` | `/api/students/101` | Change selected fields |
| Delete a student | `DELETE` | `/api/students/101` | Remove student 101 |

These are example routes. An actual API's behavior depends on its implementation and documentation.

## 8. How a REST API Works

Consider a mobile app that wants to show the details of student 101.

1. The client sends an HTTP `GET` request to `/api/students/101`.
2. The server receives the request.
3. The server validates the request and checks authorization where required.
4. The server retrieves the student information from its data source.
5. The server returns an HTTP response, often containing JSON.
6. The client reads the response and displays the student's details.

### REST request-response diagram

```text
+---------------------+
| Mobile App / Client |
+---------------------+
          |
          | GET /api/students/101
          | Accept: application/json
          v
+---------------------+
| REST API Server     |
|                     |
| 1. Receive request  |
| 2. Validate access  |
| 3. Find student     |
| 4. Build response   |
+---------------------+
          |
          | HTTP 200 OK
          | Content-Type: application/json
          | {"id":101,"name":"Aarav",...}
          v
+---------------------+
| Client displays     |
| student information |
+---------------------+
```

## 9. Important REST Concepts

### 9.1 Resource

A **resource** is the information or entity managed by the API.

Examples:
- Student
- Product
- Employee
- Order
- Book

For a student API, an individual student is a resource.

### 9.2 URI / URL

A **URI** identifies a resource. In common API examples, the resource address is written as a URL.

Examples:

```text
/api/students
/api/students/101
/api/courses/CS101
```

Prefer resource-oriented names, usually nouns, rather than action-oriented routes.

**Better:** `GET /api/students/101`  
**Less REST-oriented:** `GET /getStudentById?id=101`

The second style can still work as an HTTP endpoint, but the first expresses the resource more clearly.

### 9.3 Endpoint

An **endpoint** is an API address used with a particular HTTP method to access a function or resource.

For example:

```text
GET /api/students/101
```

The endpoint is understood using both the route and the HTTP method. `GET /api/students/101` and `DELETE /api/students/101` have different meanings even though the route is the same.

### 9.4 HTTP Method

The HTTP method communicates what the client wants to do.

| Method | Typical purpose | Example |
|---|---|---|
| `GET` | Read data | Fetch student details |
| `POST` | Create a resource or submit an operation | Add a student |
| `PUT` | Replace a resource representation | Replace student details |
| `PATCH` | Partially modify a resource | Change a student's course |
| `DELETE` | Delete a resource | Remove a student |

**Important:** `GET` should be safe: it should not intentionally change the server's data. `GET` and `PUT` are defined as idempotent HTTP methods. `DELETE` is also defined as idempotent, meaning repeating the same request should have the same intended final effect, even if the response differs. `POST` is not generally idempotent.

### 9.5 Request Headers

Headers provide metadata about the request.

Examples:

```http
Accept: application/json
Content-Type: application/json
Authorization: Bearer <access-token>
```

- `Accept` tells the server which response media types the client can accept.
- `Content-Type` tells the server the format of the request body.
- `Authorization` carries authentication credentials or a token according to the chosen scheme.

Do not put passwords or secret tokens in public URLs.

### 9.6 Request Body

The request body carries data sent by the client, especially with `POST`, `PUT`, and `PATCH`.

Example JSON body for creating a student:

```json
{
  "name": "Aarav",
  "course": "B.Tech CSE"
}
```

The client usually sends `Content-Type: application/json` when sending JSON.

### 9.7 Response Body

The response body contains the returned data or details about the result.

Example:

```json
{
  "id": 101,
  "name": "Aarav",
  "course": "B.Tech CSE"
}
```

### 9.8 Statelessness

One of REST's key constraints is **stateless communication**: each request must contain the information needed to understand and process it. The server should not depend on stored conversational session state from a previous request to interpret the current request.

For example, a request may include an access token so the server can identify and authorize the caller.

**Stateless does not mean the server cannot store data.** The server can store students, orders, and other application data in a database. It means the server does not rely on client session context being remembered between requests.

### 9.9 Representation

A resource can be represented in a format such as JSON or XML. The server and client exchange representations of the resource rather than transferring the server's internal object directly.

### 9.10 Client-Server Separation

The client handles the user interface, while the server handles business rules and data access. They can evolve separately as long as they continue to follow the API contract.

### 9.11 Cacheability

Responses can indicate whether they may be cached. Appropriate caching can reduce network traffic and improve performance. Sensitive or frequently changing data needs careful cache-control settings.

### 9.12 Layered System

A client may communicate through intermediaries such as gateways, load balancers, proxies, or security layers without needing to understand every layer.

---

## 10. HTTP Status Codes in REST APIs

An HTTP status code tells the client the general outcome of its request.

| Status code | Meaning | Example |
|---|---|---|
| `200 OK` | Request succeeded | Student details returned |
| `201 Created` | A resource was created | New student added |
| `204 No Content` | Successful response with no response body | Student deleted |
| `400 Bad Request` | Request is invalid | Required field is missing or malformed |
| `401 Unauthorized` | Valid authentication is missing or failed | No valid access token |
| `403 Forbidden` | Caller is not allowed to perform the action | Student cannot access an admin operation |
| `404 Not Found` | Requested resource does not exist | Student ID 999 not found |
| `405 Method Not Allowed` | Method is not supported for that route | `DELETE` is not allowed there |
| `409 Conflict` | Request conflicts with current resource state | Duplicate unique student identifier |
| `500 Internal Server Error` | Unexpected server-side error | Unhandled server failure |

**Common interview point:** `401` generally relates to authentication; `403` means the server understood the request but refuses to authorize the action.

---

# Part C — REST vs SOAP

## 11. Difference Between SOAP and REST

SOAP and REST are related to web service design, but they are not the same kind of thing: **SOAP is a protocol, while REST is an architectural style.**

| Basis | SOAP | REST |
|---|---|---|
| Type | Messaging protocol | Architectural style |
| Message format | XML with a defined SOAP envelope | Can use JSON, XML, or other representations; JSON is common |
| Transport | Can work over HTTP and other transports | Commonly uses HTTP |
| Interface | Often uses operations defined in a service contract such as WSDL | Commonly uses resource-oriented URIs and HTTP methods |
| Message structure | Standardized envelope, header, and body | Uses HTTP request/response structure |
| Complexity | Often more formal and verbose | Often simpler for web and mobile APIs |
| Error handling | SOAP Fault structure | HTTP status codes and an API-defined error body |
| Typical use | Contract-driven enterprise integration and systems requiring SOAP standards | Web apps, mobile apps, microservices, and public APIs |
| Performance | XML and envelope overhead can be significant, depending on implementation | Often lightweight with JSON, though actual performance depends on design and payload |
| Security | SOAP-related standards can support message-level security | Commonly uses HTTPS plus mechanisms such as OAuth 2.0 or tokens |

### Which one should we use?

- Use **REST** when you need a straightforward HTTP API for a website, mobile application, or service-to-service communication.
- Use **SOAP** when an existing system, integration contract, or required enterprise standard specifically depends on SOAP and its associated standards.

Neither is automatically best for every project. Requirements, interoperability, security, existing infrastructure, and the service contract should guide the choice.

---

# Part D — Building a Simple RESTful Web Service in Java

## 12. Java Technologies Used for REST

Java provides APIs and frameworks for building RESTful web services.

- **JAX-RS:** The former name for Java's standard API for RESTful web services.
- **Jakarta RESTful Web Services:** The current Jakarta EE name for that API.
- **Jersey** and **RESTEasy:** Common implementations of the API.
- **JSON:** A common data format for request and response bodies.
- **Servlet container / Jakarta EE runtime:** The environment that runs the application, depending on the selected implementation and deployment.

**Version note:** Older Java EE examples often use `javax.ws.rs.*`. Newer Jakarta EE examples use `jakarta.ws.rs.*`. Do not mix the namespaces in one project; use the namespace supported by your server and library versions.

The following is a small illustrative example using the `jakarta.ws.rs.*` namespace. It demonstrates the resource class and annotations; it is not a complete standalone project configuration. A JAX-RS implementation and a compatible runtime must be configured before it can run.

## 13. Example: Student Management REST API

We will build a simple API that can return student information.

### Step 1: Create a Student class

```java
public class Student {
    private int id;
    private String name;
    private String course;

    public Student() {
        // Required by many JSON providers
    }

    public Student(int id, String name, String course) {
        this.id = id;
        this.name = name;
        this.course = course;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
```

**Explanation:**
- `Student` represents the data we want to return.
- The fields store the student's ID, name, and course.
- Getters and setters allow a JSON provider to read and populate the object.
- A JSON provider is needed to convert Java objects to JSON and JSON to Java objects.

### Step 2: Create a REST resource

```java
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/students")
public class StudentResource {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent() {
        Student student =
            new Student(101, "Aarav", "B.Tech CSE");

        return Response.ok(student).build();
    }
}
```

This is a simplified teaching example. In a complete implementation, the `id` from the route should be accepted as a method parameter, and the student should normally be fetched from a service or database. The next example shows route-parameter binding.

### Step 3: Read the ID from the URL

```java
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/students")
public class StudentResource {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudent(@PathParam("id") int id) {
        // Demo only: a real application would look up this ID.
        return new Student(id, "Aarav", "B.Tech CSE");
    }
}
```

### Understanding the annotations

| Annotation | Meaning |
|---|---|
| `@Path("/students")` | Defines the base route for this resource class |
| `@GET` | Maps the method to HTTP GET requests |
| `@Path("/{id}")` | Defines a route segment named `id` |
| `@PathParam("id")` | Reads the `id` value from the URL |
| `@Produces(MediaType.APPLICATION_JSON)` | Declares that the method returns JSON |
| `@POST` | Maps a method to HTTP POST requests |
| `@Consumes(MediaType.APPLICATION_JSON)` | Declares that the method accepts JSON input |

### Step 4: Call the API

If the application is deployed with `/api` as its API base path and the resource path is `/students`, a request might look like this:

```http
GET http://localhost:8080/myapp/api/students/101
Accept: application/json
```

The exact URL depends on the application context path and JAX-RS configuration.

Possible response:

```http
HTTP/1.1 200 OK
Content-Type: application/json
```

```json
{
  "id": 101,
  "name": "Aarav",
  "course": "B.Tech CSE"
}
```

A browser, Postman, `curl`, or a frontend application can send this request. The response format depends on the JSON provider and its configuration.

---

## 14. Example of Creating a Student with POST

A `POST` request commonly sends a JSON object in its request body.

```java
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/students")
public class StudentResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent(Student student) {
        // Teaching demo only:
        // Validate the input and save the student in a real application.

        return Response.status(Response.Status.CREATED)
                       .entity(student)
                       .build();
    }
}
```

Example request:

```http
POST /api/students
Content-Type: application/json
Accept: application/json
```

Request body:

```json
{
  "id": 102,
  "name": "Diya",
  "course": "B.Tech IT"
}
```

Possible response: `201 Created`, with the returned student representation.

**Important:** This demonstration returns the submitted object but does not save it to a database. A real application must validate the input, persist the student, and handle errors. A production API would normally generate or validate the ID on the server rather than blindly trusting a client-supplied ID.

---

## 15. How REST Annotations Connect to HTTP

```text
Client Request
      |
      v
GET /api/students/101
      |
      v
JAX-RS Runtime
      |
      | Matches @Path("/students")
      | Matches @Path("/{id}")
      | Matches @GET
      v
getStudent(@PathParam("id") int id)
      |
      v
Java object: Student
      |
      v
JSON Provider converts object to JSON
      |
      v
HTTP Response: 200 OK + JSON
```

This is the general flow. The JAX-RS runtime handles routing and delegates to the matching Java method. A configured provider converts the returned object to the response representation.

---

# Part E — REST API Best Practices

## 16. Best Practices for Designing REST APIs

1. **Use meaningful resource names.** Prefer `/students` and `/students/101`.
2. **Use HTTP methods correctly.** Use `GET` to read and `POST` to create, according to the API design.
3. **Return meaningful status codes.** Do not return `200 OK` for every possible outcome.
4. **Validate input.** Reject malformed data and invalid field values.
5. **Handle errors clearly.** Provide a consistent error response without exposing internal stack traces.
6. **Secure the API.** Use HTTPS and appropriate authentication and authorization.
7. **Avoid exposing secrets.** Never include passwords, private keys, or secret tokens in URLs or logs.
8. **Use pagination for large collections.** Avoid returning thousands of records in one response.
9. **Document the API.** Explain routes, methods, parameters, request bodies, response formats, and errors.
10. **Keep the API consistent.** Use consistent naming, field formats, and error structures.
11. **Think about versioning.** If breaking changes are necessary, provide a planned migration path, such as a versioned route.
12. **Test success and failure cases.** Check missing records, invalid input, unauthorized requests, and server failures.

### Example of a consistent error response

```json
{
  "error": "STUDENT_NOT_FOUND",
  "message": "No student exists with ID 999"
}
```

This is an example application-defined error format, not a universal REST standard.

## 17. REST API vs. Database

A REST API is **not a database**. It is an interface through which a client can request operations or data.

```text
+----------------+
| Web / Mobile   |
| Client         |
+----------------+
        |
        | HTTP request
        v
+----------------+
| REST API       |
| Java Backend   |
+----------------+
        |
        | Query / update
        v
+----------------+
| Database       |
| Student Records|
+----------------+
```

The API applies business rules and access controls before interacting with the database. Clients should generally not connect directly to a production database.

## 18. Common Misunderstandings

- **REST is not a programming language.** It is an architectural style.
- **REST is not the same as JSON.** JSON is a common representation format, but REST APIs can use other formats.
- **REST is not a protocol like SOAP.** SOAP is a protocol; REST is a set of architectural constraints.
- **Every HTTP API is not automatically RESTful.** An API can use HTTP without following REST's architectural constraints.
- **Stateless does not mean no database.** The server can store application data; it should not depend on prior client-session context to understand each request.
- **A URL alone does not define the operation.** The HTTP method and route together determine the requested action.
- **`POST` does not always mean “create a row.”** It can also submit an operation or process data.
- **`PUT` and `PATCH` are not identical.** `PUT` generally replaces a resource representation; `PATCH` applies partial modifications.

---

# Part F — Quick Revision

## 19. Important Terms

| Term | Easy meaning |
|---|---|
| Web service | A network-accessible way for applications to communicate |
| API | A defined interface through which software components interact |
| REST | An architectural style for networked applications |
| SOAP | A messaging protocol that uses structured XML messages |
| Resource | An entity or piece of information managed by an API |
| URI | An identifier for a resource |
| Endpoint | A route and method through which an API operation is accessed |
| HTTP | A protocol used for web communication |
| JSON | A lightweight text format for structured data |
| Stateless | Each request carries the context needed to process it |
| JAX-RS / Jakarta REST | Java APIs for building RESTful web services |
| WSDL | A language for describing web service interfaces, commonly associated with SOAP |

## 20. One-Minute Summary

- A **web service** allows applications to communicate over a network.
- **SOAP** is a protocol that uses a defined XML message structure.
- **REST** is an architectural style that commonly uses HTTP and resource-oriented routes.
- **JSON** is a common format for REST API data, but it is not mandatory.
- `GET` reads, `POST` commonly creates or submits, `PUT` replaces, `PATCH` partially updates, and `DELETE` deletes.
- A REST response includes an HTTP status code and may include a response body.
- In Java, **JAX-RS / Jakarta REST** annotations such as `@Path`, `@GET`, `@POST`, and `@PathParam` help map HTTP requests to Java methods.
- A real API also needs validation, error handling, security, and a configured runtime.

---

# 21. University Examination Questions — 10 Marks

1. **Define web services.** Explain their need, advantages, limitations, and working with a suitable diagram.
2. **Explain SOAP web services.** Describe the SOAP message structure and explain the role of Envelope, Header, Body, and Fault.
3. **What are RESTful web services?** Explain REST architecture, resources, URIs, endpoints, statelessness, and client-server separation with a suitable diagram.
4. **Differentiate SOAP and REST** with respect to message format, interface, transport, complexity, security approach, and use cases.
5. **Explain HTTP methods used in REST APIs.** Discuss `GET`, `POST`, `PUT`, `PATCH`, and `DELETE` with examples.
6. **Explain HTTP status codes in RESTful web services.** Give examples of success, client-error, and server-error responses.
7. **Explain how to develop a simple RESTful web service in Java using JAX-RS / Jakarta REST.** Describe `@Path`, `@GET`, `@POST`, `@PathParam`, `@Produces`, and `@Consumes`.
8. **Explain the complete request-response cycle of a REST API** with a Student Management example and a diagram.
9. **What is JSON?** Explain how JSON is used in REST API request and response bodies with examples.
10. **Discuss best practices for designing secure and maintainable REST APIs.** Include validation, status codes, authentication, error handling, and documentation.

---

## 22. Short Practice Questions

1. What is the difference between an API and a web service?
2. Is REST a protocol or an architectural style?
3. Why is JSON commonly used in REST APIs?
4. What is the purpose of `@Path` in Jakarta REST?
5. What is the difference between `@Produces` and `@Consumes`?
6. When should an API return `201 Created`?
7. What is the difference between `401` and `403`?
8. Why should a `GET` request not change application data?
9. What does statelessness mean in REST?
10. Why should a REST API validate request data?

---

**End of Notes**
