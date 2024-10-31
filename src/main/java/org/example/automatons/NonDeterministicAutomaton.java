package org.example.automatons;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NonDeterministicAutomaton extends Automaton{
    public NonDeterministicAutomaton(Set<String> states, Set<String> alphabet, String startState, Set<String> finalStates, Map<String, Map<String, Set<String>>> transitionTable) {
        super(states, alphabet, startState, finalStates,transitionTable);
    }

    public NonDeterministicAutomaton(Automaton automaton) {
        super(automaton);
    }

    public NonDeterministicAutomaton() {
        super();
    }

    @Override
    public boolean runAutomaton(List<String> input) {
        Set<String> currentStates = new LinkedHashSet<>();
        currentStates.add(this.getStartState());
        for(String symbol:input){
            if(alphabet.contains(symbol)) {
                System.out.println("Новая итерация");
                System.out.println("Текущие состояния: "+currentStates);
                System.out.println("Входной символ: "+symbol);
                Set<String> newCurrentStates = new LinkedHashSet<>();
                Set<String> visitedStates = new LinkedHashSet<>();
                for(String currentState : currentStates) {
                    if (transitionTable.get(currentState).containsKey(symbol)) {
                        newCurrentStates.addAll(transitionTable.get(currentState).get(symbol));
                        visitedStates.add(currentState);
                    }else{
                        newCurrentStates.add(currentState);
                    }
                }
                currentStates.removeAll(visitedStates);
                currentStates.addAll(newCurrentStates);
                System.out.println("Переход в состояния: "+currentStates);
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
