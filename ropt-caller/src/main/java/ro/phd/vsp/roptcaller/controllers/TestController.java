package ro.phd.vsp.roptcaller.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.phd.vsp.roptcaller.services.ClasicCallerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

  private final ClasicCallerService clasicCallerService;

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
}
