# Java 8 Stream API — Complete Practical Notes

## 1. What is Stream API?

Stream API was introduced in **Java 8** to process collections in a declarative and functional style.

### Traditional approach

```java
List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50);

for (Integer n : numbers) {
    if (n > 25) {
        System.out.println(n);
    }
}
```

### Stream approach

```java
numbers.stream()
       .filter(n -> n > 25)
       .forEach(System.out::println);
```

Output:

```text
30
40
50
```

### Important

A Stream is **not a data structure**.

It does not store data. It processes data from a source such as:

- List
- Set
- Map
- Array
- File
- Other streams

Think:

```text
Collection
    ↓
  stream()
    ↓
Stream
    ↓
filter()
    ↓
map()
    ↓
sorted()
    ↓
collect()
    ↓
Result
```

---

# 2. Why Stream API?

Before Java 8:

```java
List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50);

List<Integer> result = new ArrayList<>();

for (Integer n : numbers) {
    if (n > 25) {
        result.add(n * 2);
    }
}

System.out.println(result);
```

Java 8:

```java
List<Integer> result =
        numbers.stream()
               .filter(n -> n > 25)
               .map(n -> n * 2)
               .collect(Collectors.toList());

System.out.println(result);
```

Output:

```text
[60, 80, 100]
```

---

# 3. Basic Stream Pipeline

A Stream generally has three parts:

```text
SOURCE
   ↓
INTERMEDIATE OPERATIONS
   ↓
TERMINAL OPERATION
```

Example:

```java
numbers.stream()                       // Source
       .filter(n -> n > 20)            // Intermediate
       .map(n -> n * 2)                // Intermediate
       .forEach(System.out::println);  // Terminal
```

---

# 4. Creating a Stream

## From Collection

```java
List<Integer> numbers =
        Arrays.asList(10, 20, 30, 40);

Stream<Integer> stream = numbers.stream();
```

## From Set

```java
Set<String> names =
        new HashSet<>(Arrays.asList(
                "Akash",
                "Rahul",
                "Amit"
        ));

names.stream()
     .forEach(System.out::println);
```

## From Array

```java
int[] numbers = {10, 20, 30, 40};

Arrays.stream(numbers)
      .forEach(System.out::println);
```

Object array:

```java
String[] names = {"Akash", "Rahul", "Amit"};

Arrays.stream(names)
      .forEach(System.out::println);
```

---

# 5. Stream.of()

```java
Stream<String> stream =
        Stream.of("Java", "Python", "C++");

stream.forEach(System.out::println);
```

Output:

```text
Java
Python
C++
```

---

# 6. Empty Stream

```java
Stream<String> stream = Stream.empty();
```

---

# 7. Intermediate vs Terminal Operations

## Intermediate Operations

Intermediate operations return another Stream.

Major intermediate operations:

```text
filter()
map()
flatMap()
distinct()
sorted()
limit()
skip()
peek()
```

Example:

```java
numbers.stream()
       .filter(n -> n > 20)
       .map(n -> n * 2)
       .sorted();
```

Nothing is actually executed yet because there is no terminal operation.

---

## Terminal Operations

Terminal operations produce the final result.

Major terminal operations:

```text
forEach()
forEachOrdered()
collect()
reduce()
count()
min()
max()
findFirst()
findAny()
anyMatch()
allMatch()
noneMatch()
toArray()
iterator()
```

Example:

```java
long count =
        numbers.stream()
               .filter(n -> n > 20)
               .count();
```

---

# 8. Lazy Evaluation

Streams are **lazy**.

This does not execute the filter:

```java
numbers.stream()
       .filter(n -> {
           System.out.println("Filtering " + n);
           return n > 20;
       });
```

Nothing prints because there is no terminal operation.

Add one:

```java
numbers.stream()
       .filter(n -> {
           System.out.println("Filtering " + n);
           return n > 20;
       })
       .forEach(System.out::println);
```

Now the pipeline executes.

### Key idea

```text
Stream created
      ↓
Intermediate operations
      ↓
Nothing executes yet
      ↓
Terminal operation
      ↓
Pipeline executes
```

---

# 9. filter()

`filter()` is used to select elements.

Syntax:

```java
filter(Predicate<T>)
```

Example:

```java
List<Integer> numbers =
        Arrays.asList(10, 15, 20, 25, 30);

numbers.stream()
       .filter(n -> n > 20)
       .forEach(System.out::println);
```

