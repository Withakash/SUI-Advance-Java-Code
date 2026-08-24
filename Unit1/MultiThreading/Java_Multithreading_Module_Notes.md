# Advanced Java — Multithreading Module

## Module Overview

This module introduces Java multithreading from the ground up and gradually moves from basic thread creation to thread pools and asynchronous results.

### Recommended Learning Sequence

**Process vs Thread → Create Thread → Observe Concurrent Execution → Thread States → Problem of Too Many Threads → Thread Pool → ExecutorService → Callable → Future → Synchronization & Concurrency Problems**

---

# 1. Process vs Thread

Start with this topic because students need to understand **why threads exist** before writing multithreading code.

## What is a Process?

A **process** is a program that is currently executing. A process has its own memory space and operating-system-managed resources.

Examples:

- Running Chrome
- Running IntelliJ IDEA
- Running a Java application

## What is a Thread?

A **thread** is the smallest unit of execution inside a process. A process can contain one or more threads.

A thread has its own:

- Stack
- Program counter / execution state
- Registers

Threads belonging to the same process share resources such as:

- Heap memory
- Code / program instructions
- Open resources owned by the process

## Process Memory vs Thread Memory

```text
PROCESS
│
├── Code / Program Instructions      ← Shared by threads
├── Heap Memory                      ← Shared by threads
├── Shared Resources                 ← Shared by threads
│
├── Thread 1 ── Stack + Registers
├── Thread 2 ── Stack + Registers
└── Thread 3 ── Stack + Registers
```

### Single-threaded vs Multithreaded Application

**Single-threaded application:**

```text
Process
   │
   └── Thread 1
```

**Multithreaded application:**

```text
Process
   ├── Thread 1
   ├── Thread 2
   └── Thread 3
```

## Context Switching

A **context switch** happens when the CPU changes from executing one thread/process to another and saves the current execution state so it can continue later.

Too many switches create overhead because the CPU spends time switching and managing execution contexts instead of doing useful work.

## Why are Threads Lightweight?

Threads are generally lighter than processes because threads in the same process can share the process's memory and resources. Creating a completely independent process usually requires more operating-system resources.

## Advantages of Multithreading

- Better responsiveness
- Better CPU utilization
- Can overlap independent tasks
- Useful for servers and I/O-heavy applications
- Can improve throughput when work can be executed concurrently

## Disadvantages of Multithreading

- More difficult to understand and debug
- Race conditions may occur
- Synchronization may be required
- Too many threads cause memory and scheduling overhead
- Incorrect shared-state handling can lead to deadlocks and other concurrency bugs

## Important Comparison

| Process | Thread |
|---|---|
| Independent program execution | Smallest unit of execution inside a process |
| Has its own process address space | Shares process address space |
| Relatively heavyweight | Relatively lightweight |
| Creation is generally more expensive | Creation is generally faster |
| Inter-process communication is more involved | Communication through shared memory is easier, but requires synchronization |
| Failure is generally isolated to the process boundary | An uncaught failure can terminate the process depending on where it occurs |

## Classroom Example: Browser

Ask students:

> **“When you open Chrome, is every tab a separate program?”**

Use this to introduce the idea that modern applications may use multiple processes and multiple threads for different responsibilities.

Possible activities to discuss:

- UI handling
- Network operations
- Rendering
- Background work

Avoid telling students that “one tab = exactly one process” because modern browsers use more complex process models.

---

# 2. Creating Threads in Java

Once students understand the concept, show how Java creates threads.

## Method 1: Extending `Thread`

```java
class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}

public class Main {
    public static void main(String[] args) {

        MyThread t1 = new MyThread();
        t1.start();
    }
}
```

## `start()` vs `run()`

This is an excellent classroom demonstration.

```text
start()
   ↓
JVM schedules a new thread
   ↓
run() executes on that new thread
```

But:

```text
run()
   ↓
Normal method call
   ↓
No new thread is created
```

