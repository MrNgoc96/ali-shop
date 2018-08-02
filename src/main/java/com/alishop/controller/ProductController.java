package com.alishop.controller;

import com.alishop.bases.BaseUtils;
import com.alishop.dto.ProductDTO;
import com.alishop.entity.Product;
import com.alishop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author longoc
 */
@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    private final int PAGE_SIZE = 12;

    /**
     * Map list scope price
     *
     * @return
     */
    public HashMap<String, String> listScopePrice() {
        HashMap<String, String> list = new HashMap<>();
        list.put("100000/300000", "100.000 đồng - 300.000 đồng");
        list.put("300000/500000", "300.000 đồng - 500.000 đồng");
        list.put("500000/1000000", "500.000 đồng - 1.000.000 đồng");
        list.put("1000000/1500000", "1.000.000 đồng - 1.500.000 đồng");
        list.put("1500000/5000000", "Lớn hơn 1.500.000 đồng");
        return list;
    }

    /**
     * Show product detail
     *
     * @return
     */
    @GetMapping(value = "/products/{id}")
    public String showProductDetail(@PathVariable int id, Model model) {
        ProductDTO product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "product";
    }

    /**
     * Show list products
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/products")
    public String showProducts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String sortType,
                               @RequestParam(defaultValue = "") String scopePrice, Model model) {
        List<ProductDTO> products = productService.getProductPrice(scopePrice, sortType, page, PAGE_SIZE);
        int totalProduct = productService.countNumberProducts(scopePrice);
        int totalPages = BaseUtils.countTotalPage(totalProduct, PAGE_SIZE);
        model.addAttribute("products", products);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("listScopePrice", BaseUtils.sortByValues(listScopePrice(), true));
        return "products";
    }


}
