package com.abc.cache.service.impl;

import com.abc.cache.dao.UserDao;
import com.abc.cache.entity.User;
import com.abc.cache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Cacheable(value = "user", key = "#id")
    @Override
    public User loadById(int id) {
        return userDao.loadById(id);
    }

    @Cacheable(value = "user", key = "#user.id")
    @Override
    public User saveUser(User user) {
        userDao.saveUser(user);
        return user;
    }

    @CachePut(value = "user",key = "#user.id")
    @Override
    public User updateById(User user) {
        userDao.updateById(user);
        return user;
    }

    @CacheEvict(value = "user", key = "#id")
    @Override
    public void deleteById(int id) {
        userDao.deleteById(id);
    }
}
