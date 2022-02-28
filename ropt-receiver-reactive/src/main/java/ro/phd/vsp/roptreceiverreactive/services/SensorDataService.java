package ro.phd.vsp.roptreceiverreactive.services;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.phd.vsp.roptreceiverreactive.dtos.SensorDataDTO;
import ro.phd.vsp.roptreceiverreactive.models.SensorData;
import ro.phd.vsp.roptreceiverreactive.repositories.SensorDataRepository;

@Component
@RequiredArgsConstructor
public class SensorDataService {

  private final SensorDataRepository sensorDataRepository;

  public Flux<SensorDataDTO> getAll() {
    return sensorDataRepository.findAll().map(this::toDto);
  }

  public Mono<SensorDataDTO> getById(UUID guid) {
    return sensorDataRepository.findById(guid).map(this::toDto);
  }

  public Mono<SensorDataDTO> save(SensorDataDTO sensorDataDTO) {
    return sensorDataRepository.save(toEntity(sensorDataDTO)).map(this::toDto);
  }

  public Mono<SensorDataDTO> updateWithGet(SensorDataDTO sensorDataDTO) {

    return sensorDataRepository.findById(sensorDataDTO.getGuid())
        .map(d -> {
          d.setBattery(sensorDataDTO.getBattery());
          d.setValue(sensorDataDTO.getValue());
          d.setUpdatedOn(sensorDataDTO.getUpdatedOn());
          return d;
        })
        .flatMap(sensorDataRepository::save)
        .map(this::toDto);

  }

  private SensorDataDTO toDto(SensorData data) {
    return new ModelMapper().typeMap(SensorData.class, SensorDataDTO.class).map(data);
  }

  private SensorData toEntity(SensorDataDTO data) {
    return new ModelMapper().typeMap(SensorDataDTO.class, SensorData.class).map(data);
  }
}
