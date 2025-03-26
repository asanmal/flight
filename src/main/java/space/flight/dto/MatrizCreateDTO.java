package space.flight.dto;

import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatrizCreateDTO {
    // No pongo ni la id ni la lista al crear la matriz
    @Min(value = 0, message = "La coordenada X de la matriz debe ser mayor o igual a 0")
    private int mtzX;

    @Min(value = 0, message = "La coordenada Y de la matriz debe ser mayor o igual a 0")
    private int mtzY;
}
