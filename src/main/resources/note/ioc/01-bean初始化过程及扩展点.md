### spring ico 总体流程
#### 1、容器启动阶段
`beanDefinitionReader` 对加载的Configuration MetaData 进行解析和分析, 并将分析后的信息编组为相应的`BeanDefinition`,
最后把这些保存了bean定义必要信息的`BeanDefinition`，注册到相应的`BeanDefinitionRegistry`,这样容器启动工作就完成了。

#### 2、Bean实例化阶段
经过第一个阶段，现在所有的Bean定义信息都通过`BeanDefinition`的方式注册到了`BeanDefinitionRegistry`中。当某个请求方通过容器`Bean的getBean`
方法明确地请求某个对象，或者依赖关系容器需要隐式地调用getBean方法时，就会触发第二阶段的活动。
该阶段，容器会首先检查所请求的对象之前是否已经初始化。如果没有，则会根据注册的`BeanDefinition`所提供的信息实例化呗请求对象，并为其注入依赖。
如果该对象实现了某些回调接口，也会根据回调接口的要求来装配它。当该对象装配完毕之后，容器会立即将其返回请求方使用。如果第一阶段只是根据图纸装配
生产线的话，那么第二阶段就是使用装配好的生产线来生产具体的产品。

### 扩展点
#### BeanFactoryPostProcessor
允许我们在容器实例化相应对象之前，对注册到容器的`BeanDefinition`所保存的信息做相应的修改。这就相当于在容器实现的第一阶段最后加入一道工序，
让我们对最终的`BeanDefinition`做一些额外的操作，比如修改其中Bean定义的某些属性，为bean定义增加其他信息等。

spring 几个现成的BeanFactoryProcessor实现类。其中`PropertyPlaceHolderConfigurer`和`PropertyOverrideConfigurer`是两个
比较常用的BeanFactoryPostProcessor。

##### PropertyPlaceHolderConfigurer
`PropertyPlaceHolderConfigurer`允许我们在XML配置文件中使用占位符(PlaceHolder),并将这些占位符所代表的资源单独配置到简单的properties,
文件中加载。以数据源的配置为例，使用了PropertyPlaceHolderConfigurer之后，可以在XML配置文件写类似${jdbc.url}，而不用将链接地址、用户名
和密码等都配置到XML中。

##### PropertyOverrideConfigurer
`PropertyOverrideConfigurer`可以通过占位符，来明确表明bean定义中的property与properties文件中的各配置项之间的对应关系。如果`PropertyOverrideConfigurer`
做的这些是"明事"的话，那相对来说，PropertyOverrideConfigurer所做的可能就有点"神不知鬼不觉"了。

如果要对容器中的某些Bean定义的property信息进行覆盖，我们需要按照如下规则提供一个`PropertyOverrideConfigurer`使用的配置文件:
beanName.propertyName=value

##### CustomEditorConfigurer
我们知道，不管对象什么类型，也不管这些对象所声明的依赖对象是什么类型，通常都是通过XML(或者properties甚至其他媒介)文件格式来配置这些对象类型。
但XML所记载的，都是String类型，即容器从XML格式的文件中读取的都是字符串形式，最终应用程序却是由各种类型的对象所构成。要想完成这种由字符串到具体
对象的转换（不管这个转换工作最终由谁来做），都需要这种转换规相关的信息，而`CustomEditorConfigurer`就是帮助我们传达类似信息的。

+ StringArrayPropertyEditor: 该propertyEditor会将符合CSV格式的字符串转换成String[]数组形式，默认以逗号分割字符串。也可以自定义分隔符
+ ClassEditor: 根据String类型的class名称，直接将其转换成相应Class对象，相当于通过Class.forName(String)完成的功效。
+ FileEditor: Spring提供的对象java.io.File类型的PropertyEditor。
+ LocaleEditor: 针对java.util.Locale类型的PropertyEditor.
+ PatternEditor: 针对java1.4之后才引入的java.util.regex.Pattern的PropertyEditor。

自己实现可以继承`PropertyEditorSupport` 具体可参考`com.bycsmys.ioc.spring.DatePropertyEditor`





