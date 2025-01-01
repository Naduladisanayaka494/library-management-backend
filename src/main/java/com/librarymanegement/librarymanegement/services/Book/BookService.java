package com.librarymanegement.librarymanegement.services.Book;




import com.librarymanegement.librarymanegement.entity.Book;
import com.librarymanegement.librarymanegement.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    // Path to store uploaded files (Update this path based on your server configuration)
    private final Path uploadDir = Paths.get("uploads/books");

    public BookService(BookRepository bookRepository) throws IOException {
        this.bookRepository = bookRepository;
        // Ensure upload directory exists
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> updateBook(Long bookId, String title, String author, Integer quantity, MultipartFile bookImage) throws IOException {
        return bookRepository.findById(bookId).map(existingBook -> {
            if (title != null) existingBook.setTitle(title);
            if (author != null) existingBook.setAuthor(author);
            if (quantity != null) existingBook.setQuantity(quantity);

            if (bookImage != null && !bookImage.isEmpty()) {
                try {
                    String imagePath = storeBookImage(bookImage);
                    existingBook.setBookImage(imagePath);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to store book image", e);
                }
            }

            return bookRepository.save(existingBook);
        });
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public String storeBookImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;
        Path filePath = uploadDir.resolve(uniqueFilename);

        Files.copy(file.getInputStream(), filePath);

        return filePath.toString(); // Save this path in the database
    }
}
