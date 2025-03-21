package space.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.flight.entity.Matriz;

@Repository
public interface MatrizRepository extends JpaRepository<Matriz, Long> {
}
