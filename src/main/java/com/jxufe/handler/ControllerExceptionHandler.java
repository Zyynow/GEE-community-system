package com.jxufe.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;

/**
 * @ControllerAdvice会拦截所有被@Controller标注的控制器
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 拿到这个类的日志对象
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @ExceptionHandler表示这个方法可以做异常处理
     * @param req
     * @param e
     * @return 数据信息与视图
     */
    @ExceptionHandler(Exception.class) //表示所有属于Exception的异常会被方法做异常处理
    public ModelAndView printException(HttpServletRequest req, Exception e) throws Exception {
        //拿到异常信息
        logger.error("Request URL : {}, Exception : {}", req.getRequestURL(), e.getMessage());

        //逻辑处理：判断是否是想拦截的异常
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        //创建对象
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", req.getRequestURL());
        mv.addObject("exception", e);
        //返回error.html页面
        mv.setViewName("error/error");
        return mv;
    }
}
