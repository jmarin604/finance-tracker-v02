package com.finance.tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.tracker.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
