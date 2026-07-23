# Java Collections Framework (JCF) - Complete Practical Notes

> Designed for Students | Industry Oriented | Java 8+ | Interview Ready

---

# Table of Contents

1. Introduction to Collection Framework
2. Why Collection Framework?
3. Collection Framework Architecture
4. Collection Hierarchy
5. Iterable Interface
6. Collection Interface
7. List Interface
8. Common List Methods (With Complete Examples)
9. ArrayList (Complete)
10. LinkedList (Complete)
11. Vector (Complete)
12. Stack (Complete)
13. Interview Questions
14. Time Complexity
15. Best Practices

---

# 1. What is Collection Framework?

Java Collection Framework (JCF) is a predefined architecture that provides classes and interfaces to store, manage and manipulate groups of objects dynamically.

Instead of creating arrays manually, Java provides ready-made data structures.

Examples

- Student List
- Employee Database
- Product List
- Shopping Cart
- Browser History
- Undo/Redo
- Music Playlist

---

# Why Collection Framework?

Without Collection Framework

```java
Student s1;
Student s2;
Student s3;
Student s4;
...
```

OR

```java
Student[] students = new Student[100];
```

Problems

✔ Fixed Size

✔ Difficult Searching

✔ Difficult Sorting

✔ Difficult Updating

✔ Cannot Grow Automatically

Collections solve all these problems.

---

# Features

- Dynamic Size
- Built-in Algorithms
- Generic Support
- Sorting
- Searching
- Thread Safe Collections
- Performance Optimized

---

# 2. Collection Framework Architecture

```
                   Iterable
                       │
                iterator()
                       │
                 Collection
      ┌────────────┼────────────┐
      │            │            │
     List         Set         Queue
      │
      │
 ┌────┼───────────────┐
 │    │               │
ArrayList LinkedList Vector
                    │
                  Stack
```

---

# Map Hierarchy (Separate)

```
Map
│
├── HashMap
├── LinkedHashMap
├── TreeMap
├── Hashtable
```

Map is NOT part of Collection Interface.

---

# Iterable Interface

Only one method

```java
Iterator<E> iterator();
```

Used in

```java
for(Integer x : list)
```

---

# Collection Interface

Root Interface of Java Collections

Important Methods

```
add()
remove()
contains()
clear()
size()
isEmpty()
iterator()
toArray()
addAll()
removeAll()
retainAll()
containsAll()
```

---

# List Interface

Characteristics

✔ Ordered

✔ Duplicate Allowed

✔ Index Based

✔ Preserves Insertion Order

Implementations

- ArrayList
- LinkedList
- Vector
- Stack

---

# 3. Common Methods Available in ALL Lists

These methods work on

- ArrayList
- LinkedList
- Vector
- Stack

---

## add()

```java
list.add("Java");
list.add("Python");

System.out.println(list);
```

Output

```
[Java, Python]
```

---

## add(index, element)

```java
list.add(1,"C++");
```

Output

```
[Java, C++, Python]
```

---

## addAll()

```java
List<String> l2 = new ArrayList<>();

l2.add("HTML");
l2.add("CSS");

list.addAll(l2);

System.out.println(list);
```

---

## get()

```java
System.out.println(list.get(0));
```

---

## set()

Replace element

```java
list.set(1,"Spring");

System.out.println(list);
```

---

## remove(index)

```java
list.remove(1);
```

---

## remove(Object)

```java
list.remove("Java");
```

---

## removeAll()

```java
list.removeAll(l2);
```

---

## retainAll()

Keeps only common elements

```java
list.retainAll(l2);
```

---

## clear()

```java
list.clear();
```

---

## contains()

```java
System.out.println(list.contains("Java"));
```

---

## containsAll()

```java
System.out.println(list.containsAll(l2));
```

---

## isEmpty()

```java
System.out.println(list.isEmpty());
```

---

## size()

```java
System.out.println(list.size());
```

---

## indexOf()

```java
System.out.println(list.indexOf("Java"));
```

---

## lastIndexOf()

```java
System.out.println(list.lastIndexOf("Java"));
```

---

## equals()

```java
System.out.println(list1.equals(list2));
```

---

## hashCode()

```java
System.out.println(list.hashCode());
```

---

## iterator()

```java
Iterator<String> it = list.iterator();

while(it.hasNext())
{
    System.out.println(it.next());
}
```

---

## listIterator()

```java
ListIterator<String> it = list.listIterator();

while(it.hasNext())
{
    System.out.println(it.next());
}
```

