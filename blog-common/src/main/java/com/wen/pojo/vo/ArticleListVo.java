package com.wen.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {
    // 文章id
    private Long id;
    // 文章标题
    private String title;
    // 文章摘要
    private String summary;
    // 文章所属分类名
    private String categoryName;
    // 文章缩略图
    private String thumbnail;
    // 文章创建时间
    private Date createTime;
}
