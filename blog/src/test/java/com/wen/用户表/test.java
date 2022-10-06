package com.wen.用户表;

import com.wen.SpringbootApplication;
import com.wen.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SpringbootApplication.class )
public class test {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        userMapper.userAccountExist("wen");
        userMapper.userEmailExist("123@qq.com");
    }
}
