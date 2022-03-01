package ro.phd.vsp.roptreceiverreactive.utils;

import java.util.List;
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
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("instance_id", instanceId);
    return headers;
  }

}
