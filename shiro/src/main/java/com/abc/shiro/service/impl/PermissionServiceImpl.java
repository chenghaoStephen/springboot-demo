package com.abc.shiro.service.impl;

import com.abc.shiro.dao.PermissionDao;
import com.abc.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Set<String> fetchPermissionsByUsername(String username) {
        return permissionDao.fetchPermissionsByUsername(username);
    }
}
