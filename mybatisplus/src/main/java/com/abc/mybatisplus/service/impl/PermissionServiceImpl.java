package com.abc.mybatisplus.service.impl;

import com.abc.mybatisplus.entity.Permission;
import com.abc.mybatisplus.mapper.PermissionMapper;
import com.abc.mybatisplus.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stephen
 * @since 2020-11-16
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
