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

    protected Automaton(Set<String> states, Set<String> alphabet, String startState, Set<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.finalStates = finalStates;
    }

    public abstract boolean runAutomaton(List<String> input);
}