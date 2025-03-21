package space.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.flight.entity.Dron;

@Repository
public interface DronRepository extends JpaRepository<Dron, Long> {
}
