package space.flight.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.flight.application.dto.MatrizCreateDTO;
import space.flight.application.dto.MatrizDTO;
import space.flight.application.dto.MatrizEditDTO;
import space.flight.domain.entity.Matriz;
import space.flight.application.mapper.MatrizMapper;
import space.flight.domain.service.MatrizService;

import java.util.List;

@RestController
@RequestMapping("/api/vuelos/matriz")
@RequiredArgsConstructor
public class MatrizController {
    private final MatrizService matrizService;

    // Crear matriz de vuelo
    @Operation( summary = "Crear matriz de vuelo", responses = {
            @ApiResponse(responseCode = "201", description = "Matriz creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Petición inválida")
    })
    @PostMapping("/create")
    public ResponseEntity<MatrizDTO> createMatriz(@Valid @RequestBody MatrizCreateDTO dto) {
        Matriz created = matrizService.createMatriz(dto);
        // Conversion a DTO sin que nos salga todos los valores que no deseamos
        MatrizDTO response = MatrizMapper.toDto(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // Editar una matriz de vuelo
    @Operation( summary = "Editar matriz de vuelo por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Matriz editada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la matriz inválidos"),
            @ApiResponse(responseCode = "404", description = "Matriz no encontrada")
    })
    @PutMapping("/edit/{matrizId}")
    public ResponseEntity<MatrizDTO> editMatriz(@PathVariable Long matrizId, @Valid @RequestBody MatrizEditDTO dto) {
        Matriz updated = matrizService.editMatriz(matrizId, dto);
        // Conversion a DTO sin que nos salga todos los valores que no deseamos
        MatrizDTO response = MatrizMapper.toDto(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Eliminar una matriz de vuelo
    @Operation( summary = "Eliminar matriz de vuelo por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Matriz eliminada correctamente"),
            @ApiResponse(responseCode = "400", description = "La matriz tiene drones asociados"),
            @ApiResponse(responseCode = "404", description = "Matriz no encontrada")
    })
    @DeleteMapping("/delete/{matrizId}")
    public ResponseEntity<Void> deleteMatriz(@PathVariable Long matrizId) {
        matrizService.deleteMatriz(matrizId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // Consultar todas las matrices
    @Operation( summary = "Listar las matrices", responses = {
            @ApiResponse(responseCode = "200", description = "Matrices listadas correctamente")
    })
    @GetMapping("/list")
    public ResponseEntity<List<MatrizDTO>> getAllMatrices() {
        List<MatrizDTO> matrizDTOs = matrizService.findAllMatrizDTO();
        return new ResponseEntity<>(matrizDTOs, HttpStatus.OK);
    }


    // Consulta una matriz por su ID.
    @Operation( summary = "Consultar matriz por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Matriz encontrada"),
            @ApiResponse(responseCode = "404", description = "Matriz no encontrada")
    })
    @GetMapping("/{matrizId}")
    public ResponseEntity<MatrizDTO> getMatrizById(@PathVariable Long matrizId) {
        MatrizDTO matriz = matrizService.findMatrizById(matrizId);
        return new ResponseEntity<>(matriz, HttpStatus.OK);
    }
}
