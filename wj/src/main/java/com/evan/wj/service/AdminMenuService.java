package com.evan.wj.service;

import com.evan.wj.dao.AdminMenuDAO;
import com.evan.wj.pojo.AdminMenu;
import com.evan.wj.pojo.AdminRoleMenu;
import com.evan.wj.pojo.AdminUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMenuService {
    @Autowired
    AdminMenuDAO adminMenuDAO;

    @Autowired
    AdminUserRoleService adminUserRoleService;

    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public List<AdminMenu> getMenusByUid(int uid) {
        List<Integer> rids = adminUserRoleService.listAllByUid(uid)
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());
        List<Integer> menuIds = adminRoleMenuService.findAllByRid(rids)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        //这里distinct的原因就是多个角色对应的menus可能重复
        List<AdminMenu> menus = adminMenuDAO.findAllById(menuIds).stream().distinct().collect(Collectors.toList());
        handleMenus(menus);
        return menus;
    }


    public List<AdminMenu> getMenusByRid(int rid) {
        List<Integer> mids = adminRoleMenuService.findAllByRid(rid)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuDAO.findAllById(mids);
        handleMenus(menus);
        return menus;
    }

    public void handleMenus(List<AdminMenu> menus) {
        menus.forEach(m -> {
            List<AdminMenu> children = getAllByParentId(m.getId());
            m.setChildren(children);
        });

        menus.removeIf(m -> m.getParentId() != 0);
    }

    public List<AdminMenu> getAllByParentId(int pid) {
        return adminMenuDAO.findAllByParentId(pid);
    }

}
