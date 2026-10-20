# Lecture 27 — Security in JEE (Jakarta EE)

> **Main focus:** Security in JEE  
> **Duration:** 50 minutes  
> **Level:** Beginner-friendly Advanced Java notes

---

## 1. Learning Objectives

By the end of this lecture, students should be able to:

- Explain why security is required in Java Enterprise applications.
- Understand the difference between authentication and authorization.
- Explain how Java EE / Jakarta EE provides application security.
- Describe HTTPS, TLS, encryption, and decryption.
- Identify common security risks and basic prevention methods.

## 2. What Is Security in JEE?

**Security in JEE (Java Enterprise Edition), now Jakarta EE,** means protecting enterprise applications, users, and data from unauthorized access, misuse, modification, or disclosure.

A JEE application may include:
- A web application used by students or employees.
- Servlets and JSP pages.
- REST APIs.
- Business logic and services.
- A database containing sensitive information.

Security must protect each relevant part of the application, not just the login page.

### Real-life example: College portal

A college portal may have three types of users:

- **Student:** Can view their own attendance and marks.
- **Faculty:** Can manage attendance and marks for assigned classes.
- **Administrator:** Can manage users and system settings.

The application must verify who the user is and what that user is allowed to do.

### Security flow diagram

```text
             User / Client
                  |
                  | HTTPS request
                  v
        +----------------------+
        | Web Application      |
        | Servlet / REST API   |
        +----------------------+
                  |
                  v
        +----------------------+
        | Authentication       |
        | Who are you?          |
        +----------------------+
                  |
          Identity verified?
             /          \
           No            Yes
           |              |
       Reject /       +----------------------+
       Login error    | Authorization        |
                      | What may you do?     |
                      +----------------------+
                                |
                      Permission granted?
                         /            \
                       No              Yes
                       |                |
                   HTTP 403        Process request
                                        |
                                        v
                                  Protected data
```

**Key point:** Logging in is not enough. The application must also check whether the user is allowed to perform the requested action.

---

## 3. Why Is Security Important?

Security helps protect:

1. **Confidentiality:** Only authorized people can view protected information.
2. **Integrity:** Data cannot be changed by unauthorized people.
3. **Availability:** Authorized users can access the system when needed.
4. **Authentication:** The system can verify a user's identity.
5. **Accountability:** Actions can be recorded in appropriate audit logs.

These are commonly discussed as the **CIA triad** plus identity and accountability controls.

### Example

Suppose a student changes a URL from:

```text
/marks/student/101
```

to:

```text
/marks/student/102
```

If the server returns another student's marks without checking permissions, the application has an authorization vulnerability. Hiding the URL or button in the user interface does not fix it. The server must enforce access control.

## 4. Common Security Threats in Enterprise Applications

| Threat | Meaning | Basic protection |
|---|---|---|
| Unauthorized access | A user accesses protected features without permission | Authentication and authorization |
| Weak passwords | Passwords can be guessed or stolen | Strong password policy, rate limiting, MFA where appropriate |
| SQL injection | Untrusted input changes the meaning of a database query | Parameterized queries and safe data access |
| Cross-site scripting (XSS) | Attacker-controlled script runs in another user's browser | Context-aware output encoding and safe rendering |
| Cross-site request forgery (CSRF) | A browser is tricked into submitting an unwanted authenticated request | CSRF protection, appropriate cookie settings, and origin checks |
| Session hijacking | Someone obtains and reuses a user's session identifier | HTTPS, secure cookies, session rotation, short and suitable expiry |
| Data interception | Traffic is read or modified in transit | HTTPS using TLS |
| Sensitive data exposure | Secrets or private data are leaked | Minimize collected data, encrypt where appropriate, restrict access, avoid logging secrets |

Security is layered. No single feature can protect an application from every threat.

---

# Part A — Authentication and Authorization

## 5. What Is Authentication?

**Authentication is the process of verifying a user's identity.**

It answers:

> **Who are you?**

Common authentication methods include:
- Username and password.
- One-time password (OTP).
- Multi-factor authentication (MFA).
- Client certificates in suitable systems.
- Tokens issued by a trusted identity provider.

### Example

A student enters their college email and password. The application verifies the credentials. If valid, the user is authenticated.

```text
User submits credentials
          |
          v
Application / Identity Provider
          |
          v
Verify credentials
     /          \
 Invalid        Valid
    |             |
 Reject       Authenticated
```

