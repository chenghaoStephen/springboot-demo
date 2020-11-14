package com.abc.shiro.service;

import com.abc.shiro.entity.UserEntity;

public interface UserService {

    public UserEntity loadByUsername(String username);

}
