package com.jxufe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 自动扫描BlogAppliaction所在包下的所有类，检查是否有符合条件的标注
 */
@SpringBootApplication
public class GuideSystemApplication {

    public static void main(String[] args) {
        /* SpringBoot自带MD5加密
            String result = DigestUtils.md5DigestAsHex("hyfhsy".getBytes());
            System.out.println(result);
        */
        SpringApplication.run(GuideSystemApplication.class, args);
    }

}
