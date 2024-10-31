package org.example.converters;

import org.example.automatons.Automaton;

public abstract class Converter<T extends Automaton, E extends Automaton> {
    protected T automaton;

    protected Converter(T automaton) {
        this.automaton = automaton;
    }

    public abstract E convert();

    protected abstract E initNewAutomaton();
}