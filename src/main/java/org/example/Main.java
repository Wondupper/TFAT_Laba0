package org.example;

import org.example.automatons.Automaton;
import org.example.io.AutomatonToJson;
import org.example.io.JsonToAutomaton;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Automaton automaton = JsonToAutomaton.getAutomaton("src/main/resources/ENKA.json");
        AutomatonToJson.saveAutomatonToJson("src/main/resources/save2",automaton);
    }
}