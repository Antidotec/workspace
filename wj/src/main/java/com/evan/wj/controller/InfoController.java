package com.evan.wj.controller;

import com.evan.wj.pojo.Address;
import com.evan.wj.pojo.User;
import com.evan.wj.service.AddressService;
import com.evan.wj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InfoController {

    @Autowired
    AddressService addressService;

    @Autowired
    UserService userService;

    @CrossOrigin
    @GetMapping("/api/address")
    public List<Address> listByCategory(@RequestParam("uid") int uid) throws Exception {
        return addressService.findAllByUid(uid);
    }

    @CrossOrigin
    @PostMapping("/api/modifyAddress")
    public void addOrUpdate(@RequestBody Address address) throws Exception {
        addressService.addOrUpdate(address);
    }

    @CrossOrigin
    @PostMapping("/api/delAddress")
    public void delAddress(@RequestBody Address address) throws Exception {
        addressService.delAddress(address.getId());
    }

    @CrossOrigin
    @GetMapping("/api/getAddress")
    public Address getAddress(@RequestParam("id") int id) throws Exception {
        return addressService.findAddressById(id);
    }

    @CrossOrigin
    @PostMapping("/api/becomeVip")
    public void becomeVip(@RequestParam("id") int id,@RequestParam("degree") int vip) throws Exception {
        userService.becomeVip(id,vip);
    }

    @CrossOrigin
    @GetMapping("/api/userList")
    public List<User> userList() {
        return userService.list();
    }

    @CrossOrigin
    @PostMapping("/api/modifyUserInfo")
    public void becomeVip(@RequestBody User user) throws Exception {
        userService.updateUser(user);
    }

    @CrossOrigin
    @PostMapping("/api/delUser")
    public void delUser(@RequestParam int id){
        userService.delUser(id);
    }
    @CrossOrigin
    @GetMapping("/api/getUser")
    public User getUser(@RequestParam int id){
        return userService.getById(id);
    }
}


