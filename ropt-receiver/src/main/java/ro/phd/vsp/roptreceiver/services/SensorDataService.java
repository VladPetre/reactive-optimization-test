package ro.phd.vsp.roptreceiver.services;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ro.phd.vsp.roptreceiver.dtos.SensorDataDTO;
import ro.phd.vsp.roptreceiver.exceptions.DataNotFoundException;
import ro.phd.vsp.roptreceiver.models.SensorData;
import ro.phd.vsp.roptreceiver.repositories.SensorDataRepository;

@Component
@RequiredArgsConstructor
public class SensorDataService {

  private final SensorDataRepository sensorDataRepository;

  public SensorDataDTO getById(UUID guid) {
    return toDto(sensorDataRepository.findById(guid)
        .orElseThrow(DataNotFoundException::new));
  }

  public SensorDataDTO save(SensorDataDTO sensorDataDTO) {
    return toDto(sensorDataRepository.save(toEntity(sensorDataDTO)));
  }

  public SensorDataDTO updateWithGet(SensorDataDTO sensorDataDTO) {
    SensorData oldData = sensorDataRepository.findById(sensorDataDTO.getGuid())
        .orElseThrow(DataNotFoundException::new);
    oldData.setBattery(sensorDataDTO.getBattery());
    oldData.setValue(sensorDataDTO.getValue());
    oldData.setUpdatedOn(sensorDataDTO.getUpdatedOn());

    return toDto(sensorDataRepository.save(oldData));
  }

  private SensorDataDTO toDto(SensorData data) {
    return new ModelMapper().typeMap(SensorData.class, SensorDataDTO.class).map(data);
  }

  private SensorData toEntity(SensorDataDTO data) {
    return new ModelMapper().typeMap(SensorDataDTO.class, SensorData.class).map(data);
  }
}
