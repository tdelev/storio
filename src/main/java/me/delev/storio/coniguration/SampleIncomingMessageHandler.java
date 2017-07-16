package me.delev.storio.coniguration;

import org.axonframework.domain.DomainEventMessage;
import org.axonframework.domain.EventMessage;
import org.axonframework.eventhandling.Cluster;
import org.axonframework.eventhandling.replay.IncomingMessageHandler;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author tdelev
 */
public class SampleIncomingMessageHandler implements IncomingMessageHandler {

  final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void prepareForReplay(Cluster destination) {
    log.debug("Preparing for replay: {}", destination.getName());
  }

  @Override
  public List<EventMessage> onIncomingMessages(Cluster destination, EventMessage... messages) {
    return null;
  }

  @Override
  public List<EventMessage> releaseMessage(Cluster destination, DomainEventMessage message) {
    DateTime dateTime = message.getTimestamp();
    LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime.toDate().getTime()), ZoneId.systemDefault());
    log.debug("On release message: {}", message.getIdentifier());
    log.debug("Sequence number: {}", message.getSequenceNumber());
    log.debug("Time: {}", time);
    return null;
  }

  @Override
  public void processBacklog(Cluster destination) {

  }

  @Override
  public void onReplayFailed(Cluster destination, Throwable cause) {

  }
}
