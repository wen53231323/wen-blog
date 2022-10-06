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
public class HotArticleVo {
    // 文章id
    private Long id;
    // 文章标题
    private String title;
    //访问量
    private Integer viewCount;
}
