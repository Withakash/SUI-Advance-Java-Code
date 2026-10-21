# Lec 27: Security in JEE

**Course:** Advanced Java\
**Lecture duration:** 50 minutes\
**Main focus:** Understanding security in Java Enterprise applications

------------------------------------------------------------------------

## Learning Outcomes

By the end of this lecture, you should be able to:

-   Explain what security means in a JEE/Jakarta EE web application.
-   Identify common security risks in web applications.
-   Differentiate authentication from authorization.
-   Explain HTTPS, SSL/TLS, encryption, and decryption.
-   Describe how security fits into a Servlet/JSP application.
-   Apply basic security practices to a student portal or login
    application.

## 1. What Is Security in JEE?

**Security in JEE (Java Enterprise Edition)** means protecting an
enterprise application, its users, its data, and its resources from
unauthorized access, misuse, modification, or disclosure.

JEE is now commonly referred to as **Jakarta EE**. The fundamental
security ideas are the same, although package names and APIs can differ
between older Java EE versions and modern Jakarta EE versions.

Imagine that we build a student portal using Servlets, JSP, JDBC, and
MySQL. Students can log in, view their marks, and update their profiles.
Teachers can upload marks, while an administrator can manage student
accounts.

Without security: - A student might open another student's marks. - A
user might access an admin page by typing its URL. - A password might be
exposed while travelling over an unsafe connection. - An attacker might
submit harmful input to the application. - Private information might be
revealed through error messages or logs.

Security makes sure that the right person can perform only the actions
they are allowed to perform, while data is protected during storage and
transmission.

### A simple definition

> **JEE security is the collection of rules, APIs, configuration, and
> practices used to protect enterprise applications, users, data, and
> resources.**

Security is not just a login page. A login page is only one part of a
complete security system.

## 2. Why Do Enterprise Applications Need Security?

Enterprise applications often handle sensitive information such as
passwords, student records, salary details, customer profiles, payment
information, and business reports.

Security helps to provide:

1.  **Confidentiality** --- only authorized people can see protected
    information.
2.  **Integrity** --- information cannot be changed without permission.
3.  **Availability** --- legitimate users can access the service when
    needed.
4.  **Authentication** --- the application can verify who a user is.
5.  **Authorization** --- the application can decide what that user is
    allowed to do.
6.  **Accountability** --- important actions can be recorded so that
    activity can be investigated.

### The CIA triad

The CIA triad is a common model for thinking about security.

``` text
                 SECURITY
                    |
          +---------+---------+
          |         |         |
   Confidentiality Integrity Availability
          |         |         |
      Protect     Protect    Keep the
      secrecy     accuracy   service usable
```

**Example: Student portal**

  -----------------------------------------------------------------------
  Security goal                       Example
  ----------------------------------- -----------------------------------
  Confidentiality                     A student can see their own marks,
                                      not everyone else's.

  Integrity                           A student cannot change marks
                                      stored in the database.

  Availability                        The portal remains usable for
                                      legitimate users.
  -----------------------------------------------------------------------

Security controls often support more than one goal. For example, access
controls can help protect both confidentiality and integrity.

## 3. What Does JEE Security Protect?

In a web application, security may protect several layers:

``` text
Browser / Mobile App
        |
        | HTTPS: protects data in transit
        v
Web Server / Servlet Container
        |
        | Authentication and access rules
        v
Servlets / JSP / Business Services
        |
        | Validation and permission checks
        v
Database (e.g., MySQL)
        |
        v
Student, marks, login and other records
```

Each layer has a different responsibility.

-   **Transport security:** protects communication between the browser
    and server.
-   **Container security:** the Servlet/Jakarta EE container can
    authenticate users and enforce configured access rules.
-   **Application security:** application code checks ownership, roles,
    and business rules.
-   **Database security:** database accounts and permissions limit what
    the application can do.
-   **Operational security:** configuration, updates, secrets, backups,
    and logs are managed safely.

A secure application needs several layers of protection. HTTPS alone
does not make an application secure.

## 4. Common Security Threats

### 4.1 Unauthorized access

A person accesses a page, file, or operation without the required
permission.

