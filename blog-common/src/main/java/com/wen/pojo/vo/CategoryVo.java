package com.wen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
public class CategoryVo {
    // 文章分类id
    private Long id;
    // 文章分类名称
    private String name;
    // 文章分类描述
    private String description;
}
