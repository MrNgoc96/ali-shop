package com.alishop.controller;

import com.alishop.cartbean.CartBean;
import com.alishop.cartbean.ProductPTO;
import com.alishop.dto.AccountDTO;
import com.alishop.dto.OrderDTO;
import com.alishop.dto.OrderDetailDTO;
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
    public String showPaymentPage(HttpSession session,RedirectAttributes ra) {
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

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setAddress(address);
        orderDTO.setOrderDate(new Date());
        orderDTO.setUsername(accountDTO.getUsername());
        orderDTO.setStatus("Đang đặt hàng");
        orderDTO = orderService.saveOrder(orderDTO);

        for (Map.Entry<Integer, ProductPTO> entry : cartBean.entrySet()) {
            int productId = entry.getValue().getProduct().getId();
            int orderId = orderDTO.getId();
            int quantity = entry.getValue().getQuantity();
            OrderDetailDTO od = new OrderDetailDTO(orderId, productId, quantity);
            orderService.saveOrderDetail(od);
        }

        orderService.saveOrder(orderDTO);
        ra.addFlashAttribute("message", "Chúc mừng bạn vừa thực hiện đặt hàng thành công, chúng tôi sẽ sớm giao hàng đến cho bạn !");
        return "redirect:home";
    }

}
