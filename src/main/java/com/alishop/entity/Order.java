package com.alishop.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;
    @Column(name = "order_address")
    private String address;
    private String status;
    @Temporal(TemporalType.DATE)
    @Column(name = "order_date")
    private Date orderDate;
    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;
    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    public Order() {
        // TODO Auto-generated constructor stub
    }

    public Order(String address, String status, Date orderDate, Account account) {
        this.address = address;
        this.status = status;
        this.orderDate = orderDate;
        this.account = account;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return new ArrayList<OrderDetail>(orderDetails);
    }


    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(orderDate);
    }

    public int getStatusId(){
        if(status.contains("Đang đặt hàng")){
            return 1;
        }else if(status.contains("Đang giao hàng")){
            return 2;
        }else if(status.contains("Đã giao hàng")){
            return 3;
        }else if(status.contains("Yêu cầu hủy")){
            return 4;
        }else {
            return 0;
        }
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

    public double getTotalPrice(){
        double total = 0;
        for (OrderDetail od : getOrderDetails()){
            total += od.getProduct().getPrice() * od.getQuantity();
        }
        return total;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


}
