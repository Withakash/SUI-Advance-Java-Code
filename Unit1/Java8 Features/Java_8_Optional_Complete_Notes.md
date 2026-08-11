# Java 8 Optional — Complete Practical Notes

## 1. What is Optional?

`Optional<T>` was introduced in **Java 8**.

It is a container object that represents:

```text
A value
   OR
No value
```

Instead of directly using `null`, we can use `Optional` to explicitly represent the possibility that a value may not exist.

Example:

```java
Optional<String> name = Optional.of("Akash");
```

or:

```java
Optional<String> name = Optional.empty();
```

### Simple definition

> `Optional<T>` is a Java 8 container that represents either a value of type `T` or no value.

---

# 2. Why Do We Need Optional?

Consider:

```java
String name = null;

System.out.println(name.length());
```

This causes:

```text
NullPointerException
```

The problem is that `name` doesn't contain a value.

With Optional:

```java
Optional<String> name = Optional.empty();
```

Now the code explicitly represents:

```text
name
 ↓
may contain String
      OR
may be empty
```

This encourages explicit handling of missing values.

---

# 3. Optional as a Box

Think of Optional as a box.

When it contains a value:

```text
┌─────────────────┐
│ Optional        │
│                 │
│     "Akash"     │
│                 │
└─────────────────┘
```

When it doesn't:

```text
┌─────────────────┐
│ Optional        │
│                 │
│     EMPTY       │
│                 │
└─────────────────┘
```

Mental model:

```text
Optional<T>
     ↓
┌───────────────┐
│ value         │
│      OR       │
│ empty         │
└───────────────┘
```

---

# 4. Creating Optional

## Optional.of()

Use `of()` when you know the value is not null.

```java
Optional<String> name =
        Optional.of("Akash");
```

The Optional contains:

```text
"Akash"
```

### Important

If the value is `null`:

```java
String name = null;

Optional<String> result =
        Optional.of(name);
```

This throws:

```text
NullPointerException
```

---

# 5. Optional.empty()

Creates an empty Optional.

```java
Optional<String> name =
        Optional.empty();
```

Conceptually:

```text
Optional
   ↓
EMPTY
```

---

# 6. Optional.ofNullable()

Use `ofNullable()` when the value may be null.

```java
String name = null;

Optional<String> result =
        Optional.ofNullable(name);
```

Result:

```text
Optional.empty()
```

If:

```java
String name = "Akash";

Optional<String> result =
        Optional.ofNullable(name);
```

Result:

```text
Optional["Akash"]
```

### Important Difference

```text
Optional.of(value)
        ↓
null → NullPointerException

Optional.ofNullable(value)
        ↓
null → Optional.empty()
```

---

# 7. isPresent()

Checks whether a value exists.

```java
Optional<String> name =
        Optional.of("Akash");

System.out.println(name.isPresent());
```

Output:

```text
true
```

Empty Optional:

```java
Optional<String> name =
        Optional.empty();

System.out.println(name.isPresent());
```

Output:

```text
false
```

---

# 8. ifPresent()

`ifPresent()` executes code only when a value exists.

Example:

```java
Optional<String> name =
        Optional.of("Akash");

name.ifPresent(
    n -> System.out.println(n)
);
```

Output:

```text
Akash
```

Using method reference:

```java
name.ifPresent(System.out::println);
```

### Mental model

```text
ifPresent()
     ↓
"If value exists,
 execute this code."
```

---

# 9. get()

`get()` retrieves the value.

```java
Optional<String> name =
        Optional.of("Akash");

System.out.println(name.get());
```

Output:

```text
Akash
```

### Dangerous case

```java
Optional<String> name =
        Optional.empty();

name.get();
```

This throws:

```text
NoSuchElementException
```

Therefore, avoid blindly using:

```java
optional.get()
```

Prefer safer methods such as:

```text
ifPresent()
orElse()
orElseGet()
orElseThrow()
```

---

# 10. orElse()

`orElse()` provides a default value when the Optional is empty.

```java
Optional<String> name =
        Optional.empty();

String result =
        name.orElse("Unknown");

System.out.println(result);
```

