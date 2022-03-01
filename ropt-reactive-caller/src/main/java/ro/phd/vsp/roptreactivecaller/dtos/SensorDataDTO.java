package ro.phd.vsp.roptreactivecaller.dtos;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SensorDataDTO {

  private UUID guid;
  private Double value;
  private Double battery;
  private LocalDateTime updatedOn;
}
