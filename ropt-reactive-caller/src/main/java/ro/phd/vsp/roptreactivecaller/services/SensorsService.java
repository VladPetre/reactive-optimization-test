package ro.phd.vsp.roptreactivecaller.services;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.phd.vsp.roptreactivecaller.models.Sensor;
import ro.phd.vsp.roptreactivecaller.repositories.SensorsReactiveRepository;

@Component
@RequiredArgsConstructor
public class SensorsService {

  private final SensorsReactiveRepository sensorsRepository;
  private final List<Sensor> sensorsCache = new ArrayList<>();

  public List<Sensor> getAllSensorsBlock() {
    if (!sensorsCache.isEmpty()) {
      return sensorsCache;
    }

    return sensorsRepository.findAll().collectList().block();
  }

}
