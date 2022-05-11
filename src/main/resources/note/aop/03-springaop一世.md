## Pointcut
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

### 常用的Pointcut
#### NamedMatchMethodPointcut
属于StaticMethodMatcherPointcut的子类，可以根据自身指定的一组方法名称与Joinpoint处的方法的方法名称进行匹配。
无法对重载的方法名进行匹配。
#### JdkRegexMethodPointcut 和 Per15RegexMethodPointcut
`JdkRegexMethodPointcut`的实现基于jdk1.4之后引入的jdk标准正则表达式。
`Per15RegexMethodPointcut` 低于jdk1.4的替代品
#### AnnotationMatchingPointcut
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
#### ComposablePointcut
可以进行逻辑运算的pointcut实现。可以进行Pointcut之间的"并"以及"交"运算。
#### controlFlowPointcut
匹配程序的调用流程，不是对某个方法之心所在的Joinpoint处的单一特征进行匹配。

## Advice
### before advice
实现`MethodBeforeAdvice` 
```java
    public interface MethodBeforeAdvice extends BeforeAdvice {
    	void before(Method method, Object[] args, @Nullable Object target) throws Throwable;
    }
```
### throwsAdvice 
对应ThrowsAdvice,是aop概念中的AfterThrowingAdvice

### AfterReturningAdvice
只有在方法正常返回的情况下，才会执行。可以访问到返回值，但是不可以更改返回值，与通用的AfterReturningAdvice的特性有所出入。

### AroundAdvice
spring中没有直接定义对应的Around Advice的实现接口，而是直接采用Aop Alliance的标准接口MethodInterceptor,该接口定义如下:
```java
    public interface MethodInterceptor extends Interceptor {
        Object invoke(MethodInvocation invocation) throws Throwable;
    }
```
### pre-instance类型的Advice
与per-class类型的Advice不同，per-instance类型的Advice不会在目标类所有对象实例之间共享，而是会为不同的实例对象保存它们各自的状态以及相关逻辑。
spring aop中，introduction就是唯一的一种per-instance型Advice。

#### DelegatingIntroductionInterception

#### DelegatePerTargetObjectIntroductionInterceptor


## Aspect
Advisor代表Spring中的Aspect，但是，与正常的Aspect不同，Advisor通常只持有一个Pointcut和一个Advice。而理论上，Aspect定义中可以有多个Pointcut和多个Advice，
所以，我们可以认为Advisor是一种特殊的Aspect。

Advisor有两个分支，一个分支以PointcutAdvisor，另一个分支则以IntroductionAdvisor为头。

### PointcutAdvisor
PointAdvisor才是真正定义一个Pointcut和一个Advice的Advisor，大部分的Advisor实现全都是PointAdvisor的"部下"。

#### 常用的PointAdvisor的实现
+ DefaultPointcutAdvisor: 最通用的PointcutAdvisor实现。
+ NameMatchMethodPointcutAdvisor: 是细化后的DefaultPointcutAdvisor，它限定了自身可以使用的Pointcut类型为NameMatchMethodPointcut，并且外部不可更改。
+ RegexpMethodPointcutAdvisor: 与NameMatchMethodPointcutAdvisor类似，也限定了自身可以使用的Pointcut类型，即只能通过正则表达式为其设置相应的Pointcut。
+ DefaultBeanFactoryPointcutAdvisor: 是使用比较少的一个Advisor实现，因为自身绑定到了BeanFactory，所以，要使用DefaultBeanFactoryPointcutAdvisor，我们
的应用特定要绑定到spring的ioc容器了。而且，通常情况下，DefaultPointcutAdvisor已经完全可以满足需求。

#### IntroductionAdvisor
Introduction与PointcutAdvisor最本质的区别就是，IntroductionAdvisor只能应用于类级别的拦截，只能使用Introduction型的Advice，而不能像PointcutAdvisor那样，
可以使用任何类型的pointcut，以及差不多任何类型的Advice。也就是说，IntroductionAdvisor纯粹就是为了introduction而生的。

### Ordered的作用
顺序号越小优先级越高，优先级排在前面的，将被优先执行。

## Spring Aop的织入
AspectJ采用ajc编译器作为它的织入器；JBoss AOP使用自定义的ClassLoader作为它的织入器；而在Spring Aop中，使用类ProxyFactory作为织入器。

