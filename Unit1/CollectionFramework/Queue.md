# Java Queue Framework - Complete Notes
## Queue, PriorityQueue, Deque, ArrayDeque & LinkedList

> **Topic:** Java Collections Framework - Queue Family
>
> **Language:** Java 8+
>
> **Level:** Beginner → Advanced
>
> **Objective:** Learn every Queue implementation, its internal working, methods, and real-world usage.

---

# Table of Contents

```
1. Introduction to Queue
2. Queue Hierarchy
3. Queue Interface
4. Queue Implementations
5. Queue Interface Methods
6. Collection Methods
7. Traversing Queue
8. PriorityQueue
9. PriorityQueue Internal Working
10. PriorityQueue Constructors
11. PriorityQueue Methods
12. PriorityQueue Practical Examples
13. Deque Interface
14. Deque Methods
15. ArrayDeque
16. ArrayDeque Internal Working
17. ArrayDeque Methods
18. LinkedList as Queue & Deque
19. LinkedList Internal Working
20. Queue vs Deque vs PriorityQueue
21. Time Complexity
22. Best Practices
23. Interview Questions
```

---

# Queue Hierarchy

```
                    Iterable
                        │
                   Collection
                        │
                    Queue<E>
                  /           \
             Deque<E>     PriorityQueue<E>
                │
      -------------------------
      │                       │
 ArrayDeque<E>          LinkedList<E>
```

---

# Queue Interface

Queue is a **Collection Interface** that follows the

# FIFO (First In First Out)

principle.

```
Insert

A
B
C
D

↓

Remove

A
B
C
D
```

---

# Real Life Examples

- Printer Queue
- Ticket Counter
- CPU Scheduling
- Restaurant Order Queue
- Hospital Token System
- BFS Algorithm
- Task Scheduling
- Message Queues

---

# Queue Implementations

| Class | Ordering | Internal Structure | Null Allowed |
|---------|-----------|-------------------|--------------|
| LinkedList | FIFO | Doubly Linked List | Yes |
| ArrayDeque | FIFO | Circular Array | No |
| PriorityQueue | Priority Order | Binary Heap | No |

---

# Queue Interface Methods

Queue defines six primary methods.

| Operation | Throws Exception | Returns Special Value |
|------------|------------------|------------------------|
| Insert | add() | offer() |
| Remove | remove() | poll() |
| Read Head | element() | peek() |

---

# Queue Methods

## Insert

```java
add(E element)

offer(E element)
```

---

## Remove

```java
remove()

poll()
```

---

## Read Head

```java
element()

peek()
```

---

# Collection Methods

Queue inherits every Collection method.

```
addAll()

contains()

containsAll()

removeAll()

retainAll()

removeIf()

iterator()

forEach()

stream()

parallelStream()

spliterator()

size()

clear()

isEmpty()

toArray()

equals()

hashCode()
```

---

# Traversing Queue

## For Loop

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

# PriorityQueue

PriorityQueue **does NOT follow FIFO.**

It follows

```
Priority
```

By default

```
Smallest Element

↓

Highest Priority
```

---

## Example

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>();

pq.offer(50);
pq.offer(10);
pq.offer(40);
pq.offer(5);

System.out.println(pq);
```

Output

```
[5,10,40,50]
```

---

# Internal Working

PriorityQueue uses

```
Binary Heap
```

Internally

```
           5
         /   \
       10     40
      /
    50
```

The smallest element always stays at the root.

---

# PriorityQueue Constructors

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>();
```

---

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>(20);
```

---

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>(Collections.reverseOrder());
```

Creates Max Heap.

---

```java
PriorityQueue<Student> pq =
new PriorityQueue<>(
(a,b)->a.age-b.age
);
```

Custom Comparator.

---

# PriorityQueue Methods

```
offer()

add()

poll()

remove()

peek()

element()

contains()

size()

clear()

iterator()

stream()

forEach()
```

---

# PriorityQueue Example

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>();

pq.offer(30);
pq.offer(10);
pq.offer(20);

while(!pq.isEmpty())
{
    System.out.println(pq.poll());
}
```

Output

```
10

20

30
```

---

# Max Heap

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>(
Collections.reverseOrder()
);

pq.offer(30);
pq.offer(10);
pq.offer(50);

while(!pq.isEmpty())
{
    System.out.println(pq.poll());
}
```

Output

```
50

30

10
```

---

# Deque Interface

Deque means

```
Double Ended Queue
```

Insertion and deletion happen from

```
Front

AND

Rear
```

---

# Deque Diagram

```
Front

↓

10 20 30 40

↑

Rear
```

---

# Deque Methods

