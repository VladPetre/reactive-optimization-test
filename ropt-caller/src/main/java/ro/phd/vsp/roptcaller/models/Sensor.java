package ro.phd.vsp.roptcaller.models;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sensors")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Sensor {

  @Id
  @GeneratedValue()
  private UUID guid;
  private String sensorType;
  private String location;
  private Integer status;

}