Output:

```text
25
30
```

Multiple filters:

```java
numbers.stream()
       .filter(n -> n > 10)
       .filter(n -> n % 2 == 0)
       .forEach(System.out::println);
```

---

# 10. map()

`map()` transforms each element.

Example:

```java
List<String> names =
        Arrays.asList("akash", "rahul", "amit");

names.stream()
     .map(String::toUpperCase)
     .forEach(System.out::println);
```

Output:

```text
AKASH
RAHUL
AMIT
```

Another example:

```java
List<Integer> numbers =
        Arrays.asList(1, 2, 3, 4, 5);

List<Integer> result =
        numbers.stream()
               .map(n -> n * n)
               .collect(Collectors.toList());

System.out.println(result);
```

Output:

```text
[1, 4, 9, 16, 25]
```

### Mental model

```text
10 → 20
20 → 40
30 → 60
```

One input produces one output.

```text
Stream<T>
    ↓ map()
Stream<R>
```

---

# 11. flatMap()

`flatMap()` is used when each element contains multiple elements.

Example:

```java
List<List<Integer>> numbers =
        Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );
```

Using `map()`:

```java
numbers.stream()
       .map(list -> list.stream())
       .forEach(System.out::println);
```

This creates streams inside streams.

Using `flatMap()`:

```java
numbers.stream()
       .flatMap(list -> list.stream())
       .forEach(System.out::println);
```

Output:

```text
1
2
3
4
5
6
7
8
9
```

### Mental model

`map()`:

```text
[1,2,3]
[4,5,6]
[7,8,9]

       ↓

Stream<Stream<Integer>>
```

`flatMap()`:

```text
[1,2,3]
[4,5,6]
[7,8,9]

       ↓

1,2,3,4,5,6,7,8,9
```

---

# 12. distinct()

Removes duplicate elements.

```java
List<Integer> numbers =
        Arrays.asList(10, 20, 20, 30, 30, 30);

numbers.stream()
       .distinct()
       .forEach(System.out::println);
```

Output:

```text
10
20
30
```

---

# 13. sorted()

Natural sorting:

```java
List<Integer> numbers =
        Arrays.asList(50, 10, 30, 20, 40);

numbers.stream()
       .sorted()
       .forEach(System.out::println);
```

Output:

```text
10
20
30
40
50
```

Descending:

```java
numbers.stream()
       .sorted(Comparator.reverseOrder())
       .forEach(System.out::println);
```

---

# 14. Sorting Objects

Example class:

```java
class Student {

    int id;
    String name;
    double marks;

    Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
}
```

Sort by marks:

```java
students.stream()
        .sorted(Comparator.comparingDouble(s -> s.marks))
        .forEach(s -> System.out.println(s.name));
```

Descending:

```java
students.stream()
        .sorted(
            Comparator.comparingDouble((Student s) -> s.marks)
                      .reversed()
        )
        .forEach(s -> System.out.println(s.name));
```

---

# 15. limit()

Takes the first N elements.

```java
numbers.stream()
       .limit(3)
       .forEach(System.out::println);
```

For:

```text
10 20 30 40 50
```

Output:

```text
10
20
30
```

---

# 16. skip()

Skips the first N elements.

```java
numbers.stream()
       .skip(2)
       .forEach(System.out::println);
```

Output:

```text
30
40
50
```

---

# 17. peek()

`peek()` is mainly useful for debugging or inspecting a pipeline.

```java
numbers.stream()
       .filter(n -> n > 20)
       .peek(n -> System.out.println("After filter: " + n))
       .map(n -> n * 2)
       .peek(n -> System.out.println("After map: " + n))
       .collect(Collectors.toList());
```

Avoid using `peek()` for important business logic.

---

# 18. forEach()

Terminal operation.

```java
numbers.stream()
       .forEach(System.out::println);
```

Equivalent:

```java
numbers.stream()
       .forEach(n -> System.out.println(n));
```

---

# 19. forEachOrdered()

Important with parallel streams.

Normal parallel processing:

```java
numbers.parallelStream()
       .forEach(System.out::println);
```

Order is not guaranteed.

Ordered version:

```java
numbers.parallelStream()
       .forEachOrdered(System.out::println);
```

This preserves encounter order.

---

# 20. count()

