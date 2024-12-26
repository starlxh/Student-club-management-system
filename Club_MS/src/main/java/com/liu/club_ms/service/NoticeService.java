package com.liu.club_ms.service;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.Notice;

public interface NoticeService {

    // 分页查询或者高级查询公告
    PageInfo<Notice> queryNoticeList(int page,
                                     int limit,
                                     String realName,
                                     String title,
                                     Integer captainId,
                                     String clubName,
                                     String order);

    // 编辑公告
    boolean editNotice(Notice notice);

    // 添加公告
    boolean addNotice(Notice notice);

    // 通过ID删除公告
    boolean deleteById(Integer noticeId);

    // 通过ID查询公告
    Notice queryNoticeById(Integer noticeId);
}
