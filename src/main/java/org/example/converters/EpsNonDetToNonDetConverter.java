package org.example.converters;

import org.example.automatons.Automaton;
import org.example.automatons.EpsNonDeterministicAutomaton;
import org.example.automatons.NonDeterministicAutomaton;

import java.util.*;

public class EpsNonDetToNonDetConverter extends Converter<EpsNonDeterministicAutomaton, NonDeterministicAutomaton> {
    public EpsNonDetToNonDetConverter(EpsNonDeterministicAutomaton automaton) {
        super(automaton);
    }

    @Override
    public NonDeterministicAutomaton convert() {
        NonDeterministicAutomaton newAutomaton= initNewAutomaton();
        for(String state : newAutomaton.getStates()){
            Set<String> epsilonClosure = findEpsilonClosure(state);
            linkByEpsilonStatesInEpsilonClosure(newAutomaton,epsilonClosure);
            addNewFinalStates(newAutomaton,state,epsilonClosure);
            addNewTransitions(newAutomaton,epsilonClosure);
        }
        deleteAllEpsilonTransitions(newAutomaton);
        return newAutomaton;
    }

    private Set<String> findEpsilonClosure(String targetState) {
        Set<String> epsilonClosure = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(targetState);
        while (!queue.isEmpty()) {
            String currentState = queue.poll();
            epsilonClosure.add(currentState);
            if (automaton.getTransitionTable().get(currentState).containsKey("e")) {
                for (String state : automaton.getTransitionTable().get(currentState).get("e")) {
                    queue.add(state);
                    epsilonClosure.add(state);
                }
            }
        }
        return epsilonClosure;
    }

    private void linkByEpsilonStatesInEpsilonClosure(NonDeterministicAutomaton automaton, Set<String> epsilonClosure){
        List<String> epsilonClosureList = epsilonClosure.stream().toList();
        for (int i = 0; i < epsilonClosureList.size()-1; i++) {
            String currentState = epsilonClosureList.get(i);
            for (int j = epsilonClosureList.size()-1; j > i ; j--) {
                String currentState2 = epsilonClosureList.get(j);
                if(automaton.getTransitionTable().get(currentState).containsKey("e")) {
                    automaton.getTransitionTable().get(currentState).get("e").add(currentState2);
                }
            }
        }
    }

    private void addNewFinalStates(NonDeterministicAutomaton automaton, String state, Set<String> epsilonClosure){
        Set<String> finalStates = new LinkedHashSet<>(automaton.getFinalStates());
        for(String finalState :finalStates){
            if(epsilonClosure.contains(finalState)) automaton.getFinalStates().add(state);
        }
    }

    private void addNewTransitions(NonDeterministicAutomaton automaton,Set<String> epsilonClosure){
        List<String> epsilonClosureList = epsilonClosure.stream().toList();
        for (int i = 0; i < epsilonClosureList.size()-1; i++) {
            String currentState = epsilonClosureList.get(i);
            for (int j = i+1; j < epsilonClosureList.size(); j++) {
                String currentState2 = epsilonClosureList.get(j);
                for(String symbol : automaton.getTransitionTable().get(currentState2).keySet()){
                    if(!symbol.equals("e")) {
                        if (!automaton.getTransitionTable().get(currentState).containsKey(symbol)) {
                            automaton.getTransitionTable().get(currentState).put(symbol, automaton.getTransitionTable().get(currentState2).get(symbol));
                        } else {
                            automaton.getTransitionTable().get(currentState).get(symbol).addAll(automaton.getTransitionTable().get(currentState2).get(symbol));
                        }
                    }
                }
            }
        }
    }

    private void deleteAllEpsilonTransitions(NonDeterministicAutomaton automaton){
        for(String state : automaton.getStates()){
            automaton.getTransitionTable().get(state).remove("e");
        }
    }

    @Override
    protected NonDeterministicAutomaton initNewAutomaton() {
        NonDeterministicAutomaton newAutomaton= new NonDeterministicAutomaton();
        newAutomaton.setStates(new LinkedHashSet<>(automaton.getStates()));
        newAutomaton.setAlphabet(new LinkedHashSet<>(automaton.getAlphabet()));
        newAutomaton.setStartState(automaton.getStartState());
        newAutomaton.setFinalStates(new LinkedHashSet<>(automaton.getFinalStates()));
        newAutomaton.setTransitionTable(new LinkedHashMap<>(automaton.getTransitionTable()));
        return newAutomaton;
    }
}
