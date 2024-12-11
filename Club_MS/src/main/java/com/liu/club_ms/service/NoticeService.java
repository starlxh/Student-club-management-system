package com.liu.club_ms.service;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.LeaveInfo;
import com.liu.club_ms.model.Notice;

public interface NoticeService {

    PageInfo<Notice> queryNoticeList(int page, int limit, String realName, String title, Integer captainId);

    boolean editNotice(Notice notice);

    boolean addNotice(Notice notice);

    boolean deleteById(Integer noticeId);
}
