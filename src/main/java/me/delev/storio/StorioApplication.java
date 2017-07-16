package me.delev.storio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "me.delev.storio.query.story.repository")
@EntityScan(basePackages = "me.delev.storio.query.story.model")
public class StorioApplication {

  public static void main(String[] args) {
    SpringApplication.run(StorioApplication.class, args);
  }
}
