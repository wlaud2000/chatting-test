package com.project.chatting_test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.host}")
    private String relayHost;

    @Value("${spring.rabbitmq.port}")
    private int relayPort;

    @Value("${spring.rabbitmq.username}")
    private String clientLogin;

    @Value("${spring.rabbitmq.password}")
    private String clientPasscode;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableStompBrokerRelay("/topic", "/queue")  // RabbitMQ를 브로커로 사용
                .setRelayHost(relayHost)
                .setRelayPort(relayPort)
                .setClientLogin(clientLogin)
                .setClientPasscode(clientPasscode);
        config.setApplicationDestinationPrefixes("/app");  // 클라이언트가 메시지를 보낼 때 경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                .setAllowedOrigins("*") // 실제 운영 환경에서는 특정 도메인만 허용
                .withSockJS();  // STOMP 엔드포인트 등록
    }
}
