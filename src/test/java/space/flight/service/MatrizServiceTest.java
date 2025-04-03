package space.flight.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import space.flight.application.dto.MatrizCreateDTO;
import space.flight.application.dto.MatrizDTO;
import space.flight.application.dto.MatrizEditDTO;
import space.flight.domain.entity.Dron;
import space.flight.domain.entity.Matriz;
import space.flight.domain.service.MatrizService;
import space.flight.common.exception.InvalidMatrixDimensionsException;
import space.flight.common.exception.MatrixNotFoundException;
import space.flight.infrastructure.repository.MatrizRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class MatrizServiceTest {

    private MatrizRepository matrizRepository;
    private MatrizService matrizService;

    @BeforeEach
    void setUp() {
        matrizRepository = mock(MatrizRepository.class);
        matrizService = new MatrizService(matrizRepository);
    }

    @Test
    void testCreateMatrizSuccessfully() {
        MatrizCreateDTO dto = new MatrizCreateDTO();
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        when(matrizRepository.save(any())).thenReturn(matriz);

        Matriz result = matrizService.createMatriz(dto);

        assertEquals(5, result.getMtzX());
        assertEquals(5, result.getMtzY());
    }

    @Test
    void testCreateMatrizInvalidDimensions() {
        MatrizCreateDTO dto = new MatrizCreateDTO(-1, -2);

        assertThrows(InvalidMatrixDimensionsException.class, () -> {
            matrizService.createMatriz(dto);
        });
    }

    @Test
    void testDeleteMatrizSuccessfully() {
        Matriz matriz = new Matriz();

        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));

        matrizService.deleteMatriz(1L);

        verify(matrizRepository).delete(matriz);
    }

    @Test
    void testDeleteMatrizWithDrones() {
        Matriz matriz = new Matriz();
        // Simulamos que la matriz tiene al menos un dron
        Dron dron = new Dron();
        matriz.setDrones(List.of(dron));

        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));

        assertThrows(InvalidMatrixDimensionsException.class, () -> {
            matrizService.deleteMatriz(1L);
        });
    }

    @Test
    void testDeleteMatrizNotFound() {
        when(matrizRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(MatrixNotFoundException.class, () -> {
            matrizService.deleteMatriz(100L);
        });
    }

    @Test
    void testEditMatrizSuccessfully() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        MatrizEditDTO dto = new MatrizEditDTO(15, 15);
        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));
        when(matrizRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Matriz result = matrizService.editMatriz(1L,dto);

        assertEquals(15, result.getMtzX());
        assertEquals(15, result.getMtzY());
    }

    @Test
    void testEditMatrizNotFound() {
        when(matrizRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(MatrixNotFoundException.class, () -> {
            matrizService.editMatriz(100L, new MatrizEditDTO(5, 5));
        });
    }

    @Test
    void testFindMatrizByIdSuccessfully() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));

        MatrizDTO result = matrizService.findMatrizById(1L);

        assertEquals(5, result.getMtzX());
        assertEquals(5, result.getMtzY());
    }

    @Test
    void testFindMatrizByIdNotFound() {
        when(matrizRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(MatrixNotFoundException.class, () -> {
            matrizService.findMatrizById(100L);
        });
    }

    @Test
    void testFindAllMatrices() {
        Matriz matriz1 = new Matriz();
        matriz1.setMtzX(5);
        matriz1.setMtzY(5);

        Matriz matriz2 = new Matriz();
        matriz2.setMtzX(10);
        matriz2.setMtzY(10);

        when(matrizRepository.findAll()).thenReturn(List.of(matriz1, matriz2));

        List<MatrizDTO> result = matrizService.findAllMatrizDTO();

        assertEquals(2, result.size());
        assertEquals(5, result.get(0).getMtzX());
        assertEquals(5, result.get(0).getMtzY());
        assertEquals(10, result.get(1).getMtzX());
        assertEquals(10, result.get(1).getMtzY());
    }

}
