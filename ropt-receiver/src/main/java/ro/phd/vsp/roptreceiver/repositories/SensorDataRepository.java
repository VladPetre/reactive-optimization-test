package ro.phd.vsp.roptreceiver.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.phd.vsp.roptreceiver.models.SensorData;

public interface SensorDataRepository extends JpaRepository<SensorData, UUID> {

}
