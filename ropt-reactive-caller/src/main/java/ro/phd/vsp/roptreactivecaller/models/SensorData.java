package ro.phd.vsp.roptreactivecaller.models;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Table("sensors_data")
public class SensorData {

  @Id
  private UUID guid;
  private Double value;
  private Double battery;
  private LocalDateTime updatedOn;

}
