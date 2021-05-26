package ro.phd.vsp.roptcaller.repositories;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ro.phd.vsp.roptcaller.models.ExecutionStep;

public interface ExecutionStepsRepository extends JpaRepository<ExecutionStep, UUID> {

  @Modifying
  @Query(value = "update execution_steps  set last_active = ?1 where instance_id = ?2", nativeQuery = true)
  Integer updateLastActive(LocalDateTime lastActive, UUID instanceId);

}
