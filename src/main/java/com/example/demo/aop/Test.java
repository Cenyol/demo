package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author Chenhanqun mail: chenhanqun1@corp.netease.com
 * @date 2018/12/27 09:59
 */
@Slf4j
@Aspect
@Configuration
public class Test {
    ThreadLocal<String> operatorUid = new ThreadLocal<>();
    ThreadLocal<String> operatorName = new ThreadLocal<>();

    @AfterReturning(value = "execution(* com.example.demo.service.AOPService.groupUpdate*(..)) && args(obj)",
//    @AfterReturning(value = "execution(* com.example.demo.service.AOPService.groupUpdate*(..)) && args(obj, obj2)",
        returning = "returnValue")
    public void afterGroupUpdate(JoinPoint joinPoint, Object obj, Object returnValue) {
        log.info("afterGroupUpdate thread id: {}", Thread.currentThread().getId());

        if (obj != null) {
            log.info("Obj class {}.", obj.getClass());

            if (obj instanceof Double)
                log.info("Double Object obj {}", obj);
            if (obj instanceof Integer)
                log.info("Integer Object obj {}", obj);
            if (obj instanceof Collection)
                log.info("Collection Object obj {}", obj);
        }
        if (returnValue != null) {
            log.info("returnValue {}", returnValue);
        }
        log.info("after execution of {}", joinPoint);
        if (!StringUtils.isEmpty(operatorUid.get())) {
            log.info("operatorUid {}", operatorUid.get());
        }
    }

    @Before(value = "execution(* com.example.demo.controller.*.*(..))")
    public void beforeRequestHandle(JoinPoint joinPoint) {
        log.info("beforeRequestHandle thread id: {}", Thread.currentThread().getId());

        HttpServletRequest request = null;
        Object[] signatureArgs = joinPoint.getArgs();
        for (Object signatureArg: signatureArgs) {
            System.out.println("Arg class: " + signatureArg.getClass());
            System.out.println("Arg: " + signatureArg);

            if (signatureArg instanceof HttpServletRequest) {
                request = (HttpServletRequest) signatureArg;
                log.info("before request handle of {}, args: {}", joinPoint, request.getRequestURL());
                operatorUid.set(request.getRequestURL().toString());
                operatorName.set(request.getRequestedSessionId());
            }
        }
    }
}
