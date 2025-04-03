package space.flight.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import space.flight.domain.entity.Orden;
import space.flight.domain.entity.Orientacion;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DronDTO {
    @Schema(description = "ID del dron", example = "1")
    private Long dronId;

    @Schema(description = "Nombre del dron", example = "Halcon-R")
    private String nombre;

    @Schema(description = "Modelo del dron", example = "X200")
    private String modelo;

    @Schema(description = "Coordenada X de la posición del dron", example = "0")
    private int x;

    @Schema(description = "Coordenada Y de la posición del dron", example = "0")
    private int y;

    @Schema(description = "Orientación del dron", example = "N")
    private Orientacion orientacion;

    @Schema(description = "Lista de órdenes del dron")
    private List<Orden> ordenes;

    @Schema(description = "ID de la matriz donde se posiciona el dron", example = "1")
    private Long matrizId;
}
