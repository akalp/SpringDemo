package com.example.demo.domains;

import org.neo4j.ogm.annotation.*;

import java.util.List;

@NodeEntity
public class Wordnet {
    @Id @GeneratedValue private Long id;

    private String name, lang, type;

    @Property(name = "id") private String synsetID;

    private String definition;

    @Relationship(type = "Hypernym", direction = Relationship.OUTGOING) private List<HypernymRel> hypernyms;

    @Relationship(type = "Hyponym", direction = Relationship.OUTGOING) private List<HyponymRel> hyponyms;

    @Relationship(type = "Antonym", direction = Relationship.OUTGOING) private List<AntonymRel> antonyms;

    @Relationship(type = "Meronym", direction = Relationship.OUTGOING) private List<MeronymRel> meronyms;

    @Relationship(type = "Holonym", direction = Relationship.OUTGOING) private List<HolonymRel> holonyms;

    @Relationship(type = "Synset", direction = Relationship.OUTGOING) private List<SynsetRel> synsets;


    public Wordnet(){}

    public Wordnet(String name, String lang, String type){
        this.name = name;
        this.lang = lang;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getLang() {
        return lang;
    }

    public String getType() {
        return type;
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

    public List<HypernymRel> getHypernyms() {
        return hypernyms;
    }

    public List<HyponymRel> getHyponyms() {
        return hyponyms;
    }

    public List<AntonymRel> getAntonyms() {
        return antonyms;
    }

    public List<SynsetRel> getSynsets() {
        return synsets;
    }

    public List<MeronymRel> getMeronyms() {
        return meronyms;
    }

    public List<HolonymRel> getHolonyms() {
        return holonyms;
    }
}
