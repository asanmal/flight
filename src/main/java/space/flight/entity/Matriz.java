package space.flight.entity;

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
@Table(name = "matriz_db")
@Data
@Builder
public class Matriz {

    @Schema(description = "ID de la matriz", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matrizId;

    @Schema(description = "Coordenada X de la matriz", example = "10")
    @Column(nullable = false)
    private int mtzX;

    @Schema(description = "Coordenada Y de la matriz", example = "10")
    @Column(nullable = false)
    private int mtzY;

    @Schema(description = "Lista de drones de la matriz", example = "[{dronId: 1, nombre: 'Halcon-R', modelo: 'X200', x: 0, y: 0, orientacion: 'N', ordenes: ['MOVE_FORWARD', 'TURN_LEFT'], matrizId: 1}]")
    @OneToMany(mappedBy = "matriz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dron> drones = new ArrayList<>();
}
