package accounttransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import accounttransaction.entities.Movement;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, UUID> {
    Optional<List<Movement>> findByAccountNumber(@NotNull(message = "El número de cuenta no puede ser nulo") String accountNumber);
}