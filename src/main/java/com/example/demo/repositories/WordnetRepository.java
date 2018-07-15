package com.example.demo.repositories;

import com.example.demo.domains.Wordnet;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface WordnetRepository extends Neo4jRepository<Wordnet, Long> {
    Collection<Wordnet> findByName(@Param("name") String name);
}
