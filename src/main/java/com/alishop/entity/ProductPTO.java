package com.alishop.entity;


import com.alishop.dto.ProductDTO;

public class ProductPTO {

    private ProductDTO product;

    private int quantity;

    public ProductPTO(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
