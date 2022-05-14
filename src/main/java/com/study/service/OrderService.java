package com.study.service;

import com.study.model.domain.*;
import com.study.model.domain.item.Item;
import com.study.repository.CartRepository;
import com.study.repository.ItemRepository;
import com.study.repository.MemberRepository;
import com.study.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findById(memberId);
        Item item = itemRepository.findById(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId) {
        // 엔티티 조회
        Order order = orderRepository.findById(orderId);
        // 주문 취소
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByQuery(orderSearch);
    }

    @Transactional
    public Long orderItemsInCarts(long memberId, Long[] cartIds) {
        // 엔티티 조회
        Member member = memberRepository.findById(memberId);
        List<Cart> cartList = Arrays.stream(cartIds)
                .map(cartRepository::findById)
                .collect(Collectors.toList());

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        List<OrderItem> orderItemList = new ArrayList<>();
        for (Cart cart : cartList) {
            Item item = cart.getItem();
            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), cart.getQuantity());
            orderItemList.add(orderItem);
        }

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItemList.toArray(new OrderItem[0]));

        // 주문 저장
        orderRepository.save(order);
        return order.getId();
    }
}
