package com.brliu.service.interfaces;

import com.brliu.domain.entity.Users;

public interface UserService {
    boolean queryUserNameExist(String username);

    void saveUser(Users users);
}
