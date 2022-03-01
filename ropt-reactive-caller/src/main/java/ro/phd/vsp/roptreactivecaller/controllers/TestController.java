package ro.phd.vsp.roptreactivecaller.controllers;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.phd.vsp.roptreactivecaller.services.ReactiveCallerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

  private static final Logger LOG = LoggerFactory.getLogger(TestController.class);
  private static final String INSTANCE_KEY = "instance-id";

  private final ReactiveCallerService reactiveCallerService;

  @Qualifier("uniqueInstanceUUID")
  private final UUID UNIQUE_INSTANCE_UUID;

  @Value("${ropt.version}")
  public String svcVersion;

  @GetMapping(path = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getSvcVersion() {
    return "{\"version\" : \"" + svcVersion + "\"}";
  }

  @GetMapping(path = "/fjp", produces = MediaType.APPLICATION_JSON_VALUE)
  public void testDeploy() {

    reactiveCallerService.executeReactiveRequests();

  }

  @GetMapping(path = "/log")
  public void testLogs() {
    MDC.put(INSTANCE_KEY, UNIQUE_INSTANCE_UUID.toString());
    LOG.info("my log: {}", UUID.randomUUID());
    MDC.remove(INSTANCE_KEY);
  }

}
