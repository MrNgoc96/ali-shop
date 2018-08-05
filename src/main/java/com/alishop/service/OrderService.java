package com.alishop.service;


import com.alishop.dto.OrderDTO;
import com.alishop.dto.OrderDetailDTO;
import com.alishop.entity.OrderDetail;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getOrders(int page, int size);

    OrderDTO getOrder(int id);

    OrderDTO saveOrder(OrderDTO orderDTO);

    boolean updateOrder(OrderDTO order, int id);

    void deleteOrder(int id);

    boolean saveOrderDetail(OrderDetailDTO orderDetailDTO);


}
