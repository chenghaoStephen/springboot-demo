package com.abc.mybatisplus.service.impl;

import com.abc.mybatisplus.entity.Role;
import com.abc.mybatisplus.mapper.RoleMapper;
import com.abc.mybatisplus.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stephen
 * @since 2020-11-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> fetchRoles() {
        return roleMapper.fetchRoles();
    }
}
