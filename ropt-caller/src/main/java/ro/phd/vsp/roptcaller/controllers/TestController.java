package ro.phd.vsp.roptcaller.controllers;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.phd.vsp.roptcaller.repositories.ExecutionStatusRepository;
import ro.phd.vsp.roptcaller.services.ClasicCallerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

  private final ClasicCallerService clasicCallerService;
  private final ExecutionStatusRepository executionStatusRepository;

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
    clasicCallerService.testCallerConsumer();
  }

}
