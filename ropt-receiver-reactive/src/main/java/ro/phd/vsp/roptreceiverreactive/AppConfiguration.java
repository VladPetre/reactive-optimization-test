package ro.phd.vsp.roptreceiverreactive;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ro.phd.vsp.roptreceiverreactive.handlers.SensorDataHandler;

@Configuration
@ComponentScan(basePackages = {"ro.phd.vsp.roptreceiverreactive"})
public class AppConfiguration {

  @Value("${ROPT_LOCALIZER_REACTIVE_URI}")
  public String rLclURI;

  @Bean
  public RouterFunction<ServerResponse> sensorDataRoute(SensorDataHandler sensorDataHandler) {
    return RouterFunctions
        .route(GET("/rt/sensor-data/{id}").and(accept(MediaType.APPLICATION_JSON)),
            sensorDataHandler::getById)
        .andRoute(POST("/rt/sensor-data").and(accept(MediaType.APPLICATION_JSON)),
            sensorDataHandler::save)
        .andRoute(PUT("/rt/sensor-data/{id}").and(accept(MediaType.APPLICATION_JSON)),
            sensorDataHandler::updateWithGet);
  }



  /**
   * Return webclient object with baseUrl for reactive runType
   *
   * @return Webclient object
   */
  @Bean
  @Qualifier("reactiveClient")
  public WebClient reactWebClient() {
    return WebClient.builder()
        .baseUrl(rLclURI)
        .build();
  }

}
