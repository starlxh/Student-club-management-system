package com.liu.club_ms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {
    @TableId(type = IdType.AUTO)
    private Integer categoryId;

    private String categoryName;

    private String remarks;

    private String order;
}
