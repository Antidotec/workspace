package com.evan.wj.controller;

import com.evan.wj.pojo.*;
import com.evan.wj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@CrossOrigin
public class AdminController {
    @Autowired
    AdminUserService adminUserService;

    @Autowired
    AdminRoleService adminRoleService;

    @Autowired
    AdminPermissionService adminPermissionService;

    @Autowired
    AdminMenuService adminMenuService;

    @Autowired
    AdminRolePermissionService adminRolePermissionService;

    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    @CrossOrigin
    @GetMapping("/api/menu")
    public List<AdminMenu> menu(@RequestParam("uid") int uid) {
        return adminMenuService.getMenusByUid(uid);
    }

    @CrossOrigin
    @GetMapping("/api/admin/user")
    public List<AdminUser> allAdminUsers() {
        return adminUserService.list();
    }

    @CrossOrigin
    @GetMapping("/api/admin/role")
    public List<AdminRole> allRoles() {
        return adminRoleService.findAll();
    }

    //修改管理员用户
    @CrossOrigin
    @PostMapping("/api/admin/modifyUser")
    public void updateUser(@RequestBody AdminUser adminUser) {
        adminUserService.updateUser(adminUser);
    }

    @CrossOrigin
    @GetMapping("/api/admin/role/perm")
    public List<AdminPermission> allPerms() {
        return adminPermissionService.list();
    }

    @CrossOrigin
    @GetMapping("/api/admin/role/menu")
    public List<AdminMenu> listAllMenus() {
        return adminMenuService.getMenusByRid(1);
    }

    @CrossOrigin
    @PostMapping("/api/admin/modifyRole")
    public void updateRole(@RequestBody AdminRole adminRole) {
        adminRoleService.updateRole(adminRole);
    }

    @CrossOrigin
    @PostMapping("/api/admin/modifyMenu")
    public void updateRoleMenu(@RequestParam int rid, @RequestBody LinkedHashMap menusIds) {
        adminRoleMenuService.updateRoleMenu(rid, menusIds);
    }

}
