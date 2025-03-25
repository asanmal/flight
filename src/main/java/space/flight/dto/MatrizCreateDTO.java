package space.flight.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatrizCreateDTO {
    // No pongo ni la id ni la lista al crear la matriz
    private int mtzX;
    private int mtzY;
}
