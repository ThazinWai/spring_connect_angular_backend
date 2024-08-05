package com.techie.springconnect.controller;


import com.techie.springconnect.dto.RegisterRequest;
import com.techie.springconnect.model.User;
import com.techie.springconnect.repository.UserRepository;
import com.techie.springconnect.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(RegisterRequest registerRequest){
        authService.signUp(registerRequest);
        return new ResponseEntity<>("User Register successful",OK);
    }
}
