package com.project.chatting_test.service;

import com.project.chatting_test.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final SimpMessagingTemplate messagingTemplate;

    // 메시지 처리 및 발송
    public void sendMessage(ChatMessageDto chatMessage) {
        // 메시지 타입이 입장일 경우, 입장 메시지로 변경
        if (chatMessage.messageType() == ChatMessageDto.MessageType.ENTER) {
            chatMessage = chatMessage.withMessage(chatMessage.sender() + "님이 입장하셨습니다.");
        }

        // 발행 경로를 설정하고, 로그로 경로와 메시지 내용을 출력
        String destination = "/exchange/amq.topic/chat/" + chatMessage.roomId();
        System.out.println("Sending message to: " + destination);  // 경로 출력
        System.out.println("Message content: " + chatMessage.message());  // 메시지 내용 출력

        // 메시지 발행
        messagingTemplate.convertAndSend(destination, chatMessage);
    }
}

