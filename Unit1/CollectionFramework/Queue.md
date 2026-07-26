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
