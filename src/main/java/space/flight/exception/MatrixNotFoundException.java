package space.flight.exception;

import lombok.Data;

@Data
public class MatrixNotFoundException extends RuntimeException {

    private final String errorMessage;

    public MatrixNotFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
