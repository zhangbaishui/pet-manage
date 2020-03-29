package com.zq.controller;

import com.zq.pojo.User;
import com.zq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: Quan.Zhang
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/test")
    public  String  test(){
        return  "傻逼一号卢本伟准备就绪";
    }

    @RequestMapping("/user")
    public List<User> user(){
        List<User> users = userService.selectAllUser();
        return users ;
    }
}
