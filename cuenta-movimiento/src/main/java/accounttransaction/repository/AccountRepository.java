package accounttransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import accounttransaction.entities.Account;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID>
{
}