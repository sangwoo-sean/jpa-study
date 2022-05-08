package com.study.api;

import com.study.model.dto.ApiResult;
import com.study.model.dto.OrderDto;
import com.study.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/orders")
    public ApiResult findAllWithMemberDelivery() {
        List<OrderDto> orders = orderRepository.findAllWithMemberDelivery().stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
        return new ApiResult(orders);
    }
}