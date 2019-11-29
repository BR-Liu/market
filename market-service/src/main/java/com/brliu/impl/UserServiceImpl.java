package com.brliu.impl;

import com.brliu.entity.Users;
import com.brliu.mapper.UsersMapper;
import com.brliu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UsersMapper usersMapper;

    @Override

    public boolean queryUserNameExist(String username) {
        int count = usersMapper.selectCountByExample(
                Example.builder(Users.class)
                        .where(WeekendSqls.<Users>custom()
                                .andEqualTo(Users::getUsername, username))
                        .build()
        );
        return count == 1 ? true : false;
    }
}
