package me.delev.storio.errors;

import org.springframework.dao.DataIntegrityViolationException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

public class ErrorMessage {

  /**
   * contains the same HTTP Status code returned by the server
   */
  int status;

  /**
   * message describing the error
   */
  String message;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ErrorMessage(NotFoundException ex) {
    this.status = Response.Status.NOT_FOUND.getStatusCode();
    this.message = ex.getMessage();
  }

  public ErrorMessage(AppException appException) {
    this.status = appException.status;
    this.message = appException.getMessage();
  }

  public ErrorMessage(DataIntegrityViolationException e) {
    this.status = Response.Status.CONFLICT.getStatusCode();
    this.message = e.getLocalizedMessage();
  }

  public ErrorMessage() {
  }
}
