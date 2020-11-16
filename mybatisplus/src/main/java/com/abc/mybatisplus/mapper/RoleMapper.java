package com.abc.mybatisplus.mapper;

import com.abc.mybatisplus.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stephen
 * @since 2020-11-16
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> fetchRoles();
}
