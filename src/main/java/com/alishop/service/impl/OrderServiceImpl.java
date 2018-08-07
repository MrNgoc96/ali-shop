package com.alishop.service.impl;

import com.alishop.bases.TransformUtils;
import com.alishop.dto.AccountDTO;
import com.alishop.entity.Account;
import com.alishop.entity.Order;
import com.alishop.entity.OrderDetail;
import com.alishop.entity.Product;
import com.alishop.repository.AccountRepository;
import com.alishop.repository.OrderDetailRepository;
import com.alishop.repository.OrderRepository;
import com.alishop.repository.ProductRepository;
import com.alishop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TransformUtils<Order, Order> transformUtils;

    @Override
    public List<Order> getOrders(int page, int size) {
        return null;
    }

    @Override
    public Order getOrder(int id) {
        return orderRepository.getOne(id);
    }

    @Override
    public Order saveOrder(Order order, String clientUsername) {
        Account account = accountRepository.getOne(clientUsername);
        order.setAccount(account);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order orderDTO, int id) {
        return orderRepository.save(orderDTO);
    }

    @Override
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDetail saveOrderDetail(int orderId, int productId, int quantity) {
        Order order = getOrder(orderId);
        Product product = productRepository.getOne(productId);
        OrderDetail orderDetail = new OrderDetail(order, product, quantity);
        return orderDetailRepository.save(orderDetail);
    }


}
