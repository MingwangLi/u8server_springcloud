package com.jmsht.u8server.admin.component;

import com.jmsht.u8server.admin.annotation.LogAnnotaion;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class LogAnnotationAnalytic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @After("execution(* com.jmsht.u8server..*.*(..)) && @annotation(logAnnotaion)")
    public void logRecord(JoinPoint joinPoint,LogAnnotaion logAnnotaion) throws Exception{
        String targetClassName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String params = "";
        Object[] args = joinPoint.getArgs();
        Class targetClass = Class.forName(targetClassName);
        Method[] methods = targetClass.getMethods();
        for (Method method:methods) {
            if (methodName.equals(method.getName())) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == args.length) {
                    if (!method.isAnnotationPresent(LogAnnotaion.class)) {
                        return;
                    }
                    for (int i = 0; i < args.length; i++) {
                        params = params + args[i] + (i == args.length - 1 ? "" : ",");
                    }
                    break;
                }
            }
        }
       HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        logger.info("----请求方法:{}",targetClassName+"."+methodName+"()");
        logger.info("----请求参数:{}",params);
        logger.info("----请求IP:{}",ip);
    }
}
