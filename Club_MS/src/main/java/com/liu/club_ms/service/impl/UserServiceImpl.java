package com.liu.club_ms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.UserMapper;
import com.liu.club_ms.model.Club;
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

    /*用于分页查询或者高级查询社团信息*/
    @Override
    public PageInfo<User> queryUserList(Integer page, Integer limit, User user) {
        PageHelper.startPage(page, limit);
        List<User> list = userMapper.queryUserList();
        return new PageInfo<>(list);
    }

    @Override
    public boolean emailIsExist(String email) {
        return userMapper.emailIsExist(email) > 0;
    }
}
