package ro.phd.vsp.roptlocalizerreactive.model;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@Data
@Table("sensors")
public class Sensor {

  @Id
  private UUID guid;
  private String sensorType;
  private String location;
  private Integer status;

}
