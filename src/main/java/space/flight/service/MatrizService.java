package space.flight.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import space.flight.entity.Matriz;
import space.flight.repository.MatrizRepository;


@AllArgsConstructor
@Service
public class MatrizService {

    private final MatrizRepository matrizRepository;



    // Crear matriz de vuelo
    public Matriz createMatriz(Matriz matriz) {
        // Comprobamos si la matriz ya existe
        if (matriz.getMtzX() <= 0 || matriz.getMtzY() <= 0) {
            throw new IllegalArgumentException("Las dimensiones de la matriz son inválidas.");
        }

        return matrizRepository.save(matriz);
    }


    // Editar matriz de vuelo
    public Matriz editMatriz(Long matrizId, Matriz nuevaMatriz) {
        Matriz matrizExistente = matrizRepository.findById(matrizId)
                .orElseThrow(() -> new IllegalArgumentException("La matriz con el ID " + matrizId + " no existe."));

        // Actualizamos los nuevos valores de la matriz
        matrizExistente.setMtzX(nuevaMatriz.getMtzX());
        matrizExistente.setMtzY(nuevaMatriz.getMtzY());

        // Guardamos la matriz editada
        return matrizRepository.save(matrizExistente);
    }


    // Consultar matriz por su id
    public Matriz findMatrizById(Long matrizId) {
        return matrizRepository.findById(matrizId)
                .orElseThrow(() -> new IllegalArgumentException("La matriz no existe."));
    }
}