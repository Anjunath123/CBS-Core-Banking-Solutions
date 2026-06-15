package com.canfin.corebanking.customerservice.service.impl;

import com.canfin.corebanking.customerservice.dto.*;
import com.canfin.corebanking.customerservice.entity.AppUser;
import com.canfin.corebanking.customerservice.exception.OmniNGException;
import com.canfin.corebanking.customerservice.repository.AppUserRepository;
import com.canfin.corebanking.customerservice.security.JwtUtil;
import com.canfin.corebanking.customerservice.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Map<String, String> login(LoginRequest request) throws OmniNGException {
        AppUser user = appUserRepository.findByUsernameAndIsActive(request.getUsername(), 1)
                .orElseThrow(() -> new OmniNGException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new OmniNGException("Invalid username or password");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(), user.getRole());

        user.setRefreshToken(refreshToken);
        appUserRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("token", accessToken);
        response.put("refreshToken", refreshToken);
        response.put("username", user.getUsername());
        response.put("fullName", user.getFullName());
        response.put("role", user.getRole());
        logger.info("User logged in: {}", user.getUsername());
        return response;
    }

    @Override
    public Map<String, String> refreshToken(String refreshToken) throws OmniNGException {
        if (!jwtUtil.validateToken(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
            throw new OmniNGException("Invalid or expired refresh token. Please login again.");
        }

        String username = jwtUtil.getUsername(refreshToken);
        AppUser user = appUserRepository.findByUsernameAndIsActive(username, 1)
                .orElseThrow(() -> new OmniNGException("User not found"));

        if (user.getRefreshToken() == null || !user.getRefreshToken().equals(refreshToken)) {
            throw new OmniNGException("Refresh token has been revoked. Please login again.");
        }

        String newAccessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getUsername(), user.getRole());

        user.setRefreshToken(newRefreshToken);
        appUserRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("token", newAccessToken);
        response.put("refreshToken", newRefreshToken);
        response.put("username", user.getUsername());
        response.put("fullName", user.getFullName());
        logger.info("Token refreshed for user: {}", user.getUsername());
        return response;
    }

    @Override
    public String register(RegisterRequest request) throws OmniNGException {
        if (appUserRepository.existsByUsername(request.getUsername())) {
            throw new OmniNGException("Username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRole("USER");
        user.setIsActive(1);
        appUserRepository.save(user);
        logger.info("User registered: {}", request.getUsername());
        return "User registered successfully";
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest request) throws OmniNGException {
        AppUser user = appUserRepository.findByUsernameAndIsActive(request.getUsername(), 1)
                .orElseThrow(() -> new OmniNGException("User not found"));

        String otp = String.format("%06d", new Random().nextInt(999999));
        user.setResetOtp(otp);
        user.setResetOtpExpiry(LocalDateTime.now().plusMinutes(10));
        appUserRepository.save(user);

        logger.info("OTP generated for user: {} OTP: {}", user.getUsername(), otp);
        // In production, send OTP via email. For now, return it in response.
        return "OTP sent successfully. OTP: " + otp;
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) throws OmniNGException {
        AppUser user = appUserRepository.findByUsernameAndIsActive(request.getUsername(), 1)
                .orElseThrow(() -> new OmniNGException("User not found"));

        if (user.getResetOtp() == null || !user.getResetOtp().equals(request.getOtp())) {
            throw new OmniNGException("Invalid OTP");
        }

        if (user.getResetOtpExpiry() == null || user.getResetOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new OmniNGException("OTP has expired. Please request a new one");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetOtp(null);
        user.setResetOtpExpiry(null);
        appUserRepository.save(user);
        logger.info("Password reset for user: {}", user.getUsername());
        return "Password reset successfully";
    }
}
