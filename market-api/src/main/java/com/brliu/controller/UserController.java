package com.brliu.controller;

import com.brliu.annotation.ResponseResult;
import com.brliu.domain.bo.UserBO;
import com.brliu.domain.entity.Users;
import com.brliu.enums.ResponseStateEnum;
import com.brliu.exception.RestMessageException;
import com.brliu.service.interfaces.UserService;
import com.brliu.utils.BeanConverter;
import com.brliu.utils.IdWorker;
import com.brliu.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Api(value = "用户处理接口",tags = {"用于处理用户信息相关的接口"})
@Slf4j
@RestController
@ResponseResult
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "判断用户名存在",notes = "用户名存在判断")
    @GetMapping("/username/exist")
    public void checkUserNameExist(@RequestParam String username) {
        if (StringUtils.isNotBlank(username)) {
            userService.queryUserNameExist(username);
        }
    }

    @ApiOperation(value = "用户注册",notes = "用于用户注册")
    @PostMapping("/register")
    public void registerUser(@RequestBody UserBO userBO) {
        if (Objects.isNull(userBO)) {
            throw new RestMessageException(ResponseStateEnum.PARAM_ERROR, "注册用户参数为空");
        }
        userBO.setId(String.valueOf(IdWorker.getFlowIdWorkerInstance().nextId()));
        userBO.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        userService.saveUser(BeanConverter.convert(userBO, Users.class));
    }


}