### Key Demonstration

```java
MyThread t1 = new MyThread();

t1.start();   // Starts a new thread
// t1.run();  // Normal method call if called directly
```

Tell students to replace `start()` with `run()` and observe the difference.

---

## Method 2: Implementing `Runnable`

```java
class Task implements Runnable {

    @Override
    public void run() {
        System.out.println("Task is running");
    }
}

public class Main {

    public static void main(String[] args) {

        Thread thread = new Thread(new Task());
        thread.start();
    }
}
```

### Why is `Runnable` Usually Preferred?

- Java supports single inheritance.
- Your class can extend another class.
- It separates the **task** from the **thread object**.
- It fits naturally with `ExecutorService` and thread pools.

## Lambda Expression

Because `Runnable` is a functional interface, it can be written using a lambda:

```java
Thread thread = new Thread(() -> {
    System.out.println("Running using Lambda");
});

thread.start();
```

---

# 3. Thread Lifecycle and Java Thread States

Java provides six official `Thread.State` values:

- `NEW`
- `RUNNABLE`
- `BLOCKED`
- `WAITING`
- `TIMED_WAITING`
- `TERMINATED`

> **Important:** Java combines the concepts of “ready to run” and “currently running” under `RUNNABLE`. You should not teach `RUNNING` as a separate Java `Thread.State` value.

## State Flow

```text
                 start()
NEW ─────────────────────────► RUNNABLE
                                  │
                    ┌─────────────┼──────────────┐
                    │             │              │
                    ▼             ▼              ▼
                 BLOCKED       WAITING    TIMED_WAITING
                    │             │              │
                    └─────────────┴──────────────┘
                                  │
                              resumes
                                  │
                                  ▼
                              RUNNABLE
                                  │
                           run() completes
                                  ▼
                             TERMINATED
```

## `NEW`

The thread object has been created but `start()` has not been called.

```java
Thread t = new Thread();
```

## `RUNNABLE`

The thread has been started and is either ready to run or currently executing.

```java
t.start();
```

## `BLOCKED`

The thread is waiting to acquire a monitor lock because another thread currently owns that lock.

This state becomes especially important when teaching `synchronized` and locks.

## `WAITING`

The thread is waiting indefinitely for another thread or event to cause it to continue.

Examples include:

```java
thread.join();
```

and:

```java
object.wait();
```

## `TIMED_WAITING`

The thread is waiting for a specified amount of time.

Example:

```java
Thread.sleep(2000);
```

Other timed operations can also produce this state, such as timed `join()` or timed waits.

## `TERMINATED`

The thread has finished execution. Its `run()` method has completed.

```text
run() finishes
     ↓
TERMINATED
```

---

# 4. One Simple Program to Observe All Thread States

The following program is intentionally designed for classroom demonstration. It creates different threads so the main thread can observe all six Java states.

### State Demo Program

