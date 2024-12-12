package com.liu.club_ms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.club_ms.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

//  高级查询
    List<User> queryUserList();

//  社团页面查询数据
    List<User> queryUserIdNameList();

//  根据用户名,密码,类型
    User queryUserByNamePwdType(User admin);

    int emailIsExist(String email);
}
