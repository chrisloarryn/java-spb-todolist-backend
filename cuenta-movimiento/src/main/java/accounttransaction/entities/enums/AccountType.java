package accounttransaction.entities.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum AccountType
{
    Ahorro("A"),
    Corriente("C");

    private String value = "";

    AccountType(String value) {
        this.value = value;
    }

    // get description
    public String getDescription()
    {
        switch (this)
        {
            case Ahorro:
                return "Ahorro";
            case Corriente:
                return "Corriente";
            default:
                return "";
        }
    }
}