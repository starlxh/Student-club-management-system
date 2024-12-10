package com.liu.club_ms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Club {
    @TableId
    private Integer clubId;

    private String clubName;

    private Integer createBy;

    private Integer categoryId;

    private Integer captainId;

    private String createTime;

    private Integer status;

    private String images;

    @TableField(exist = false)
    private User user;

    @TableField(exist = false)
    private Category category;
}
