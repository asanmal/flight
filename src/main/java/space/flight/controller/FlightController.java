package space.flight.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import space.flight.service.FlightService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vuelos/flight")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    // Ejectuar ordenes en un dron por su ID
    @Operation(summary = "Ejecutar ordenes en un dron por su ID", responses = {
            @ApiResponse(responseCode = "200", description = "Ordenes ejecutadas correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o fuera de matriz"),
            @ApiResponse(responseCode = "404", description = "Dron no encontrados"),
            @ApiResponse(responseCode = "409", description = "Posición ya ocupada por otro dron")
    })
    @PostMapping("/{dronId}")
        public ResponseEntity<DronDTO> executeCommands(@PathVariable Long dronId, @Valid @RequestBody List<Orden> ordenes) {
        Dron updated = flightService.executeCommands(dronId, ordenes);
        DronDTO response = DronMapper.toDto(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Ejectuar ordenes en multiples dron por su ID
    @Operation( summary = "Ejecutar ordenes para multiples dron por su ID", responses = {
            @ApiResponse(responseCode = "200", description = "Ordenes ejecutadas correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o fuera de matriz"),
            @ApiResponse(responseCode = "404", description = "Dron no encontrados"),
            @ApiResponse(responseCode = "409", description = "Posición ya ocupada por otro dron")
    })
    @PostMapping("/group")
    public ResponseEntity<List<DronDTO>> executeCommandsForAllDrons(@Valid @RequestBody FlightDTO request) {
        List<Dron> drones = flightService.executeCommandsForAllDrons(request.getDronIds(), request.getOrdenes());

        List<DronDTO> response;
        response = new ArrayList<>();
        for (Dron drone : drones) {
            DronDTO dto = DronMapper.toDto(drone);
            response.add(dto);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
