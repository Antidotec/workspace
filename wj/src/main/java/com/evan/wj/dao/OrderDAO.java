package com.evan.wj.dao;

import com.evan.wj.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order,Integer> {
    List<Order> findAllByUid(int uid);
    Order findOrderById(int id);
}
