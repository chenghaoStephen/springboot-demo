package com.abc.shiro.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface RoleDao {
    Set<String> fetchRolesByUsername(String username);
}
