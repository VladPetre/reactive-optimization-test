package ro.phd.vsp.roptreceiverreactive;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = {"ro.phd.vsp.roptreceiverreactive"})
public class AppConfiguration {

  /**
   * Generate unique UUID to identify each instance. Used for execution step table as key of status
   * table for receiver reactive
   *
   * @return UUID.randomUUID()
   */
  @Bean
  @Qualifier("uniqueInstanceUUID")
  public UUID uniqueInstanceUUID() {
    return UUID.randomUUID();
  }
}
