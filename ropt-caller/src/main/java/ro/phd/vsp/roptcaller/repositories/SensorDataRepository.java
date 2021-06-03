package ro.phd.vsp.roptcaller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.phd.vsp.roptcaller.models.SensorData;

public interface SensorDataRepository extends JpaRepository<SensorData, Integer> {

}
