package accounttransaction.business.dto.requests.update;

import accounttransaction.entities.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonProperty;

import accounttransaction.entities.enums.Priority;
import accounttransaction.entities.enums.State;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountRequest {
    @JsonProperty("numero")
    @NonNull
    private String accountNumber;

    @JsonProperty("saldoinicial")
    @NonNull
    private Double initialBalance;

    @JsonProperty("status")
    @NonNull
    private Boolean status;

    @JsonProperty("tipo")
    @NonNull
    private AccountType accountType = AccountType.Ahorro;

    @JsonProperty("persona")
    @NonNull
    private UUID personId;
}
