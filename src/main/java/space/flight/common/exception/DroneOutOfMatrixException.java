package space.flight.common.exception;

import lombok.Data;

@Data
public class DroneOutOfMatrixException extends RuntimeException {

    private final String errorMessage;

    public DroneOutOfMatrixException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
