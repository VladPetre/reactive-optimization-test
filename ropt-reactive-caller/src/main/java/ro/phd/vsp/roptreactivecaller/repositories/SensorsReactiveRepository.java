package ro.phd.vsp.roptreactivecaller.repositories;

import java.util.UUID;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ro.phd.vsp.roptreactivecaller.models.Sensor;

public interface SensorsReactiveRepository extends ReactiveCrudRepository<Sensor, UUID> {

  @Modifying
  @Query(value = "update sensors  set status = :status where guid = :sensorId")
  Mono<Integer> updateLastActive(Integer status, UUID sensorId);
}
