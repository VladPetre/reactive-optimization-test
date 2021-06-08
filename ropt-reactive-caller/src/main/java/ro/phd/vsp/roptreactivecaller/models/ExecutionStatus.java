package ro.phd.vsp.roptreactivecaller.models;

import java.time.LocalDateTime;
import java.util.UUID;
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
@Table("execution_status")
public class ExecutionStatus {

  @Id
  private Long id;
  private UUID instanceId;
  private String instanceType;
  private LocalDateTime startedAt;
  private LocalDateTime finishedAt;
  private Integer error; //0 - none, 1 - exception
  private Long executionStepId;
}
