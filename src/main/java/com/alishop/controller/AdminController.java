package com.alishop.controller;

import com.alishop.bases.BaseUtils;
import com.alishop.dto.ProductDTO;
import com.alishop.service.OrderService;
import com.alishop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/shop-management")
public class AdminController {

    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    final int PAGE_SIZE = 5;

    @GetMapping
    public String showManagementPage() {
        return "redirect:shop-management/products";
    }

    @GetMapping("/products")
    public String showManagementProductPage(Model model, @RequestParam(defaultValue = "1") int page) {
        List<ProductDTO> products = productService.getProducts(page, PAGE_SIZE);
        int numberProducts = productService.countNumberProducts("");
        int totalPages = BaseUtils.countTotalPage(numberProducts,PAGE_SIZE);
        model.addAttribute("products", products);
        model.addAttribute("totalPages",totalPages);
        return "management";
    }


}
