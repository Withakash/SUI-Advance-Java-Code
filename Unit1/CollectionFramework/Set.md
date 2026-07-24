# Java Set Collection Framework (Complete Notes)

> **Topic:** Java Collections Framework - Set Interface
> **Level:** Beginner to Advanced
> **Language:** Java
> **Version:** Java 8+

---

# Table of Contents

1. What is Set?
2. Why Set?
3. Features of Set
4. Set Hierarchy
5. Types of Set
6. HashSet
7. LinkedHashSet
8. TreeSet
9. Common Methods
10. Traversing Set
11. Java 8 Methods
12. Time Complexity
13. Interview Questions
14. Live Practice Programs

---

# What is Set?

Set is an interface in the Java Collection Framework.

It stores **unique elements only**.

Duplicate values are not allowed.

```java
Set<String> set = new HashSet<>();
```

---

# Real Life Example

Imagine a college attendance register.

Student IDs

101
102
103
101

Student **101** cannot be present twice.

Exactly the same concept is used by Set.

---

# Why Set?

Without Set

```
Java
Python
Java
C++
Python
```

With Set

```
Java
Python
C++
```

Duplicates are removed automatically.

---

# Features of Set

- No duplicate elements
- Stores Objects
- Can contain one null (HashSet & LinkedHashSet)
- Not index based
- Faster searching than List
- Supports Generics
- Iterable

---

# Set Hierarchy

```
                Iterable
                    │
               Collection
                    │
                  Set
          ┌────────┼────────┐
          │        │        │
      HashSet LinkedHashSet TreeSet
```

---

# Types of Set

## 1. HashSet

- Fastest
- No duplicate
- No insertion order
- Uses Hash Table

Example

```java
Set<String> set = new HashSet<>();

set.add("Java");
set.add("Python");
set.add("Java");

System.out.println(set);
```

Output

```
[Python, Java]
```

Order is random.

---

## 2. LinkedHashSet

Maintains insertion order.

```java
Set<String> set = new LinkedHashSet<>();

set.add("Java");
set.add("Python");
set.add("C++");

System.out.println(set);
```

Output

```
[Java, Python, C++]
```

---

## 3. TreeSet

Stores elements in sorted order.

```java
Set<Integer> set = new TreeSet<>();

set.add(50);
set.add(10);
set.add(40);
set.add(20);

System.out.println(set);
```

Output

```
[10,20,40,50]
```

---

# Common Methods

---

# add()

Adds an element.

Returns boolean.

```java
Set<String> set = new HashSet<>();

System.out.println(set.add("Java"));

System.out.println(set.add("Java"));

System.out.println(set);
```

Output

```
true
false
[Java]
```

---

# addAll()

```java
Set<Integer> s1 = new HashSet<>();

s1.add(10);
s1.add(20);

Set<Integer> s2 = new HashSet<>();

s2.add(30);
s2.add(40);

s1.addAll(s2);

System.out.println(s1);
```

Output

```
[10,20,30,40]
```

---

# remove()

```java
Set<String> set = new HashSet<>();

set.add("Java");
set.add("Python");

set.remove("Python");

System.out.println(set);
```

Output

```
[Java]
```

---

# removeAll()

```java
Set<Integer> s1 = new HashSet<>();

s1.addAll(Arrays.asList(1,2,3,4));

Set<Integer> s2 = new HashSet<>();

s2.addAll(Arrays.asList(2,4));

s1.removeAll(s2);

System.out.println(s1);
```

Output

```
[1,3]
```

---

# retainAll()

Keeps only common elements.

```java
Set<Integer> s1 = new HashSet<>();

s1.addAll(Arrays.asList(1,2,3,4));

Set<Integer> s2 = new HashSet<>();

s2.addAll(Arrays.asList(2,4,6));

s1.retainAll(s2);

System.out.println(s1);
```

Output

```
[2,4]
```

---

# contains()

```java
Set<String> set = new HashSet<>();

set.add("Java");

System.out.println(set.contains("Java"));
```

Output

```
true
```

---

# containsAll()

```java
Set<Integer> set = new HashSet<>();

set.addAll(Arrays.asList(10,20,30));

System.out.println(
set.containsAll(Arrays.asList(10,20))
);
```

Output

```
true
```

---

# size()

```java
Set<Integer> set = new HashSet<>();

set.add(10);
set.add(20);

System.out.println(set.size());
```

Output

```
2
```

---

# isEmpty()

```java
Set<String> set = new HashSet<>();

System.out.println(set.isEmpty());

set.add("Java");

System.out.println(set.isEmpty());
```

