package com.liu.club_ms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.club_ms.model.Admin;

import java.util.List;

public interface AdminService extends IService<Admin> {

    List<Admin> queryAllIdName();

    Admin queryByNamePassType(Admin admin);
}
