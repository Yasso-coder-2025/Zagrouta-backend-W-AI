package com.zagrouta.backend.repository;

import com.zagrouta.backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // كل الرسائل بين مستخدمَين (الاتجاهين)
    @Query("SELECT m FROM Message m WHERE " +
           "(m.sender.id = :userA AND m.receiver.id = :userB) OR " +
           "(m.sender.id = :userB AND m.receiver.id = :userA) " +
           "ORDER BY m.sentAt ASC")
    List<Message> findConversation(@Param("userA") Long userA, @Param("userB") Long userB);

    // آخر رسالة مع كل شخص تكلمه الـ vendor — لعمل قائمة المحادثات
    @Query("SELECT m FROM Message m WHERE m.id IN (" +
           "  SELECT MAX(m2.id) FROM Message m2 " +
           "  WHERE m2.sender.id = :vendorId OR m2.receiver.id = :vendorId " +
           "  GROUP BY CASE WHEN m2.sender.id = :vendorId THEN m2.receiver.id ELSE m2.sender.id END" +
           ") ORDER BY m.sentAt DESC")
    List<Message> findLatestMessagesForUser(@Param("vendorId") Long vendorId);

    // عدد الرسائل غير المقروءة
    long countByReceiverIdAndReadFalse(Long receiverId);

    // تحديد رسالة كـ "تمت قراءتها"
    @Query("SELECT m FROM Message m WHERE " +
           "m.sender.id = :senderId AND m.receiver.id = :receiverId AND m.read = false")
    List<Message> findUnreadMessages(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);
}
