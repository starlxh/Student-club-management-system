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

    // 分页查询或者高级查询会议记录
    @Override
    public PageInfo<Notice> queryNoticeList(int page, int limit,
                                            String realName,
                                            String title,
                                            Integer captainId,
                                            String clubName,
                                            String order) {
        PageHelper.startPage(page, limit);
        List<Notice> list = noticeMapper.queryNoticeList(
                realName,
                title,
                captainId,
                clubName,
                order);
        return new PageInfo<>(list);
    }

    // 编辑公告
    @Override
    public boolean editNotice(Notice notice) {
        return noticeMapper.editNotice(notice) > 0;
    }

    // 添加公告
    @Override
    public boolean addNotice(Notice notice) {
        return noticeMapper.addNotice(notice) > 0;
    }

    // 通过ID删除公告
    @Override
    public boolean deleteById(Integer noticeId) {
        return noticeMapper.deleteById(noticeId) > 0;
    }

    // 通过ID查询公告
    @Override
    public Notice queryNoticeById(Integer noticeId) {
        return noticeMapper.queryNoticeById(noticeId);
    }
}
