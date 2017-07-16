package me.delev.storio.coniguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.delev.storio.domain.story.Story;
import me.delev.storio.query.story.StoryPersistEventHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.commandhandling.interceptors.BeanValidationInterceptor;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.domain.DefaultIdentifierFactory;
import org.axonframework.domain.IdentifierFactory;
import org.axonframework.eventhandling.*;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.eventstore.jpa.*;
import org.axonframework.serializer.AnnotationRevisionResolver;
import org.axonframework.serializer.ChainingConverterFactory;
import org.axonframework.serializer.Serializer;
import org.axonframework.serializer.json.JacksonSerializer;
import org.axonframework.unitofwork.SpringTransactionManager;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;

/**
 * Axon Java Configuration with reasonable defaults like
 * SimpleCommandBus, SimpleEventBus and GenericJpaRepository.
 *
 * @author albert
 */
@Configuration
@EntityScan(basePackageClasses = {DomainEventEntry.class, SnapshotEventEntry.class})
public class AxonConfiguration {

  @Inject
  private PlatformTransactionManager transactionManager;

  @Bean
  public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {
    AnnotationEventListenerBeanPostProcessor processor = new AnnotationEventListenerBeanPostProcessor();
    processor.setEventBus(clusteringEventBus());
    return processor;
  }

  @Bean
  public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
    AnnotationCommandHandlerBeanPostProcessor processor = new AnnotationCommandHandlerBeanPostProcessor();
    processor.setCommandBus(commandBus());
    return processor;
  }

  @Bean
  public CommandBus commandBus() {
    SimpleCommandBus commandBus = new SimpleCommandBus();
    commandBus.setHandlerInterceptors(Arrays.asList(new BeanValidationInterceptor()));
    commandBus.setTransactionManager(new SpringTransactionManager(transactionManager));
    return commandBus;
  }

  @Bean
  public EventBus eventBus() {
    return new SimpleEventBus();
  }

  @Bean
  public Cluster readCluster() {
    return new SimpleCluster("read-cluster");
  }

  @Bean
  public ClusterSelector clusterSelector() {
    return new DefaultClusterSelector(readCluster());
  }

  @Bean
  public ClusteringEventBus clusteringEventBus() {
    return new ClusteringEventBus(clusterSelector());
  }

  @Bean
  public CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean() {
    CommandGatewayFactoryBean<CommandGateway> factory = new CommandGatewayFactoryBean<CommandGateway>();
    factory.setCommandBus(commandBus());
    return factory;
  }

  @Bean
  public IdentifierFactory identifierFactory() {
    return new DefaultIdentifierFactory();
  }

  @Bean
  public EntityManagerProvider entityManagerProvider() {
    return new ContainerManagedEntityManagerProvider();
  }

  @Bean
  JpaEventStore jpaEventStore() {
    return new JpaEventStore(entityManagerProvider(), serializer());
  }

  @Bean
  FileSystemEventStore fileSystemEventStore() {
    return new FileSystemEventStore(new SimpleEventFileResolver(new File("data/stories")));
  }

/*  @Bean
  EventStore eventStore() {
    return jpaEventStore();
  }*/

  @Bean
  Serializer serializer() {
    ObjectMapper objectMapper = new ObjectMapper();
    Serializer serializer = new JacksonSerializer(objectMapper,
      new AnnotationRevisionResolver(),
      new ChainingConverterFactory()
      , getClass().getClassLoader()); // fix for restart class loader bug
    return serializer;
  }

  @Bean
  public EventSourcingRepository<Story> storyRepository() {
    EventSourcingRepository<Story> repository =
      new EventSourcingRepository<>(Story.class, jpaEventStore());
    repository.setEventBus(clusteringEventBus());
    return repository;
  }

  @Bean
  public AggregateAnnotationCommandHandler<Story> storyCommandHandler() {
    AggregateAnnotationCommandHandler<Story> commandHandler =
      AggregateAnnotationCommandHandler.subscribe(Story.class, storyRepository(), commandBus());
    return commandHandler;
  }

  @Bean
  EventEntryStore eventEntryStore() {
    return new DefaultEventEntryStore<>();
  }

  @Bean
  ReplayingCluster replayingCluster() {
    return new ReplayingCluster(readCluster(), jpaEventStore(),
      new SpringTransactionManager(transactionManager), 0, new SampleIncomingMessageHandler());
  }

}
