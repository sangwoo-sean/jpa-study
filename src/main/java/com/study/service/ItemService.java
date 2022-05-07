package com.study.service;

import com.study.model.domain.item.Book;
import com.study.model.domain.item.Item;
import com.study.model.form.BookForm;
import com.study.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Transactional
    public void updateBook(Long itemId, BookForm form) {
        Book book = itemRepository.findBookById(itemId);
        book.update(form);
    }
}
