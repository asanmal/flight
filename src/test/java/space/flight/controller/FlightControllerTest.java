package space.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import space.flight.dto.FlightDTO;
import space.flight.entity.Dron;
import space.flight.entity.Matriz;
import space.flight.entity.Orden;
import space.flight.entity.Orientacion;
import space.flight.service.FlightService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FlightControllerTest {

    private FlightService flightService;
    private FlightController flightController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        flightService = mock(FlightService.class);
        flightController = new FlightController(flightService);
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testExecuteCommands() throws Exception {
        Matriz matriz = new Matriz();
        matriz.setMatrizId(1L);
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron = new Dron();
        dron.setDronId(1L);
        dron.setNombre("TestDron");
        dron.setModelo("TestModel");
        dron.setX(1);
        dron.setY(1);
        dron.setOrientacion(Orientacion.E);
        dron.setMatriz(matriz);

        List<Orden> ordenes = new ArrayList<>();

        when(flightService.executeCommands(any(Long.class), any())).thenReturn(dron);

        mockMvc.perform(post("/api/vuelos/flight/{dronId}", dron.getDronId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(ordenes)))
                .andExpect(status().isOk());

        verify(flightService).executeCommands(any(Long.class), any());
    }

    @Test
    void testExecuteCommandsForAllDrons() throws Exception {
        FlightDTO flightDTO = new FlightDTO();
        List<Long> dronIds = List.of(1L, 2L);
        flightDTO.setDronIds(dronIds);

        List<Orden> ordenes = new ArrayList<>();
        ordenes.add(Orden.TURN_LEFT);
        ordenes.add(Orden.MOVE_FORWARD);

        flightDTO.setOrdenes(ordenes);

        Matriz matriz = new Matriz();
        matriz.setMatrizId(1L);
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        Dron dron1 = new Dron();
        dron1.setDronId(1L);
        dron1.setNombre("TestDron1");
        dron1.setModelo("TestModel1");
        dron1.setX(1);
        dron1.setY(1);
        dron1.setOrientacion(Orientacion.E);
        dron1.setMatriz(matriz);

        Dron dron2 = new Dron();
        dron2.setDronId(2L);
        dron2.setNombre("TestDron2");
        dron2.setModelo("TestModel2");
        dron2.setX(2);
        dron2.setY(2);
        dron2.setOrientacion(Orientacion.E);
        dron2.setMatriz(matriz);

        List<Dron> drones = List.of(dron1, dron2);

        when(flightService.executeCommandsForAllDrons(any(), any())).thenReturn(drones);

        mockMvc.perform(post("/api/vuelos/flight/group")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(flightDTO)))
                .andExpect(status().isOk());

        verify(flightService).executeCommandsForAllDrons(any(), any());
        }
}
