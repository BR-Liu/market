package com.brliu.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * @author br_liu
 * 切面表达式的含义：
 * 1. 第一个*表示匹配所有返回类型
 * 2. com.brliu.service.impl 匹配的包引用
 * 3. .表示该包及其全部子包
 * 4. .*表示包下面全部类
 * 5. .*(..)表示类下面全部方法，这些方法的参数可以是任意类型
 */
@Slf4j
@Aspect
@Component
public class ServiceTimeRecordAspect {

    @Around("execution(* com.brliu.service.impl..*.*(..))")
    public void recordServiceExecuteTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(
                "------------ 服务:{}.{}开始执行 ------------",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName()
        );
        long beginTime = System.currentTimeMillis();
        joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("------------ 当前服务执行结束，执行耗费:{}毫秒 ------------", endTime - beginTime);
    }
}
