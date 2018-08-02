package com.alishop.controller;

import com.alishop.dto.ProductDTO;
import com.alishop.entity.Product;
import com.alishop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

        @Autowired
        ProductService productService;

        private final int SIZE = 8;

        @RequestMapping("/")
        protected String redirectToHome() {
            return "redirect:home";
        }

        @GetMapping("/home")
        protected String showHomePage(Model model, HttpServletRequest request, HttpSession session) throws Exception {
            String host = request.getHeader("Host");
            String protocol = request.getScheme();
            String context = request.getContextPath();
            session.setAttribute("host", protocol + "://" + host + context);
            List<ProductDTO> maleProducts = productService.getMaleProducts(1,4);
            List<ProductDTO> femaleProducts = productService.getFeMaleProducts(1,4);
            model.addAttribute("maleProducts",maleProducts);
            model.addAttribute("femaleProducts",femaleProducts);
            return "index";
        }

        @GetMapping("/error")
        protected String showErrorPage() {
            return "error";
        }


}
