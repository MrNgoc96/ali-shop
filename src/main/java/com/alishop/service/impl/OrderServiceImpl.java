package com.alishop.service.impl;

import com.alishop.bases.TransformUtils;
import com.alishop.dto.OrderDTO;
import com.alishop.dto.OrderDetailDTO;
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
    TransformUtils<Order, OrderDTO> transformUtils;

    @Override
    public List<OrderDTO> getOrders(int page, int size) {
        return null;
    }

    @Override
    public OrderDTO getOrder(int id) {
        Order order = orderRepository.getOne(id);
        return transform(order);
    }

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) {
        Order order = transformUtils.transformReverse(orderDTO, Order.class);
        Account account = accountRepository.getOne(orderDTO.getUsername());
        order.setAccount(account);
        return transformUtils.transform(orderRepository.save(order), OrderDTO.class);
    }

    @Override
    public boolean updateOrder(OrderDTO orderDTO, int id) {
        if (!orderRepository.existsById(id)) return false;
        Order order = transformUtils.transformReverse(orderDTO, Order.class);
        Account account = accountRepository.getOne(orderDTO.getUsername());
        order.setAccount(account);
        return orderRepository.save(order) != null;
    }

    @Override
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    public boolean saveOrderDetail(OrderDetailDTO oddt) {
        Order order = orderRepository.getOne(oddt.getOrderId());
        Product product = productRepository.getOne(oddt.getProductId());
        OrderDetail orderDetail = new OrderDetail(order, product, oddt.getQuantity());
        return orderDetailRepository.save(orderDetail) != null;
    }


    private OrderDTO transform(Order order) {
        OrderDTO orderDTO = transformUtils.transform(order, OrderDTO.class);
        orderDTO.setUsername(order.getAccount().getUsername());
        return orderDTO;
    }


}
