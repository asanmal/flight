package space.flight.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import space.flight.exception.UnknownOrientationException;

public enum Orientacion {
    @Schema(description = "Norte")
    N,

    @Schema(description = "Sur")
    S,

    @Schema(description = "Este")
    E,

    @Schema(description = "Oeste")
    O;

    @JsonCreator
    public static Orientacion fromString(String orientacion) {
        try {
            return Orientacion.valueOf(orientacion.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownOrientationException("Orientación desconocida: " + orientacion + ". Los valores permitidos son: N, S, E, O");
        }
    }
}
