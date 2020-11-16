package com.evan.wj.service;

import com.evan.wj.dao.AdminRoleDAO;
import com.evan.wj.dao.AdminUserDAO;
import com.evan.wj.pojo.AdminMenu;
import com.evan.wj.pojo.AdminRole;
import com.evan.wj.pojo.AdminUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminRoleService {
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRoleDAO adminRoleDAO;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;
    @Autowired
    AdminMenuService adminMenuService;
    @Autowired
    AdminPermissionService adminPermissionService;

    public List<AdminRole> listRolesByUser(int uid) {
        List<Integer> rids = adminUserRoleService.listAllByUid(uid)
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());
        return adminRoleDAO.findAllById(rids);
    }

    //还要返回 角色对应的 menus以及perms
    public List<AdminRole> findAll() {
        List<AdminRole> roles = adminRoleDAO.findAll();
        for (AdminRole role : roles) {
            role.setMenus(adminMenuService.getMenusByRid(role.getId()));
            role.setPerms(adminPermissionService.getPermsByRid(role.getId()));
        }
        return roles;
    }

    public void updateRole(AdminRole adminRole) {
        adminRoleDAO.save(adminRole);
        adminRolePermissionService.savePermChanges(adminRole.getId(), adminRole.getPerms());
    }
}
