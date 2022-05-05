package com.study.service;

import com.study.exception.NotEnoughStockException;
import com.study.model.domain.item.Book;
import com.study.model.domain.item.Item;
import com.study.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    void 상품등록() {
        //given
        Item item = createBook("책", 5000, 100);

        //when
        Long saveId = itemRepository.save(item);
        Item findItem = itemRepository.findById(saveId);

        //then
        assertEquals(findItem.getName(), item.getName());
    }
    
    @Test
    void 상품_재고_추가() {
        //given
        Item item = createBook("책", 5000, 100);
        itemRepository.save(item);

        //when
        item.addStock(10);
        Item findItem = itemRepository.findById(item.getId());

        //then
        assertEquals(110, findItem.getStockQuantity());
    }

    private Item createBook(String name, int price, int quantity) {
        Item item = new Book();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(quantity);
        return item;
    }

    @Test
    void 상품_재고_감소_정상() {
        //given
        Item item = createBook("책", 5000, 100);
        itemRepository.save(item);

        //when
        item.removeStock(10);
        Item findItem = itemRepository.findById(item.getId());

        //then
        assertEquals(90, findItem.getStockQuantity());
    }

    @Test
    void 상품_재고_감소_재고부족() {
        //given
        Item item = createBook("책", 5000, 5);
        itemRepository.save(item);

        //when

        //then
        assertThrows(NotEnoughStockException.class, () -> {
            item.removeStock(10);
        });
    }
}