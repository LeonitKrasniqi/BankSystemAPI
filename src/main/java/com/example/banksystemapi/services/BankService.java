package com.example.banksystemapi.services;

import com.example.banksystemapi.dto.AccountDto;
import com.example.banksystemapi.dto.BankDto;
import com.example.banksystemapi.model.Account;
import com.example.banksystemapi.model.Bank;
import com.example.banksystemapi.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankService {
    private final BankRepository bankRepository;

    public Bank createBank(String name, double transactionPercentFeeValue) {
        if (transactionPercentFeeValue <= 0) {
            throw new IllegalArgumentException("Transaction percent fee value must be greater than zero.");
        }
        Bank bank = new Bank();
        bank.setName(name);
        bank.setTransactionPercentFeeValue(transactionPercentFeeValue);

        return bankRepository.save(bank);
    }

    public BankDto getBank(int id) {
        Optional<Bank> optionalBank = bankRepository.findById(id);
       return optionalBank.map(this::convertToDto).orElse(null);
    }

    private BankDto convertToDto(Bank bank) {
        return new BankDto( bank.getId(),bank.getName(), bank.getTransactionPercentFeeValue());
    }
}

