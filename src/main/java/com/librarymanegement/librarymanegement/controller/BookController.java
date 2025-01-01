package com.librarymanegement.librarymanegement.controller;





import com.librarymanegement.librarymanegement.entity.Book;

import com.librarymanegement.librarymanegement.services.Book.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> addBook(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("quantity") int quantity,
            @RequestParam(value = "bookImage", required = false) MultipartFile bookImage
    ) throws IOException {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setQuantity(quantity);

        if (bookImage != null && !bookImage.isEmpty()) {
            String imagePath = bookService.storeBookImage(bookImage);
            book.setBookImage(imagePath);
        }

        return ResponseEntity.ok(bookService.addBook(book));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "bookImage", required = false) MultipartFile bookImage
    ) throws IOException {
        Optional<Book> updatedBook = bookService.updateBook(id, title, author, quantity, bookImage);
        return updatedBook.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
