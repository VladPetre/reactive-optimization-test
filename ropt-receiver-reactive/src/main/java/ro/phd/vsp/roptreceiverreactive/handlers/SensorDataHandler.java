package ro.phd.vsp.roptreceiverreactive.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.created;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import ro.phd.vsp.roptreceiverreactive.dtos.SensorDataDTO;
import ro.phd.vsp.roptreceiverreactive.services.SensorDataService;

@Component
@RequiredArgsConstructor
@Slf4j
public class SensorDataHandler {

  private final SensorDataService sensorDataService;

  public Mono<ServerResponse> getById(ServerRequest request) {
    return Mono.fromSupplier(() -> UUID.fromString(request.pathVariable("id")))
        .map(sensorDataService::getById)
        .onErrorReturn(Mono.error(() -> new RuntimeException("Failed to get sensor with id " + request.pathVariable("id"))))
        .flatMap(
            s ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(s, SensorDataDTO.class)
        );

//    return Mono.fromSupplier(() -> UUID.fromString(request.pathVariable("id")))
//        .flatMap(
//            id ->
//                ServerResponse.ok()
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(sensorDataService.getById(id), SensorDataDTO.class)
//                    .onErrorResume(ex -> {
//                      log.error("Error retrieving data {} | {} ", id, ex);
//                      return Mono.error(DataNotFoundException::new);
//                    })
//        );
  }

  public Mono<ServerResponse> save(ServerRequest request) {
    return request.bodyToMono(SensorDataDTO.class)
        .map(SensorDataDTO::new)
        .flatMap(sensorDataService::save)
        .flatMap(data ->
            created(UriComponentsBuilder.fromPath("rt/sensor-data").build().toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(data)
        );
  }

  public Mono<ServerResponse> updateWithGet(ServerRequest request) {
    return request.bodyToMono(SensorDataDTO.class)
        .map(d -> {
          d.setGuid(UUID.fromString(request.pathVariable("id")));
          return d;
        })
        .map(SensorDataDTO::new)
        .flatMap(sensorDataService::updateWithGet)
        .flatMap(data ->
            created(UriComponentsBuilder.fromPath("rt/sensor-data").build().toUri())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(data)
        );
  }

}
