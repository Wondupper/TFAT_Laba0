package org.example.converters;

import org.example.automatons.Automaton;

//TODO
public class NonDetToDetConverter extends Converter{
    protected NonDetToDetConverter(Automaton automaton) {
        super(automaton);
    }

    @Override
    public Automaton convert() {
        String currentState;
        return automaton;
    }

}
