package org.example.automatons;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NonDeterministicAutomaton extends Automaton{
    protected Map<String, Map<String, Set<String>>> transitionTable;

    protected NonDeterministicAutomaton(Set<String> states, Set<String> alphabet, String startState, Set<String> finalStates, Map<String, Map<String, Set<String>>> transitionTable) {
        super(states, alphabet, startState, finalStates);
        this.transitionTable=transitionTable;
    }

    @Override
    public boolean runAutomaton(List<String> input) {
        Set<String> currentStates = new HashSet<>();
        currentStates.add(this.getStartState());
        for(String symbol:input){
            if(alphabet.contains(symbol)) {
                Set<String> newCurrentStates = new HashSet<>();
                for(String currentState : currentStates) {
                    if (transitionTable.get(currentState).containsKey(symbol)) {
                        newCurrentStates.addAll(transitionTable.get(currentState).get(symbol));
                    }
                }
                currentStates.clear();
                currentStates.addAll(newCurrentStates);
            }else{
                return false;
            }
        }
        for(String state : currentStates){
            if(finalStates.contains(state)) return true;
        }
        return false;
    }
}
