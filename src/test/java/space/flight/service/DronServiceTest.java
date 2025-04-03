package space.flight.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import space.flight.application.dto.DronCreateDTO;
import space.flight.application.dto.DronEditDTO;
import space.flight.domain.entity.Dron;
import space.flight.domain.entity.Matriz;
import space.flight.domain.service.DronService;
import space.flight.common.exception.DroneNotFoundException;
import space.flight.common.exception.DroneOutOfMatrixException;
import space.flight.common.exception.DronePositionOccupiedException;
import space.flight.common.exception.MatrixNotFoundException;
import space.flight.infrastructure.repository.DronRepository;
import space.flight.infrastructure.repository.MatrizRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static space.flight.domain.entity.Orientacion.E;

@SpringBootTest
class DronServiceTest {

    private DronRepository dronRepository;
    private MatrizRepository matrizRepository;
    private DronService dronService;

    @BeforeEach
    void setup() {
        dronRepository = mock(DronRepository.class);
        matrizRepository = mock(MatrizRepository.class);
        dronService = new DronService(dronRepository, matrizRepository);

    }

    @Test
    void testCreateDronSuccessfully() {
        DronCreateDTO dto = new DronCreateDTO("DronTest", "DronModel", 1, 1, E, 1L);
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);
        matriz.setDrones(new ArrayList<>());

        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Dron result = dronService.createDron(dto);

