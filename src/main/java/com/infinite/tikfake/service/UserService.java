package com.infinite.tikfake.service;

import com.infinite.tikfake.entity.User;

public interface UserService {
    User getUserByName(String name);

    User getUserById(Integer id);

    void save(User user);
}
