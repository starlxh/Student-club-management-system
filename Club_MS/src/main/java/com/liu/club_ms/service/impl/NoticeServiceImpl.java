package com.liu.club_ms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.NoticeMapper;
import com.liu.club_ms.model.Notice;
import com.liu.club_ms.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public PageInfo<Notice> queryNoticeList(int page, int limit, String realName, String title, Integer userId) {
        PageHelper.startPage(page, limit);
        List<Notice> list = noticeMapper.queryNoticeList(realName, title, userId);
        return new PageInfo<>(list);
    }

    @Override
    public boolean editNotice(Notice notice) {
        int num = noticeMapper.editNotice(notice);
        return num > 0;
    }

    @Override
    public boolean addNotice(Notice notice) {
        int num = noticeMapper.addNotice(notice);
        return num > 0;
    }

    @Override
    public boolean deleteById(Integer noticeId) {
        int num = noticeMapper.deleteById(noticeId);
        return num > 0;
    }


}
