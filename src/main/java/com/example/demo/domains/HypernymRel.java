package com.example.demo.domains;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "Hypernym")
public class HypernymRel {
    @Id @GeneratedValue private Long id;
    @StartNode private Wordnet s;
    @EndNode private Wordnet e;

//    public Wordnet getS() {
//        return s;
//    }


    public Wordnet getE() {
        return e;
    }
}
