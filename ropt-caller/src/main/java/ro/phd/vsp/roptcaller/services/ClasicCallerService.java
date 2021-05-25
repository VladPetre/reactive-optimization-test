package ro.phd.vsp.roptcaller.services;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ClasicCallerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClasicCallerService.class);

  private final RestTemplate restTemplate;

  @Value("${ROPT_RECEIVER_URI}")
  public String roptReceiverURI;


  @Scheduled(fixedDelay = 1000, initialDelay = 1000)
  public void testReceiverMService() {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<String> response = null;

    try {
      response = restTemplate
          .exchange(buildURI("/test"), HttpMethod.GET, entity, String.class);
    } catch (RestClientException e) {
      LOGGER.error(e.getStackTrace().toString());
    }

    LOGGER.info("res: {}", response);
  }

  
  private String buildURI(String path) {
    return roptReceiverURI + path;
  }

}