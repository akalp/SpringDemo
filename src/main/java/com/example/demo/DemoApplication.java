package com.example.demo;

import com.example.demo.repositories.DefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class DemoApplication {

    @Autowired
    DefinitionRepository definitionRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
