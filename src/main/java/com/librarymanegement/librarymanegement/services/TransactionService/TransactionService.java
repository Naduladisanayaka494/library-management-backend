package com.librarymanegement.librarymanegement.services.TransactionService;



import com.librarymanegement.librarymanegement.entity.Book;
import com.librarymanegement.librarymanegement.entity.Transaction;
import com.librarymanegement.librarymanegement.repository.BookRepository;
import com.librarymanegement.librarymanegement.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BookRepository bookRepository;

    public TransactionService(TransactionRepository transactionRepository, BookRepository bookRepository) {
        this.transactionRepository = transactionRepository;
        this.bookRepository = bookRepository;
    }

    public Transaction lendBook(Transaction transaction) {
        Book book = bookRepository.findById(transaction.getBook().getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getQuantity() <= 0) {
            throw new RuntimeException("No copies of the book are available");
        }

        // Update book quantity
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        // Set lend date and save transaction
        transaction.setLendDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    public Transaction returnBook(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        Book book = transaction.getBook();

        // Update book quantity
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        // Set return date and update transaction
        transaction.setReturnDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }
}
