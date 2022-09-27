## spring事务
spring事务的抽象包括三个主要的接口`PlatformTransactionManager`,`TransactionDefinition`,`TransactionStatus`
+ `PlatformTransactionManager` 负责界定事务边界
+ `TransactionDefinition` 负责定义事务相关属性，包括隔离级别、传播行为等
+ `TransactionStatus` 事务开启之后到事务结束期间的事务状态。

### TransactionDefinition
主要定义了有哪些事务属性可以指定，包括:
+ 事务的隔离级别
+ 事务的传播行为
+ 事务的超时时间
+ 是否为只读事务

#### 事务的隔离级别
定义了5个常量用于标志可共选择的隔离级别。
+ ISOLATION_DEFAULT: 如果指定隔离级别为ISOLATION_DEFAULT,则表示使用数据库默认的隔离级别，通常情况下是Read Committed
+ ISOLATION_READ_UNCOMMITTED: 对应Read Uncommitted隔离级别 ，无法避免脏读，不可重复读和幻读。
+ ISOLATION_READ_COMMITTED: 对应Read Committed隔离级别，可以避免脏读，但无法避免不可重复读和幻读。
+ ISOLATION_REPEATABLE_READ:  对应 Repeatable read 隔离级别，可以避免脏读和不可重复读，不可避免幻读。
+ ISOLATION_SERIALIZABLE: 对应serializable隔离级别，可以避免所有的脏读，不可重复读以及幻读，但并发效率最低。

#### 事务的传播行为
+ PROPAGATION_REQUIRED: 如果当前存在一个事务，则加入事务。不存在任何事务，则创建一个新的事务。通常作为默认的传播行为
+ PROPAGATION_SUPPORTS: 如果当前存在一个事务，则加入事务。不存在事务，则直接执行。对于一些查询方法来说，通常是比较适合的传播行为选择。
+ PROPAGATION_MANDATORY: 强制要求当前存在一个事务，不存在则异常。如果某个方法需要事务支持，但自身又不管理事务提交或者回滚，那么比较适合。
+ PROPAGATION_REQUIRES_NEW: 不管当前是否存在一个事务，都会创建新的事务。某个业务对象所做的事情不想影响外层事务，适合选择。比如，更新某些日志，更新失败，业务想回滚。
+ PROPAGATION_NOT_SUPPORTED: 不支持当前事务，而是在没有事务的情况下执行。
+ PROPAGATION_NEVER: 永远不需要当前存在事务，存在则抛异常
+ PROPAGATION_NESTED: 如果存在当前事务，则在当前事务的一个嵌套事务中执行，否则与PROPAGATION_REQUIRED的行为类似，即创建新的事务，在新创建的事务中执行。

#### TransactionDefinition相关实现
+ `DefaultTransactionDefinition`是TransactionDefinition接口的默认实现类，它提供了各事务的属性默认值，并通过它的setter方法，我们可以更改这些默认设置
+ `TransactionTemplate`是spring提供的进行编程式事务管理的模版方法类，它直接继承了DefaultTransactionDefinition。
+ `TransationAttribute`是继承自TransactionDefinition的接口定义，主要面向使用spring aop进行声明式事务管理的场合。它在TransactionDefinition定义的基础上
添加了rollbackOn的实现，DefaultTransactionAttribute的实现指定了，当异常类型为unchecked exception的情况下将回滚事务。

DefaultTransactionAttribute下有两个实现类，即RuleBasedTransactionAttribute和DelegatingTransactionAttribute。
+ RuleBasedTransactionAttribute允许我们同时指定多个回滚规则，这些规则包含RollbackRuleAttribute或者NoRollbackRuleAttribute的List形式提供。
RuleBasedTransactionAttribute的rollbackOn将使用传入的异常类型与这些回滚规则进行匹配，然后再决定是否要回滚事务。
+ DelegatingTransactionAttribute是抽象类，它存在的目的就是被子类化，DelegatingTransactionAttribute会将所有方法调用委派给另一个具体的TransactionAttribute实现类
比如DefaultTransactionAttribute或则RuleBasedTransactionAttribute。不过，除非 不是简单的直接委派，否则实现一个DelegatingTransactionAttribute是没有任何意义的

#### TransactionStatus
该接口表示整个事务处理过程中的事务状态，更多时候将在编程式事务中使用该接口。

### PlatformTransactionManager
+ transaction object: 承载了当前事务的必要信息，PlatformTransactionManager实现类可以根据transaction object所提供的信息来决定如何处理当前事务。
+ TransactionSynchronization: 可以注册到事务处理过程中的回调接口。就是事务处理的时间监听器，当事务处理的某些规定时点发生时，会用TransactionSynchronization上的一些方法
来执行相应的回调逻辑，如在事务完成后清理相应的系统资源等操作。
+ TransactionSynchronizationManager:类似与JTA规范中的javax.transaction.TransactionSynchronizationRegistry,我们通过TransactionSynchronizationManager
来管理TransactionSynchronization、当前事务状态以及具体的事务资源。