**Example:** A student opens `/admin/manageStudents`.

**Protection:** Check access on the server for every protected request.
Hiding a link in the UI is not enough.

### 4.2 Weak or stolen passwords

Passwords such as `123456` are easy to guess. Passwords can also be
stolen through phishing or reused passwords from another breached
service.

**Protection:** Use strong password policies where appropriate,
rate-limit login attempts, use multi-factor authentication when
suitable, and store passwords using a dedicated password-hashing
algorithm such as Argon2id, bcrypt, or PBKDF2. **Do not store plaintext
passwords or reversible encrypted passwords.**

### 4.3 SQL injection

SQL injection can occur when untrusted input is combined directly with
SQL text.

**Unsafe example:**

``` java
String sql = "SELECT * FROM users WHERE username = '"
             + username + "'";
```

If input is inserted directly into SQL, specially crafted input may
change the meaning of the query.

**Safer JDBC approach:**

``` java
String sql = "SELECT * FROM users WHERE username = ?";
PreparedStatement ps = connection.prepareStatement(sql);
ps.setString(1, username);

ResultSet rs = ps.executeQuery();
```

A `PreparedStatement` keeps the SQL structure separate from the supplied
value. Use parameterized queries for values and allow-list validation
for dynamic identifiers such as column names.

### 4.4 Cross-site scripting (XSS)

XSS occurs when an application displays untrusted content as executable
browser code.

**Example:** A user enters a script as their profile name, and the
application inserts it into a page without appropriate output encoding.

**Protection:** Encode output for the correct HTML/JavaScript/URL
context, avoid unsafe HTML insertion, validate input where appropriate,
and consider a Content Security Policy as an additional defence. Input
validation alone is not a substitute for output encoding.

### 4.5 Cross-site request forgery (CSRF)

CSRF tricks a logged-in user's browser into sending an unwanted request
to an application where the user is already authenticated.

**Example:** A logged-in administrator is tricked into submitting an
unwanted account-change request.

**Protection:** Use framework/container-supported CSRF protection or
anti-CSRF tokens for state-changing requests. Configure cookies
appropriately, including `SameSite`, `Secure`, and `HttpOnly` where
suitable. `SameSite` is useful but should not be treated as a universal
replacement for CSRF protection.

### 4.6 Session theft or misuse

After login, an application often identifies a user through a session
cookie. If an attacker obtains a valid session identifier, they may be
able to act as that user.

**Protection:** - Use HTTPS. - Set session cookies securely (`Secure`,
`HttpOnly`, and an appropriate `SameSite` policy). - Regenerate the
session identifier after login or privilege changes. - Invalidate the
session on logout. - Apply idle and absolute session timeouts where
appropriate. - Never put session IDs in URLs.

### 4.7 Insecure direct object reference (IDOR)

IDOR is an access-control flaw where changing an identifier allows a
user to access another user's resource.

**Example:** `/marks?studentId=102` is changed to
`/marks?studentId=103`.

**Protection:** The server must verify that the logged-in user is
permitted to access the requested student's record. Do not trust an ID
merely because it came from a URL or form.

### 4.8 Sensitive information exposure

Passwords, database credentials, secret keys, or private records may be
exposed in source code, logs, error pages, or unencrypted communication.

**Protection:** Keep secrets outside source control, limit access, avoid
returning stack traces to users, redact sensitive logs, and use HTTPS.

## 5. Authentication vs Authorization

These are two of the most important concepts in JEE security.

### Authentication: Who are you?

Authentication verifies a user's identity.

Examples: - Username and password - One-time code - Security key -
Multi-factor authentication

### Authorization: What are you allowed to do?

Authorization decides whether an authenticated user can perform a
particular action or access a resource.

Examples: - A student can view their own profile. - A teacher can enter
marks for assigned classes. - An administrator can manage user accounts.

### Analogy: College building

-   Showing a valid college ID at the entrance is like
    **authentication**.
-   Being allowed into the examination office but not the server room is
    like **authorization**.