Returns the number of elements.

```java
long count =
        numbers.stream()
               .filter(n -> n > 20)
               .count();

System.out.println(count);
```

---

# 21. min()

Returns the minimum element as an `Optional`.

```java
Optional<Integer> min =
        numbers.stream()
               .min(Integer::compare);

System.out.println(min.get());
```

Safer:

```java
numbers.stream()
       .min(Integer::compare)
       .ifPresent(System.out::println);
```

### Why Optional?

Because the stream may be empty.

---

# 22. max()

```java
Optional<Integer> max =
        numbers.stream()
               .max(Integer::compare);

max.ifPresent(System.out::println);
```

---

# 23. findFirst()

Returns the first element matching a condition.

```java
Optional<Integer> result =
        numbers.stream()
               .filter(n -> n > 20)
               .findFirst();

result.ifPresent(System.out::println);
```

---

# 24. findAny()

Returns any matching element.

```java
numbers.stream()
       .filter(n -> n > 20)
       .findAny()
       .ifPresent(System.out::println);
```

With sequential streams it commonly appears to return the first matching element, but code should not depend on that behavior.

With `parallelStream()`, it may return any matching element.

---

# 25. anyMatch()

Question:

> Does at least one element satisfy the condition?

```java
boolean result =
        numbers.stream()
               .anyMatch(n -> n > 40);

System.out.println(result);
```

Output:

```text
true
```

---

# 26. allMatch()

Question:

> Do all elements satisfy the condition?

```java
boolean result =
        numbers.stream()
               .allMatch(n -> n > 0);
```

---

# 27. noneMatch()

Question:

> Does no element satisfy the condition?

```java
boolean result =
        numbers.stream()
               .noneMatch(n -> n < 0);
```

---

# 28. Match Methods — Easy Memory Trick

```text
anyMatch  → ANY
allMatch  → ALL
noneMatch → NONE
```

For:

```text
10 20 30 40 50
```

```java
anyMatch(n -> n > 40)
```

Result:

```text
true
```

```java
allMatch(n -> n > 0)
```

Result:

```text
true
```

```java
noneMatch(n -> n < 0)
```

Result:

```text
true
```

---

# 29. collect()

One of the most important Stream API methods.

It is used to gather stream results into collections or other result structures.

```java
List<Integer> result =
        numbers.stream()
               .filter(n -> n > 20)
               .collect(Collectors.toList());
```

---

# 30. Collectors.toList()

```java
List<Integer> list =
        numbers.stream()
               .collect(Collectors.toList());
```

---

# 31. Collectors.toSet()

```java
Set<Integer> set =
        numbers.stream()
               .collect(Collectors.toSet());
```

Useful when a Set result is required and duplicates should not remain.

---

# 32. Collectors.joining()

```java
List<String> names =
        Arrays.asList("Java", "Spring", "SQL");

String result =
        names.stream()
             .collect(Collectors.joining(", "));

System.out.println(result);
```

Output:

```text
Java, Spring, SQL
```

With prefix and suffix:

```java
String result =
        names.stream()
             .collect(
                 Collectors.joining(
                     ", ",
                     "[",
                     "]"
                 )
             );
```

Output:

```text
[Java, Spring, SQL]
```

---

# 33. Collectors.groupingBy()

Very important for real-world Java.

Example:

```java
class Employee {

    String name;
    String department;
    double salary;

    // constructor
}
```

Group employees by department:

```java
Map<String, List<Employee>> result =
        employees.stream()
                 .collect(
                     Collectors.groupingBy(
                         e -> e.department
                     )
                 );
```

Conceptually:

```text
IT       → [Akash, Rahul]
HR       → [Amit, Priya]
Finance  → [Ravi, Neha]
```

---

# 34. groupingBy() With Counting

```java
Map<String, Long> result =
        employees.stream()
                 .collect(
                     Collectors.groupingBy(
                         e -> e.department,
                         Collectors.counting()
                     )
                 );
```

Conceptual output:

```text
IT → 5
HR → 3
Finance → 4
```

---

# 35. groupingBy() With Average

```java
Map<String, Double> averageSalary =
        employees.stream()
                 .collect(
                     Collectors.groupingBy(
                         e -> e.department,
                         Collectors.averagingDouble(
                             e -> e.salary
                         )
                     )
                 );
```

---

# 36. partitioningBy()

