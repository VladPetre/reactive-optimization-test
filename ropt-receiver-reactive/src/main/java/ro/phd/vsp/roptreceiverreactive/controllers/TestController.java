package ro.phd.vsp.roptreceiverreactive.controllers;

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

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/test")
public class TestController {

  private static final Logger LOG = LoggerFactory.getLogger( TestController.class );
  private static final String INSTANCE_KEY = "instance-id";

  @Qualifier("uniqueInstanceUUID")
  private final UUID UNIQUE_INSTANCE_UUID;

  @Value("${ropt.version}")
  public String svcVersion;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public String testDeploy() {
    return "{\"test\" : \"success\"}";
  }

  @GetMapping(path = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getSvcVersion() {
    return "{\"version\" : \"" + svcVersion + "\"}";
  }

  @GetMapping(path = "/log")
  public void testLogs() {
    MDC.put(INSTANCE_KEY, UNIQUE_INSTANCE_UUID.toString());
    LOG.info("my log: {}", UUID.randomUUID());
    MDC.remove(INSTANCE_KEY);
  }

}