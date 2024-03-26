package accounttransaction.business.dto.responses.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetMovementResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("numerocuenta")
    private String accountNumber;

    @JsonProperty("tipo")
    private String transactionType;

    @JsonProperty("saldoinicial")
    private Double initialBalance;

    @JsonProperty("valormovimiento")
    private Double transactionValue;

    @JsonProperty("estado")
    private Boolean status;

    @JsonProperty("detalle")
    private String detail;

    @JsonProperty("fecha_creacion")
    @CreationTimestamp
    private Date createdAt;
}
