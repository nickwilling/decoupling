本项目演示spring解耦思路
## classpath
src目录下的代码和资源文件会被编译进classes文件夹，classPath目录其实就是这个classes目录，而资源文件(resource)下面的文件会直接被编译到classes文件夹下。
```shell
-classes[classpath]
    [src.main.java]
    -com
        -wwl
            -dao
            -factory
            -jdbc
            -service
            -ui
    -META_INF
    [src.main.resources]
    -bean.properties
```

## driver
连接mysql5之前的版本的数据库驱动类是`com.mysql.jdbc.Driver`

现在的mysql的版本是8.几了，使用的驱动类是`com.mysql.cj.jdbc.Driver`

## 文件目录
- jdbc.JdbcDemo1：演示了耦合的数据库连接过程以及解耦的思路
- factory.BeanFactory演示了工厂模式用bean工厂生产多例对象和单例对象[使用容器创建]
- bean2.xml：bean创建的三种方式
## bean工厂
工厂模式解耦：需要什么，工厂就生产什么，依赖的对象发生了改变，以后只需要改动工厂就行了，减少依赖。

使用反射来创建对象:
- Class.forName(“”)返回的是类。
- Class.forName(“”).newInstance()返回的是object 。

## 单例和多例
- 单例的对象只会被创建一次，从而类中的成员也就只会初始化一次
- 多例对象会被创建多次，执行效率没有单例对象高

## 控制反转
    1、APP可以通过new关键字自己去指定资源
    private IAccountDao accountDao = new AccountDaoImpl();
    2、APP通过指定配置文件，让bean工厂去创建对象，放弃了自己直接创建对象的权利
    IAccountDao accountDao = (IAccountDao) BeanFactory.getBean("accountDao");
 
 控制反转（Inversion Of Control，IOC）把创建对象的权利交给框架。它包括依赖注入（Dependency injection，DI）和依赖查找（Dependency Lookup）
 
 **IOC的作用：** 削减程序的耦合（解除代码中的依赖关系），只能降低，不能消除
 
 ## 配置XML文件
 去Spring官网找参考文档下面的Core文档搜索xmlns获取最新的配置件
 
 ![Reference Doc](https://docs.spring.io/spring/docs/5.2.5.RELEASE/spring-framework-reference/)
 
 ## Spring核心容器
 核心容器(Core Container)
 - Beans
 - Core
 - Context
 - SpEL
 ### 核心容器之ApplicationContext接口（单例对象适用）
 实际开放中多采用此接口定义容器对象。
 
 它在构建核心容器时，创建对象采取的策略是立即加载的方式，只要一读取完配置文件马上就创建文件中配置的对象。
 ```java
//这行代码执行完以后就把bean.xml下配置的所有bean都加载了。
 ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml")
```
 实现类：
 
 基于xml配置的实现类：
 - ClassPathXmlApplicationContext：它可以加载类路径下的配置文件，要求配置文件必须在类路径下。不在的话加载不了
 - FileSystemXmlApplicationContext：它可以加在磁盘任意路径下的配置文件（必须有访问权限）
 
 基于注解配置的实现类
 - AnnotationConfigApplicationContext：用于读取注解创建的容器
  
 ### 核心容器之BeanFactory接口（多例对象适用）
 它在构建核心容器时，创建对象采取的策略是延迟加载的方式，什么时候根据id获取对象了，什么时候才真正创建对象。
 ```java
        Resource resource = new ClassPathResource("bean.xml");
        BeanFactory factory = new XmlBeanFactory(resource);
//      这行代码执行完以后才会创建accountService这个bean
        IAccountService service = factory.getBean("accountService",AccountServiceImpl.class);
```
 ### bean创建的三种方式
 ```xml
<!--    第一种方式：使用默认构造函数-->
    <bean id="accountService" class="com.wwl.service.impl.AccountServiceImpl"></bean>

<!--    第二种方式：某个类没有默认构造函数。比如使用普通工厂中的普通方法创建对象(使用某个类中的普通方法创建对象)，并存入-spring容器-->
<!--    这样创建出来的bean是一个工厂对象，但我只想要里面的返回值对象accountService-->
<!--    普通方法执行前需要实例化类，所以要先有工厂类实例以后才能创建工厂类创建的类，必须分为两步-->
    <bean id="instanceFactory" class="com.wwl.factory.InstanceFactory"></bean>
    <bean id="accountService2" factory-bean="instanceFactory" factory-method="getAccountService"></bean>

<!-- 第三种方式：使用工厂中的静态方法创建对象(使用某个类中的静态方法创建对象)，并存入spring容器   -->
<!--    静态方法不需要实例化类，所以可以直接调用方法-->
    <bean id="accountService3" class="com.wwl.factory.InstanceFactory" factory-method="getAccountService2"></bean>

```

### bean对象的生命周期：
         单例对象：
            出生：当容器创建时对象出生
            活着：只要容器还在，对象一直活着
            死亡：容器销毁，对象消亡
            总结：单例对象的生命周期和容器相同
        多例对象：
            出生：使用对象的时候spring为我们创建
            活着：对象只要是在使用过程中就一直活着
            死亡：spring容器是不知道多例对象什么时候不用的，当对象长时间不用且没有别的对象引用时，由java的垃圾回收机制销毁
