package com.canfin.corebanking.customerservice.controller;

import com.canfin.corebanking.customerservice.dto.*;
import com.canfin.corebanking.customerservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Map<String, String> response = authService.login(request);
        BaseResponse<Map<String, String>> baseResponse = new BaseResponse<>();
        baseResponse.setData(response);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("Login successful");
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        String message = authService.register(request);
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setData(message);
        baseResponse.setSuccessCode(HttpStatus.CREATED.toString());
        baseResponse.setSuccessMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        String message = authService.forgotPassword(request);
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setData(message);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage(message);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        Map<String, String> response = authService.refreshToken(refreshToken);
        BaseResponse<Map<String, String>> baseResponse = new BaseResponse<>();
        baseResponse.setData(response);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage("Token refreshed successfully");
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        String message = authService.resetPassword(request);
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setData(message);
        baseResponse.setSuccessCode(HttpStatus.OK.toString());
        baseResponse.setSuccessMessage(message);
        return ResponseEntity.ok(baseResponse);
    }
}
