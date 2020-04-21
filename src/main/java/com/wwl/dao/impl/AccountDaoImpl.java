package com.wwl.dao.impl;

import com.wwl.dao.IAccountDao;
/*
* 账户的持久层实现类
* */
public class AccountDaoImpl implements IAccountDao {
    public void saveAccount() {
        System.out.println("保存了账户");
    }
}
