package com.liu.club_ms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingInfo {
    @TableId(type = IdType.AUTO)
    private Integer miId;

    private String name;

    private String startTime;

    private String endTime;

    private Integer clubId;

    private Integer userId;

    private String password;

    @TableField(exist = false)
    private String order;

    @TableField(exist = false)
    private String clubName;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String realName;
}
