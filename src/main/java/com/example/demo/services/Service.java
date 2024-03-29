package com.example.demo.services;

import com.example.demo.domains.*;
import com.example.demo.repositories.DefinitionRepository;
import com.example.demo.repositories.WordnetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@org.springframework.stereotype.Service
public class Service {

    final private
    DefinitionRepository definitionRepository;
    final private
    WordnetRepository wordnetRepository;

    @Autowired
    public Service(DefinitionRepository definitionRepository, WordnetRepository wordnetRepository) {
        this.definitionRepository = definitionRepository;
        this.wordnetRepository = wordnetRepository;
    }

    public Map<String, Object> graphOfWordWithSourceAndTargetLang(String name,
                                     String slang,
                                     String tlang[],
                                     String rels[]) {
        return toD3(wordnetRepository.graphOfWordWithSourceAndTargetLang(name, slang, tlang, rels));
    }

    /**
     * This function returns map object for D3 visualization
     * @param wordnets Wordnet objects which returns from repository according to parameters
     * @return Map for json output to D3 visualization
     */
    private Map<String, Object> toD3(List<Wordnet> wordnets) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> rels = new ArrayList<>();
        Map<String, Set<String>> synsetIDs = new HashMap<>();
        synsetIDs.put("Noun", new HashSet<>());
        synsetIDs.put("Verb", new HashSet<>());
        synsetIDs.put("Adverb", new HashSet<>());
        synsetIDs.put("Adjective", new HashSet<>());
        Map<String, Object> definitions = new HashMap<>();

        String[] nodeKeys = new String[]{"word","lang","type", "synsetID", "definition"};

        for (Wordnet wordnet : wordnets) {

            if (wordnet.getSynsetID() != null)
                synsetIDs.get(wordnet.getType()).add(wordnet.getSynsetID());

            Map<String, Object> source = map(nodeKeys,
                    new Object[]{wordnet.getName(), wordnet.getLang(), wordnet.getType(), wordnet.getSynsetID(), wordnet.getDefinition()});

            if(!nodes.contains(source))
                nodes.add(source);

            if (wordnet.getSynsets() != null)
                for (SynsetRel synsetRel : wordnet.getSynsets()) {
                    Wordnet node = synsetRel.getE();
                    synsetIDs.get(node.getType()).add(node.getSynsetID());
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
                    synsetIDs.get(node.getType()).add(node.getSynsetID());
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
                    synsetIDs.get(node.getType()).add(node.getSynsetID());
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
                    synsetIDs.get(node.getType()).add(node.getSynsetID());
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
                    synsetIDs.get(node.getType()).add(node.getSynsetID());
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
                    synsetIDs.get(node.getType()).add(node.getSynsetID());
                    Map<String, Object> target = map(nodeKeys,
                            getNodeObjects(node));
                    if(!nodes.contains(target)){
                        nodes.add(target);
                    }
                    rels.add(map(new String[]{"source", "target", "type"}, new Object[]{nodes.indexOf(source), nodes.indexOf(target), "Holonym"}));
                }
        }

        for(String key: synsetIDs.keySet()){
            List<Definition> defs = definitionRepository.findDefinitionsBySynsetIDIsIn(synsetIDs.get(key).toArray());
            List<Map<String, Object>> maps = new ArrayList<>();
            for(Definition definition: defs){
                maps.add(map(new String[]{"id", "definition"}, new Object[]{definition.getSynsetID(), definition.getEng()}));
            }
            definitions.put(key, maps);
        }
        return map(new String[]{"nodes", "links", "definitions"} , new Object[]{nodes,rels,definitions});
    }

    /**
     * It returns map according to parameters
     * @param keys String array of keys
     * @param objects Object array of values of keys
     * @return Map object for json
     */
    private Map<String, Object> map(String[] keys, Object[] objects) {
        int end = keys.length;
        Map<String, Object> result = new HashMap<String, Object>(end);
        for(int i=0; i<end; i++){
            result.put(keys[i], objects[i]);
        }
        return result;
    }

    /**
     * @param node Wordnet Object
     * @return Object array of properties of Wordnet object
     */
    private Object[] getNodeObjects(Wordnet node){
        return new Object[]{node.getName(), node.getLang(), node.getType(), node.getSynsetID(), node.getDefinition()};
    }

}
