package com.canfin.corebanking.customerservice.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "APP_USER")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 100)
    private String fullName;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String role = "USER";

    @Column(nullable = false)
    private Integer isActive = 1;

    @Column(length = 500)
    private String refreshToken;

    @Column(length = 6)
    private String resetOtp;

    private LocalDateTime resetOtpExpiry;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Integer getIsActive() { return isActive; }
    public void setIsActive(Integer isActive) { this.isActive = isActive; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public String getResetOtp() { return resetOtp; }
    public void setResetOtp(String resetOtp) { this.resetOtp = resetOtp; }
    public LocalDateTime getResetOtpExpiry() { return resetOtpExpiry; }
    public void setResetOtpExpiry(LocalDateTime resetOtpExpiry) { this.resetOtpExpiry = resetOtpExpiry; }
}
