package space.flight.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejamos las excepciones de validación lanzadas por @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MatrixNotFoundException.class)
    public ResponseEntity<Object> handleMatrixNotFoundException(MatrixNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getErrorMessage());
    }

    @ExceptionHandler(DroneNotFoundException.class)
    public ResponseEntity<Object> handleDroneNotFoundException(DroneNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getErrorMessage());
    }

    @ExceptionHandler(DroneOutOfMatrixException.class)
    public ResponseEntity<Object> handleDroneOutOfMatrixException(DroneOutOfMatrixException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getErrorMessage());
    }

    @ExceptionHandler(DronePositionOccupiedException.class)
    public ResponseEntity<Object> handleDronePositionOccupiedException(DronePositionOccupiedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getErrorMessage());
    }

    @ExceptionHandler(InvalidMatrixDimensionsException.class)
    public ResponseEntity<Object> handleInvalidMatrixDimensionsException(InvalidMatrixDimensionsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getErrorMessage());
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<Object> handleInvalidOrderException(InvalidOrderException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getErrorMessage());
    }

    @ExceptionHandler(UnknownOrientationException.class)
    public ResponseEntity<Object> handleUnknownOrientationException(UnknownOrientationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getErrorMessage());
    }

    @ExceptionHandler( value = { Exception.class })
    public ResponseEntity<Object> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error en la ejecución del servicio");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

}
