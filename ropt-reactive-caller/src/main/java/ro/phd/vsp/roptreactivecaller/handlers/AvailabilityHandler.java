package ro.phd.vsp.roptreactivecaller.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class AvailabilityHandler {

  public Mono<ServerResponse> isReady(ServerRequest request) {
    return Mono.just(1)
        .flatMap(id -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"status\":\"UP\"}")
        );
  }

  public Mono<ServerResponse> isLive(ServerRequest request) {
    return Mono.just(1)
        .flatMap(id -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"status\":\"UP\"}")
        );
  }

}
