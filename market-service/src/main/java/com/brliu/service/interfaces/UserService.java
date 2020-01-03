package com.brliu.service.interfaces;

import com.brliu.domain.bo.UserBO;
import com.brliu.domain.entity.Users;

public interface UserService {
    boolean queryUserNameExist(String username);

    void saveUser(Users users);

    Users checkPassword(String id, String password);

    Users createUser(UserBO userBO);

    boolean queryUserNameIsExist(String username);

    Users queryUserForLogin(String username, String password);
}
