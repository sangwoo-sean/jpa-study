package com.study.repository;

import com.study.model.domain.Cart;
import com.study.model.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final EntityManager em;

    public Long save(Cart cart) {
        em.persist(cart);
        return cart.getId();
    }

    public List<Cart> findByMemberId(Long memberId) {
        return em.createQuery("select c from Cart c " +
                        " join fetch c.item i" +
                        " where c.member.id = :memberId", Cart.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<Cart> findByMemberIdAndItemId(Long memberId, Long itemId) {
        return em.createQuery("select c from Cart c" +
                        " where c.member.id = :memberId" +
                        " and c.item.id = :itemId", Cart.class)
                .setParameter("memberId", memberId)
                .setParameter("itemId", itemId)
                .getResultList();
    }

    public void deleteCartById(Long cartId) {
        em.createQuery("delete from Cart c where c.id = :cartId")
                .setParameter("cartId", cartId)
                .executeUpdate();
    }

    public Cart findById(Long cartId) {
        return em.find(Cart.class, cartId);
    }
}
