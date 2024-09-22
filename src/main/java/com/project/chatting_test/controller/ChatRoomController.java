package com.project.chatting_test.controller;

import com.project.chatting_test.dto.ChatRoomDto;
import com.project.chatting_test.entity.ChatMessageEntity;
import com.project.chatting_test.service.ChatRoomService;
import com.project.chatting_test.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    @PostMapping("/room")
    public ChatRoomDto createRoom(@RequestParam String name) {
        return chatRoomService.createRoom(name);
    }

    @GetMapping("/rooms")
    public List<ChatRoomDto> findAllRooms() {
        return chatRoomService.findAllRooms();
    }

    @GetMapping("/room/{roomId}")
    public ChatRoomDto findRoomById(@PathVariable String roomId) {
        return chatRoomService.findRoomById(roomId);
    }
}
