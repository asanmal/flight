package space.flight.exception;

import lombok.Data;

@Data
public class UnknownOrientationException extends RuntimeException{

    private final String errorMessage;

    public UnknownOrientationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
