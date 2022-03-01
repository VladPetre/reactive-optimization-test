package ro.phd.vsp.roptreactivecaller.services;

import static ro.phd.vsp.roptreactivecaller.utils.HttpUtils.buildRTHeaders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ro.phd.vsp.roptreactivecaller.dtos.SensorDataDTO;
import ro.phd.vsp.roptreactivecaller.enums.ExecutionMethod;
import ro.phd.vsp.roptreactivecaller.models.ExecutionStep;
import ro.phd.vsp.roptreactivecaller.models.Sensor;
import ro.phd.vsp.roptreactivecaller.utils.WebClientFactory;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReactiveCallerService {

  public static final Random RANDOM = new Random();

  private final WebClientFactory webClientFactory;
  private final ExecutionRegistrationService registrationService;
  private final SensorsService sensorsService;
  private final UUID uniqueInstanceUuid;

  /**
   * Execute Requests to Receiver using Reactive WebClient The configuration is taken from
   * ExecutionSteps Scheduled task with fixedRate = 5s and initialDelay 3s
   */
  @Scheduled(fixedRate = 5000, initialDelay = 3000)
  public void executeReactiveRequests() {

    MDC.put("SID", uniqueInstanceUuid.toString());

    ExecutionStep step = null;
    try {
      step = registrationService.getReactStepToExecute();
    } catch (Exception e) {
      return;
    }

    executeStep(step);

    MDC.clear();
  }

  private void executeStep(ExecutionStep step) {
    List<Sensor> sensors = sensorsService.getAllSensorsBlock();

    log.info("Executing step | {}", step.getId());

    try {
      Flux.range(0, step.getEntriesNumber())
          .flatMap(aL -> getReactCallerMono(step, sensors, aL), step.getThreadsNumber())
          .subscribeOn(Schedulers.boundedElastic())
          .subscribe();

    } catch (Exception e) {
      log.error("Error executing step: {}: {}", step.getId(), e);
    }
  }


  private Mono<Integer> getReactCallerMono(ExecutionStep step, List<Sensor> sensors, Integer i) {
    try {
      int randomNr = getRandom(0, sensors.size());
      Sensor sensor = sensors.get(randomNr);
      ExecutionMethod method = ExecutionMethod.valueOf(step.getMethod());
      return doRequest(webClientFactory.getclient(method),
          method,
          new SensorDataDTO(sensor.getGuid(), (double) randomNr, (double) randomNr / 10,
              LocalDateTime.now())).filter(Objects::nonNull).flatMap(sd -> Mono.just(1));
    } catch (Exception e) {
      log.error("Error calling receiver, iteration {}, ex: {}", i, e);
    }
    return Mono.just(1);
  }

  /**
   * Call reactive receiver based on Execution Method
   *
   * @param method ExExecutionMethods enum value
   * @param data   senserDataDTO details
   * @return SensorDataDTO
   */
  private Mono<SensorDataDTO> doRequest(WebClient client, ExecutionMethod method,
      SensorDataDTO data) {
    switch (method) {
      case GET:
      case REACT_GET:
        return doGetRequest(client, data);
      case SAVE:
      case REACT_SAVE:
        return doPostRequest(client, data);
      case GET_SAVE:
      case REACT_GET_SAVE:
        return doPutRequest(client, data);
      default:
        throw new IllegalArgumentException("Invalid option for method:" + method);
    }
  }

  private Mono<SensorDataDTO> doGetRequest(WebClient client, SensorDataDTO data) {

    return client.get().uri("/rt/sensor-data/" + data.getGuid())
        .headers(h -> buildRTHeaders(uniqueInstanceUuid.toString()))
        .retrieve()
        .bodyToMono(SensorDataDTO.class);
  }

  private Mono<SensorDataDTO> doPostRequest(WebClient client, SensorDataDTO data) {

    return client.post().uri("/rt/sensor-data")
        .headers(h -> buildRTHeaders(uniqueInstanceUuid.toString()))
        .body(Mono.just(data), SensorDataDTO.class)
        .retrieve()
        .bodyToMono(SensorDataDTO.class);
  }

  private Mono<SensorDataDTO> doPutRequest(WebClient client, SensorDataDTO data) {

    return client.put().uri("/rt/sensor-data/" + data.getGuid())
        .headers(h -> buildRTHeaders(uniqueInstanceUuid.toString()))
        .body(Mono.just(data), SensorDataDTO.class)
        .retrieve()
        .bodyToMono(SensorDataDTO.class);
  }

  private int getRandom(int min, int max) {
    return RANDOM.nextInt(max - min) + min;
  }

}
