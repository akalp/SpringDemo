package com.example.demo.domains;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collection;

@NodeEntity
public class Word extends Wordnet{

    @Relationship(type = "Synset", direction = Relationship.OUTGOING) private Collection<Wordnet> synsets;

    public Word(){
        super();
    }

    public Word(String name, String lang){
        super(name, lang);
    }

    public Collection<Wordnet> getSynsets() {
        return synsets;
    }
}
