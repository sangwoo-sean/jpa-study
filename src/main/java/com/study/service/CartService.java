package com.study.service;

import com.study.model.domain.Cart;
import com.study.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    public Long save(Cart cart) {
        List<Cart> cartList = cartRepository.findByMemberIdAndItemId(cart.getMember().getId(), cart.getItem().getId());
        if (cartList.isEmpty()) {
            cartRepository.save(cart);
            return cart.getId();
        } else {
            Cart prev_cart = cartList.get(0);
            prev_cart.addQuantity(cart.getQuantity());
            return prev_cart.getId();
        }
    }
}
