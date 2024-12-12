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
public class ApplyList {
    @TableId(type = IdType.AUTO)
    private Integer applyListId;

    private Integer  applyInfoId;

    private String content;

    private String createTime;

    private Integer authorId;

    @TableField(exist = false)
    private Integer status;

    @TableField(exist = false)
    private String authorName;
}
