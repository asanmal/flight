package space.flight.dto;

import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatrizEditDTO {
    // No pongo ni la id ni la lista al editar la matriz

    @Min(value = 0, message = "La coordenada X debe ser mayor o igual a 0")
    private int mtzX;

    @Min(value = 0, message = "La coordenada Y debe ser mayor o igual a 0")
    private int mtzY;
}
