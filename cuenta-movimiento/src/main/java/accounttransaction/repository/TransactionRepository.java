package accounttransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import accounttransaction.entities.Transaction;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>
{
}