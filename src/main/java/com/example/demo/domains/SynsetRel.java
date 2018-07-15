package com.example.demo.domains;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "Synset")
public class SynsetRel {
    @Id @GeneratedValue Long id;
    @StartNode Wordnet s;
    @EndNode Wordnet e;

    public Wordnet getE() {
        return e;
    }
}
