package com.evan.wj.service;

import com.evan.wj.dao.AdminPermissionDAO;
import com.evan.wj.dao.AdminRolePermissionDAO;
import com.evan.wj.pojo.AdminPermission;
import com.evan.wj.pojo.AdminRole;
import com.evan.wj.pojo.AdminRoleMenu;
import com.evan.wj.pojo.AdminRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminPermissionService {
    @Autowired
    AdminRoleService adminRoleService;

    @Autowired
    AdminRolePermissionDAO adminRolePermissionDAO;

    @Autowired
    AdminPermissionDAO adminPermissionDAO;

    //通过用户的id获取权限
    public Set<String> listPermissionURLsByUser(int uid) {
        List<Integer> rids = adminRoleService.listRolesByUser(uid)
                .stream().map(AdminRole::getId).collect(Collectors.toList());
        List<Integer> pids = adminRolePermissionDAO.findAllByRid(rids)
                .stream().map(AdminRolePermission::getPid).collect(Collectors.toList());
        List<AdminPermission> perms = adminPermissionDAO.findAllById(pids);
        Set<String> urls = perms.stream().map(AdminPermission::getUrl).collect(Collectors.toSet());
        return urls;
    }

    //判断该api是否可供该管理员用户使用
    public boolean needFilter(String requestAPI) {
        List<AdminPermission> ps = adminPermissionDAO.findAll();
        for (AdminPermission p : ps) {
            if (p.getUrl().equals(requestAPI)) {
                return true;
            }
        }
        return false;
    }

    public List<AdminPermission> list() {
        return adminPermissionDAO.findAll();
    }

    public List<AdminPermission> getPermsByRid(int rid) {
        List<Integer> pids = adminRolePermissionDAO.findByRid(rid)
                .stream().map(AdminRolePermission::getPid).collect(Collectors.toList());
        return adminPermissionDAO.findAllById(pids);
    }

}
