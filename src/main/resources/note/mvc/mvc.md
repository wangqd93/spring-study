## `ContextLoaderListener`与`WEB-INF/applicationContext.xml`
从`web.xml`开始，我们首先通过<listener>元素增加了一个`ServletContextListener`的定义，即`com.springframework.web.context.ContextLoaderListener`。
`ContextLoaderListener`的职责在于，它将整个的web应用程序加载顶层的`WebApplicationContext`.该顶层的`WebApplicationContext`主要用于提供应用所使用的中间层服务。
我们所使用的数据源(DataSource)定义、数据访问对象(DAO)定义、服务对象(Services)定义等，都在该`WebApplicationContext`中注册。
你完全可以将其比作独立饿的应用程序中我们所使用的ClassPathXlApplicationContext或者FileSystemXmlApplicationContext.
只不过`WebApplicationContext`专门用于Web环境下，在这种容器中，我们可以使用在讲解Spring的IOC容器部分提到的自定义Scope来注册相应的bean定义了，包括request、session等。

`ContextLoaderListener`加载的`WebApplication`的默认配置文件路径为/WEB-INF/applicationContext.xml.


## `DispatcherServlet`与XXX-servlet.xml
`DispatcherServlet`比作`Struts`框架中的ActionServlet。
