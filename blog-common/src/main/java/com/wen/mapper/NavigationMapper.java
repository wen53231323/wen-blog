package com.wen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.entity.Navigation;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Navigation)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-30 12:06:36
 */
@Mapper
@Repository
public interface NavigationMapper extends BaseMapper<Navigation> {

}

