package space.flight.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.flight.domain.entity.Dron;

@Repository
public interface DronRepository extends JpaRepository<Dron, Long> {
}
