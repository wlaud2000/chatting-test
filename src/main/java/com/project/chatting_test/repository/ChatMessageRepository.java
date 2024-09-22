package com.project.chatting_test.repository;

import com.project.chatting_test.entity.ChatMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessageEntity, String> {
    List<ChatMessageEntity> findByRoomId(String roomId);
}
