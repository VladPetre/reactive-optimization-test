package ro.phd.vsp.roptlocalizerreactive.repository;

import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ro.phd.vsp.roptlocalizerreactive.model.Sensor;

public interface SensorsReactiveRepository extends ReactiveCrudRepository<Sensor, UUID> {

}