### Comparison

  -----------------------------------------------------------------------
  Authentication                      Authorization
  ----------------------------------- -----------------------------------
  Verifies identity                   Checks permissions

  Usually happens before access       Happens whenever a protected
  decisions                           resource or action is requested

  Example: verify login credentials   Example: check whether user has the
                                      `ADMIN` role

  Answers: "Who are you?"             Answers: "What may you do?"
  -----------------------------------------------------------------------

A user may be authenticated but still not be authorized to perform a
particular action.

## 6. How Security Fits into a Servlet/JSP Application

Consider a student portal with these URLs:

  URL                  Intended permission
  -------------------- ---------------------------------
  `/login`             Public
  `/student/profile`   Logged-in student
  `/student/marks`     Student viewing their own marks
  `/teacher/marks`     Authorized teacher
  `/admin/users`       Administrator

A simplified request flow:

``` text
User requests a protected URL
              |
              v
Is the user authenticated?
       /             \
     No               Yes
     |                 |
 Redirect to      Check permission
 login page       for this action
                       |
                 +-----+-----+
                 |           |
              Allowed      Denied
                 |           |
          Run operation   Return 403
```

This is a conceptual flow. A real application must also handle public
resources, expired sessions, errors, and its chosen authentication
mechanism.

### Important rule

**Security checks must run on the server.** A hidden button, disabled
menu item, or JavaScript check does not prevent someone from sending a
request directly.

For example, hiding the Admin button from students is good UI design,
but the server must still deny a student's request to `/admin/users`.

## 7. Authentication and Authorization in Jakarta EE

Jakarta EE supports security features through the platform and the
web/application container. Exact configuration depends on the Jakarta EE
version, server, and authentication system being used.

Common concepts include:

-   **Identity / Principal:** represents the authenticated user.
-   **Roles:** named groups of permissions, such as `STUDENT`,
    `TEACHER`, and `ADMIN`.
-   **Security constraints:** rules that restrict access to URL patterns
    and HTTP methods.
-   **Programmatic checks:** code can check the current caller's
    identity or role when needed.
-   **Declarative security:** access rules are described through
    annotations or deployment configuration and enforced by a compatible
    container.

### Example: Declarative security with annotations

The following is a conceptual example for a Jakarta EE web application
running in a compatible container:

``` java
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/admin")
public class AdminResource {

    @GET
    @RolesAllowed("ADMIN")
    public String dashboard() {
        return "Welcome, administrator!";
    }
}
```

This example uses **Jakarta REST (JAX-RS)**, not a Servlet. It
illustrates role-based declarative security: a compatible runtime must
be configured with an identity and roles for the rule to work.

For a Servlet-only application, URL restrictions can also be configured
using the deployment descriptor (`web.xml`) or supported Servlet
security annotations. Do not assume that adding a role annotation alone
configures user accounts or a login system.

### Example: Checking a role in a Servlet

``` java
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        if (!request.isUserInRole("ADMIN")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        response.getWriter().println("Admin dashboard");
    }
}
```

**Important:** `isUserInRole("ADMIN")` checks the role associated with
the current container-authenticated user. It does not implement login by
itself. The application server must have authentication and role mapping
configured.

For a student record, also check record ownership. A user having the
`STUDENT` role does not automatically mean they may view every student's
marks.

## 8. Declarative vs Programmatic Security

### Declarative security

Security rules are specified outside the main business logic, for
example through supported annotations or `web.xml`. The container
enforces the configured rules.

**Advantages:** - Centralized and easier to review. - Less repeated
permission-checking code. - Suitable for standard URL and role
restrictions.

### Programmatic security

Application code explicitly checks identity or permissions using APIs
such as `request.isUserInRole(...)`, or by consulting
application-specific authorization services.

**Advantages:** - Useful for decisions that depend on business data. -
Can check ownership, such as whether a student owns the requested
record. - Can support complex rules that URL-level constraints cannot
fully express.

**Practical guidance:** Use declarative security for broad access rules
and programmatic checks for business-specific authorization. Both may be
needed.

## 9. HTTPS, SSL, and TLS

### What is HTTPS?

HTTPS means **HTTP Secure**. It uses HTTP over a TLS-protected
connection to help protect communication between a client and server.