Divides data into two groups:

```text
true
false
```

Example:

```java
Map<Boolean, List<Integer>> result =
        numbers.stream()
               .collect(
                   Collectors.partitioningBy(
                       n -> n % 2 == 0
                   )
               );
```

Result:

```text
true  → [10,20,30,40]
false → [15,25]
```

### groupingBy vs partitioningBy

```text
groupingBy()
    ↓
multiple groups

partitioningBy()
    ↓
exactly two groups
true / false
```

---

# 37. Collectors.toMap()

Convert a stream into a Map.

```java
Map<Integer, String> map =
        students.stream()
                .collect(
                    Collectors.toMap(
                        s -> s.id,
                        s -> s.name
                    )
                );
```

### Important

If two elements produce the same key, `toMap()` needs a merge function.

Example:

```java
Map<String, Integer> map =
        names.stream()
             .collect(
                 Collectors.toMap(
                     name -> name,
                     name -> 1,
                     Integer::sum
                 )
             );
```

---

# 38. reduce()

`reduce()` combines multiple elements into a single result.

Example:

```java
List<Integer> numbers =
        Arrays.asList(10, 20, 30, 40);

Optional<Integer> result =
        numbers.stream()
               .reduce((a, b) -> a + b);

System.out.println(result.get());
```

Output:

```text
100
```

---

# 39. reduce() With Identity

```java
int sum =
        numbers.stream()
               .reduce(
                   0,
                   (a, b) -> a + b
               );
```

Simpler:

```java
int sum =
        numbers.stream()
               .reduce(0, Integer::sum);
```

### Mental model

For:

```text
[10, 20, 30, 40]
```

Think:

```text
10 + 20 = 30
30 + 30 = 60
60 + 40 = 100
```

Final result:

```text
100
```

---

# 40. toArray()

Basic:

```java
Object[] arr =
        numbers.stream()
               .toArray();
```

Typed array:

```java
Integer[] arr =
        numbers.stream()
               .toArray(Integer[]::new);
```

---

# 41. Primitive Streams

Java provides:

```text
IntStream
LongStream
DoubleStream
```

Instead of:

```text
Stream<Integer>
```

we can use:

```text
IntStream
```

Example:

```java
int sum =
        IntStream.of(10, 20, 30, 40)
                 .sum();

System.out.println(sum);
```

---

# 42. Important IntStream Methods

Useful terminal operations:

```text
sum()
average()
min()
max()
count()
summaryStatistics()
```

Example:

```java
IntStream stream =
        IntStream.of(10, 20, 30, 40, 50);

System.out.println(stream.sum());
```

---

# 43. average()

```java
OptionalDouble avg =
        IntStream.of(10, 20, 30, 40)
                 .average();

avg.ifPresent(System.out::println);
```

Output:

```text
25.0
```

---

# 44. summaryStatistics()

Very useful for statistics on primitive streams.

```java
IntSummaryStatistics stats =
        IntStream.of(10, 20, 30, 40, 50)
                 .summaryStatistics();

System.out.println(stats.getCount());
System.out.println(stats.getSum());
System.out.println(stats.getMin());
System.out.println(stats.getMax());
System.out.println(stats.getAverage());
```

Output:

```text
5
150
10
50
30.0
```

---

# 45. IntStream.range()

```java
IntStream.range(1, 5)
         .forEach(System.out::println);
```

Output:

```text
1
2
3
4
```

`5` is excluded.

---

# 46. IntStream.rangeClosed()

```java
IntStream.rangeClosed(1, 5)
         .forEach(System.out::println);
```

Output:

```text
1
2
3
4
5
```

### Difference

```text
range()
    1 → 5
    5 excluded

rangeClosed()
    1 → 5
    5 included
```

---

# 47. boxed()

Converts a primitive stream to an object stream.

```java
List<Integer> list =
        IntStream.rangeClosed(1, 5)
                 .boxed()
                 .collect(Collectors.toList());
```

Result:

```text
[1, 2, 3, 4, 5]
```

---

# 48. mapToInt()

Example:

```java
List<String> numbers =
        Arrays.asList("10", "20", "30");

int sum =
        numbers.stream()
               .mapToInt(Integer::parseInt)
               .sum();
```

---

# 49. mapToDouble()

```java
double total =
        employees.stream()
                 .mapToDouble(e -> e.salary)
                 .sum();
```

