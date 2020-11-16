package com.evan.wj.service;

import com.evan.wj.dao.AdminUserDAO;
import com.evan.wj.pojo.AdminRole;
import com.evan.wj.pojo.AdminUser;
import com.evan.wj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserService {
    @Autowired
    AdminUserDAO adminUserDAO;

    @Autowired
    AdminRoleService adminRoleService;

    @Autowired
    AdminUserRoleService adminUserRoleService;

    //adminUser返回的还有他所拥有的roles
    public List<AdminUser> list() {
        List<AdminUser> users = adminUserDAO.findAll();
        for (AdminUser user : users) {
            List<AdminRole> roles = adminRoleService.listRolesByUser(user.getId());
            user.setRoles(roles);
        }
        return users;
    }


    public void updateUser(AdminUser user) {
        AdminUser userInDB = adminUserDAO.findByUsername(user.getUsername());
        userInDB.setName(user.getName());
        userInDB.setPhone(user.getPhone());
        userInDB.setEmail(user.getEmail());
        adminUserDAO.save(userInDB);
        //更新UserRole 表
        adminUserRoleService.saveRoleChanges(userInDB.getId(), user.getRoles());
    }

    public AdminUser get(String username, String password) {
        return adminUserDAO.getByUsernameAndPassword(username, password);
    }
}
