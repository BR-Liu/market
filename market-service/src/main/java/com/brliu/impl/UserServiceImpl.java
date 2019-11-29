package com.brliu.impl;

import com.brliu.mapper.UsersMapper;
import com.brliu.pojo.Users;
import com.brliu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UsersMapper usersMapper;

    @Override

    public boolean queryUserNameExist(String username) {
        List<Users> users = usersMapper.selectByExample(
                Example.builder(Users.class)
                .where(WeekendSqls.<Users>custom()
                        .andEqualTo(Users::getUsername, username))
                .build()
        );
        return Objects.nonNull(users);
    }
}
