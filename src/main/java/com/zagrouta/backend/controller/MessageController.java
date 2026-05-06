package com.zagrouta.backend.controller;

import com.zagrouta.backend.entity.Message;
import com.zagrouta.backend.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // ===== إرسال رسالة =====
    // POST /api/messages/send
    // Body: { "senderId": 1, "receiverId": 2, "content": "نص الرسالة" }
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Map<String, Object> body) {
        Long senderId = Long.valueOf(body.get("senderId").toString());
        Long receiverId = Long.valueOf(body.get("receiverId").toString());
        String content = body.get("content").toString();
        Message msg = messageService.sendMessage(senderId, receiverId, content);
        return ResponseEntity.ok(msg);
    }

    // ===== جيب المحادثة بين مستخدمَين =====
    // GET /api/messages/conversation?userA=1&userB=2
    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(
            @RequestParam Long userA,
            @RequestParam Long userB) {
        return ResponseEntity.ok(messageService.getConversation(userA, userB));
    }

    // ===== قائمة كل المحادثات لمستخدم =====
    // GET /api/messages/conversations/1
    @GetMapping("/conversations/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getConversations(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getConversationsList(userId));
    }

    // ===== عدد الرسائل غير المقروءة =====
    // GET /api/messages/unread-count/1
    @GetMapping("/unread-count/{userId}")
    public ResponseEntity<Map<String, Long>> getUnreadCount(@PathVariable Long userId) {
        long count = messageService.getUnreadCount(userId);
        return ResponseEntity.ok(Map.of("unreadCount", count));
    }
}
