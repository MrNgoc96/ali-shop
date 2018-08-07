package com.alishop.controller;

import com.alishop.cartbean.CartBean;
import com.alishop.cartbean.ProductPTO;
import com.alishop.dto.AccountDTO;
import com.alishop.entity.Order;
import com.alishop.entity.OrderDetail;
import com.alishop.service.AccountService;
import com.alishop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @GetMapping
    public String showPaymentPage(HttpSession session, RedirectAttributes ra) {
        CartBean cartBean = (CartBean) session.getAttribute("cartBean");
        if (cartBean == null || cartBean.size() < 1) {
            ra.addFlashAttribute("message", "Giỏ hàng của bạn đang rỗng, hãy tiếp tục mua hàng đi nào !");
            return "redirect:cart";
        }
        return "payment";
    }

    @PostMapping
    public String payment(@RequestParam String name, @RequestParam String phone,
                          @RequestParam String email, @RequestParam String address,
                          @SessionAttribute CartBean cartBean,
                          HttpSession session,
                          RedirectAttributes ra) {
        AccountDTO accountDTO = (AccountDTO) session.getAttribute("currentUser");

        if (accountDTO == null) {
            accountDTO = new AccountDTO();
            accountDTO.setUsername(email);
            accountDTO.setPassword("12345");
            accountDTO.setAddress(address);
            accountDTO.setPhone(phone);
            accountDTO.setEmail(email);
            accountDTO.setAge(18);
            accountDTO.setTypeUser("KH");
            accountDTO.setGender("Nam");
            accountDTO = accountService.saveAccount(accountDTO);
        }

        Order order = new Order();
        order.setAddress(address);
        order.setOrderDate(new Date());
        order.setStatus("Đang đặt hàng");
        order = orderService.saveOrder(order, accountDTO.getUsername());

        for (Map.Entry<Integer, ProductPTO> entry : cartBean.entrySet()) {
            int productId = entry.getValue().getProduct().getId();
            int orderId = order.getId();
            int quantity = entry.getValue().getQuantity();
            orderService.saveOrderDetail(orderId,productId,quantity);
        }

        session.setAttribute("cartBean",null);
        ra.addFlashAttribute("message", "Chúc mừng bạn vừa thực hiện đặt hàng thành công, chúng tôi sẽ sớm giao hàng đến cho bạn !");
        return "redirect:home";
    }

}
