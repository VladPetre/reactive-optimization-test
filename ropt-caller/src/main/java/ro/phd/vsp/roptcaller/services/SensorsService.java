package ro.phd.vsp.roptcaller.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.phd.vsp.roptcaller.models.Sensor;
import ro.phd.vsp.roptcaller.repositories.SensorsRepository;

@Component
@RequiredArgsConstructor
public class SensorsService {

  private final SensorsRepository sensorsRepository;
  private final List<Sensor> sensorsCache = new ArrayList<>();

  public List<Sensor> getAllSensors() {
    if(!sensorsCache.isEmpty()){
      return sensorsCache;
    }
    return sensorsRepository.findAll();
  }

  @Transactional
  public Integer updateSensorStatus(Integer status, UUID sensorId) {
    return sensorsRepository.updateLastActive(status, sensorId);
  }

}
