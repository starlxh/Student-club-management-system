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
public class CostList {
    @TableId(type = IdType.AUTO)
    private Integer costListId;

    private Integer clubId;

    private String name;

    private Integer price;

    private String remarks;

    private String createTime;

    @TableField(exist = false)
    private String clubName;
}
