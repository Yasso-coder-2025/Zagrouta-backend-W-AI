package com.zagrouta.backend.controller;

import com.zagrouta.backend.entity.User;
import com.zagrouta.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE }) // دي بتسمح لأي فرونت إند إنه يكلم الباك إند بتاعنا
@RestController // بنعرف سبرينج إن ده كلاس بيستقبل طلبات ويب
@RequestMapping("/api/users") // أي رابط هيبدأ بـ /api/users هيجي هنا
public class UserController {

    private final UserService userService;

    // بنحقن الـ Service عشان نستخدم دوالها
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. Endpoint لتسجيل مستخدم جديد (POST Request)
    // الرابط هيكون: http://localhost:8080/api/users/register
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        // Validation for Egyptian phone numbers
        if (user.getPhone() == null || !user.getPhone().matches("^\\+201[0125][0-9]{8}$")) {
            return "Error: Invalid Egyptian phone number!";
        }

        // نتأكد الأول إن الإيميل مش مستخدم قبل كده
        if (userService.isEmailTaken(user.getEmail())) {
            return "Error: Email is already taken!";
        }

        userService.saveUser(user);
        return "User registered successfully!";
    }

    // 2. Endpoint تجيب بيانات يوزر معين بالإيميل (GET Request)
    // الرابط هيكون: http://localhost:8080/api/users/{email}
    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email).orElse(null);
    }

    // 3. Endpoint تجيب كل اليوزرز (للتجربة بس) (GET Request)
    // الرابط هيكون: http://localhost:8080/api/users/all
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 4. Endpoint لتسجيل الدخول
    // الرابط: http://localhost:8080/api/users/login
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());

        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Email or Password!");
        }
    }

    // 5. Endpoint لتحديث كلمة المرور
    // الرابط: http://localhost:8080/api/users/update-password
    @PutMapping("/update-password")
    public String updatePassword(@RequestBody User user) {
        if (!userService.isEmailTaken(user.getEmail())) {
            return "Error: Email not found!";
        }

        boolean isUpdated = userService.updatePassword(user.getEmail(), user.getPassword());
        if (isUpdated) {
            return "Password updated successfully!";
        } else {
            return "Error: Could not update password.";
        }
    }

    // 6. Endpoint لتحديث بيانات الحساب
    // الرابط: http://localhost:8080/api/users/update
    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody User user) {
        // Validation for Egyptian phone numbers
        if (user.getPhone() == null || !user.getPhone().matches("^\\+201[0125][0-9]{8}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid Egyptian phone number!");
        }

        // Here, the user object contains email, password (current), fullName, phone,
        // and gender
        boolean isUpdated = userService.updateUserProfile(
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                user.getPhone(),
                user.getGender());
        if (isUpdated) {
            return ResponseEntity.ok("Profile updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid password or user not found.");
        }
    }
}