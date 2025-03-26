package space.flight.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.flight.dto.MatrizCreateDTO;
import space.flight.dto.MatrizDTO;
import space.flight.dto.MatrizEditDTO;
import space.flight.entity.Matriz;
import space.flight.mapper.MatrizMapper;
import space.flight.service.MatrizService;

import java.util.List;

@RestController
@RequestMapping("/api/vuelos/matriz")
@RequiredArgsConstructor
public class MatrizController {

    private final MatrizService matrizService;

    // Crear matriz de vuelo
    @PostMapping("/create")
    @Operation( summary = "Crear matriz de vuelo")
    public ResponseEntity<MatrizDTO> createMatriz(@Valid @RequestBody MatrizCreateDTO dto) {
        Matriz created = matrizService.createMatriz(dto);
        // Conversion a DTO sin que nos salga todos los valores que no deseamos
        MatrizDTO response = MatrizMapper.toDto(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Editar una matriz de vuelo
    @PutMapping("/edit/{matrizId}")
    @Operation( summary = "Editar matriz de vuelo por ID")
    public ResponseEntity<MatrizDTO> editMatriz(@PathVariable Long matrizId, @Valid @RequestBody MatrizEditDTO dto) {
        Matriz updated = matrizService.editMatriz(matrizId, dto);
        // Conversion a DTO sin que nos salga todos los valores que no deseamos
        MatrizDTO response = MatrizMapper.toDto(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Eliminar una matriz de vuelo
    @DeleteMapping("/delete/{matrizId}")
    @Operation( summary = "Eliminar matriz de vuelo por ID")
    public ResponseEntity<Void> deleteMatriz(@PathVariable Long matrizId) {
        matrizService.deleteMatriz(matrizId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Consultar todas las matrices
    @GetMapping("/list")
    @Operation( summary = "Listar las matrices")
    public ResponseEntity<List<MatrizDTO>> getAllMatrices() {
        List<MatrizDTO> matrizDTOs = matrizService.findAllMatrizDTO();
        return new ResponseEntity<>(matrizDTOs, HttpStatus.OK);
    }


    // Consulta una matriz por su ID.
    @GetMapping("/{matrizId}")
    @Operation( summary = "Consultar matriz por ID")
    public ResponseEntity<MatrizDTO> getMatrizById(@PathVariable Long matrizId) {
        MatrizDTO matriz = matrizService.findMatrizById(matrizId);
        return new ResponseEntity<>(matriz, HttpStatus.OK);
    }
}
