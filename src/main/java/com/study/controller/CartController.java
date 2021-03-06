package com.study.controller;

import com.study.model.dto.CartDto;
import com.study.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartRepository cartRepository;

    @GetMapping("/carts")
    public String cartList(Model model) {
        long memberId = 1L;
        List<CartDto> carts = cartRepository.findByMemberId(memberId).stream()
                .map(CartDto::new)
                .collect(Collectors.toList());
        model.addAttribute("carts", carts);
        return "cart/carts";
    }

    @GetMapping("/cart/{cartId}/delete")
    @Transactional
    public String deleteCart(@PathVariable Long cartId) {
        cartRepository.deleteCartById(cartId);
        return "redirect:/carts";
    }
}
