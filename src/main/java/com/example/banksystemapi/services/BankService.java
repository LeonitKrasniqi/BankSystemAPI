package com.example.banksystemapi.services;

import com.example.banksystemapi.model.Bank;
import com.example.banksystemapi.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
