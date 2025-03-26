package space.flight.exception;

import lombok.Data;

@Data
public class InvalidOrderException extends RuntimeException {
    private final String errorMessage;

    public InvalidOrderException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
