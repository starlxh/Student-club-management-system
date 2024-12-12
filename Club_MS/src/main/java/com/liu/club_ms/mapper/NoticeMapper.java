package com.liu.club_ms.mapper;

import com.liu.club_ms.model.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    List<Notice> queryNoticeList(String realName, String title, Integer userId);

    int editNotice(Notice notice);

    int addNotice(Notice notice);

    int deleteById(Integer noticeId);
}