For example:

``` text
Without HTTPS:
Browser  -------- readable/interceptable traffic --------> Server

With HTTPS:
Browser  ===== encrypted TLS connection =================> Server
```

HTTPS provides: - **Confidentiality:** network observers generally
cannot read the protected application data. - **Integrity:** tampering
with protected traffic can be detected. - **Server authentication:** the
browser can verify the server's certificate, provided certificate
validation succeeds.

HTTPS does not prove that a website is honest, and it does not prevent
every application-level vulnerability.

### What are SSL and TLS?

-   **SSL (Secure Sockets Layer)** is an older protocol and is obsolete.
-   **TLS (Transport Layer Security)** is its modern successor.
-   People sometimes say "SSL certificate," but modern HTTPS uses TLS.

Use current, securely configured TLS versions, generally TLS 1.2 or TLS
1.3, and obtain certificates from a trusted certificate authority or an
appropriate private PKI for internal systems.

### What happens when HTTPS is used?

At a high level:

1.  The browser connects to the server.
2.  The server presents its digital certificate.
3.  The browser checks that the certificate is valid for the requested
    hostname and chains to a trusted authority.
4.  The client and server negotiate cryptographic settings and establish
    shared session keys.
5.  Application data is transmitted through the protected connection.

This is a simplified description; the exact handshake differs by TLS
version.

### HTTPS in a JEE application

A Servlet/JSP application commonly runs in a container such as Apache
Tomcat. TLS may be configured directly on the container or terminated at
a reverse proxy/load balancer.

``` text
Browser
   |
   | HTTPS / TLS
   v
Tomcat or Reverse Proxy
   |
   | Application request
   v
Servlet / JSP / Business Logic
   |
   v
Database
```

If TLS terminates at a proxy, the connection from the proxy to the
application server must also be designed and protected appropriately for
the deployment. Configure trusted proxy headers carefully.

**HTTPS is not the same as authentication or authorization.** HTTPS
protects the connection; login identifies a user; authorization checks
their permissions.

## 10. Encryption and Decryption

### Encryption

Encryption transforms readable information (**plaintext**) into
unreadable-looking information (**ciphertext**) using a cryptographic
algorithm and a key.

### Decryption

Decryption transforms ciphertext back into readable plaintext using the
appropriate key.

``` text
Plaintext + Key
       |
       v
  Encryption
       |
       v
   Ciphertext
       |
       | Decryption + appropriate key
       v
    Plaintext
```

Example concept:

``` text
Plaintext:  "Hello"
Encryption: algorithm + key
Ciphertext: "unreadable encrypted data"
Decryption: appropriate key
Plaintext:  "Hello"
```

The ciphertext shown here is illustrative, not the output of a real
algorithm.

### Symmetric encryption

The same secret key is used to encrypt and decrypt data.

-   Efficient for large amounts of data.
-   The key must be shared and stored safely.
-   Example: AES-GCM when configured correctly.

### Asymmetric cryptography

Uses a mathematically related public/private key pair. Different
algorithms support different purposes, such as digital signatures or key
establishment.

-   The public key may be shared.
-   The private key must be protected.
-   Examples: RSA and elliptic-curve cryptography, depending on the
    operation and configuration.

Modern TLS uses a combination of cryptographic techniques to
authenticate the server and establish keys, then protects application
traffic using symmetric authenticated encryption.

### Hashing is not encryption

A cryptographic hash produces a fixed-length digest. It is not designed
to be decrypted back into the original input.

Password storage should normally use a **password-hashing function**
such as Argon2id, bcrypt, or PBKDF2, with appropriate parameters and
salts---not plain SHA-256 and not reversible encryption.

### Encryption vs hashing

  -----------------------------------------------------------------------
  Encryption                          Hashing
  ----------------------------------- -----------------------------------
  Designed to be reversible with the  Designed to be one-way
  appropriate key                     

  Used to protect data that must      Used for integrity checks and other
  later be recovered                  digest-based tasks

  Example: encrypting stored          Example: storing password verifiers
  sensitive data where recovery is    with a password-hashing algorithm
  required                            
  -----------------------------------------------------------------------

