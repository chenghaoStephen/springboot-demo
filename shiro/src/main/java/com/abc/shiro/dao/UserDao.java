package com.abc.shiro.dao;

import com.abc.shiro.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    UserEntity loadByUsername(String username);
}
