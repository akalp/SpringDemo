package com.example.demo.controller;

import com.example.demo.domains.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@Scope("prototype")
@RestController
@RequestMapping(value = "/")
public class Controller {
    final private
    DefinitionRepository definitionRepository;
    final private
    WordnetRepository wordnetRepository;

    @Autowired
    public Controller(DefinitionRepository definitionRepository, WordnetRepository wordnetRepository) {
        this.definitionRepository = definitionRepository;
        this.wordnetRepository = wordnetRepository;
    }

    @RequestMapping(value = "/wordnet/graph", method = RequestMethod.GET)
    public Map<String, Object> graph(@RequestParam("name") String name, @RequestParam(value = "slang", required = false) String slang, @RequestParam(value = "tlang", required = false) String tlang) {
        if(slang != null && tlang != null)
            return toD3(wordnetRepository.graphOfWordWithSourceAndTargetLang(name, slang, tlang.split(",")));
        if(slang != null)
            return toD3(wordnetRepository.graphOfWordWithSourceLang(name, slang));
        if(tlang != null)
            return toD3(wordnetRepository.graphOfWordWithTargetLang(name, tlang.split(",")));

        return toD3(wordnetRepository.graphOfWord(name));
    }

    private Map<String, Object> toD3(Collection<Wordnet> wordnets) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        
        String[] nodeKeys = new String[]{"word","lang","type", "synsetID", "definition"};
        
        for (Wordnet wordnet : wordnets) {
            if (wordnet.getSynsetID() != null)
                wordnet.setDefinition(definitionRepository.findDefinitionBySynsetID(wordnet.getSynsetID()).getEng());
            
            Map<String, Object> source = map(nodeKeys, 
                    new Object[]{wordnet.getName(), wordnet.getLang(), wordnet.getType(), wordnet.getSynsetID(), wordnet.getDefinition()});
            
            if(!nodes.contains(source))
                nodes.add(source);
            
            if (wordnet.getSynsets() != null)
                for (SynsetRel synsetRel : wordnet.getSynsets()) {
                    Wordnet node = synsetRel.getE();
                    node.setDefinition(definitionRepository.findDefinitionBySynsetID(node.getSynsetID()).getEng());
                    Map<String, Object> target = map(nodeKeys, 
                            getNodeObjects(node));
                    if(!nodes.contains(target)){
                        nodes.add(target);
                    }
                    rels.add(map(new String[]{"source", "target", "type"}, new Object[]{nodes.indexOf(source), nodes.indexOf(target), "Synset"}));
                }
            if (wordnet.getHyponyms() != null)
                for (HyponymRel hyponymRel : wordnet.getHyponyms()) {
                    Wordnet node = hyponymRel.getE();
                    node.setDefinition(definitionRepository.findDefinitionBySynsetID(node.getSynsetID()).getEng());
                    Map<String, Object> target = map(nodeKeys, 
                            getNodeObjects(node));
                    if(!nodes.contains(target)){
                        nodes.add(target);
                    }
                    rels.add(map(new String[]{"source", "target", "type"}, new Object[]{nodes.indexOf(source), nodes.indexOf(target), "Hyponym"}));
                }
            if (wordnet.getHypernyms() != null)
                for (HypernymRel hypernymRel : wordnet.getHypernyms()) {
                    Wordnet node = hypernymRel.getE();
                    node.setDefinition(definitionRepository.findDefinitionBySynsetID(node.getSynsetID()).getEng());
                    Map<String, Object> target = map(nodeKeys, 
                            getNodeObjects(node));
                    if(!nodes.contains(target)){
                        nodes.add(target);
                    }
                    rels.add(map(new String[]{"source", "target", "type"}, new Object[]{nodes.indexOf(source), nodes.indexOf(target), "Hypernym"}));
                }

            if (wordnet.getAntonyms() != null)
                for (AntonymRel antonymRel : wordnet.getAntonyms()) {
                    Wordnet node = antonymRel.getE();
                    node.setDefinition(definitionRepository.findDefinitionBySynsetID(node.getSynsetID()).getEng());
                    Map<String, Object> target = map(nodeKeys, 
                            getNodeObjects(node));
                    if(!nodes.contains(target)){
                        nodes.add(target);
                    }
                    rels.add(map(new String[]{"source", "target", "type"}, new Object[]{nodes.indexOf(source), nodes.indexOf(target), "Antonym"}));
                }
                
            if (wordnet.getMeronyms() != null)
                for (MeronymRel meronymRel : wordnet.getMeronyms()) {
                    Wordnet node = meronymRel.getE();
                    node.setDefinition(definitionRepository.findDefinitionBySynsetID(node.getSynsetID()).getEng());
                    Map<String, Object> target = map(nodeKeys, 
                            getNodeObjects(node));
                    if(!nodes.contains(target)){
                        nodes.add(target);
                    }
                    rels.add(map(new String[]{"source", "target", "type"}, new Object[]{nodes.indexOf(source), nodes.indexOf(target), "Meronym"}));
                }
            if (wordnet.getHolonyms() != null)
                for (HolonymRel holonymRel : wordnet.getHolonyms()) {
                    Wordnet node = holonymRel.getE();
                    node.setDefinition(definitionRepository.findDefinitionBySynsetID(node.getSynsetID()).getEng());
                    Map<String, Object> target = map(nodeKeys, 
                            getNodeObjects(node));
                    if(!nodes.contains(target)){
                        nodes.add(target);
                    }
                    rels.add(map(new String[]{"source", "target", "type"}, new Object[]{nodes.indexOf(source), nodes.indexOf(target), "Holonym"}));
                }    
        }
        return map(new String[]{"nodes", "links"} , new Object[]{nodes,rels});
    }

    private Map<String, Object> map(String[] keys, Object[] objects) {
        int end = keys.length;
        Map<String, Object> result = new HashMap<String, Object>(end);
        for(int i=0; i<end; i++){
            result.put(keys[i], objects[i]);
        }
        return result;
    }
    
    private Object[] getNodeObjects(Wordnet node){
        return new Object[]{node.getName(), node.getLang(), node.getType(), node.getSynsetID(), node.getDefinition()};
    }

    @RequestMapping("/languages")
    public List<String> getLanguages(){
        return wordnetRepository.getLanguages();
    }


}
