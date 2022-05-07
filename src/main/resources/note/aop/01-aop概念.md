### 常见概念
+ joinPoint: 将要在其之上进行织入操作的系统执行点就称为joinPoint。
+ PointCut: 代表的是JoinPoint的表达方式。将横切逻辑织入当前系统的过程中，需要参考PointCut规定的JoinPoint信息，
才可以知道应该往系统的那些jointPoint上织入横切逻辑。
+ Advice: Advice是单一横切关注点逻辑的载体，它代表将会织入到JoinPoint的横切逻辑。如果将Aspect比作OOP中的Class，那么Advice就相当于Class中的Method。
+ Aspect: Aspect可以包含多个pointCut以及Advice定义。