---

# 50. mapToLong()

```java
long total =
        employees.stream()
                 .mapToLong(e -> e.id)
                 .sum();
```

---

# 51. Stream Cannot Be Reused

Important interview question.

Wrong:

```java
Stream<Integer> stream =
        numbers.stream();

stream.count();

stream.forEach(System.out::println);
```

This throws:

```text
IllegalStateException:
stream has already been operated upon or closed
```

Correct:

```java
numbers.stream().count();

numbers.stream().forEach(System.out::println);
```

A Stream is generally **single-use**.

---

# 52. Stream vs Collection

| Collection | Stream |
|---|---|
| Stores data | Processes data |
| Can be reused | Generally single-use |
| Eager | Lazy |
| Data structure | Processing pipeline |
| Add/remove elements | Transform/filter elements |

### Easy memory trick

```text
Collection = DATA
Stream     = PROCESSING
```

---

# 53. map() vs filter()

`filter()` changes the **number of elements**.

```text
[1,2,3,4,5]
       ↓ filter even
[2,4]
```

`map()` changes the **elements**.

```text
[1,2,3,4,5]
       ↓ map × 10
[10,20,30,40,50]
```

---

# 54. map() vs flatMap()

```text
map()
    one input → one output

flatMap()
    one input → multiple outputs, then flatten
```

Example:

```java
List<List<String>> data =
        Arrays.asList(
            Arrays.asList("Java", "Spring"),
            Arrays.asList("SQL", "Docker")
        );

data.stream()
    .flatMap(List::stream)
    .forEach(System.out::println);
```

Output:

```text
Java
Spring
SQL
Docker
```

---

# 55. Complete Real-World Employee Example

## Employee Class

```java
class Employee {

    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(
            int id,
            String name,
            String department,
            double salary) {

        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return name + " - " + department + " - " + salary;
    }
}
```

## Data

```java
List<Employee> employees =
        Arrays.asList(

            new Employee(1, "Akash",
                         "IT", 60000),

            new Employee(2, "Rahul",
                         "HR", 45000),

            new Employee(3, "Amit",
                         "IT", 75000),

            new Employee(4, "Priya",
                         "Finance", 80000),

            new Employee(5, "Ravi",
                         "IT", 55000)
        );
```

## Find IT Employees

```java
employees.stream()
         .filter(e -> e.getDepartment().equals("IT"))
         .forEach(System.out::println);
```

## Find employees with salary > 60K

```java
employees.stream()
         .filter(e -> e.getSalary() > 60000)
         .forEach(System.out::println);
```

## Get employee names

```java
List<String> names =
        employees.stream()
                 .map(Employee::getName)
                 .collect(Collectors.toList());
```

## Sort by salary

```java
employees.stream()
         .sorted(
             Comparator.comparingDouble(
                 Employee::getSalary
             )
         )
         .forEach(System.out::println);
```

## Highest salary

```java
employees.stream()
         .max(
             Comparator.comparingDouble(
                 Employee::getSalary
             )
         )
         .ifPresent(System.out::println);
```

## Lowest salary

```java
employees.stream()
         .min(
             Comparator.comparingDouble(
                 Employee::getSalary
             )
         )
         .ifPresent(System.out::println);
```

## Total salary

```java
double total =
        employees.stream()
                 .mapToDouble(Employee::getSalary)
                 .sum();
```

## Average salary

```java
double average =
        employees.stream()
                 .mapToDouble(Employee::getSalary)
                 .average()
                 .orElse(0);
```

## Group by department

```java
Map<String, List<Employee>> result =
        employees.stream()
                 .collect(
                     Collectors.groupingBy(
                         Employee::getDepartment
                     )
                 );
```

## Count employees by department

```java
Map<String, Long> result =
        employees.stream()
                 .collect(
                     Collectors.groupingBy(
                         Employee::getDepartment,
                         Collectors.counting()
                     )
                 );
```

---

# 56. Method References

Instead of:

```java
numbers.stream()
       .forEach(n -> System.out.println(n));
```

Use:

```java
numbers.stream()
       .forEach(System.out::println);
```

Instead of:

```java
.map(e -> e.getName())
```

Use:

```java
.map(Employee::getName)
```

Method references make Stream code cleaner.

---

# 57. Parallel Stream

Normal:

```java
numbers.stream()
```

