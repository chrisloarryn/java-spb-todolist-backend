package accounttransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import accounttransaction.entities.Account;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findByPersonId(UUID personId);
}