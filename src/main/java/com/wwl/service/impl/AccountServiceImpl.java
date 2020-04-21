package com.wwl.service.impl;

import com.wwl.dao.IAccountDao;
import com.wwl.dao.impl.AccountDaoImpl;
import com.wwl.factory.BeanFactory;
import com.wwl.service.IAccountService;

/*
* 账户的业务层实现类
* */
public class AccountServiceImpl implements IAccountService {
    public AccountServiceImpl(){
        System.out.println("对象创建了");
    }
//    1.这里就产生了类的耦合，应该要避免使用new创建对象
//    private IAccountDao accountDao = new AccountDaoImpl();
//    2.使用自己写的bean工厂创建bean实例
//    IAccountDao accountDao = (IAccountDao) BeanFactory.getBean("accountDao");
//    3、使用spring管理bean
    IAccountDao accountDao = null;
    private int i = 1;
    public void saveAccount() {
        accountDao.saveAccount();
        System.out.println(i);
        i++;//每次调用这个函数i就+1
    }
}