Output

```
true
false
```

---

# clear()

```java
Set<Integer> set = new HashSet<>();

set.add(10);
set.add(20);

set.clear();

System.out.println(set);
```

Output

```
[]
```

---

# iterator()

```java
Set<String> set = new HashSet<>();

set.add("Java");
set.add("Python");
set.add("C++");

Iterator<String> itr = set.iterator();

while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

---

# toArray()

```java
Set<String> set = new HashSet<>();

set.add("Java");
set.add("Python");

Object arr[] = set.toArray();

System.out.println(Arrays.toString(arr));
```

Output

```
[Java, Python]
```

---

# toArray(T[])

```java
String arr[] = set.toArray(new String[0]);

System.out.println(Arrays.toString(arr));
```

---

# equals()

```java
Set<Integer> s1 = new HashSet<>();

s1.addAll(Arrays.asList(1,2,3));

Set<Integer> s2 = new HashSet<>();

s2.addAll(Arrays.asList(3,2,1));

System.out.println(s1.equals(s2));
```

Output

```
true
```

---

# hashCode()

```java
Set<Integer> set = new HashSet<>();

set.add(10);
set.add(20);

System.out.println(set.hashCode());
```

---

# Java 8 Methods

---

# forEach()

```java
Set<String> set = new HashSet<>();

set.add("Java");
set.add("Python");
set.add("C++");

set.forEach(System.out::println);
```

Equivalent

```java
set.forEach(
element ->
System.out.println(element)
);
```

---

# removeIf()

```java
Set<Integer> set = new HashSet<>();

set.addAll(Arrays.asList(10,15,20,25));

set.removeIf(
x -> x%2==0
);

System.out.println(set);
```

Output

```
[15,25]
```

---

# stream()

```java
Set<Integer> set = new HashSet<>();

set.addAll(Arrays.asList(10,20,30,40));

set.stream()
.filter(x->x>20)
.forEach(System.out::println);
```

Output

```
30
40
```

---

# parallelStream()

```java
Set<Integer> set = new HashSet<>();

set.addAll(Arrays.asList(10,20,30,40));

set.parallelStream()
.forEach(System.out::println);
```

Order is not guaranteed.

---

# spliterator()

```java
Set<String> set = new HashSet<>();

set.add("Java");
set.add("Python");

Spliterator<String> sp =
set.spliterator();

sp.forEachRemaining(System.out::println);
```

---

# Traversing Set

## Enhanced For Loop

```java
for(String data : set)
{
    System.out.println(data);
}
```

---

## Iterator

```java
Iterator<String> itr =
set.iterator();

while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

---

## Lambda

```java
set.forEach(System.out::println);
```

---

## Stream

```java
set.stream()
.forEach(System.out::println);
```

---

# Time Complexity

| Method | Complexity |
|---------|------------|
| add() | O(1) |
| remove() | O(1) |
| contains() | O(1) |
| size() | O(1) |
| iterator() | O(n) |
| addAll() | O(n) |
| removeAll() | O(n) |
| retainAll() | O(n) |
| stream() | O(n) |

---

# HashSet vs LinkedHashSet vs TreeSet

| Feature | HashSet | LinkedHashSet | TreeSet |
|----------|----------|---------------|----------|
| Duplicate | ❌ | ❌ | ❌ |
| Order | Random | Insertion | Sorted |
| Null | One | One | Not Allowed |
| Performance | Fastest | Fast | Slower |
| Backed By | Hash Table | Hash Table + Linked List | Red-Black Tree |

---

# Interview Questions

### Q1 Can Set store duplicate values?

No.

---

### Q2 Which Set maintains insertion order?

LinkedHashSet

---

### Q3 Which Set stores sorted elements?

TreeSet

---

### Q4 Which Set is fastest?

HashSet

---

### Q5 Does TreeSet allow null?

No.

---

### Q6 Can we access Set using index?

No.

---

### Q7 Which traversal methods are available?

- Enhanced For Loop
- Iterator
- forEach()
- Stream API
- Spliterator

---

# Practice Questions

1. Remove duplicates from an ArrayList using HashSet.
2. Find common elements between two Sets.
3. Sort numbers using TreeSet.
4. Store student names without duplicates.
5. Count unique words in a paragraph using Set.
6. Compare HashSet, LinkedHashSet, and TreeSet with the same data.
7. Remove all even numbers using `removeIf()`.
8. Print all elements using `iterator()`, `forEach()`, and `stream()`.
