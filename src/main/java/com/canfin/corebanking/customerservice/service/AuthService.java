package com.canfin.corebanking.customerservice.service;

import com.canfin.corebanking.customerservice.dto.*;
import com.canfin.corebanking.customerservice.exception.OmniNGException;

import java.util.Map;

public interface AuthService {
    Map<String, String> login(LoginRequest request) throws OmniNGException;
    Map<String, String> refreshToken(String refreshToken) throws OmniNGException;
    String register(RegisterRequest request) throws OmniNGException;
    String forgotPassword(ForgotPasswordRequest request) throws OmniNGException;
    String resetPassword(ResetPasswordRequest request) throws OmniNGException;
}
