package com.ifmo.web.lab4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ifmo.web.lab4.model.data.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
