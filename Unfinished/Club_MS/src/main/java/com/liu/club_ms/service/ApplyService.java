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

    //  修改状态
    boolean updateStatus(ApplyInfo applyInfo);

    //  添加审批记录信息
    boolean insertApplyList(ApplyList applyList);
}
