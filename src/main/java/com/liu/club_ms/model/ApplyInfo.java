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
public class ApplyInfo {
    @TableId(type = IdType.AUTO)
    private Integer applyInfoId;

    private String userId;

    private String content;

    private String createTime;

    private Integer status;

    private Integer clubId;

    //添加社团对象
    @TableField(exist = false)
    private Club club;

    @TableField(exist = false)
    private User user;
}
