package com.study.controller;

import com.study.model.domain.item.Book;
import com.study.model.domain.item.Item;
import com.study.model.form.BookForm;
import com.study.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    public String itemList(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/new")
    public String item(Model model) {
        model.addAttribute("bookForm", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String createBook(@Valid BookForm form, BindingResult result) {
        if (result.hasErrors()) return "items/createItemForm";

        Book book = Book.createBook(form);
        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findById(itemId);
        BookForm bookForm = BookForm.updateFormPreprocess(item);
        model.addAttribute("bookForm", bookForm);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, BookForm bookForm) {
        itemService.updateBook(itemId, bookForm);
        return "redirect:/items";
    }
}