```java
public class AllThreadStatesDemo {

    private static final Object LOCK = new Object();
    private static final Object WAIT_OBJECT = new Object();

    public static void main(String[] args) throws Exception {

        // -------------------------------------------------
        // 1. NEW
        // -------------------------------------------------
        Thread newThread = new Thread(() -> {});
        System.out.println("1. NEW            : " + newThread.getState());

        // -------------------------------------------------
        // 2. RUNNABLE
        // -------------------------------------------------
        Thread runnableThread = new Thread(() -> {
            long end = System.currentTimeMillis() + 2000;

            // Keep the thread active so its state can be observed.
            while (System.currentTimeMillis() < end) {
                // Busy work only for demonstration.
            }
        });

        runnableThread.start();
        Thread.sleep(50);
        System.out.println("2. RUNNABLE        : " + runnableThread.getState());

        // -------------------------------------------------
        // 3. BLOCKED
        // -------------------------------------------------
        Thread lockHolder = new Thread(() -> {
            synchronized (LOCK) {
                try {
                    Thread.sleep(1500); // Holds LOCK while sleeping
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread blockedThread = new Thread(() -> {
            synchronized (LOCK) {
                System.out.println("Blocked thread acquired the lock.");
            }
        });

        lockHolder.start();
        Thread.sleep(100); // Give lockHolder time to acquire LOCK
        blockedThread.start();
        Thread.sleep(100); // Give blockedThread time to become BLOCKED

        System.out.println("3. BLOCKED         : " + blockedThread.getState());

        // -------------------------------------------------
        // 4. WAITING
        // -------------------------------------------------
        Thread waitingThread = new Thread(() -> {
            synchronized (WAIT_OBJECT) {
                try {
                    WAIT_OBJECT.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        waitingThread.start();
        Thread.sleep(100);
        System.out.println("4. WAITING         : " + waitingThread.getState());

        // -------------------------------------------------
        // 5. TIMED_WAITING
        // -------------------------------------------------
        Thread timedWaitingThread = new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        timedWaitingThread.start();
        Thread.sleep(100);
        System.out.println("5. TIMED_WAITING   : " + timedWaitingThread.getState());

        // -------------------------------------------------
        // Release waitingThread
        // -------------------------------------------------
        synchronized (WAIT_OBJECT) {
            WAIT_OBJECT.notify();
        }

        // Wait for all demo threads to finish.
        runnableThread.join();
        lockHolder.join();
        blockedThread.join();
        waitingThread.join();
        timedWaitingThread.join();

        // -------------------------------------------------
        // 6. TERMINATED
        // -------------------------------------------------
        System.out.println("6. TERMINATED       : " + timedWaitingThread.getState());
    }
}
```

### Expected Output

The exact timing can vary slightly between machines, but the output should look like this:

```text
1. NEW            : NEW
2. RUNNABLE        : RUNNABLE
3. BLOCKED         : BLOCKED
4. WAITING         : WAITING
5. TIMED_WAITING   : TIMED_WAITING
Blocked thread acquired the lock.
6. TERMINATED       : TERMINATED
```

### What Students Should Notice

| State | How the demo creates it |
|---|---|
| `NEW` | Thread object created, but `start()` not called |
| `RUNNABLE` | Thread started and doing active work |
| `BLOCKED` | Waiting to enter a `synchronized` block whose lock is held by another thread |
| `WAITING` | `Object.wait()` with no timeout |
| `TIMED_WAITING` | `Thread.sleep(...)` |
| `TERMINATED` | `run()` has completed |

### Important Classroom Note

`getState()` is a snapshot. Thread scheduling is controlled by the JVM and operating system, so state observations can change quickly. The program above uses deliberate delays to make the states easy to observe, but thread-state timing is not a hard real-time guarantee.

---

# 5. Thread Management and Control

After introducing states, teach the methods students use to control and coordinate threads.

## `sleep()`

Pauses the current thread for a specified time.

```java
Thread.sleep(1000);
```

The current thread enters `TIMED_WAITING` during the sleep.

## `join()`

Makes one thread wait for another thread to finish.

```java
thread.join();
```

Useful when the main thread must wait for worker threads before continuing.

## `interrupt()`

Requests interruption of a thread. It does not forcibly kill the thread.

```java
thread.interrupt();
```

When a sleeping/waiting thread is interrupted, methods such as `sleep()` or `wait()` can throw `InterruptedException`.

## `isAlive()`

Checks whether a thread has started and has not yet terminated.

```java
System.out.println(thread.isAlive());
```

## Thread Priority

Briefly introduce priority as a scheduling hint, not a correctness mechanism.

```java
thread.setPriority(Thread.MAX_PRIORITY);
```

Do not teach students that priority guarantees execution order.

## Daemon Threads

A daemon thread is a background-support thread. The JVM can exit when no live non-daemon threads remain.

Example:

