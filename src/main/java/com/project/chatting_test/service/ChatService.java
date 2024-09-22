package com.project.chatting_test.service;

import com.project.chatting_test.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final SimpMessagingTemplate messagingTemplate;

    // 메시지 처리 및 발송
    public void sendMessage(ChatMessageDto chatMessage) {
        if (chatMessage.messageType() == ChatMessageDto.MessageType.ENTER) {
            chatMessage = chatMessage.withMessage(chatMessage.sender() + "님이 입장하셨습니다.");
        }
        // /topic/chat/roomId 경로로 메시지를 발행
        messagingTemplate.convertAndSend("/topic/chat/" + chatMessage.roomId(), chatMessage);
    }
}
