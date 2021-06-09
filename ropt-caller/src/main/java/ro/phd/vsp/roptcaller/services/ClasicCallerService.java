package ro.phd.vsp.roptcaller.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
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


  /**
   * Execute Requests to Receiver using Restemplate The configuration is taken from ExecutionSteps
   * Scheduled task with fixedRate = 5s and initialDelay 5s
   */
  @Scheduled(fixedRate = 5000, initialDelay = 3000)
  public void executeRTRequests() {

    ExecutionStep step;
    try {
      step = registrationService.getRTStepToExecute().get();
    } catch (Exception e) {
      return;
    }
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
              getRTCallConsumer(step, sensors))
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
   * Get Resttemplate comsumer that calls the receiver service
   *
   * @param step
   * @param sensors
   * @return
   */
  private Consumer<Integer> getRTCallConsumer(ExecutionStep step, List<Sensor> sensors) {
    return x -> {
      try {
        int randomNr = getRandom(0, sensors.size() - 1);
        Sensor sensor = sensors.get(randomNr);
        SensorDataDTO sensorDataDTO = doRequest(
            ExecutionMethods.valueOf(step.getMethod()),
            new SensorDataDTO(sensor.getGuid(), (double) randomNr, (double) randomNr / 10,
                LocalDateTime.now()));
        sensorsService.updateSensorStatus(randomNr, sensor.getGuid());

      } catch (Exception e) {
        LOGGER.error("Error calling receiver from {}, iteration {}, ex: {}",
            UNIQUE_INSTANCE_UUID, x, e);
      }
    };
  }

  /**
   * Call receiver based on Execution Method
   *
   * @param method ExExecutionMethods enum value
   * @param data   senserDataDTO details
   * @return SensorDataDTO
   */
  private SensorDataDTO doRequest(ExecutionMethods method, SensorDataDTO data) {
    switch (method) {
      case GET:
        return doGetRequest(data).getBody();
      case SAVE:
        return doPostRequest(data).getBody();
      case GET_SAVE:
        return doPutRequest(data).getBody();
      default:
        throw new IllegalArgumentException("Invalid option for method:" + method);
    }
  }

  private ResponseEntity<SensorDataDTO> doGetRequest(SensorDataDTO data) {
    HttpEntity<String> entity = new HttpEntity<>(
        HttpUtils.buildRTHeaders(UNIQUE_INSTANCE_UUID.toString()));

    return restTemplate
        .exchange("/rt/sensor-data/" + data.getGuid(), HttpMethod.GET, entity,
            SensorDataDTO.class);
  }

  private ResponseEntity<SensorDataDTO> doPostRequest(SensorDataDTO data) {
    HttpEntity<SensorDataDTO> entity = new HttpEntity<>(data,
        HttpUtils.buildRTHeaders(UNIQUE_INSTANCE_UUID.toString()));

    return restTemplate
        .exchange("/rt/sensor-data", HttpMethod.POST, entity,
            SensorDataDTO.class);
  }

  private ResponseEntity<SensorDataDTO> doPutRequest(SensorDataDTO data) {
    HttpEntity<SensorDataDTO> entity = new HttpEntity<>(data,
        HttpUtils.buildRTHeaders(UNIQUE_INSTANCE_UUID.toString()));

    return restTemplate
        .exchange("/rt/sensor-data/" + data.getGuid(), HttpMethod.PUT, entity,
            SensorDataDTO.class);
  }

  public int getRandom(int min, int max) {
    return new Random().nextInt(max - min) + min;
  }
}