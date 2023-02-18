package com.infinite.tikfake.service;

import com.infinite.tikfake.common.AjaxResult;
import com.infinite.tikfake.entity.User;

public interface UserService {
    User getUserByName(String name);

    User getUserById(Integer id);

    void save(User user);

    AjaxResult register(String username, String password);

    AjaxResult login(String username, String password);

}
