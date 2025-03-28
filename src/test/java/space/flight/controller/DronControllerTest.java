package space.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import space.flight.service.DronService;

@WebMvcTest(DronController.class)
public class DronControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DronService dronService;

}
