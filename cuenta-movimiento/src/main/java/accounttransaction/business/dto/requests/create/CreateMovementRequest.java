package accounttransaction.business.dto.requests.create;

import accounttransaction.entities.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovementRequest {
    @JsonProperty("tipo")
    @NonNull
    private AccountType transactionType = AccountType.Ahorro;

    @JsonProperty("numerocuenta")
    @NonNull
    private String accountNumber;

    // @JsonProperty("saldoinicial")
    // @NonNull
    @JsonIgnore
    private Double initialBalance;

    @JsonProperty("valormovimiento")
    @NonNull
    private Double transactionValue;

    @JsonProperty("estado")
    private Boolean status = true;
}
