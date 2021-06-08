package ro.phd.vsp.roptcallerreactive.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.phd.vsp.roptcallerreactive.models.SensorData;

public interface SensorDataRepository extends JpaRepository<SensorData, Integer> {

}
