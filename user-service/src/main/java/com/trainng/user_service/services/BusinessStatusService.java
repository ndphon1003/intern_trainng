package com.trainng.user_service.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainng.user_service.models.BusinessStatus;
import com.trainng.user_service.repositories.BusinessStatusRepo;

@Service
public class BusinessStatusService {
    @Autowired
    private BusinessStatusRepo businessStatusRepo;

    public Void createBusinessStatusForUserId(UUID userId){
        if (businessStatusRepo.findByUserId(userId) == null) {
            businessStatusRepo.save(new BusinessStatus(userId));
        }
        return null;
    }

    public BusinessStatus getBusinessStatusByUserId(UUID userId) {
        return businessStatusRepo.findByUserId(userId);
    }

    public BusinessStatus deactivateUser(UUID userId) {

        BusinessStatus status = businessStatusRepo.findByUserId(userId);

        // nếu chưa có record thì tạo mới luôn (tránh null)
        if (status == null) {
            status = new BusinessStatus(userId);
        }

        // nếu đã bị deactivate rồi thì không làm gì nữa
        if (status.isDeactivated()) {
            return status;
        }

        // update trạng thái
        status.setDeactivated(true);

        // lưu DB
        return businessStatusRepo.save(status);
    }
}
