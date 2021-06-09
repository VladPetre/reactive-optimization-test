package ro.phd.vsp.roptreactivecaller.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.phd.vsp.roptreactivecaller.models.ExecutionStep;
import ro.phd.vsp.roptreactivecaller.repositories.ExecutionStepsRepository;

@Component
@RequiredArgsConstructor
public class ExecutionRegistrationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      ExecutionRegistrationService.class);

  private final ExecutionStepsRepository executionStepsRepository;

  /**
   * Get execution step configuration for REACT caller where is active and last_active is no older
   * than a number of seconds whithin step
   *
   * @return Optional<ExecutionStep>
   */
  public Optional<ExecutionStep> getReactStepToExecute() {
    
    List<ExecutionStep> activeSteps = executionStepsRepository
        .findAll()
        .filter(s -> s.getActive() && s.getMethod().toUpperCase().contains("REACT_")
            && s.getLastActive().until(
            LocalDateTime.now(), ChronoUnit.SECONDS) <= s
            .getSecondsOffset()
        )
        .collectList()
        .block();

    if (activeSteps.size() != 1) {
      LOGGER.error("REACT: None or Many steps were active!");
      throw new IllegalArgumentException("None or Many steps were active!");
    }

    return Optional.of(activeSteps.get(0));
  }
}
