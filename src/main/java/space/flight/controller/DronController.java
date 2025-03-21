package space.flight.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.flight.entity.Dron;
import space.flight.service.DronService;

@RestController
@RequestMapping("/api/vuelos/dron")
@RequiredArgsConstructor
public class DronController {

    private final DronService dronService;

    // Crear un dron en el sistema
    @PostMapping("/create")
    public ResponseEntity<Dron> createDron(@RequestBody Dron dron) {
        Dron savedDron = dronService.createDron(dron);
        return new ResponseEntity<>(savedDron, HttpStatus.CREATED);
    }

}
