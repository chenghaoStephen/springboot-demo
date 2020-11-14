package com.abc.shiro.service;

import java.util.Set;

public interface PermissionService {
    Set<String> fetchPermissionsByUsername(String username);
}
