package com.trainng.auth_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainng.auth_service.repositories.UserRepo;

@Service
public class AuthService {
    
    @Autowired
    private UserRepo userRepo;
}
