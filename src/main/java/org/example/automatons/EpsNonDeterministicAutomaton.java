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
        Set<String> currentStates = new LinkedHashSet<>();
        currentStates.add(startState);
        findEpsilonClosure(startState);
        for(String symbol:input){
            if(alphabet.contains(symbol)) {
                System.out.println("Новая итерация");
                System.out.println("Текущие состояния: "+currentStates);
                System.out.println("Входной символ: "+symbol);
                Set<String> newCurrentStates = new LinkedHashSet<>();
                for(String currentState : currentStates) {
                    findEpsilonClosure(currentState);
                    if (transitionTable.get(currentState) != null && transitionTable.get(currentState).containsKey(symbol)) {
                        newCurrentStates.addAll(transitionTable.get(currentState).get(symbol));
                    }else if(transitionTable.get(currentState) != null && !transitionTable.get(currentState).containsKey(symbol)){
                        newCurrentStates.add(currentState);
                    }
                }
                if (!newCurrentStates.isEmpty()) {
                    currentStates = newCurrentStates;
                }
                System.out.println("Переход в состояния: "+currentStates);
            }else{
                return false;
            }
        }
        for (String state : currentStates) {
            if (finalStates.contains(state)) return true;
        }
        return false;
    }

    private Set<String> findEpsilonClosure(String targetState) {
        Set<String> epsilonClosure = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(targetState);
        epsilonClosure.add(targetState);

        while (!queue.isEmpty()) {
            String currentState = queue.poll();
            if (transitionTable.get(currentState) != null && transitionTable.get(currentState).containsKey("e")) {
                for (String nextState : transitionTable.get(currentState).get("e")) {
                    if (!epsilonClosure.contains(nextState)) {
                        epsilonClosure.add(nextState);
                        queue.add(nextState);
                    }
                }
            }
        }

        System.out.println("Состояние: "+targetState+" . "+"Эпсилон замыкание: "+epsilonClosure);
        return epsilonClosure;
    }
}
