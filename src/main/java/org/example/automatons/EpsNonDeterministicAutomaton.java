package org.example.automatons;

import java.util.Map;
import java.util.Set;


public class EpsNonDeterministicAutomaton extends NonDeterministicAutomaton{
    protected EpsNonDeterministicAutomaton(Set<String> states, Set<String> alphabet, String startState, Set<String> finalStates, Map<String, Map<String, Set<String>>> transitionTable) {
        super(states, alphabet, startState, finalStates, transitionTable);
    }
}
