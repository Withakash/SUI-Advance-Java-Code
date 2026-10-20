# JMS Messaging Models and JMS API

## Learning Outcomes

After studying these notes, you will be able to:

-   Explain the two main JMS messaging models: Point-to-Point and
    Publish/Subscribe.
-   Differentiate between a Queue and a Topic.
-   Identify the main components of the JMS API.
-   Understand how a message is produced, delivered, and consumed.
-   Recognize common JMS API classes and interfaces used in Java
    applications.

## 1. Introduction

**Java Message Service (JMS)** is a Java API that allows applications to
exchange messages through a messaging provider or broker.

JMS supports two main messaging models:

1.  **Point-to-Point (Queue)**
2.  **Publish/Subscribe (Topic)**

The model you choose depends on how the application should deliver
messages.

For example, an online shopping application might send an
order-processing task to a queue, while it might publish an
order-confirmation event to a topic so that several systems can react to
it.

> **Remember:** JMS defines the API. A messaging provider implements it
> and handles the actual message transport and delivery.

------------------------------------------------------------------------

## 2. JMS Messaging Models

A **messaging model** describes how a message is sent from a producer to
one or more consumers.

### 2.1 Point-to-Point Model (Queue)

In the Point-to-Point model, a producer sends a message to a **Queue**.
Consumers receive messages from that queue.

``` text
             Message
Producer ---------------> Queue
                            |
                 +----------+----------+
                 |                     |
              Consumer A            Consumer B
```

If multiple consumers listen to the same queue, they generally compete
for messages. A particular message is normally delivered to **one
consumer**, rather than being broadcast to every consumer.

#### Example: Order Processing

Suppose an online store receives 100 orders. Each order becomes a
message in an `OrderQueue`.

``` text
New Order
    |
    v
 OrderQueue
    |
    +----> Worker 1 processes a message
    |
    +----> Worker 2 processes another message
    |
    +----> Worker 3 processes another message
```

Workers can share the workload. The exact distribution and delivery
behavior depend on the messaging provider and configuration.

#### Important Features

-   Uses a **Queue** as the destination.
-   Messages are usually processed by one competing consumer.
-   Useful for distributing tasks among worker services.
-   Producers and consumers do not need to run at the same time if
    messages are stored by the provider.
-   Delivery guarantees depend on configuration, acknowledgements,
    transactions, and provider behavior.

#### Where Is It Used?

-   Order processing
-   Background email or report generation
-   Payment-processing tasks
-   Image or video processing jobs
-   Work queues shared by multiple workers

------------------------------------------------------------------------

### 2.2 Publish/Subscribe Model (Topic)

In the Publish/Subscribe model, a producer publishes a message to a
**Topic**. Multiple subscribers can receive a copy of that message.

``` text
                    Publisher
                        |
                        v
                      Topic
                 /      |      \
                v       v       v
          Subscriber A  B   Subscriber C
```

The publisher does not need to know the identities of the subscribers.
The topic distributes the message to eligible subscribers.

#### Example: Order Placed Event

When a customer places an order, an application publishes an
`OrderPlaced` event.

``` text
                OrderPlaced Event
                       |
                       v
                 OrderEvents Topic
                  /      |       \
                 v       v        v
          Email Service Inventory Analytics
```

The email service can send a confirmation, the inventory service can
update stock, and the analytics service can record the event.

#### Important Features

-   Uses a **Topic** as the destination.
-   One published message can be delivered to multiple eligible
    subscribers.
-   Publisher and subscribers are loosely coupled.
-   Useful for event notifications and broadcasting information.
-   Ordinary non-durable subscriptions can miss messages when the
    subscriber is disconnected. Durable subscriptions and provider
    configuration can help retain messages for eligible subscribers.

#### Where Is It Used?

-   Notifications
-   News or event updates
-   Order-status events
-   Monitoring and analytics events
-   Broadcasting application events to multiple services

------------------------------------------------------------------------

## 3. Queue vs Topic

  ------------------------------------------------------------------------
  Feature                 Point-to-Point           Publish/Subscribe
  ----------------------- ------------------------ -----------------------
  Destination             Queue                    Topic

  Main idea               Distribute work          Broadcast an event

  Message recipients      Normally one competing   Multiple eligible
                          consumer receives each   subscribers can receive
                          message                  the message

  Typical use             Task processing          Notifications and event
                                                   distribution

  Example                 Process a refund request Notify multiple
                                                   services that a refund
                                                   occurred

  Subscriber offline      Messages may remain in   Ordinary subscriptions
                          the queue, depending on  may miss messages;
                          provider/configuration   durable subscriptions
                                                   can retain eligible
                                                   messages
  ------------------------------------------------------------------------

