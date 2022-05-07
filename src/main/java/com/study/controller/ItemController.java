package com.study.controller;

import com.study.model.domain.item.Book;
import com.study.model.form.BookForm;
import com.study.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

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
}
