package org.example.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.automatons.Automaton;
import org.example.automatons.DeterministicAutomaton;
import org.example.automatons.EpsNonDeterministicAutomaton;
import org.example.automatons.NonDeterministicAutomaton;

import java.io.File;
import java.io.IOException;

public class AutomatonToJson {
    public static void saveAutomatonToJson(String path,Automaton automaton) throws IOException {
        path = path+".json";
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(path), automaton);
        ObjectNode nodes = mapper.readValue(new File(path), ObjectNode.class);
        if(automaton instanceof DeterministicAutomaton){
            nodes.put("type", "DKA");
            mapper.writer().writeValue(new File(path), nodes);
        } else if (automaton instanceof NonDeterministicAutomaton) {
            nodes.put("type", "NKA");
            mapper.writer().writeValue(new File(path), nodes);
        } else if (automaton instanceof EpsNonDeterministicAutomaton) {
            nodes.put("type", "ENKA");
            mapper.writer().writeValue(new File(path), nodes);
        }

    }
}
