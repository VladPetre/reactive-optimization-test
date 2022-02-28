package ro.phd.vsp.roptcaller.models;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sensors")
@NoArgsConstructor
@Data
public class Sensor {

  @Id
  private UUID guid;
  private String sensorType;
  private String location;
  private Integer status;

}