## Front Operations

```java
addFirst()

offerFirst()

removeFirst()

pollFirst()

getFirst()

peekFirst()
```

---

## Rear Operations

```java
addLast()

offerLast()

removeLast()

pollLast()

getLast()

peekLast()
```

---

## Stack Operations

```java
push()

pop()

peek()
```

Deque can behave like

- Queue
- Stack

---

# ArrayDeque

ArrayDeque implements

```
Deque
```

Internally it uses

```
Resizable Circular Array
```

---

# Circular Array

```
Front

↓

10

20

30

40

↑

Rear
```

When rear reaches the end

it wraps around.

```
40

↓

10

20

30
```

---

# Why ArrayDeque?

Advantages

✔ Faster than Stack

✔ Faster than LinkedList

✔ No Synchronization

✔ Continuous Memory

---

# ArrayDeque Constructors

```java
ArrayDeque<Integer> dq =
new ArrayDeque<>();
```

---

```java
ArrayDeque<Integer> dq =
new ArrayDeque<>(20);
```

---

# Queue Operations

```java
offer()

poll()

peek()
```

---

# Deque Operations

```java
offerFirst()

offerLast()

pollFirst()

pollLast()

peekFirst()

peekLast()
```

---

# Stack Operations

```java
push()

pop()

peek()
```

---

# Extra Methods

```java
descendingIterator()

clone()

contains()

removeFirstOccurrence()

removeLastOccurrence()
```

---

# ArrayDeque Example

```java
ArrayDeque<Integer> dq =
new ArrayDeque<>();

dq.offerFirst(20);

dq.offerLast(30);

dq.offerFirst(10);

System.out.println(dq);
```

Output

```
[10,20,30]
```

---

# Stack Example

```java
ArrayDeque<Integer> stack =
new ArrayDeque<>();

stack.push(10);
stack.push(20);
stack.push(30);

while(!stack.isEmpty())
{
    System.out.println(stack.pop());
}
```

Output

```
30

20

10
```

LIFO behavior.

---

# LinkedList

LinkedList implements

```
List

Queue

Deque
```

So it supports

- List methods
- Queue methods
- Deque methods

---

# Internal Working

LinkedList is implemented as

```
Doubly Linked List
```

```
NULL

↓

Prev | Data | Next

↓

Prev | Data | Next

↓

Prev | Data | Next

↓

NULL
```

---

# Queue Operations

```java
offer()

poll()

peek()
```

---

# Deque Operations

```java
offerFirst()

offerLast()

pollFirst()

pollLast()

peekFirst()

peekLast()
```

---

# List Operations

```java
add()

get()

set()

remove()

subList()

indexOf()

lastIndexOf()

listIterator()
```

---

# Example

```java
LinkedList<String> list =
new LinkedList<>();

list.offer("Java");

list.offer("Python");

list.offerFirst("C++");

list.offerLast("Go");

System.out.println(list);
```

Output

```
[C++, Java, Python, Go]
```

---

# Queue vs PriorityQueue vs Deque

| Feature | Queue | PriorityQueue | Deque |
|----------|--------|---------------|--------|
| Order | FIFO | Priority | Both Ends |
| Front Insert | No | No | Yes |
| Rear Insert | Yes | Yes | Yes |
| Front Remove | Yes | Yes | Yes |
| Rear Remove | No | No | Yes |
| Stack Support | No | No | Yes |

---

# Which One Should I Use?

```
Simple FIFO

↓

LinkedList
```

---

```
Fast Queue

↓

ArrayDeque
```

---

```
Priority Scheduling

↓

PriorityQueue
```

---

```
Queue + Stack

↓

Deque
```

---

# Time Complexity

| Operation | LinkedList | ArrayDeque | PriorityQueue |
|------------|------------|------------|---------------|
| add() | O(1) | O(1) | O(log n) |
| offer() | O(1) | O(1) | O(log n) |
| remove() | O(1) | O(1) | O(log n) |
| poll() | O(1) | O(1) | O(log n) |
| peek() | O(1) | O(1) | O(1) |
| contains() | O(n) | O(n) | O(n) |
| iterator() | O(n) | O(n) | O(n) |

---

# Best Practices

✅ Use the `Queue` interface for FIFO operations.

```java
Queue<Integer> queue =
new LinkedList<>();
```

---

✅ Use `ArrayDeque` instead of the legacy `Stack` class.

---

✅ Use `PriorityQueue` for scheduling and priority-based processing.

---

✅ Use `Deque` when insertion/removal from both ends is required.

---

# Interview Questions

### Q1 Why is Queue called FIFO?

The first element inserted is the first element removed.

