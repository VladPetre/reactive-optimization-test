package ro.phd.vsp.roptreceiverreactive.services;

import static ro.phd.vsp.roptreceiverreactive.utils.HttpUtils.buildRTHeaders;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ro.phd.vsp.roptreceiverreactive.dtos.SensorDataDTO;
import ro.phd.vsp.roptreceiverreactive.exceptions.DataNotFoundException;
import ro.phd.vsp.roptreceiverreactive.models.SensorData;
import ro.phd.vsp.roptreceiverreactive.repositories.SensorDataRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class SensorDataService {

  private final SensorDataRepository sensorDataRepository;
  private final WebClient webClient;

  public Mono<SensorDataDTO> getById(UUID guid) {
    return webClient.get().uri("/rt/sensors/" + guid)
        .headers(h -> buildRTHeaders(guid.toString()))
        .retrieve()
        .bodyToMono(String.class)

        // poate in loc de filter pot sa pun un map la boolean sau ceva si sa arunc exceptie si sa folosesc on error

        .filter(s -> s != null && !s.trim().isEmpty())
        .flatMap(s -> sensorDataRepository.findById(guid).map(this::toDto))
        .onErrorResume(ex -> {
          log.error("Error retrieving data {} ", ex);
          return Mono.error(DataNotFoundException::new);
        });
  }

  public Mono<SensorDataDTO> save(SensorDataDTO sensorDataDTO) {
    return webClient.get().uri("/rt/sensors/" + sensorDataDTO.getGuid())
        .headers(h -> buildRTHeaders(sensorDataDTO.getGuid().toString()))
        .retrieve()
        .bodyToMono(String.class)
        .filter(s -> s != null && !s.trim().isEmpty())
        .flatMap(s -> sensorDataRepository.save(toEntity(sensorDataDTO)).map(this::toDto));
  }

  public Mono<SensorDataDTO> updateWithGet(SensorDataDTO sensorDataDTO) {
    return webClient.get().uri("/rt/sensors/" + sensorDataDTO.getGuid())
        .headers(h -> buildRTHeaders(sensorDataDTO.getGuid().toString()))
        .retrieve()
        .bodyToMono(String.class)
        .filter(s -> s != null && !s.trim().isEmpty())
        .flatMap(s -> sensorDataRepository.findById(sensorDataDTO.getGuid())
            .map(d -> {
              d.setBattery(sensorDataDTO.getBattery());
              d.setValue(sensorDataDTO.getValue());
              d.setUpdatedOn(sensorDataDTO.getUpdatedOn());
              return d;
            })
            .flatMap(sensorDataRepository::save)
            .map(this::toDto));

  }

  private SensorDataDTO toDto(SensorData data) {
    return new ModelMapper().typeMap(SensorData.class, SensorDataDTO.class).map(data);
  }

  private SensorData toEntity(SensorDataDTO data) {
    return new ModelMapper().typeMap(SensorDataDTO.class, SensorData.class).map(data);
  }
}
