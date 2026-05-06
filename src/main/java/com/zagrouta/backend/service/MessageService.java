package com.zagrouta.backend.service;

import com.zagrouta.backend.entity.Message;
import com.zagrouta.backend.entity.User;
import com.zagrouta.backend.repository.MessageRepository;
import com.zagrouta.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    // ===== إرسال رسالة =====
    public Message sendMessage(Long senderId, Long receiverId, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());
        message.setRead(false);

        return messageRepository.save(message);
    }

    // ===== المحادثة بين مستخدمَين =====
    @Transactional
    public List<Message> getConversation(Long userA, Long userB) {
        // نحدد الرسائل اللي بعتها الـ userB للـ userA كـ "مقروءة"
        List<Message> unread = messageRepository.findUnreadMessages(userB, userA);
        unread.forEach(m -> m.setRead(true));
        messageRepository.saveAll(unread);

        return messageRepository.findConversation(userA, userB);
    }

    // ===== قائمة المحادثات لمستخدم معين =====
    public List<Map<String, Object>> getConversationsList(Long userId) {
        List<Message> latestMessages = messageRepository.findLatestMessagesForUser(userId);

        return latestMessages.stream().map(msg -> {
            // الشخص التاني في المحادثة (مش أنا)
            User otherUser = msg.getSender().getId().equals(userId)
                    ? msg.getReceiver()
                    : msg.getSender();

            // حساب عدد الرسائل غير المقروءة من هذا الشخص
            List<Message> unreadFromOther = messageRepository.findUnreadMessages(otherUser.getId(), userId);
            long unreadCount = unreadFromOther.size();

            Map<String, Object> conv = new LinkedHashMap<>();
            conv.put("userId", otherUser.getId());
            conv.put("name", otherUser.getFullName());
            conv.put("role", otherUser.getRole());
            conv.put("lastMessage", msg.getContent());
            conv.put("sentAt", msg.getSentAt());
            conv.put("unreadCount", unreadCount);
            conv.put("lastSenderId", msg.getSender().getId());
            return conv;
        }).collect(Collectors.toList());
    }

    // ===== إجمالي الرسائل غير المقروءة =====
    public long getUnreadCount(Long userId) {
        return messageRepository.countByReceiverIdAndReadFalse(userId);
    }
}
