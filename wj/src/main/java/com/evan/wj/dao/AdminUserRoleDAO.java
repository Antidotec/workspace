package com.evan.wj.dao;

import com.evan.wj.pojo.AdminUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminUserRoleDAO extends JpaRepository<AdminUserRole,Integer> {
    public List<AdminUserRole> findAllByUid(int uid);

    void deleteAllByUid(int uid);
}
