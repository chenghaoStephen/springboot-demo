package com.abc.shiro.service.impl;

import com.abc.shiro.dao.RoleDao;
import com.abc.shiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Set<String> fetchRolesByUsername(String username) {
        return roleDao.fetchRolesByUsername(username);
    }
}
