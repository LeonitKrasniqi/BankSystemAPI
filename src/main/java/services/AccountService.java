package services;

import dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Account;
import org.springframework.stereotype.Service;
import repository.AccountRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService  {
    private final AccountRepository accountRepository;

    public AccountDto createAccount(AccountDto accountDto) {
        Account account = convertToEntity(accountDto);
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
