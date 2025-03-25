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
@Table(name = "drones_db")
@Data
@Builder
public class Dron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dronId;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private int y;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Orientacion orientacion;

    @ElementCollection
    @CollectionTable(name = "dron_ordenes", joinColumns = @JoinColumn(name = "dron_id"))
    @Enumerated(EnumType.STRING)
    private List<Orden> ordenes = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "matrizId")
    private Matriz matriz;

}
