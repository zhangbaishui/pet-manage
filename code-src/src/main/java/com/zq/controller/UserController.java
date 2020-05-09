package com.zq.controller;

import com.zq.pojo.User;
import com.zq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String,Boolean> nameIsEixt(@RequestParam(value = "name")String name) {
        Boolean res = userService.nameIsEixt(name);
        HashMap<String, Boolean> stringBooleanHashMap = new HashMap<>();
        stringBooleanHashMap.put("nameis",res);
        return stringBooleanHashMap;
    }

    /*校验账号密码是否错误*/
    @RequestMapping("/namePassIsTrue")
    @ResponseBody
    public Map<String,Object> namePassIsTrue(@RequestParam(value = "name")String name, @RequestParam(value = "pass")String pass) {
        Boolean res = userService.namePassIsTrue(name,pass);
        HashMap<String, Object> stringBooleanHashMap = new HashMap<>();
        stringBooleanHashMap.put("ist",res);
        if(res = true){
            /*姓名以及邮箱传递给用户*/
            User user = new User();
            user.setName(name);
            user.setPass(pass);
            User  user2  =  userService.selectOneUser(user);
            stringBooleanHashMap.put("name",user2.getName());
            stringBooleanHashMap.put("mail",user2.getMail());
        }

        return stringBooleanHashMap;
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
    /*用户注册*/
    @PostMapping("/register")
    @ResponseBody
    public Map<String, String> register(User user) {
        HashMap<String, String> map = userService.registerUser(user);
        return map;
    }

    /*找回密码*/
    @PostMapping("/register")
    @ResponseBody
    public Map<String, String> callbackPass(@RequestParam(value = "iphone")String iphone) {
        HashMap<String, String> map = userService.callbackPassService(iphone);
        return map;
    }

}
