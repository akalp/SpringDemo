package com.example.demo.repositories;

import com.example.demo.domains.Definition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefinitionRepository extends JpaRepository<Definition, String > {
    Definition findDefinitionBySynsetID(String synsetID);
    List<Definition> findDefinitionsBySynsetIDIsIn(Object[] synsetID);
}
