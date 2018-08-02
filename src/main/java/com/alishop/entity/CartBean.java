package com.alishop.entity;


import java.util.HashMap;

public class CartBean extends HashMap<Integer, ProductPTO> {

    private int totalPrice;

    private int numberItems;

    public void addProduct(ProductPTO productPTO) {
        int key = productPTO.getProduct().getId();
        if (containsKey(key)) {
            int oldQuantity = get(key).getQuantity();
            this.get(key).setQuantity(oldQuantity + 1);
        } else {
            put(key, productPTO);
        }
    }

    public void removeProduct(int productId) {
        if (containsKey(productId)) {
            remove(productId);
        }
    }

    public int getTotalPrice() {
        totalPrice = 0;
        for (int key : this.keySet()) {
            ProductPTO productPTO = this.get(key);
            totalPrice += productPTO.getProduct().getPrice() * productPTO.getQuantity();
        }
        return totalPrice;
    }

    public int getNumberItems() {
        this.numberItems = 0;
        for (int key : this.keySet()) {
            ProductPTO productPTO = this.get(key);
            this.numberItems += productPTO.getQuantity();
        }
        return this.numberItems;
    }

    public void setNumberItems(int numberItems) {
        this.numberItems = numberItems;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