Output:

```text
Unknown
```

If a value exists:

```java
Optional<String> name =
        Optional.of("Akash");

String result =
        name.orElse("Unknown");
```

Result:

```text
Akash
```

### Mental model

```text
orElse()
   ↓
value exists?
   ↓ yes → return value
   ↓ no  → return default
```

---

# 11. orElseGet()

`orElseGet()` accepts a `Supplier` and creates the fallback value lazily.

```java
String name =
        optional.orElseGet(
            () -> "Unknown"
        );
```

Example:

```java
Optional<String> name =
        Optional.empty();

String result =
        name.orElseGet(
            () -> "Unknown"
        );

System.out.println(result);
```

Output:

```text
Unknown
```

---

# 12. orElse() vs orElseGet()

Both provide fallback values.

```java
optional.orElse("Unknown");
```

and:

```java
optional.orElseGet(() -> "Unknown");
```

The important difference is **evaluation**.

`orElse()` evaluates its argument even when the Optional contains a value.

`orElseGet()` evaluates the Supplier only when the Optional is empty.

For an expensive fallback operation, `orElseGet()` can therefore be preferable.

---

# 13. orElseThrow()

Throws an exception when the Optional is empty.

Java 8 style:

```java
String name =
        optional.orElseThrow(
            () -> new RuntimeException("Name not found")
        );
```

If the value exists:

```text
return value
```

Otherwise:

```text
throw exception
```

---

# 14. Optional + Stream API

Optional is very commonly used with Stream API.

Example:

```java
List<Integer> numbers =
        Arrays.asList(10, 20, 30, 40, 50);

Optional<Integer> result =
        numbers.stream()
               .filter(n -> n > 35)
               .findFirst();
```

The result is:

```text
Optional[40]
```

Then:

```java
result.ifPresent(System.out::println);
```

Output:

```text
40
```

### Pipeline

```text
List
 ↓
stream()
 ↓
filter()
 ↓
findFirst()
 ↓
Optional<Integer>
 ↓
ifPresent()
 ↓
40
```

---

# 15. Why findFirst() Returns Optional

Consider:

```java
List<Integer> numbers =
        Arrays.asList(10, 20, 30);

Optional<Integer> result =
        numbers.stream()
               .filter(n -> n > 100)
               .findFirst();
```

There is no matching value.

Instead of returning `null`, Java returns:

```text
Optional.empty()
```

This forces the developer to consider the "no result" case.

---

# 16. min() Returns Optional

```java
List<Integer> numbers =
        Arrays.asList(10, 20, 30, 40);

Optional<Integer> min =
        numbers.stream()
               .min(Integer::compare);
```

Use:

```java
min.ifPresent(System.out::println);
```

Output:

```text
10
```

Why Optional?

Because the stream could be empty:

```text
[]
 ↓
min()
 ↓
Optional.empty()
```

---

# 17. max() Returns Optional

```java
Optional<Integer> max =
        numbers.stream()
               .max(Integer::compare);

max.ifPresent(System.out::println);
```

Output:

```text
40
```

---

# 18. findFirst()

Returns the first matching element as an Optional.

```java
Optional<Integer> result =
        numbers.stream()
               .filter(n -> n > 20)
               .findFirst();
```

Result:

```text
Optional[30]
```

If nothing matches:

```text
Optional.empty()
```

---

# 19. findAny()

Returns any matching element.

```java
Optional<Integer> result =
        numbers.stream()
               .filter(n -> n > 20)
               .findAny();
```

With a sequential stream it commonly appears to return the first matching element, but code should not depend on that behavior.

With a parallel stream, it can return any matching element.

---

# 20. Optional map()

Optional itself has a `map()` method.

Example:

```java
Optional<String> name =
        Optional.of("akash");

Optional<String> upper =
        name.map(String::toUpperCase);
```

Result:

```text
Optional["AKASH"]
```

Conceptually:

```text
Optional["akash"]
        ↓ map()
Optional["AKASH"]
```

If the original Optional is empty:

```text
Optional.empty()
        ↓ map()
Optional.empty()
```

