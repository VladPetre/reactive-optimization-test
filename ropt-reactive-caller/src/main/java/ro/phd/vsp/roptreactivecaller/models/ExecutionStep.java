package ro.phd.vsp.roptreactivecaller.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table("execution_steps")
public class ExecutionStep {

  @Id
  private Long id;
  private Integer threadsNumber;
  private Integer entriesNumber;
  private String method;
  private Integer status;
  private Boolean active;
  private LocalDateTime lastActive;
  private Integer receiverNrInstances;
  private Long secondsOffset;

}
