package ro.phd.vsp.roptlocalizerreactive;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ro.phd.vsp.roptlocalizerreactive.handlers.SensorsHandler;
import ro.phd.vsp.roptlocalizerreactive.handlers.TestingHandler;

@Configuration
@ComponentScan(basePackages = {"ro.phd.vsp.roptlocalizerreactive"})
public class AppConfiguration {

  @Bean
  public RouterFunction<ServerResponse> sensorDataRoute(SensorsHandler sensorsHandler) {
    return RouterFunctions.route(GET("/rt/sensors/{id}").and(accept(MediaType.APPLICATION_JSON)),
        sensorsHandler::getLocalizationById);
  }

  @Bean
  public RouterFunction<ServerResponse> testingDataRoute(TestingHandler testingHandler) {
    return RouterFunctions.route(GET("/t/logs"), testingHandler::generateLogs);
  }

  // METRICS
  @Bean
  MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
  //    return registry -> registry.config().commonTags("application", "my app");
    return registry -> registry.config();
  }

  @Bean
  TimedAspect timedAspect(MeterRegistry registry) {
    return new TimedAspect(registry);
  }


}
