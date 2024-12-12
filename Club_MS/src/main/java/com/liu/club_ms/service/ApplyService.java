package com.liu.club_ms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.ApplyInfo;
import com.liu.club_ms.model.ApplyList;

import java.util.List;

public interface ApplyService {
    //  高级查询
    PageInfo<ApplyInfo> queryApplyInfoListByPage(int page, int limit, ApplyInfo applyInfo);

    //  根据申请id 查询审核记录
    List<ApplyList> queryApplyListByApplyInfoId(Integer applyInfoId);

    boolean addApplyList(ApplyList applyList);

    boolean editApplyInfoStatusById(Integer status, Integer applyInfoId);

    boolean addApplyInfo(ApplyInfo applyInfo);

    boolean deleteApplyListByApplyInfoId(Integer applyInfoId);

    boolean deleteApplyInfoById(Integer applyInfoId);
}
