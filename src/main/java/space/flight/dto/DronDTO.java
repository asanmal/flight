package space.flight.dto;

import lombok.*;
import space.flight.entity.Orden;
import space.flight.entity.Orientacion;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DronDTO {
    private Long dronId;
    private String nombre;
    private String modelo;
    private int x;
    private int y;
    private Orientacion orientacion;
    private List<Orden> ordenes;
    private Long matrizId;
}
