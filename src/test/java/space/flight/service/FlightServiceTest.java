package space.flight.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import space.flight.entity.Dron;
import space.flight.entity.Matriz;
import space.flight.entity.Orden;
import space.flight.exception.DroneNotFoundException;
import space.flight.exception.DroneOutOfMatrixException;
import space.flight.exception.DronePositionOccupiedException;
import space.flight.exception.UnknownOrientationException;
import space.flight.repository.DronRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static space.flight.entity.Orientacion.*;

@SpringBootTest
class FlightServiceTest {

    private DronRepository dronRepository;
    private FlightService flightService;

    @BeforeEach
    void setUp() {
        dronRepository = mock(DronRepository.class);
        flightService = new FlightService(dronRepository);
    }

    @Test
    void testExecuteCommandsMoveForward() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setX(0);
        dron.setY(0);
        dron.setOrientacion(N);
        dron.setMatriz(matriz);
        matriz.setDrones(List.of(dron));

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Orden> ordenes =  List.of(Orden.MOVE_FORWARD);
        Dron result = flightService.executeCommands(1L, ordenes);

        assertEquals(0, result.getX());
        assertEquals(1, result.getY());
        assertEquals(N, result.getOrientacion());

        verify(dronRepository).save(dron);
    }

    @Test
    void testExecuteCommandsTurnLeft() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setX(0);
        dron.setY(0);
        dron.setOrientacion(N);
        dron.setMatriz(matriz);
        matriz.setDrones(List.of(dron));

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Orden> ordenes =  List.of(Orden.TURN_LEFT);
        Dron result = flightService.executeCommands(1L, ordenes);

        assertEquals(0, result.getX());
        assertEquals(0, result.getY());
        assertEquals(O, result.getOrientacion());

        verify(dronRepository).save(dron);
    }

    @Test
    void testExecuteCommandsTurnLeftFromEast() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setX(0);
        dron.setY(0);
        dron.setOrientacion(E);
        dron.setMatriz(matriz);
        matriz.setDrones(List.of(dron));

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Orden> ordenes =  List.of(Orden.TURN_LEFT);
        Dron result = flightService.executeCommands(1L, ordenes);

        assertEquals(0, result.getX());
        assertEquals(0, result.getY());
        assertEquals(N, result.getOrientacion());

        verify(dronRepository).save(dron);
    }

    @Test
    void testExecuteCommandsTurnLeftFromSouth() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setX(0);
        dron.setY(0);
        dron.setOrientacion(S);
        dron.setMatriz(matriz);
        matriz.setDrones(List.of(dron));

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Orden> ordenes =  List.of(Orden.TURN_LEFT);
        Dron result = flightService.executeCommands(1L, ordenes);

        assertEquals(0, result.getX());
        assertEquals(0, result.getY());
        assertEquals(E, result.getOrientacion());

        verify(dronRepository).save(dron);
    }

    @Test
    void testExecuteCommandsTurnLeftFromWest() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setX(0);
        dron.setY(0);
        dron.setOrientacion(O);
        dron.setMatriz(matriz);
        matriz.setDrones(List.of(dron));

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Orden> ordenes =  List.of(Orden.TURN_LEFT);
        Dron result = flightService.executeCommands(1L, ordenes);

        assertEquals(0, result.getX());
        assertEquals(0, result.getY());
        assertEquals(S, result.getOrientacion());

        verify(dronRepository).save(dron);
    }

    @Test
    void testTurnLeftInvalidOrientation() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setX(3);
        dron.setY(3);
        dron.setOrientacion(null);
        dron.setMatriz(matriz);
        matriz.setDrones(List.of(dron));

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Orden> ordenes =  List.of(Orden.TURN_LEFT);

        assertThrows(UnknownOrientationException.class, () -> {
            flightService.executeCommands(1L, ordenes);
        });
    }

    @Test
    void testExecuteCommandsTurnRight() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setX(0);
        dron.setY(0);
        dron.setOrientacion(N);
        dron.setMatriz(matriz);
        matriz.setDrones(List.of(dron));

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Orden> ordenes =  List.of(Orden.TURN_RIGHT);
        Dron result = flightService.executeCommands(1L, ordenes);

        assertEquals(0, result.getX());
        assertEquals(0, result.getY());
        assertEquals(E, result.getOrientacion());

        verify(dronRepository).save(dron);
    }

    @Test
    void testExecuteCommandsTurnRightFromEast() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setX(0);
        dron.setY(0);
        dron.setOrientacion(E);
        dron.setMatriz(matriz);
        matriz.setDrones(List.of(dron));

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Orden> ordenes =  List.of(Orden.TURN_RIGHT);
        Dron result = flightService.executeCommands(1L, ordenes);

        assertEquals(0, result.getX());
        assertEquals(0, result.getY());
        assertEquals(S, result.getOrientacion());

        verify(dronRepository).save(dron);
    }

    @Test
    void testExecuteCommandsTurnRightFromSouth() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setX(0);
        dron.setY(0);
        dron.setOrientacion(S);
        dron.setMatriz(matriz);
        matriz.setDrones(List.of(dron));

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Orden> ordenes =  List.of(Orden.TURN_RIGHT);
        Dron result = flightService.executeCommands(1L, ordenes);

        assertEquals(0, result.getX());
        assertEquals(0, result.getY());
        assertEquals(O, result.getOrientacion());

        verify(dronRepository).save(dron);
    }

    @Test
    void testExecuteCommandsTurnRightFromWest() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setX(0);
        dron.setY(0);
        dron.setOrientacion(O);
        dron.setMatriz(matriz);
        matriz.setDrones(List.of(dron));

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Orden> ordenes =  List.of(Orden.TURN_RIGHT);
        Dron result = flightService.executeCommands(1L, ordenes);

        assertEquals(0, result.getX());
        assertEquals(0, result.getY());
        assertEquals(N, result.getOrientacion());

        verify(dronRepository).save(dron);
    }

    @Test
    void testTurnRightInvalidOrientation() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setX(3);
        dron.setY(3);
        dron.setOrientacion(null);
        dron.setMatriz(matriz);
        matriz.setDrones(List.of(dron));

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Orden> ordenes =  List.of(Orden.TURN_RIGHT);

        assertThrows(UnknownOrientationException.class, () -> {
            flightService.executeCommands(1L, ordenes);
        });
    }

    @Test
    void testExecuteCommandsOutOfMatrix() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(1);
        matriz.setMtzY(1);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setX(0);
        dron.setY(0);
        dron.setOrientacion(N);
        dron.setMatriz(matriz);
        matriz.setDrones(List.of(dron));

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Orden> ordenes =  List.of(Orden.MOVE_FORWARD, Orden.MOVE_FORWARD);

        assertThrows(DroneOutOfMatrixException.class, () -> {
            flightService.executeCommands(1L, ordenes);
        });

    }

    @Test
    void testExecuteCommandsPositionOccupied() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron1 = new Dron();
        dron1.setDronId(1L);
        dron1.setX(0);
        dron1.setY(0);
        dron1.setOrientacion(N);
        dron1.setMatriz(matriz);

        Dron dron2 = new Dron();
        dron2.setDronId(2L);
        dron2.setX(0);
        dron2.setY(1);
        dron2.setOrientacion(N);
        dron2.setMatriz(matriz);

        matriz.setDrones(List.of(dron1, dron2));

        when(dronRepository.findById(1L)).thenReturn(Optional.of(dron1));
        when(dronRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        List<Orden> ordenes =  List.of(Orden.MOVE_FORWARD);

        assertThrows(DronePositionOccupiedException.class, () -> {
            flightService.executeCommands(1L, ordenes);
        });
    }

    @Test
    void testExecuteCommandsForAllDrones() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron1 = new Dron();
        dron1.setDronId(1L);
        dron1.setX(1);
        dron1.setY(0);
        dron1.setOrientacion(O);
        dron1.setMatriz(matriz);

        Dron dron2 = new Dron();
        dron2.setDronId(2L);
        dron2.setX(1);
        dron2.setY(1);
        dron2.setOrientacion(E);
        dron2.setMatriz(matriz);

        Dron dron3 = new Dron();
        dron3.setDronId(3L);
        dron3.setX(1);
        dron3.setY(1);
        dron3.setOrientacion(S);
        dron3.setMatriz(matriz);
        matriz.setDrones(List.of(dron1, dron2, dron3));

        when(dronRepository.findAllById(List.of(1L, 2L, 3L))).thenReturn(List.of(dron1, dron2, dron3));
        when(dronRepository.findById(any())).thenAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            if (id.equals(1L)) {
                return Optional.of(dron1);
            } else if (id.equals(2L)) {
                return Optional.of(dron2);
            } else if (id.equals(3L)) {
                return Optional.of(dron3);
            } else {
                return Optional.empty();
            }
        });
        when(dronRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        List<Dron> result = flightService.executeCommandsForAllDrons(List.of(1L, 2L, 3L), List.of(Orden.MOVE_FORWARD));

        assertEquals(3, result.size());
        assertEquals(0, dron1.getX());
        assertEquals(0, dron1.getY());
        assertEquals(2, dron2.getX());
        assertEquals(1, dron2.getY());
        assertEquals(1, dron3.getX());
        assertEquals(0, dron3.getY());
    }

    @Test
    void testExecuteCommandsForAllDronsPositionOccupied() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron1 = new Dron();
        dron1.setDronId(1L);
        dron1.setX(0);
        dron1.setY(0);
        dron1.setOrientacion(N);
        dron1.setMatriz(matriz);

        Dron dron2 = new Dron();
        dron2.setDronId(2L);
        dron2.setX(0);
        dron2.setY(0);
        dron2.setOrientacion(N);
        dron2.setMatriz(matriz);

        matriz.setDrones(List.of(dron1, dron2));

        when(dronRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(dron1, dron2));
        when(dronRepository.findById(any())).thenAnswer(inv -> {
            Long id = inv.getArgument(0);
            return Optional.of(id == 1L ? dron1 : dron2);
        });
        when(dronRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        List<Orden> ordenes =  List.of(Orden.MOVE_FORWARD);

        assertThrows(DronePositionOccupiedException.class, () -> {
            flightService.executeCommandsForAllDrons(List.of(1L, 2L), ordenes);
        });
    }

    @Test
    void testExecuteCommandsForAllDronsNotFound() {
        when(dronRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of());

        List<Orden> ordenes =  List.of(Orden.MOVE_FORWARD);

        assertThrows(DroneNotFoundException.class, () -> {
            flightService.executeCommandsForAllDrons(List.of(1L, 2L), ordenes);
        });
    }

    @Test
    void testExecuteCommandsForAllDronsWithMissingIds() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron1 = new Dron();
        dron1.setDronId(1L);
        dron1.setX(0);
        dron1.setY(0);
        dron1.setOrientacion(N);
        dron1.setMatriz(matriz);

        matriz.setDrones(List.of(dron1));

        when(dronRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(dron1));
        when(dronRepository.findById(any())).thenAnswer(inv -> {
            Long id = inv.getArgument(0);
            return Optional.of(id == 1L ? dron1 : null);
        });
        when(dronRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        List<Orden> ordenes =  List.of(Orden.MOVE_FORWARD);

        assertThrows(DroneNotFoundException.class, () -> {
            flightService.executeCommandsForAllDrons(List.of(1L, 2L), ordenes);
        });
    }

    @Test
    void testExecuteCommandsForAllDronsWithEmptyList() {
        List<Dron> result = flightService.executeCommandsForAllDrons(List.of(), List.of(Orden.MOVE_FORWARD));

        assertEquals(0, result.size());
        verify(dronRepository, never()).save(any());
    }

    @Test
    void testExecuteCommandsForAllDronsEmptyOrders() {
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron1 = new Dron();
        dron1.setDronId(1L);
        dron1.setX(0);
        dron1.setY(0);
        dron1.setOrientacion(S);
        dron1.setMatriz(matriz);

        matriz.setDrones(List.of(dron1));

        when(dronRepository.findAllById(List.of(1L))).thenReturn(List.of(dron1));
        when(dronRepository.findById(any())).thenAnswer(inv -> {
            Long id = inv.getArgument(0);
            return Optional.of(id == 1L ? dron1 : null);
        });
        when(dronRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        List<Orden> ordenes =  List.of();

        List<Dron> result = flightService.executeCommandsForAllDrons(List.of(1L), ordenes);

        assertEquals(1, result.size());
        assertEquals(0, dron1.getX());
        assertEquals(0, dron1.getY());
    }

}
