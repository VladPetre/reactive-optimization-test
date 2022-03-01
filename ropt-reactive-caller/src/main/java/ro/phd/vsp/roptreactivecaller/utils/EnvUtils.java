package ro.phd.vsp.roptreactivecaller.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EnvUtils {

  private static String HOSTNAME;

  public static String getHostname() {
    return HOSTNAME;
  }

  @PostConstruct
  public void lazySetup() {
    try {
      HOSTNAME = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      log.error("Could not retrieve hostname!");
    }
  }

}
