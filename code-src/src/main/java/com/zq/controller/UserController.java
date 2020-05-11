package com.zq.controller;

import com.zq.pojo.User;
import com.zq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        System.out.println(res);
        if(res == true){
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
    public boolean deleteUser( User user) {
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
    public Map<String, Object> register(HttpServletRequest request ,
                                        @RequestParam(value = "name")String name,
                                        @RequestParam(value = "pass")String pass,
                                        @RequestParam(value = "gender")String gender,
                                        @RequestParam(value = "iphone")String iphone,
                                        @RequestParam(value = "mail")String mail,
                                        @RequestParam(value = "age")String age,
                                        @RequestParam(value = "image") MultipartFile image) throws IOException {
        User user = new User();
        user.setName(name);
        user.setPass(pass);
        user.setGender(gender);
        user.setIphone(iphone);
        user.setMail(mail);
        user.setAge(Integer.parseInt(age));

        Date t = new Date();
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /*文件上传OSS  upload 第三个参数为oss文件夹名称  */
        HashMap<String,Object> br = userService.upload(request,image,dataFormat.toString());


//        user.setImage(image);
//        HashMap<String, String> map = userService.registerUser(user);
        HashMap<String, Object> map = userService.registerUser(user,br);
        return map;
    }

    /*找回密码*/
    @PostMapping("/callback")
    @ResponseBody
    public Map<String, String> callbackPass(@RequestParam(value = "iphone")String iphone) {
        HashMap<String, String> map = userService.callbackPassService(iphone);
        return map;
    }

    /*修改名字*/
    @PostMapping("/uupdateName")
    @ResponseBody
    public Map<String, String> uupdateName(@RequestParam(value = "name")String name,@RequestParam(value = "id")String id) {
        HashMap<String, String> map = userService.uupdateName(id,name);
        return map;
    }

    /*修改邮箱*/
    @PostMapping("/updateMali")
    @ResponseBody
    public Map<String, String> updateMali(@RequestParam(value = "mail")String mail,@RequestParam(value = "id")String id) {
        HashMap<String, String> map = userService.updateMali(id,mail);
        return map;
    }

    /*通过用户名和邮箱获取信息*/
    @PostMapping("/getUser")
    @ResponseBody
    public Map<String, Object> getUser(@RequestParam(value = "mail")String mail,@RequestParam(value = "name")String name) {
        HashMap<String, Object> map = userService.getUser(name,mail);
        return map;
    }
    /*通过用户名和邮箱获取信息*/
    @PostMapping("/getId")
    @ResponseBody
    public Map<String, Object> getId(@RequestParam(value = "mail")String mail,@RequestParam(value = "name")String name) {
        HashMap<String, Object> map = userService.getId(name,mail);
        return map;
    }
}
