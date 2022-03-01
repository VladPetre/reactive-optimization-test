package ro.phd.vsp.roptreceiver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = {"ro.phd.vsp.roptreceiver"})
public class AppConfiguration {

  @Value("${ROPT_LOCALIZER_URI}")
  public String roptLocalizerURI;

  /**
   * Return resttemplate object bean with base url
   *
   * @return resttemplate object
   */
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplateBuilder()
        .rootUri(roptLocalizerURI)
        .build();
  }
}
