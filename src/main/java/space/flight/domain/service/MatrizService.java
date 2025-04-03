package space.flight.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import space.flight.application.dto.MatrizCreateDTO;
import space.flight.application.dto.MatrizDTO;
import space.flight.application.dto.MatrizEditDTO;
import space.flight.domain.entity.Matriz;
import space.flight.common.exception.InvalidMatrixDimensionsException;
import space.flight.common.exception.MatrixNotFoundException;
import space.flight.application.mapper.MatrizMapper;
import space.flight.infrastructure.repository.MatrizRepository;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
public class MatrizService {

    private final MatrizRepository matrizRepository;


    // Crear matriz de vuelo
    public Matriz createMatriz(MatrizCreateDTO dto) {
        Matriz matriz = MatrizMapper.toEntity(dto);
        // Comprobamos si la matriz es valida
        if (matriz.getMtzX() < 0 || matriz.getMtzY() < 0) {
            throw new InvalidMatrixDimensionsException("Las dimensiones de la matriz son inválidas.");
        }

        return matrizRepository.save(matriz);
    }

    // Eliminar matriz de vuelo
    public void deleteMatriz(Long matrizId) {
        Matriz matriz = matrizRepository.findById(matrizId)
                .orElseThrow(() -> new MatrixNotFoundException("La matriz con el ID " + matrizId + " no existe."));

        // Comprobamos si la matriz tiene drones
        if (matriz.getDrones() != null && !matriz.getDrones().isEmpty()) {
            throw new InvalidMatrixDimensionsException("No se puede eliminar la matriz porque tiene drones.");
        }

        matrizRepository.delete(matriz);
    }


    // Editar matriz de vuelo
    public Matriz editMatriz(Long matrizId, MatrizEditDTO dto) {
        Matriz matrizExistente = matrizRepository.findById(matrizId)
                .orElseThrow(() -> new MatrixNotFoundException("La matriz con el ID " + matrizId + " no existe."));

        matrizExistente.setMtzX(dto.getMtzX());
        matrizExistente.setMtzY(dto.getMtzY());

        // Guardamos la matriz editada
        return matrizRepository.save(matrizExistente);
    }


    // Consultar matriz por su id
    public MatrizDTO findMatrizById(Long matrizId) {
        Matriz matriz = matrizRepository.findById(matrizId)
                .orElseThrow(() -> new MatrixNotFoundException("La matriz no existe."));

        // Convertimos la entidad a DTO
        return MatrizMapper.toDto(matriz);
    }

    // Consultar todas las matrices
    public List<MatrizDTO> findAllMatrizDTO() {
        List<Matriz> matrices = matrizRepository.findAll();
        // Mapeamos cada entidad a un MatrizDTO
        List<MatrizDTO> list = new ArrayList<>();
        for (Matriz matrix : matrices) {
            MatrizDTO dto = MatrizMapper.toDto(matrix);
            list.add(dto);
        }
        return list;
    }

}