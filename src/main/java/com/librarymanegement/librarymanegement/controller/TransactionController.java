package com.librarymanegement.librarymanegement.controller;



import com.librarymanegement.librarymanegement.entity.Transaction;

import com.librarymanegement.librarymanegement.services.TransactionService.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/lend")
    public ResponseEntity<Transaction> lendBook(@RequestBody Transaction transaction) {
        try {
            Transaction createdTransaction = transactionService.lendBook(transaction);
            return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return/{transactionId}")
    public ResponseEntity<Transaction> returnBook(@PathVariable Long transactionId) {
        try {
            Transaction updatedTransaction = transactionService.returnBook(transactionId);
            return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
