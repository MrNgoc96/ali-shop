package com.alishop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop-management")
public class AdminController {

    public String showManagementPage() {
        return "management";
    }

}
