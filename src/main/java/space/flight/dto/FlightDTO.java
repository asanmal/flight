package space.flight.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import space.flight.entity.Orden;
import java.util.List;

@Data
public class FlightDTO {

    @NotEmpty(message = "La lista de IDs de drones no puede estar vacía")
    private List<Long> dronIds;

    @NotEmpty(message = "La lista de órdenes no puede estar vacía")
    private List<Orden> ordenes;
}
