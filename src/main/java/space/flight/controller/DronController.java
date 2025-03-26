package space.flight.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.flight.dto.DronCreateDTO;
import space.flight.dto.DronDTO;
import space.flight.dto.DronEditDTO;
import space.flight.entity.Dron;
import space.flight.mapper.DronMapper;
import space.flight.service.DronService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vuelos/dron")
@RequiredArgsConstructor
public class DronController {

    private final DronService dronService;

    // Crear un dron en el sistema
    @Operation(summary = "Crear un nuevo dron")
    @PostMapping("/create")
    public ResponseEntity<DronDTO> createDron(@Valid @RequestBody DronCreateDTO dronDTO) {
        Dron saved = dronService.createDron(dronDTO);
        // Conversion a DTO sin que nos salga todos los valores que no deseamos
        DronDTO response = DronMapper.toDto(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Editar un dron en el sistema
    @Operation(summary = "Editar un dron por su ID")
    @PutMapping("/edit/{dronId}")
    public ResponseEntity<DronDTO> editDron(@PathVariable Long dronId,@Valid @RequestBody DronEditDTO dronDTO) {
        Dron updated = dronService.editDron(dronId, dronDTO);
        // Conversion a DTO sin que nos salga todos los valores que no deseamos
        DronDTO response = DronMapper.toDto(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Eliminar un dron del sistema
    @Operation(summary = "Eliminar un dron por su ID")
    @DeleteMapping("/delete/{dronId}")
    public ResponseEntity<Void> deleteDron(@PathVariable Long dronId) {
        dronService.deleteDron(dronId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/list")
    @Operation(summary = "Listar los drones")
    public ResponseEntity<List<DronDTO>> findAllDrons() {
        List<Dron> drones = dronService.findAllDrons();
        List<DronDTO> response = new ArrayList<>();
        for (Dron drone : drones) {
            DronDTO dto = DronMapper.toDto(drone);
            response.add(dto);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list/{matrizId}")
    @Operation(summary = "Listar drones de una matriz por su id")
    public ResponseEntity<List<DronDTO>> findAllDronsByMatriz(@PathVariable Long matrizId) {
        List<Dron> drones = dronService.findAllDronsByMatriz(matrizId);
        List<DronDTO> response = new ArrayList<>();
        for (Dron drone : drones) {
            DronDTO dto = DronMapper.toDto(drone);
            response.add(dto);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list/{x}/{y}/{matrizId}")
    @Operation(summary = "Buscar dron por coordenadas e id de matriz")
    public ResponseEntity<DronDTO> findDronByPosition(@PathVariable int x, @PathVariable int y, @PathVariable Long matrizId) {
        Dron dron = dronService.findDronByPosition(x, y, matrizId);
        DronDTO response = DronMapper.toDto(dron);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
