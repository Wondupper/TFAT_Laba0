package org.example.automatons;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DeterministicAutomaton extends Automaton {

    public DeterministicAutomaton(Set<String> states, Set<String> alphabet, String startState, Set<String> finalStates, Map<String, Map<String, Set<String>>> transitionTable) {
        super(states, alphabet, startState, finalStates,transitionTable);
    }

    protected DeterministicAutomaton(Automaton automaton) {
        super(automaton);
    }

    public DeterministicAutomaton() {
        super();
    }

    @Override
    public boolean runAutomaton(List<String> input) {
        String currentState = this.getStartState();
        for (String symbol : input) {
            System.out.println("Новая итерация");
            System.out.println("Текущее состояние: "+currentState);
            System.out.println("Входной символ: "+currentState);
            if (alphabet.contains(symbol)) {
                if (transitionTable.get(currentState).containsKey(symbol)) {
                    currentState = transitionTable.get(currentState).get(symbol).stream().toList().getFirst();
                    System.out.println("Переход в состояние: "+currentState);
                }
            } else {
                return false;
            }
        }
        return finalStates.contains(currentState);
    }
}
