package com.ifmo.web.lab4.service;

import com.ifmo.web.lab4.model.data.User;

public interface UserService {
    User save(User user);

    User findByUsername(String username);
}
