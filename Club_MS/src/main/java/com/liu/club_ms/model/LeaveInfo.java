package com.liu.club_ms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeaveInfo {
    @TableId(type = IdType.AUTO)
    private Integer leaveInfoId;

    private Integer userId;

    private Integer clubId;

    private String content;

    private String createTime;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String realName;

    @TableField(exist = false)
    private String clubName;
}
