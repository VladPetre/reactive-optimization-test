package ro.phd.vsp.roptcaller.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ro.phd.vsp.roptcaller.models.Sensor;

public interface SensorsReactiveRepository extends ReactiveCrudRepository<Sensor, UUID> {

  @Modifying
  @Query(value = "update sensors  set status = ?1 where guid = ?2", nativeQuery = true)
  Mono<Integer> updateLastActive(Integer status, UUID sensorId);
}
