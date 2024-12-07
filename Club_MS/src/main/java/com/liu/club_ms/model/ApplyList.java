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

    private Integer  status;

    private String createTime;

    private Integer  applyAuthorId;

    @TableField(exist = true)
    private User user;
}
