package com.wen.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (UserFollow)表实体类
 *
 * @author makejava
 * @since 2022-10-07 10:39:17
 */
// @SuppressWarnings注解是jse提供的注解，屏蔽无关紧要的警告。
@SuppressWarnings("serial")
// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
//MyBatis-Puls提供的注解，设置实体类对应的表名
@TableName("user_follow")
public class UserFollow {
    //主键ID    
    private Long id;
    //用户ID    
    private Long userIdA;
    //被关注ID    
    private Long userIdB;
    //标记（1被关注、2拉黑）
    private String followedSign;
    //创建时间    
    private Date createTime;
}

