package com.liu.club_ms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userId;

    private String userName;

    private String password;

    private String realName;

    private String sex;

    private String tel;

    private String email;

    private String qq;

    private String wx;

    private String image;

    private Integer type;

    private String createTime;

    public User(String realName){
        this.realName = realName;
    }
}
