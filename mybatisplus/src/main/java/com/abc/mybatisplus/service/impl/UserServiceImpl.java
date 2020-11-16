package com.abc.mybatisplus.service.impl;

import com.abc.mybatisplus.entity.User;
import com.abc.mybatisplus.mapper.UserMapper;
import com.abc.mybatisplus.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
