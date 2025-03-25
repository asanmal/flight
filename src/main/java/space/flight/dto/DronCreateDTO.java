package space.flight.dto;

import lombok.*;
import space.flight.entity.Orientacion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DronCreateDTO {
    // No incluyo el id por que se crea automatico incrementado
    private String nombre;
    private String modelo;
    private int x;
    private int y;
    private Orientacion orientacion;
    // No incluyo el campo ordenes a la hora de crear dron
    private Long matrizId;
}
