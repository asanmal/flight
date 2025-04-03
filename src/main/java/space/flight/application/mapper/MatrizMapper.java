package space.flight.application.mapper;

import space.flight.application.dto.DronDTO;
import space.flight.application.dto.MatrizCreateDTO;
import space.flight.application.dto.MatrizDTO;
import space.flight.domain.entity.Dron;
import space.flight.domain.entity.Matriz;
import java.util.ArrayList;
import java.util.List;

public class MatrizMapper {

    private MatrizMapper() {}

    public static Matriz toEntity(MatrizDTO dto){
        return  Matriz.builder()
                .matrizId(dto.getMatrizId())
                .mtzX(dto.getMtzX())
                .mtzY(dto.getMtzY())
                .build();
    }

    // Metodo que uso para crear una matriz sin tener que añadir una lista ni su id por que se genera automaticamente
    public static Matriz toEntity(MatrizCreateDTO dto) {
        return Matriz.builder()
                .mtzX(dto.getMtzX())
                .mtzY(dto.getMtzY())
                .drones(new ArrayList<>())  // Inicializa la lista vacía
                .build();
    }

    public static MatrizDTO toDto(Matriz matriz) {
        List<DronDTO> dronesDTO = new ArrayList<>();
        if (matriz.getDrones() != null) {
            for (Dron dron : matriz.getDrones()) {
                DronDTO dto = DronMapper.toDto(dron);
                dronesDTO.add(dto);
            }
        }
        return new MatrizDTO(matriz.getMatrizId(), matriz.getMtzX(), matriz.getMtzY(), dronesDTO);
    }

}
