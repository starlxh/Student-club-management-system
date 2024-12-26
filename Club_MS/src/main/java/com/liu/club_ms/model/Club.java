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
public class Club {
    @TableId(type = IdType.AUTO)
    private Integer clubId;

    private String clubName;

    private Integer createBy;

    private Integer categoryId;

    private Integer captainId;

    private String createTime;

    private Integer status;

    private String images;

    private String introduction;

    private Integer count;

    @TableField(exist = false)
    private String order;

    @TableField(exist = false)
    private String creatorName;

    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private String captainName;

    public Club(Integer clubId, String images) {
        this.clubId = clubId;
        this.images = images;
    }
}
