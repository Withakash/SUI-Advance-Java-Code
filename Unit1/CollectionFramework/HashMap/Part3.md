# Java HashMap - Complete Notes (Part 3)
## Traversing HashMap, Java 8 Methods & Advanced Operations

> **Topic:** Java Collections Framework - HashMap
>
> **Language:** Java 8+
>
> **Part:** 3

---

# Topics Covered

- keySet()
- values()
- entrySet()
- forEach(BiConsumer)
- Iterator using entrySet()
- Iterator using keySet()
- Stream API
- parallelStream()
- compute()
- computeIfAbsent()
- computeIfPresent()
- merge()
- replaceAll()
- equals()
- hashCode()

---

# Traversing HashMap

There are **7 common ways** to traverse a HashMap.

```
1. keySet()

2. values()

3. entrySet()

4. Iterator

5. forEach()

6. Stream API

7. Parallel Stream
```

---

# Sample HashMap

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");
map.put(102,"Rahul");
map.put(103,"Aman");
```

---

# 1. keySet()

Returns all keys.

Return Type

```java
Set<K>
```

Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");
map.put(102,"Rahul");
map.put(103,"Aman");

System.out.println(map.keySet());
```

Output

```
[101,102,103]
```

---

## Iterating using keySet()

```java
for(Integer key : map.keySet())
{
    System.out.println(key);
}
```

Output

```
101
102
103
```

---

## Getting Values Using Keys

```java
for(Integer key : map.keySet())
{
    System.out.println(
        key + " -> " + map.get(key)
    );
}
```

Output

```
101 -> Akash

102 -> Rahul

103 -> Aman
```

---

# 2. values()

Returns all values.

Return Type

```java
Collection<V>
```

Example

```java
System.out.println(map.values());
```

Output

```
[Akash, Rahul, Aman]
```

---

## Iterating Values

```java
for(String value : map.values())
{
    System.out.println(value);
}
```

Output

```
Akash
Rahul
Aman
```

---

# 3. entrySet()

Most important traversal method.

Returns

```
Key + Value
```

Return Type

```java
Set<Map.Entry<K,V>>
```

Example

```java
System.out.println(map.entrySet());
```

Output

```
[101=Akash,102=Rahul,103=Aman]
```

---

## Using Enhanced For Loop

```java
for(Map.Entry<Integer,String> entry
        : map.entrySet())
{

    System.out.println(

        entry.getKey()

        + " -> "

        + entry.getValue()

    );

}
```

Output

```
101 -> Akash

102 -> Rahul

103 -> Aman
```

---

# Why entrySet() is Faster?

Suppose

```java
for(Integer key : map.keySet())
{
    map.get(key);
}
```

HashMap

```
Key

↓

Search Again

↓

Return Value
```

Extra searching happens.

---

Using entrySet()

```
Key

↓

Value

Already Available
```

No extra lookup.

Recommended in interviews.

---

# 4. Iterator using entrySet()

```java
Iterator<Map.Entry<Integer,String>>
iterator =
map.entrySet().iterator();

while(iterator.hasNext())
{

    Map.Entry<Integer,String> entry =
    iterator.next();

    System.out.println(

        entry.getKey()

        + " -> "

        + entry.getValue()

    );

}
```

---

# 5. Iterator using keySet()

```java
Iterator<Integer> iterator =
map.keySet().iterator();

while(iterator.hasNext())
{

    Integer key = iterator.next();

    System.out.println(

        key + " -> " + map.get(key)

    );

}
```

---

# 6. Java 8 forEach()

HashMap introduced

```
BiConsumer
```

Example

```java
map.forEach(

(key,value) ->

System.out.println(

key + " -> " + value

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

# Method Reference Example

```java
map.forEach(

(k,v)->System.out.println(k+" : "+v)

);
```

---

# 7. Stream API

Convert entrySet into Stream.

```java
map.entrySet()

.stream()

.forEach(System.out::println);
```

Output

```
101=Akash

102=Rahul

103=Aman
```

---

# Filter Example

```java
map.entrySet()

.stream()

.filter(

entry -> entry.getKey()>101

)

.forEach(System.out::println);
```

Output

```
102=Rahul

103=Aman
```

---

# Map Example

```java
map.entrySet()

.stream()

.map(

entry -> entry.getValue()

)

.forEach(System.out::println);
```

Output

```
Akash

Rahul

Aman
```

---

# Parallel Stream

```java
map.entrySet()

.parallelStream()

