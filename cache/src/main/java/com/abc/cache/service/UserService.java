package com.abc.cache.service;

import com.abc.cache.entity.User;

public interface UserService {
    User loadById(int id);

    User saveUser(User user);

    User updateById(User user);

    void deleteById(int id);
}
