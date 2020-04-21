package com.wwl.factory;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/*
* 一个创建Bean对象的工厂
*
* Bean：在计算机英语中，又可重用组件的含义
* JavaBean：用java语言编写的可重用组件(一个service、一个dao都是可以被其他类重用的组件)
* 工厂类用于创建我们的service和dao对象
* 过程：
*   1、需要一个配置文件来配置我们的service和dao
*      配置的内容：唯一标志=全限定类名(key=value)
*        【accountService=com.wwl.service.impl.AccountServiceImpl
           accountDao=com.wwl.dao.impl.AccountDaoImpl】
*   2、通过读取配置文件中的内容，反射创建bean对象
* 配置文件可以是xml和properties
* */
public class BeanFactory {
//    使用静态变量定义一个Properties对象,用来获取配置文件
    private static Properties props;

//    定义一个Map，用于存放我们要创建的对象，称之为容器
    private static Map<String,Object> beans;

//    使用静态代码块为Properties对象赋值
    static {
        try {
            props = new Properties(); //这里也用到了new关键字，可见耦合只能降低，不能消除
//    此方法的意思是通过加载器在classPath目录下获取资源，并且是以流的形式（因为src目录下的代码和资源文件会被编译进classes文件夹，classPath目录其实就是这个classes目录）
            InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
            props.load(in);
            beans = new HashMap<String, Object>();
//          取出配置文件中所有的key
            Enumeration keys = props.keys();
//          遍历枚举
            while (keys.hasMoreElements()){
//                取出每个key
                String key = keys.nextElement().toString();
//                根据key获取value
                String beanPath = props.getProperty(key);
//                反射创建对象
                Object value = Class.forName(beanPath).newInstance();
//                把key和value存入容器
                beans.put(key,value);
            }
        }catch (Exception e){
            throw new ExceptionInInitializerError("初始化properties失败！");
        }

    }
/*
*   根据bean的名称获取单例对象
* */
    public static Object getBean(String beanName){
        return beans.get(beanName);
    }

    /*
    * 根据bean的id获取多例bean对象
    * static：直接用类名调用
    * */
//    public static Object getBean(String beanName){
//        Object bean = null;
//        try {
//            String beanPath = props.getProperty(beanName);
////            这里的bean应该类的实例而不是类
//            //这个bean是多例的。因为每次都会调用默认构造函数创建新对象，所以要在第一个对象创建出来的时候放入map容器中存起来
//            bean = Class.forName(beanPath).newInstance();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return bean;
//    }
}
