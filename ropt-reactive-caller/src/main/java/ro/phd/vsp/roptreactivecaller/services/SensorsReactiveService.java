package ro.phd.vsp.roptreactivecaller.services;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ro.phd.vsp.roptreactivecaller.repositories.SensorsReactiveRepository;

@Component
@RequiredArgsConstructor
public class SensorsReactiveService {

  private final SensorsReactiveRepository sensorsReactiveRepository;

  public Mono<Integer> updateSensorStatus(Integer status, UUID sensorId) {
    return sensorsReactiveRepository.updateLastActive(status, sensorId);
  }
}