使用proxyFactory只需要指定如下两个最基本的东西。
+ 第一个是要对其进行织入的目标对象。我们可以通过ProxyFactory的构造方法直接传入，也可以在ProxyFactory构造完成之后，通过相应的setter方法进行设置。
+ 第二个是将要应用到目标对象的Aspect。在Spring里面交Advisor，不过，除了可以指定相应的Advisor之外，还可以使用如下代码，直接指定各种类型的Advice。
参考`com.bycsmys.aop.proxyfactory`

满足以下三种情况的任何一种，proxyFactory将对目标类进行基于类的代理
+ 如果目标没有实现任何接口，不管proxyTargetClass的值是什么，proxyFactory会采用类的代理
+ 如果proxyFactory的proxyTargetClass的属性被设置为true，ProxyFactory会采用基于里的代理
+ 如果ProxyFactory的optimize属性被设置为true，proxyFactory会采用基于类的代理

### 看清proxyFactory
#### AopProxy
根据配置创建aopProxy ,`if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config))` 则创建cglib，否则jdkProxy
```java
    public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {
    
    	@Override
    	public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
    		if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
    			Class<?> targetClass = config.getTargetClass();
    			if (targetClass == null) {
    				throw new AopConfigException("TargetSource cannot determine target class: " +
    						"Either an interface or a target is required for proxy creation.");
    			}
    			if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
    				return new JdkDynamicAopProxy(config);
    			}
    			return new ObjenesisCglibAopProxy(config);
    		}
    		else {
    			return new JdkDynamicAopProxy(config);
    		}
    	}
   
    	private boolean hasNoUserSuppliedProxyInterfaces(AdvisedSupport config) {
    		Class<?>[] ifcs = config.getProxiedInterfaces();
    		return (ifcs.length == 0 || (ifcs.length == 1 && SpringProxy.class.isAssignableFrom(ifcs[0])));
    	}
    }
```

DefaultAopProxyFactory 创建代理的方法定义`public AopProxy createAopProxy(AdvisedSupport config)`
AdvisedSupport所承载的信息可以划分为两类，
一类是ProxyConfig为统领，记载生成代理对象的控制信息;
一类以Advised为旗帜，承载生成代理对象所需要的必要信息，如相关目标类、Advice、Advisor等。

proxyConfig其实就是一个普通的javaBean，它定义了5搞个boolean型的属性，分别控制在生成代理对象的时候，应该采取那些行为措施，
+ proxyTargetClass: 属性设置为true，则proxyFactory将会使用CGLIB对目标对象进行代理，默认值false。
+ optimize: 该书型的主要用于告知代理对象是否需要采取进一步的优化措施，如代理对象生成之后，即使为其添加或者移除相应的Advice，代理对象也可以忽略这种变动。
另外我们也曾经提到过，当该属性为true时，proxyFactory会使用cglib进行代理对象的生成。默认情况下，该属性为false。
+ opaque: 该属性用于控制生成的代理对象是否可以强制转型为Advised，默认值为false，表示任何生成的代理对象都可以强制转型为Advised，我们可以通过Advised查询代理对象的一些状态。
+ exposeProxy: 设置exposeProxy，可以让Spring Aop框架在生成代理对象时，将当前代理对象绑定到ThreadLocal。
如果目标对象需要访问当前代理对象，可以通过AopContext.currentProxy()取得。处于性能方面考虑，该属性默认值为false。
+ frozen: 如果将frozen设置为true，那么一旦针对代理对象生成的各项信息配置完成，则不容许更改。比如，如果proxyFactory的设置完毕，
并且frozen为true，则不能对Advice进行任何变动，这样可以优化代理对象生成的性能。默认情况下，该值为false。

#### 容器中的织入器 - proxyFactoryBean
##### proxyFactoryBean的本质
proxyFactoryBean = proxy + FactoryBean
如果将ProxyFactoryBean的singleton属性设置为true，则ProxyFactoryBean的第一次生成代理对象之后，会通过内部实例singletonInstance(Object类型)缓存生成的代理对象。之后所有请求都会返回这一缓存实例，
从而满足singleton的语义。反之，如果将ProxyFactoryBean的singleton属性设置为false，那么ProxyFactoryBean每次都会重新检测各项设置，并为当前调用准备一套新的环境，然后再根据最新的环境数据，返回一个新的代理对象。

