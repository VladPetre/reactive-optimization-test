package ro.phd.vsp.roptcaller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


  //@Scheduled(fixedRate = 1000, initialDelay = 1000)
  public void testReceiverMService(int nrOfThreads, int data) {
    ForkJoinPool forkJoinPool = null;

    List<Integer> list = new ArrayList<>();
    IntStream.range(0, data).forEach(i -> list.add(i));

    System.out.println(nrOfThreads + " - " + data);
    long start = System.currentTimeMillis();

    try {

      forkJoinPool = new ForkJoinPool(nrOfThreads);
      forkJoinPool.submit(
          () -> list.parallelStream().forEach(
              x -> {
                doGetRequest();
//                System.out.println(
//                    Thread.currentThread().getName() + " -- " /*+ (System.currentTimeMillis() - start)
//                        + " -- "*/ + doGetRequest());
              }
          )
      ).get();

      System.out.println("finish: " + (System.currentTimeMillis() - start));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String doGetRequest() {
    HttpHeaders headers = buildRTHeaders();

    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<String> response = null;

    try {
      response = restTemplate
          .exchange(buildURI("/test"), HttpMethod.GET, entity, String.class);
//      LOGGER.info("res: {}", response);
      return response.getBody();
    } catch (RestClientException e) {
      LOGGER.error("ex: {}", e);
    }

    return null;
  }

  private HttpHeaders buildRTHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    return headers;
  }


  private String buildURI(String path) {
    return roptReceiverURI + path;
  }

}