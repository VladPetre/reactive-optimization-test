package ro.phd.vsp.roptreactivecaller.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ro.phd.vsp.roptreactivecaller.models.Sensor;
import ro.phd.vsp.roptreactivecaller.repositories.SensorsReactiveRepository;

@Component
@RequiredArgsConstructor
public class SensorsService {

  private final SensorsReactiveRepository sensorsRepository;
  private final List<Sensor> sensorsCache = new ArrayList<>();


  public List<Sensor> getAllSensorsBlock() {
    if(!sensorsCache.isEmpty()){
      return sensorsCache;
    }

    return sensorsRepository.findAll().collectList().block();
  }

  public Mono<Integer> updateSensorStatus(Integer status, UUID sensorId) {
    return sensorsRepository.updateSensorStatus(status, sensorId);
  }

}
