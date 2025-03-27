package space.flight.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import space.flight.exception.InvalidOrderException;

public enum Orden {
    @Schema(description = "Girar a la izquierda")
    TURN_LEFT,

    @Schema(description = "Girar a la derecha")
    TURN_RIGHT,

    @Schema(description = "Mover hacia adelante")
    MOVE_FORWARD;

    @JsonCreator
    public static Orden fromString(String orden) {
        try {
            return Orden.valueOf(orden.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidOrderException("Orden inválida: " + orden + ". Las órdenes válidas son: TURN_LEFT, TURN_RIGHT, MOVE_FORWARD");
        }
    }
}
