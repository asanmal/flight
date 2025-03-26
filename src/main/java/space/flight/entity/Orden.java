package space.flight.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import space.flight.exception.InvalidOrderException;

public enum Orden {
    TURN_LEFT, TURN_RIGHT, MOVE_FORWARD;

    @JsonCreator
    public static Orden fromString(String orden) {
        try {
            return Orden.valueOf(orden.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidOrderException("Orden inválida: " + orden + ". Las órdenes válidas son: TURN_LEFT, TURN_RIGHT, MOVE_FORWARD");
        }
    }
}