```java
Thread background = new Thread(() -> {
    while (true) {
        System.out.println("Background work...");
    }
});

background.setDaemon(true);
background.start();
```

Use this only as a basic introduction at this stage.

---

# 6. Problems with Creating Too Many Threads

Before introducing `ExecutorService`, create the problem first.

Example:

```java
for (int i = 1; i <= 1000; i++) {

    Thread t = new Thread(() -> {
        System.out.println("Task executing");
    });

    t.start();
}
```

Ask students:

> **“What happens if our server receives 100,000 requests?”**

Discuss:

- Thread creation overhead
- Memory consumption
- Context switching
- Too many active threads
- Difficult thread management
- Reduced throughput when the system is overloaded

Then introduce the idea:

> Instead of creating a new worker every time, why not maintain a group of reusable workers?

This naturally leads to **Thread Pools**.

---

# 7. ExecutorService and Thread Pools

## Thread Pool Concept

```text
                TASKS
                  │
        ┌─────────┼─────────┐
        ▼         ▼         ▼

           THREAD POOL

        ┌─────────────────┐
        │ Thread 1        │
        │ Thread 2        │
        │ Thread 3        │
        │ Thread 4        │
        └─────────────────┘
```

Tasks are submitted to the pool. Threads are reused.

## Example

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {

    public static void main(String[] args) {

        ExecutorService executor =
                Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {

            int taskNumber = i;

            executor.submit(() -> {

                System.out.println(
                    "Task " + taskNumber +
                    " executed by " +
                    Thread.currentThread().getName()
                );

            });
        }

        executor.shutdown();
    }
}
```

### Visual Explanation

```text
10 Tasks
   │
   ▼
Queue
   │
   ▼
3 Worker Threads
├── Worker 1
├── Worker 2
└── Worker 3
```

## Types to Teach

### Fixed Thread Pool

```java
Executors.newFixedThreadPool(5);
```

A fixed number of worker threads is maintained.

### Single Thread Executor

```java
Executors.newSingleThreadExecutor();
```

Tasks execute one at a time using a single worker thread.

### Cached Thread Pool

```java
Executors.newCachedThreadPool();
```

Creates threads as needed and reuses idle threads. This should be taught with the caution that it is not automatically the best choice for every workload.

### Scheduled Executor

```java
ScheduledExecutorService scheduler =
        Executors.newScheduledThreadPool(2);
```

Useful for:

- Scheduled tasks
- Delayed execution
- Periodic execution

## `execute()` vs `submit()`

### `execute()`

Accepts a `Runnable` and does not provide a `Future` result.

```java
executor.execute(() -> {
    System.out.println("Task running");
});
```

### `submit()`

Accepts `Runnable` or `Callable` and returns a `Future` for tracking/completion/result handling.

```java
Future<?> future = executor.submit(() -> {
    System.out.println("Task running");
});
```

## Shutting Down the Executor

```java
executor.shutdown();
```

Stops accepting new tasks and allows submitted tasks to finish.

```java
executor.shutdownNow();
```

Attempts to stop actively executing tasks by interrupting worker threads and returns tasks that were awaiting execution. It is not a force-kill mechanism.

---

# 8. Callable and Future

Students already know `Runnable`. Now ask:

> **“Runnable can perform a task, but what if the task needs to return a result?”**

Then introduce `Callable`.

## Runnable vs Callable

| Runnable | Callable |
|---|---|
| Uses `run()` | Uses `call()` |
| Does not return a result | Returns a result |
| Does not declare a checked exception from `run()` | `call()` can throw checked exceptions |
| Commonly submitted with `execute()` or `submit()` | Commonly submitted with `submit()` |

## Callable Example

```java
import java.util.concurrent.*;

public class CallableDemo {

