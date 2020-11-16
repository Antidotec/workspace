package com.evan.wj.service;

import com.evan.wj.dao.AdminRolePermissionDAO;
import com.evan.wj.pojo.AdminPermission;
import com.evan.wj.pojo.AdminRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminRolePermissionService {

    @Autowired
    AdminRolePermissionDAO adminRolePermissionDAO;

    @Transactional
    public void savePermChanges(int rid, List<AdminPermission> perms) {
        adminRolePermissionDAO.deleteAllByRid(rid);
        perms.forEach(p -> {
            AdminRolePermission rp = new AdminRolePermission();
            rp.setRid(rid);
            rp.setPid(p.getId());
            adminRolePermissionDAO.save(rp);
        });
    }
}
