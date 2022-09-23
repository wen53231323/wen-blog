package com.wen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
public class CommentVo {
    //评论id
    private Long id;
    //评论所属id（给谁评论的）
    private Long belongId;
    //评论内容
    private String content;
    //评论发表者id（谁评论的）
    private Long createBy;
    //评论发表时间
    private Date createTime;
    //评论点赞数
    private Integer likeNum;

    // 评论发表者昵称
    private String nickName;
    // 子评论列表
    private List<ReplyVo> reply;
}
