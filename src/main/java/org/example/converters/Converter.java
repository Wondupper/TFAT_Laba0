package org.example.converters;

import org.example.automatons.Automaton;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Converter<T extends Automaton,E extends Automaton> {
    protected T automaton;

    protected Map<Set<String>,String> unionStatesAndAliases;

    protected Converter(T automaton) {
        this.automaton = automaton;
        this.unionStatesAndAliases = new HashMap<>();
    }

    public abstract E convert();
}