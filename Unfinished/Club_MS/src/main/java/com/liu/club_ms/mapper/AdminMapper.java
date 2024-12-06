package com.liu.club_ms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.club_ms.model.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

//  高级查询
    List<Admin> queryAdminList();

//  社团页面查询数据
    List<Admin> queryAdminIdNameList();

//  根据用户名,密码,类型
    Admin queryAdminByNamePwdType(Admin admin);
}
