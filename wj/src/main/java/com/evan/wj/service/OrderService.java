package com.evan.wj.service;

import com.evan.wj.dao.AddressDAO;
import com.evan.wj.dao.BookDAO;
import com.evan.wj.dao.OrderDAO;
import com.evan.wj.pojo.Address;
import com.evan.wj.pojo.Book;
import com.evan.wj.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    BookDAO bookDAO;
    @Autowired
    AddressDAO addressDAO;

    public void addOrUpdate(Order order) {
        orderDAO.save(order);
    }

    public List<Order> getOrderByUid(int uid) {

        List<Order> orders = orderDAO.findAllByUid(uid);
        for (Order o : orders) infoUpdate(o);
        return orders;
    }

    public void delOrder(int oid) {
        orderDAO.deleteById(oid);
    }

    public void updateByAid(int id, int aid) {
        Order order = orderDAO.findOrderById(id);
        order.setAid(aid);
        addOrUpdate(order);
    }

    public void updatePayment(int id) {
        Order order = orderDAO.findOrderById(id);
        order.setSuccessful(true);
        addOrUpdate(order);
    }

    public void updateRefund(int id) {
        Order order = orderDAO.findOrderById(id);
        order.setRefund(true);
        order.setFinish(false);
        addOrUpdate(order);
    }

    public void updateFinish(int id) {
        Order order = orderDAO.findOrderById(id);
        order.setFinish(true);
        addOrUpdate(order);
    }

    public void updatePaidAll(int id) {
        Order order = orderDAO.findOrderById(id);
        order.setPaidAll(true);
        addOrUpdate(order);
    }

    public List<Order> list() {
        List<Order> orders = orderDAO.findAll();
        for (Order o : orders) infoUpdate(o);
        return orders;
    }

    public void infoUpdate(Order o) {
        Book b = bookDAO.findBookById(o.getBid());
        Address a = addressDAO.findAddressById(o.getAid());
        a.initialFormat();
        o.setSale(b.isSale());
        o.confirmStatus();
        o.setTitle(b.getTitle());
        o.setAuthor(b.getAuthor());
        o.setAddress(a.getFormat());
        BigDecimal bg = BigDecimal.valueOf(o.getPrice() * o.getNumber()).setScale(2, RoundingMode.UP);
        o.setTotal(bg.doubleValue());
    }

    public List<Order> deliverList() {
        List<Order> orders = list();
        orders.removeIf(o -> !o.isPaidAll() || o.isRefund() || o.isDelivered());
        return orders;
    }

    public void deliverOrder(int id) {
        Order order = orderDAO.findOrderById(id);
        order.setDelivered(true);
        orderDAO.save(order);
    }

    public List<Order> refundList() {
        List<Order> orders = list();
        orders.removeIf(o -> !o.isRefund() || o.isFinish());
        return orders;
    }
}
