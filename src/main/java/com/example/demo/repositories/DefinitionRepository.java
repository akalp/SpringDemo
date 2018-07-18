package com.example.demo.repositories;

import com.example.demo.domains.Definition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefinitionRepository extends JpaRepository<Definition, String > {
    Definition findDefinitionBySynsetID(String synsetID);
}
