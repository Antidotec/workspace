package com.evan.wj.dao;

import com.evan.wj.pojo.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminUserDAO extends JpaRepository<AdminUser, Integer> {
    AdminUser findByUsername(String name);

    AdminUser getByUsernameAndPassword(String username, String password);


    List<AdminUser> findAllByUsernameLike(String s);
}