### Easy Analogy

-   **Queue:** A stack of tasks assigned among available workers. Each
    task should be handled by one worker.
-   **Topic:** A public announcement. Every eligible subscriber receives
    the announcement.

### How to Choose?

Ask yourself:

-   **Should one worker handle each task?** Use a Queue.
-   **Should several systems receive the same event?** Use a Topic.

------------------------------------------------------------------------

## 4. Introduction to the JMS API

The **JMS API** provides Java interfaces and classes for creating
connections, sending messages, receiving messages, and working with
destinations.

A JMS application generally has these roles:

-   **Producer:** Creates and sends messages.
-   **Destination:** Where messages are sent (a Queue or Topic).
-   **Consumer:** Receives messages.
-   **Connection factory:** Creates a connection or context to the
    messaging provider.
-   **Messaging provider:** Implements JMS and manages message transport
    and delivery.

### Basic Flow

``` text
Java Application
      |
      v
ConnectionFactory
      |
      v
JMSContext / Connection + Session
      |
      +------> Producer ------> Queue or Topic
                                  |
                                  v
                               Consumer
```

The exact setup depends on the JMS version and the application server or
provider being used.

------------------------------------------------------------------------

## 5. Important JMS API Components

### 5.1 ConnectionFactory

A `ConnectionFactory` is used to create a connection or a `JMSContext`
for communicating with the messaging provider.

**Purpose:** Provides the starting point for a JMS client to connect to
the messaging system.

### 5.2 Connection

A `Connection` represents an active connection between a JMS client and
the provider.

It is used in the classic JMS API style.

### 5.3 Session

A `Session` is a context for creating and using messages, producers, and
consumers. In classic JMS, it also controls aspects such as
acknowledgement and transactions.

### 5.4 JMSContext

`JMSContext` is a simplified API introduced in JMS 2.0. It combines
connection and session functionality and supports a more concise
programming style.

### 5.5 Destination

A `Destination` represents where messages are sent.

Common destination types:

-   `Queue` --- for Point-to-Point messaging.
-   `Topic` --- for Publish/Subscribe messaging.

### 5.6 JMSProducer

A `JMSProducer` sends messages to a destination. It is part of the
simplified JMS 2.0 API.

### 5.7 JMSConsumer

A `JMSConsumer` receives messages from a destination. It is part of the
simplified JMS 2.0 API.

### 5.8 Message

A `Message` is the data unit exchanged between JMS clients. A message
can contain headers, properties, and a body.

Common message body types include:

-   `TextMessage` --- text content.
-   `ObjectMessage` --- a serializable Java object; use carefully and
    follow secure serialization practices.
-   `BytesMessage` --- a stream of bytes.
-   `MapMessage` --- a set of name/value pairs with supported types.
-   `StreamMessage` --- a stream of primitive values and strings.

In newer JMS code, messages can also be created using convenient methods
such as `JMSContext.createTextMessage(...)`.

------------------------------------------------------------------------

## 6. Simple JMS Example (JMS 2.0 Style)

The following example illustrates the simplified API. It assumes that a
JMS provider is configured and that a Queue named
`java:/jms/queue/OrderQueue` is available under that provider's naming
configuration. The exact JNDI name varies by server.

### Sending a Message

``` java
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import javax.naming.InitialContext;

public class OrderProducer {
    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();

        ConnectionFactory factory =
                (ConnectionFactory) context.lookup("jms/ConnectionFactory");

        Queue queue =
                (Queue) context.lookup("java:/jms/queue/OrderQueue");

        try (JMSContext jms = factory.createContext()) {
            jms.createProducer().send(queue, "Order #105 created");
        }

        context.close();
    }
}
```

**What happens?**

1.  The application looks up the connection factory and queue.
2.  It creates a `JMSContext`.
3.  It creates a producer and sends a text message to the queue.
4.  The provider handles the message according to its configuration.

> The lookup names above are examples, not universal names. Configure
> the destination and connection factory in your chosen server/provider
> before running the code.

