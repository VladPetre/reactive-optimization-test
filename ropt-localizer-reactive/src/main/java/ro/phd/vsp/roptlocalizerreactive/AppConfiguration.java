package ro.phd.vsp.roptlocalizerreactive;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ro.phd.vsp.roptlocalizerreactive.handlers.SensorsHandler;

@Configuration
@ComponentScan(basePackages = {"ro.phd.vsp.roptlocalizerreactive"})
public class AppConfiguration {

  @Bean
  public RouterFunction<ServerResponse> sensorDataRoute(SensorsHandler sensorsHandler) {
    return RouterFunctions.route(GET("/rt/sensors/{id}")
        .and(accept(MediaType.APPLICATION_JSON)), sensorsHandler::getLocalizationById);
  }

}
