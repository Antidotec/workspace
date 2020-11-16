package com.evan.wj.service;

import com.evan.wj.dao.UserDAO;
import com.evan.wj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public boolean isExist(String username) {
        User user = getByName(username);
        return null != user;
    }

    public User getByName(String username) {
        return userDAO.findByUsername(username);
    }

    public User get(String username, String password) {
        return userDAO.getByUsernameAndPassword(username, password);
    }

    public void add(User user) {
        userDAO.save(user);
    }

    public void becomeVip(int id) {
        User user = userDAO.findUserById(id);
        user.setVip(true);
        userDAO.save(user);
    }

    public List<User> list() {
        return userDAO.findAll();
    }

    public void updateUser(User user) {
        userDAO.save(user);
    }

    public void delUser(int id){
        userDAO.deleteById(id);
    }

    public void register(User user) {
        userDAO.save(user);
    }
    public User getById(int id){
        return userDAO.findUserById(id);
    }
}
