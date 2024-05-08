package com.example.banksystemapi.services;

import com.example.banksystemapi.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.banksystemapi.model.Account;
import org.springframework.stereotype.Service;
import com.example.banksystemapi.repository.AccountRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService  {
    private final AccountRepository accountRepository;

    public AccountDto createAccount(String name, double amount) {
        Account account = Account.builder()
                .name(name)
                .amount(amount)
                .build();
        Account savedAccount = accountRepository.save(account);
        return convertToDto(savedAccount);
    }

    public AccountDto findAccountById(int id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        return optionalAccount.map(this::convertToDto).orElse(null);
    }

    public void deleteAccountById(int id) {
        accountRepository.deleteById(id);
    }

    private AccountDto convertToDto(Account account) {
        return new AccountDto(account.getId(), account.getName(), account.getAmount());
    }

    private Account convertToEntity(AccountDto accountDto) {
        return new Account(accountDto.getId(), accountDto.getName(), accountDto.getAmount());
    }

}
