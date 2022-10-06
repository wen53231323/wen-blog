package com.wen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Tag)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-06 15:49:23
 */
@Mapper
@Repository
public interface TagMapper extends BaseMapper<Tag> {

}

