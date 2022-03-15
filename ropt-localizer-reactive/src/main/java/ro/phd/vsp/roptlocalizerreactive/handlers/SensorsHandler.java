package ro.phd.vsp.roptlocalizerreactive.handlers;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ro.phd.vsp.roptlocalizerreactive.service.SensorsReactiveService;

@Component
@RequiredArgsConstructor
public class SensorsHandler {

  private final SensorsReactiveService sensorsReactiveService;

  public Mono<ServerResponse> getLocalizationById(ServerRequest request) {

    return Mono.fromSupplier(() -> UUID.fromString(request.pathVariable("id")))
        .map(sensorsReactiveService::getLocalizationById)
        .onErrorReturn(Mono.error(() -> new RuntimeException("Failed to get sensor with id " + request.pathVariable("id"))))
        .flatMap(s -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(s, String.class));
  }

}
