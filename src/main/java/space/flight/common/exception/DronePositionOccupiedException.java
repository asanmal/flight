package space.flight.common.exception;

import lombok.Data;

@Data
public class DronePositionOccupiedException extends RuntimeException {

    private final String errorMessage;

    public DronePositionOccupiedException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
