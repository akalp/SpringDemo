package com.example.demo.repositories;

import com.example.demo.domains.Verb;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface VerbRepository extends Neo4jRepository<Verb, Long> {
    Collection<Verb> findByName(@Param("name") String name);
}
