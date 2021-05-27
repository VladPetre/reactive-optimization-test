package ro.phd.vsp.roptcaller;

import java.util.Arrays;
import java.util.Optional;

public enum ExecutionMethods {
  GET,
  SAVE,
  GET_SAVE;
  private String text;

  public String valueOf() {
    return this.text;
  }

  public static Optional<ExecutionMethods> fromText(String text) {
    return Arrays.stream(values())
        .filter(bl -> bl.text.equalsIgnoreCase(text))
        .findFirst();
  }

}
