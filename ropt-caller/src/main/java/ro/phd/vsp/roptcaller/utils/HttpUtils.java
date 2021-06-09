package ro.phd.vsp.roptcaller.utils;

import java.util.Arrays;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpUtils {

  /**
   * Build header for Resttemplate request
   *
   * @return httpHeaders
   */
  public static HttpHeaders buildRTHeaders(String instanceId) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("instance_id", instanceId);
    return headers;
  }

}
