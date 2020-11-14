package com.abc.shiro.service;

import java.util.Set;

public interface RoleService {
    Set<String> fetchRolesByUsername(String username);
}
