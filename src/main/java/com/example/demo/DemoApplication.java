package com.example.demo;

import com.example.demo.repositories.DefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableNeo4jRepositories
@EnableJpaRepositories
public class DemoApplication {

    @Autowired
    DefinitionRepository definitionRepository;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
    }
}
