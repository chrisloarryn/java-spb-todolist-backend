package personclient.business.dto.requests.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import personclient.entities.enums.Priority;
import personclient.entities.enums.State;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientRequest {
    @NonNull
    @JsonProperty("nombre")
    private String name;

    @NonNull
    @JsonProperty("clienteid")
    private String client_id;

    @NonNull
    @JsonProperty("contrasena")
    private String password;

    @NonNull
    @JsonProperty("genero")
    private String gender;

    @JsonProperty("edad")
    private int age;

    @JsonProperty("identificacion")
    private String email_identifier;

    @JsonProperty("direccion")
    private String address;

    @JsonProperty("telefono")
    private String phone_number;

    @JsonProperty("estado")
    private String status;

    @JsonProperty("fecha_creacion")
    private Date createdAt;
}
