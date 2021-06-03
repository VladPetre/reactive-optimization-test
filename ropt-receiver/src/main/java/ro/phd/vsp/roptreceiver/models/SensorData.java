package ro.phd.vsp.roptreceiver.models;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sensors_data")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SensorData {

  @Id
  private UUID guid;
  private Double value;
  private Double battery;
  private LocalDateTime updatedOn;

}
