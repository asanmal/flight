package space.flight.dto;

import lombok.*;
import space.flight.entity.Orientacion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DronEditDTO {
    // No incluyo el id de dron para que no sea editado
    // No incluyo el campo ordenes a la hora de editar el dron
    private String nombre;
    private String modelo;
    private int x;
    private int y;
    private Orientacion orientacion;
    private Long matrizId;

}
