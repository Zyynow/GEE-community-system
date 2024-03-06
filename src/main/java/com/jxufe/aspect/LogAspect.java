package com.jxufe.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Aspect 表示当前类是切面类
 * 切面类：是用来给业务方法增加功能的类，在这个类中有实现切面的方法
 * 切面：表示增加的功能(理解成 -> 一切变两面 -> 功能变广)
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * execution("...")
     * 切入点表达式：
     *  execution(访问权限(可省) 返回值类型 方法声明 异常类型(可省))
     *  *表示通配符(所有)
     *  ..表示子包或者父包
     *  +表示子类或者父类
     */
    @Pointcut(value = "execution(* com.jxufe.web.*.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint join) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = join.getSignature().getDeclaringTypeName() + "." + join.getSignature().getName();
        Object[] args = join.getArgs();
        RequestLog log = new RequestLog(url, ip, classMethod, args);
        logger.info("RequestLog : {}", log);
    }

    //@After也叫做最终执行
    @After("log()")
    public void doAfter() {
        logger.info("------------doAfter-----------");
    }

    /**
     * @AfterReturning：后置通知注解，目标方法之后执行
     * @param obj
     */
    @AfterReturning(returning = "obj", pointcut = "log()")
    public void doAfterReturn(Object obj) {
        logger.info("method : {}", obj);
    }

    /**
     * 内部类
     * 用于获取请求的相应日志
     */
    public class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
