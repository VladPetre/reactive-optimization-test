package ro.phd.vsp.roptcaller.models;

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
@Table(name = "execution_steps")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExecutionStep {

  @Id
  private UUID instanceId;
  private String instanceType;
  private Integer threadsNumber;
  private Integer entriesNumber;
  private LocalDateTime startedAt;
  private LocalDateTime finishedAt;
  private String method;
  private Integer status;
  private LocalDateTime lastActive;

}
