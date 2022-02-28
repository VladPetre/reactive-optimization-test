package ro.phd.vsp.roptcaller.models;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sensors_data")
@NoArgsConstructor
@Data
public class SensorData {

  @Id
  private UUID guid;
  private Double value;
  private Double battery;
  private LocalDateTime updatedOn;

}
