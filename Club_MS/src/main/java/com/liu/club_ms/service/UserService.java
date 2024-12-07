package com.liu.club_ms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.club_ms.model.User;

import java.util.List;

public interface UserService extends IService<User> {

    List<User> queryAllIdName();

    User queryByNamePwdType(User user);
}
