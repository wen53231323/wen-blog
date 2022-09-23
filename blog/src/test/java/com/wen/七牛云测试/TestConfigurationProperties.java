package com.wen.七牛云测试;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
@Getter
@Setter
@SpringBootTest
// @ConfigurationProperties注解：Springboot提供读取配置文件的一个注解，需提供其setter和getter方法
@ConfigurationProperties(prefix = "test")
public class TestConfigurationProperties {

    private String test1;
    private String test2;

    @Test
    public void test(){
        System.out.println(test1);
        System.out.println(test2);
    }

}
