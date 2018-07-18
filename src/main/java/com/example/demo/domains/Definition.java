package com.example.demo.domains;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "definitions", schema = "public")
public class Definition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue
    @Column(name = "id", insertable = false, updatable = false)
    private String synsetID;

    @Column(name = "eng")
    private String eng;

    protected Definition(){}

    public Definition(String synsetID, String eng){
        this.synsetID = synsetID;
        this.eng = eng;
    }

    public String getSynsetID() {
        return synsetID;
    }

    public String getEng() {
        return eng;
    }
}
