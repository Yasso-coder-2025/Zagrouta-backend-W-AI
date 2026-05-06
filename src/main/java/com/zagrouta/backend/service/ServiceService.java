package com.zagrouta.backend.service;

import com.zagrouta.backend.entity.ServiceEntity;
import com.zagrouta.backend.entity.User;
import com.zagrouta.backend.repository.ServiceRepository;
import com.zagrouta.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    public ServiceService(ServiceRepository serviceRepository, UserRepository userRepository) {
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }

    public ServiceEntity addService(ServiceEntity service, @org.springframework.lang.NonNull Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            service.setUser(user);
            // الحفظ وإرجاع النتيجة
            return serviceRepository.save(service);
        }
        return null;
    }

    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    public List<ServiceEntity> getServicesByVendor(Long vendorId) {
        return serviceRepository.findAllByUserId(vendorId);
    }

    public void deleteService(Long serviceId, Long vendorId) {
        ServiceEntity service = serviceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
        if (!service.getUser().getId().equals(vendorId)) {
            throw new RuntimeException("Unauthorized to delete this service");
        }
        serviceRepository.delete(service);
    }

    public ServiceEntity getServiceById(Long id) {
        return serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));
    }

    public ServiceEntity updateService(Long serviceId, Long vendorId, ServiceEntity updatedData) {
        ServiceEntity service = serviceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
        if (!service.getUser().getId().equals(vendorId)) {
            throw new RuntimeException("Unauthorized to update this service");
        }
        service.setName(updatedData.getName());
        service.setCategory(updatedData.getCategory());
        service.setPrice(updatedData.getPrice());
        service.setLocation(updatedData.getLocation());
        service.setImageUrl(updatedData.getImageUrl());
        service.setDescription(updatedData.getDescription());
        
        return serviceRepository.save(service);
    }
}