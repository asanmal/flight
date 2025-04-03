package space.flight.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatrizEditDTO {
    // No pongo ni la id ni la lista al editar la matriz
    @Schema(description = "Coordenada X de la matriz", example = "10")
    @Min(value = 0, message = "La coordenada X debe ser mayor o igual a 0")
    private int mtzX;

    @Schema(description = "Coordenada Y de la matriz", example = "10")
    @Min(value = 0, message = "La coordenada Y debe ser mayor o igual a 0")
    private int mtzY;
}
