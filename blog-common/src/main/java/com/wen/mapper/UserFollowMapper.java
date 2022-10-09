package com.wen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.entity.UserFollow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;


/**
 * (UserFollow)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-07 10:39:15
 */
@Mapper
@Repository
public interface UserFollowMapper extends BaseMapper<UserFollow> {

    // 查找双方关系
    @Select("select 1 from user_follow where user_id_a=#{userIdA} and user_id_b=#{userIdB} and followed_sign=#{followedSign}")
    Object selectFollows(@Param("userIdA") Long userIdA, @Param("userIdB") Long userIdB, @Param("followedSign") String followedSign);

    // 添加关注关系
    @Update("insert into user_follow(user_id_a,user_id_b,followed_sign) values (#{userIdA}, #{userIdB}, #{followedSign} )")
    void insertUserFocus(@Param("userIdA") Long userIdA, @Param("userIdB") Long userIdB, @Param("followedSign") String followedSign);

    // 删除关注关系
    @Delete("delete from user_follow where user_id_a=#{userIdA} and user_id_b=#{userIdB} and followed_sign=#{followedSign}")
    void deleteUserFocus(@Param("userIdA") Long userIdA, @Param("userIdB") Long userIdB, @Param("followedSign") String followedSign);

}

