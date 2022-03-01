package ro.phd.vsp.roptlocalizerreactive.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ro.phd.vsp.roptlocalizerreactive.model.Sensor;
import ro.phd.vsp.roptlocalizerreactive.repository.SensorsReactiveRepository;

@Component
@RequiredArgsConstructor
public class SensorsReactiveService {

  private final SensorsReactiveRepository sensorsRepository;

  public Mono<String> getLocalizationById(UUID id)
  {
    return sensorsRepository.findById(id)
        .map(Sensor::getLocation);
  }

}
