package space.flight.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import space.flight.entity.Orientacion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DronEditDTO {
    // No incluyo el id de dron para que no sea editado
    // No incluyo el campo ordenes a la hora de editar el dron
    @Schema(description = "Nombre del dron", example = "Halcon-R")
    @NotBlank(message = "El nombre del dron es obligatorio")
    private String nombre;

    @Schema(description = "Modelo del dron", example = "X200")
    @NotBlank(message = "El modelo del dron es obligatorio")
    private String modelo;

    @Schema(description = "Coordenada X de la posición del dron", example = "0")
    @Min(value = 0, message = "La coordenada X debe ser mayor o igual a 0")
    private int x;

    @Schema(description = "Coordenada Y de la posición del dron", example = "0")
    @Min(value = 0, message = "La coordenada Y debe ser mayor o igual a 0")
    private int y;

    @Schema(description = "Orientación del dron", example = "N")
    @NotNull(message = "La orientación del dron es obligatoria")
    private Orientacion orientacion;

    @Schema(description = "ID de la matriz donde se posiciona el dron", example = "1")
    @NotNull(message = "El ID de la matriz del dron es obligatoria")
    private Long matrizId;

}