Backward

```java
while(it.hasPrevious())
{
    System.out.println(it.previous());
}
```

---

## subList()

```java
System.out.println(list.subList(1,4));
```

---

## toArray()

```java
Object arr[] = list.toArray();

for(Object x : arr)
{
    System.out.println(x);
}
```

---

## toArray(T[])

```java
String arr[] = list.toArray(new String[0]);
```

---

## forEach()

```java
list.forEach(System.out::println);
```

---

## removeIf()

```java
list.removeIf(x -> x.startsWith("J"));
```

---

## replaceAll()

```java
list.replaceAll(String::toUpperCase);
```

---

## sort()

```java
list.sort(null);
```

Descending

```java
list.sort(Collections.reverseOrder());
```

---

## stream()

```java
list.stream()
    .filter(x->x.length()>3)
    .forEach(System.out::println);
```

---

## parallelStream()

```java
list.parallelStream()
    .forEach(System.out::println);
```

---

## spliterator()

```java
Spliterator<String> sp = list.spliterator();

sp.forEachRemaining(System.out::println);
```

---

# 4. ArrayList

Package

```java
import java.util.ArrayList;
```

---

## Internal Working

ArrayList internally uses

```
Object[]
```

When full

↓

Creates New Bigger Array

↓

Copies old data

↓

Deletes old array

---

## Characteristics

✔ Dynamic Array

✔ Fast Random Access

✔ Ordered

✔ Duplicate Allowed

✔ Not Thread Safe

✔ Best for Reading

---

## Constructors

```java
ArrayList<Integer> list = new ArrayList<>();

ArrayList<Integer> list2 = new ArrayList<>(20);

ArrayList<Integer> list3 = new ArrayList<>(list);
```

---

## Capacity

Default Capacity

```
10
```

Growth

```
Old + Old/2

10

↓

15

↓

22

↓

33
```

---

## Complete Example

```java
import java.util.*;

public class Demo
{
    public static void main(String args[])
    {
        ArrayList<String> list = new ArrayList<>();

        list.add("Java");
        list.add("Python");
        list.add("C++");

        System.out.println(list);

        list.remove("Python");

        System.out.println(list);

        list.set(1,"Spring");

        System.out.println(list);

        System.out.println(list.contains("Java"));

        list.forEach(System.out::println);
    }
}
```

---

## ArrayList Specific Method

### ensureCapacity()

```java
ArrayList<Integer> list = new ArrayList<>();

list.ensureCapacity(100);
```

---

### trimToSize()

```java
list.trimToSize();
```

Shrinks internal array.

---

# Time Complexity

| Operation | Complexity |
|------------|------------|
| add() | O(1) |
| get() | O(1) |
| set() | O(1) |
| remove(last) | O(1) |
| remove(middle) | O(n) |
| search | O(n) |

---

# 5. LinkedList

Package

```java
import java.util.LinkedList;
```

---

## Internal Working

```
Prev ← Node → Next
```

Every node stores

- Data
- Previous Address
- Next Address

---

## Advantages

Fast Insertion

Fast Deletion

Queue

Deque

Stack

---

## Constructors

```java
LinkedList<Integer> list = new LinkedList<>();
```

---

## LinkedList Specific Methods

### addFirst()

```java
list.addFirst(100);
```

---

### addLast()

```java
list.addLast(500);
```

---

### getFirst()

```java
System.out.println(list.getFirst());
```

---

### getLast()

```java
System.out.println(list.getLast());
```

---

### removeFirst()

```java
list.removeFirst();
```

---

### removeLast()

```java
list.removeLast();
```

---

### offer()

```java
list.offer(10);
```

---

### offerFirst()

```java
list.offerFirst(5);
```

---

### offerLast()

```java
list.offerLast(50);
```

---

### peek()

```java
System.out.println(list.peek());
```

---

### peekFirst()

```java
System.out.println(list.peekFirst());
```

---

### peekLast()

```java
System.out.println(list.peekLast());
```

---

### poll()

```java
System.out.println(list.poll());
```

---

### pollFirst()

```java
System.out.println(list.pollFirst());
```

---

### pollLast()

```java
System.out.println(list.pollLast());
```

---

### push()

```java
list.push(100);
```

---

### pop()

```java
System.out.println(list.pop());
```

---

### descendingIterator()

```java
Iterator<Integer> it = list.descendingIterator();

while(it.hasNext())
{
    System.out.println(it.next());
}
```

---

# Time Complexity

