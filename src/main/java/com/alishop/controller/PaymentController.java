package com.alishop.controller;

import com.alishop.cartbean.CartBean;
import com.alishop.cartbean.ProductPTO;
import com.alishop.dto.AccountDTO;
import com.alishop.dto.OrderDTO;
import com.alishop.dto.OrderDetailDTO;
import com.alishop.dto.ProductDTO;
import com.alishop.entity.Product;
import com.alishop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    OrderService orderService;

    @GetMapping
    public String showPaymentPage() {
        return "payment";
    }

    @PostMapping
    public String payment(@RequestParam String name, @RequestParam String phone,
                          @RequestParam String email, @RequestParam String address,
                          @SessionAttribute CartBean cartBean,
                          @SessionAttribute AccountDTO currentUser,
                            RedirectAttributes ra) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setAddress(address);
        orderDTO.setOrderDate(new Date());
        orderDTO.setUsername(currentUser.getUsername());
        orderDTO = orderService.saveOrder(orderDTO);

        for (Map.Entry<Integer, ProductPTO> entry : cartBean.entrySet()) {
            int productId = entry.getValue().getProduct().getId();
            int orderId = orderDTO.getId();
            int quantity = entry.getValue().getQuantity();
            OrderDetailDTO od = new OrderDetailDTO(orderId, productId, quantity);
            orderService.saveOrderDetail(od);
        }

        orderService.saveOrder(orderDTO);
        ra.addFlashAttribute("message","Chúc mừng bạn vừa thực hiện đặt hàng thành công, chúng tôi sẽ sớm giao hàng đến cho bạn !");
        return "redirect:home";
    }

}
