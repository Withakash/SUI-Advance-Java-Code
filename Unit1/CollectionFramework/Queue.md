# Java Queue Interface - Complete Notes

> **Topic:** Queue Interface in Java Collection Framework  
> **Language:** Java 8+  
> **Level:** Beginner to Advanced

---

# Table of Contents

1. Introduction
2. Queue Hierarchy
3. Queue Implementations
4. Creating Queue Objects
5. Queue Methods
6. Collection Methods Inherited
7. Traversing Queue
8. Queue vs Deque
9. Time Complexity
10. Interview Questions

---

# What is Queue?

A **Queue** is a linear data structure that follows the **FIFO (First In First Out)** principle.

The element inserted first will be removed first.

Example:

```
Insertion

A
B
C
D

Removal

A
B
C
D
```

Real Life Examples

- Ticket Counter
- Printer Queue
- CPU Scheduling
- Call Center
- Food Ordering System

---

# Queue Hierarchy

```
                 Iterable
                     │
                Collection
                     │
                 Queue<E>
               /          \
          Deque<E>     PriorityQueue
             │
      -----------------
      │               │
 ArrayDeque      LinkedList
```

---

# Queue Implementations

| Class | Order | Duplicate | Null |
|---------|-------|-----------|------|
| LinkedList | FIFO | Yes | Yes |
| ArrayDeque | FIFO | Yes | No |
| PriorityQueue | Priority Order | Yes | One null not allowed |

---

# Creating Queue

## Using LinkedList

```java
Queue<String> queue = new LinkedList<>();
```

---

## Using ArrayDeque

```java
Queue<Integer> queue = new ArrayDeque<>();
```

---

## Using PriorityQueue

```java
Queue<Integer> queue = new PriorityQueue<>();
```

---

# Queue Methods

Queue provides six important methods.

| Operation | Throws Exception | Returns Special Value |
|------------|------------------|------------------------|
| Insert | add() | offer() |
| Remove | remove() | poll() |
| Read Head | element() | peek() |

---

# 1. add()

Adds an element into the queue.

Returns **true** if successful.

Throws Exception if insertion fails.

```java
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Queue<String> queue = new LinkedList<>();

        queue.add("Java");
        queue.add("Python");
        queue.add("C++");

        System.out.println(queue);
    }
}
```

Output

```
[Java, Python, C++]
```

---

# 2. offer()

Safely inserts an element.

Returns **true** or **false**.

```java
Queue<String> queue = new LinkedList<>();

System.out.println(queue.offer("Java"));
System.out.println(queue.offer("Python"));

System.out.println(queue);
```

Output

```
true
true

[Java, Python]
```

Difference

```
add()   → throws Exception

offer() → returns false
```

---

# 3. remove()

Removes the front element.

Throws Exception if queue is empty.

```java
Queue<String> queue = new LinkedList<>();

queue.offer("Java");
queue.offer("Python");
queue.offer("C++");

System.out.println(queue.remove());

System.out.println(queue);
```

Output

```
Java

[Python, C++]
```

---

# 4. poll()

Safely removes first element.

Returns **null** if queue is empty.

```java
Queue<String> queue = new LinkedList<>();

queue.offer("Java");
queue.offer("Python");

System.out.println(queue.poll());

System.out.println(queue);
```

Output

```
Java

[Python]
```

Difference

```
remove() → Exception

poll() → null
```

---

# 5. element()

Returns first element.

Does not remove it.

Throws Exception if queue is empty.

```java
Queue<String> queue = new LinkedList<>();

queue.offer("Java");
queue.offer("Python");

System.out.println(queue.element());

System.out.println(queue);
```

Output

```
Java

[Java, Python]
```

---

# 6. peek()

Returns first element.

Does not remove it.

Returns **null** if queue is empty.

```java
Queue<String> queue = new LinkedList<>();

queue.offer("Java");
queue.offer("Python");

System.out.println(queue.peek());

System.out.println(queue);
```

Output

```
Java

[Java, Python]
```

Difference

```
element() → Exception

peek() → null
```

---

# Collection Methods Available in Queue

Since Queue extends Collection, these methods are inherited.

---

# addAll()

```java
Queue<Integer> q1 = new LinkedList<>();
q1.offer(10);
q1.offer(20);

Queue<Integer> q2 = new LinkedList<>();
q2.offer(30);
q2.offer(40);

q1.addAll(q2);

System.out.println(q1);
```

Output

```
[10, 20, 30, 40]
```

---

# contains()

```java
Queue<String> queue = new LinkedList<>();

queue.offer("Java");
queue.offer("Python");

System.out.println(queue.contains("Python"));
```

Output

```
true
```

---

# containsAll()

```java
Queue<Integer> queue = new LinkedList<>();

queue.addAll(Arrays.asList(10,20,30));

System.out.println(
queue.containsAll(Arrays.asList(10,20))
);
```

Output

```
true
```

---

# removeAll()