---

# 21. Optional filter()

Optional also has `filter()`.

```java
Optional<Integer> number =
        Optional.of(50);

Optional<Integer> result =
        number.filter(n -> n > 30);
```

Result:

```text
Optional[50]
```

If the condition fails:

```java
Optional<Integer> result =
        number.filter(n -> n > 100);
```

Result:

```text
Optional.empty()
```

---

# 22. Real-World Student Example

Suppose:

```java
class Student {

    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
```

Search for a student:

```java
Optional<Student> student =
        students.stream()
                .filter(s -> s.getId() == 101)
                .findFirst();
```

Print only if found:

```java
student.ifPresent(
    s -> System.out.println(s.getName())
);
```

Using method reference and Optional `map()`:

```java
String name =
        student
            .map(Student::getName)
            .orElse("Student Not Found");

System.out.println(name);
```

This is a very practical combination of:

```text
Stream
   +
Optional
   +
Method Reference
```

---

# 23. Optional + Method Reference

Consider:

```java
student.map(Student::getName)
```

The following concepts are combined:

```text
Student::getName
       ↓
Method Reference
```

and:

```text
map()
       ↓
Transform Optional<Student>
into Optional<String>
```

So:

```text
Optional<Student>
       ↓
map(Student::getName)
       ↓
Optional<String>
```

---

# 24. Optional Methods Cheat Sheet

| Method | Purpose |
|---|---|
| `of()` | Create Optional with a non-null value |
| `ofNullable()` | Create Optional from possibly-null value |
| `empty()` | Create empty Optional |
| `isPresent()` | Check if value exists |
| `get()` | Get value; can throw exception |
| `ifPresent()` | Execute code if value exists |
| `orElse()` | Provide default value |
| `orElseGet()` | Lazily create default value |
| `orElseThrow()` | Throw exception if empty |
| `map()` | Transform contained value |
| `filter()` | Keep value if condition matches |

---

# 25. Optional vs null

## Traditional null approach

```java
String name = getName();

if (name != null) {
    System.out.println(name.toUpperCase());
} else {
    System.out.println("Unknown");
}
```

## Optional approach

```java
Optional<String> name =
        getName();

String result =
        name.map(String::toUpperCase)
            .orElse("Unknown");
```

Conceptually:

```text
null
 ↓
must manually check

Optional
 ↓
explicitly represents absence
 ↓
use Optional operations
```

---

# 26. Important: Optional Does NOT Eliminate Null Everywhere

Optional is not a replacement for every use of `null`.

It is primarily useful for **representing an optional return value**.

For example:

```java
public Optional<Student> findStudentById(int id) {
    // return student if found
    // otherwise Optional.empty()
}
```

This communicates to the caller:

> A student may or may not be found.

---

# 27. Good Practice

Prefer:

```java
optional.ifPresent(...)
```

```java
optional.orElse(...)
```

```java
optional.orElseGet(...)
```

```java
optional.orElseThrow(...)
```

Avoid blindly doing:

```java
optional.get()
```

especially when you haven't established that the value exists.

---

# 28. Complete Live Coding Example

```java
import java.util.*;

public class OptionalDemo {

    public static void main(String[] args) {

        // 1. Create Optional with value
        Optional<String> name =
                Optional.of("Akash");

        System.out.println(name);


        // 2. Check value
        System.out.println(
                name.isPresent()
        );


        // 3. Get value safely
        name.ifPresent(
                System.out::println
        );


        // 4. Default value
        String result =
                name.orElse("Unknown");

        System.out.println(result);


        // 5. Empty Optional
        Optional<String> empty =
                Optional.empty();

        System.out.println(
                empty.orElse("Unknown")
        );


        // 6. ofNullable
        String value = null;

        Optional<String> optional =
                Optional.ofNullable(value);

        System.out.println(
                optional.orElse("Default")
        );


        // 7. map
        Optional<String> upper =
                name.map(String::toUpperCase);

        upper.ifPresent(
                System.out::println
        );


        // 8. filter
        Optional<String> filtered =
                name.filter(
                        n -> n.length() > 3
                );

        filtered.ifPresent(
                System.out::println
        );
    }
}
```

