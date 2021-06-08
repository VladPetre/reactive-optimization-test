package ro.phd.vsp.roptcallerreactive.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ro.phd.vsp.roptcallerreactive.models.Sensor;

public interface SensorsRepository extends JpaRepository<Sensor, UUID> {

  @Modifying
  @Query(value = "update sensors  set status = ?1 where guid = ?2", nativeQuery = true)
  Integer updateLastActive(Integer status, UUID sensorId);
}