Passwords should not be stored as plain text. Applications should use a suitable password-hashing scheme with salts, such as Argon2id, bcrypt, or PBKDF2, according to the platform and security requirements. A normal fast hash such as plain SHA-256 is not a suitable password-storage solution by itself.

## 6. What Is Authorization?

**Authorization determines which actions or resources an authenticated user is allowed to access.**

It answers:

> **What are you allowed to do?**

Example permissions in a college portal:

| Action | Student | Faculty | Administrator |
|---|---:|---:|---:|
| View own marks | Yes | As permitted by role / assignment | As permitted by policy |
| Enter marks | No | For assigned classes | As permitted by policy |
| Create user accounts | No | Usually no | Yes |
| Change system settings | No | No | Yes |

The exact permissions depend on the institution's policy.

## 7. Authentication vs Authorization

| Authentication | Authorization |
|---|---|
| Verifies identity | Checks permissions |
| Asks “Who are you?” | Asks “What may you do?” |
| Commonly happens before protected actions | Is checked when a protected resource or operation is requested |
| Example: Validate login credentials | Example: Check whether a faculty member can edit marks for this class |

### Easy example

A college security guard checks a person's ID at the entrance. That is similar to authentication.

The person is then allowed into specific rooms based on their role. That is similar to authorization.

**Important:** A user can be authenticated but still not be authorized to access a particular resource.

## 8. How JEE / Jakarta EE Supports Security

Java EE and Jakarta EE provide standard mechanisms that can be integrated with the application server or identity system.

Common concepts include:

- **Declarative security:** Configure access rules using annotations or deployment configuration instead of writing every access check manually.
- **Programmatic security:** Check the caller's identity or roles from application code where necessary.
- **Roles:** Named groups of permissions, such as `STUDENT`, `FACULTY`, and `ADMIN`.
- **Authentication mechanism:** The mechanism used to establish the caller's identity.
- **Transport security:** Require secure HTTPS communication.
- **Container-managed security:** The runtime or application server enforces configured security constraints.

Exact configuration and APIs depend on the Java EE / Jakarta EE version and the server in use.

### Example: Role-based security in a Jakarta REST resource

```java
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/admin")
public class AdminResource {

    @GET
    @Path("/report")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("ADMIN")
    public String getReport() {
        return "Confidential admin report";
    }
}
```

### Explanation

- `@Path("/admin")` defines the resource path.
- `@GET` maps the method to an HTTP GET request.
- `@RolesAllowed("ADMIN")` restricts access to callers with the `ADMIN` role.
- The application server or configured Jakarta REST security integration must be set up to authenticate callers and enforce role checks.

**This annotation does not create an administrator account or configure login by itself.** Authentication, role mapping, and server security configuration are still required.

For servlet applications, similar role-based constraints can be configured using servlet security annotations or deployment descriptors, depending on the environment.

---

# Part B — HTTPS and SSL/TLS

## 9. What Is HTTPS?

**HTTPS is HTTP communication protected by TLS (Transport Layer Security).**

It helps provide:
- **Confidentiality:** Prevents outsiders from reading protected traffic in transit.
- **Integrity:** Helps detect unauthorized modification of traffic.
- **Server authentication:** Certificates help the client verify that it is communicating with the intended server.

HTTPS does not automatically prove that a website's business practices are trustworthy, and it does not prevent every application-level attack.

## 10. What Are SSL and TLS?

- **SSL (Secure Sockets Layer)** is an older security protocol and is obsolete.
- **TLS (Transport Layer Security)** is its modern successor and is what current HTTPS deployments should use.

People sometimes say “SSL certificate,” but modern HTTPS uses TLS. A server certificate is part of the mechanism used to authenticate the server and establish secure communication.

### HTTP vs HTTPS

| HTTP | HTTPS |
|---|---|
| Application data is not protected by TLS | HTTP is carried over TLS |
| Traffic can be more vulnerable to interception or modification | TLS protects traffic in transit |
| Does not provide TLS-based server authentication | Certificate validation helps authenticate the server |
| Should not be used for sensitive production traffic over untrusted networks | Standard choice for production web traffic |

### HTTPS communication diagram