```java
Queue<Integer> queue = new LinkedList<>();

queue.addAll(Arrays.asList(10,20,30,40));

queue.removeAll(Arrays.asList(20,40));

System.out.println(queue);
```

Output

```
[10, 30]
```

---

# retainAll()

```java
Queue<Integer> queue = new LinkedList<>();

queue.addAll(Arrays.asList(10,20,30,40));

queue.retainAll(Arrays.asList(20,40));

System.out.println(queue);
```

Output

```
[20, 40]
```

---

# clear()

```java
Queue<Integer> queue = new LinkedList<>();

queue.offer(10);
queue.offer(20);

queue.clear();

System.out.println(queue);
```

Output

```
[]
```

---

# size()

```java
Queue<Integer> queue = new LinkedList<>();

queue.offer(10);
queue.offer(20);

System.out.println(queue.size());
```

Output

```
2
```

---

# isEmpty()

```java
Queue<String> queue = new LinkedList<>();

System.out.println(queue.isEmpty());

queue.offer("Java");

System.out.println(queue.isEmpty());
```

Output

```
true

false
```

---

# iterator()

```java
Queue<String> queue = new LinkedList<>();

queue.offer("Java");
queue.offer("Python");
queue.offer("C++");

Iterator<String> itr = queue.iterator();

while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

---

# toArray()

```java
Queue<String> queue = new LinkedList<>();

queue.offer("Java");
queue.offer("Python");

Object arr[] = queue.toArray();

System.out.println(Arrays.toString(arr));
```

Output

```
[Java, Python]
```

---

# toArray(T[])

```java
String arr[] = queue.toArray(new String[0]);

System.out.println(Arrays.toString(arr));
```

---

# forEach()

```java
Queue<String> queue = new LinkedList<>();

queue.offer("Java");
queue.offer("Python");
queue.offer("C++");

queue.forEach(System.out::println);
```

Equivalent

```java
queue.forEach(
element ->
System.out.println(element)
);
```

---

# removeIf()

```java
Queue<Integer> queue = new LinkedList<>();

queue.addAll(Arrays.asList(10,15,20,25));

queue.removeIf(
x -> x % 2 == 0
);

System.out.println(queue);
```

Output

```
[15, 25]
```

---

# stream()

```java
Queue<Integer> queue = new LinkedList<>();

queue.addAll(Arrays.asList(10,20,30,40));

queue.stream()
.filter(x -> x > 20)
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
Queue<Integer> queue = new LinkedList<>();

queue.addAll(Arrays.asList(10,20,30,40));

queue.parallelStream()
.forEach(System.out::println);
```

Output order is **not guaranteed**.

---

# spliterator()

```java
Queue<String> queue = new LinkedList<>();

queue.offer("Java");
queue.offer("Python");

Spliterator<String> sp =
queue.spliterator();

sp.forEachRemaining(System.out::println);
```

---

# equals()

```java
Queue<Integer> q1 = new LinkedList<>();
q1.addAll(Arrays.asList(10,20));

Queue<Integer> q2 = new LinkedList<>();
q2.addAll(Arrays.asList(10,20));

System.out.println(q1.equals(q2));
```

Output

```
true
```

---

# hashCode()

```java
Queue<Integer> queue = new LinkedList<>();

queue.offer(10);
queue.offer(20);

System.out.println(queue.hashCode());
```

---

# Traversing Queue

## Enhanced For Loop

```java
for(String value : queue)
{
    System.out.println(value);
}
```

---

## Iterator

```java
Iterator<String> itr = queue.iterator();

while(itr.hasNext())
{
    System.out.println(itr.next());
}
```

---

## Lambda

```java
queue.forEach(System.out::println);
```

---

## Stream API

```java
queue.stream()
     .forEach(System.out::println);
```

---

# Time Complexity

| Method | Complexity |
|----------|------------|
| add() | O(1) |
| offer() | O(1) |
| remove() | O(1) |
| poll() | O(1) |
| peek() | O(1) |
| element() | O(1) |
| contains() | O(n) |
| iterator() | O(n) |

---

# Interview Questions

### Q1 What is Queue?

A Queue follows FIFO (First In First Out).

---

### Q2 Difference between add() and offer()?

- `add()` throws an exception on failure.
- `offer()` returns `false` on failure.

---

### Q3 Difference between remove() and poll()?

- `remove()` throws an exception if the queue is empty.
- `poll()` returns `null` if the queue is empty.

---

### Q4 Difference between element() and peek()?

- `element()` throws an exception if the queue is empty.
- `peek()` returns `null` if the queue is empty.

---

### Q5 Which Queue maintains priority?

`PriorityQueue`

---

### Q6 Which Queue is best for FIFO operations?

- `LinkedList`
- `ArrayDeque`

---

# Summary

- **Queue** follows FIFO.
- Main implementations: **LinkedList**, **ArrayDeque**, **PriorityQueue**.
- Six core methods: `add()`, `offer()`, `remove()`, `poll()`, `element()`, `peek()`.
- Inherits all common `Collection` methods such as `addAll()`, `contains()`, `iterator()`, `stream()`, and more.
