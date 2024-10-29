package org.example.automatons;

import java.util.*;

public class EpsNonDeterministicAutomaton extends Automaton {
    public EpsNonDeterministicAutomaton(Set<String> states, Set<String> alphabet, String startState, Set<String> finalStates, Map<String, Map<String, Set<String>>> transitionTable) {
        super(states, alphabet, startState, finalStates, transitionTable);
    }

    public EpsNonDeterministicAutomaton(Automaton automaton) {
        super(automaton);
    }

    public EpsNonDeterministicAutomaton() {
        super();
    }

    @Override
    public boolean runAutomaton(List<String> input) {
        Set<String> currentStates = new HashSet<>();
        currentStates.add(this.getStartState());
        currentStates = goByEpsilon(this.getStartState());
        for (String symbol : input) {
            if (alphabet.contains(symbol)) {
                Set<String> newCurrentStates = new HashSet<>();
                Set<String> visitedStates = new HashSet<>();
                for(String currentState : currentStates) {
                    if (transitionTable.get(currentState).containsKey(symbol)) {
                        newCurrentStates.addAll(transitionTable.get(currentState).get(symbol));
                        visitedStates.add(currentState);
                    }else{
                        newCurrentStates.add(currentState);
                    }
                }
                currentStates.removeAll(visitedStates);
                for (String state : newCurrentStates) {
                    Set<String> addedStates = goByEpsilon(state);
                    currentStates.addAll(addedStates);
                }
            } else {
                return false;
            }
        }
        for (String state : currentStates) {
            if (finalStates.contains(state)) return true;
        }
        return false;
    }

    private Set<String> goByEpsilon(String currentState) {
        Set<String> currentStates = new HashSet<>();
        Queue<String> states = new LinkedList<>();
        states.add(currentState);
        while (!states.isEmpty()) {
            currentState = states.poll();
            currentStates.add(currentState);
            if (transitionTable.get(currentState).containsKey("e")) {
                Set<String> newCurrentStates = new HashSet<>();
                for (String state : transitionTable.get(currentState).get("e")) {
                    newCurrentStates.add(state);
                    states.add(state);
                }
                currentStates.remove(currentState);
                currentStates.addAll(newCurrentStates);
            }
        }
        return currentStates;
    }
}
