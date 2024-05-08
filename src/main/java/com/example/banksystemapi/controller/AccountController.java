package com.example.banksystemapi.controller;

import com.example.banksystemapi.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.banksystemapi.services.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto request) {
        String name = request.getName();
        double amount = request.getAmount();

        AccountDto createdAccount = accountService.createAccount(name, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }



    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") Integer id) {
        AccountDto account = accountService.findAccountById(id);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable("id") Integer id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.noContent().build();
    }
}
