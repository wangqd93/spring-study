
### 常用的applicationContext
+ `FileSystemXmlApplicationContext`: 在默认情况下，从文件系统加载bean定义以及相关资源的`ApplicationContext`实现。
+ `ClassPathXmlApplicationContext`: 从classpath加载bean定义以及相关资源的`ApplicationContext`实现。
+ `XmlWebApplicationContetxt`: spring提供的用于web应用程序的`ApplicationContext`实现。


### Resource 资源 及 ResourceLoader 资源加载
#### 常用的Resource实现
+ ByteArrayResource: 将字节数组提供的数据作为一种资源的封装。
+ ClassPathResource: 从java应用程序的ClassPath中加载具体资源并进行封装，可以使用指定的类加载器(ClassLoader)或者给定的类进行资源加载。
+ FileSystemResource:对java.io.File类型的封装
+ InputStreamResource: 给定的InputStream视为一种资源的Resource实现类。

#### ResourceLoader
##### 常用的ResourceLoader
+ DefaultResourceLoader: 首先检查资源路径是否以classpath:前缀开头，如果是则尝试构造ClassPathResource型资源并返回，
否则尝试通过url，如果还是无法定位则委派给getResourceByPath(String)方法来定位,DefaultResourceLoader的getResourceByPath(String)
方法默认实现逻辑是 ，构造ClassPathResource类型的资源并返回。代码见`com.bycsmys.ioc.spring.resource.DefaultResourceLoaderTest.main`

+ FileSystemResourceLoader
为了避免DefaultResourceLoader在最后getResourcePath(String)方法上的不恰当处理，我们可以使用FileSystemResourceLoader,它继承自DefaultResourcceLoader,
但覆写了getResourceByPath(String)方法，使之从文件系统加载资源并以FileSystemResource类型返回。

#### ResourcePatternResolver - 批量查找的ResourceLoader
ResourcePatternResolver是 ResourceLoader 的扩展，ResourceLoader每次自能根据资源路径返回确定的单个Resource实例，而ResourcePatternResolver则可以根据指定
的资源路径匹配模式，每次返回多个Resource实例。
ResourcePatternResolver最常用的一个实现是PathMatchingResourcePatternResolver,该实现类支持ResourceLoader级别的资源加载，支持基于Ant风格的路径匹配模式
(类似于**/*.suffix之类的路径形式),支持ResourcePatternResolver新增加的classpath:*前缀等，基本上集所有技能于一身。

#### ApplicationContext与ResourceLoader
ApplicationContext继承了ResourcePatternResolver,当然就间接实现了ResourceLoader接口。所以任何的ApplicationContext实现都可以看作是一个ResourceLoader
甚至ResourcePatternResolver。而这就是ApplicationContext支持spring内统一资源加载策略的真相。
+ 扮演resourceLoader 参见 `com.bycsmys.ioc.spring.applicationcontext.ApplicationContextResourceTest`
+ ResourceLoader类型的注入 通过`ApplicationContextAware` 注入 或 `ResourceLoaderAware`
+ Resource类型注入
+ 特定情况下，ApplicationContext的Resource加载行为

#### ApplicationContext与MessageResource(国际化)
先忽略

#### ApplicationContext与ApplicationEventPublisher
##### jdk自定义事件发布
javaSE提供了实现自定义事件发布功能的基础类，即java.util.EventObject类和java.util.EventListener接口。所有的自定义事件类型可以通过扩展EventObject来实现，而事件的监听器则扩展自EventListener.
事例代码`com.bycsmys.ioc.event`

##### spring的容器内事件发布类结构分析
ApplicationEvent 继承自EventObject，它是一个抽象类，需要根据情况提供相应的子类以区分不同情况。默认情况下，Spring提供了三个实现：
+ ContextClosedEvent:ApplicationContext容器在即将关闭的时候发布的事件类型。
+ ContextRefreshEvent:ApplicationContext容器在初始化或者刷新的时候发布的事件类型。
+ RequestHandledEvent: Web请求处理后发布的事件，其有一子类ServletRequestHandledEvent提供特定与java EE的Servlet相关事件。

ApplicationListener: ApplicationContext容器内使用的自定义事件监听器接口定义，继承自EventListener.ApplicationContext容器在启动时，
会自动并加载EventListener类型bean定义，一旦容器内有事件发布，将通知这些注册到容器的EventListener。

ApplicationContext: 接口定义继承了ApplicationEventPublisher 接口，该接口提供了void publishEvent(ApplicationEvent event)方法定义。
不难看出，ApplicationContext容器现在担当的就是事件发布者角色。

ApplicationContext容器的具体实现类在实现事件的发布和事件监听器的注册方面，并没有事必躬亲，而是把这些活转包给了一个称作ApplicationEventMulticaster接口。
该接口定义了具体事件监听器的注册管理以及事件发布的方法。但接口终归是接口，还得有具体实现。ApplicationEventMulticaster有一抽象实现类AbstractApplicationEventMulticaster,
它实现了事件监听器的管理功能。出于灵活性和扩展性考虑，事件的发布功能则委托给了其子类。SimpleApplicationEventMulticaster是spring提供的AbstractApplicationEventMulticaster
的一个子类实现，添加了事件发布功能的实现。不过，默认使用了SyncTaskExecutor进行事件的发布。与我们给出的样例事件发布者一样，事件是同步顺序发布的。
为了避免这种方式可能存在的性能问题，我们可以为其提供其他类型的TaskExecutor实现类。事例`com.bycsmys.ioc.spring.event`
















