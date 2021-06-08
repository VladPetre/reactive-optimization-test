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
@ComponentScan(basePackages = {"ro.phd.vsp.roptcaller"})
public class AppConfiguration {

  @Value("${ROPT_RECEIVER_REACTIVE_URI}")
  public String roptReactiveReceiverURI;

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
   * Return reactive webclient object with baseUrl
   *
   * @return Webclient object
   */
  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        .baseUrl(roptReactiveReceiverURI)
        .build();
  }
}
