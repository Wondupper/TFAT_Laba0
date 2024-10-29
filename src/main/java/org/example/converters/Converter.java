package org.example.converters;

import org.example.automatons.Automaton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Converter<T extends Automaton,E extends Automaton> {
    protected T automaton;

    protected Converter(T automaton) {
        this.automaton = automaton;
    }

    public abstract E convert();

    protected abstract E initNewAutomaton();

    protected void unionStates(String state1, String state2){
        String newState = state1+";"+state2;
        Map<String, Set<String>> newMap = new HashMap<>();
        Set<String> newKeys = new HashSet<>();
        newKeys.addAll(automaton.getTransitionTable().get(state1).keySet());
        newKeys.addAll(automaton.getTransitionTable().get(state2).keySet());
        for(String symbol : newKeys){
            Set<String> newValue = new HashSet<>();
            if(automaton.getTransitionTable().get(state1).containsKey(symbol)){
                newValue.addAll(automaton.getTransitionTable().get(state1).get(symbol));
            }if(automaton.getTransitionTable().get(state2).containsKey(symbol)){
                newValue.addAll(automaton.getTransitionTable().get(state2).get(symbol));
            }
            newMap.put(symbol,newValue);
        }
        automaton.getTransitionTable().remove(state1);
        automaton.getTransitionTable().remove(state2);
        automaton.getTransitionTable().put(newState,newMap);
    }
}
