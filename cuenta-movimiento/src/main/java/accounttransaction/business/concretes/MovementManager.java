package accounttransaction.business.concretes;


import accounttransaction.business.abstracts.MovementService;
import accounttransaction.business.dto.requests.create.CreateMovementRequest;
import accounttransaction.business.dto.requests.update.UpdateAccountRequest;
import accounttransaction.business.dto.requests.update.UpdateMovementRequest;
import accounttransaction.business.dto.responses.create.CreateMovementResponse;
import accounttransaction.business.dto.responses.get.GetAllMovementsResponse;
import accounttransaction.business.dto.responses.get.GetMovementResponse;
import accounttransaction.business.dto.responses.update.UpdateMovementResponse;
import accounttransaction.business.rules.AccountBusinessRules;
import accounttransaction.entities.AccountNotFoundException;
import accounttransaction.entities.Movement;
import accounttransaction.exceptions.BadRequestException;
import accounttransaction.repository.MovementRepository;
import accounttransaction.utils.mappers.ModelMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MovementManager implements MovementService {

    private final MovementRepository repo;
    private final ModelMapperService mapper;
    private final AccountBusinessRules rules;

    @Override
    public List<GetAllMovementsResponse> getAll() {
        var todos = repo.findAll();
        return todos
                .stream()
                .map(todo -> mapper.forResponse().map(todo, GetAllMovementsResponse.class))
                .toList();
    }

    @Override
    public GetMovementResponse getById(UUID id) {
        if (!repo.existsById(id)) {
            throw new AccountNotFoundException(
                    "Transaction with id " + id + " does not exists");
        }
        var todo = repo.findById(id).orElseThrow();
        return mapper.forResponse().map(todo, GetMovementResponse.class);
    }

    @Override
    public CreateMovementResponse add(CreateMovementRequest todoRequest) {
        try {
            var todo = mapper.forRequest().map(todoRequest, Movement.class);
            var createdTodo = repo.save(todo);
            return mapper.forResponse().map(createdTodo, CreateMovementResponse.class);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage(), e.getCode());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public UpdateMovementResponse update(UUID id, UpdateMovementRequest todoRequest) {
        if (!repo.existsById(id)) {
            throw new AccountNotFoundException(
                    "Transaction with id " + id + " does not exists");
        }
        var todo = mapper.forRequest().map(todoRequest, Movement.class);
        todo.setId(id);
        repo.save(todo);
        return mapper.forResponse().map(todo, UpdateMovementResponse.class);
    }

    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new AccountNotFoundException(
                    "Transaction with id " + id + " does not exists");
        }
        repo.deleteById(id);
    }
}
