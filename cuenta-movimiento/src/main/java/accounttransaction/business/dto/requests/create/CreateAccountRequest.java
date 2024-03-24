package accounttransaction.business.dto.requests.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import accounttransaction.entities.enums.AccountType;
import accounttransaction.entities.enums.Priority;
import accounttransaction.entities.enums.State;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
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
