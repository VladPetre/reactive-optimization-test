package ro.phd.vsp.roptcaller.services;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.phd.vsp.roptcaller.repositories.ExecutionStepsRepository;

@Component
@RequiredArgsConstructor
public class ExecutionRegistrationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionRegistrationService.class);

  @Qualifier("uniqueInstanceUUID")
  private final UUID UNIQUE_INSTANCE_UUID;
  private final ExecutionStepsRepository executionStepsRepository;


  /**
   * Update last active timestamp, used as flag for app shutdown. For further checks if the
   * timestamp is older than 5 minutes, it can start another batch with new pods
   * <p>
   * Updates every 10s
   */
  @Scheduled(fixedDelay = 10000, initialDelay = 200)
  @Transactional
  public void registerForExecution() {

    try {
      executionStepsRepository
          .findByInstanceId(UNIQUE_INSTANCE_UUID)
          .ifPresent(
              ss -> {
                if (!ss.isEmpty()) {
                  executionStepsRepository
                      .updateLastActive(LocalDateTime.now(), UNIQUE_INSTANCE_UUID);
                }
              }
          );
    } catch (Exception e) {
      LOGGER.error("Failed to update last_active for : {} -> {}", UNIQUE_INSTANCE_UUID, e);
    }

    LOGGER.debug("Updated last_active for : {} ", UNIQUE_INSTANCE_UUID);
  }
}
