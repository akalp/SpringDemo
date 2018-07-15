package com.example.demo.domains;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Wordnet {
    @Id @GeneratedValue private Long id;

    private String name, lang;

    public Wordnet(){}

    public Wordnet(String name, String lang){
        this.name = name;
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public String getLang() {
        return lang;
    }

}
