package com.wen.pojo.vo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
public class ReplyVo {
    //回复id
    private Long id;
    //回复的父评论id（针对哪条评论的回复）
    private Long commentId;
    //回复的目标id（给哪个用户回复）
    private Long toUserId;
    //回复内容
    private String content;
    //回复时间
    private Date createTime;
    //回复者id（谁回复的）
    private Long createBy;

    // 回复者昵称（谁回复的）
    private String nickName;
    // 回复的目标昵称（给哪个用户回复）
    private String parentNickName;
}
