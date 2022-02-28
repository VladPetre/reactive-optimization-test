package ro.phd.vsp.roptreactivecaller.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.phd.vsp.roptreactivecaller.models.ExecutionStep;
import ro.phd.vsp.roptreactivecaller.repositories.ExecutionStepsRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExecutionRegistrationService {

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
            LocalDateTime.now(), ChronoUnit.SECONDS) <= s.getSecondsOffset())
        .collectList()
        .block();

    if (activeSteps != null && activeSteps.size() != 1) {
      log.error("None or Many steps were active!");
      throw new IllegalArgumentException("None or Many steps were active!");
    }

    return Optional.of(activeSteps.get(0));
  }
}
