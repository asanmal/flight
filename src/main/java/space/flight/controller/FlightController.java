package space.flight.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.flight.dto.DronDTO;
import space.flight.dto.FlightDTO;
import space.flight.entity.Dron;
import space.flight.entity.Orden;
import space.flight.mapper.DronMapper;
import space.flight.service.DronService;
import space.flight.service.FlightService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vuelos/flight")
@RequiredArgsConstructor
public class FlightController {
    private final DronService dronService;
    private final FlightService flightService;

    @PostMapping("/{dronId}")
    public ResponseEntity<DronDTO> executeCommands(@PathVariable Long dronId, @RequestBody List<Orden> ordenes) {
        Dron updated = flightService.executeCommands(dronId, ordenes);
        DronDTO response = DronMapper.toDto(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/group")
    public ResponseEntity<List<DronDTO>> executeCommandsForAllDrons(@RequestBody FlightDTO request) {
        List<Dron> drones = new ArrayList<>();
        // Filtramos los drones que coincidan con los IDs del dlightDAO
        for (Dron d : dronService.findAllDrons()) {
            if (request.getDronIds().contains(d.getDronId())) {
                drones.add(d);
            }
        }

        // Ejecuta todas las ordenes a todos los drones
        flightService.executeCommandsForAllDrons(drones, request.getOrdenes());

        // Convertimos el dron a dto para la salida
        List<DronDTO> response = new ArrayList<>();
        for (Dron drone : drones) {
            DronDTO dto = DronMapper.toDto(drone);
            response.add(dto);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
