package com.example.demo.repositories;

import com.example.demo.domains.Adverb;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface AdverbRepository extends Neo4jRepository<Adverb, Long> {
    Collection<Adverb> findByName(@Param("name") String name);
}
