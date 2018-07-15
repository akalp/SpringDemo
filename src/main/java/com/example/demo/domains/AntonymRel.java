package com.example.demo.domains;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "Antonym")
public class AntonymRel {
    @Id @GeneratedValue Long id;
    @StartNode Wordnet s;
    @EndNode Wordnet e;

    public Wordnet getE() {
        return e;
    }
}
