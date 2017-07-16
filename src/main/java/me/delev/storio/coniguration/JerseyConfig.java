package me.delev.storio.coniguration;


import me.delev.storio.api.story.StoryEndpoint;
import me.delev.storio.errors.GenericExceptionMapper;
import me.delev.storio.errors.NotFoundExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

  public JerseyConfig() {
    register(StoryEndpoint.class);

    // exceptions
    register(NotFoundExceptionMapper.class);
    register(GenericExceptionMapper.class);

    // filter
    register(CORSResponseFilter.class);

    property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
  }
}
