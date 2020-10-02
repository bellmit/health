package com.health.service;

import com.health.pojo.User;

public interface UserService {
    //获取用户的角色权限信息
    User findByUsername(String username);
}