    public static void main(String[] args)
            throws Exception {

        ExecutorService executor =
                Executors.newSingleThreadExecutor();

        Callable<Integer> task = () -> {

            System.out.println("Calculating...");

            Thread.sleep(2000);

            return 10 + 20;
        };

        Future<Integer> future =
                executor.submit(task);

        System.out.println("Doing other work...");

        Integer result = future.get();

        System.out.println("Result: " + result);

        executor.shutdown();
    }
}
```

## Future Concept

```text
Callable Task
      │
      ▼
ExecutorService
      │
      ▼
Future Object
      │
      ├── Is task completed?
      ├── Cancel task?
      └── Get result
```

## Important Methods

```java
future.get();
future.isDone();
future.cancel(true);
```

### Important Point About `get()`

```java
future.get();
```

can block the current thread until the result becomes available.

This is one of the most important concepts when discussing asynchronous programming: **submitting a task can be asynchronous, but calling `get()` may wait synchronously for the result.**

---

# 9. Four-Lecture Teaching Plan

## Lecture 1 — Introduction to Multithreading

### Topics

- Process vs Thread
- Single-threaded vs multithreaded application
- Process memory vs thread memory
- Creating threads using `Thread`
- Creating threads using `Runnable`
- Lambda expression for `Runnable`
- `start()` vs `run()`
- Classroom practical

### Practical

Create three threads:

- Download Thread
- Upload Thread
- Notification Thread

Make each print messages at different intervals so students can observe concurrent execution.

---

## Lecture 2 — Thread Lifecycle and Control

### Topics

- Thread lifecycle
- Java thread states
- `sleep()`
- `join()`
- `interrupt()`
- `isAlive()`
- Thread priority — brief discussion
- Daemon threads — basic introduction

### Practical

Create:

```text
Main Thread
    │
    ├── File Download Thread
    │
    └── Processing Thread
            │
            └── Main waits using join()
```

Also run the **AllThreadStatesDemo** from this module.

---

## Lecture 3 — ExecutorService and Thread Pool

### Topics

- Problem with manually creating threads
- Thread Pool concept
- `ExecutorService`
- `execute()` vs `submit()`
- Fixed Thread Pool
- Cached Thread Pool
- Single Thread Executor
- `shutdown()` and `shutdownNow()`

### Practical Project — Task Processing System

```text
100 Tasks
    ↓
Task Queue
    ↓
5 Worker Threads
    ↓
Process Tasks
```

This makes thread reuse and task scheduling visible.

---

## Lecture 4 — Callable and Future

### Topics

- Runnable limitation
- Callable
- Future
- Asynchronous results
- `get()`
- `isDone()`
- `cancel()`
- Exception handling

### Practical

Create three `Callable` tasks:

```text
Task 1 → Sum of numbers
Task 2 → Find factorial
Task 3 → Simulate API response
```

Submit all three using `Callable` and collect the results using `Future`.

---

# 10. Suggested Classroom Activities

## Activity 1 — `start()` vs `run()`

Run the same program twice:

```java
t1.start();
```

and:

```java
t1.run();
```

Ask students to identify whether a new thread was created.

## Activity 2 — Concurrent Tasks

Create three worker threads:

```text
Download
Upload
Notification
```

Each prints 5 messages with a small delay.

Ask students:

- Why does the output order change?
- Are the tasks truly executing at exactly the same time?
- What is the difference between concurrency and parallelism?

## Activity 3 — Thread State Observation

Run `AllThreadStatesDemo` and ask students to match each state to the code responsible for producing it.

## Activity 4 — Thread Pool Comparison

Run 20 tasks with:

- 20 manually created threads
- A fixed pool of 5 threads
- A single-thread executor

Compare execution order, thread reuse, and management.

## Activity 5 — Callable Results

Run three independent calculations using `Callable<Integer>` and retrieve results using `Future<Integer>`.

---

# 11. Final Concept Map

```text
JAVA MULTITHREADING
│
├── Process vs Thread
│
├── Creating Threads
│   ├── Thread Class
│   ├── Runnable
│   └── Lambda
│
├── Thread Lifecycle
│   ├── NEW
│   ├── RUNNABLE
│   ├── BLOCKED
│   ├── WAITING
│   ├── TIMED_WAITING
│   └── TERMINATED
│
├── Thread Management
│   ├── sleep()
│   ├── join()
│   ├── interrupt()
│   ├── isAlive()
│   └── Daemon Thread
│
├── Thread Pool
│   ├── ExecutorService
│   ├── FixedThreadPool
│   ├── CachedThreadPool
│   ├── SingleThreadExecutor
│   └── Scheduled Executor
│
└── Asynchronous Results
    ├── Callable
    └── Future
