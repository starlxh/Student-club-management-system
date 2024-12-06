package com.liu.club_ms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.club_ms.mapper.AdminMapper;
import com.liu.club_ms.model.Admin;
import com.liu.club_ms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl
        extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> queryAllIdName() {
        return adminMapper.queryAdminIdNameList();
    }

    @Override
    public Admin queryByNamePassType(Admin admin) {
        return adminMapper.queryAdminByNamePwdType(admin);
    }
}
