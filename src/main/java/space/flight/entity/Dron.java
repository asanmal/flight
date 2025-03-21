package space.flight.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Orden orden;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "matrizId")
    private Matriz matriz;

}
