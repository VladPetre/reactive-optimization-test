package ro.phd.vsp.roptreceiverreactive.handlers;

import static org.springframework.web.reactive.function.server.ServerResponse.created;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
public class SensorDataHandler {

  private final SensorDataService sensorDataService;

  public Mono<ServerResponse> getAll(ServerRequest request) {
    return ServerResponse.ok()
        .contentType(MediaType.TEXT_EVENT_STREAM)
        .body(sensorDataService.getAll(), SensorDataDTO.class);
  }

  public Mono<ServerResponse> getById(ServerRequest request) {
    return Mono.fromSupplier(() -> UUID.fromString(request.pathVariable("id")))
        .flatMap(
            id ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(sensorDataService.getById(id), SensorDataDTO.class)
        );
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
                .body(data, SensorDataDTO.class)
        );
  }
}
