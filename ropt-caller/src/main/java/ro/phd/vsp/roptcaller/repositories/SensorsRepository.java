package ro.phd.vsp.roptcaller.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.phd.vsp.roptcaller.models.Sensor;

public interface SensorsRepository extends JpaRepository<Sensor, UUID> {

}
