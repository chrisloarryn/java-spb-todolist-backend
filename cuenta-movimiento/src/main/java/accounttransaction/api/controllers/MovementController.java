package accounttransaction.api.controllers;

import accounttransaction.business.abstracts.MovementService;
import accounttransaction.business.dto.requests.create.CreateMovementRequest;
import accounttransaction.business.dto.requests.update.UpdateMovementRequest;
import accounttransaction.business.dto.responses.create.CreateMovementResponse;
import accounttransaction.business.dto.responses.get.GetAllMovementsResponse;
import accounttransaction.business.dto.responses.get.GetMovementResponse;
import accounttransaction.business.dto.responses.update.UpdateMovementResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/movimientos")
public class MovementController {
    private final MovementService service;

    @GetMapping
    public List<GetAllMovementsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetMovementResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateMovementResponse add(@RequestBody @NotNull @Validated CreateMovementRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateMovementResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateMovementRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
