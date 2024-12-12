package com.liu.club_ms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notice {
    @TableId(type = IdType.AUTO)
    private Integer noticeId;

    private String title;

    private String content;

    private Integer userId;

    private String createTime;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String realName;
}
