package space.flight.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import space.flight.common.exception.*;
import space.flight.domain.entity.Dron;
import space.flight.domain.entity.Matriz;
import space.flight.domain.entity.Orden;
import space.flight.domain.entity.Orientacion;

import java.util.ArrayList;
import java.util.List;

import space.flight.infrastructure.repository.DronRepository;

@AllArgsConstructor
@Service
public class FlightService {

    private final DronRepository dronRepository;


    // Ejecutar ordenes de vuelo sobre un dron especifico
    public Dron executeCommands(Long dronId, List<Orden> ordenes) {
        Dron dron = dronRepository.findById(dronId)
                .orElseThrow(() -> new DroneNotFoundException("El dron no existe."));
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
                    throw new InvalidOrderException("Orden incorrecta.");
            }
        }

        // Comprobar que no haya ningun otro dron en esa posicion.
        for (Dron d : matriz.getDrones()) {
            if (!d.getDronId().equals(dron.getDronId()) &&
                    d.getX() == dron.getX() &&
                    d.getY() == dron.getY()) {
                throw new DronePositionOccupiedException("Esta ocupada la posición por otro dron.");
            }
        }
        return dronRepository.save(dron);
    }


    // Ejecutar ordenes de vuelo en secuencia para un conjunto de drones
    public List<Dron> executeCommandsForAllDrons(List<Long> dronIds, List<Orden> ordenes) {
        List<Dron> drones = dronRepository.findAllById(dronIds);

        // Comprobamos que todos los drones recibidos existan
        if (drones.size() != dronIds.size()) {
            // Calcula la lista de IDs que no se encontraron
            List<Long> missingIds = new ArrayList<>();
            for (Long id : dronIds) {
                if (drones.stream().noneMatch(d -> d.getDronId().equals(id))) {
                    missingIds.add(id);
                }
            }

            // Si hay un dron que no se encontro, lanzar excepcion y muestro la lista de los no encontrados
            if (!missingIds.isEmpty()) {
                throw new DroneNotFoundException("Los siguientes drones no existen: " + missingIds);
            }
        }

        // Recorro los drones y le ejecuto todas las ordenes a cada uno
        for (Dron dron : drones) {
            executeCommands(dron.getDronId(), ordenes);
        }
        return drones;
    }


    // Metodos para giros y movimientos
    private Orientacion turnLeft(Orientacion orientacionLeft) {
        if (orientacionLeft == null) {
            throw new UnknownOrientationException("La orientacion es desconocida: " + orientacionLeft);
        }
        return switch (orientacionLeft) {
            case N -> Orientacion.O;
            case O -> Orientacion.S;
            case S -> Orientacion.E;
            case E -> Orientacion.N;
            default -> throw new UnknownOrientationException("La orientacion es desconocida: " + orientacionLeft);
        };
    }

    private Orientacion turnRight(Orientacion orientacionRight) {
        if (orientacionRight == null) {
            throw new UnknownOrientationException("Orientacion desconocida: " + orientacionRight);
        }

        return switch (orientacionRight) {
            case N -> Orientacion.E;
            case E -> Orientacion.S;
            case S -> Orientacion.O;
            case O -> Orientacion.N;
            default -> throw new UnknownOrientationException("Orientacion desconocida: " + orientacionRight);
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
            throw new DroneOutOfMatrixException("El dron esta fuera de la matriz.");
        }
    }


}
