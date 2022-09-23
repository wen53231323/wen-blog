package com.wen.aspect;

import com.alibaba.fastjson.JSON;
import com.wen.annotations.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 切面类，对需要打印日志的接口做增强
 */
@Slf4j
// @Order(数值)：通过value属性设置优先级，值越小，优先级越高，默认值为Integer的最大值
@Order(1)
// @Aspect注解：标识切面类
@Aspect
// @Component注解，标识普通组件，保证这个切面类能够放入IOC容器
@Component
public class LogAspect {

    /**
     * 可重用的切点
     */
    // @Pointcut注解：声明可重用的切入点表达式
    // @annotation内定义自定义注解使用的位置
    @Pointcut("@annotation(com.wen.annotations.SystemLog)")
    public void pt() {

    }

    // @Around：环绕通知，使用try...catch...finally结构围绕整个被代理的目标方法，包括上面四种通知对应的所有位置
    // 使用可重用的切入点表达式pt()
    @Around("pt()")
    public Object printLot(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed;
        try {
            log.info("==============================Start==============================");
            // 前置通知
            handBefore(joinPoint);
            proceed = joinPoint.proceed();

            // 返回通知
            handAfterReturning(proceed);

        } finally {
            // 后置通知，System.lineSeparator()表示拼接换行
            log.info("==============================End==============================" + System.lineSeparator());
        }
        return proceed;
    }

    public void handBefore(ProceedingJoinPoint joinPoint) {
        // 获取请求url
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        // 打印请求url
        log.info("URL                    : {}", request.getRequestURI());
        // 打印描述信息（指定业务名）
        log.info("BusinessName           : {}", systemLog.businessName());
        // 打印请求方法Http method
        log.info("Http Method            : {}", request.getMethod());
        // 打印调用 Controller 的全路径以及执行方法
        log.info("Class Method           : {},{}", joinPoint.getSignature().getDeclaringTypeName(), ((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的IP
        log.info("IP                     : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Resquest Args          : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }

    public void handAfterReturning(Object proceed) {
        // 打印出参
        log.info("Response               : {}",JSON.toJSONString(proceed));

    }

}
