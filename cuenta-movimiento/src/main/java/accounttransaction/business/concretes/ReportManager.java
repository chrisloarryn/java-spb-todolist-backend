package accounttransaction.business.concretes;


import accounttransaction.business.abstracts.MovementService;
import accounttransaction.business.abstracts.ReportService;
import accounttransaction.business.dto.requests.create.CreateMovementRequest;
import accounttransaction.business.dto.requests.update.UpdateMovementRequest;
import accounttransaction.business.dto.responses.create.CreateMovementResponse;
import accounttransaction.business.dto.responses.get.GetAccountResponse;
import accounttransaction.business.dto.responses.get.GetAllMovementsResponse;
import accounttransaction.business.dto.responses.get.GetClientResponse;
import accounttransaction.business.dto.responses.get.GetMovementResponse;
import accounttransaction.business.dto.responses.get.GetReportResponse;
import accounttransaction.business.dto.responses.update.UpdateMovementResponse;
import accounttransaction.business.rules.AccountBusinessRules;
import accounttransaction.entities.Account;
import accounttransaction.entities.AccountNotFoundException;
import accounttransaction.entities.Movement;
import accounttransaction.exceptions.BadRequestException;
import accounttransaction.exceptions.UnparseableDateException;
import accounttransaction.repository.AccountRepository;
import accounttransaction.repository.MovementRepository;
import accounttransaction.utils.mappers.ModelMapperService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportManager implements ReportService {

    private final MovementRepository repo;
    private final AccountRepository accountRepo;
    private final AccountManager accountManager;
    private final ModelMapperService mapper;
    private final AccountBusinessRules rules;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public GetReportResponse getReport(String fecha, UUID cliente) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(fecha);

            String url = "http://localhost:1203/api/clients/" + cliente;

            GetClientResponse externalAccountDetails = restTemplate.getForObject(url, GetClientResponse.class);

            if (externalAccountDetails == null) {
                throw new AccountNotFoundException("Account with client name " + cliente + " does not exists");
            }

            GetAccountResponse account = accountRepo.findByPersonId(externalAccountDetails.getId())
                    .map(todo -> mapper.forResponse().map(todo, GetAccountResponse.class))
                    .orElseThrow(() -> new AccountNotFoundException("Account with client name " + cliente + " does not exists"));

            List<Movement> movements = repo.findAll().stream()
                    .filter(movement -> movement.getCreatedAt().before(date) && movement.getAccountNumber().equals(account.getAccountNumber()))
                    .collect(Collectors.toList());

            GetAllMovementsResponse allMovements = mapper.forResponse().map(movements, GetAllMovementsResponse.class);

            GetReportResponse report = new GetReportResponse();
            if (!movements.isEmpty()) {
                report.setData(allMovements);
            }
            report.setDate(date);
            report.setResults(movements.size());

            return report;
        } catch (ParseException e) {
            throw new UnparseableDateException("Date format is not valid");
        }
    }
}
