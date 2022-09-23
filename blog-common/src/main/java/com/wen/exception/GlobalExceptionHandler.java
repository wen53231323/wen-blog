package com.wen.exception;


import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理，出现的异常只要在其中声明即可捕获
 * */

// @ControllerAdvice注解：将类标识为异常处理组件
// @ResponseBody注解：将java对象直接转换为json字符串并响应到浏览器
// @RestControllerAdvice = @ControllerAdvice + @ResponseBody
@RestControllerAdvice
// 由于自己开启日志需要写上：　private  final Logger logger = LoggerFactory.getLogger(XXX.class);
// 为了方便起见就可以使用注解@Slf4j来直接使用log对象，简化了一行代码（lombok提供）
@Slf4j
public class GlobalExceptionHandler {
    // @ExceptionHandler注解：拦截所有的异常信息
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        // 打印异常信息
        log.error("出现了异常！ {}",e);
        // 从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    // @ExceptionHandler注解：拦截所有的异常信息
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        // 打印异常信息
        log.error("出现了异常！ {}",e);
        // 从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
