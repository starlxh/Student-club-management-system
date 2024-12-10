package com.liu.club_ms.mapper;

import com.liu.club_ms.model.ApplyInfo;
import com.liu.club_ms.model.ApplyList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplyMapper {
//  高级查询
    List<ApplyInfo> queryApplyInfoList(ApplyInfo applyInfo);


//  根据申请id 查询审核记录
    List<ApplyList> queryApplyListByApplyInfoId(@Param("applyInfoId") Integer applyInfoId);

//  修改状态
    Integer updateStatus(ApplyInfo applyInfo);

//  添加审批记录信息
    Integer insertApplyList(ApplyList applyList);
}
