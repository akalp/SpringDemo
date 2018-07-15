package com.example.demo.domains;

import org.neo4j.ogm.annotation.*;

import java.util.Collection;

@NodeEntity
public class Wordnet {
    @Id @GeneratedValue private Long id;

    private String name, lang;

    @Property(name = "id") private String synsetID;

    private String definition;

    @Relationship(type = "Hypernym") private Collection<HypernymRel> hypernyms;

    @Relationship(type = "Hyponym") private Collection<HyponymRel> hyponyms;

    @Relationship(type = "Antonym") private Collection<AntonymRel> antonyms;

    @Relationship(type = "Synset") private Collection<SynsetRel> synsets;


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

    public String getSynsetID() {
        return synsetID;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Collection<HypernymRel> getHypernyms() {
        return hypernyms;
    }

    public Collection<HyponymRel> getHyponyms() {
        return hyponyms;
    }

    public Collection<AntonymRel> getAntonyms() {
        return antonyms;
    }

    public Collection<SynsetRel> getSynsets() {
        return synsets;
    }
}
