package space.flight.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import space.flight.entity.Dron;
import space.flight.entity.Matriz;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static space.flight.entity.Orientacion.E;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DronRepositoryTest {

    @Autowired
    private DronRepository dronRepository;

    @Autowired
    private MatrizRepository matrizRepository;

    @Test
    void testGuardarYRecuperarDron() {
        // Crear matriz
        Matriz matriz = new Matriz();
        matriz.setMtzX(5);
        matriz.setMtzY(5);
        matriz = matrizRepository.save(matriz);

        // Crear dron
        Dron dron = new Dron();
        dron.setNombre("DronTest");
        dron.setModelo("DronModel");
        dron.setX(1);
        dron.setY(1);
        dron.setOrientacion(E);
        dron.setMatriz(matriz);

        // Guardar dron
        Dron guardado = dronRepository.save(dron);

        // Buscar por ID
        Optional<Dron> encontrado;
        encontrado = dronRepository.findById(guardado.getDronId());

        assertTrue(encontrado.isPresent());
        assertEquals("DronTest", encontrado.get().getNombre());
        assertEquals("DronModel", encontrado.get().getModelo());
        assertEquals(1, encontrado.get().getX());
        assertEquals(1, encontrado.get().getY());
        assertEquals(E, encontrado.get().getOrientacion());
        assertEquals(matriz.getMatrizId(), encontrado.get().getMatriz().getMatrizId());

        // Listar todos los drones
        List<Dron> todos = dronRepository.findAll();
        assertFalse(todos.isEmpty());
    }
}
