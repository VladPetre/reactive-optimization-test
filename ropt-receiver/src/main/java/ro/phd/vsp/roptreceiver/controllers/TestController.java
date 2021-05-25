package ro.phd.vsp.roptreceiver.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TestController {

  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  public String testInterCom() {
    return "{\"test\" : \"success\"}";
  }

}