Parallel:

```java
numbers.parallelStream()
```

Example:

```java
numbers.parallelStream()
       .forEach(System.out::println);
```

Multiple threads may process elements.

### Important

Do not blindly use parallel streams.

For small collections, parallel processing can be slower because of thread-management and coordination overhead.

---

# 58. stream() vs parallelStream()

```text
stream()
   ↓
Sequential processing

parallelStream()
   ↓
Parallel processing
```

Example:

```java
numbers.stream()
       .map(...)
       .filter(...)
       .collect(...);
```

vs:

```java
numbers.parallelStream()
       .map(...)
       .filter(...)
       .collect(...);
```

---

# 59. Short-Circuit Operations

Some operations can stop processing early.

Important short-circuit operations include:

```text
limit()
findFirst()
findAny()
anyMatch()
allMatch()
noneMatch()
```

Example:

```java
numbers.stream()
       .filter(n -> {
           System.out.println(n);
           return n > 30;
       })
       .findFirst();
```

The pipeline can stop once the first matching result is found.

---

# 60. Stream Operation Cheat Sheet

## Intermediate Operations

| Method | Purpose |
|---|---|
| `filter()` | Select elements |
| `map()` | Transform elements |
| `flatMap()` | Flatten nested streams |
| `distinct()` | Remove duplicates |
| `sorted()` | Sort elements |
| `limit()` | Take first N |
| `skip()` | Skip first N |
| `peek()` | Inspect/debug |

## Terminal Operations

| Method | Purpose |
|---|---|
| `forEach()` | Process each element |
| `forEachOrdered()` | Ordered processing |
| `collect()` | Gather result |
| `reduce()` | Combine into one value |
| `count()` | Count elements |
| `min()` | Minimum |
| `max()` | Maximum |
| `findFirst()` | First element |
| `findAny()` | Any matching element |
| `anyMatch()` | At least one |
| `allMatch()` | Every element |
| `noneMatch()` | No element |
| `toArray()` | Convert to array |

---

# 61. Important Collectors

Students should know these especially:

```text
Collectors.toList()
Collectors.toSet()
Collectors.toMap()
Collectors.joining()
Collectors.groupingBy()
Collectors.partitioningBy()
Collectors.counting()
Collectors.summingInt()
Collectors.summingDouble()
Collectors.averagingInt()
Collectors.averagingDouble()
Collectors.mapping()
Collectors.collectingAndThen()
```

---

# 62. Practical Stream Pipeline Pattern

A common pattern is:

```java
collection.stream()
          .filter(...)
          .map(...)
          .sorted(...)
          .distinct(...)
          .limit(...)
          .collect(...);
```

Example:

```java
List<String> result =
        employees.stream()

                 .filter(e -> e.getSalary() > 50000)

                 .map(Employee::getName)

                 .sorted()

                 .collect(Collectors.toList());
```

Think:

```text
employees
    ↓
filter
    ↓
map
    ↓
sorted
    ↓
collect
    ↓
List<String>
```

---

# 63. Important Interview Questions

## Is Stream API a replacement for Collection?

**No.**

Collection:

```text
stores data
```

Stream:

```text
processes data
```

A Stream does not hold the elements.

---

## Why are streams lazy?

Intermediate operations do not execute immediately.

```java
numbers.stream()
       .filter(...)
       .map(...);
```

Nothing happens until a terminal operation such as:

```java
.collect(...)
```

is called.

Lazy execution can help avoid unnecessary processing and enables pipeline optimizations.

---

## Can a Stream be reused?

No. A Stream is generally single-use.

After a terminal operation, create a new stream if more processing is required.

---

# 64. Complete Live Coding Demo

This is a good classroom example because operations can be added one by one.