##### ProxyFactoryBean的使用
proxyFactoryBean在继承了父类ProxyCreatorSupport的所有配置属性之外，还添加了几个自己独有的
+ proxyInterfaces: 如果我们采用基于接口的代理方式，那么需要通过该属性配置相应的接口类型，这是一个Collection类型实例，所以我们可以通过配置元素<list>来指定一个或者多个接口类型。
实际上，与interfaces属性指定接口类型是等效的。不指定也会自动检测。有个属性autodetectInterfaces属性，默认值true，没有指定则自动检测并进行代理。
+ interceptorNames: 通过该属性，我们可以指定多个将要织入的目标对象的Advice、拦截器以及Advisor，而再也不用通过ProxyFactory那样的addAdvice或者addAdvisor方法一个一个添加。
没有设置目标对象，可以在interceptorNames最后一个属性设置目标对象，不建议。
通过在指定的interceptorNames某个元素名称之后添加*通配符，可以让ProxyFactoryBean在容器中搜寻符合条件的所有的Advisor并应用到目标对象。
singleton。因为ProxyFactoryBean本质上是一个FactoryBean，所以我们可以通过singleton属性，指定每次getObject调用是返回同一个代理对象，还是返回一个新的。

##### spring aop 自动代理
###### 要使用自动代理机制，
需要以spring 的ioc容器为依托。更进一步说，需要使用ApplicationContext类型的Ioc容器。
spring aop的自动代理的实现建立在Ioc容器的BeanPostProcessor概念之上。通过BeanPostProcessor我们可以在遍历容器中所有bean的基础上，对遍历到的bean进行一些操作。
拦截生成代理对象的条件
+ 可以通过外部配置文件传入这些拦截条件信息，比如我们在容器的配置文件中注册的有关Pointcut以及Advisor等就包括这些信息。
+ 还可以在具体类的定义文件中，通过元数据来指明具体的拦截条件是什么，比如可以通过jakarta Commons Attributes或者 java 5的注解，直接在代码类中标注Pointcut等拦截信息。

###### 可用的AutoProxyCreator
spring aop在org.springframework.aop.framework.autoproxy包提供两个常用的AutoProxyCreator，即BeanNameAutoProxyCreator和DefaultAdvisorAutoProxyCreator。
+ BeanNameAutoProxyCreator: 可以通过指定一组容器内饿的目标对象对应的beanName，将指定的一组拦截器应用到这些目标对象之上。
+ DefaultAdvisorAutoProxyCreator: 使用DefaultAdvisorAutoProxyCreator，我们只需要在ApplicationContext的配置文件中注册一下DefaultAdvisorAutoProxyBean的bean定义就可以了。

###### 扩展AutoProxyCreator
可以在spring aop提供的AbstractAutoProxyCreator或者AbstractAdvisorAutoProxyCreator基础之上，实现相应的子类。

## TargetSource
TargetSource最主要的特性就是，每次的方法调用都会触发TargetSource的getTarget()方法，getTarget()方法将从相应的TargetSource实现类中取得具体的目标对象，这样我们就可以控制每次方法调用作用到的具体对象实例:
+ 提供一个目标对象池，每次从TargetSource取得的目标对象都从这个目标对象池中取得。
+ 让一个TargetSource实现类持有多个目标对象实例，然后按照某种规则，在每次方法调用时，返回相应的目标对象实例。

### 可用的TargetSource实现类
+ SingletonTargetSource: 使用最多的TargetSource实现。
+ PrototypeTargetSource: 每次都返回一个新的对象实例
    + 目标对象的bean定义声明的scope必须是prototype
    + 通过targetBeanName属性指定目标的bean定义名称，而不是引用。
+ HotSwappableTargetSource: 可以让我们在应用程序运行的时候，根据某种特定条件，动态地替换目标类饿的具体实现。
+ CommonsPoolTargetSource: 对象池，可以指定对象池的大小，初试对象数量等。  
+ ThreadLocalTargetSource: 不同的线程提供不同的目标对象。

### 自定义TargetSource
要实现自定义的TargetSource，可以直接扩展TargetSource接口。
```java
    public interface TargetSource extends TargetClassAware {
    
    	@Override
    	@Nullable
    	Class<?> getTargetClass();
    	
    	boolean isStatic();
    	
    	@Nullable
    	Object getTarget() throws Exception;
    	
    	void releaseTarget(Object target) throws Exception;
    }
```    
+ getTargetClass()方法返回目标对象的类型
+ isStatic()用于表明是否要返回同一个目标对象实例，SingletonTargetSource的这个方法肯定返回true，其他的实现根据情况，通常返回false
+ getTarget()是核心，要返回哪个目标对象实例
+ 具体调用过程的结束，如果isStatic()为false，则会调用releaseTarget()以释放当前调用的目标对象。但是否需要释放，完全是由实现的需要决定的，大部分时候，该方法可以空着不实现。
详情看`com.bycsmys.aop.targetsource`


    












