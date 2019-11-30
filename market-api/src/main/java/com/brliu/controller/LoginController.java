package com.brliu.controller;

import com.brliu.annotation.ResponseResult;
import com.brliu.domain.bo.UserBO;
import com.brliu.domain.entity.Users;
import com.brliu.enums.ResponseStateEnum;
import com.brliu.exception.RestMessageException;
import com.brliu.service.UserService;
import com.brliu.utils.BeanConverter;
import com.brliu.utils.IdWorker;
import com.brliu.utils.MD5Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@ResponseResult
@RequestMapping("/login")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    private final IdWorker idWorker;

    private final UserService userService;

    @GetMapping("/username/exist")
    public void checkUserNameExist(@RequestParam String username) {
        if (StringUtils.isNotBlank(username)) {
            userService.queryUserNameExist(username);
        }
    }

    @PostMapping("/register")
    public void registerUser(UserBO userBO) {
        if (Objects.isNull(userBO)) {
            throw new RestMessageException(ResponseStateEnum.PARAM_ERROR, "注册用户参数为空");
        }
        userBO.setId(String.valueOf(idWorker.nextId()));
        userBO.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        userService.saveUser(BeanConverter.convert(userBO, Users.class));
    }


}