---

# 29. Stream + Optional Live Example

```java
import java.util.*;
import java.util.stream.*;

public class StreamOptionalDemo {

    public static void main(String[] args) {

        List<Integer> numbers =
                Arrays.asList(
                        10, 20, 30, 40, 50
                );

        // Find first number greater than 35
        Optional<Integer> result =
                numbers.stream()
                       .filter(n -> n > 35)
                       .findFirst();

        result.ifPresent(
                n -> System.out.println(
                        "Found: " + n
                )
        );


        // Find maximum
        Optional<Integer> max =
                numbers.stream()
                       .max(Integer::compare);

        max.ifPresent(
                n -> System.out.println(
                        "Max: " + n
                )
        );


        // Find a number greater than 100
        Optional<Integer> notFound =
                numbers.stream()
                       .filter(n -> n > 100)
                       .findFirst();

        System.out.println(
                notFound.orElse(-1)
        );
    }
}
```

Output:

```text
Found: 40
Max: 50
-1
```

---

# 30. Optional Flow

```text
              Optional<T>
                   │
          ┌────────┴────────┐
          │                 │
       PRESENT            EMPTY
          │                 │
     get value          no value
          │                 │
    ┌─────┼─────┐           │
    │     │     │           │
  map()  filter()       orElse()
    │     │             orElseGet()
    │     │             orElseThrow()
    │     │
    └─────┴─────┐
                │
           ifPresent()
```

---

# 31. Most Important Interview Questions

## Q1. What is Optional?

`Optional<T>` is a Java 8 container that represents a value that may or may not be present.

---

## Q2. Why was Optional introduced?

Primarily to provide a clear way to model absence of a return value and reduce accidental null handling problems.

---

## Q3. Difference between `of()` and `ofNullable()`?

```java
Optional.of(value)
```

expects a non-null value.

```java
Optional.ofNullable(value)
```

allows the value to be null and creates `Optional.empty()` in that case.

---

## Q4. Difference between `orElse()` and `orElseGet()`?

```java
orElse(value)
```

evaluates the argument immediately.

```java
orElseGet(() -> value)
```

evaluates the Supplier only when the Optional is empty.

---

## Q5. Why does `findFirst()` return Optional?

Because there may be no matching element.

```text
matching element
      ↓
Optional[value]

no matching element
      ↓
Optional.empty()
```

---

## Q6. Should we always use Optional instead of null?

No.

Optional is particularly useful when a method's result may legitimately be absent, especially as a return type.

---

## Q7. Is Optional a replacement for exceptions?

No.

Optional represents an absent/present value. Exceptions represent exceptional situations.

---

# 32. Quick Revision

```text
Optional.of(value)
    → value must not be null

Optional.ofNullable(value)
    → value may be null

Optional.empty()
    → no value

isPresent()
    → value exists?

ifPresent()
    → execute if value exists

get()
    → retrieve value; unsafe if empty

orElse()
    → default value

orElseGet()
    → lazy default value

orElseThrow()
    → throw if empty

map()
    → transform value

filter()
    → keep value if condition matches
```

---

# 33. Most Important Pattern

Remember this:

```java
Optional<T> result =
        stream
            .filter(...)
            .findFirst();

result.ifPresent(...);
```

Or:

```java
String result =
        optional
            .map(...)
            .orElse("Default");
```

---

# 34. Final Mental Model

```text
             OPTIONAL
                │
                ↓
       "Value may exist"
                │
        ┌───────┴───────┐
        ↓               ↓
     PRESENT           EMPTY
        │               │
        ↓               ↓
     map()          orElse()
     filter()       orElseGet()
     ifPresent()    orElseThrow()
```

### One-line definition

> **Optional = a safe container representing "a value OR no value".**

### Java 8 Stream connection

```text
Collection
    ↓
stream()
    ↓
filter()
    ↓
findFirst()
    ↓
Optional<T>
    ↓
ifPresent()
```

This combination is extremely common in Java 8+ code.
