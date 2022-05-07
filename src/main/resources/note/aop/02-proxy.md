
### 静态代理
定一个一个接口，存在一个具体实现，
一个代理实现，代理实现持有一个具体实现的成员变量，调用代理实现，可以在调用具体实现前后执行其他操作
事例：`com.bycsmys.aop.staticproxy`

### 动态代理
#### jdk动态代理
jdk动态代理只能代理接口，不能代理具体实现类 通过implments InvocationHandler 实现
事例：`com.bycsmys.aop.dynamicproxy`

#### cglib代理
cglib 即可以代理接口也可以代理具体对象，唯一的限制就是无法对final方法进行复写
