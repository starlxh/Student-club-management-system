package com.liu.club_ms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @TableId(type = IdType.AUTO)
    private Integer adminId;

    private String adminName;

    private String adminPass;

    private String realName;

    private String sex;

    private String tel;

    private String email;

    private Integer status;

    private Integer adminType;

    private String createTime;

}
