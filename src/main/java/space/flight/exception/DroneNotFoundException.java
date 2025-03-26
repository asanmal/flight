package space.flight.exception;

import lombok.Data;

@Data
public class DroneNotFoundException extends RuntimeException {

    private final String errorMessage;

    public DroneNotFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
