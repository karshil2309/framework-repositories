# ActiveMQ笔记

## 1.1 背景 & JMS概述
CORBA、DCOM、RMI等RPC中间件技术已广泛应用于各个领域。但是，面对规模和复杂度都越来越高的分布式系统，这些技术也显示出其局限性：
1. 同步通信：客户发出调用后，必须等待服务对象完成处理，并返回结果后才能继续执行；
1. 客户和服务对象的生命周期紧密耦合：客户进程和服务对象进程都必须正常运行；如果由于服务对象崩溃或者网络故障导致客户的请求不可达，客户会接收到异常；
1. 点对点通信：客户的一次调用只发送给某个单独的目标对象。

面向消息的中间件(Message Oriented Middleware, MOM)较好的解决了以上问题。发送者将消息发送给消息服务器，消息服务器将消息存放在若千队列中，在合适的时候再将消息转发给接收者。这种模式下，发送和接收是异步的，发送者无需等待二者的生命周期未必相同：发送消息的时候接收者不一定运行，接收消息的时候发送者也不一定运行；一对多通信：对于一个消息可以有多个接收者。

JAVA消息服务(JMS)定义了Java中访问消息中间件的接口。JMS只是接口，并没有给予实现，实现JMS接口的消息中间件称为JMS Provider，已有的MOM系统包括Apache的ActiveMQ、以及阿里巴巴的RocketMQ、IBM的MQSeries、Microsoft的MSMQ和BEA的MessageQ、RabbitMQ、kafka等等，它们基本都遵循JMS规范

## 1.2 JMS术语
JMS实现JMS接口的消息中间件
- Provider(MessageProvider)：生产者
- Consumer(MessageConsumer)：消费者
- PTP：Point to Point，即点对点的消息模型
- Pub/Sub： Publish/Subscribe，即发布/订阅的消息模型
- Queue： 队列目标
- Topic：主题目标
- ConnectionFactory：连接工厂，JMS用它创建连接
- Connection：JMS客户端到JMS Provider的连接
- Destination：消息的目的地
- Session：会话，一个发送或接收消息的线程

## 1.3 JMS术语概念
- ConnectionFactory接口（连接工厂）：用户用来创建到JMS提供者的连接的被管对象。JMS客户通过可移植的接口访问连接，这样当下层的实现改变时，代码不需要进行修改。管理员在JNDI名字空间中配置连接工厂，这样，JMS客户才能够查找到它们。根据消息类型的不同，用户将使用队列连接工厂，或者主题连接工厂。
- Connection接口（连接）：连接代表了应用程序和消息服务器之间的通信链路。在获得了连接工厂后，就可以创建一一个与JMS提供者的连接。根据不同的连接类型，连接允许用户创建会话，以发送和接收队列和主题到目标。
- Destination接口（目标）：目标是一个包装了消息目标标识符的被管对象，消息目标是指消息发布和接收的地点，或者是队列，或者是主题。JMS管理员创建这些对象，然后用户通过JNDI发现它们。和连接工厂一样，管理员可以创建两种类型的目标，点对点模型的队列，以及发布者/订阅者模型的主题。
- MessageConsumer接口（消息消费者）：由会话创建的对象，用于接收发送到目标的消息。消费者可以同步地（阻塞模式），或异步（非阻塞）接收队列和主题类型的消息。
- MessageProducer接口（消息生产者）：由会话创建的对象，用于发送消息到目标。用户可以创建某个目标的发送者，也可以创建一个通用的发送者，在发送消息时指定目标。
- Message接口（消息）：是在消费者和生产者之间传送的对象，也就是说从一个应用程序创送到另一个应用程序。一个消息有三个主要部分：
    - 消息头（必须）：包含用于识别和为消息寻找路由的操作设置。
    - 一组消息属性（可选）：包含额外的属性，支持其他提供者和用户的兼容。可以创建定制的字段和过滤器（消息选择器）。
    - 一个消息体（可选）：允许用户创建五种类型的消息（文本消息，映射消息，字节消息，流消息和对象消息）。

消息接口非常灵活，并提供了许多方式来定制消息的内容。

## 1.4 消息格式定义



## 2.1 ActiveMQ简介


## 2.2 ActiveMQ使用


## 2.3 ActiveMQ Hello World


## 2.4 ActiveMQ安全机制


## 3.1 Connection方法使用


## 3.2 Session方法使用


## 3.3 MessageProducer


## 3.4 MessageConsumer


## 4.x 高级主题（两种经典的消息模式、与Spring整合、集群、监控、配置优化等）

## Results
- 示例：`ActiveMQProviderApplication`，`ActiveMQConsumerApplication`，`TestMQProducer`
- activemq-provider中，`MailUtil`设置`username`
- activemq-consumer中，`application.yml`设置`username`，`password`
- 发送邮件：[http://localhost:8080/activemq-provider/mail/send](http://localhost:8080/activemq-provider/mail/send)

## References
- ActiveMQ