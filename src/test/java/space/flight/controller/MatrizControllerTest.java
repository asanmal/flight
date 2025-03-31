package space.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import space.flight.dto.MatrizCreateDTO;
import space.flight.dto.MatrizDTO;
import space.flight.dto.MatrizEditDTO;
import space.flight.entity.Matriz;
import space.flight.service.MatrizService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MatrizControllerTest {

    private MatrizService matrizService;
    private MatrizController matrizController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        matrizService = mock(MatrizService.class);
        matrizController = new MatrizController(matrizService);
        mockMvc = MockMvcBuilders.standaloneSetup(matrizController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateMatriz() throws Exception {
        MatrizCreateDTO dto = new MatrizCreateDTO(5, 5);

        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        when(matrizService.createMatriz(any(MatrizCreateDTO.class))).thenReturn(matriz);

        mockMvc.perform(post("/api/vuelos/matriz/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(matrizService).createMatriz(any(MatrizCreateDTO.class));
    }

    @Test
    void testEditMatriz() throws Exception {
        MatrizEditDTO dto = new MatrizEditDTO(15, 12);

        Matriz matriz = new Matriz();
        matriz.setMatrizId(1L);
        matriz.setMtzX(5);
        matriz.setMtzY(5);

        when(matrizService.editMatriz(any(Long.class), any(MatrizEditDTO.class))).thenReturn(matriz);

        mockMvc.perform(put("/api/vuelos/matriz/edit/{matrizId}", matriz.getMatrizId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(matrizService).editMatriz(any(Long.class), any(MatrizEditDTO.class));

    }

    @Test
    void testDeleteMatriz() throws Exception {
        mockMvc.perform(delete("/api/vuelos/matriz/delete/{matrizId}", 1L))
                .andExpect(status().isNoContent());

        verify(matrizService).deleteMatriz(1L);
    }

    @Test
    void testGetAllMatrices() throws Exception {
        MatrizDTO dto = new MatrizDTO();
        dto.setMatrizId(1L);
        dto.setMtzX(5);
        dto.setMtzY(5);

        when(matrizService.findAllMatrizDTO()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/vuelos/matriz/list"))
                .andExpect(status().isOk());

        verify(matrizService).findAllMatrizDTO();
    }

    @Test
    void testGetMatrizById() throws Exception {
        MatrizDTO dto = new MatrizDTO();
        dto.setMatrizId(1L);
        dto.setMtzX(5);
        dto.setMtzY(5);

        when(matrizService.findMatrizById(any(Long.class))).thenReturn(dto);

        mockMvc.perform(get("/api/vuelos/matriz/{matrizId}", dto.getMatrizId()))
                .andExpect(status().isOk());

        verify(matrizService).findMatrizById(any(Long.class));
    }
}
