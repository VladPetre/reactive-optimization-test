package ro.phd.vsp.roptreceiver.controllers;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.phd.vsp.roptreceiver.dtos.SensorDataDTO;
import ro.phd.vsp.roptreceiver.services.SensorDataService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/rt/sensor-data", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class MainController {

  private final SensorDataService sensorDataService;

  @GetMapping(path = "/{id}")
  public ResponseEntity<SensorDataDTO> getById(@PathVariable UUID id) {
    return new ResponseEntity<>(sensorDataService.getById(id), HttpStatus.OK);
  }

  @PostMapping()
  public ResponseEntity<SensorDataDTO> save(@RequestBody SensorDataDTO sensorDataDTO) {
    return new ResponseEntity<>(sensorDataService.save(sensorDataDTO), HttpStatus.CREATED);
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<SensorDataDTO> updateWithGet(@PathVariable UUID id,
      @RequestBody SensorDataDTO sensorDataDTO) {
    sensorDataDTO.setGuid(id);
    return new ResponseEntity<>(sensorDataService.updateWithGet(sensorDataDTO), HttpStatus.OK);
  }

}