For passwords, do not decrypt a stored password to compare it. Verify
the entered password against the stored password hash using the
password-hashing library.

## 11. Password Security in a JEE Application

A common beginner mistake is storing passwords directly in a database:

  user_id   username    password
  --------- ----------- ---------------
  1         student01   mypassword123

This is unsafe because anyone who gains access to the table can read the
passwords.

Instead, store a password hash generated by a dedicated password-hashing
library. The stored value should include the parameters and salt
information required for verification, as supported by the chosen
format.

Conceptual flow:

``` text
User enters password
        |
        v
Password verification library
        |
        v
Compare against stored password hash
        |
     +--+--+
     |     |
  Match   No match
     |     |
 Login    Reject login
```

Do not invent your own password-hashing algorithm. Use a maintained
library, protect password-reset flows, and avoid revealing whether a
particular username exists unless the product's threat model explicitly
allows it.

## 12. Sessions, Cookies, and Logout

Servlet applications often use an HTTP session to remember that a user
has logged in.

``` java
// After successful authentication:
request.getSession(true).setAttribute("userId", userId);
```

This is only an illustration of storing a user identifier after
authentication. It does not implement secure authentication by itself.

For logout:

``` java
request.getSession(false); // May be null if no session exists
```

A complete logout implementation should invalidate the existing session
when appropriate:

``` java
var session = request.getSession(false);

if (session != null) {
    session.invalidate();
}

response.sendRedirect(request.getContextPath() + "/login");
```

Additional session guidance: - Regenerate the session ID after
successful login or privilege changes, for example with
`request.changeSessionId()` where supported. - Configure the session
cookie with `Secure`, `HttpOnly`, and a suitable `SameSite` policy. - Do
not store passwords in session attributes. - Protect state-changing
actions against CSRF. - Expire sessions appropriately. - Do not rely
only on a client-side logout button; invalidate the server-side session.

## 13. A Secure Login Flow: Big Picture

A simplified login and authorization flow:

``` text
1. User opens login page
          |
2. User submits credentials over HTTPS
          |
3. Server validates the request
          |
4. Authentication mechanism verifies credentials
          |
5. Session is created or renewed securely
          |
6. User requests a protected page
          |
7. Server checks role and resource ownership
          |
     +----+----+
     |         |
  Allowed    Denied
     |         |
 Show data   403 / suitable
             access response
```

A real application also needs password-reset security, rate limiting,
session expiration, secure cookies, CSRF controls, logging, error
handling, and secure database access.

## 14. Best Practices Checklist

Before deploying a JEE/Jakarta EE application, review these practices:

-   [ ] Use HTTPS with properly configured TLS.
-   [ ] Use a maintained authentication mechanism.
-   [ ] Store passwords using a dedicated password-hashing algorithm.
-   [ ] Check authorization on the server for every protected action.
-   [ ] Verify ownership when users access records by ID.
-   [ ] Use `PreparedStatement` or another parameterized database API.
-   [ ] Encode untrusted output to prevent XSS.
-   [ ] Protect state-changing requests against CSRF.
-   [ ] Use secure session-cookie settings and invalidate sessions on
    logout.
-   [ ] Keep database credentials and secrets out of source control.
-   [ ] Apply least privilege to application and database accounts.
-   [ ] Avoid displaying stack traces or secrets to end users.
-   [ ] Keep the JDK, server, dependencies, and security libraries
    updated.
-   [ ] Log relevant security events without logging passwords, session
    IDs, or other sensitive secrets.

## 15. Mini Practical: Protect an Admin Servlet

**Goal:** Allow only a container-authenticated user with the `ADMIN`
role to access an admin page.

1.  Create a Servlet mapped to `/admin/dashboard`.
2.  Configure authentication in the container or application deployment.
3.  Map the authenticated administrator to the `ADMIN` role.
4.  Check `request.isUserInRole("ADMIN")` before returning protected
    content, or configure an appropriate declarative constraint.
5.  Test with an administrator, an authenticated student, and an
    unauthenticated visitor.

