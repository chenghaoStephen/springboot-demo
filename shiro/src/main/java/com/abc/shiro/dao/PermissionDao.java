package com.abc.shiro.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface PermissionDao {
    Set<String> fetchPermissionsByUsername(String username);
}
