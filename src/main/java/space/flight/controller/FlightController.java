package space.flight.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    @Operation( summary = "Ejecutar ordenes en un dron por su ID")
    public ResponseEntity<DronDTO> executeCommands(@PathVariable Long dronId, @Valid @RequestBody List<Orden> ordenes) {
        Dron updated = flightService.executeCommands(dronId, ordenes);
        DronDTO response = DronMapper.toDto(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/group")
    @Operation( summary = "Ejecutar ordenes para multiples dron por su ID")
    public ResponseEntity<List<DronDTO>> executeCommandsForAllDrons(@Valid @RequestBody FlightDTO request) {
        List<Dron> drones = flightService.executeCommandsForAllDrons(request.getDronIds(), request.getOrdenes());

        List<DronDTO> response = new ArrayList<>();
        for (Dron drone : drones) {
            DronDTO dto = DronMapper.toDto(drone);
            response.add(dto);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
