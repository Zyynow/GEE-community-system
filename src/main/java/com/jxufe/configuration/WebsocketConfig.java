package com.jxufe.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @version v1.0
 * @ClassName: WebsocketConfig
 * @Description: 由该配置类扫描添加有@ServerEndpoint注解的Bean对象
 * @Author: Kafka
 */
@Configuration
public class WebsocketConfig {

    @Bean // 将方法返回值Bean对象注入到容器中交由Spring管理
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
