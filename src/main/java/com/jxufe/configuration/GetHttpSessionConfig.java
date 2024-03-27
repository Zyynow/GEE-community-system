package com.jxufe.configuration;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @version v1.0
 * @ClassName: GetHttpSessionConfig
 * @Description: TODO(一句话描述该类的功能)
 * @Author: Kafka
 */
public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {

    // 即修改握手操作
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        // 通过请求获取HttpSession对象
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        // 将httpSession对象存储在ServerEndpointConfig的userProperties对象中，这样就可以通过这个Map集合拿到httpSession对象
        sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
    }
}
