package com.zq.controller;

import com.zq.pojo.User;
import com.zq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: Quan.Zhang
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    /*测试专用*/
    @RequestMapping("/user")
    public List<User> user() {
        List<User> users = userService.selectAllUser();
        return users;
    }

    /**/
    /*校验用户名是否存在*/
    @RequestMapping("/nameIsEixt")
    @ResponseBody
    public Boolean nameIsEixt(@RequestParam(value = "name")String name) {
        Boolean res = userService.nameIsEixt(name);
        return res;
    }

    /*添加用户*/
    @PostMapping("/add")
    public ResponseEntity<Void> addUser(User user) {
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /*修改用户*/
    @PostMapping("/update")
    public boolean updateUser(User user) {
        Boolean result = userService.upadateUser(user);
        return result;
    }

    /*删除用户*/
    @PostMapping("/delete")
    public boolean deleteUser(User user) {
        Boolean result = userService.deleteUser(user);
        return result;
    }

    /*根据user名称查询用户*/
    @PostMapping("/queryByName")
    public User selectUserByName(String name) {
        User user = userService.selectUserByName(name);
        return user;
    }

}
