package space.flight.entity;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matrizId;

    @Column(nullable = false)
    private int mtzX;

    @Column(nullable = false)
    private int mtzY;

    @OneToMany(mappedBy = "matriz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dron> drones = new ArrayList<>();
}
