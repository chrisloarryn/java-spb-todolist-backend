package accounttransaction.business.concretes;


import accounttransaction.business.abstracts.MovementService;
import accounttransaction.business.abstracts.ReportService;
import accounttransaction.business.dto.requests.create.CreateMovementRequest;
import accounttransaction.business.dto.requests.update.UpdateMovementRequest;
import accounttransaction.business.dto.responses.create.CreateMovementResponse;
import accounttransaction.business.dto.responses.get.GetAccountResponse;
import accounttransaction.business.dto.responses.get.GetAllAccountsResponse;
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
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
            Date startDate = sdf.parse(fecha);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            Date endDate = calendar.getTime();

            String url = "http://cliente-persona:1203/api/clients/" + cliente;

            GetClientResponse externalAccountDetails = restTemplate.getForObject(url, GetClientResponse.class);

            if (externalAccountDetails == null) {
                throw new AccountNotFoundException("Account with client name " + cliente + " does not exists");
            }

            List<Account> accounts = accountRepo.findByPersonId(externalAccountDetails.getId())
                    .orElseThrow(() -> new AccountNotFoundException("No se encontraron cuentas para el cliente con ID " + cliente));

            List<Movement> allMovements = accounts.stream()
                    .flatMap(account -> repo.findByAccountNumber(account.getAccountNumber())
                            .orElseThrow(() -> new AccountNotFoundException("No se encontraron movimientos para la cuenta con número " + account.getAccountNumber()))
                            .stream())
                    .filter(movement -> (movement.getCreatedAt().after(startDate) || movement.getCreatedAt().equals(startDate)) && (movement.getCreatedAt().before(endDate) || movement.getCreatedAt().equals(endDate)))
                    .toList();

            List<GetAllMovementsResponse> mapped = allMovements.stream().map(movement -> mapper.forResponse().map(movement, GetAllMovementsResponse.class)).collect(Collectors.toList());

            GetReportResponse report = new GetReportResponse();
            report.setData(mapped);
            report.setDate(endDate);
            report.setResults(allMovements.size());

            return report;

        } catch (ParseException e) {
            throw new UnparseableDateException("Date format is not valid");
        }
    }
}
