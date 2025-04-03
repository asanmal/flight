package space.flight.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.flight.application.dto.DronCreateDTO;
import space.flight.application.dto.DronDTO;
import space.flight.application.dto.DronEditDTO;
import space.flight.domain.entity.Dron;
import space.flight.application.mapper.DronMapper;
import space.flight.domain.service.DronService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vuelos/dron")
@RequiredArgsConstructor
public class DronController {
    private final DronService dronService;

    // Crear un dron en el sistema
    @Operation(summary = "Crear un nuevo dron", responses = {
    @ApiResponse (responseCode = "201", description = "Dron creado exitosamente"),
    @ApiResponse (responseCode = "400", description = "Petición inválida"),
    @ApiResponse (responseCode = "409", description = "El dron ya existe")})
    @PostMapping("/create")
    public ResponseEntity<DronDTO> createDron(@Valid @RequestBody DronCreateDTO dronDTO) {
        Dron saved = dronService.createDron(dronDTO);
        // Conversion a DTO sin que nos salga todos los valores que no deseamos
        DronDTO response = DronMapper.toDto(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // Editar un dron en el sistema
    @Operation(summary = "Editar un dron por su ID", responses = {
            @ApiResponse(responseCode = "200", description = "Dron editado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o fuera de matriz"),
            @ApiResponse(responseCode = "404", description = "Dron o matriz no encontrados"),
            @ApiResponse(responseCode = "409", description = "Posición ya ocupada por otro dron")
    })
    @PutMapping("/edit/{dronId}")
    public ResponseEntity<DronDTO> editDron(@PathVariable Long dronId,@Valid @RequestBody DronEditDTO dronDTO) {
        Dron updated = dronService.editDron(dronId, dronDTO);
        // Conversion a DTO sin que nos salga todos los valores que no deseamos
        DronDTO response = DronMapper.toDto(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Eliminar un dron del sistema
    @Operation(summary = "Eliminar un dron por su ID", responses = {
            @ApiResponse(responseCode = "204", description = "Dron eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Dron no encontrado")
    })
    @DeleteMapping("/delete/{dronId}")
    public ResponseEntity<Void> deleteDron(@PathVariable Long dronId) {
        dronService.deleteDron(dronId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // Listar todos los drones del sistema
    @Operation(summary = "Listar los drones", responses = {
            @ApiResponse(responseCode = "200", description = "Drones listados correctamente")
    })
    @GetMapping("/list")
    public ResponseEntity<List<DronDTO>> findAllDrons() {
        List<Dron> drones = dronService.findAllDrons();
        List<DronDTO> response = new ArrayList<>();
        for (Dron drone : drones) {
            DronDTO dto = DronMapper.toDto(drone);
            response.add(dto);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Listar drones de una matriz por su id
    @Operation(summary = "Listar drones de una matriz por su id", responses = {
            @ApiResponse(responseCode = "200", description = "Drones listados correctamente"),
            @ApiResponse(responseCode = "404", description = "Matriz no encontrada")
    })
    @GetMapping("/list/{matrizId}")
        public ResponseEntity<List<DronDTO>> findAllDronsByMatriz(@PathVariable Long matrizId) {
        List<Dron> drones = dronService.findAllDronsByMatriz(matrizId);
        List<DronDTO> response;
        response = new ArrayList<>();
        for (Dron drone : drones) {
            DronDTO dto = DronMapper.toDto(drone);
            response.add(dto);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Listar drones por sus coordenadas e id
    @Operation(summary = "Buscar dron por coordenadas e id de matriz", responses = {
            @ApiResponse(responseCode = "200", description = "Dron encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Dron no encontrado")
    })
    @GetMapping("/list/{x}/{y}/{matrizId}")
    public ResponseEntity<DronDTO> findDronByPosition(@PathVariable int x, @PathVariable int y, @PathVariable Long matrizId) {
        Dron dron = dronService.findDronByPosition(x, y, matrizId);
        DronDTO response = DronMapper.toDto(dron);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
