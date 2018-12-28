package com.nineswords.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create by Jarvis.wang on Jarvis's device
 *
 * @author Jarvis.wang  Jarvis
 * @date 2018-12-27 15:44
 */
@Controller
public class AuthController {

    @GetMapping("/login")
    public String toLogin() {
        return "index";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam(value = "userName") String userName,
                        @RequestParam(value = "password") String password) {
        return "success";
    }
}