```text
+----------------+                         +----------------+
| Browser / App  |                         | Web Server     |
+----------------+                         +----------------+
        |                                          |
        | 1. Connect using HTTPS                   |
        |----------------------------------------->|
        |                                          |
        | 2. TLS handshake and certificate checks  |
        |<---------------------------------------->|
        |                                          |
        | 3. Encrypted HTTP request                |
        |=========================================>|
        |                                          |
        | 4. Encrypted HTTP response               |
        |<=========================================|
```

The TLS handshake establishes cryptographic parameters and keys. The exact handshake steps depend on the TLS version and configuration.

## 11. How Does a Certificate Help?

A TLS certificate binds a server identity (such as a domain name) to a public key, with that binding vouched for by a certificate authority or another configured trust mechanism.

Simplified process:

1. The client connects to a server using HTTPS.
2. The server presents its certificate.
3. The client checks whether the certificate is trusted, valid for the hostname, within its validity period, and otherwise acceptable.
4. The TLS handshake establishes secure session keys.
5. HTTP requests and responses are protected using the negotiated cryptographic connection.

**Do not assume the padlock means a site is honest.** It indicates that the connection has met the browser's TLS checks; phishing sites can also obtain valid certificates.

## 12. Why HTTPS Matters in JEE Applications

A JEE / Jakarta EE application may transmit:
- Login credentials.
- Session cookies.
- Student or employee records.
- Payment-related information.
- API access tokens.

Without TLS, traffic on an untrusted network could be exposed or altered. HTTPS is therefore essential for normal production web applications, including login pages and APIs.

### Practical security reminders

- Redirect HTTP to HTTPS where appropriate.
- Use current TLS versions and secure server configuration.
- Mark session cookies `Secure` and `HttpOnly`; choose an appropriate `SameSite` setting.
- Do not disable certificate validation in production clients.
- Avoid placing tokens or passwords in query strings.
- Remember that HTTPS protects traffic in transit; it does not replace authorization or secure database access.

---

# Part C — Encryption and Decryption

## 13. What Is Encryption?

**Encryption converts readable information (plaintext) into an unreadable form (ciphertext) using a cryptographic algorithm and key.**

Its purpose is to prevent people without the required key from reading protected information.

## 14. What Is Decryption?

**Decryption converts ciphertext back into readable plaintext using the appropriate key and algorithm.**

### Basic diagram

```text
Plaintext
   |
   | Encryption + key
   v
Ciphertext (unreadable data)
   |
   | Decryption + appropriate key
   v
Plaintext (original data)
```

Example:

```text
Original message:  Hello Student
Encrypted data:    [ciphertext — appears unreadable]
Decrypted message: Hello Student
```

The ciphertext shown here is only a placeholder; actual output depends on the algorithm, key, nonce/IV, and other parameters.

## 15. Types of Encryption

### 15.1 Symmetric Encryption

Symmetric encryption uses the **same secret key** to encrypt and decrypt data.

```text
                 Shared secret key
                /                  \
Plaintext -> Encryption         Decryption <- Ciphertext
                \                  /
                 Same key
```

Example algorithm: **AES**.

**Advantages**
- Fast and suitable for encrypting large amounts of data.
- Commonly used to protect data after secure session keys have been established.

**Challenge**
- The secret key must be shared and stored securely.

### 15.2 Asymmetric Cryptography

Asymmetric cryptography uses a related **public key and private key**.

Different algorithms support different operations. For example, public-key encryption can use a public key for encryption and the matching private key for decryption; digital signatures use a private key to sign and a public key to verify.

```text
Public key  -> Can be shared
Private key -> Must be protected
```

Example algorithm family: RSA. Elliptic-curve cryptography is also widely used for public-key operations.

**Important:** A public key is not a password, and a private key must never be exposed.

### 15.3 How HTTPS Uses Cryptography

HTTPS does not usually encrypt every HTTP message directly using public-key encryption. Modern TLS uses asymmetric cryptography and certificates as part of authentication and key establishment, then uses symmetric cryptography to protect the actual traffic efficiently.

```text
Certificate / public-key cryptography
             |
             v
Authenticate server and establish session keys
             |
             v
Symmetric encryption protects HTTP traffic
```

This is a simplified overview; modern TLS key establishment and authentication details vary by configuration and TLS version.

## 16. Encryption Is Not the Same as Hashing

| Encryption | Hashing |
|---|---|
| Designed to be reversible with the appropriate key | Designed as a one-way transformation |
| Used to protect information that must later be recovered | Used for integrity checks and other purposes |
| Example: AES | Example: SHA-256 |
| Decryption can recover the original plaintext | A secure hash is not decrypted to recover the original input |

