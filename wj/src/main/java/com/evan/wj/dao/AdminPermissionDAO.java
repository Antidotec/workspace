package com.evan.wj.dao;

import com.evan.wj.pojo.AdminPermission;
import com.evan.wj.service.AdminPermissionService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPermissionDAO extends JpaRepository<AdminPermission,Integer> {

}
