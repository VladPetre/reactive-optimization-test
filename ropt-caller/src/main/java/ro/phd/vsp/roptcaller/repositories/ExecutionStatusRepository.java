package ro.phd.vsp.roptcallerreactive.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ro.phd.vsp.roptcallerreactive.models.ExecutionStatus;

public interface ExecutionStatusRepository extends JpaRepository<ExecutionStatus, Integer> {

  @Modifying
  @Query(value = "update execution_status  set last_active = ?1 where instance_id = ?2", nativeQuery = true)
  Integer updateLastActive(LocalDateTime lastActive, UUID instanceId);

  Optional<List<ExecutionStatus>> findByInstanceId(UUID uuid);
}
