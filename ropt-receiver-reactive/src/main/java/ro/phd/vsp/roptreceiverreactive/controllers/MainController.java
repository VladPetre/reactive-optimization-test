package ro.phd.vsp.roptreceiverreactive.controllers;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import ro.phd.vsp.roptreceiverreactive.dtos.SensorDataDTO;
import ro.phd.vsp.roptreceiverreactive.services.SensorDataService;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/rt/sensor-data")
public class MainController {

  private final SensorDataService sensorDataService;

  @GetMapping(path = "/{id}")
  public Mono<SensorDataDTO> getById(@PathVariable UUID id) {
    return sensorDataService.getById(id);
  }

  @PostMapping()
  public Mono<SensorDataDTO> save(@RequestBody SensorDataDTO sensorDataDTO) {
    return sensorDataService.save(sensorDataDTO);
  }

  @PutMapping(path = "/{id}")
  public Mono<SensorDataDTO> updateWithGet(@PathVariable UUID id,
      @RequestBody SensorDataDTO sensorDataDTO) {
    sensorDataDTO.setGuid(id);
    return sensorDataService.updateWithGet(sensorDataDTO);
  }

}
