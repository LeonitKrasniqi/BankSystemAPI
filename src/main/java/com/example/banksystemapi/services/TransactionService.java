package com.example.banksystemapi.services;

import com.example.banksystemapi.dto.AccountDto;
import com.example.banksystemapi.dto.TransactionDto;
import com.example.banksystemapi.model.Account;
import com.example.banksystemapi.model.Transaction;
import com.example.banksystemapi.repository.AccountRepository;
import com.example.banksystemapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public void transferMoney(TransactionDto transactionDto) throws Exception{


        AccountDto originatingAccount = accountService.findAccountById(transactionDto.getOriginatingAccountId());
        AccountDto resultingAccount = accountService.findAccountById(transactionDto.getResultingAccountId());

        if (transactionDto.getAmount() < 0) {
            throw new IllegalArgumentException("Transaction amount cannot be negative");
        }

        if (originatingAccount.getAmount() < transactionDto.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds in originating account");
        }

        double transferAmount = transactionDto.getAmount();
        originatingAccount.setAmount(originatingAccount.getAmount() - transferAmount);
        accountRepository.save(accountService.convertToEntity(originatingAccount));


        resultingAccount.setAmount(resultingAccount.getAmount() + transferAmount);
        accountRepository.save(accountService.convertToEntity(resultingAccount));

        Transaction transaction = Transaction.builder()
                .amount(transferAmount)
                .description(transactionDto.getDescription())
                .originatingAccount(convertToEntity(originatingAccount))
                .resultingAccount(convertToEntity(resultingAccount))
                .build();

        transactionRepository.save(transaction);

    }
    public void deposit(TransactionDto transactionDto) throws Exception {
        AccountDto account = accountService.findAccountById(transactionDto.getOriginatingAccountId());

        if (transactionDto.getAmount() < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }

        double depositAmount = transactionDto.getAmount();
        account.setAmount(account.getAmount() + depositAmount);
        accountRepository.save(accountService.convertToEntity(account));

        Transaction transaction = Transaction.builder()
                .amount(depositAmount)
                .description(transactionDto.getDescription())
                .resultingAccount(convertToEntity(account))
                .build();

        transactionRepository.save(transaction);
    }

    public void withdraw(TransactionDto transactionDto) throws Exception {
        AccountDto account = accountService.findAccountById(transactionDto.getOriginatingAccountId());

        if (transactionDto.getAmount() < 0) {
            throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        }

        if (account.getAmount() < transactionDto.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds in account");
        }

        double withdrawalAmount = transactionDto.getAmount();
        account.setAmount(account.getAmount() - withdrawalAmount);
        accountRepository.save(accountService.convertToEntity(account));

        Transaction transaction = Transaction.builder()
                .amount(withdrawalAmount)
                .description(transactionDto.getDescription())
                .originatingAccount(accountService.convertToEntity(account))
                .build();

        transactionRepository.save(transaction);
    }


    public List<TransactionDto> getTransactionsByAccountId(Integer accountId) {
        List<Transaction> transactions = transactionRepository.findByOriginatingAccountIdOrResultingAccountId(accountId, accountId);
        List<TransactionDto> transactionDtos = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setAmount(transaction.getAmount());
            transactionDto.setDescription(transaction.getDescription());
            // Set other transaction attributes as needed
            transactionDtos.add(transactionDto);
        }

        return transactionDtos;
    }



    public Account convertToEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setId(accountDto.getId());
        account.setName(accountDto.getName());
        account.setAmount(accountDto.getAmount());
        return account;
    }



}
