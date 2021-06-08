package ro.phd.vsp.roptcallerreactive.services;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.phd.vsp.roptcallerreactive.models.Sensor;
import ro.phd.vsp.roptcallerreactive.repositories.SensorsRepository;

@Component
@RequiredArgsConstructor
public class SensorsService {

  private final SensorsRepository sensorsRepository;

  public List<Sensor> getAllSensors() {
    return sensorsRepository.findAll();
  }

  public Integer updateSensorStatus(Integer status, UUID sensorId) {
    return sensorsRepository.updateLastActive(status, sensorId);
  }

}