.forEach(System.out::println);
```

Output order is **not guaranteed**.

---

# Java 8 Advanced Methods

---

# compute()

Updates value using a function.

Syntax

```java
map.compute(key,

(k,v)->newValue);
```

Example

```java
HashMap<String,Integer> map =
new HashMap<>();

map.put("Java",100);

map.compute(

"Java",

(k,v)->v+50

);

System.out.println(map);
```

Output

```
{Java=150}
```

---

# computeIfAbsent()

Executes only if key does NOT exist.

Example

```java
HashMap<String,Integer> map =
new HashMap<>();

map.computeIfAbsent(

"Java",

k->100

);

System.out.println(map);
```

Output

```
{Java=100}
```

---

If key exists

```java
map.computeIfAbsent(

"Java",

k->500

);
```

Nothing changes.

---

# computeIfPresent()

Runs only when key exists.

Example

```java
HashMap<String,Integer> map =
new HashMap<>();

map.put("Java",100);

map.computeIfPresent(

"Java",

(k,v)->v+200

);

System.out.println(map);
```

Output

```
{Java=300}
```

---

If key is absent

Nothing happens.

---

# merge()

Combines existing value with new value.

Syntax

```java
map.merge(

key,

value,

(oldValue,newValue)->result

);
```

Example

```java
HashMap<String,Integer> map =
new HashMap<>();

map.put("Java",100);

map.merge(

"Java",

50,

(oldValue,newValue)

-> oldValue + newValue

);

System.out.println(map);
```

Output

```
{Java=150}
```

---

If key doesn't exist

```java
map.merge(

"Python",

200,

(a,b)->a+b

);
```

Output

```
Python=200
```

Automatically inserted.

---

# replaceAll()

Updates every value.

Example

```java
HashMap<String,Integer> map =
new HashMap<>();

map.put("Java",100);
map.put("Python",200);
map.put("C++",300);

map.replaceAll(

(key,value)

-> value+10

);

System.out.println(map);
```

Output

```
{Java=110,

Python=210,

C++=310}
```

---

# equals()

Compares two HashMaps.

Example

```java
HashMap<Integer,String> m1 =
new HashMap<>();

m1.put(1,"Java");

HashMap<Integer,String> m2 =
new HashMap<>();

m2.put(1,"Java");

System.out.println(

m1.equals(m2)

);
```

Output

```
true
```

---

# hashCode()

Returns hash code of entire map.

```java
System.out.println(

map.hashCode()

);
```

Example Output

```
987654
```

(The exact value depends on the map contents.)

---

# Traversal Summary

| Method | Returns |
|----------|---------|
| keySet() | Set of Keys |
| values() | Collection of Values |
| entrySet() | Set of Key-Value Pairs |
| iterator() | Iterator |
| forEach() | BiConsumer Traversal |
| stream() | Sequential Stream |
| parallelStream() | Parallel Stream |

---

# Java 8 Methods Summary

| Method | Purpose |
|----------|----------|
| compute() | Update value |
| computeIfAbsent() | Add if absent |
| computeIfPresent() | Update if present |
| merge() | Merge values |
| replaceAll() | Update every value |
| forEach() | Traverse |

---

# Which Traversal is Best?

```
entrySet()

↓

Fastest

↓

Recommended
```

Reason

No extra searching.

---

# Time Complexity

| Method | Complexity |
|---------|------------|
| keySet() | O(n) |
| values() | O(n) |
| entrySet() | O(n) |
| iterator() | O(n) |
| stream() | O(n) |
| compute() | O(1) Average |
| merge() | O(1) Average |
| replaceAll() | O(n) |

---

# Interview Questions

### Q1 Which is the best way to iterate over a HashMap?

**Answer:** `entrySet()` because both the key and value are available without calling `get()` again.

---

### Q2 What does `computeIfAbsent()` do?

It computes and inserts a value only if the key is not already present.

---

### Q3 What is the difference between `compute()` and `computeIfPresent()`?

- `compute()` runs whether the key exists or not.
- `computeIfPresent()` runs only when the key already exists.

---

### Q4 What does `merge()` do?

It combines an existing value with a new value using a `BiFunction`. If the key is absent, it simply inserts the new value.

---

### Q5 What does `replaceAll()` do?

It updates every value in the map using the provided function.

---

# Next Part (Part 4)

In the next part, you'll learn:

- Internal implementation with source code explanation
- `HashMap` vs `LinkedHashMap`
- `HashMap` vs `TreeMap`
- `HashMap` vs `Hashtable`
- Time complexity analysis
- Real-world interview programs
- Frequency counter
- Duplicate element counting
- Character frequency
- Word frequency
- Employee database
- Student management system
- Top interview questions
- 
