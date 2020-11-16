package com.evan.wj.service;

import com.evan.wj.dao.AdminRoleMenuDAO;
import com.evan.wj.pojo.AdminRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class AdminRoleMenuService {
    @Autowired
    AdminRoleMenuDAO adminRoleMenuDAO;

    public List<AdminRoleMenu> findAllByRid(List<Integer> rids) {
        return adminRoleMenuDAO.findAllByRid(rids);
    }

    public List<AdminRoleMenu> findAllByRid(int rid) {
        return adminRoleMenuDAO.findAllByRid(rid);
    }

    @Modifying
    @Transactional
    public boolean updateRoleMenu(int rid, LinkedHashMap menusIds) {
       adminRoleMenuDAO.deleteAllByRid(rid);
        for (Object value : menusIds.values()) {
            for (int mid : (List<Integer>) value) {
                AdminRoleMenu rm = new AdminRoleMenu();
                rm.setRid(rid);
                rm.setMid(mid);
                adminRoleMenuDAO.save(rm);
            }
        }
        return true;
    }
}
