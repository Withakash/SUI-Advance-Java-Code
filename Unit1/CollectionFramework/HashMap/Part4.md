# Java HashMap - Complete Notes (Part 4)
## Comparisons, Time Complexity, Internal Source Code, Practical Programs & Interview Questions

> **Topic:** Java Collections Framework - HashMap
>
> **Language:** Java 8+
>
> **Part:** 4 (Final)

---

# Topics Covered

1. HashMap vs LinkedHashMap
2. HashMap vs TreeMap
3. HashMap vs Hashtable
4. HashMap Internal Source Code
5. Time Complexity
6. Real World Applications
7. Practical Programs
8. Interview Questions

---

# HashMap vs LinkedHashMap

| Feature | HashMap | LinkedHashMap |
|----------|----------|---------------|
| Order | Not Guaranteed | Insertion Order |
| Duplicate Key | ❌ | ❌ |
| Duplicate Value | ✅ | ✅ |
| Null Key | One | One |
| Null Value | Multiple | Multiple |
| Performance | Faster | Slightly Slower |
| Internal Structure | Hash Table | Hash Table + Doubly Linked List |

---

## Example

### HashMap

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(3,"C");
map.put(1,"A");
map.put(2,"B");

System.out.println(map);
```

Possible Output

```
{1=A,3=C,2=B}
```

---

### LinkedHashMap

```java
LinkedHashMap<Integer,String> map =
new LinkedHashMap<>();

map.put(3,"C");
map.put(1,"A");
map.put(2,"B");

System.out.println(map);
```

Output

```
{3=C,1=A,2=B}
```

Insertion order is preserved.

---

# HashMap vs TreeMap

| Feature | HashMap | TreeMap |
|----------|----------|----------|
| Ordering | Random | Sorted |
| Internal Structure | Hash Table | Red Black Tree |
| Null Key | One | Not Allowed |
| Null Value | Multiple | Multiple |
| Search | O(1) | O(log n) |
| Insertion | O(1) | O(log n) |

---

## Example

```java
TreeMap<Integer,String> map =
new TreeMap<>();

map.put(50,"Java");
map.put(20,"Python");
map.put(10,"C++");

System.out.println(map);
```

Output

```
{10=C++,20=Python,50=Java}
```

TreeMap always keeps keys sorted.

---

# HashMap vs Hashtable

| Feature | HashMap | Hashtable |
|----------|----------|-----------|
| Thread Safe | ❌ | ✅ |
| Performance | Faster | Slower |
| Null Key | One | Not Allowed |
| Null Value | Multiple | Not Allowed |
| Introduced | Java 1.2 | Java 1.0 |
| Synchronization | No | Yes |

---

# Which One Should You Use?

```
Need Maximum Performance

↓

HashMap
```

```
Need Insertion Order

↓

LinkedHashMap
```

```
Need Sorted Keys

↓

TreeMap
```

```
Need Legacy Thread Safety

↓

Hashtable
```

---

# Internal Source Code (Simplified)

The actual JDK implementation is much larger, but the core structure is similar to:

```java
class Node<K,V>
{
    final int hash;

    final K key;

    V value;

    Node<K,V> next;
}
```

Each bucket stores a linked list (or a Red-Black Tree after treeification).

---

# Simplified put()

```text
put(key,value)

↓

hashCode()

↓

Find Bucket

↓

Bucket Empty?

↓

Yes

↓

Insert Node

↓

No

↓

Collision

↓

Compare Keys

↓

Same Key?

↓

Replace Value

↓

Different Key

↓

Add New Node

↓

Check Treeify
```

---

# Simplified get()

```text
get(key)

↓

hashCode()

↓

Bucket

↓

Linked List

↓

Compare Keys

↓

Found

↓

Return Value
```

---

# Simplified remove()

```text
remove(key)

↓

Find Bucket

↓

Search Node

↓

Found

↓

Delete Node

↓

Reconnect List

↓

Return Removed Value
```

---

# Time Complexity

| Operation | Average | Worst Case |
|------------|----------|------------|
| put() | O(1) | O(log n) |
| get() | O(1) | O(log n) |
| remove() | O(1) | O(log n) |
| containsKey() | O(1) | O(log n) |
| containsValue() | O(n) | O(n) |
| keySet() | O(n) | O(n) |
| values() | O(n) | O(n) |
| entrySet() | O(n) | O(n) |

---

# Real World Applications

## Student Database

```
Roll Number

↓

Student Name
```

```
101

↓

Akash
```

---

## Phone Directory

```
Name

↓

Phone Number
```

---

## Dictionary

```
Word

↓

Meaning
```

---

## Inventory

```
Product ID

↓

Quantity
```

---

## Login System

```
Username

↓

Password
```

---

## Country Codes

```
IN

↓

India
```

---

# Practical Program 1

## Student Database

```java
HashMap<Integer,String> students =
new HashMap<>();

students.put(101,"Akash");
students.put(102,"Rahul");
students.put(103,"Aman");

students.forEach(

(id,name)->

System.out.println(

id+" -> "+name

)

);
```

Output

```
101 -> Akash

102 -> Rahul

103 -> Aman
```

---

# Practical Program 2

## Word Frequency Counter

```java
String sentence =
"java python java c java python";

HashMap<String,Integer> map =
new HashMap<>();

