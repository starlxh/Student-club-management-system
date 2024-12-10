package com.liu.club_ms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.ApplyMapper;
import com.liu.club_ms.model.ApplyInfo;
import com.liu.club_ms.model.ApplyList;
import com.liu.club_ms.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyServiceImpl
        implements ApplyService {
    @Autowired
    ApplyMapper applyMapper;

    @Override
    public PageInfo<ApplyInfo> queryApplyInfoListByPage(int page, int limit, ApplyInfo applyInfo) {
        PageHelper.startPage(page, limit);
        List<ApplyInfo> list = applyMapper.queryApplyInfoList(applyInfo);
        return new PageInfo<>(list);
    }

    @Override
    public List<ApplyList> queryApplyListByApplyInfoId(Integer applyInfoId) {
        return applyMapper.queryApplyListByApplyInfoId(applyInfoId);
    }

    @Override
    public boolean updateStatus(ApplyInfo applyInfo) {
        return applyMapper.updateStatus(applyInfo) > 0;
    }

    @Override
    public boolean insertApplyList(ApplyList applyList) {
        return applyMapper.insertApplyList(applyList) > 0;
    }
}
