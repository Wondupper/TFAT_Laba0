package org.example;

import org.example.automatons.Automaton;
import org.example.automatons.DeterministicAutomaton;
import org.example.automatons.EpsNonDeterministicAutomaton;
import org.example.automatons.NonDeterministicAutomaton;
import org.example.converters.Converter;
import org.example.converters.EpsNonDetToNonDetConverter;
import org.example.converters.NonDetToDetConverter;
import org.example.io.AutomatonToJson;
import org.example.io.JsonToAutomaton;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        while(true) {
            System.out.println("Введите путь к автомату");
            String path = System.console().readLine();
            Automaton automaton = JsonToAutomaton.getAutomaton(path);
            System.out.println("Введите последовательность");
            System.out.println("Разрешенные символы: "+automaton.getAlphabet());
            List<String> input = Arrays.stream(System.console().readLine().split("")).toList();
            System.out.println("Результат работы: "+automaton.runAutomaton(input));
            if(!JsonToAutomaton.type.equals("DKA")) {
                System.out.println("Введите путь для сохранения преобразованного автомата");
                String path2 = System.console().readLine();
                switch (JsonToAutomaton.type) {
                    case "NKA" -> {
                        Converter<NonDeterministicAutomaton,DeterministicAutomaton> converter = new NonDetToDetConverter((NonDeterministicAutomaton) automaton);
                        AutomatonToJson.saveAutomatonToJson(path2,converter.convert());
                    }
                    case "ENKA" -> {
                        Converter<EpsNonDeterministicAutomaton,NonDeterministicAutomaton> converter = new EpsNonDetToNonDetConverter((EpsNonDeterministicAutomaton) automaton);
                        AutomatonToJson.saveAutomatonToJson(path2,converter.convert());
                    }
                }
            }
            System.out.println("Для работы с новым автоматом введите 'y'");
            if(!System.console().readLine().equals("y")) break;
        }
    }
}