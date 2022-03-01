package ro.phd.vsp.roptreactivecaller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

  /**
   * Generate unique UUID to identify each instance. Used for execution step table as key of status
   * table for caller
   *
   * @return UUID.randomUUID()
   */
  @Bean
  @Qualifier("uniqueInstanceUUID")
  public UUID uniqueInstanceUUID() {
    return UUID.randomUUID();
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
}
