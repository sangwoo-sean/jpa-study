package com.study.model.domain;

import com.study.model.domain.item.Book;
import com.study.model.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Cart createNewCart(Long itemId, Long memberId, int quantity) {
        Cart cart = new Cart();

        Item item = new Book();
        item.setId(itemId);
        cart.setItem(item);

        Member member = new Member();
        member.setId(memberId);
        cart.setMember(member);

        cart.quantity = quantity;
        return cart;
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }
}
