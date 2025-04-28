package com.milind.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milind.modal.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}