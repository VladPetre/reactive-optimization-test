package ro.phd.vsp.roptreceiverreactive.dtos;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ro.phd.vsp.roptreceiverreactive.models.SensorData;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SensorDataDTO {

  private UUID guid;
  private Double value;
  private Double battery;
  private LocalDateTime updatedOn;

  public SensorDataDTO(SensorData data) {
    this(data.getGuid(), data.getValue(), data.getBattery(), data.getUpdatedOn());
  }

  public SensorDataDTO(SensorDataDTO that) {
    this(that.getGuid(), that.getValue(), that.getBattery(), that.getUpdatedOn());
  }
}
