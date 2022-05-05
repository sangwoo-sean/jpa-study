package com.study.service;

import com.study.exception.NotEnoughStockException;
import com.study.model.domain.Address;
import com.study.model.domain.Member;
import com.study.model.domain.Order;
import com.study.model.domain.item.Book;
import com.study.model.domain.item.Item;
import com.study.model.enums.OrderStatus;
import com.study.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EntityManager em;

    @Test
    void 상품주문() {
        //given
        Member member = createMember("상우", new Address("서울", "마포구", "03965"));
        Item book = createItem("JPA", 10000, 10);
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findById(orderId);

        assertEquals(OrderStatus.ORDERED, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(10000 * orderCount, getOrder.getTotalPrice());
        assertEquals(8, book.getStockQuantity());
    }

    private Item createItem(String name, int price, int quantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }

    @Test
    void 주문취소() {
        //given
        Member member = createMember("상우", new Address("서울", "마포구", "03965"));
        Item book = createItem("JPA", 10000, 10);

        //when
        int orderCount = 11;

        //then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        });
    }

    @Test
    void 상품주문_재고수량초과() {
        //given
        Member member = createMember("상우", new Address("서울", "마포구", "03965"));
        Item book = createItem("JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findById(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals(10, book.getStockQuantity());
    }
}