package com.wen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.entity.ArticleTag;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;



/**
 * (ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-06 16:59:16
 */
@Mapper
@Repository
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}

