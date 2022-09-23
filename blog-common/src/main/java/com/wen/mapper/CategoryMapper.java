package com.wen.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.entity.Category;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Category)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-28 12:44:29
 */
@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}

