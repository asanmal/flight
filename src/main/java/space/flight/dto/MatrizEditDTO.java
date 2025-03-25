package space.flight.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatrizEditDTO {
    // No pongo ni la id ni la lista al editar la matriz
    private int mtzX;
    private int mtzY;
}