```java
import java.util.*;
import java.util.stream.*;

public class StreamDemo {

    public static void main(String[] args) {

        List<Integer> numbers =
                Arrays.asList(
                        10, 20, 30, 40,
                        50, 60, 70
                );

        // 1. Print all
        numbers.stream()
               .forEach(System.out::println);


        // 2. Filter
        numbers.stream()
               .filter(n -> n > 30)
               .forEach(System.out::println);


        // 3. Map
        numbers.stream()
               .map(n -> n * 2)
               .forEach(System.out::println);


        // 4. Filter + Map
        numbers.stream()
               .filter(n -> n > 30)
               .map(n -> n * 2)
               .forEach(System.out::println);


        // 5. Count
        long count =
                numbers.stream()
                       .filter(n -> n > 30)
                       .count();

        System.out.println("Count = " + count);


        // 6. Find First
        numbers.stream()
               .filter(n -> n > 30)
               .findFirst()
               .ifPresent(System.out::println);


        // 7. Any Match
        boolean exists =
                numbers.stream()
                       .anyMatch(n -> n > 100);

        System.out.println(exists);


        // 8. All Match
        boolean allPositive =
                numbers.stream()
                       .allMatch(n -> n > 0);

        System.out.println(allPositive);


        // 9. Reduce
        int sum =
                numbers.stream()
                       .reduce(0, Integer::sum);

        System.out.println("Sum = " + sum);


        // 10. Collect
        List<Integer> result =
                numbers.stream()
                       .filter(n -> n > 30)
                       .collect(Collectors.toList());

        System.out.println(result);
    }
}
```

---

# 65. Final Mental Map

```text
                    STREAM API
                         │
            ┌────────────┴────────────┐
            │                         │
          SOURCE                  PIPELINE
            │                         │
      List.stream()            Intermediate
      Set.stream()                  │
      Arrays.stream()        ┌───────┴────────┐
      Stream.of()             │                │
                            filter            map
                            flatMap          sorted
                            distinct         limit
                            skip             peek
                               │
                               ↓
                          Terminal
                               │
                 ┌─────────────┼─────────────┐
                 │             │             │
              collect        reduce        forEach
                 │
           ┌─────┼─────┐
          List  Set   Map
                       │
                groupingBy
                partitioningBy
                joining
                counting
```

---

# 66. Top 10 Methods to Master First

Students should master these first:

```text
1. filter()
2. map()
3. flatMap()
4. sorted()
5. distinct()
6. collect()
7. reduce()
8. forEach()
9. findFirst()
10. anyMatch()
```

Then move to:

```text
groupingBy()
partitioningBy()
toMap()
joining()
mapToInt()
mapToDouble()
summaryStatistics()
parallelStream()
```

---

# 67. One-Line Revision

```text
filter()       → select
map()          → transform
flatMap()      → flatten
distinct()     → remove duplicates
sorted()       → sort
limit()        → take N
skip()         → skip N
peek()         → debug
forEach()      → process
collect()      → gather
reduce()       → combine
count()        → count
min()/max()    → extreme value
findFirst()    → first
findAny()      → any
anyMatch()     → at least one
allMatch()     → all
noneMatch()    → none
```

---

# 68. Recommended Classroom Flow

For a practical-first Java class:

### Part 1 — Foundation

1. What is Stream API?
2. Collection vs Stream
3. Stream pipeline
4. Lazy evaluation
5. Intermediate vs terminal operations

### Part 2 — Core Operations

6. `filter()`
7. `map()`
8. `flatMap()`
9. `distinct()`
10. `sorted()`
11. `limit()`
12. `skip()`

### Part 3 — Terminal Operations

13. `forEach()`
14. `count()`
15. `min()`
16. `max()`
17. `findFirst()`
18. `findAny()`
19. `anyMatch()`
20. `allMatch()`
21. `noneMatch()`
22. `reduce()`

### Part 4 — Collectors

23. `toList()`
24. `toSet()`
25. `toMap()`
26. `joining()`
27. `groupingBy()`
28. `partitioningBy()`
29. `counting()`
30. `averagingDouble()`
31. `summingDouble()`

### Part 5 — Advanced

32. Primitive streams
33. `IntStream`
34. `LongStream`
35. `DoubleStream`
36. `mapToInt()`
37. `mapToDouble()`
38. `mapToLong()`
39. `range()`
40. `rangeClosed()`
41. `boxed()`
42. `summaryStatistics()`
43. Parallel streams
44. Short-circuit operations
45. Stream interview questions

---

# 69. Final Formula

The most important Stream API pattern to remember:

```java
SOURCE
    .stream()
    .filter(...)
    .map(...)
    .sorted(...)
    .distinct(...)
    .limit(...)
    .collect(...);
```

### Remember:

```text
Collection = stores data
Stream     = processes data

Intermediate = returns Stream
Terminal     = produces result

filter  = select
map     = transform
flatMap = flatten
collect = gather
reduce  = combine
```
