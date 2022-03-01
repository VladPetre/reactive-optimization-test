package ro.phd.vsp.roptreceiver.services;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.phd.vsp.roptreceiver.dtos.SensorDataDTO;
import ro.phd.vsp.roptreceiver.exceptions.DataNotFoundException;
import ro.phd.vsp.roptreceiver.models.SensorData;
import ro.phd.vsp.roptreceiver.repositories.SensorDataRepository;
import ro.phd.vsp.roptreceiver.utils.HttpUtils;

@Component
@RequiredArgsConstructor
public class SensorDataService {

  private final SensorDataRepository sensorDataRepository;
  private final RestTemplate restTemplate;

  public SensorDataDTO getById(UUID guid) {
    if (isInvalid(guid)) {
      throw new DataNotFoundException("could not validate data");
    }

    return toDto(sensorDataRepository.findById(guid)
        .orElseThrow(DataNotFoundException::new));
  }

  public SensorDataDTO save(SensorDataDTO sensorDataDTO) {
    if (isInvalid(sensorDataDTO.getGuid())) {
      throw new DataNotFoundException("could not validate data");
    }

    return toDto(sensorDataRepository.save(toEntity(sensorDataDTO)));
  }

  public SensorDataDTO updateWithGet(SensorDataDTO sensorDataDTO) {
    if (isInvalid(sensorDataDTO.getGuid())) {
      throw new DataNotFoundException("could not validate data");
    }

    SensorData oldData = sensorDataRepository.findById(sensorDataDTO.getGuid())
        .orElseThrow(DataNotFoundException::new);
    oldData.setBattery(sensorDataDTO.getBattery());
    oldData.setValue(sensorDataDTO.getValue());
    oldData.setUpdatedOn(sensorDataDTO.getUpdatedOn());

    return toDto(sensorDataRepository.save(oldData));
  }

  private boolean isInvalid(UUID id) {
    String validation = callForValidation(id);
    return validation == null || validation.trim().isEmpty();
  }

  private String callForValidation(UUID id) {
    HttpEntity<String> entity = new HttpEntity<>(
        HttpUtils.buildRTHeaders(id.toString()));

    return restTemplate
        .exchange("/rt/sensors/" + id, HttpMethod.GET, entity, String.class).getBody();
  }

  private SensorDataDTO toDto(SensorData data) {
    return new ModelMapper().typeMap(SensorData.class, SensorDataDTO.class).map(data);
  }

  private SensorData toEntity(SensorDataDTO data) {
    return new ModelMapper().typeMap(SensorDataDTO.class, SensorData.class).map(data);
  }
}