For password storage, use a dedicated password-hashing algorithm such as Argon2id, bcrypt, or PBKDF2 with appropriate parameters. Do not store plain-text passwords, and do not treat ordinary fast hashes as sufficient password protection.

---

# Part D — Putting It All Together

## 17. Example: Secure Student Portal

Suppose a student opens a college portal and requests their marks.

1. The browser connects to the application using **HTTPS**.
2. The student authenticates using the configured login mechanism.
3. The server establishes or validates the user's authenticated session or token.
4. The student requests their marks.
5. The server performs **authorization** checks to ensure the requested marks belong to that student or that the caller has an explicitly permitted role.
6. The server retrieves the permitted data.
7. The response is sent over the protected TLS connection.
8. Appropriate audit events may be recorded without logging passwords, tokens, or unnecessary sensitive data.

```text
Student Browser
      |
      | HTTPS / TLS
      v
JEE / Jakarta EE Application
      |
      +--> Authentication: verify identity
      |
      +--> Authorization: verify permissions
      |
      +--> Business logic and validation
      |
      v
Database: retrieve only permitted data
      |
      v
Response sent through HTTPS
```

This example shows that security is a combination of controls. Encryption alone cannot decide whether a student is allowed to see another student's marks.

## 18. Common Misunderstandings

- **Authentication is not authorization.** A valid login does not grant every permission.
- **HTTPS is not the same as encryption at rest.** HTTPS protects traffic in transit; database and file storage need their own protection.
- **SSL is obsolete.** Current deployments should use properly configured TLS.
- **Hiding a button is not access control.** The server must enforce permissions.
- **A role annotation is not a complete login system.** The runtime must be configured to authenticate callers and map roles.
- **Encryption is not hashing.** They solve different problems.
- **No single security measure is enough.** Secure applications use layered defenses.

---

# 19. Quick Revision Table

| Concept | One-line meaning |
|---|---|
| Security in JEE | Protecting enterprise applications, users, and data |
| Authentication | Verifying who the user is |
| Authorization | Deciding what the user can access |
| Role | A named set of permissions, such as `ADMIN` |
| Declarative security | Defining security rules through configuration or annotations |
| HTTPS | HTTP protected by TLS |
| SSL | Obsolete predecessor to TLS |
| TLS | Protocol that protects data in transit and supports server authentication |
| Encryption | Converting plaintext into ciphertext using a key |
| Decryption | Recovering plaintext from ciphertext using the appropriate key |
| Symmetric encryption | Uses the same secret key for encryption and decryption |
| Asymmetric cryptography | Uses related public and private keys for supported cryptographic operations |
| Hashing | One-way transformation, often used for integrity and password verification |

---

# 20. University Examination Questions — 10 Marks

1. **Explain Security in JEE / Jakarta EE.** Discuss its importance, common threats, and security mechanisms with a suitable diagram.
2. **Differentiate authentication and authorization.** Explain both concepts using a College Management System example.
3. **Explain declarative and programmatic security in JEE / Jakarta EE.** Discuss role-based access control and the purpose of `@RolesAllowed`.
4. **What is HTTPS?** Explain the role of TLS, server certificates, and secure communication with a diagram.
5. **Differentiate HTTP and HTTPS.** Explain why HTTPS is required for login pages and REST APIs.
6. **Define encryption and decryption.** Explain symmetric and asymmetric cryptography with examples and diagrams.
7. **Differentiate encryption and hashing.** Explain why passwords should be stored using a dedicated password-hashing algorithm.
8. **Explain how a secure JEE application processes a request**, from HTTPS connection and authentication to authorization and data retrieval.
9. **Discuss common security threats in enterprise Java applications** and explain suitable prevention methods.
10. **Explain the CIA triad**—confidentiality, integrity, and availability—with examples from a college portal.

---

# 21. Short Practice Questions

1. What is security in JEE?
2. What is the difference between authentication and authorization?
3. What does `@RolesAllowed("ADMIN")` do?
4. What is the full form of HTTPS?
5. Why is SSL considered obsolete?
6. What is the purpose of a TLS certificate?
7. What is the difference between symmetric and asymmetric cryptography?
8. What is ciphertext?
9. Why should passwords not be stored as plain text?
10. Does HTTPS alone prevent unauthorized access? Explain briefly.

---

**End of Lecture 27 Notes**
