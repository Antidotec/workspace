package com.evan.wj.controller;

import com.evan.wj.pojo.Order;
import com.evan.wj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @CrossOrigin
    @PostMapping("/api/submitOrder")
    public void addOrder(@RequestBody Order order) throws Exception {
        orderService.addOrUpdate(order);
    }

    @CrossOrigin
    @GetMapping("/api/getOrder")
    public List<Order> getOrder(@RequestParam("uid") int uid) {
        return orderService.getOrderByUid(uid);
    }

    @CrossOrigin
    @PostMapping("/api/delOrder")
    public void delOrder(@RequestParam("id") int oid) {
        orderService.delOrder(oid);
    }

    @CrossOrigin
    @PostMapping("/api/updateOrder")
    public void updateByAid(@RequestBody Order order) {
        orderService.updateByAid(order.getId(), order.getAid());
    }

    @CrossOrigin
    @PostMapping("/api/updatePayment")
    public void updatePayment(@RequestParam("id") int id) {
        orderService.updatePayment(id);
    }

    @CrossOrigin
    @PostMapping("/api/updateRefund")
    public void updateRefund(@RequestParam("id") int id) {
        orderService.updateRefund(id);
    }

    @CrossOrigin
    @PostMapping("/api/finish")
    public void updateFinish(@RequestParam("id") int id) {
        orderService.updateFinish(id);
    }

    @CrossOrigin
    @PostMapping("/api/paidAll")
    public void updatePaidAll(@RequestParam("id") int id) {
        orderService.updatePaidAll(id);
    }

    @CrossOrigin
    @GetMapping("/api/getAllOrders")
    public List<Order> getAllOrders() {
        return orderService.list();
    }

    @CrossOrigin
    @GetMapping("/api/getAllDelivers")
    public List<Order> getAllDelivers() {
        return orderService.deliverList();
    }
    @CrossOrigin
    @GetMapping("/api/getAllRefunds")
    public List<Order> getAllRefund() {
        return orderService.refundList();
    }
    @CrossOrigin
    @PostMapping("/api/deliverOrder")
    public void deliverOrder(@RequestParam("id") int id) {
        orderService.deliverOrder(id);
    }
    @CrossOrigin
    @PostMapping("/api/refundOrder")
    public void refundOrder(@RequestParam("id") int id) {
        orderService.updateFinish(id); //这里操作是一样的
    }
}

