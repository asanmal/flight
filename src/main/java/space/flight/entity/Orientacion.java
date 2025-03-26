package space.flight.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import space.flight.exception.UnknownOrientationException;

public enum Orientacion {
    N, S, E, O;

    @JsonCreator
    public static Orientacion fromString(String orientacion) {
        try {
            return Orientacion.valueOf(orientacion.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownOrientationException("Orientación desconocida: " + orientacion + ". Los valores permitidos son: N, S, E, O");
        }
    }
}
