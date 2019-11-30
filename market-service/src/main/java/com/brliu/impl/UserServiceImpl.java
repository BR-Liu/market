package com.brliu.impl;

import com.brliu.domain.entity.Users;
import com.brliu.enums.ResponseStateEnum;
import com.brliu.exception.RestMessageException;
import com.brliu.mapper.UsersMapper;
import com.brliu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(Users users) {
        if (usersMapper.insertSelective(users) != 1) {
            throw new RestMessageException(ResponseStateEnum.SERVER_ERROR, "保存用户失败");
        }
    }
}
