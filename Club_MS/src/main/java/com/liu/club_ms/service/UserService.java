package com.liu.club_ms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.AdminStatus;
import com.liu.club_ms.model.User;

import java.util.List;

public interface UserService extends IService<User> {
/* 用户管理相关 */
    // 通过邮箱和密码查询用户
    User queryUserByEmailPwd(User user);

    // 用于分页查询或者高级查询用户信息
    PageInfo<User> queryUserList(Integer page, Integer limit,
                                 String userName,
                                 Integer type,
                                 String order);

    // 查询邮箱是否存在
    boolean emailIsExist(String email);

    // 通过ID删除用户
    boolean deleteById(Integer userId, Integer type);

    // 编辑用户信息
    boolean editUser(User user);

    // 添加用户
    boolean addUser(User user);

    // 通过用户ID获取用户信息
    User getUserInfoById(Integer userId);

    // 通过邮箱查找用户ID
    Integer queryUserIdByEmail(String email);

/* 管理员管理相关 */
    // 用于分页查询或者高级查询所有管理员信息
    PageInfo<User> queryAdminList(Integer page, Integer limit,
                                  String userName,
                                  Integer type,
                                  String order);

    // 查询所有管理员信息
    List<User> queryAllAdminList();

    // 通过ID删除管理员
    boolean deleteAdminById(Integer adminId);

    // 编辑管理员状态
    boolean editAdminStatus(AdminStatus adminStatus);

    // 添加管理员
    boolean addAdmin(User user);

    // 通过ID获取邮箱
    String getEmailByUserId(Integer userId);

    // 用于查询所有的社长
    List<User> queryAllCaptainInfo();
}
