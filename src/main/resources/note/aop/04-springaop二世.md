## 通过AspectJ 形式 AOP
事例 `com.bycsmys.aop.aspectj`

### 编程方式织入
通过AspectJProxyFactory,我们就可以通过实现Aspect定义到目标对象的织入。
```java
    public class Main {
        public static void main(String[] args) {
            AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory();
    
            aspectJProxyFactory.setTarget(new Foo());
            aspectJProxyFactory.addAspect(PerformanceTraceAspect.class);
    
            Foo proxy = (Foo) aspectJProxyFactory.getProxy();
            proxy.method1();
            proxy.method2();
        }
    }
```

### 通过自动代理织入
自动注入`AnnotationAwareAspectJAutoProxyCreator` 该对象会自动搜集IOC容器中注册的Aspect，并用到Pointcut定义的各个目标对象上。
如果我们通过容器取得现在的target对象的话，会发现它已经是被代理过的了。


## @AspectJ形式的Pointcut
### @AspectJ形式Pointcut的声明方式
@Aspect形式的Pointcut声明，依附在@Aspect所标注的Aspect定义类之内，通过使用org.aspectj.lang.annotation.Pointcut这个注解。指定AspectJ形式
的Pointcut表达式之后，将这个制定了相应表达式的注解标注到Aspect定义类的某个方法上即可。
```java
    @Aspect
    public class YourAspect {
    
        @Pointcut("aspectj style point expression")
        public void pointcutMethod() {
        }
    
        @Pointcut("aspectj style point expression")
        public void pointcutMethod2() {   
        }
    }
```
@AspectJ形式的Pointcut声明包含如下两部分。
+ Pointcut Expression。Pointcut Expression的载体为@Pointcut,该注解是方法级别的注解，所以，Pointcut Expression不能脱离某个方法单独声明。
Pointcut Expression附着于上的方法称为该Pointcut Expression的Pointcut Signature。PointcutExpression是真正规定Pointcut匹配规则的地方，
可以通过@Pointcut直接指定AspectJ形式的Pointcut表达式。@Pointcut所指定的AspectJ形式的Pointcut表达式由如下两部分组成:
    + Pointcut标志符。标志符表明该Pointcut将以什么样的行为来匹配表达式。
    + 表达式匹配模式。在Pointcut标志符之内可以指定具体的匹配模式。
+ Point Signature。Pointcut Signature在这里具体化为一个方法定义，它是Pointcut Expression的载体。Pointcut Signature所在的方法定义，出了返回类型必须
是void之外没有其他限制。
   
    
### @AspectJ形式Pointcut表达式的标志符
因为spring aop 只支持方法级别的Joinpoint，所以使用AspectJ的Pointcut表述语言中的少数几种标识符。 
+ execution: 帮助我们匹配拥有指定方法签名的Joinpoint。
例如
```java
    public class Foo{
        public void doSomething(String arg){
        }       
    }
``` 
我们可以使用如下的Pointcut表达式来匹配Foo的doSomething的方法执行:`execution(public void Foo.doSomething(String))`
因为部分匹配模式可以省略，所以，我们也可以简化以上模式: `execution(void doSomething(String))`
除此之外，我们还可以在execution的表达式中使用两种通配符，即`*`和`..` .
`*` 可以用于任何部分的匹配模式中，可以匹配相邻的多个字符。
`..`  可以在两个位置使用，一个是在declaring-type-pattern规定的位置，一个是在方法参数匹配模式的位置。
例如
`execution(void com.bycsmys.*.doSomething(*))` // 指定到com.bycsmys这一层下的所有类型
`execution(void com.bycsmys..*.doSomething(*))` // 可以匹配com.bycsmys包下的所有类型，以及com.bycsmys下层包下声明的所有类型。
如果`..`用于方法参数列表匹配位置，则表示该方法可以有0到多个参数，参数类型不限，如: `execution(void *.doSomething(..))`

