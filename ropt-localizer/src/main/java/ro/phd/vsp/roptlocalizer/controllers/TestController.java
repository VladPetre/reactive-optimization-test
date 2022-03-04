package ro.phd.vsp.roptlocalizer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.phd.vsp.roptlocalizer.utils.EnvUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/t", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class TestController {

  @GetMapping(path = "/logs")
  public ResponseEntity<String> getById() {
    log.info("INFO log from {}", EnvUtils.getHostname());
    log.warn("WARN log from {}", EnvUtils.getHostname());
    log.error("ERROR log from {}", EnvUtils.getHostname());
    return new ResponseEntity<>("yay! It worked!", HttpStatus.OK);
  }

}
