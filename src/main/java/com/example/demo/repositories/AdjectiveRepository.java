package com.example.demo.repositories;

import com.example.demo.domains.Adjective;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface AdjectiveRepository extends Neo4jRepository<Adjective, Long> {
    Collection<Adjective> findByName(@Param("name") String name);
}