+ within: 标志符只接受类型声明，它将会匹配指定类型下所有的Jointpoint。
在我们为within指定某个类后，它将匹配指定类所声明的所有方法执行。如：within(com.bycsmys.MockTarget)
那么,该Pointcut表达式在Spring aop中将会匹配MockTarget类中的所有方法声明。

+ this 和 target
this指代调用方法一方所在的对象，target指代被调用方法所在的对象，这样通常可以同时使用这两个标志符限定方法的调用关系。
spring aop的this和target标志符语义，有别于AspectJ中这两个标志符的原始语义。this指代的是目标对象的代理对象，而target指代的是就是目标对象。
如果使用this(ObjectType)作为Pointcut定义，那么当目标对象的代理对象是ObjectType类型的时候，该Pointcut定义将匹配ObjectType类型所有的Joinpoint。
在spring aop中，也就是ObjectType中定义的所有方法。
+ args: 帮助我们捕捉拥有指定参数类型、指定参数数量的方法级Joinpoint，而不管该方法在什么类型中被声明。
如: `args(com.bycsmys.User)` 则会匹配参数为User的方法

+ @within
@within指定了某种类型的注解，那么，只要对象标注了该类型的注解，使用了@within标志符的Pointcut表达式将匹配该对象内部所有的Joinpoint。
+ @target
匹配注解
+ @args
匹配注解
+ annotation
接受的注解类型只应用于方法级别，即标注了@Target(ElementType.METHOD)的注解声明。

### @AspectJ 形式的Pointcut在Spring aop中的真实面目
@AspectJ形式声明的所有Pointcut表达式，在Spring Aop内部都会通过解析，转化为具体的Pointcut对象。
在AspectJProxyFactory或者AnnotationAwareAspectJAutoProxyCreator通过反射获取了Aspect中的@Pointcut定义的AspectJ形式的Pointcut定义之后，
在spring aop框架内部都会构造一个对应的AspectJExpressionPointcut对象实例。AspectJExpressionPointcut内部持有通过反射获得的Pointcut表达式。

## @AspectJ形式的Advice
@Aspect形式的Advice定义，实际上就是使用@Aspect标注的Aspect定义类中的普通方法。只不过，这些方法需要针对不同的Advice类型使用对应的注解进行标注。
可以用于标注对应Advice定义方法的注解包括：
+ @Before。用于标注Before Advice定义所在的方法。
+ @AfterReturning。用于标注After Returning Advice定义所在的方法
+ @AfterThrowing。用于标注After Throwing Advice定义所在的方法，也就是在Spring AOP中称为ThrowAdvice的那种Advice类型。
+ @After。用于标注After(finally) Advice定义所在的方法。
+ @Around。用于标注Around Advice定义所在的方法，也就是常说的拦截器类型的Advice。第一个参数为ProceedingJoinPoint类型
+ @DeclareParents。用于标注Introduction类型的Advice，但该注解对应标注对象的域(Field),而不是方法(Method)

某些情况下，我们可能需要在Advice定义中访问Joinpoint处的方法参数，现在有两种方式
+ 通过org.aspectj.lang.JoinPoint.在@AspectJ形式的Aspect中，定义Before Advice的方法可以将第一个参数声明为org.aspectj.lang.JoinPoint类型，
通过Joinpoint我们可以借助它的getArgs()方法，访问相应的Joinpoint处方法的参数值。
+ 通过args标志符绑定。
```java
    @Before(value = "execution(boolean *.execute(String,..)) && args(taskName)")
    public void setupResourcesBefore(String taskName) throw Throwable{}
```


### @AspectJ中的Aspect更多话题

#### Advice的执行顺序
在同一个Aspect内: 最先声明的Advice拥有最高的优先级。对于Before Advice来说，拥有最高优先级的最先运行，而对于AfterReturningAdvice，拥有最高优先级的则最后运行。

在不同的Aspect内: 基于Ordered接口。较小值的Aspect，拥有较高的优先级。

## 基于 Schema 的 AOP




    