package accounttransaction.api.controllers;

import accounttransaction.business.abstracts.AccountService;
import accounttransaction.business.abstracts.MovementService;
import accounttransaction.business.abstracts.ReportService;
import accounttransaction.business.dto.requests.create.CreateMovementRequest;
import accounttransaction.business.dto.requests.update.UpdateMovementRequest;
import accounttransaction.business.dto.responses.create.CreateMovementResponse;
import accounttransaction.business.dto.responses.get.GetAccountResponse;
import accounttransaction.business.dto.responses.get.GetAllMovementsResponse;
import accounttransaction.business.dto.responses.get.GetMovementResponse;
import accounttransaction.business.dto.responses.get.GetReportResponse;
import accounttransaction.business.dto.responses.update.UpdateMovementResponse;
import accounttransaction.exceptions.InsuficientBalanceException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService service;


    // query param: fecha (requerido)
    // query param: cliente (requerido)
    // account report cuentas con saldos, detalle de los movimientos.
    @GetMapping
    public GetReportResponse getAll(
            @RequestParam("fecha") String fecha,
            @RequestParam("cliente") String cliente
    ) {
        return service.getReport(fecha, UUID.fromString(cliente));
    }
}
