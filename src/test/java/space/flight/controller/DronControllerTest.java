package space.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import space.flight.dto.DronCreateDTO;
import space.flight.entity.Dron;
import space.flight.entity.Matriz;
import space.flight.entity.Orientacion;
import space.flight.service.DronService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class DronControllerTest {

    private DronService dronService;
    private DronController dronController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        dronService = mock(DronService.class);
        dronController = new DronController(dronService);
        mockMvc = MockMvcBuilders.standaloneSetup(dronController).build();
    }

    @Test
    void testCreateDron() throws Exception {
        DronCreateDTO dto = new DronCreateDTO("TestDron", "TestModel", 1, 1, Orientacion.E, 1L);

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

        when(dronService.createDron(any())).thenReturn(dron);

        mockMvc.perform(post("/api/vuelos/dron/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(dronService).createDron(any());
    }

    @Test
    void testEditDron() throws Exception {
        DronCreateDTO dto = new DronCreateDTO("TestDron", "TestModel", 1, 1, Orientacion.E, 1L);

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

        when(dronService.editDron(any(), any())).thenReturn(dron);

        mockMvc.perform(put("/api/vuelos/dron/edit/{dronId}", dron.getDronId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(dronService).editDron(any(), any());
    }

    @Test
    void testDeleteDron() throws Exception {
        Dron dron = new Dron();
        dron.setDronId(1L);
        mockMvc.perform(delete("/api/vuelos/dron/delete/{dronId}", dron.getDronId()))
                .andExpect(status().isNoContent());

        verify(dronService).deleteDron(1L);
    }

    @Test
    void testFindAllDrons() throws Exception {
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

        when(dronService.findAllDrons()).thenReturn(List.of(dron));

        mockMvc.perform(get("/api/vuelos/dron/list"))
                .andExpect(status().isOk());

        verify(dronService).findAllDrons();
    }

    @Test
    void testFindAllDronsByMatriz() throws Exception {
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

        when(dronService.findAllDronsByMatriz(1L)).thenReturn(List.of(dron));

        mockMvc.perform(get("/api/vuelos/dron/list/{matrizId}", dron.getMatriz().getMatrizId()))
                .andExpect(status().isOk());

        verify(dronService).findAllDronsByMatriz(1L);
    }

    @Test
    void testFindDronByPosition() throws Exception {
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

        when(dronService.findDronByPosition(1, 1, 1L)).thenReturn(dron);

        mockMvc.perform(get("/api/vuelos/dron/list/{x}/{y}/{matrizId}", dron.getX(), dron.getY(), dron.getMatriz().getMatrizId()))
                .andExpect(status().isOk());

        verify(dronService).findDronByPosition(1, 1, 1L);
    }

}
