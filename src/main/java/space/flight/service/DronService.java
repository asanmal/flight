package space.flight.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import space.flight.entity.Dron;
import space.flight.entity.Matriz;
import space.flight.repository.DronRepository;
import space.flight.repository.MatrizRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DronService {

    private final DronRepository dronRepository;
    private final MatrizRepository matrizRepository;


    // Crear un dron en el sistema
    public Dron createDron(Dron dron) {
        // Para contener si la matriz es nula y no existe
        Optional<Matriz> matrizOpt = matrizRepository.findById(dron.getMatriz().getMatrizId());

        if (matrizOpt.isPresent()) {
            // Si la matriz existe la guardamos
            Matriz matriz = matrizOpt.get();

            // Compruebo que la posicion del dron este en la matriz
            if (dron.getX() < 0 || dron.getX() >= matriz.getMtzX() || dron.getY() < 0 || dron.getY() >= matriz.getMtzY()) {
                throw new IllegalArgumentException("El dron esta fuera de la matriz.");
            }

            // Recorro la lista de drones que este asociada a esa matriz
            // para comprobar que el dron este en una posicion que no este ocupada
            for (Dron d : matriz.getDrones()) {
                if (d.getX() == dron.getX() && d.getY() == dron.getY()) {
                    throw new IllegalArgumentException("Ya existe un dron en la posicion X:" + dron.getX() + " e Y:" + dron.getY());
                }
            }
        } else{
            throw new IllegalArgumentException("La matriz de vuelo no existe.");
        }
        return dronRepository.save(dron);
    }


    // Editar un dron del sistema
    public Dron editDron(Long dronId, Dron dronActualizado) {
        // Para contener si el dron no existe
        Optional<Dron> dronOpt = dronRepository.findById(dronId);

        if (dronOpt.isPresent()) {
            Dron dronExistente = dronOpt.get();

            // Actualizamos con los datos actuales
            dronExistente.setNombre(dronActualizado.getNombre());
            dronExistente.setModelo(dronActualizado.getModelo());
            dronExistente.setX(dronActualizado.getX());
            dronExistente.setY(dronActualizado.getY());
            dronExistente.setOrientacion(dronActualizado.getOrientacion());
            dronExistente.setOrden(dronActualizado.getOrden());

            // Compruebo que la posicion del dron este en la matriz
            Matriz matriz = dronExistente.getMatriz();
            if (dronExistente.getX() < 0 || dronExistente.getX() >= matriz.getMtzX() || dronExistente.getY() < 0 || dronExistente.getY() >= matriz.getMtzY()) {
                throw new IllegalArgumentException("El dron esta fuera de la matriz.");
            }

            // Recorro la lista de drones que este asociada a esa matriz
            // para comprobar que el dron este en una posicion que no este ocupada
            for (Dron d : matriz.getDrones()) {
                if (!d.getDronId().equals(dronExistente.getDronId()) && d.getX() == dronExistente.getX() && d.getY() == dronExistente.getY()) {
                    throw new IllegalArgumentException("Ya existe un dron en la posicion X:" + dronExistente.getX() + " e Y:" + dronExistente.getY());
                }
            }
            return dronRepository.save(dronExistente);

        } else{
            throw new IllegalArgumentException("El dron no existe.");
        }
    }


    // Eliminar un dron del sistema
    public void deleteDron(Long dronId) {
        // Para contener si el dron no existe
        Optional<Dron> dronOpt = dronRepository.findById(dronId);

        if (dronOpt.isPresent()) {
            // Eliminar el dron del sistema
            dronRepository.deleteById(dronId);
        } else{
            throw new IllegalArgumentException("El dron no existe.");
        }
    }


    // Consultar todos los drones
    public List<Dron> findAllDrons() {return dronRepository.findAll();}

    // Consultar el listado de drones por matriz
    public List<Dron> findAllDronsByMatriz(Long matrizId) {
        // Para contener si la matriz es nula y no existe
        Optional<Matriz> matrizOpt = matrizRepository.findById(matrizId);

        if (matrizOpt.isPresent()) {
            return matrizOpt.get().getDrones();
        } else{
            throw new IllegalArgumentException("La matriz no existe.");
        }
    }


    // Consultar los detalles del dron que esta posicionado en las coordenadas X, Y
    public Dron findDronByPosition(int x, int y, Long matrizId) {
        // Para contener si la matriz es nula o no existe
        Optional<Matriz> matrizOpt = matrizRepository.findById(matrizId);

        if (matrizOpt.isPresent()) {
            for (Dron dron : matrizOpt.get().getDrones()) {
                if (dron.getX() == x && dron.getY() == y) {
                    return dron;
                }
            }

        } else{
            throw new IllegalArgumentException("La matriz no existe.");
        }

        throw new IllegalArgumentException("No hay ningún dron en las coordenadas especificadas.");
    }
}
