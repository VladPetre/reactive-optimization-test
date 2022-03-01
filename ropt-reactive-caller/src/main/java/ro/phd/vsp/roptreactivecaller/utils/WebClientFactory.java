package ro.phd.vsp.roptreactivecaller.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ro.phd.vsp.roptreactivecaller.enums.ExecutionMethod;

@Component
public class WebClientFactory {

  @Autowired
  @Qualifier(value = "classicClient")
  private WebClient classicClient;

  @Autowired
  @Qualifier(value = "reactiveClient")
  private WebClient reactiveClient;

  /**
   * Get webClient based on runType.
   * <p>CLASSIC: get webClient configured for classic run
   * <p>REACTIVE: get webClient configured for reactive run
   *
   * @param runMethod
   * @return
   */
  public WebClient getclient(ExecutionMethod runMethod) {
    switch (runMethod) {
      case GET:
      case SAVE:
      case GET_SAVE:
        return classicClient;
      case REACT_GET:
      case REACT_SAVE:
      case REACT_GET_SAVE:
        return reactiveClient;
      default:
        throw new IllegalArgumentException("Invalid run method!");
    }
  }

}
