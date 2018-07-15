package com.example.demo.repositories;

import com.example.demo.domains.Word;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface WordRepository extends Neo4jRepository<Word, Long> {
    Collection<Word> findByName(String name);
    Word findWordById(Long id);
}
