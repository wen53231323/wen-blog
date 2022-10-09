package com.wen.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-30 17:12:47
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    // 查询用户账号是否存在（返回值大于0存在）
    @Select("select 1 from user where account = #{account} limit 1")
    Object userAccountExist(@Param("account") String account);

    // 查询用户手机号是否存在（返回值大于0存在）
    @Select("select 1 from user where phone = #{phone} limit 1")
    Object userPhoneExist(@Param("phone") String phone);

    // 查询用户邮箱是否存在（返回值大于0存在）
    @Select("select 1 from user where email = #{email} limit 1")
    Object userEmailExist(@Param("email") String email);
}

