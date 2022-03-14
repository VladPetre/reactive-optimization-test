package ro.phd.vsp.roptreactivecaller;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = {"ro.phd.vsp.roptreactivecaller"})
public class AppConfiguration {

  @Value("${ROPT_RECEIVER_REACTIVE_URI}")
  public String rRcvURI;

  @Value("${ROPT_RECEIVER_CLASSIC_URI}")
  public String cRcvURI;

//  @Bean
//  public RouterFunction<ServerResponse> availabilityRoute(AvailabilityHandler availabilityHandler) {
//    return RouterFunctions.route(GET("/availability/ready"), availabilityHandler::isReady)
//        .andRoute(GET("/availability/ready"), availabilityHandler::isLive);
//  }

  /**
   * Return webclient object with baseUrl for reactive runType
   *
   * @return Webclient object
   */
  @Bean
  @Qualifier("reactiveClient")
  public WebClient reactWebClient() {
    return WebClient.builder()
        .baseUrl(rRcvURI)
        .build();
  }

  /**
   * Return webclient object with baseUrl for classic runType
   *
   * @return Webclient object
   */
  @Bean
  @Qualifier("classicClient")
  public WebClient classicWebClient() {
    return WebClient.builder()
        .baseUrl(cRcvURI)
        .build();
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
