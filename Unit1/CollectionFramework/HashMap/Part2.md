# Java HashMap - Complete Notes (Part 2)
## HashMap Methods with Complete Live Examples

> **Topic:** Java Collections Framework - HashMap
>
> **Language:** Java 8+
>
> **Part:** 2
>
> In this part, we will learn every commonly used HashMap method with practical code examples.

---

# Topics Covered

- put()
- putIfAbsent()
- putAll()
- get()
- getOrDefault()
- remove(key)
- remove(key, value)
- replace(key, value)
- replace(key, oldValue, newValue)
- containsKey()
- containsValue()
- size()
- isEmpty()
- clear()
- clone()

---

# Creating a HashMap

```java
import java.util.*;

public class Main {

    public static void main(String[] args) {

        HashMap<Integer, String> students = new HashMap<>();

    }

}
```

---

# 1. put(K key, V value)

Adds a key-value pair into the HashMap.

If the key already exists, the old value is replaced.

### Syntax

```java
map.put(key, value);
```

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101, "Akash");
map.put(102, "Rahul");
map.put(103, "Aman");

System.out.println(map);
```

### Output

```
{101=Akash, 102=Rahul, 103=Aman}
```

---

## Replacing Existing Value

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101, "Akash");

map.put(101, "Rohit");

System.out.println(map);
```

Output

```
{101=Rohit}
```

Since key **101** already existed, its value was replaced.

---

# 2. putIfAbsent()

Adds the value only if the key is not already present.

### Syntax

```java
map.putIfAbsent(key, value);
```

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");

map.putIfAbsent(101,"Rahul");

map.putIfAbsent(102,"Aman");

System.out.println(map);
```

### Output

```
{101=Akash, 102=Aman}
```

Existing keys are not overwritten.

---

# Difference

```
put()

Always inserts or replaces.
```

```
putIfAbsent()

Inserts only if key doesn't exist.
```

---

# 3. putAll()

Copies all entries from another Map.

### Example

```java
HashMap<Integer,String> map1 = new HashMap<>();

map1.put(101,"Akash");
map1.put(102,"Rahul");

HashMap<Integer,String> map2 = new HashMap<>();

map2.put(103,"Aman");
map2.put(104,"Neha");

map1.putAll(map2);

System.out.println(map1);
```

### Output

```
{101=Akash, 102=Rahul, 103=Aman, 104=Neha}
```

---

# 4. get()

Returns the value associated with a key.

### Syntax

```java
map.get(key);
```

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");
map.put(102,"Rahul");

System.out.println(map.get(102));
```

### Output

```
Rahul
```

---

## If Key Does Not Exist

```java
System.out.println(map.get(999));
```

Output

```
null
```

---

# 5. getOrDefault()

Returns the value if the key exists.

Otherwise returns a default value.

### Syntax

```java
map.getOrDefault(key, defaultValue);
```

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");

System.out.println(
map.getOrDefault(102,"Student Not Found")
);
```

### Output

```
Student Not Found
```

---

# Difference

```
get()

↓

null
```

```
getOrDefault()

↓

Default Value
```

---

# 6. remove(key)

Removes an entry using the key.

### Syntax

```java
map.remove(key);
```

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");
map.put(102,"Rahul");
map.put(103,"Aman");

map.remove(102);

System.out.println(map);
```

### Output

```
{101=Akash,103=Aman}
```

---

# remove() Return Value

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");

System.out.println(map.remove(101));
```

Output

```
Akash
```

Returns the removed value.

---

# 7. remove(key, value)

Removes only if both key and value match.

### Syntax

```java
map.remove(key, value);
```

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");

System.out.println(

map.remove(101,"Rahul")

);

System.out.println(map);
```

Output

```
false

{101=Akash}
```

---

### Correct Value

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");

System.out.println(

map.remove(101,"Akash")

);

System.out.println(map);
```

Output

```
true

{}
```

---

# 8. replace(key, value)

Replaces the value of an existing key.

### Syntax

```java
map.replace(key, value);
```

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");

map.replace(101,"Rahul");

System.out.println(map);
```

Output

```
{101=Rahul}
```

---

# If Key Doesn't Exist

```java
map.replace(999,"Test");
```

Nothing happens.

---

# 9. replace(key, oldValue, newValue)

Replaces only if the current value matches.

### Syntax

```java
map.replace(key, oldValue, newValue);
```

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");

System.out.println(

map.replace(101,"Akash","Rahul")

);

System.out.println(map);
```

Output

```
true

{101=Rahul}
```

---

### Wrong Old Value

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");

System.out.println(

map.replace(101,"Aman","Rahul")

);

System.out.println(map);
```

Output

```
false

{101=Akash}
```

---

# 10. containsKey()

Checks whether the key exists.

### Syntax

```java
map.containsKey(key);
```

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");

System.out.println(

map.containsKey(101)

);

System.out.println(

map.containsKey(999)

);
```

Output

```
true

false
```

---

# 11. containsValue()

Checks whether a value exists.

### Syntax

```java
map.containsValue(value);
```

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");

System.out.println(

map.containsValue("Akash")

);

System.out.println(

map.containsValue("Rahul")

);
```

Output

```
true

false
```

---

# 12. size()

Returns total number of entries.

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");
map.put(102,"Rahul");

System.out.println(map.size());
```

Output

```
2
```

---

# 13. isEmpty()

Checks whether the map contains any entries.

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

System.out.println(map.isEmpty());

map.put(101,"Akash");

System.out.println(map.isEmpty());
```

Output

```
true

false
```

---

# 14. clear()

Removes all key-value pairs.

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");
map.put(102,"Rahul");

map.clear();

System.out.println(map);
```

Output

```
{}
```

---

# 15. clone()

Creates a shallow copy of the HashMap.

### Example

```java
HashMap<Integer,String> map = new HashMap<>();

map.put(101,"Akash");
map.put(102,"Rahul");

HashMap<Integer,String> copy =
(HashMap<Integer,String>) map.clone();

System.out.println(copy);
```

Output

```
{101=Akash,102=Rahul}
```

---

# Summary Table

| Method | Description |
|---------|-------------|
| put() | Insert or update key-value pair |
| putIfAbsent() | Insert only if key doesn't exist |
| putAll() | Copy another map |
| get() | Get value by key |
| getOrDefault() | Return value or default |
| remove(key) | Remove using key |
| remove(key,value) | Remove only if both match |
| replace(key,value) | Replace existing value |
| replace(key,old,new) | Replace only if old value matches |
| containsKey() | Check key exists |
| containsValue() | Check value exists |
| size() | Number of entries |
| isEmpty() | Check if map is empty |
| clear() | Remove all entries |
| clone() | Create shallow copy |

---

# Interview Questions

### Q1 What happens if `put()` is called with an existing key?

The old value is replaced with the new value.

---

### Q2 What is the difference between `put()` and `putIfAbsent()`?

- `put()` always inserts or replaces.
- `putIfAbsent()` inserts only if the key does not exist.

---

### Q3 What is the difference between `get()` and `getOrDefault()`?

- `get()` returns `null` if the key is missing.
- `getOrDefault()` returns the specified default value.

---

### Q4 What is the difference between `remove(key)` and `remove(key, value)`?

- `remove(key)` removes the entry if the key exists.
- `remove(key, value)` removes the entry only if both the key and value match.

---

### Q5 What does `clone()` do?

It creates a **shallow copy** of the `HashMap`. The map structure is copied, but mutable objects stored as values are shared between the original and the clone.
