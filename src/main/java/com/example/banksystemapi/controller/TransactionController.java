package com.example.banksystemapi.controller;

import com.example.banksystemapi.dto.TransactionDto;
import com.example.banksystemapi.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransactionDto transactionDto) {
        try {
            transactionService.transferMoney(transactionDto);
            return ResponseEntity.ok("Money transferred successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to transfer money: " + e.getMessage());
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> depositMoney(@RequestBody TransactionDto transactionDto) {
        try {
            transactionService.deposit(transactionDto);
            return ResponseEntity.ok("Deposit successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawMoney(@RequestBody TransactionDto transactionDto) {
        try {
            transactionService.withdraw(transactionDto);
            return ResponseEntity.ok("Withdrawal successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }


}
