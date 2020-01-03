package com.brliu.service.impl;

import com.brliu.domain.bo.UserBO;
import com.brliu.domain.entity.Users;
import com.brliu.enums.ResponseStateEnum;
import com.brliu.enums.SexEnum;
import com.brliu.exception.RestMessageException;
import com.brliu.mapper.UsersMapper;
import com.brliu.service.interfaces.UserService;
import com.brliu.utils.DateUtil;
import com.brliu.utils.MD5Utils;
import com.brliu.utils.Sid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UsersMapper usersMapper;

    private final Sid sid;

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

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

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users checkPassword(String id, String password) {
        return usersMapper.selectOneByExample(
                Example.builder(Users.class)
                        .where(WeekendSqls.<Users>custom()
                                .andEqualTo(Users::getId, id)
                                .andEqualTo(Users::getPassword, MD5Utils.getMD5Str(password)))
                        .build()
        );
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {
        String userId = sid.nextShort();

        Users user = new Users();
        user.setId(userId);
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 默认用户昵称同用户名
        user.setNickname(userBO.getUsername());
        // 默认头像
        user.setFace(USER_FACE);
        // 默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        // 默认性别为 保密
        user.setSex(SexEnum.secret.type);

        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());

        usersMapper.insert(user);

        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserNameIsExist(String username) {
        Users users = usersMapper.selectOneByExample(
                Example.builder(Users.class)
                        .where(WeekendSqls.<Users>custom()
                                .andEqualTo(Users::getUsername, username)
                        )
                        .build()
        );

        return users != null;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {
        return usersMapper.selectOneByExample(
                Example.builder(Users.class)
                        .where(WeekendSqls.<Users>custom()
                                .andEqualTo(Users::getUsername, username)
                                .andEqualTo(Users::getPassword,password)
                        )
                        .build()
        );
    }
}
