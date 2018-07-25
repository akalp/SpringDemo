package com.example.demo.repositories;

import com.example.demo.domains.Wordnet;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordnetRepository extends Neo4jRepository<Wordnet, Long> {
    List<Wordnet> findByName(@Param("name") String name);

    @Query("match (s:Wordnet{name:{word}})-[r]-(e) return s,r,e")
    List<Wordnet> graphOfWord(@Param("word") String word);

    @Query("match (s:Wordnet{name:{word}, lang:{sourceLang}})-[r]-(e) return s,r,e")
    List<Wordnet> graphOfWordWithSourceLang(@Param("word") String word, @Param("sourceLang") String sourceLang);

    @Query("match (s:Wordnet{name:{word}})-[r]-(e) where e.lang in {targetLang} return s,r,e")
    List<Wordnet> graphOfWordWithTargetLang(@Param("word") String word, @Param("targetLang") String[] targetLang);

    @Query("match (s:Wordnet{name:{word}, lang:{sourceLang}})-[r]-(e) where e.lang in {targetLang} return s,r,e")
    List<Wordnet> graphOfWordWithSourceAndTargetLang(@Param("word") String word, @Param("sourceLang") String sourceLang, @Param("targetLang") String[] targetLang);
}
