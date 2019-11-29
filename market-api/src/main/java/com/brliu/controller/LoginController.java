package com.brliu.controller;

import com.brliu.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    private final UserService userService;

    @GetMapping("/username")
    public void checkUserNameExist(@RequestParam String username) {
        userService.queryUserNameExist(username);
    }
}
