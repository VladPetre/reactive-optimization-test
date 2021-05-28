package ro.phd.vsp.roptcaller.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.phd.vsp.roptcaller.models.ExecutionStatus;
import ro.phd.vsp.roptcaller.models.ExecutionStep;
import ro.phd.vsp.roptcaller.repositories.ExecutionStatusRepository;
import ro.phd.vsp.roptcaller.repositories.ExecutionStepsRepository;

@Component
@RequiredArgsConstructor
public class ExecutionRegistrationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionRegistrationService.class);
  private static final List<Integer> nrOfThreadsList = Arrays.asList(4, 16, 32);
  private static final List<Integer> nrOfRowsList = Arrays.asList(1000, 10000, 100000);
  private static final List<String> methods = Arrays.asList("GET", "SAVE", "GET-SAVE");
  private static final String INSTANCE_TYPE = "ROPT-CALLER";

  @Qualifier("uniqueInstanceUUID")
  private final UUID UNIQUE_INSTANCE_UUID;
  private final ExecutionStepsRepository executionStepsRepository;
  private final ExecutionStatusRepository executionStatusRepository;

  /**
   * Register this service for execution with all combination for nrOfThreads and nrOfRows
   */
  @PostConstruct
  @Transactional
  public void registerForExecution() {

    List<ExecutionStatus> statusesToInsert = new ArrayList<>();

    for (Integer i : nrOfThreadsList) {
      for (Integer j : nrOfRowsList) {
        for (String m : methods) {

          statusesToInsert.add(
              new ExecutionStatus(null,
                  UNIQUE_INSTANCE_UUID,
                  INSTANCE_TYPE,
                  i,
                  j,
                  null,
                  null,
                  m,
                  1,
                  LocalDateTime.now(),
                  0));
        }
      }
    }

    if (!statusesToInsert.isEmpty()) {
      executionStatusRepository.saveAll(statusesToInsert);
    }

    LOGGER.info("Registers for execution {} ", UNIQUE_INSTANCE_UUID);
  }

  /**
   * Update last active timestamp, used as flag for app shutdown. For further checks if the
   * timestamp is older than 5 minutes, it can start another batch with new pods
   * <p>
   * Updates every 10s
   */
  @Scheduled(fixedDelay = 10000, initialDelay = 200)
  @Transactional
  public void updateLastActive() {

    try {
      executionStatusRepository
          .findByInstanceId(UNIQUE_INSTANCE_UUID)
          .ifPresent(
              ss -> {
                if (!ss.isEmpty()) {
                  executionStatusRepository
                      .updateLastActive(LocalDateTime.now(), UNIQUE_INSTANCE_UUID);
                }
              }
          );
    } catch (Exception e) {
      LOGGER.error("Failed to update last_active for : {} -> {}", UNIQUE_INSTANCE_UUID, e);
    }

    LOGGER.debug("Updated last_active for : {} ", UNIQUE_INSTANCE_UUID);
  }

  public Optional<ExecutionStep> checkStep() {

    List<ExecutionStep> steps = executionStepsRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

    for (ExecutionStep step : steps) {

    }

    return Optional.of(null);

  }
}
