package com.example.demo.controller;

import com.example.demo.domains.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/")
public class Controller {
    final
    DefinitionRepository definitionRepository;
    final
    WordnetRepository wordnetRepository;
    final
    WordRepository wordRepository;
    final
    NounRepository nounRepository;
    final
    VerbRepository verbRepository;
    final
    AdverbRepository adverbRepository;
    final
    AdjectiveRepository adjectiveRepository;

    @Autowired
    public Controller(DefinitionRepository definitionRepository, WordnetRepository wordnetRepository, WordRepository wordRepository, NounRepository nounRepository, VerbRepository verbRepository, AdverbRepository adverbRepository, AdjectiveRepository adjectiveRepository) {
        this.definitionRepository = definitionRepository;
        this.wordnetRepository = wordnetRepository;
        this.wordRepository = wordRepository;
        this.nounRepository = nounRepository;
        this.verbRepository = verbRepository;
        this.adverbRepository = adverbRepository;
        this.adjectiveRepository = adjectiveRepository;
    }

    @RequestMapping(value = "/words/{id}", method = RequestMethod.GET)
    public Word getWordByID(@PathVariable("id") Long id){
        return wordRepository.findWordById(id);
    }

    @RequestMapping(value = "/words", method = RequestMethod.GET)
    public Collection<Word> findWordsByName(@RequestParam("name") String name){
        return wordRepository.findByName(name);
    }

    @RequestMapping(value = "/nouns", method = RequestMethod.GET)
    public Collection<Noun> findNounsByName(@RequestParam("name") String name){
        Collection<Noun> nouns = nounRepository.findByName(name);
        for(Noun noun:nouns){
            noun.setDefinition(definitionRepository.findDefinitionBySynsetID(noun.getSynsetID()).getEng());
        }
        return nouns;
    }

    @RequestMapping(value = "/verbs", method = RequestMethod.GET)
    public Collection<Verb> findVerbsByName(@RequestParam("name") String name){
        return verbRepository.findByName(name);
    }

    @RequestMapping(value = "/adverbs", method = RequestMethod.GET)
    public Collection<Adverb> findAdverbsByName(@RequestParam("name") String name){
        return adverbRepository.findByName(name);
    }

    @RequestMapping(value = "/adjectives", method = RequestMethod.GET)
    public Collection<Adjective> findAdjectivesByName(@RequestParam("name") String name){
        return adjectiveRepository.findByName(name);
    }

    @RequestMapping(value = "/wordnets", method = RequestMethod.GET)
    public Collection<Wordnet> findWordnetsByName(@RequestParam("name") String name){
        return wordnetRepository.findByName(name);
    }


}
