package ro.phd.vsp.roptcaller.exceptions;

public class DataNotFoundException extends RuntimeException {

  public DataNotFoundException() {
  }

  public DataNotFoundException(String message) {
    super(message);
  }

  public DataNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataNotFoundException(Throwable cause) {
    super(cause);
  }
}