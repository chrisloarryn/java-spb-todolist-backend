package personclient.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;
import personclient.entities.enums.AccountType;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Table(name = "accounts", schema = "public")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonProperty("numero")
    @NotEmpty(message = "Numero is required")
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @JsonProperty("saldoinicial")
    @Column(name = "initial_balance", nullable = false)
    private Double initialBalance;

    @JsonProperty("status")
    @Column(name = "status", nullable = false)
    private Boolean status;

    @JsonProperty("tipo")
    @NotEmpty(message = "Tipo is required")
    @Column(name = "account_type", nullable = false)
    private AccountType accountType = AccountType.Ahorro;

    @JsonProperty("persona")
    @NotEmpty(message = "Persona is required")
    @Column(name = "person_id", nullable = false)
    private UUID personId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
}
