package com.chen.blog.controller;

import com.chen.blog.service.LoginService;
import com.chen.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public Result logout(@RequestHeader("authorization") String token) {
        return loginService.logout(token);
    }
}
