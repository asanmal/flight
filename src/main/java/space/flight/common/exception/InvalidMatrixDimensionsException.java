package space.flight.common.exception;

import lombok.Data;

@Data
public class InvalidMatrixDimensionsException extends RuntimeException {

    private final String errorMessage;

    public InvalidMatrixDimensionsException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
