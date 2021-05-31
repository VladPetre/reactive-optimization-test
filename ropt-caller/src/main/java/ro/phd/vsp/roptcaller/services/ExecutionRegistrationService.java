package ro.phd.vsp.roptcaller.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ro.phd.vsp.roptcaller.models.ExecutionStep;
import ro.phd.vsp.roptcaller.repositories.ExecutionStepsRepository;

@Component
@RequiredArgsConstructor
public class ExecutionRegistrationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionRegistrationService.class);

  private final ExecutionStepsRepository executionStepsRepository;

  /**
   * Get execution step configuration where is active and last_active is no older than a number of
   * seconds whithin step
   *
   * @return Optional<ExecutionStep>
   */
  public Optional<ExecutionStep> getStepToExecute() {

    List<ExecutionStep> activeSteps = executionStepsRepository
        .findAll(Sort.by(Sort.Direction.ASC, "id"))
        .stream().filter(ExecutionStep::getActive).filter(s -> s.getLastActive().until(
            LocalDateTime.now(), ChronoUnit.SECONDS) <= s.getSecondsOffset())
        .collect(Collectors.toList());

    if (activeSteps.size() != 1) {
      LOGGER.error("None or Many steps were active!");
      throw new IllegalArgumentException("None or Many steps were active!");
    }

    return Optional.of(activeSteps.get(0));
  }
}