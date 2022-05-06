### 常用注解
@Autowired: 按照类型匹配进行依赖注入
@Qualifier: 按照ByName自动绑定，当有多个类型的时候，需要通过这个注解指定要注入的BeanName
@Resource: 按照ByName自动绑定，然后查找到的对象事例注入给@Resource标注的对象。
@PostConstruct: 与InitializingBean的init-method 作用类似，在对象实例化之后被调用
@PreDestroy: 与DisposableBean的destroy-method作用类似，在对象销毁之前调用
