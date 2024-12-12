package com.liu.club_ms.service.impl;

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
        List<ApplyInfo> applyInfoList = applyMapper.queryApplyInfoList(applyInfo);
        return new PageInfo<>(applyInfoList);
    }

    @Override
    public List<ApplyList> queryApplyListByApplyInfoId(Integer applyInfoId) {
        return applyMapper.queryApplyListByApplyInfoId(applyInfoId);
    }

    @Override
    public boolean addApplyList(ApplyList applyList) {
        return applyMapper.addApplyList(applyList) > 0;
    }

    @Override
    public boolean editApplyInfoStatusById(Integer status, Integer applyInfoId) {
        return applyMapper.editApplyInfoStatusById(status, applyInfoId) > 0;
    }

    @Override
    public boolean addApplyInfo(ApplyInfo applyInfo) {
        return applyMapper.addApplyInfo(applyInfo) > 0;
    }

    @Override
    public boolean deleteApplyListByApplyInfoId(Integer applyInfoId) {
        return applyMapper.deleteApplyListByApplyInfoId(applyInfoId) > 0;
    }

    @Override
    public boolean deleteApplyInfoById(Integer applyInfoId) {
        return applyMapper.deleteApplyInfoById(applyInfoId) > 0;
    }

}
