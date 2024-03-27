package com.jxufe.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*
 * @RestController 注解相当于 @ResponseBody + @Controller 合在一起的作用
 * 若使用@Controller注解，在对应的方法上，视图解析器可以解析return的jsp,html页面，并且跳转到相应页面
        若返回json等内容到页面，则需要加@ResponseBody注解
        在Restful的服务中，我们大部分情况是使用JSON为返回数据的，所以可以直接使用@Controller注解
 *
 * @RequestMapping注解是用来映射请求的，即指明处理器可以处理哪些URL请求，该注解既可以用在类上，也可以用在方法上
 * 当使用@RequestMapping标记控制器类时，方法的请求地址是相对类的请求地址而言的；当没有使用@RequestMapping标记类时，方法的请求地址是绝对路径
 * @ResponseBody注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区
 */
@Controller
@RequestMapping("chat")
public class ChatController {

    @GetMapping("/record")
    public String record(Model model) {
        // 读取最近记录
        return "friends_record";
    }

}
