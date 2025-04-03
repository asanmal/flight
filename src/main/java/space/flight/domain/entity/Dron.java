package space.flight.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drones_db")
@Data
@Builder
public class Dron {

    @Schema(description = "ID del dron", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dronId;

    @Schema(description = "Nombre del dron", example = "Halcon-R")
    @Column(nullable = false)
    private String nombre;

    @Schema(description = "Modelo del dron", example = "X200")
    @Column(nullable = false)
    private String modelo;

    @Schema(description = "Coordenada X de la posición del dron", example = "0")
    @Column(nullable = false)
    private int x;

    @Schema(description = "Coordenada Y de la posición del dron", example = "0")
    @Column(nullable = false)
    private int y;

    @Schema(description = "Orientación del dron", example = "N")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Orientacion orientacion;

    @Schema(description = "Lista de órdenes del dron", example = "['MOVE_FORWARD', 'TURN_LEFT']")
    @ElementCollection
    @CollectionTable(name = "dron_ordenes", joinColumns = @JoinColumn(name = "dron_id"))
    @Enumerated(EnumType.STRING)
    private List<Orden> ordenes = new ArrayList<>();


    @Schema(description = "ID de la matriz donde se posiciona el dron", example = "1")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "matrizId")
    private Matriz matriz;

}
