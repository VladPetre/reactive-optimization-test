package ro.phd.vsp.roptcaller.controllers;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.phd.vsp.roptcaller.models.ExecutionStatus;
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

  @GetMapping(path = "/fjp/{threads}/{data}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void testDeploy(@PathVariable int threads, @PathVariable int data) {
    clasicCallerService.testReceiverMService(threads, data);
  }

  @GetMapping(path = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getSvcVersion() {
    return "{\"version\" : \"" + svcVersion + "\"}";
  }

  @GetMapping(path = "/ctx", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getCtx() {
    return UNIQUE_INSTANCE_UUID.toString();
  }

  @GetMapping(path = "/updStep", produces = MediaType.APPLICATION_JSON_VALUE)
  public String testUpdateStatus() {
    ExecutionStatus step = new ExecutionStatus();

    step.setInstanceId(UNIQUE_INSTANCE_UUID);
    step.setLastActive(LocalDateTime.now());
    step.setEntriesNumber(10);
    step.setMethod("GET");

    executionStatusRepository.save(step);

    return UNIQUE_INSTANCE_UUID.toString();
  }
}
