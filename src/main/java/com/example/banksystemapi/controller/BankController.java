package com.example.banksystemapi.controller;

import com.example.banksystemapi.dto.BankDto;
import com.example.banksystemapi.model.Bank;
import com.example.banksystemapi.services.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/banks")
@RequiredArgsConstructor
public class BankController {
    private final BankService bankService;
    @PostMapping("/")
    public ResponseEntity<Bank> createBank(@RequestBody BankDto bankDTO) {
        Bank bank = bankService.createBank(bankDTO.getName(), bankDTO.getTransactionPercentFeeValue());
        return new ResponseEntity<>(bank, HttpStatus.CREATED);
    }
}
