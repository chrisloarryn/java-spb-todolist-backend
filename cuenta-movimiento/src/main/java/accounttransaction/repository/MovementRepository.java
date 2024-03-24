package accounttransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import accounttransaction.entities.Movement;

import java.util.UUID;

public interface MovementRepository extends JpaRepository<Movement, UUID>
{
}