package org.example.converters;

import org.example.automatons.Automaton;
import org.example.automatons.EpsNonDeterministicAutomaton;
import org.example.automatons.NonDeterministicAutomaton;

import java.util.*;

public class EpsNonDetToNonDetConverter extends Converter<EpsNonDeterministicAutomaton, NonDeterministicAutomaton>{
    protected EpsNonDetToNonDetConverter(EpsNonDeterministicAutomaton automaton) {
        super(automaton);
    }

    @Override
    public NonDeterministicAutomaton convert() {
        for(String state : automaton.getTransitionTable().keySet()){
            unionStatesInEpsilonClosure(findEpsilonClosure(state));
        }
        return automaton;
    }

    private Set<String> findEpsilonClosure(String targetState){
        Set<String> epsilonClosure = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(targetState);
        while (!queue.isEmpty()){
            String currentState = queue.poll();
            epsilonClosure.add(targetState);
            if(!automaton.getTransitionTable().get(currentState).get("e").isEmpty()) {
                for (String state : automaton.getTransitionTable().get(currentState).get("e")) {
                    queue.add(state);
                }
            }
        }
        return epsilonClosure;
    }

    private void unionStatesInEpsilonClosure(Set<String> epsilonClosure){
        if(epsilonClosure.size()>=2){
            List<String> epsList = epsilonClosure.stream().toList();
            while (epsList.size()!=1){
                unionStates(epsList.getFirst(), epsList.getLast());
            }
        }
    }

    @Override
    protected NonDeterministicAutomaton initNewAutomaton() {
        return null;
    }
}
