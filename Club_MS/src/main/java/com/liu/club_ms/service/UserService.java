package com.liu.club_ms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.User;

import java.util.List;

public interface UserService extends IService<User> {

    List<User> queryAllIdName();

    User queryByNamePwdType(User user);

    /*用于分页查询或者高级查询社团信息*/
    PageInfo<User> queryUserList(Integer page, Integer limit, User user);
    //查询邮箱是否存在
    boolean emailIsExist(String email);
}
