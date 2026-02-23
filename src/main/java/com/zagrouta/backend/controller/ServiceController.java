package com.zagrouta.backend.controller;

import com.zagrouta.backend.entity.ServiceEntity;
import com.zagrouta.backend.service.ServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // دي بتسمح لأي فرونت إند إنه يكلم الباك إند بتاعنا
@RestController
@RequestMapping("/api/services") // أي رابط هيبدأ بـ /api/services هيجي هنا
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    // 1. Endpoint لإضافة خدمة جديدة لمستخدم معين (Vendor)
    // الرابط: http://localhost:8080/api/services/add/{userId}
    // مثال: /api/services/add/1 (عشان نضيف خدمة لليوزر رقم 1)
    @PostMapping("/add/{userId}")
    public ServiceEntity addService(@RequestBody ServiceEntity service,
            @PathVariable @org.springframework.lang.NonNull Long userId) {
        return serviceService.addService(service, userId);
    }

    // 2. Endpoint تجيب كل الخدمات الموجودة في الموقع (لصفحة الـ Home)
    // الرابط: http://localhost:8080/api/services/all
    @GetMapping("/all")
    public List<ServiceEntity> getAllServices() {
        return serviceService.getAllServices();
    }

    // 3. Endpoint تجيب خدمات يوزر معين (عشان بروفايل الفيندور)
    // الرابط: http://localhost:8080/api/services/user/{userId}
    @GetMapping("/user/{userId}")
    public List<ServiceEntity> getServicesByVendor(@PathVariable Long userId) {
        return serviceService.getServicesByVendor(userId);
    }
}