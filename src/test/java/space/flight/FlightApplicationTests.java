package space.flight;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class FlightApplicationTests {

	@Test
	void contextLoads() {
		// Este test pasa si el contexto de la aplicación se carga correctamente.
	}

	@Test
	void testMain() {
		assertDoesNotThrow(() -> FlightApplication.main(new String[] {}));
	}

}
