package ro.phd.vsp.roptlocalizerreactive.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "availability")
public class AvailabilityController {

  @GetMapping(value = "/ready")
  public String isReady() {
    return "{\"status\":\"UP\"}";
  }

  @GetMapping(value = "/live")
  public String isLive() {
    return "{\"status\":\"UP\"}";
  }

}
