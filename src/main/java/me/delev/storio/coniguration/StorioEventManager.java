package me.delev.storio.coniguration;

import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.EventVisitor;
import org.axonframework.eventstore.management.Criteria;
import org.axonframework.eventstore.management.CriteriaBuilder;
import org.axonframework.eventstore.management.EventStoreManagement;

import javax.inject.Inject;

/**
 * Created by tdelev on 12.3.16.
 */
public class StorioEventManager implements EventStoreManagement {


  @Override
  public void visitEvents(EventVisitor visitor) {
    System.out.println("visit events");
  }

  @Override
  public void visitEvents(Criteria criteria, EventVisitor visitor) {

  }

  @Override
  public CriteriaBuilder newCriteriaBuilder() {
    return null;
  }
}
