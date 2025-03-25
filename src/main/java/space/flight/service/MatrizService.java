package space.flight.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import space.flight.dto.MatrizCreateDTO;
import space.flight.dto.MatrizDTO;
import space.flight.dto.MatrizEditDTO;
import space.flight.entity.Matriz;
import space.flight.mapper.MatrizMapper;
import space.flight.repository.MatrizRepository;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
public class MatrizService {

    private final MatrizRepository matrizRepository;


    // Crear matriz de vuelo
    public Matriz createMatriz(MatrizCreateDTO dto) {
        Matriz matriz = MatrizMapper.toEntity(dto);
        // Comprobamos si la matriz ya existe
        if (matriz.getMtzX() < 0 || matriz.getMtzY() < 0) {
            throw new IllegalArgumentException("Las dimensiones de la matriz son inválidas.");
        }

        return matrizRepository.save(matriz);
    }


    // Editar matriz de vuelo
    public Matriz editMatriz(Long matrizId, MatrizEditDTO dto) {
        Matriz matrizExistente = matrizRepository.findById(matrizId)
                .orElseThrow(() -> new IllegalArgumentException("La matriz con el ID " + matrizId + " no existe."));

        matrizExistente.setMtzX(dto.getMtzX());
        matrizExistente.setMtzY(dto.getMtzY());

        // Guardamos la matriz editada
        return matrizRepository.save(matrizExistente);
    }


    // Consultar matriz por su id
    public MatrizDTO findMatrizById(Long matrizId) {
        Matriz matriz = matrizRepository.findById(matrizId)
                .orElseThrow(() -> new IllegalArgumentException("La matriz no existe."));

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