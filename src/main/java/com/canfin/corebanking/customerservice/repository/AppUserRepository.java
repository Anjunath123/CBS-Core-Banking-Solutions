package com.canfin.corebanking.customerservice.repository;

import com.canfin.corebanking.customerservice.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsernameAndIsActive(String username, Integer isActive);
    Optional<AppUser> findByEmailAndIsActive(String email, Integer isActive);
    boolean existsByUsername(String username);
}
