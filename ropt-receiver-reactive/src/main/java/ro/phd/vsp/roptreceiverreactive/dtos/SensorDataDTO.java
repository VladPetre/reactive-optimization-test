package ro.phd.vsp.roptreceiverreactive.dtos;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
}
