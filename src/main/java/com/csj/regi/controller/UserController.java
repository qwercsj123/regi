package com.csj.regi.controller;

import com.csj.regi.common.Result;
import com.csj.regi.domain.User;
import com.csj.regi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author:csj
 * @version:1.0
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public Result<String> sendMessage(@RequestBody User user, HttpSession session){
        //获取手机号
       return userService.sendMessage(user,session);
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody Map<String,String> map,HttpSession session){
        return userService.login(map,session);
    }
}
