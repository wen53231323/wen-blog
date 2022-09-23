package com.wen.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.entity.Reply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Reply)评论回复表数据库访问层
 *
 * @author makejava
 * @since 2022-09-06 19:46:28
 */
@Mapper
@Repository
public interface ReplyMapper extends BaseMapper<Reply> {

    /**
     * 向评论回复表插入，一级评论id、回复的目标id（给哪个用户回复）、回复内容
     */
    @Insert("insert into reply( comment_id, to_user_id, content, create_by, update_by ) values( #{commentId}, #{toUserId}, #{content}, #{createBy}, #{updateBy})")
    int replyInsert(@Param("commentId") Long commentId, @Param("toUserId") Long toUserId, @Param("content") String content, @Param("createBy") Long createBy, @Param("updateBy") Long updateBy);

}

