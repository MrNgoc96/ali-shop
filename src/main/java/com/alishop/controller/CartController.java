package com.alishop.controller;


import com.alishop.dto.ProductDTO;
import com.alishop.cartbean.CartBean;
import com.alishop.cartbean.ProductPTO;
import com.alishop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ProductService productService;

    private final String CART_VIEW_NAME = "cart";

    @GetMapping("")
    public String viewCart() {
        return CART_VIEW_NAME;
    }

    @GetMapping("/add-to-cart")
    public String addToCart(@RequestParam int productId, @RequestParam(defaultValue = "1") int quantity, HttpSession session) {
        CartBean cartBean = (CartBean) session.getAttribute("cartbean");
        if (cartBean == null) {
            cartBean = new CartBean();
        }
        ProductDTO product = productService.getProduct(productId);
        cartBean.addProduct(new ProductPTO(product, quantity));
        session.setAttribute("cartBean", cartBean);
        return "index";
    }

    @GetMapping("/remove-product")
    public String removeProduct(@RequestParam int productId, HttpSession session) {
        CartBean cartBean = (CartBean) session.getAttribute("cartbean");
        if (cartBean != null) {
            cartBean.removeProduct(productId);
            session.setAttribute("cartBean", cartBean);
        }
        return CART_VIEW_NAME;
    }

    @GetMapping("/update-quantity")
    public String updateQuantity(@RequestParam int productId, @RequestParam int quantity, HttpSession session) {
        CartBean cartBean = (CartBean) session.getAttribute("cartbean");
        ProductPTO productPTO = cartBean.get(productId);
        if (productPTO == null) {
            return CART_VIEW_NAME;
        }
        if (quantity < 1) quantity = 1;
        if (quantity > 5) quantity = 5;
        cartBean.removeProduct(productId);
        productPTO.setQuantity(quantity);
        cartBean.addProduct(productPTO);
        session.setAttribute("cartBean", cartBean);
        return CART_VIEW_NAME;
    }

}