---

### Q2 Which Queue implementation is the fastest?

For general FIFO operations:

```
ArrayDeque
```

---

### Q3 Why doesn't PriorityQueue follow FIFO?

Because elements are removed according to **priority (natural ordering or comparator)** rather than insertion order.

---

### Q4 Can ArrayDeque be used as a Stack?

Yes.

It provides:

```java
push()

pop()

peek()
```

---

### Q5 Which class implements both List and Queue?

```
LinkedList
```

---

### Q6 Which implementation should be used for a task scheduler?

```
PriorityQueue
```

---

### Q7 Which implementation should be used for browser history (forward/back)?

```
Deque (ArrayDeque)
```

---

# Complete Queue Framework Summary

```
Queue
│
├── LinkedList
│     ├── Queue
│     ├── Deque
│     └── List
│
├── ArrayDeque
│     ├── Queue
│     ├── Deque
│     └── Stack Operations
│
└── PriorityQueue
      ├── Binary Heap
      ├── Min Heap
      ├── Max Heap
      └── Custom Comparator
```

---

# Next Topics to Learn

- BlockingQueue
- ConcurrentLinkedQueue
- DelayQueue
- ArrayBlockingQueue
- LinkedBlockingQueue
- PriorityBlockingQueue
- TransferQueue
- SynchronousQueue

These are part of **Java Concurrent Collections** and are commonly used in multithreading and enterprise applications.





# Java PriorityQueue - Constructors & Comparators (Complete Notes)

> **Topic:** PriorityQueue Constructors & Comparator
>
> **Language:** Java 8+
>
> **Level:** Beginner → Advanced

---

# Table of Contents

1. What is PriorityQueue?
2. How PriorityQueue Works
3. Natural Ordering
4. Constructors
5. Comparator
6. Min Heap
7. Max Heap
8. Custom Object Sorting
9. Lambda Comparator
10. Interview Questions

---

# What is PriorityQueue?

A `PriorityQueue` is a Queue implementation that **does not follow FIFO**.

Instead, it removes elements according to **priority**.

By default, Java uses **Natural Ordering**.

```
Elements Added

30
10
50
20

↓

Removed

10
20
30
50
```

Smallest element gets the **highest priority**.

---

# Internal Working

PriorityQueue internally uses a

```
Binary Heap
```

(Default: **Min Heap**)

```
        10
       /  \
     20    50
    /
   30
```

Root always contains the highest-priority element.

---

# Constructors of PriorityQueue

Java provides several constructors.

---

# 1. Default Constructor

### Syntax

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
```

### Meaning

- Initial capacity = **11**
- Natural ordering
- Min Heap

### Example

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();

pq.offer(30);
pq.offer(10);
pq.offer(50);
pq.offer(20);

System.out.println(pq);
```

Possible Output

```
[10, 20, 50, 30]
```

> **Note:** The printed order is **not sorted**. It represents the internal heap structure.

To get sorted output:

```java
while(!pq.isEmpty())
{
    System.out.println(pq.poll());
}
```

Output

```
10
20
30
50
```

---

# 2. Constructor with Initial Capacity

### Syntax

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>(20);
```

### Meaning

```
Capacity = 20

Natural Ordering

Min Heap
```

The queue can grow beyond 20 if needed. The value **20** is only the initial capacity.

### Example

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>(20);

pq.add(100);
pq.add(40);
pq.add(60);

System.out.println(pq.poll());
```

Output

```
40
```

---

# Why Specify Capacity?

Suppose you know you will insert

```
100000 elements
```

Instead of repeatedly resizing,

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>(100000);
```

improves performance by reducing reallocations.

---

# 3. Constructor Using Comparator

### Syntax

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>(Comparator);
```

### Meaning

The comparator decides

```
Which element has higher priority?
```

instead of natural ordering.

---

# Max Heap Example

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>(
Collections.reverseOrder()
);
```

### Meaning

Normally

```
10

20

30

40
```

Smallest comes first.

Now

```
40

30

20

10
```

Largest gets highest priority.

---

### Example

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>(
Collections.reverseOrder()
);

pq.offer(10);
pq.offer(50);
pq.offer(30);
pq.offer(20);

while(!pq.isEmpty())
{
    System.out.println(pq.poll());
}
```

Output

```
50
30
20
10
```

---

# 4. Constructor Using Another Collection

### Syntax

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>(collection);
```

### Meaning

Creates a PriorityQueue from an existing collection.

### Example

```java
List<Integer> list =
Arrays.asList(50,10,30,20);

PriorityQueue<Integer> pq =
new PriorityQueue<>(list);

