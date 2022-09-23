package com.wen.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Comment)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-06 16:02:57
 */
@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 向评论表插入，文章id和评论内容
     */
    @Insert("insert into comment( belong_id, content, create_by, update_by ) values( #{articleId}, #{content}, #{createBy}, #{updateBy} )")
    int commentInsert(@Param("articleId") Long articleId, @Param("content") String content, @Param("createBy") Long createBy, @Param("updateBy") Long updateBy);
}

