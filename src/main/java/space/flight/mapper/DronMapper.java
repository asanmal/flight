package space.flight.mapper;

import space.flight.dto.DronCreateDTO;
import space.flight.dto.DronDTO;
import space.flight.dto.DronEditDTO;
import space.flight.entity.Dron;
import space.flight.entity.Matriz;
import space.flight.entity.Orden;

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
