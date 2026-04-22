package com.trainng.auth_service.middlewares;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.trainng.auth_service.repositories.UserRepo;

@Component
public class RegisterValidate {
    
    @Autowired
    private UserRepo userRepo;

    public boolean isEmailTaken(String email) {
        return userRepo.findByEmail(email).isPresent();
    }
    public boolean isUsernameTaken(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    public ValidateResponse validate(String username, String email, String password, String role) {

        if (password.length() < 6) {
            return new ValidateResponse(HttpStatus.BAD_REQUEST.value(), "Password too short");
        }

        if (isEmailTaken(email)) {
            return new ValidateResponse(HttpStatus.BAD_REQUEST.value(), "Email already exists");
        }

        if (isUsernameTaken(username)) {
            return new ValidateResponse(HttpStatus.BAD_REQUEST.value(), "Username already exists");
        }

        if (!role.equals("CUSTOMER") && !role.equals("MANAGER") && !role.equals("ADMIN")) {
            return new ValidateResponse(HttpStatus.BAD_REQUEST.value(), "Invalid role");
        }

        return new ValidateResponse(HttpStatus.OK.value(), "Valid for registration");
    }
}
