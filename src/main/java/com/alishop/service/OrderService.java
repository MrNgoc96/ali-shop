package com.alishop.service;


import com.alishop.dto.AccountDTO;
import com.alishop.entity.Order;
import com.alishop.entity.OrderDetail;

import java.util.List;

public interface OrderService {

    List<Order> getOrders(int page, int size);

    Order getOrder(int id);

    Order saveOrder(Order orderDTO, String clientUsername);

    Order updateOrder(Order order, int id);

    void deleteOrder(int id);

    OrderDetail saveOrderDetail(int orderId,int productId, int quantity);


}
