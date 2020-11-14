package com.abc.shiro.service.impl;

import com.abc.shiro.dao.UserDao;
import com.abc.shiro.entity.UserEntity;
import com.abc.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity loadByUsername(String username) {
        return userDao.loadByUsername(username);
    }
}
