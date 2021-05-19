package ro.phd.vsp.roptcaller.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String testDeploy()
    {
     return "{\"test\" : \"success\"}";
    }
}
