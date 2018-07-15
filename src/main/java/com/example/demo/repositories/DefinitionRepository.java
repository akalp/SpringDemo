package com.example.demo.repositories;

import com.example.demo.domains.Definition;
import org.springframework.data.repository.CrudRepository;

public interface DefinitionRepository extends CrudRepository<Definition, String > {
    Definition findDefinitionBySynsetID(String synsetID);
}
