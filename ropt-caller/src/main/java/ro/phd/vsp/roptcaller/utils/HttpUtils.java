package ro.phd.vsp.roptcaller.utils;

import java.util.Arrays;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import ro.phd.vsp.roptcaller.enums.ExecutionMethods;

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

  /**
   * Get Receiver Path by ExecutionMethods
   *
   * @param method
   * @return
   */
  public static String getRTDestinationPathByMethod(ExecutionMethods method) {
    String path = "";
    switch (method) {
      case GET:
        path = "/rt/mget";
        break;
      case SAVE:
        path = "/rt/msave";
        break;
      case GET_SAVE:
        path = "/rt/mget-save";
        break;
      default:
        throw new IllegalArgumentException("Unexpected value: " + method);
    }
    return path;
  }
}
