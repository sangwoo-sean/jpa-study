package com.study.model.form;

import com.study.model.domain.item.Book;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BookForm {

    private Long id;

    @NotEmpty(message = "상품 이름은 필수입니다.")
    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    public static BookForm updateFormPreprocess(Book book) {
        BookForm bookForm = new BookForm();
        bookForm.id = book.getId();
        bookForm.name = book.getName();
        bookForm.price = book.getPrice();
        bookForm.stockQuantity = book.getStockQuantity();
        bookForm.author = book.getAuthor();
        bookForm.isbn = book.getIsbn();
        return bookForm;
    }
}
