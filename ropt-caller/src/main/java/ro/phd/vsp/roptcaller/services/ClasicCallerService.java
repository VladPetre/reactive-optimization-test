package ro.phd.vsp.roptcaller.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ro.phd.vsp.roptcaller.dtos.SensorDataDTO;
import ro.phd.vsp.roptcaller.enums.ExecutionMethods;
import ro.phd.vsp.roptcaller.enums.InstanceTypes;
import ro.phd.vsp.roptcaller.models.ExecutionStatus;
import ro.phd.vsp.roptcaller.models.ExecutionStep;
import ro.phd.vsp.roptcaller.models.Sensor;
import ro.phd.vsp.roptcaller.repositories.ExecutionStatusRepository;
import ro.phd.vsp.roptcaller.utils.HttpUtils;

@Service
@RequiredArgsConstructor
public class ClasicCallerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClasicCallerService.class);
  private final RestTemplate restTemplate;
  private final ExecutionRegistrationService registrationService;
  private final SensorsService sensorsService;
  private final ExecutionStatusRepository executionStatusRepository;

  @Qualifier("uniqueInstanceUUID")
  private final UUID UNIQUE_INSTANCE_UUID;

  @Value("${ROPT_RECEIVER_URI}")
  public String roptReceiverURI;

  /**
   * Execute Requests to Receiver using Restemplate The configuration is taken from ExecutionSteps
   * Scheduled task with fixedRate = 5s and initialDelay 5s
   */
  @Scheduled(fixedRate = 5000, initialDelay = 5000)
  public void executeRTRequests() {

    ExecutionStep step = registrationService.getStepToExecute().get();
    List<Integer> list = IntStream.range(0, step.getEntriesNumber()).boxed()
        .collect(Collectors.toList());
    ExecutionStatus execStatus = new ExecutionStatus(null, UNIQUE_INSTANCE_UUID,
        InstanceTypes.CALLER.toString(),
        null, null, 0, step);

    List<Sensor> sensors = sensorsService.getAllSensors();

    ForkJoinPool forkJoinPool = null;

    LOGGER.info("Executing instance / step {}/{}", UNIQUE_INSTANCE_UUID, step);

    try {
      forkJoinPool = new ForkJoinPool(step.getThreadsNumber());
      execStatus.setStartedAt(LocalDateTime.now());
      forkJoinPool.submit(
          () -> list.parallelStream().forEach(
              x -> {
                int randomNr = getRandom(0, sensors.size());
                Sensor sensor = sensors.get(randomNr);
                doRequest(ExecutionMethods.valueOf(step.getMethod()),
                    new SensorDataDTO(sensor.getGuid(), (double) randomNr, (double) randomNr / 10,
                        null));
              })
      ).join();

      execStatus.setFinishedAt(LocalDateTime.now());
      executionStatusRepository.save(execStatus);

    } catch (Exception e) {
      LOGGER.error("Error executing step {} - {}: {}", UNIQUE_INSTANCE_UUID, step, e);
      execStatus.setError(1);
    } finally {
      forkJoinPool.shutdown();
    }

  }

  /**
   * Call receiver with ExecutionMethods.GET
   *
   * @param data senserDataDTO details
   * @return SensorDataDTO
   */
  private SensorDataDTO doRequest(ExecutionMethods method, SensorDataDTO data) {
    HttpEntity<SensorDataDTO> entity = new HttpEntity<>(data,
        HttpUtils.buildRTHeaders(UNIQUE_INSTANCE_UUID.toString()));

    ResponseEntity<SensorDataDTO> response = null;

    try {
      response = restTemplate
          .exchange(HttpUtils.getRTDestinationPathByMethod(method), HttpMethod.POST, entity,
              SensorDataDTO.class);
      return response.getBody();
    } catch (RestClientException e) {
      LOGGER.error("Resttemplate - exchange error: {} - {}", UNIQUE_INSTANCE_UUID, e);
    }

    return null;
  }

  public int getRandom(int min, int max) {
    return new Random().nextInt(max - min) + min;
  }
}