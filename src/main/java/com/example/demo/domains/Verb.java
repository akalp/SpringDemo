package com.example.demo.domains;

import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collection;

public class Verb extends Wordnet {

    @Property(name = "id") private String synsetID;

    private String definition;

    @Relationship(type = "Hypernym", direction = Relationship.OUTGOING) private Collection<HypernymRel> hypernyms;

    @Relationship(type = "Hyponym", direction = Relationship.OUTGOING) private Collection<HyponymRel> hyponyms;

    @Relationship(type = "Antonym", direction = Relationship.OUTGOING) private Collection<AntonymRel> antonyms;

    public Verb(){
        super();
    }

    public Verb(String synsetID, String name, String lang){
        super(name, lang);
        this.synsetID = synsetID;
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
}
