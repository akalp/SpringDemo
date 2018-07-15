package com.example.demo.repositories;

import com.example.demo.domains.Noun;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface NounRepository extends Neo4jRepository<Noun, Long> {
    Collection<Noun> findByName(@Param("name") String name);
}
