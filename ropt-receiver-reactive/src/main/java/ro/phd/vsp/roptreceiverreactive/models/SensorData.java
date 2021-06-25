package ro.phd.vsp.roptreceiverreactive.models;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import ro.phd.vsp.roptreceiverreactive.dtos.SensorDataDTO;

@Table(value = "sensors_data")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SensorData {

  @Id
  private UUID guid;
  private Double value;
  private Double battery;
  private LocalDateTime updatedOn;

  public SensorData(SensorDataDTO dto) {
    this.guid = dto.getGuid();
    this.value = dto.getValue();
    this.battery = dto.getBattery();
    this.updatedOn = dto.getUpdatedOn();
  }

  public SensorData(SensorData that) {
    this(that.getGuid(), that.getValue(), that.getBattery(), that.getUpdatedOn());
  }

}
