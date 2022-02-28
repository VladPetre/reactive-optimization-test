package ro.phd.vsp.roptcaller.dtos;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SensorDataDTO {

  private UUID guid;
  private Double value;
  private Double battery;
  private LocalDateTime updatedOn;
}
