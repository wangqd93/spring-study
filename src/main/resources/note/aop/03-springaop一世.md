### Pointcut
类定义
```java
    public interface Pointcut {
    	ClassFilter getClassFilter();
    	MethodMatcher getMethodMatcher();
    	Pointcut TRUE = TruePointcut.INSTANCE;
    }

    public interface MethodMatcher {
    	// 仅方法匹配 不参数匹配
        // 静态方法匹配，运行时可缓存
    	boolean matches(Method method, Class<?> targetClass);
    	
        //  调用参数匹配方法 返回true
    	boolean isRuntime();
    
        // 方法参数均匹配
        // 动态方法匹配
    	boolean matches(Method method, Class<?> targetClass, Object... args);
    	MethodMatcher TRUE = TrueMethodMatcher.INSTANCE;
    }

    public interface ClassFilter {
    	boolean matches(Class<?> clazz);
    	ClassFilter TRUE = TrueClassFilter.INSTANCE;
    }
```

#### 常用的Pointcut
##### NamedMatchMethodPointcut
属于StaticMethodMatcherPointcut的子类，可以根据自身指定的一组方法名称与Joinpoint处的方法的方法名称进行匹配。
无法对重载的方法名进行匹配。
##### JdkRegexMethodPointcut 和 Per15RegexMethodPointcut
`JdkRegexMethodPointcut`的实现基于jdk1.4之后引入的jdk标准正则表达式。
`Per15RegexMethodPointcut` 低于jdk1.4的替代品
##### AnnotationMatchingPointcut
类级别的注解
```
    AnnotationMatchingPointcut pointcut =  new AnnotationMatchingPointcut(ClassLevelAnnotation.class);
    AnnotationMatchingPointcut pointcut2 = new AnnotationMatchingPointcut.forClassAnnotation(ClassLevelAnnotation.class)
```
方法级别的注解
```
   AnnotationMatchingPointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(MethodLevelAnnotation.class); 
```
类 + 方法级别的注解(同时匹配)
```
   AnnotationMatchingPointcut pointcut = AnnotationMatchingPointcut(ClassLevelAnnotation.class,MethodLevelAnnotation.class);
```
##### ComposablePointcut
可以进行逻辑运算的pointcut实现。可以进行Pointcut之间的"并"以及"交"运算。
##### controlFlowPointcut
匹配程序的调用流程，不是对某个方法之心所在的Joinpoint处的单一特征进行匹配。

### Advice
#### before advice
实现`MethodBeforeAdvice` 
```java
    public interface MethodBeforeAdvice extends BeforeAdvice {
    	void before(Method method, Object[] args, @Nullable Object target) throws Throwable;
    }
```
#### throwsAdvice 
对应ThrowsAdvice,是aop概念中的AfterThrowingAdvice

#### AfterReturningAdvice
只有在方法正常返回的情况下，才会执行。可以访问到返回值，但是不可以更改返回值，与通用的AfterReturningAdvice的特性有所出入。

#### AroundAdvice
spring中没有直接定义对应的Around Advice的实现接口，而是直接采用Aop Alliance的标准接口MethodInterceptor,该接口定义如下:
```java
    public interface MethodInterceptor extends Interceptor {
        Object invoke(MethodInvocation invocation) throws Throwable;
    }
```
#### pre-instance类型的Advice
与per-class类型的Advice不同，per-instance类型的Advice不会在目标类所有对象实例之间共享，而是会为不同的实例对象保存它们各自的状态以及相关逻辑。
spring aop中，introduction就是唯一的一种per-instance型Advice。

##### DelegatingIntroductionInterception

##### DelegatePerTargetObjectIntroductionInterceptor


### Aspect
Advisor代表Spring中的Aspect，但是，与正常的Aspect不同，Advisor通常只持有一个Pointcut和一个Advice。而理论上，Aspect定义中可以有多个Pointcut和多个Advice，
所以，我们可以认为Advisor是一种特殊的Aspect。

Advisor有两个分支，一个分支以PointcutAdvisor，另一个分支则以IntroductionAdvisor为头。

#### PointcutAdvisor
PointAdvisor才是真正定义一个Pointcut和一个Advice的Advisor，大部分的Advisor实现全都是PointAdvisor的"部下"。

##### 常用的PointAdvisor的实现
+ DefaultPointcutAdvisor: 最通用的PointcutAdvisor实现。
+ NameMatchMethodPointcutAdvisor: 是细化后的DefaultPointcutAdvisor，它限定了自身可以使用的Pointcut类型为NameMatchMethodPointcut，并且外部不可更改。
+ RegexpMethodPointcutAdvisor: 与NameMatchMethodPointcutAdvisor类似，也限定了自身可以使用的Pointcut类型，即只能通过正则表达式为其设置相应的Pointcut。
+ DefaultBeanFactoryPointcutAdvisor: 是使用比较少的一个Advisor实现，因为自身绑定到了BeanFactory，所以，要使用DefaultBeanFactoryPointcutAdvisor，我们
的应用特定要绑定到spring的ioc容器了。而且，通常情况下，DefaultPointcutAdvisor已经完全可以满足需求。

##### IntroductionAdvisor
Introduction与PointcutAdvisor最本质的区别就是，IntroductionAdvisor只能应用于类级别的拦截，只能使用Introduction型的Advice，而不能像PointcutAdvisor那样，
可以使用任何类型的pointcut，以及差不多任何类型的Advice。也就是说，IntroductionAdvisor纯粹就是为了introduction而生的。

### Ordered的作用
顺序号越小优先级越高，优先级排在前面的，将被优先执行。

### Spring Aop的织入
AspectJ采用ajc编译器作为它的织入器；JBoss AOP使用自定义的ClassLoader作为它的织入器；而在Spring Aop中，使用类ProxyFactory作为织入器。

使用proxyFactory只需要指定如下两个最基本的东西。
+ 第一个是要对其进行织入的目标对象。我们可以通过ProxyFactory的构造方法直接传入，也可以在ProxyFactory构造完成之后，通过相应的setter方法进行设置。
+ 第二个是将要应用到目标对象的Aspect。在Spring里面交Advisor，不过，除了可以指定相应的Advisor之外，还可以使用如下代码，直接指定各种类型的Advice。
参考`com.bycsmys.aop.proxyfactory`

满足以下三种情况的任何一种，proxyFactory将对目标类进行基于类的代理
+ 如果目标没有实现任何接口，不管proxyTargetClass的值是什么，proxyFactory会采用类的代理
+ 如果proxyFactory的proxyTargetClass的属性被设置为true，ProxyFactory会采用基于里的代理
+ 如果ProxyFactory的optimize属性被设置为true，proxyFactory会采用基于类的代理









