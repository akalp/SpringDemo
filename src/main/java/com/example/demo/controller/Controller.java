package com.example.demo.controller;

import com.example.demo.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping(value = "/")
public class Controller {
    final private Service service;

    @Autowired
    public Controller(Service service) {
        this.service = service;
    }

    /**
     * @param name Name of word
     * @param slang Source language to search // filtering
     * @param tlang Target languages to search // filtering
     * @param rels Relationship types to search // filtering
     * @return Map to json output for D3 visualization and definitions of words
     */
    @RequestMapping(value = "/wordnet/graph", method = RequestMethod.GET)
    public Map<String, Object> graph(@RequestParam("name") String name,
                                     @RequestParam(value = "slang") String slang,
                                     @RequestParam(value = "tlang") String tlang,
                                     @RequestParam(value= "rels", required = false) String rels) {
        return service.graphOfWordWithSourceAndTargetLang(name, slang, tlang.split(","), rels.split(","));
    }

    /**
     * This function returns the 200 most commonly used languages and relationship types in the database.
     * @return Map object which contains languages and relationships.
     */
    @RequestMapping(value = "/filters", method = RequestMethod.GET)
    public Map<String,List<String>> getLanguages(){
        Map<String, List<String>> result = new HashMap<>();
        result.put("langs", Arrays.asList("eng", "fra", "deu", "rus", "spa", "fin", "ita", "swe", "nld", "pol", "kur", "por", "tur", "cmn", "jpn", "ces", "ell", "cat", "ukr", "hun", "epo", "lit", "ron", "ara", "lat", "dan", "oci", "nor", "fas", "ido", "bul", "hbs", "heb", "isl", "kor", "slk", "glg", "zho", "est", "srp", "eus", "mkd", "ind", "bel", "gle", "kat", "slv", "vie", "hye", "hrv", "hin", "mri", "msa", "lav", "kaz", "tgl", "aze", "tha", "swa", "nno", "mlg", "cym", "yue-can", "gla", "yid", "sqi", "vol", "tat", "fao", "tgk", "tuk", "tel", "afr", "uzb", "khm", "nrm", "kir", "ina", "grc", "lim", "mon", "ast", "urd", "ben", "nav", "bre", "roh", "mlt", "lao", "bak", "oss", "glv", "srd", "ltz", "nan", "mar", "tam", "wln", "mya", "fur"));
        result.put("rels", Arrays.asList("Synset","Hypernym","Hyponym","Antonym","Meronym","Holonym"));
        return result;
    }

}