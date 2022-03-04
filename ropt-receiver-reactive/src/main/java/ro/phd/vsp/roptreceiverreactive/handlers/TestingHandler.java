package ro.phd.vsp.roptreceiverreactive.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ro.phd.vsp.roptreceiverreactive.utils.EnvUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class TestingHandler {

  public Mono<ServerResponse> generateLogs(ServerRequest request) {
    return Mono.empty()
        .flatMap(
            id ->
            {
              log.info("INFO log from {}", EnvUtils.getHostname());
              log.warn("WARN log from {}", EnvUtils.getHostname());
              log.error("ERROR log from {}", EnvUtils.getHostname());
              return ServerResponse.ok()
                  .contentType(MediaType.APPLICATION_JSON)
                  .body("yay! It worked!", String.class);
            }
        );
  }

}