while(!pq.isEmpty())
{
    System.out.println(pq.poll());
}
```

Output

```
10
20
30
50
```

---

# 5. Constructor Using Another PriorityQueue

### Syntax

```java
PriorityQueue<Integer> copy =
new PriorityQueue<>(oldQueue);
```

### Example

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>();

pq.add(30);
pq.add(10);
pq.add(20);

PriorityQueue<Integer> copy =
new PriorityQueue<>(pq);

System.out.println(copy);
```

The new queue contains the same elements and ordering rules.

---

# What is a Comparator?

A **Comparator** is an object that tells Java

```
How should two objects be compared?
```

Without Comparator

```
Java

↓

Natural Ordering
```

With Comparator

```
You Decide
```

---

# Comparator Method

```java
compare(a,b)
```

Returns

```
Negative

↓

a comes first
```

```
Positive

↓

b comes first
```

```
Zero

↓

Both are equal
```

---

# Example

```java
Comparator<Integer> comp =
(a,b)->a-b;
```

Meaning

```
Small Number

↓

Higher Priority
```

---

# Reverse Comparator

```java
Comparator<Integer> comp =
(a,b)->b-a;
```

Meaning

```
Large Number

↓

Higher Priority
```

---

# Lambda Comparator

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>(

(a,b)->a-b

);
```

Equivalent to

```
Ascending Order
```

---

# Descending Comparator

```java
PriorityQueue<Integer> pq =
new PriorityQueue<>(

(a,b)->b-a

);
```

Equivalent to

```
Descending Order
```

---

# Comparator for Strings

```java
PriorityQueue<String> pq =
new PriorityQueue<>(
(a,b)->a.compareTo(b)
);

pq.offer("Java");
pq.offer("Python");
pq.offer("C");

while(!pq.isEmpty())
{
    System.out.println(pq.poll());
}
```

Output

```
C
Java
Python
```

---

# Reverse String Order

```java
PriorityQueue<String> pq =
new PriorityQueue<>(
(a,b)->b.compareTo(a)
);
```

Output

```
Python
Java
C
```

---

# Comparator with Custom Objects

## Student Class

```java
class Student
{
    int id;
    String name;
    int marks;

    Student(int id,String name,int marks)
    {
        this.id=id;
        this.name=name;
        this.marks=marks;
    }

    @Override
    public String toString()
    {
        return id+" "+name+" "+marks;
    }
}
```

---

## Sort by Marks

```java
PriorityQueue<Student> pq =
new PriorityQueue<>(

(a,b)->a.marks-b.marks

);

pq.offer(new Student(1,"Akash",80));
pq.offer(new Student(2,"Rahul",60));
pq.offer(new Student(3,"Aman",95));

while(!pq.isEmpty())
{
    System.out.println(pq.poll());
}
```

Output

```
2 Rahul 60
1 Akash 80
3 Aman 95
```

---

## Highest Marks First

```java
PriorityQueue<Student> pq =
new PriorityQueue<>(

(a,b)->b.marks-a.marks

);
```

Output

```
3 Aman 95
1 Akash 80
2 Rahul 60
```

---

# Comparator vs Comparable

| Comparable | Comparator |
|------------|------------|
| Implemented inside the class | Implemented outside the class |
| Uses `compareTo()` | Uses `compare()` |
| One natural ordering | Multiple custom orderings |
| Modifies the class | Doesn't modify the class |

---

# Summary of Constructors

| Constructor | Meaning |
|-------------|---------|
| `new PriorityQueue<>()` | Default Min Heap with natural ordering |
| `new PriorityQueue<>(capacity)` | Sets initial capacity |
| `new PriorityQueue<>(Comparator)` | Uses custom ordering |
| `new PriorityQueue<>(Collection)` | Creates from an existing collection |
| `new PriorityQueue<>(PriorityQueue)` | Creates a copy of another priority queue |

---

# Interview Questions

### Q1 Why does PriorityQueue not follow FIFO?

Because elements are removed based on **priority**, not insertion order.

---

### Q2 What is the default ordering of PriorityQueue?

Natural ordering (ascending for numbers), implemented as a **Min Heap**.

---

### Q3 What does `Collections.reverseOrder()` do?

It provides a comparator that reverses the natural ordering, effectively creating a **Max Heap**.

---

### Q4 What is the difference between Comparable and Comparator?

- **Comparable** defines one natural ordering inside the class.
- **Comparator** allows multiple custom orderings outside the class.

---

### Q5 Does `System.out.println(pq)` print sorted elements?

**No.** It prints the internal heap representation. To retrieve elements in priority order, repeatedly call `poll()`.
