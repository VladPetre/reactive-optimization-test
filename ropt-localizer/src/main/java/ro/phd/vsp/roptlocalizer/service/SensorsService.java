package ro.phd.vsp.roptlocalizer.service;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.phd.vsp.roptlocalizer.repository.SensorsRepository;

@Component
@RequiredArgsConstructor
public class SensorsService {

  private final SensorsRepository sensorsRepository;
  private final MeterRegistry meterRegistry;

  @Timed("lcl.sensors.getLocationById")
  public String getLocationById(UUID id) {
    meterRegistry.counter("lcl.counter").increment();
    return sensorsRepository.getOne(id).getLocation();
  }

}
