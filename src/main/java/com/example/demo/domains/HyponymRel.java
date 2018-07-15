package com.example.demo.domains;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "Hyponym")
public class HyponymRel {
    @Id @GeneratedValue Long id;
    @StartNode Wordnet s;
    @EndNode Wordnet e;

    public Wordnet getE() {
        return e;
    }
}
