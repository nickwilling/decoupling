package com.wwl.ui;

import com.wwl.dao.IAccountDao;
import com.wwl.service.IAccountService;
import com.wwl.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


/*
* 模拟一个表现层，用于调用业务层
* */
public class Client {
    public static void main(String[] args) {
//        1、用户自己创建bean
//        IAccountService as = new AccountServiceImpl();
//        2、使用beanFactory创建
//        for (int i=0;i<5;i++){
//            IAccountService as = (IAccountService) BeanFactory.getBean("accountService");
//            System.out.println(as);
//            as.saveAccount();
//        }

//        3、spring来创建，获取 spring的核心容器，并根据id获取对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean2.xml");//从classpath里找配置文件，bean.xml在根目录
        for (int i=0; i<5; i++) {
            IAccountService as = (IAccountService) ac.getBean("accountService");
            IAccountDao ad = ac.getBean("accountDao", IAccountDao.class);//这里传入类的字节码了就不用强制转换

            System.out.println(as);
            System.out.println(ad);
            as.saveAccount();
        }

//        4、使用bean工厂来创建容器
//        Resource resource = new ClassPathResource("bean.xml");
//        BeanFactory factory = new XmlBeanFactory(resource);
//        IAccountService service = factory.getBean("accountService",AccountServiceImpl.class);
//        System.out.println(service);

    }
}
