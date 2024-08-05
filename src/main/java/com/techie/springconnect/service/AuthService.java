package com.techie.springconnect.service;


import com.techie.springconnect.dto.RegisterRequest;
import com.techie.springconnect.exceptions.SpringConnectException;
import com.techie.springconnect.model.NotificationEmail;
import com.techie.springconnect.model.User;
import com.techie.springconnect.model.VerificationToken;
import com.techie.springconnect.repository.UserRepository;
import com.techie.springconnect.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.internal.ServiceProxyGenerationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    public void signUp(RegisterRequest registerRequest){
        //save user
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);

        //generate token
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail(
                "Please Activate your account",
                user.getEmail(),
                "Thank you for signing up. Please click below the Url to activate your account" +
                        "http://localhost:8080/api/auth/accountVerification/"+token));

    }

    //generate and save verification Token
    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setCreatedDate(Instant.now());

        verificationTokenRepository.save(verificationToken);

        return token;
    }

    //verifyAccount
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringConnectException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    //fetch username based on "token" table and update "enabled - true"
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new SpringConnectException("User not found with this name" + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

}
