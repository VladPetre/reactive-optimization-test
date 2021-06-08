package ro.phd.vsp.roptreactivecaller.models;

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
@Table("sensors")
public class Sensor {

  @Id
  private UUID guid;
  private String sensorType;
  private String location;
  private Integer status;

}
