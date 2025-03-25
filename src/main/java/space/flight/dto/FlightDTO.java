package space.flight.dto;

import lombok.Data;
import space.flight.entity.Orden;
import java.util.List;

@Data
public class FlightDTO {
    private List<Long> dronIds;
    private List<Orden> ordenes;
}
