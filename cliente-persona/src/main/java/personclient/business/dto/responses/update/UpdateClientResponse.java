package personclient.business.dto.responses.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import personclient.entities.enums.Priority;
import personclient.entities.enums.State;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("clienteid")
    private String client_id;

    @JsonProperty("contrasena")
    private String password;

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