Expected behavior:

  -----------------------------------------------------------------------
  Request                             Expected result
  ----------------------------------- -----------------------------------
  Administrator opens admin dashboard Access allowed

  Student opens admin dashboard       Access denied

  Unauthenticated visitor opens       Login challenge/redirect or other
  protected dashboard                 configured authentication response
  -----------------------------------------------------------------------

The exact unauthenticated response depends on the configured
authentication mechanism. A role check alone does not configure users,
passwords, or the login page.

## 16. Common Misunderstandings

**"If I hide the admin button, the page is secure."**\
No. A user can send the URL directly. Enforce access on the server.

**"HTTPS means the application is completely secure."**\
No. HTTPS protects data in transit but does not prevent SQL injection,
broken authorization, weak passwords, or insecure business logic.

**"Authentication and authorization are the same."**\
No. Authentication verifies identity; authorization decides permissions.

**"SSL is the modern protocol for HTTPS."**\
SSL is obsolete. Modern HTTPS uses TLS.

**"Passwords should be encrypted so we can decrypt them later."**\
Normally, passwords should be stored using a dedicated password-hashing
algorithm and verified, not decrypted.

**"A user with the STUDENT role can read any student record."**\
Not necessarily. Role checks may need to be combined with
record-ownership checks.

## 17. Quick Revision

-   **JEE security:** Protects application resources, users, and data.
-   **Confidentiality:** Prevents unauthorized disclosure.
-   **Integrity:** Prevents unauthorized modification.
-   **Availability:** Keeps services usable.
-   **Authentication:** Who are you?
-   **Authorization:** What are you allowed to do?
-   **HTTPS:** HTTP protected by TLS.
-   **TLS:** Protects data in transit and supports server
    authentication.
-   **Encryption:** Reversible protection using a key.
-   **Hashing:** One-way digest operation; password storage requires a
    dedicated password-hashing function.
-   **Declarative security:** Rules configured through supported
    annotations or deployment configuration.
-   **Programmatic security:** Permission checks performed by
    application code.
-   **Session security:** Protects the session that represents a
    logged-in user.
-   **Least privilege:** Give users and services only the permissions
    they need.

## 18. Practice Questions

### Short-answer questions

1.  What is security in JEE? Why is it needed?
2.  Explain the CIA triad with a student-portal example.
3.  Differentiate authentication and authorization.
4.  What is HTTPS? Why is TLS used?
5.  Differentiate encryption and hashing.
6.  What is declarative security? How is it different from programmatic
    security?
7.  Why is hiding a protected link not enough to secure a web
    application?
8.  What is session hijacking, and how can session security be improved?

### Application-based questions

1.  A student changes `studentId=101` to `studentId=102` in a URL and
    sees another student's marks. Identify the security problem and
    explain the correct server-side check.
2.  An application builds SQL by concatenating a username into a query.
    Explain the risk and rewrite it using a `PreparedStatement`.
3.  A website uses HTTPS but allows every logged-in user to open
    `/admin`. Explain why the application is still insecure.
4.  A developer stores passwords in plain text in MySQL. Recommend a
    safer password-storage approach.
5.  Design the access rules for `/student`, `/teacher`, and `/admin`
    pages using the roles `STUDENT`, `TEACHER`, and `ADMIN`. Explain
    which checks must happen at the server.

### Practical assignment

Build a small **Student Portal Security Demo** using Servlets, JSP, and
JDBC.

Requirements: - Create login and logout flows. - Define the roles
`STUDENT` and `ADMIN`. - Protect the admin page on the server. - Ensure
students can view only their own profile. - Use parameterized SQL
queries. - Do not store plaintext passwords. - Document how HTTPS/TLS
would be configured in the deployment environment. - Test and document
both allowed and denied access attempts.

**Submission:** Source code, a short README explaining the security
design, and a table of test cases with expected and actual results.

------------------------------------------------------------------------

## Final takeaway

**Security in JEE is not one feature or one API. It is a set of
coordinated protections.** Authentication identifies the user,
authorization restricts what they can do, TLS protects communication,
password hashing protects stored password verifiers, and secure
application/database practices protect data and business operations.
