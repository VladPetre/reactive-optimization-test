package ro.phd.vsp.roptcaller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = {"ro.phd.vsp.roptcaller"})
public class AppConfiguration {

  @Value("${ROPT_RECEIVER_URI}")
  public String roptReceiverURI;

  @Bean
  public RestTemplate restTemplate() {

    return new RestTemplateBuilder()
        .rootUri(roptReceiverURI)
//        .uriTemplateHandler(new DefaultUriBuilderFactory(roptReceiverURI))
        .build();
  }
}
