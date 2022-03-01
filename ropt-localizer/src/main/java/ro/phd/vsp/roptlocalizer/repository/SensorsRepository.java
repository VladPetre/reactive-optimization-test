package ro.phd.vsp.roptlocalizer.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.phd.vsp.roptlocalizer.model.Sensor;

public interface SensorsRepository extends JpaRepository<Sensor, UUID> {

}
