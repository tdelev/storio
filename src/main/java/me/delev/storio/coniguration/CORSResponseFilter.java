package me.delev.storio.coniguration;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

/**
 * CORS Response filter
 */
public class CORSResponseFilter implements ContainerResponseFilter {
  @Override
  public void filter(ContainerRequestContext requestContext,
                     ContainerResponseContext responseContext) throws IOException {
    MultivaluedMap<String, Object> headers = responseContext.getHeaders();

    headers.add("Access-Control-Allow-Origin", "*");
    //headers.add("Access-Control-Allow-Origin", "http://our-domain.org"); //allows CORS requests only coming from custom domain
    headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, OPTIONS");
    headers.add("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization, X-Requested-With");
  }
}
