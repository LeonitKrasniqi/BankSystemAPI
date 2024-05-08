package repository;

import model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account createAccount(String name, double amount);
    Account findAccountById(int id);
    void deleteAccountById(int id);
}
