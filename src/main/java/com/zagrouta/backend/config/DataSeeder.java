package com.zagrouta.backend.config;

import com.zagrouta.backend.entity.ServiceEntity;
import com.zagrouta.backend.entity.User;
import com.zagrouta.backend.repository.ServiceRepository;
import com.zagrouta.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    public DataSeeder(ServiceRepository serviceRepository, UserRepository userRepository) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Find or create "Yassin hesham" vendor
        User vendor = userRepository.findByEmail("Yassin@test.com").orElseGet(() -> {
            User newVendor = new User();
            newVendor.setFullName("Yassin hesham");
            newVendor.setEmail("Yassin@test.com");
            newVendor.setPassword("12345678");
            newVendor.setRole("VENDOR");
            return userRepository.save(newVendor);
        });

        if (serviceRepository.count() < 10) {
            // Insert dummy data
            List<ServiceEntity> dummyServices = Arrays.asList(
                new ServiceEntity(null, "قاعة الماسة", "قاعة أفراح فخمة", "25,000 ج.م", "venue", "مدينة نصر، القاهرة", "https://images.unsplash.com/photo-1519167758481-83f550bb49b3?auto=format&fit=crop&w=500&q=60", vendor),
                new ServiceEntity(null, "فستان سندريلا", "فستان زفاف رائع", "5,000 ج.م", "dress", "التجمع الخامس", "https://s.alicdn.com/@sc04/kf/Hb2a67e5d2fc74020b6f41f6cfa79265fT/Mumuleo-Pink-15-Party-Sexy-Red-Ball-Gown-Quinceanera-Dresses-3D-Bow-Design-Tulle-Formal-Cinderella-Birthday.jpg", vendor),
                new ServiceEntity(null, "سارة ميك أب", "ميك أب آرتيست محترفة", "3,500 ج.م", "makeup", "المهندسين", "https://images.unsplash.com/photo-1487412720507-e7ab37603c6f?auto=format&fit=crop&w=500&q=60", vendor),
                new ServiceEntity(null, "قاعة فيرونا", "قاعة مناسبات مميزة", "15,000 ج.م", "venue", "المعادي، القاهرة", "https://images.unsplash.com/photo-1519741497674-611481863552?auto=format&fit=crop&w=500&q=60", vendor),
                new ServiceEntity(null, "أتيليه ليلة العمر", "شراء وإيجار الفساتين", "12,000 ج.م", "dress", "مصر الجديدة", "/atelie_dress.png", vendor),
                new ServiceEntity(null, "نورا فوتوغرافي", "جلسات تصوير احترافية", "4,000 ج.م", "photography", "مدينة نصر", "https://images.unsplash.com/photo-1537633552985-df8429e8048b?auto=format&fit=crop&w=500&q=60", vendor),
                new ServiceEntity(null, "فستان زفاف ملكي", "تصميم ملكي فاخر", "7,000 ج.م", "dress", "مصر الجديدة", "/dress1.jpg", vendor),
                new ServiceEntity(null, "فستان سندريلا تركي", "مستورد من تركيا", "5,000 ج.م", "dress", "التجمع الخامس", "/dress2.jpg", vendor),
                new ServiceEntity(null, "فستان سمبل أبيض", "تصميم هادئ وبسيط", "15,000 ج.م", "dress", "المهندسين", "/dress3.jpg", vendor),
                new ServiceEntity(null, "قاعة أوبرا هاوس", "من أرقى القاعات", "35,000 ج.م", "venue", "الزمالك", "https://images.unsplash.com/photo-1464013778555-8e723c2f01f8?auto=format&fit=crop&w=500&q=60", vendor),
                new ServiceEntity(null, "مروة عادل ميك أب", "أحدث صيحات الميك أب", "4,500 ج.م", "makeup", "مدينة نصر", "https://images.unsplash.com/photo-1596462502278-27bfdc403348?auto=format&fit=crop&w=500&q=60", vendor),
                new ServiceEntity(null, "أحمد فوتوغرافي", "تصوير فيديو وفوتو", "3,000 ج.م", "photography", "المهندسين", "https://images.unsplash.com/photo-1520854221256-17451cc331bf?auto=format&fit=crop&w=500&q=60", vendor)
            );
            serviceRepository.saveAll(dummyServices);
            System.out.println("Dummy Services Seeded Successfully for vendor: Yassin hesham");
        }
    }
}
