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
    public AccountDto createAccount(AccountDto accountDto) throws Exception {
        try {
            Optional<Account> existingAccount = accountRepository.findById(accountDto.getId());
            if (existingAccount.isPresent()) {
                throw new IllegalArgumentException("Account with ID " + accountDto.getId() + " already exists");
            }

            Account account = convertToEntity(accountDto);

            account = accountRepository.save(account);

            return convertToDto(account);
        } catch (Exception ex) {
            throw new Exception("Failed to create account: " + ex.getMessage());
        }
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

    public Account convertToEntity(AccountDto accountDto) {
        return new Account(accountDto.getId(), accountDto.getName(), accountDto.getAmount());
    }

}
