package space.flight.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatrizDTO {
    private Long matrizId;
    private int mtzX;
    private int mtzY;

    private List<DronDTO> drones;
}
