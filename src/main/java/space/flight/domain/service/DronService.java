package space.flight.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import space.flight.application.dto.DronCreateDTO;
import space.flight.application.dto.DronEditDTO;
import space.flight.common.exception.DroneNotFoundException;
import space.flight.common.exception.DroneOutOfMatrixException;
import space.flight.common.exception.DronePositionOccupiedException;
import space.flight.common.exception.MatrixNotFoundException;
import space.flight.domain.entity.Dron;
import space.flight.domain.entity.Matriz;
import space.flight.application.mapper.DronMapper;
import space.flight.infrastructure.repository.DronRepository;
import space.flight.infrastructure.repository.MatrizRepository;
import java.util.List;

@AllArgsConstructor
@Service
public class DronService {

    private final DronRepository dronRepository;
    private final MatrizRepository matrizRepository;


    // Crear un dron en el sistema
    public Dron createDron(DronCreateDTO dto) {
        // Comprobamos que la matriz exista
        Long matrizId = dto.getMatrizId();
        Matriz matriz = matrizRepository.findById(matrizId)
                .orElseThrow(() -> new MatrixNotFoundException("La matriz no se encuentra."));

        Dron dron = DronMapper.toEntity(dto, matriz);
        // Comprobamos que el dron no este fuera de la matriz
        if (dron.getX() < 0 || dron.getX() >= matriz.getMtzX() || dron.getY() < 0 || dron.getY() >= matriz.getMtzY()) {
            throw new DroneOutOfMatrixException("El dron está fuera de la matriz.");
        }

        // Recorremos la lista de drones que hay en una matriz concreta que buscamos para comprobar
        // que no exista ningun dron en la posición que buscamos
        for (Dron d : matriz.getDrones()) {
            if (d.getX() == dron.getX() && d.getY() == dron.getY()) {
                throw new DronePositionOccupiedException("Ya existe un dron en esa posición");
            }
        }

      return dronRepository.save(dron);
    }


    // Editar un dron del sistema
    public Dron editDron(Long dronId, DronEditDTO dto) {
        //Comprobamos que el dron exista y que la matriz exista
        Dron dronExistente = dronRepository.findById(dronId)
                .orElseThrow(() -> new DroneNotFoundException("El dron no existe."));

        Matriz matriz = matrizRepository.findById(dto.getMatrizId())
                .orElseThrow(() -> new MatrixNotFoundException("No se encuentra la matriz."));

        // Actualizamos con los datos actuales
        DronMapper.updateEntityFromDTO(dto, dronExistente, matriz);

        // Compruebo que la posicion del dron este en la matriz
        if (dronExistente.getX() < 0 || dronExistente.getX() >= matriz.getMtzX() || dronExistente.getY() < 0 || dronExistente.getY() >= matriz.getMtzY()) {
            throw new DroneOutOfMatrixException("El dron está fuera de las coordenadas de la matriz.");
        }

        // Recorro la lista de drones que este asociada a esa matriz
        // para comprobar que el dron este en una posicion que no este ocupada
        for (Dron d : matriz.getDrones()) {
            if (!d.getDronId().equals(dronExistente.getDronId()) && d.getX() == dronExistente.getX() && d.getY() == dronExistente.getY()) {
                throw new DronePositionOccupiedException("Ya existe un dron en la posicion X:" + dronExistente.getX() + " e Y:" + dronExistente.getY());
            }
        }

        return dronRepository.save(dronExistente);
    }


    // Eliminar un dron del sistema
    public void deleteDron(Long dronId) {
        Dron dron = dronRepository.findById(dronId)
                .orElseThrow(() -> new DroneNotFoundException("El dron no existe."));
        dronRepository.delete(dron);
    }


    // Consultar todos los drones
    public List<Dron> findAllDrons() {return dronRepository.findAll();}

    // Consultar el listado de drones por matriz
    public List<Dron> findAllDronsByMatriz(Long matrizId) {
        Matriz matriz = matrizRepository.findById(matrizId)
                .orElseThrow(() -> new MatrixNotFoundException("La matriz no existe."));

        return matriz.getDrones();
    }


    // Consultar los detalles del dron que esta posicionado en las coordenadas X, Y
    public Dron findDronByPosition(int x, int y, Long matrizId) {
        Matriz matriz = matrizRepository.findById(matrizId)
                .orElseThrow(() -> new MatrixNotFoundException("La matriz no existe."));

        if (x < 0 || x > matriz.getMtzX() || y < 0 || y > matriz.getMtzY()) {
            throw new DroneOutOfMatrixException("El dron está fuera de la matriz.");
        }

        return matriz.getDrones().stream()
                .filter(d -> d.getX() == x && d.getY() == y)
                .findFirst()
                .orElseThrow(() -> new DroneNotFoundException("No hay ningun dron en las coordenadas."));
    }
}
