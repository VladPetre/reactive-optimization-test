package ro.phd.vsp.roptcaller.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.phd.vsp.roptcaller.dtos.SensorDataDTO;
import ro.phd.vsp.roptcaller.enums.ExecutionMethods;
import ro.phd.vsp.roptcaller.enums.InstanceTypes;
import ro.phd.vsp.roptcaller.models.ExecutionStatus;
import ro.phd.vsp.roptcaller.models.ExecutionStep;
import ro.phd.vsp.roptcaller.models.Sensor;
import ro.phd.vsp.roptcaller.repositories.ExecutionStatusRepository;
import ro.phd.vsp.roptcaller.utils.HttpUtils;

@Component
@RequiredArgsConstructor
public class ReactiveCallerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveCallerService.class);
  private final WebClient webClient;
  private final ExecutionRegistrationService registrationService;
  private final SensorsReactiveService sensorsReactiveService;
  private final SensorsService sensorsService;
  private final ExecutionStatusRepository executionStatusRepository;

  @Qualifier("uniqueInstanceUUID")
  private final UUID UNIQUE_INSTANCE_UUID;

  /**
   * Execute Requests to Receiver using Reactive WebClient The configuration is taken from
   * ExecutionSteps Scheduled task with fixedRate = 5s and initialDelay 5s
   */
  @Scheduled(fixedRate = 5000, initialDelay = 3000)
  public void executeReactiveRequests() {

    ExecutionStep step;
    try {
      step = registrationService.getReactStepToExecute().get();
    } catch (Exception e) {
      return;
    }
    ExecutionStatus execStatus = new ExecutionStatus(null, UNIQUE_INSTANCE_UUID,
        InstanceTypes.CALLER.toString(),
        null, null, 0, step);

    List<Sensor> sensors = sensorsService.getAllSensors();

    LOGGER.info("Executing instance / step {}/{}", UNIQUE_INSTANCE_UUID, step);

    try {

      Flux.range(0, step.getEntriesNumber())
          .flatMap(i -> getReactCallerMono(step, sensors, i))
          .blockLast(Duration.ofMillis(5000));

      System.out.println("finished");
      execStatus.setFinishedAt(LocalDateTime.now());
      executionStatusRepository.save(execStatus);

    } catch (Exception e) {
      LOGGER.error("Error executing step {} - {}: {}", UNIQUE_INSTANCE_UUID, step, e);
      execStatus.setError(1);
    }

  }


  private Mono<Integer> getReactCallerMono(ExecutionStep step, List<Sensor> sensors, Integer i) {
    try {
      int randomNr = getRandom(0, sensors.size());
      Sensor sensor = sensors.get(randomNr);
      doRequest(
          ExecutionMethods.valueOf(step.getMethod()),
          new SensorDataDTO(sensor.getGuid(), (double) randomNr, (double) randomNr / 10,
              LocalDateTime.now()))
          .filter(Objects::nonNull)
          .flatMap(sd -> updateSensorStatus(randomNr, sd));
    } catch (Exception e) {
      LOGGER.error("Error calling receiver from {}, iteration {}, ex: {}",
          UNIQUE_INSTANCE_UUID, i, e);
    }
    return Mono.just(i);
  }


  public Mono<Integer> updateSensorStatus(int randomNr, SensorDataDTO sd) {
    return sensorsReactiveService.updateSensorStatus(randomNr, sd.getGuid());
  }

  /**
   * Get Resttemplate comsumer that calls the receiver service
   *
   * @param step
   * @param sensors
   * @return
   */
//  private Producer<Integer> getReactiveCallProducer(ExecutionStep step, List<Sensor> sensors) {
//    return x -> {
//      try {
//        int randomNr = getRandom(0, sensors.size());
//        Sensor sensor = sensors.get(randomNr);
//        doRequest(
//            ExecutionMethods.valueOf(step.getMethod()),
//            new SensorDataDTO(sensor.getGuid(), (double) randomNr, (double) randomNr / 10,
//                LocalDateTime.now()))
//            .filter(Objects::nonNull)
//            .subscribe(sd -> sensorsService.updateSensorStatus(randomNr, sd.getGuid()));
//
//      } catch (Exception e) {
//        LOGGER.error("Error calling receiver from {}, iteration {}, ex: {}",
//            UNIQUE_INSTANCE_UUID, x, e);
//      }
//    };
//}

  /**
   * Call receiver based on Execution Method
   *
   * @param method ExExecutionMethods enum value
   * @param data   senserDataDTO details
   * @return SensorDataDTO
   */
  private Mono<SensorDataDTO> doRequest(ExecutionMethods method, SensorDataDTO data) {
    switch (method) {
      case REACT_GET:
        return doGetRequest(data);
      case REACT_SAVE:
        return doPostRequest(data);
      case REACT_GET_SAVE:
        return doPutRequest(data);
      default:
        throw new IllegalArgumentException("Invalid option for method:" + method);
    }
  }

  private Mono<SensorDataDTO> doGetRequest(SensorDataDTO data) {

    return webClient.get()
        .uri("/rt/sensor-data/" + data.getGuid())
        .headers(h -> HttpUtils.buildRTHeaders(UNIQUE_INSTANCE_UUID.toString()))
        .retrieve()
        .bodyToMono(SensorDataDTO.class);
  }

  private Mono<SensorDataDTO> doPostRequest(SensorDataDTO data) {
    HttpEntity<SensorDataDTO> entity = new HttpEntity<>(data,
        HttpUtils.buildRTHeaders(UNIQUE_INSTANCE_UUID.toString()));

    return webClient.post()
        .uri("/rt/sensor-data")
        .headers(h -> HttpUtils.buildRTHeaders(UNIQUE_INSTANCE_UUID.toString()))
        .body(Mono.just(data), SensorDataDTO.class)
        .retrieve()
        .bodyToMono(SensorDataDTO.class);
  }

  private Mono<SensorDataDTO> doPutRequest(SensorDataDTO data) {

    return webClient.put()
        .uri("/rt/sensor-data/" + data.getGuid())
        .headers(h -> HttpUtils.buildRTHeaders(UNIQUE_INSTANCE_UUID.toString()))
        .body(Mono.just(data), SensorDataDTO.class)
        .retrieve()
        .bodyToMono(SensorDataDTO.class);
  }

  public int getRandom(int min, int max) {
    return new Random().nextInt(max - min) + min;
  }

}
