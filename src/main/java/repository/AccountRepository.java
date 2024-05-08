package repository;

import model.Account;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;

public interface AccountRepository extends JpaAttributeConverter<Account, Integer> {
    Account createAccount(String name, double amount);
    Account findById(Integer id);
    void deleteById(Integer id);
}
