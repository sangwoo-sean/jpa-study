package com.study.api;

import com.study.model.domain.Cart;
import com.study.model.dto.ApiResult;
import com.study.model.dto.OrderDto;
import com.study.repository.OrderRepository;
import com.study.service.CartService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    @GetMapping("/api/orders")
    public ApiResult findAllWithMemberDelivery() {
        List<OrderDto> orders = orderRepository.findAllWithMemberDelivery().stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
        return new ApiResult(orders);
    }

    @PutMapping("/api/cart")
    public ApiResult addCart(@RequestBody CreateCartRequest request) {
        Cart cart = Cart.createNewCart(request.getItemId(), request.getMemberId(), request.getQuantity());
        Long cartId = cartService.save(cart);
        return new ApiResult(cartId);
    }

    @Data
    private static class CreateCartRequest {
        private Long memberId;
        private Long itemId;
        private int quantity;
    }
}