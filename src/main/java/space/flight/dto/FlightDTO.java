package space.flight.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import space.flight.entity.Orden;
import java.util.List;

@Data
public class FlightDTO {

    @Schema(description = "Lista de IDs de drones", example = "[1, 2, 3]")
    @NotEmpty(message = "La lista de IDs de drones no puede estar vacía")
    private List<Long> dronIds;

    @Schema(description = "Lista de órdenes de vuelo a ejecutar", example = "[\"MOVE_FORWARD\", \"TURN_LEFT\"]")
    @NotEmpty(message = "La lista de órdenes no puede estar vacía")
    private List<Orden> ordenes;
}
