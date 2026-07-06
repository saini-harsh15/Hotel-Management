package com.hotelmanagement.repository;

import com.hotelmanagement.entity.UserEntity;
import com.hotelmanagement.enums.UserRole;
import com.hotelmanagement.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    List<UserEntity> findByRole(UserRole role);

    long countByRole(UserRole role);

    long countByStatus(UserStatus status);
}
