package space.flight.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatrizDTO {
    @Schema(description = "ID de la matriz", example = "1")
    private Long matrizId;

    @Schema(description = "Coordenada X de la matriz", example = "10")
    private int mtzX;

    @Schema(description = "Coordenada Y de la matriz", example = "10")
    private int mtzY;

    @Schema(description = "Lista de drones de la matriz", example = "[{dronId: 1, nombre: 'Halcon-R', modelo: 'X200', x: 0, y: 0, orientacion: 'N', ordenes: ['MOVE_FORWARD', 'TURN_LEFT'], matrizId: 1}]")
    private List<DronDTO> drones;
}
