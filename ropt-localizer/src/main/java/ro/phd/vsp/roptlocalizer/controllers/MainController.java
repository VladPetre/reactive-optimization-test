package ro.phd.vsp.roptlocalizer.controllers;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.phd.vsp.roptlocalizer.service.SensorsService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/rt/sensors", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class MainController {

  private final SensorsService sensorsService;

  @GetMapping(path = "/{id}")
  public ResponseEntity<String> getById(@PathVariable UUID id) {
    return new ResponseEntity<>(sensorsService.getLocationById(id), HttpStatus.OK);
  }

}
