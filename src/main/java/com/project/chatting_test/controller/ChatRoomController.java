package com.project.chatting_test.controller;

import com.project.chatting_test.dto.ChatRoomDto;
import com.project.chatting_test.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

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
