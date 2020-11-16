package com.abc.mybatisplus.controller;


import com.abc.mybatisplus.entity.Role;
import com.abc.mybatisplus.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author stephen
 * @since 2020-11-16
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/detail")
    public List<Role> detail() {
        return roleService.fetchRoles();
    }

    @RequestMapping("/info/{id}")
    public Role info(@PathVariable String id) {
        return roleService.getById(id);
    }

}
