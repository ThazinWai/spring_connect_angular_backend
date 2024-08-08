package com.techie.springconnect.controller;


import com.techie.springconnect.dto.AuthenticationResponse;
import com.techie.springconnect.dto.LoginRequest;
import com.techie.springconnect.dto.RegisterRequest;
import com.techie.springconnect.model.User;
import com.techie.springconnect.repository.UserRepository;
import com.techie.springconnect.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody  RegisterRequest registerRequest){
        authService.signUp(registerRequest);
        return new ResponseEntity<>("User Register successful",OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> accountVerification(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