for(String word :
sentence.split(" "))
{

    map.put(

        word,

        map.getOrDefault(word,0)+1

    );

}

System.out.println(map);
```

Output

```
{java=3,

python=2,

c=1}
```

---

# Practical Program 3

## Character Frequency

```java
String name = "programming";

HashMap<Character,Integer> map =
new HashMap<>();

for(char ch :
name.toCharArray())
{

    map.put(

        ch,

        map.getOrDefault(ch,0)+1

    );

}

System.out.println(map);
```

Possible Output

```
{p=1,

r=2,

o=1,

g=2,

a=1,

m=2,

i=1,

n=1}
```

---

# Practical Program 4

## Counting Duplicate Elements

```java
int arr[] =
{10,20,30,10,20,10};

HashMap<Integer,Integer> map =
new HashMap<>();

for(int num : arr)
{

    map.put(

        num,

        map.getOrDefault(num,0)+1

    );

}

System.out.println(map);
```

Output

```
{10=3,

20=2,

30=1}
```

---

# Practical Program 5

## Inventory Management

```java
HashMap<String,Integer> inventory =
new HashMap<>();

inventory.put("Laptop",10);

inventory.put("Mouse",50);

inventory.put("Keyboard",30);

inventory.forEach(

(product,qty)->

System.out.println(

product+" : "+qty

)

);
```

Output

```
Laptop : 10

Mouse : 50

Keyboard : 30
```

---

# Practical Program 6

## Employee Salary

```java
HashMap<String,Integer> salary =
new HashMap<>();

salary.put("Akash",50000);

salary.put("Rahul",65000);

salary.put("Aman",45000);

salary.replaceAll(

(name,pay)

-> pay+5000

);

System.out.println(salary);
```

Output

```
{Akash=55000,

Rahul=70000,

Aman=50000}
```

---

# Common Mistakes

### Mistake 1

```java
map.put("Java",100);

map.put("Java",200);
```

Result

```
{Java=200}
```

Old value is overwritten.

---

### Mistake 2

```java
map.get("Python");
```

If key is absent

```
null
```

Use

```java
map.getOrDefault()
```

instead.

---

### Mistake 3

Never modify the map directly while iterating using a `for-each` loop, as it can cause a `ConcurrentModificationException`.

Use an `Iterator`'s `remove()` method if you need to remove entries during iteration.

---

# Best Practices

✅ Use `Map` interface for declaration.

```java
Map<Integer,String> map =
new HashMap<>();
```

---

✅ Use Generics.

```java
HashMap<Integer,String>
```

---

✅ Use `entrySet()` for iteration.

---

✅ Use `computeIfAbsent()` for caching.

---

✅ Override `equals()` and `hashCode()` properly for custom key classes.

---

# Complete Method Summary

## Basic Methods

```
put()

putIfAbsent()

putAll()

get()

getOrDefault()

remove()

replace()

containsKey()

containsValue()

size()

isEmpty()

clear()

clone()
```

---

## Traversal

```
keySet()

values()

entrySet()

iterator()

forEach()

stream()

parallelStream()
```

---

## Java 8 Methods

```
compute()

computeIfAbsent()

computeIfPresent()

merge()

replaceAll()
```

---

# Frequently Asked Interview Questions

### Q1 Why is HashMap so fast?

Because it uses **hashing**, allowing direct access to buckets in average **O(1)** time.

---

### Q2 Why should custom key classes override `hashCode()` and `equals()`?

`HashMap` first uses `hashCode()` to locate the bucket and then `equals()` to identify the correct key within that bucket. Incorrect implementations can lead to failed lookups or duplicate logical keys.

---

### Q3 What is a Collision?

Two different keys generating the same bucket index.

---

### Q4 What is Rehashing?

Creating a larger bucket array and redistributing all existing entries after the load factor threshold is exceeded.

---

### Q5 Why was Treeification introduced?

To improve lookup performance in heavily collided buckets from **O(n)** to **O(log n)**.

---

### Q6 Why is `entrySet()` preferred over `keySet()`?

Because `entrySet()` provides both the key and value together, avoiding an extra `get()` lookup for each key.

---

### Q7 Which implementation should I choose?

| Requirement | Recommended Class |
|-------------|-------------------|
| Fast lookup | `HashMap` |
| Maintain insertion order | `LinkedHashMap` |
| Keep keys sorted | `TreeMap` |
| Legacy synchronized map | `Hashtable` |

---

# Final Revision Sheet

```
HashMap

↓

Key-Value Pair

↓

Unique Keys

↓

Duplicate Values Allowed

↓

One Null Key

↓

Multiple Null Values

↓

Hashing

↓

Bucket

↓

Collision

↓

Linked List

↓

Red-Black Tree (Java 8)

↓

Default Capacity = 16

↓

Load Factor = 0.75

↓

Threshold = 12

↓

Rehashing

↓

Average Complexity = O(1)
```

---

# Congratulations!

You have now covered:

- ✅ HashMap fundamentals
- ✅ Internal working
- ✅ All commonly used methods
- ✅ Java 8 APIs
- ✅ Traversal techniques
- ✅ Performance analysis
- ✅ Comparisons with other Map implementations
- ✅ Practical coding examples
- ✅ Interview-focused concepts

This completes the full **HashMap** chapter from beginner to advanced level.
