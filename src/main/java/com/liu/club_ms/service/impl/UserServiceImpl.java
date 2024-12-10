package com.liu.club_ms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.club_ms.mapper.UserMapper;
import com.liu.club_ms.model.User;
import com.liu.club_ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> queryAllIdName() {
        return userMapper.queryUserIdNameList();
    }

    @Override
    public User queryByNamePwdType(User user) {
        return userMapper.queryUserByNamePwdType(user);
    }
}
