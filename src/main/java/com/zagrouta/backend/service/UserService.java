package com.zagrouta.backend.service;

import com.zagrouta.backend.entity.User;
import com.zagrouta.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // بنعمل Injection للـ Repository عشان نقدر نستخدمه هنا
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. دالة لتسجيل مستخدم جديد (Create User)
    public User saveUser(@org.springframework.lang.NonNull User user) {
        // (مستقبلاً هنا هنحط كود تشفير الباسورد قبل الحفظ)
        return userRepository.save(user);
    }

    // 2. دالة لجلب مستخدم بالإيميل
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // إضافة جديدة لتحديث كلمة المرور
    public boolean updatePassword(String email, String newPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // (مستقبلاً هنا هنحط كود تشفير الباسورد قبل الحفظ)
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // إضافة جديدة لتحديث بيانات المتسخدم (الاسم ورقم الهاتف والنوع) بتأكيد كلمة
    // المرور
    public boolean updateUserProfile(String email, String password, String newFullName, String newPhone,
            String newGender) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // نأكد من أن كلمة المرور صحيحة قبل التعديل
            if (user.getPassword().equals(password)) {
                user.setFullName(newFullName);
                user.setPhone(newPhone);
                user.setGender(newGender);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    // 3. دالة للتحقق هل الإيميل موجود ولا لأ
    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    // 4. دالة تجيب كل المستخدمين (عشان نختبر بيها بس)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 5. دالة تسجيل الدخول (Login)
    public User loginUser(String email, String password) {
        // ندور على اليوزر بالإيميل
        Optional<User> optionalUser = userRepository.findByEmail(email);

        // لو اليوزر موجود، نتأكد من الباسورد
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // مقارنة الباسورد (مؤقتاً بنقارن نص عادي لحد ما نضيف التشفير)
            if (user.getPassword().equals(password)) {
                return user; // البيانات صحيحة، رجع اليوزر
            }
        }
        return null; // الإيميل مش موجود أو الباسورد غلط
    }
}
