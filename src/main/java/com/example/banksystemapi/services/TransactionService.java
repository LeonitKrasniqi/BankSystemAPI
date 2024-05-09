package com.example.banksystemapi.services;

import com.example.banksystemapi.dto.AccountDto;
import com.example.banksystemapi.dto.BankDto;
import com.example.banksystemapi.dto.TransactionDto;
import com.example.banksystemapi.model.Account;
import com.example.banksystemapi.model.Bank;
import com.example.banksystemapi.model.Transaction;
import com.example.banksystemapi.repository.AccountRepository;
import com.example.banksystemapi.repository.BankRepository;
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
    private final BankRepository bankRepository;
    private final BankService bankService;


    public void transferMoney(TransactionDto transactionDto) throws Exception{


        AccountDto originatingAccount = accountService.findAccountById(transactionDto.getOriginatingAccountId());
        AccountDto resultingAccount = accountService.findAccountById(transactionDto.getResultingAccountId());
        BankDto bank = bankService.getBank(transactionDto.getBankId());

        double amountWithFee = getAmountWithFee(bank, transactionDto.getAmount(), false);

        if (originatingAccount.getAmount() < amountWithFee) {
            throw new IllegalArgumentException("Insufficient funds in originating account");
        }

        if (transactionDto.getOriginatingAccountId().equals(transactionDto.getResultingAccountId())) {
            throw new IllegalArgumentException("Cannot transfer money from and to the same account");
        }

        if (transactionDto.getAmount() < 0) {
            throw new IllegalArgumentException("Transaction amount cannot be negative");
        }


        double transferAmount = transactionDto.getAmount();
        originatingAccount.setAmount(originatingAccount.getAmount() - amountWithFee);
        accountRepository.save(accountService.convertToEntity(originatingAccount));


        resultingAccount.setAmount(resultingAccount.getAmount() + transferAmount);
        accountRepository.save(accountService.convertToEntity(resultingAccount));

        Transaction transaction = Transaction.builder()
                .amount(transferAmount)
                .description(transactionDto.getDescription())
                .originatingAccount(convertToEntity(originatingAccount))
                .resultingAccount(convertToEntity(resultingAccount))
                .bank(convertToEntityBank(bank))
                .build();

        transactionRepository.save(transaction);

    }
    public void deposit(TransactionDto transactionDto) throws Exception {
        AccountDto account = accountService.findAccountById(transactionDto.getOriginatingAccountId());
        BankDto bank = bankService.getBank(transactionDto.getBankId());

        // Normally banks don't take money on deposit.
        double amountWithFee = getAmountWithFee(bank, transactionDto.getAmount(), true);

        if (transactionDto.getAmount() < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }

        account.setAmount(account.getAmount() + amountWithFee);
        accountRepository.save(accountService.convertToEntity(account));

        Transaction transaction = Transaction.builder()
                .amount(amountWithFee)
                .description(transactionDto.getDescription())
                .resultingAccount(convertToEntity(account))
                .bank(convertToEntityBank(bank))
                .build();

        transactionRepository.save(transaction);
    }

    public void withdraw(TransactionDto transactionDto) throws Exception {
        AccountDto account = accountService.findAccountById(transactionDto.getOriginatingAccountId());
        BankDto bank = bankService.getBank(transactionDto.getBankId());

        double amountWithFee = getAmountWithFee(bank, transactionDto.getAmount(), false);

        if (transactionDto.getAmount() < 0) {
            throw new IllegalArgumentException("Withdrawal amount cannot be negative");
        }

        if (account.getAmount() < amountWithFee) {
            throw new IllegalArgumentException("Insufficient funds in account");
        }

        account.setAmount(account.getAmount() - amountWithFee);
        accountRepository.save(accountService.convertToEntity(account));

        Transaction transaction = Transaction.builder()
                .amount(amountWithFee)
                .description(transactionDto.getDescription())
                .originatingAccount(accountService.convertToEntity(account))
                .bank(convertToEntityBank(bank))
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
            transactionDtos.add(transactionDto);
        }

        return transactionDtos;
    }

    private static double getAmountWithFee(BankDto bank, double amount, boolean isDeposit) {
        double bankFee = bank.getTransactionPercentFeeValue();
        double transactionFee = amount * (bankFee/100.00);
        if (isDeposit) {
            return amount - transactionFee;

        }
        return amount + transactionFee;
    }

    public Account convertToEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setId(accountDto.getId());
        account.setName(accountDto.getName());
        account.setAmount(accountDto.getAmount());
        return account;
    }

    public Bank convertToEntityBank(BankDto bankDto) {
        Bank bank = new Bank();
        bank.setName(bankDto.getName());
        bank.setTransactionPercentFeeValue(bankDto.getTransactionPercentFeeValue());
        bank.setId(bankDto.getId());
        return bank;
    }



}