        assertNotNull(result);
        verify(dronRepository).save(any());
    }

    @Test
    void testCreateDronOutOfMatrix() {

        DronCreateDTO dto = new DronCreateDTO("DronTest", "DronModel", 6, 6, E, 1L);

        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);
        matriz.setDrones(new ArrayList<>());

        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));

        assertThrows(DroneOutOfMatrixException.class, () -> dronService.createDron(dto));
    }

    @Test
    void testCreateDronPositionOccupied() {
        DronCreateDTO dto = new DronCreateDTO("DronTest", "DronModel", 1, 1, E, 1L);
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setX(1);
        dron.setY(1);
        matriz.setDrones(new ArrayList<>());
        matriz.getDrones().add(dron);

        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));

        assertThrows(DronePositionOccupiedException.class, () -> dronService.createDron(dto));
    }

    @Test
    void testCreateDronMatrixNotFound() {
        DronCreateDTO dto = new DronCreateDTO("DronTest", "DronModel", 1, 1, E, 1L);

        when(matrizRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MatrixNotFoundException.class, () -> dronService.createDron(dto));
    }

    @Test
    void testDeleteDronSuccesfully() {
        Dron dron = new Dron();
        dron.setDronId(1L);
        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));

        dronService.deleteDron(1L);

        verify(dronRepository).delete(dron);
    }

    @Test
    void testDeleteDronNotFound() {
        when(dronRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DroneNotFoundException.class, () -> dronService.deleteDron(1L));
    }

    @Test
    void testEditDronSuccessfully() {
        Matriz matriz = new Matriz();
        matriz.setMatrizId(1L);
        matriz.setMtzX(5);
        matriz.setMtzY(5);
        matriz.setDrones(new ArrayList<>());

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setNombre("DronTest");
        dron.setModelo("DronModel");
        dron.setX(1);
        dron.setY(1);
        dron.setOrientacion(E);
        dron.setMatriz(matriz);

        DronEditDTO dto = new DronEditDTO("test", "successfully", 2, 2, E, 1L);

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Dron result = dronService.editDron(dron.getDronId(), dto);

        assertEquals("test", result.getNombre());
        assertEquals("successfully", result.getModelo());
        assertEquals(2, result.getX());
        assertEquals(2, result.getY());
        assertEquals(E, result.getOrientacion());
        assertEquals(1L, result.getMatriz().getMatrizId());

    }

    @Test
    void testEditDronOutOfMatrix() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);
        matriz.setDrones(new ArrayList<>());

        Dron existingDron = new Dron();
        existingDron.setDronId(1L);
        existingDron.setNombre("DronTest");
        existingDron.setModelo("DronModel");
        existingDron.setX(1);
        existingDron.setY(1);
        existingDron.setOrientacion(E);
        existingDron.setMatriz(matriz);

        when(dronRepository.findById(1L)).thenReturn(Optional.of(existingDron));
        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));

        DronEditDTO dto = new DronEditDTO("DronTest", "DronModel", 6, 6, E, 1L);



        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));

        assertThrows(DroneOutOfMatrixException.class, () -> dronService.editDron(1L, dto));
    }

    @Test
    void testEditDronPositionOccupied() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setNombre("DronTest");
        dron.setModelo("DronModel");
        dron.setX(1);
        dron.setY(1);
        dron.setOrientacion(E);
        dron.setMatriz(matriz);

        Dron dron2 = new Dron();
        dron2.setDronId(2L);
        dron2.setNombre("DronTest2");
        dron2.setModelo("DronModel2");
        dron2.setX(3);
        dron2.setY(2);
        dron2.setOrientacion(E);
        dron2.setMatriz(matriz);

        List<Dron> drons = new ArrayList<>();
        drons.add(dron);
        drons.add(dron2);
        matriz.setDrones(drons);

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));

        DronEditDTO dto = new DronEditDTO("DronTest", "DronModel", 3, 2, E, 1L);

        assertThrows(DronePositionOccupiedException.class, () -> dronService.editDron(1L, dto));
    }

    @Test
    void testEditDronMatrixNotFound() {
        Dron existingDron = new Dron();
        existingDron.setDronId(1L);
        existingDron.setNombre("DronTest");
        existingDron.setModelo("DronModel");
        existingDron.setX(1);
        existingDron.setY(1);
        existingDron.setOrientacion(E);

        Matriz matriz = new Matriz();
        existingDron.setMatriz(matriz);

        when(dronRepository.findById(1L)).thenReturn(Optional.of(existingDron));

        when(matrizRepository.findById(100L)).thenReturn(Optional.empty());

        DronEditDTO dto = new DronEditDTO("DronTest", "DronModel", 1, 1, E, 100L);

        assertThrows(MatrixNotFoundException.class, () -> dronService.editDron(1L, dto));
    }

    @Test
    void testEditDronNotFound() {
        DronEditDTO dto = new DronEditDTO("DronTest", "DronModel", 1, 1, E, 1L);

        when(dronRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DroneNotFoundException.class, () -> dronService.editDron(1L, dto));
    }

    @Test
    void testFindAllDrons(){
        List<Dron> drones = List.of(new Dron(), new Dron());
        when(dronRepository.findAll()).thenReturn(drones);

        List<Dron> result = dronService.findAllDrons();

        assertEquals(2, result.size());
    }

    @Test
    void testFindAllDronsByMatriz(){
        Matriz matriz = new Matriz();
        matriz.setMatrizId(1L);
        matriz.setDrones(new ArrayList<>());

        Dron dron1 = new Dron();
        dron1.setDronId(1L);
        dron1.setMatriz(matriz);

        Dron dron2 = new Dron();
        dron2.setDronId(2L);
        dron2.setMatriz(matriz);

        matriz.getDrones().add(dron1);
        matriz.getDrones().add(dron2);

        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));

        List<Dron> result = dronService.findAllDronsByMatriz(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testFindAllDronsByMatrizNotFound(){
        when(matrizRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MatrixNotFoundException.class, () -> dronService.findAllDronsByMatriz(1L));
    }

    @Test
    void testFindAllDronsByMatrizNoDrones(){
        Matriz matriz = new Matriz();
        matriz.setMatrizId(1L);
        matriz.setDrones(new ArrayList<>());

        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));

        List<Dron> result = dronService.findAllDronsByMatriz(1L);

        assertEquals(0, result.size());
    }

    @Test
    void testFindDronByPositions() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setX(2);
        dron.setY(3);

        List<Dron> drons = List.of(dron);
        matriz.setDrones(drons);

        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));

        Dron result = dronService.findDronByPosition(2, 3, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getDronId());
        assertEquals(2, result.getX());
        assertEquals(3, result.getY());
    }

    @Test
    void testFindDronByPositionNotFound() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);
        matriz.setDrones(new ArrayList<>());

        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));

        assertThrows(DroneNotFoundException.class, () -> dronService.findDronByPosition(2, 3, 1L));
    }

    @Test
    void testFindDronByPositionMatrixNotFound() {
        when(matrizRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(MatrixNotFoundException.class, () -> dronService.findDronByPosition(2, 3, 100L));
    }

    @Test
    void testFindDronByPositionOutOfMatrix() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);
        matriz.setDrones(new ArrayList<>());

        when(matrizRepository.findById(1L)).thenReturn(Optional.of(matriz));

        assertThrows(DroneOutOfMatrixException.class, () -> dronService.findDronByPosition(6, 6, 1L));
    }

}
