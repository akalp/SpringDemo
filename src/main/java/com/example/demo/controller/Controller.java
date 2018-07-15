package com.example.demo.controller;

import com.example.demo.domains.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/")
public class Controller {
    final private
    DefinitionRepository definitionRepository;
    final private
    WordnetRepository wordnetRepository;
    final private
    WordRepository wordRepository;

    @Autowired
    public Controller(DefinitionRepository definitionRepository, WordnetRepository wordnetRepository, WordRepository wordRepository) {
        this.definitionRepository = definitionRepository;
        this.wordnetRepository = wordnetRepository;
        this.wordRepository = wordRepository;
    }

    @RequestMapping(value = "/words/{id}", method = RequestMethod.GET)
    public Word getWordByID(@PathVariable("id") Long id) {
        return wordRepository.findWordById(id);
    }

    @RequestMapping(value = "/words", method = RequestMethod.GET)
    public Collection<Word> findWordsByName(@RequestParam("name") String name) {
        return wordRepository.findByName(name);
    }

    @RequestMapping(value = "/wordnets", method = RequestMethod.GET)
    public Collection<Wordnet> findWordnetsByName(@RequestParam("name") String name) {
        Collection<Wordnet> wordnets = wordnetRepository.findByName(name);
        for (Wordnet wordnet : wordnets) {
            if (wordnet.getSynsetID() != null) {
                wordnet.setDefinition(definitionRepository.findDefinitionBySynsetID(wordnet.getSynsetID()).getEng());
            }
            if (wordnet.getSynsets() != null) {
                for (SynsetRel synsetRel : wordnet.getSynsets()) {
                    Wordnet e = synsetRel.getE();
                    e.setDefinition(definitionRepository.findDefinitionBySynsetID(e.getSynsetID()).getEng());
                }
            }
            if (wordnet.getHyponyms() != null) {
                for (HyponymRel hyponymRel : wordnet.getHyponyms()) {
                    hyponymRel.getE().setDefinition(definitionRepository.findDefinitionBySynsetID(hyponymRel.getE().getSynsetID()).getEng());
                }
            }
            if (wordnet.getHypernyms() != null) {
                for (HypernymRel hypernymRel : wordnet.getHypernyms()) {
                    hypernymRel.getE().setDefinition(definitionRepository.findDefinitionBySynsetID(hypernymRel.getE().getSynsetID()).getEng());
                }
            }
            if (wordnet.getAntonyms() != null) {
                for (AntonymRel antonymRel : wordnet.getAntonyms()) {
                    antonymRel.getE().setDefinition(definitionRepository.findDefinitionBySynsetID(antonymRel.getE().getSynsetID()).getEng());
                }
            }
        }
        return wordnets;
    }


}
