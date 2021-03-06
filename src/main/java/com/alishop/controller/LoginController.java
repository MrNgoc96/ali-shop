package com.alishop.controller;


import com.alishop.dto.AccountDTO;
import com.alishop.entity.Account;
import com.alishop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    AccountService accountService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("account", new Account());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes ra) {
        AccountDTO account = accountService.login(username, password);
        if (account == null) {
            ra.addFlashAttribute("message", "Tên đăng nhập hoặc mật khẩu không chính xác !");
            return "redirect:login";
        }
        session.setAttribute("currentUser", account);
        if (account.getTypeUser().equals("Admin")) {
            ra.addFlashAttribute("message", "Chào mừng Admin <b>" + account.getName() + "</b> đã đăng nhập !");
            return "redirect:shop-management";
        } else {
            ra.addFlashAttribute("message", "Chào mừng Khách hàng <b>" + account.getName() + "</b> đã đăng nhập !");
            return "redirect:home";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("currentUser", null);
        return "redirect:home";
    }

    @PostMapping("register")
    public String register(@ModelAttribute AccountDTO account, Model model) {
        if (accountService.saveAccount(account) != null) {
            model.addAttribute("message", "Chúc mừng bạn đã đăng ký thành công !");
        } else {
            model.addAttribute("message", "Đăng ký thất bại, bạn vui lòng thử lại !");
        }
        return "login";
    }

}
