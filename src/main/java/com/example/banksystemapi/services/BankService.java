package com.example.banksystemapi.services;

import com.example.banksystemapi.model.Bank;
import com.example.banksystemapi.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;

    public double getTransactionPercentageFee(){
    Bank bank = bankRepository.findById(1).orElseThrow(()->new RuntimeException("Bank not found"));
    return bank.getTransactionPercentFeeValue();
    }

    public void updateTransactionPercentageFee(double percentageFee){
        Bank bank = bankRepository.findById(1).orElseThrow(()->new RuntimeException("Bank not found"));
        bank.setTransactionPercentFeeValue(percentageFee);
        bankRepository.save(bank);
    }
}
