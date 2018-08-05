package com.alishop.dto;

import java.util.Date;

public class OrderDTO {

    private int id;
    private String address;
    private String status;
    private Date orderDate;
    private String username;
    public OrderDTO() {
    }

    public OrderDTO(String address, String status, Date orderDate,String username) {
        this.id = id;
        this.address = address;
        this.status = status;
        this.orderDate = orderDate;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
