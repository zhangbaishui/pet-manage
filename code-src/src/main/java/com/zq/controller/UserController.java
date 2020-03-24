package com.zq.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Quan.Zhang
 */
@RestController
public class UserController {

    @RequestMapping("/test")
    public  String  test(){
        return  "傻逼一号卢本伟准备就绪";
    }
}
