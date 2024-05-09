package com.example.banksystemapi.controller;

import com.example.banksystemapi.dto.AccountDto;
import com.example.banksystemapi.model.Account;
import com.example.banksystemapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.banksystemapi.services.AccountService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final AccountRepository accountRepository;


    @PostMapping("/")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) throws Exception {
        try {
            AccountDto createdAccount = accountService.createAccount(accountDto);
            return ResponseEntity.ok(createdAccount);
        } catch (Exception e) {

           throw  new Exception("Failed to create account: "+ e.getMessage());
        }
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
    public ResponseEntity<String> deleteAccountById(@PathVariable("id") Integer id) {
        try {
            Optional<Account> optionalAccount = accountRepository.findById(id);
            if (optionalAccount.isPresent()) {
                accountService.deleteAccountById(id);
                return ResponseEntity.ok("Account with ID " + id + " deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account with ID " + id + " does not exist");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete account: " + e.getMessage());
        }
    }


}
