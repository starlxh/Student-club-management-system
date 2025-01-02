package com.liu.club_ms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.club_ms.model.AdminStatus;
import com.liu.club_ms.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

/* 用户管理相关 */
    // 分页查询或高级查询用户
    List<User> queryUserList(String realName, Integer type, String order);

    // 通过邮箱和密码查询用户
    User queryUserByEmailPwd(User user);

    // 检查邮箱是否存在
    Integer emailIsExist(String email);

    // 通过用户ID删除用户
    Integer deleteUserById(Integer id);

    // 编辑用户信息
    Integer editUser(User user);

    // 添加用户信息
    Integer addUser(User user);

    // 通过用户ID获取用户信息
    User getUserInfoById(Integer userId);

    // 通过ID获取邮箱
    String getEmailByUserId(Integer userId);

    // 通过邮箱查找用户ID
    Integer queryUserIdByEmail(String email);

/* 管理员管理相关 */
    // 分页查询或高级查询社团管理员和普通用户
    List<User> queryAdminList(String realName, Integer type, String order);

    // 编辑管理员状态
    Integer editAdminStatus(AdminStatus adminStatus);

    // 添加管理员状态
    Integer addAdminStatus(Integer userId);

    // 通过管理员ID删除管理员状态
    Integer deleteAdminStatus(Integer adminId);

    // 查询所有管理员
    List<User> queryAllAdminList();

    // 用于查询所有的社长
    List<User> queryAllCaptainInfo();
}
