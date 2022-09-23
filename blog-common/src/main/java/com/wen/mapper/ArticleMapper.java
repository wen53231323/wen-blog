package com.wen.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    // 根据文章id更新文章浏览量
    @Update("update artacle set view_count = #{ ViewCount } where id = #{ id }")
    void updateViewCountById(@Param("id") Long id, @Param("ViewCount") Integer ViewCount);
}
