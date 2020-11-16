package com.abc.mybatisplus.service;

import com.abc.mybatisplus.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stephen
 * @since 2020-11-16
 */
public interface IRoleService extends IService<Role> {
    List<Role> fetchRoles();
}
