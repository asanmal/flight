package space.flight.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import space.flight.entity.Orientacion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DronCreateDTO {
    // No incluyo el id por que se crea automatico incrementado
    @NotBlank(message = "El nombre del dron es obligatorio")
    private String nombre;

    @NotBlank(message = "El modelo del dron es obligatorio")
    private String modelo;

    @Min(value = 0, message = "La coordenada X debe ser mayor o igual a 0")
    private int x;

    @Min(value = 0, message = "La coordenada Y debe ser mayor o igual a 0")
    private int y;

    @NotNull(message = "La orientación del dron es obligatoria")
    private Orientacion orientacion;

    // No incluyo el campo ordenes a la hora de crear dron

    @NotNull(message = "El ID de la matriz del dron es obligatoria")
    private Long matrizId;
}
