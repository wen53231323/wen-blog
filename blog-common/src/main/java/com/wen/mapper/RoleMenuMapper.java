package com.wen.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 角色权限关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-09 15:56:16
 */
@Mapper
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}

