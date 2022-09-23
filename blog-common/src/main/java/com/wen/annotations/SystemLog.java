package com.wen.annotations;

import java.lang.annotation.*;

/**
 * 自定义注解，用于打印日志
 * */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
// TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE
@Target(ElementType.METHOD)
public @interface SystemLog {
    // 指定业务名
    String businessName();
}
