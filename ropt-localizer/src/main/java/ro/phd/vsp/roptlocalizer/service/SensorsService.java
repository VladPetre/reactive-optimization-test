package ro.phd.vsp.roptlocalizer.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.phd.vsp.roptlocalizer.repository.SensorsRepository;

@Component
@RequiredArgsConstructor
public class SensorsService {

  private final SensorsRepository sensorsRepository;

  public String getLocationById(UUID id)
  {
    return sensorsRepository.getOne(id).getLocation();
  }

}
