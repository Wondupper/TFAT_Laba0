package org.example.converters;

import org.example.automatons.DeterministicAutomaton;
import org.example.automatons.NonDeterministicAutomaton;

import java.util.*;

public class NonDetToDetConverter extends Converter<NonDeterministicAutomaton, DeterministicAutomaton> {

    private Map<Set<String>, Map<String, Set<String>>> transitionTableWithSets;

    public NonDetToDetConverter(NonDeterministicAutomaton automaton) {
        super(automaton);
        this.transitionTableWithSets = new LinkedHashMap<>();
    }

    @Override
    public DeterministicAutomaton convert() {
        DeterministicAutomaton newAutomaton = initNewAutomaton();
        clearTransitionTableWithSets();
        Set<Set<String>> terminalSets = new LinkedHashSet<>();
        Queue<Set<String>> queue = new LinkedList<>();
        Set<Set<String>> visitedSets = new HashSet<>();
        Set<String> startSet = new LinkedHashSet<>();
        startSet.add(automaton.getStartState());
        queue.add(startSet);
        visitedSets.add(startSet);
        transitionTableWithSets.put(startSet, new LinkedHashMap<>());
        while (!queue.isEmpty()) {
            Set<String> currentSet = queue.poll();
            for (String symbol : automaton.getAlphabet()) {
                Set<String> newSet = new LinkedHashSet<>();
                for (String state : currentSet) {
                    if (automaton.getTransitionTable().get(state).containsKey(symbol)) {
                        newSet.addAll(automaton.getTransitionTable().get(state).get(symbol));
                    }
                }
                if (!newSet.isEmpty() && !visitedSets.contains(newSet)) {
                    queue.add(newSet);
                    visitedSets.add(newSet);
                    transitionTableWithSets.put(newSet, new LinkedHashMap<>()); // Инициализация переходов для нового множества
                }
                transitionTableWithSets.get(currentSet).put(symbol, newSet);
            }
            for (String state : currentSet) {
                if (automaton.getFinalStates().contains(state)) {
                    terminalSets.add(currentSet);
                    break;
                }
            }
        }
        updateNewAutomaton(newAutomaton, startSet, terminalSets);
        return newAutomaton;
    }

    @Override
    protected DeterministicAutomaton initNewAutomaton() {
        DeterministicAutomaton newAutomaton = new DeterministicAutomaton();
        newAutomaton.setStates(new LinkedHashSet<>());
        newAutomaton.setAlphabet(new LinkedHashSet<>());
        newAutomaton.setStartState("");
        newAutomaton.setFinalStates(new LinkedHashSet<>());
        newAutomaton.setTransitionTable(new LinkedHashMap<>());
        return newAutomaton;
    }

    private void clearTransitionTableWithSets() {
        transitionTableWithSets.clear();
    }

    private void updateNewAutomaton(DeterministicAutomaton newAautomaton, Set<String> startSet, Set<Set<String>> finalSets) {
        newAautomaton.setStartState(setToString(startSet));

        for (Set<String> set : finalSets) {
            newAautomaton.getFinalStates().add(setToString(set));
        }

        newAautomaton.getAlphabet().addAll(automaton.getAlphabet());

        for (Set<String> key : transitionTableWithSets.keySet()) {
            newAautomaton.getStates().add(setToString(key));
        }

        for (Set<String> key : transitionTableWithSets.keySet()) {
            newAautomaton.getTransitionTable().put(setToString(key), new LinkedHashMap<>());
            for (String symbol : transitionTableWithSets.get(key).keySet()) {
                newAautomaton.getTransitionTable().get(setToString(key)).put(symbol, new LinkedHashSet<>());
                Set<String> singleSet = new LinkedHashSet<>();
                singleSet.add(setToString(transitionTableWithSets.get(key).get(symbol)));
                if (!setToString(singleSet).isEmpty()) {
                    newAautomaton.getTransitionTable().get(setToString(key)).put(symbol, new LinkedHashSet<>());
                    newAautomaton.getTransitionTable().get(setToString(key)).get(symbol).add(setToString(singleSet));
                }
                if(newAautomaton.getTransitionTable().get(setToString(key)).get(symbol).isEmpty()){
                    newAautomaton.getTransitionTable().get(setToString(key)).remove(symbol);
                }
            }
        }
    }

    private String setToString(Set<String> set) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String state : set) {
            stringBuilder.append(state).append(";");
        }
        if (!stringBuilder.toString().isEmpty()) stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
