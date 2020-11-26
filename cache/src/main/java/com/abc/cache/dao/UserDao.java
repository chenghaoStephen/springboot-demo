package com.abc.cache.dao;

import com.abc.cache.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    User loadById(int id);

    void saveUser(User user);

    void updateById(User user);

    void deleteById(int id);

}