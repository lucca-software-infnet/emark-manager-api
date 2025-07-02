package mercado.emark.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Cargo {
    CLIENTE,
    OPERADOR,
    ADMINISTRADOR,
    GERENTE,
    SUPORTE,
    ATENDENTE;

    @JsonCreator
    public static Cargo fromString(String value) {
        return Cargo.valueOf(value.toUpperCase());
    }
}
