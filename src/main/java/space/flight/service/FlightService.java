package space.flight.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import space.flight.entity.Dron;
import space.flight.entity.Matriz;
import space.flight.entity.Orden;
import space.flight.entity.Orientacion;
import java.util.List;
import space.flight.repository.DronRepository;

@AllArgsConstructor
@Service
public class FlightService {

    private final DronRepository dronRepository;


    // Ejecutar ordenes de vuelo sobre un dron especifico
    public Dron executeCommands(Long dronId, List<Orden> ordenes) {
        Dron dron = dronRepository.findById(dronId)
                .orElseThrow(() -> new IllegalArgumentException("El dron no existe."));
        Matriz matriz = dron.getMatriz();

        // Ejecutar cada orden de la lista
        for (Orden orden : ordenes) {
            switch (orden) {
                case MOVE_FORWARD:
                    moveForward(dron, matriz);
                    dron.getOrdenes().add(orden);
                    break;
                case TURN_LEFT:
                    dron.setOrientacion(turnLeft(dron.getOrientacion()));
                    dron.getOrdenes().add(orden);
                    break;
                case TURN_RIGHT:
                    dron.setOrientacion(turnRight(dron.getOrientacion()));
                    dron.getOrdenes().add(orden);
                    break;
                default:
                    throw new IllegalArgumentException("Orden incorrecta.");
            }
        }

        // Comprobar que no haya ningun otro dron en esa posicion.
        for (Dron d : matriz.getDrones()) {
            if (!d.getDronId().equals(dron.getDronId()) &&
                    d.getX() == dron.getX() &&
                    d.getY() == dron.getY()) {
                throw new IllegalStateException("Esta ocupada la posición por otro dron.");
            }
        }
        return dronRepository.save(dron);
    }


    // Ejecutar ordenes de vuelo en secuencia para un conjunto de drones
    public void executeCommandsForAllDrons(List<Dron> drones, List<Orden> ordenes) {
        // Iteramos sobre cada dron y le ejecutamos las ordenes
        for (Dron dron : drones) {
                // Ejecutar las ordenes para este dron
                executeCommands(dron.getDronId(), ordenes);
        }
    }


    // Metodos para giros y movimientos
    private Orientacion turnLeft(Orientacion orientacion) {
        return switch (orientacion) {
            case N -> Orientacion.O;
            case O -> Orientacion.S;
            case S -> Orientacion.E;
            case E -> Orientacion.N;
            default -> throw new IllegalStateException("Orientacion desconocida: " + orientacion);
        };
    }

    private Orientacion turnRight(Orientacion orientacion) {
        return switch (orientacion) {
            case N -> Orientacion.E;
            case E -> Orientacion.S;
            case S -> Orientacion.O;
            case O -> Orientacion.N;
            default -> throw new IllegalStateException("Orientacion desconocida: " + orientacion);
        };
    }

    private void moveForward(Dron dron, Matriz matriz) {
        int x = dron.getX();
        int y = dron.getY();

        switch (dron.getOrientacion()) {
            case N: y++; break;
            case S: y--; break;
            case E: x++; break;
            case O: x--; break;
        }

        // Compruebo que la posicion del dron este en la matriz
        if (x >= 0 && x < matriz.getMtzX() && y >= 0 && y < matriz.getMtzY()) {
            dron.setX(x);
            dron.setY(y);
        } else {
            throw new IllegalStateException("El dron esta fuera de la matriz.");
        }
    }


}
