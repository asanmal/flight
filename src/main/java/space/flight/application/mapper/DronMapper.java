package space.flight.application.mapper;

import space.flight.application.dto.DronCreateDTO;
import space.flight.application.dto.DronDTO;
import space.flight.application.dto.DronEditDTO;
import space.flight.domain.entity.Dron;
import space.flight.domain.entity.Matriz;
import space.flight.domain.entity.Orden;

import java.util.ArrayList;
import java.util.List;

public class DronMapper {

    private DronMapper() {}

    public static Dron toEntity(DronCreateDTO dto, Matriz matriz) {
        return Dron.builder()
                .nombre(dto.getNombre())
                .modelo(dto.getModelo())
                .x(dto.getX())
                .y(dto.getY())
                .orientacion(dto.getOrientacion())
                .matriz(matriz)
                .build();
    }

    public static DronDTO toDto(Dron dron) {

        List<Orden> ordenes = new ArrayList<>();

        return new DronDTO(
                dron.getDronId(),
                dron.getNombre(),
                dron.getModelo(),
                dron.getX(),
                dron.getY(),
                dron.getOrientacion(),
                dron.getOrdenes() != null ? dron.getOrdenes() : ordenes,
                dron.getMatriz().getMatrizId());
    }


    public static void updateEntityFromDTO(DronEditDTO dto, Dron dronExistente, Matriz matriz) {
        dronExistente.setNombre(dto.getNombre());
        dronExistente.setModelo(dto.getModelo());
        dronExistente.setX(dto.getX());
        dronExistente.setY(dto.getY());
        dronExistente.setOrientacion(dto.getOrientacion());
        dronExistente.setMatriz(matriz);
    }

}
