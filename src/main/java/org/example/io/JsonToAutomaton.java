package org.example.io;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.automatons.Automaton;
import org.example.automatons.DeterministicAutomaton;
import org.example.automatons.EpsNonDeterministicAutomaton;
import org.example.automatons.NonDeterministicAutomaton;

import java.io.File;
import java.io.IOException;

public class JsonToAutomaton {
    public static Automaton getAutomaton(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode node = mapper.readTree(new File(path));
        switch (node.get("type").asText()){
            case "DKA" -> {
                return mapper.readValue(new File(path), DeterministicAutomaton.class);
            }
            case "NKA" -> {
                return mapper.readValue(new File(path), NonDeterministicAutomaton.class);
            }
            case "ENKA" -> {
                return mapper.readValue(new File(path), EpsNonDeterministicAutomaton.class);
            }
            default -> throw new IllegalStateException("Unexpected value: " + node.get("type"));
        }
    }
}
