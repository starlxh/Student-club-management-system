package com.liu.club_ms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Activity {
    private Integer activityId;

    private String name;

    private Integer clubId;

    private String tel;

    private String images;

    private String acInfo;

    private String acTime;

    private String acAddress;

    private Integer userId;

    private String createTime;

    private Integer hostId;

    private Integer status;

    @TableField(exist = false)
    private String creatorName;

    @TableField(exist = false)
    private String hostName;

    @TableField(exist = false)
    private String clubName;
}
