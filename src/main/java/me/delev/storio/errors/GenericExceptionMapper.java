package me.delev.storio.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Generic exception mapper
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
  Logger log = LoggerFactory.getLogger(GenericExceptionMapper.class);

  public Response toResponse(Throwable ex) {
    log.error("GenericException", ex);
    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    errorMessage.setMessage(ex.getMessage());
    ex.printStackTrace();
    return Response.status(errorMessage.getStatus())
      .entity(errorMessage)
      .type(MediaType.APPLICATION_JSON)
      .build();
  }

}
