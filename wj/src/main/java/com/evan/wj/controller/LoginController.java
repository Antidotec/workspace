package com.evan.wj.controller;

import com.evan.wj.pojo.AdminUser;
import com.evan.wj.pojo.User;
import com.evan.wj.service.AdminUserService;
import com.evan.wj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    AdminUserService adminUserService;

    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public User login(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        User user = userService.get(username, requestUser.getPassword());
        return user;
    }

    @CrossOrigin
    @PostMapping(value = "api/adminLogin")
    @ResponseBody
    public AdminUser adminLogin(@RequestBody AdminUser requestUser) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        AdminUser user = adminUserService.get(username, requestUser.getPassword());
        return user;
    }
    @CrossOrigin
    @PostMapping(value = "api/userRegister")
    @ResponseBody
    public void userRegister(@RequestBody User user) {
        userService.register(user);
    }

    @CrossOrigin
    @PostMapping(value = "api/adminRegister")
    @ResponseBody
    public void adminRegister(@RequestBody AdminUser user) {
        adminUserService.register(user);
    }
}

