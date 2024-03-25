package accounttransaction.business.dto.responses.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
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
@Data
public class GetReportResponse {
    @JsonProperty("fecha")
    private Date date;

    @JsonProperty("resultados")
    private int results;

    @JsonProperty("data")
    private GetAllMovementsResponse data;
}
