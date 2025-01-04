package com.liu.club_ms.mapper;

import com.liu.club_ms.model.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    // 分页查询或者高级查询公告
    List<Notice> queryNoticeList(String realName,
                                 String title,
                                 Integer captainId,
                                 String clubName,
                                 String order);

    // 编辑公告
    Integer editNotice(Notice notice);

    // 添加公告
    Integer addNotice(Notice notice);

    // 通过ID删除公告
    Integer deleteById(Integer noticeId);

    // 通过社团ID删除公告
    Integer deleteByClubId(Integer clubId);

    // 通过删除的发布者ID更新公告
    Integer updateNoticeWhenDeleteUser(Integer userId);

    // 通过ID查询公告
    Notice queryNoticeById(Integer noticeId);
}
