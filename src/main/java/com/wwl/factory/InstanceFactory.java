package com.wwl.factory;

import com.wwl.service.IAccountService;
import com.wwl.service.impl.AccountServiceImpl;
/*
* 模拟一个工厂类（该类可能是存在于jar包中的，我们无法通过修改源码的方式来提供默认构造函数）
* */
public class InstanceFactory {
//    工厂里写了一个方法得到Service对象，怎么得到这个对象
    public IAccountService getAccountService(){
        return new AccountServiceImpl();
    }

    public static IAccountService getAccountService2(){
        return new AccountServiceImpl();
    }
}
