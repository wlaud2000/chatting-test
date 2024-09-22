package com.project.chatting_test.service;

import com.project.chatting_test.dto.ChatMessageDto;
import com.project.chatting_test.entity.ChatMessageEntity;
import com.project.chatting_test.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;

    // 메시지 처리 및 발송
    public void sendMessage(ChatMessageDto chatMessage) {
        // 메시지 타입이 입장일 경우, 입장 메시지로 변경
        if (chatMessage.messageType() == ChatMessageDto.MessageType.ENTER) {
            chatMessage = chatMessage.withMessage(chatMessage.sender() + "님이 입장하셨습니다.");
        }

        // 몽고DB에 메시지 저장
        ChatMessageEntity messageEntity = ChatMessageEntity.builder()
                .roomId(chatMessage.roomId())
                .sender(chatMessage.sender())
                .message(chatMessage.message())
                .messageType(chatMessage.messageType())
                .timestamp(System.currentTimeMillis())  // 현재 시간으로 저장
                .build();

        chatMessageRepository.save(messageEntity);


        // 메시지 발행
        String destination = "/exchange/amq.topic/chat/"+chatMessage.roomId();
        System.out.println("Sending message to: " + destination);  // 경로 출력
        System.out.println("Message content: " + chatMessage.message());  // 메시지 내용 출력
        messagingTemplate.convertAndSend(destination, chatMessage);
    }

    // 채팅방의 모든 메시지 불러오기
    public List<ChatMessageDto> getMessagesByRoomId(String roomId) {
        return chatMessageRepository.findByRoomId(roomId)
                .stream()
                .map(ChatMessageEntity::from)  // Entity -> DTO 변환
                .collect(Collectors.toList());
    }
}