| Operation | Complexity |
|------------|------------|
| addFirst | O(1) |
| addLast | O(1) |
| removeFirst | O(1) |
| removeLast | O(1) |
| get(index) | O(n) |

---

# 6. Vector

Vector is the older version of ArrayList.

Characteristics

✔ Dynamic

✔ Thread Safe

✔ Synchronized

✔ Slower

---

## Constructor

```java
Vector<String> v = new Vector<>();
```

---

## Common Methods

```java
v.add("Java");

v.addElement("Python");

v.removeElement("Java");

System.out.println(v.capacity());

System.out.println(v.firstElement());

System.out.println(v.lastElement());

v.insertElementAt("C++",1);

v.removeElementAt(0);

v.setElementAt("Spring",0);

v.copyInto(new Object[v.size()]);

Enumeration<String> e = v.elements();

while(e.hasMoreElements())
{
    System.out.println(e.nextElement());
}
```

---

## Extra Methods

```
addElement()

removeElement()

insertElementAt()

removeElementAt()

setElementAt()

firstElement()

lastElement()

elements()

capacity()

copyInto()

setSize()
```

---

# 7. Stack

Stack extends Vector

LIFO

Last In First Out

```
Push

↓

Top

↓

Pop
```

---

## Constructor

```java
Stack<Integer> st = new Stack<>();
```

---

## push()

```java
st.push(10);
st.push(20);
st.push(30);
```

---

## pop()

```java
System.out.println(st.pop());
```

---

## peek()

```java
System.out.println(st.peek());
```

---

## empty()

```java
System.out.println(st.empty());
```

---

## search()

Returns position from TOP

```java
System.out.println(st.search(20));
```

---

## Complete Stack Example

```java
import java.util.*;

public class Demo
{
    public static void main(String args[])
    {
        Stack<String> stack = new Stack<>();

        stack.push("Java");
        stack.push("Python");
        stack.push("Spring");

        System.out.println(stack);

        System.out.println(stack.peek());

        System.out.println(stack.pop());

        System.out.println(stack);

        System.out.println(stack.search("Java"));

        System.out.println(stack.empty());
    }
}
```

---

# Time Complexity

| Operation | Complexity |
|------------|------------|
| push | O(1) |
| pop | O(1) |
| peek | O(1) |
| search | O(n) |

---

# ArrayList vs LinkedList

| Feature | ArrayList | LinkedList |
|----------|-----------|------------|
| Structure | Dynamic Array | Doubly Linked List |
| Random Access | Fast | Slow |
| Insert Middle | Slow | Fast |
| Delete Middle | Slow | Fast |
| Memory | Less | More |
| Search | O(n) | O(n) |

---

# Vector vs ArrayList

| Feature | Vector | ArrayList |
|----------|---------|------------|
| Thread Safe | Yes | No |
| Speed | Slow | Fast |
| Synchronization | Yes | No |

---

# Stack vs LinkedList

| Stack | LinkedList |
|---------|------------|
| LIFO | List + Queue + Stack |
| Extends Vector | Implements Deque |
| Legacy | Modern Alternative |

---

# Interview Questions

### Why ArrayList is faster than LinkedList?

ArrayList uses contiguous memory, enabling O(1) index-based access. LinkedList must traverse nodes sequentially, making random access O(n).

---

### Why Vector is slower?

Because every public method is synchronized, introducing locking overhead.

---

### Why Stack is considered legacy?

`Stack` extends `Vector`, inheriting unnecessary synchronized list behavior. Modern Java recommends using `Deque` (e.g., `ArrayDeque`) for stack operations.

---

### Can List store duplicates?

Yes.

---

### Can List store null values?

Yes (ArrayList, LinkedList, Vector allow `null`; Stack inherits this behavior).

---

### Which List should you use?

- **ArrayList**: Frequent reads and random access.
- **LinkedList**: Frequent insertions/deletions at ends or middle.
- **Vector**: Legacy code requiring synchronized list.
- **Stack**: Only for legacy APIs; prefer `ArrayDeque` in new code.

---

# Summary

- Collection Framework provides reusable data structures.
- `List` is ordered, index-based, and allows duplicates.
- Common `List` methods work across `ArrayList`, `LinkedList`, `Vector`, and `Stack`.
- `ArrayList` uses a dynamic array and excels at random access.
- `LinkedList` uses a doubly linked list and excels at insertions/deletions.
- `Vector` is synchronized and thread-safe but slower.
- `Stack` follows LIFO and is built on top of `Vector`.
