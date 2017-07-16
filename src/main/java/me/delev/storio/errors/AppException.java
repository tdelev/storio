package me.delev.storio.errors;


/**
 * Class to map application related exceptions
 */
public class AppException extends RuntimeException {

  private static final long serialVersionUID = -8999932578270387947L;

  /**
   * contains redundantly the HTTP status of the response sent back to the client in case of error, so that
   * the developer does not have to look into the response headers. If null a default
   */
  Integer status;

  /**
   * @param status
   * @param message
   */
  public AppException(int status, String message) {
    super(message);
    this.status = status;
  }

  public AppException() {
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

}