### Receiving a Message

``` java
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import javax.naming.InitialContext;

public class OrderConsumer {
    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();

        ConnectionFactory factory =
                (ConnectionFactory) context.lookup("jms/ConnectionFactory");

        Queue queue =
                (Queue) context.lookup("java:/jms/queue/OrderQueue");

        try (JMSContext jms = factory.createContext()) {
            String message = jms.createConsumer(queue)
                                .receiveBody(String.class);

            System.out.println("Received: " + message);
        }

        context.close();
    }
}
```

This example uses a blocking receive, so the consumer waits until a
message is available or the receive operation otherwise returns
according to the API/provider behavior. Production applications should
consider timeouts, error handling, transactions, and resource lifecycle.

------------------------------------------------------------------------

## 7. `javax.jms` vs `jakarta.jms`

JMS packages changed as Java EE moved to Jakarta EE.

  Package           Commonly associated with
  ----------------- ----------------------------------
  `javax.jms.*`     Older Java EE / JMS applications
  `jakarta.jms.*`   Jakarta EE 9 and later

**Important:** Use the package namespace supported by your server and
dependencies. Do not mix `javax.jms` and `jakarta.jms` classes in the
same application.

For example, modern Jakarta EE applications generally use:

``` java
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
```

Older applications may use:

``` java
import javax.jms.Connection;
import javax.jms.Session;
```

------------------------------------------------------------------------

## 8. Important Points to Remember

-   JMS stands for **Java Message Service**.
-   JMS is an API; a provider or broker implements messaging
    functionality.
-   The two main messaging models are **Point-to-Point** and
    **Publish/Subscribe**.
-   Point-to-Point uses a **Queue** and is commonly used to distribute
    tasks.
-   Publish/Subscribe uses a **Topic** and is commonly used to
    distribute events.
-   A `ConnectionFactory` helps create a connection or context.
-   `JMSContext` is the simplified API style introduced in JMS 2.0.
-   A `JMSProducer` sends messages; a `JMSConsumer` receives messages.
-   A `Message` carries the content and metadata.
-   Delivery, persistence, acknowledgements, and transaction guarantees
    depend on configuration and provider behavior.

------------------------------------------------------------------------

## 9. Practice Questions

### Short-Answer Questions

1.  What is a JMS messaging model?
2.  What is the difference between a Queue and a Topic?
3.  Define `ConnectionFactory`.
4.  What is the purpose of `JMSContext`?
5.  Name any four JMS message body types.

### Medium-Answer Questions

1.  Explain the Point-to-Point messaging model with a diagram and
    example.
2.  Explain the Publish/Subscribe model with a diagram and example.
3.  Compare Queue and Topic messaging.
4.  Explain the roles of a producer, destination, consumer, and
    messaging provider.
5.  Explain the important components of the JMS API.

### Long-Answer / Exam Questions

1.  Describe the two JMS messaging models with architecture diagrams,
    examples, advantages, and use cases.
2.  Explain the JMS API components and show the flow of a message from
    producer to consumer.
3.  Compare Point-to-Point and Publish/Subscribe messaging in detail.
4.  Explain how JMS can support communication between loosely coupled
    enterprise application components.

### Scenario-Based Questions

1.  An order must be processed by one worker from a pool of workers.
    Should the application use a Queue or Topic? Explain.
2.  When an order is placed, email, inventory, and analytics services
    must all receive the event. Which messaging model is suitable?
3.  Why might a disconnected subscriber miss a topic message? What role
    can a durable subscription play?
4.  Why should an application avoid assuming that all JMS providers use
    the same JNDI destination names?

------------------------------------------------------------------------

## 10. One-Page Revision

``` text
                         JMS
                          |
              +-----------+-----------+
              |                       |
       Point-to-Point           Publish/Subscribe
              |                       |
            Queue                   Topic
              |                       |
       One competing          Multiple eligible
       consumer handles       subscribers receive
       each message           a copy of the event

JMS API Components:
ConnectionFactory -> JMSContext or Connection + Session
Producer -> Destination (Queue / Topic) -> Consumer
Message -> Headers + Properties + Body
```

**Final takeaway:** Choose a Queue when a task should be handled by one
competing consumer. Choose a Topic when an event should be delivered to
multiple eligible subscribers. The JMS API gives Java applications a
standard way to work with a messaging provider.
