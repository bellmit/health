package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.dao.UserDao;
import com.health.pojo.User;
import com.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: health_parent
 * @description:
 * @author: hw
 * @create: 2020-09-28 17:42
 **/
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    //获取用户角色权限信息
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
