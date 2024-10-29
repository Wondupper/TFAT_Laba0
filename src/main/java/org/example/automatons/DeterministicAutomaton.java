package org.example.automatons;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DeterministicAutomaton extends Automaton {
    private Map<String, Map<String, String>> transitionTable;

    protected DeterministicAutomaton(Set<String> states, Set<String> alphabet, String startState, Set<String> finalStates, Map<String, Map<String, String>> transitionTable) {
        super(states, alphabet, startState, finalStates);
        this.transitionTable = transitionTable;
    }

    @Override
    public boolean runAutomaton(List<String> input) {
        String currentState = this.getStartState();
        for (String symbol : input) {
            if (alphabet.contains(symbol)) {
                if (transitionTable.get(currentState).containsKey(symbol)) {
                    currentState = transitionTable.get(currentState).get(symbol);
                }
            } else {
                return false;
            }
        }
        return finalStates.contains(currentState);
    }
}
