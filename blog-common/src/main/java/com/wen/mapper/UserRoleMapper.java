package com.wen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.entity.UserRole;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;



/**
 * 用户角色关联表(UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-09 01:20:25
 */
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

