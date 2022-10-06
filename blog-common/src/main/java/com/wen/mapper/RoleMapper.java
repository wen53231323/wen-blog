package com.wen.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-03 23:37:51
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    // 查询用户所具有的角色权限信息
    List<String> selectRoleKeyByUserId(Long userId);
}

