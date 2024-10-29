package org.example.automatons;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public abstract class Automaton {
    protected Set<String> states;

    protected Set<String> alphabet;

    protected String startState;

    protected Set<String> finalStates;

    protected Map<String, Map<String, Set<String>>> transitionTable;

    protected Automaton(Set<String> states, Set<String> alphabet, String startState, Set<String> finalStates, Map<String, Map<String, Set<String>>> transitionTable) {
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.finalStates = finalStates;
        this.transitionTable = transitionTable;
    }

    protected Automaton(Automaton automaton){
        this.states = automaton.states;
        this.alphabet = automaton.alphabet;
        this.startState = automaton.startState;
        this.finalStates = automaton.finalStates;
        this.transitionTable = automaton.transitionTable;
    }

    protected Automaton() {
    }

    public abstract boolean runAutomaton(List<String> input);
}