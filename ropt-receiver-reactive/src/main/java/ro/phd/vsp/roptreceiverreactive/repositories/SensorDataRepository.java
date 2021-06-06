package ro.phd.vsp.roptreceiverreactive.repositories;

import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ro.phd.vsp.roptreceiverreactive.models.SensorData;

public interface SensorDataRepository extends ReactiveCrudRepository<SensorData, UUID> {

}
