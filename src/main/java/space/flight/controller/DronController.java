package space.flight.controller;

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
    @PostMapping("/create")
    public ResponseEntity<DronDTO> createDron(@RequestBody DronCreateDTO dronDTO) {
        Dron saved = dronService.createDron(dronDTO);
        // Conversion a DTO sin que nos salga todos los valores que no deseamos
        DronDTO response = DronMapper.toDto(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Editar un dron en el sistema
    @PutMapping("/edit/{dronId}")
    public ResponseEntity<DronDTO> editDron(@PathVariable Long dronId, @RequestBody DronEditDTO dronDTO) {
        Dron updated = dronService.editDron(dronId, dronDTO);
        // Conversion a DTO sin que nos salga todos los valores que no deseamos
        DronDTO response = DronMapper.toDto(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Eliminar un dron del sistema
    @DeleteMapping("/delete/{dronId}")
    public ResponseEntity<Void> deleteDron(@PathVariable Long dronId) {
        dronService.deleteDron(dronId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

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

    @GetMapping("/list/{matrizId}")
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
    public ResponseEntity<DronDTO> findDronByPosition(@PathVariable int x, @PathVariable int y, @PathVariable Long matrizId) {
        Dron dron = dronService.findDronByPosition(x, y, matrizId);
        DronDTO response = DronMapper.toDto(dron);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
