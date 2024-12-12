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
public class ClubMember {
    @TableId(type = IdType.AUTO)
    private Integer clubMemberId;

    private Integer clubId;

    private Integer userId;

    private String joinTime;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Club club;

}
