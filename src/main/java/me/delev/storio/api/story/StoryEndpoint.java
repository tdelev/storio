package me.delev.storio.api.story;

import me.delev.storio.api.story.requests.PostStoryRequest;
import me.delev.storio.domain.story.commands.DeleteStoryCommand;
import me.delev.storio.domain.story.commands.LikeStoryCommand;
import me.delev.storio.domain.story.commands.PostStoryCommand;
import me.delev.storio.domain.story.commands.StoryChangeAuthorCommand;
import me.delev.storio.query.story.model.StoryEntity;
import me.delev.storio.query.story.repository.StoryReadRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.IdentifierFactory;
import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Story REST endpoint
 */
@Path("/stories")
@Produces(MediaType.APPLICATION_JSON)
public class StoryEndpoint {

  final Logger log = LoggerFactory.getLogger(getClass());

  @Inject
  IdentifierFactory identifierFactory;

  @Inject
  CommandGateway commandGateway;

  @Inject
  ReplayingCluster replayingCluster;

  @Inject
  StoryReadRepository readRepository;

  @POST
  public Response postStory(@Valid PostStoryRequest postStoryRequest) {
    log.debug("Post story request: {}", postStoryRequest);
    commandGateway.send(new PostStoryCommand(identifierFactory.generateIdentifier(),
      postStoryRequest.getAuthor(), postStoryRequest.getText()));
    return Response.noContent().build();
  }

  @POST
  @Path("/{id}/like")
  public Response likeStory(@PathParam("id") String id) {
    log.debug("Like story: {}", id);
    commandGateway.send(new LikeStoryCommand(id));
    return Response.ok().build();
  }

  @POST
  @Path("/{id}/change_author")
  public Response changeAuthor(@PathParam("id") String id, @FormParam("name") String name) {
    log.debug("Change name: {}", id);
    commandGateway.send(new StoryChangeAuthorCommand(id, name));
    return Response.ok().build();
  }

  @DELETE
  @Path("/{id}")
  public Response deleteStory(@PathParam("id") String id) {
    log.debug("Delete story: {}", id);
    commandGateway.send(new DeleteStoryCommand(id));
    return Response.ok().build();
  }

  @POST
  @Path("/replay")
  public void replayEvents() {
    replayingCluster.startReplay();
  }

  @GET
  public List<StoryEntity> getStories() {
    return readRepository.findAll();
  }

}