```

---

# 12. Natural Next Module

After this module, the next topic should be:

```text
Synchronization & Concurrency Problems
          ↓
Race Condition
          ↓
synchronized
          ↓
Locks
          ↓
Semaphores
          ↓
Concurrent Collections
          ↓
Deadlock / Livelock / Starvation
```

This is the natural continuation because students will already understand:

- Multiple threads can access shared memory.
- Threads can run concurrently.
- Thread pools control worker execution.
- `WAITING` and `BLOCKED` states are connected to coordination and locks.

---

# 13. Trainer Notes — Key Points to Emphasize

1. A **process** is a running program; a **thread** is an execution path inside a process.
2. Threads share process-level memory/resources, but each thread has its own stack and execution context.
3. `start()` creates/schedules a new thread; directly calling `run()` does not.
4. `Runnable` separates the task from the thread object and works well with executors.
5. Java has **six** official thread states. There is no separate `RUNNING` state in `Thread.State`.
6. `BLOCKED` normally means waiting to acquire a monitor lock.
7. `WAITING` means waiting without a timeout; `TIMED_WAITING` means waiting with a timeout.
8. Thread pools avoid unnecessary repeated thread creation and provide centralized task management.
9. `Callable` is useful when a task needs to produce a result.
10. `Future.get()` may block until the task finishes.
11. `shutdown()` and `shutdownNow()` are different; neither should be taught as an absolute “kill thread immediately” operation.
12. Thread state observations are timing-sensitive because scheduling is not deterministic.

---

# 14. Quick Revision Questions

### Short Questions

1. What is a process?
2. What is a thread?
3. Why are threads considered lightweight compared with processes?
4. Differentiate `start()` and `run()`.
5. Why is `Runnable` generally preferred over extending `Thread`?
6. What is context switching?
7. What is a thread pool?
8. What is the role of `ExecutorService`?
9. Differentiate `execute()` and `submit()`.
10. What is `Callable`?
11. What is a `Future`?
12. What is the difference between `WAITING` and `TIMED_WAITING`?

### Practical Questions

1. Create three threads using `Runnable` and make them perform different tasks.
2. Demonstrate the difference between `start()` and `run()`.
3. Write a program that prints the current thread name and state.
4. Create a fixed thread pool of 3 workers and submit 10 tasks.
5. Write a `Callable` that returns the factorial of a number and retrieve the result using `Future`.
6. Modify `AllThreadStatesDemo` to print the state repeatedly while the threads are changing state.

### Long/10-Mark Style Questions

1. Explain process and thread concepts in detail. Compare them based on memory, creation cost, communication, and failure isolation. Draw a suitable diagram.
2. Explain all six Java thread states with suitable examples and a lifecycle diagram.
3. Explain `Thread`, `Runnable`, and lambda-based thread creation. Compare `start()` and `run()` with examples.
4. Explain `ExecutorService` and thread pools. Discuss fixed, cached, single-thread, and scheduled executors with examples.
5. Explain `Callable` and `Future`. Describe how results are returned from asynchronous tasks and explain `get()`, `isDone()`, and `cancel()`.

---

# 15. One-Line Takeaway

> **Start with threads, observe the problem of unmanaged threads, solve it using thread pools, and then use Callable/Future when worker tasks need to return results.**
